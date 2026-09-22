package io.micronaut.reactor.docs

// tag::imports[]
import io.micronaut.http.HttpRequest
import io.micronaut.http.sse.Event
import io.micronaut.reactor.http.client.ReactorHttpClient
import io.micronaut.reactor.http.client.ReactorSseClient
import io.micronaut.reactor.http.client.ReactorStreamingHttpClient
import jakarta.inject.Inject
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
// end::imports[]

// tag::class[]
@Singleton
class HeadlineClient {

    @Inject ReactorHttpClient httpClient // regular client
    @Inject ReactorSseClient sseClient // server sent events
    @Inject ReactorStreamingHttpClient streamingClient // streaming

    Mono<String> latest(URI server) {
        httpClient.retrieve(HttpRequest.GET(server.resolve("/headlines/latest")))
    }

    Flux<String> events(URI server) {
        sseClient.eventStream(HttpRequest.GET(server.resolve("/headlines/events")), String)
                .map(Event::getData)
    }

    Flux<Map<String, Object>> stream(URI server) {
        streamingClient.jsonStream(HttpRequest.GET(server.resolve("/headlines/stream")))
    }
}
// end::class[]
