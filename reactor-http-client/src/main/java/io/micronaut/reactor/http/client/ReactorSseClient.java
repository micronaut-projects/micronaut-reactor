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

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.http.client.sse.SseClient;
import io.micronaut.http.sse.Event;
import io.micronaut.reactor.http.client.sse.BridgedReactorSseClient;
import reactor.core.publisher.Flux;

import java.net.URL;

/**
 * Reactor variation of the {@link SseClient} interface.
 *
 * @author graemerocher
 * @since 1.0.0
 */
public interface ReactorSseClient extends SseClient {

    @Override
    <I> Flux<Event<ByteBuffer<?>>> eventStream(@NonNull HttpRequest<I> request);

    @Override
    <I, B> Flux<Event<B>> eventStream(@NonNull HttpRequest<I> request, @NonNull Argument<B> eventType);

    @Override
    <I, B> Flux<Event<B>> eventStream(@NonNull HttpRequest<I> request, @NonNull Class<B> eventType);

    @Override
    <B> Flux<Event<B>> eventStream(@NonNull String uri, @NonNull Class<B> eventType);

    @Override
    <B> Flux<Event<B>> eventStream(@NonNull String uri, @NonNull Argument<B> eventType);

    /**
     * Create a new {@link ReactorSseClient}.
     * Note that this method should only be used outside of the context of a Micronaut application.
     * The returned {@link ReactorSseClient} is not subject to dependency injection.
     * The creator is responsible for closing the client to avoid leaking connections.
     * Within a Micronaut application use {@link jakarta.inject.Inject} to inject a client instead.
     *
     * @param url The base URL
     * @return The client
     * @since 2.1.0
     */
    static ReactorSseClient create(@Nullable URL url) {
        return new BridgedReactorSseClient(SseClient.create(url));
    }

    /**
     * Create a new {@link ReactorSseClient} with the specified configuration. Note that this method should only be used
     * outside of the context of an application. Within Micronaut use {@link jakarta.inject.Inject} to inject a client instead
     *
     * @param url The base URL
     * @param configuration the client configuration
     * @return The client
     * @since 2.1.0
     */
    static ReactorSseClient create(@Nullable URL url, @NonNull HttpClientConfiguration configuration) {
        return new BridgedReactorSseClient(SseClient.create(url, configuration));
    }
}
