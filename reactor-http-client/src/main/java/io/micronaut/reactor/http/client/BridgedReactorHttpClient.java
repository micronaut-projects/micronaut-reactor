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
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.StreamingHttpClient;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * Internal bridge for the HTTP client.
 *
 * @author graemerocher
 * @since 1.0
 */
@Internal
class BridgedReactorHttpClient implements ReactorHttpClient {

    private final HttpClient httpClient;

    /**
     * Default constructor.
     * @param streamingHttpClient Streaming HTTP Client
     */
    BridgedReactorHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public BlockingHttpClient toBlocking() {
        return httpClient.toBlocking();
    }

    @Override
    public <I, O, E> Flux<HttpResponse<O>> exchange(@NonNull HttpRequest<I> request, @NonNull Argument<O> bodyType, @NonNull Argument<E> errorType) {
        return Flux.from(httpClient.exchange(request, bodyType, errorType));
    }

    @Override
    public <I, O> Flux<HttpResponse<O>> exchange(@NonNull HttpRequest<I> request, @NonNull Argument<O> bodyType) {
        return Flux.from(httpClient.exchange(request, bodyType));
    }

    @Override
    public <I, O, E> Flux<O> retrieve(@NonNull HttpRequest<I> request, @NonNull Argument<O> bodyType, @NonNull Argument<E> errorType) {
        return Flux.from(httpClient.retrieve(request, bodyType));
    }

    @Override
    public <I> Flux<HttpResponse<ByteBuffer>> exchange(@NonNull HttpRequest<I> request) {
        return Flux.from(httpClient.exchange(request));
    }

    @Override
    public Flux<HttpResponse<ByteBuffer>> exchange(@NonNull String uri) {
        return Flux.from(httpClient.exchange(uri));
    }

    @Override
    public <O> Flux<HttpResponse<O>> exchange(@NonNull String uri, @NonNull Class<O> bodyType) {
        return Flux.from(httpClient.exchange(uri, bodyType));
    }

    @Override
    public <I, O> Flux<HttpResponse<O>> exchange(@NonNull HttpRequest<I> request, @NonNull Class<O> bodyType) {
        return Flux.from(httpClient.exchange(request, bodyType));
    }

    @Override
    public <I, O> Flux<O> retrieve(@NonNull HttpRequest<I> request, @NonNull Argument<O> bodyType) {
        return Flux.from(httpClient.retrieve(request, bodyType));
    }

    @Override
    public <I, O> Flux<O> retrieve(@NonNull HttpRequest<I> request, @NonNull Class<O> bodyType) {
        return Flux.from(httpClient.retrieve(request, bodyType));
    }

    @Override
    public <I> Flux<String> retrieve(@NonNull HttpRequest<I> request) {
        return Flux.from(httpClient.retrieve(request));
    }

    @Override
    public Flux<String> retrieve(@NonNull String uri) {
        return Flux.from(httpClient.retrieve(uri));
    }

    @Override
    public boolean isRunning() {
        return httpClient.isRunning();
    }

}

