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
import java.net.URI
// end::imports[]

// tag::class[]
@Singleton
class HeadlineClient {

    @Inject lateinit var httpClient: ReactorHttpClient // regular client
    @Inject lateinit var sseClient: ReactorSseClient // server sent events
    @Inject lateinit var streamingClient: ReactorStreamingHttpClient // streaming

    fun latest(server: URI): Mono<String> =
        httpClient.retrieve(HttpRequest.GET<Any>(server.resolve("/headlines/latest")))

    fun events(server: URI): Flux<String> =
        sseClient.eventStream(HttpRequest.GET<Any>(server.resolve("/headlines/events")), String::class.java)
            .map { it.data }

    fun stream(server: URI): Flux<Map<String, Any>> =
        streamingClient.jsonStream(HttpRequest.GET<Any>(server.resolve("/headlines/stream")))
}
// end::class[]
