/*
 * Copyright 2017-2021 original authors
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
package io.micronaut.reactor.http.client.proxy;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.client.ProxyHttpClient;
import reactor.core.publisher.Flux;

@Internal
class BridgedReactorProxyHttpClient implements ReactorProxyHttpClient {

    private final ProxyHttpClient proxyHttpClient;

    BridgedReactorProxyHttpClient(ProxyHttpClient proxyHttpClient) {
        this.proxyHttpClient = proxyHttpClient;
    }

    @Override
    public Flux<MutableHttpResponse<?>> proxy(@NonNull HttpRequest<?> request) {
        return Flux.from(proxyHttpClient.proxy(request));
    }
}
