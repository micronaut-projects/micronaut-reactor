/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.reactor.instrument;

import io.micrometer.context.ContextRegistry;
import io.micrometer.context.ThreadLocalAccessor;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.propagation.PropagatedContext;
import io.micronaut.reactor.config.ReactorConfiguration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayDeque;
import java.util.Optional;

/**
 * Integrate {@link PropagatedContext} with Reactor and Micrometer Context Propagation.
 *
 * @author Denis Stepanov
 * @since 4.0.0
 */
@Requires(classes = {Schedulers.class, ContextRegistry.class, PropagatedContext.class})
@Context
@Internal
@Replaces(ReactorInstrumentation.class)
final class ReactorAutomaticContextPropagation {

    private static final PropagatedContextThreadLocalAccessor ACCESSOR = new PropagatedContextThreadLocalAccessor();

    private final boolean enableAutomaticContextPropagation;
    private final boolean enableScheduleHookContextPropagation;

    ReactorAutomaticContextPropagation(ReactorConfiguration configuration) {
        this.enableAutomaticContextPropagation = Optional.ofNullable(configuration.enableAutomaticContextPropagation()).orElse(true);
        this.enableScheduleHookContextPropagation = Optional.ofNullable(configuration.enableScheduleHookContextPropagation()).orElse(false);
    }

    @PostConstruct
    void init() {
        if (enableScheduleHookContextPropagation) {
            Schedulers.onScheduleHook(ReactorInstrumentation.KEY, PropagatedContext::wrapCurrent);
        }
        if (enableAutomaticContextPropagation) {
            Hooks.enableAutomaticContextPropagation();
            ContextRegistry.getInstance()
                .registerThreadLocalAccessor(ACCESSOR);
        }
    }

    @PreDestroy
    void removeInstrumentation() {
        if (enableScheduleHookContextPropagation) {
            Schedulers.resetOnScheduleHook(ReactorInstrumentation.KEY);
        }
        if (enableAutomaticContextPropagation) {
            ContextRegistry.getInstance().removeThreadLocalAccessor(ACCESSOR.key());
        }
    }

    private static class PropagatedContextThreadLocalAccessor implements ThreadLocalAccessor<PropagatedContext> {

        private final ThreadLocal<ArrayDeque<PropagatedContext.Scope>> localScopes = new ThreadLocal<>();

        @Override
        public String key() {
            // TODO: replace with ReactorPropagation.PROPAGATED_CONTEXT_REACTOR_CONTEXT_VIEW_KEY
            return "micronaut.propagated.context";
        }

        @Override
        public PropagatedContext getValue() {
            return PropagatedContext.find().orElse(null);
        }

        @Override
        public void setValue(PropagatedContext propagatedContext) {
            ArrayDeque<PropagatedContext.Scope> scopes = localScopes.get();
            if (scopes == null) {
                scopes = new ArrayDeque<>(5);
                localScopes.set(scopes);
            }
            scopes.push(propagatedContext.propagate());
        }

        @Override
        public void setValue() {
            // Do nothing
        }

        @Override
        public void restore(PropagatedContext previousValue) {
            ArrayDeque<PropagatedContext.Scope> scopes = localScopes.get();
            if (scopes != null && !scopes.isEmpty()) {
                scopes.pop().close();
                if (scopes.isEmpty()) {
                    localScopes.remove();
                }
            }
        }

        @Override
        public void restore() {
        }
    }
}
