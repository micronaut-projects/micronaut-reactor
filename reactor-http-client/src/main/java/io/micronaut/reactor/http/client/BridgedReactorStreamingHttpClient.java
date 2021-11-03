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

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.StreamingHttpClient;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * Internal bridge for the HTTP client.
 *
 * @author graemerocher
 * @since 1.0
 */
@Internal
class BridgedReactorStreamingHttpClient extends BridgedReactorHttpClient implements ReactorStreamingHttpClient {

    private final StreamingHttpClient streamingHttpClient;

    /**
     * Default constructor.
     * @param streamingHttpClient Streaming HTTP Client
     */
    BridgedReactorStreamingHttpClient(StreamingHttpClient streamingHttpClient) {
        super(streamingHttpClient);
        this.streamingHttpClient = streamingHttpClient;
    }

    @Override
    public <I> Flux<ByteBuffer<?>> dataStream(@NonNull HttpRequest<I> request) {
        return Flux.from(streamingHttpClient.dataStream(request));
    }

    @Override
    public <I> Publisher<ByteBuffer<?>> dataStream(@NonNull HttpRequest<I> request, @NonNull Argument<?> errorType) {
        return Flux.from(streamingHttpClient.dataStream(request, errorType));
    }

    @Override
    public <I> Flux<HttpResponse<ByteBuffer<?>>> exchangeStream(@NonNull HttpRequest<I> request) {
        return Flux.from(streamingHttpClient.exchangeStream(request));
    }

    @Override
    public <I> Publisher<HttpResponse<ByteBuffer<?>>> exchangeStream(@NonNull HttpRequest<I> request, @NonNull Argument<?> errorType) {
        return Flux.from(streamingHttpClient.exchangeStream(request, errorType));
    }

    @Override
    public <I> Flux<Map<String, Object>> jsonStream(@NonNull HttpRequest<I> request) {
        return Flux.from(streamingHttpClient.jsonStream(request));
    }

    @Override
    public <I, O> Flux<O> jsonStream(@NonNull HttpRequest<I> request, @NonNull Argument<O> type) {
        return Flux.from(streamingHttpClient.jsonStream(request, type));
    }

    @Override
    public <I, O> Publisher<O> jsonStream(@NonNull HttpRequest<I> request, @NonNull Argument<O> type, @NonNull Argument<?> errorType) {
        return Flux.from(streamingHttpClient.jsonStream(request, type, errorType));
    }

    @Override
    public <I, O> Flux<O> jsonStream(@NonNull HttpRequest<I> request, @NonNull Class<O> type) {
        return Flux.from(streamingHttpClient.jsonStream(request, type));
    }
}

