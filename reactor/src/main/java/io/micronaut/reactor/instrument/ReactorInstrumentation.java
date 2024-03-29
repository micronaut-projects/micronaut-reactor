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

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.propagation.PropagatedContext;
import io.micronaut.reactor.config.ReactorConfiguration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

/**
 * Instruments Reactor such that the thread factory used by Micronaut is used and instrumentations can be applied to the {@link java.util.concurrent.ScheduledExecutorService}.
 *
 * @author Graeme Rocher
 * @since 1.0
 */
@Requires(classes = {Schedulers.Factory.class, PropagatedContext.class})
@Context
@Internal
final class ReactorInstrumentation {

    static final String KEY = "MICRONAUT_CONTEXT_PROPAGATION";

    private final boolean enableScheduleHookContextPropagation;

    ReactorInstrumentation(ReactorConfiguration configuration) {
        this.enableScheduleHookContextPropagation = Optional.ofNullable(configuration.enableScheduleHookContextPropagation()).orElse(true);
    }

    @PostConstruct
    void init() {
        if (enableScheduleHookContextPropagation) {
            Schedulers.onScheduleHook(KEY, PropagatedContext::wrapCurrent);
        }
    }

    @PreDestroy
    void removeInstrumentation() {
        if (enableScheduleHookContextPropagation) {
            Schedulers.resetOnScheduleHook(KEY);
        }
    }
}
