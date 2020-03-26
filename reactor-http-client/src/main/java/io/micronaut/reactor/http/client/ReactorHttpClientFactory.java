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
package io.micronaut.reactor.http.client;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Secondary;
import io.micronaut.http.client.RxHttpClientRegistry;
import io.micronaut.inject.InjectionPoint;

/**
 * Factory class for creating RxJava 3 clients.
 *
 * @author graemerocher
 * @since 1.0.0
 */
@Factory
public class ReactorHttpClientFactory {

    private final RxHttpClientRegistry clientRegistry;

    /**
     * Default constructor.
     * @param clientRegistry The client registry
     */
    public ReactorHttpClientFactory(RxHttpClientRegistry clientRegistry) {
        this.clientRegistry = clientRegistry;
    }

    /**
     * Injects an RxJava 3 client at the given injection point.
     * @param injectionPoint The injection point
     * @return The client
     */
    @Bean
    @Secondary
    protected BridgedReactorHttpClient httpClient(@Nullable InjectionPoint<?> injectionPoint) {
        if (injectionPoint != null) {
            return new BridgedReactorHttpClient(clientRegistry.getClient(injectionPoint.getAnnotationMetadata()));
        } else {
            return new BridgedReactorHttpClient(clientRegistry.getDefaultClient());
        }
    }
}