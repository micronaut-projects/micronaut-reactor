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
