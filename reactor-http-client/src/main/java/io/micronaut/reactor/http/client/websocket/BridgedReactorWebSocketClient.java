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
package io.micronaut.reactor.http.client.websocket;

import io.micronaut.core.annotation.Internal;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.websocket.WebSocketClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.Map;

/**
 * Project Reactor bridge for the {@link WebSocketClient}.
 *
 * @author James Kleeh
 * @since 2.1.0
 */
@Internal
class BridgedReactorWebSocketClient implements ReactorWebSocketClient {

    private final WebSocketClient webSocketClient;

    /**
     *
     * @param webSocketClient Websocket client
     */
    BridgedReactorWebSocketClient(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    @Override
    public <T extends AutoCloseable> Flux<T> connect(Class<T> clientEndpointType, MutableHttpRequest<?> request) {
        return Flux.from(webSocketClient.connect(clientEndpointType, request));
    }

    @Override
    public <T extends AutoCloseable> Flux<T> connect(Class<T> clientEndpointType, URI uri) {
        return Flux.from(webSocketClient.connect(clientEndpointType, uri));
    }

    @Override
    public <T extends AutoCloseable> Flux<T> connect(Class<T> clientEndpointType, Map<String, Object> parameters) {
        return Flux.from(webSocketClient.connect(clientEndpointType, parameters));
    }

    @Override
    public <T extends AutoCloseable> Flux<T> connect(Class<T> clientEndpointType, String uri) {
        return Flux.from(webSocketClient.connect(clientEndpointType, uri));
    }

    @Override
    public void close() {
        webSocketClient.close();
    }
}
