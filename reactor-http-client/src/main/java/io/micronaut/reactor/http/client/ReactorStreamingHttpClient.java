package io.micronaut.reactor.http.client;

import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.StreamingHttpClient;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * Reactor variation of the {@link StreamingHttpClient} interface.
 *
 * @author graemerocher
 * @since 1.0.0
 */
public interface ReactorStreamingHttpClient extends StreamingHttpClient {

    @Override
    <I> Flux<ByteBuffer<?>> dataStream(HttpRequest<I> request);

    @Override
    <I> Flux<HttpResponse<ByteBuffer<?>>> exchangeStream(HttpRequest<I> request);

    @Override
    <I> Flux<Map<String, Object>> jsonStream(HttpRequest<I> request);

    @Override
    <I, O> Flux<O> jsonStream(HttpRequest<I> request, Argument<O> type);

    @Override
    <I, O> Flux<O> jsonStream(HttpRequest<I> request, Class<O> type);
}
