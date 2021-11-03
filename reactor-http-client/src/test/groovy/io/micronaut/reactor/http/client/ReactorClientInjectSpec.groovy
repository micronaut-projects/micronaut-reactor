package io.micronaut.reactor.http.client

import io.micronaut.reactor.http.client.proxy.ReactorProxyHttpClient
import io.micronaut.reactor.http.client.websocket.ReactorWebSocketClient
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class ReactorClientInjectSpec extends Specification {

    @Inject ReactorSseClient sseClient
    @Inject ReactorStreamingHttpClient streamingHttpClient
    @Inject ReactorHttpClient httpClient
    @Inject ReactorWebSocketClient webSocketClient
    @Inject ReactorProxyHttpClient proxyHttpClient

    void "test clients are injected"() {
        expect:
        sseClient != null
        streamingHttpClient != null
        httpClient != null
        webSocketClient != null
        proxyHttpClient != null
    }
}
