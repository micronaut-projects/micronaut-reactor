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
package io.micronaut.reactor.http.client.sse;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.sse.SseClient;
import io.micronaut.http.sse.Event;
import io.micronaut.reactor.http.client.ReactorSseClient;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

/**
 * Reactor bridge for the Server Sent events HTTP client.
 *
 * @author Sergio del Amo
 * @since 2.0.0
 */
@Internal
public class BridgedReactorSseClient implements ReactorSseClient, AutoCloseable  {

    private final SseClient sseClient;

    /**
     * Default constructor.
     * @param sseClient Server Sent Events HTTP Client
     */
    public BridgedReactorSseClient(SseClient sseClient) {
        this.sseClient = sseClient;
    }

    @Override
    public <I> Flux<Event<ByteBuffer<?>>> eventStream(@NonNull HttpRequest<I> request) {
        return Flux.from(sseClient.eventStream(request));
    }

    @Override
    public <I, B> Flux<Event<B>> eventStream(@NonNull HttpRequest<I> request, @NonNull Argument<B> eventType) {
        return Flux.from(sseClient.eventStream(request, eventType));
    }

    @Override
    public <I, B> Publisher<Event<B>> eventStream(@NonNull HttpRequest<I> request, @NonNull Argument<B> eventType, @NonNull Argument<?> errorType) {
        return Flux.from(sseClient.eventStream(request, eventType, errorType));
    }

    @Override
    public <I, B> Flux<Event<B>> eventStream(@NonNull HttpRequest<I> request, @NonNull Class<B> eventType) {
        return Flux.from(sseClient.eventStream(request, eventType));
    }

    @Override
    public <B> Flux<Event<B>> eventStream(@NonNull String uri, @NonNull Class<B> eventType) {
        return Flux.from(sseClient.eventStream(uri, eventType));
    }

    @Override
    public <B> Flux<Event<B>> eventStream(@NonNull String uri, @NonNull Argument<B> eventType) {
        return Flux.from(sseClient.eventStream(uri, eventType));
    }

    @Override
    public void close() throws Exception {
        if (sseClient instanceof AutoCloseable) {
            ((AutoCloseable) sseClient).close();
        }
    }
}
