package io.micronaut.reactor.http.client;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.RxStreamingHttpClient;
import io.micronaut.http.client.sse.RxSseClient;
import io.micronaut.http.sse.Event;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * Internal bridge for the HTTP client.
 *
 * @author graemerocher
 * @since 1.0
 */
@Internal
class BridgedReactorHttpClient implements ReactorHttpClient, ReactorSseClient, ReactorStreamingHttpClient {

    private final RxHttpClient rxHttpClient;

    /**
     * Default constructor.
     * @param rxHttpClient The target client
     */
    BridgedReactorHttpClient(RxHttpClient rxHttpClient) {
        this.rxHttpClient = rxHttpClient;
    }

    @Override
    public BlockingHttpClient toBlocking() {
        return null;
    }

    @Override
    public <I, O, E> Flux<HttpResponse<O>> exchange(HttpRequest<I> request, Argument<O> bodyType, Argument<E> errorType) {
        return Flux.from(rxHttpClient.exchange(request, bodyType, errorType));
    }

    @Override
    public <I, O> Flux<HttpResponse<O>> exchange(HttpRequest<I> request, Argument<O> bodyType) {
        return Flux.from(rxHttpClient.exchange(request, bodyType));
    }

    @Override
    public <I, O, E> Flux<O> retrieve(HttpRequest<I> request, Argument<O> bodyType, Argument<E> errorType) {
        return Flux.from(rxHttpClient.retrieve(request, bodyType));
    }

    @Override
    public <I> Flux<HttpResponse<ByteBuffer>> exchange(HttpRequest<I> request) {
        return Flux.from(rxHttpClient.exchange(request));
    }

    @Override
    public Flux<HttpResponse<ByteBuffer>> exchange(String uri) {
        return Flux.from(rxHttpClient.exchange(uri));
    }

    @Override
    public <O> Flux<HttpResponse<O>> exchange(String uri, Class<O> bodyType) {
        return Flux.from(rxHttpClient.exchange(uri, bodyType));
    }

    @Override
    public <I, O> Flux<HttpResponse<O>> exchange(HttpRequest<I> request, Class<O> bodyType) {
        return Flux.from(rxHttpClient.exchange(request, bodyType));
    }

    @Override
    public <I, O> Flux<O> retrieve(HttpRequest<I> request, Argument<O> bodyType) {
        return Flux.from(rxHttpClient.retrieve(request, bodyType));
    }

    @Override
    public <I, O> Flux<O> retrieve(HttpRequest<I> request, Class<O> bodyType) {
        return Flux.from(rxHttpClient.retrieve(request, bodyType));
    }

    @Override
    public <I> Flux<String> retrieve(HttpRequest<I> request) {
        return Flux.from(rxHttpClient.retrieve(request));
    }

    @Override
    public Flux<String> retrieve(String uri) {
        return Flux.from(rxHttpClient.retrieve(uri));
    }

    @Override
    public boolean isRunning() {
        return rxHttpClient.isRunning();
    }

    @Override
    public <I> Flux<Event<ByteBuffer<?>>> eventStream(HttpRequest<I> request) {
        return Flux.from(((RxSseClient) rxHttpClient).eventStream(request));
    }

    @Override
    public <I, B> Flux<Event<B>> eventStream(HttpRequest<I> request, Argument<B> eventType) {
        return Flux.from(((RxSseClient) rxHttpClient).eventStream(request, eventType));
    }

    @Override
    public <I, B> Flux<Event<B>> eventStream(HttpRequest<I> request, Class<B> eventType) {
        return Flux.from(((RxSseClient) rxHttpClient).eventStream(request, eventType));
    }

    @Override
    public <B> Flux<Event<B>> eventStream(String uri, Class<B> eventType) {
        return Flux.from(((RxSseClient) rxHttpClient).eventStream(uri, eventType));
    }

    @Override
    public <B> Flux<Event<B>> eventStream(String uri, Argument<B> eventType) {
        return Flux.from(((RxSseClient) rxHttpClient).eventStream(uri, eventType));
    }

    @Override
    public <I> Flux<ByteBuffer<?>> dataStream(HttpRequest<I> request) {
        return Flux.from(((RxStreamingHttpClient) rxHttpClient).dataStream(request));
    }

    @Override
    public <I> Flux<HttpResponse<ByteBuffer<?>>> exchangeStream(HttpRequest<I> request) {
        return Flux.from(((RxStreamingHttpClient) rxHttpClient).exchangeStream(request));
    }

    @Override
    public <I> Flux<Map<String, Object>> jsonStream(HttpRequest<I> request) {
        return Flux.from(((RxStreamingHttpClient) rxHttpClient).jsonStream(request));
    }

    @Override
    public <I, O> Flux<O> jsonStream(HttpRequest<I> request, Argument<O> type) {
        return Flux.from(((RxStreamingHttpClient) rxHttpClient).jsonStream(request, type));
    }

    @Override
    public <I, O> Flux<O> jsonStream(HttpRequest<I> request, Class<O> type) {
        return Flux.from(((RxStreamingHttpClient) rxHttpClient).jsonStream(request, type));
    }
}

