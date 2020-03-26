package io.micronaut.reactor.http.client;

import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.sse.SseClient;
import io.micronaut.http.sse.Event;
import reactor.core.publisher.Flux;

/**
 * Reactor variation of the {@link SseClient} interface.
 *
 * @author graemerocher
 * @since 1.0.0
 */
public interface ReactorSseClient extends SseClient {
    @Override
    <I> Flux<Event<ByteBuffer<?>>> eventStream(HttpRequest<I> request);

    @Override
    <I, B> Flux<Event<B>> eventStream(HttpRequest<I> request, Argument<B> eventType);

    @Override
    <I, B> Flux<Event<B>> eventStream(HttpRequest<I> request, Class<B> eventType);

    @Override
    <B> Flux<Event<B>> eventStream(String uri, Class<B> eventType);

    @Override
    <B> Flux<Event<B>> eventStream(String uri, Argument<B> eventType);
}
