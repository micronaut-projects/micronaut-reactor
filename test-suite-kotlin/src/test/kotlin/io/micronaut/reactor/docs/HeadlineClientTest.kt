package io.micronaut.reactor.docs

import io.micronaut.context.annotation.Property
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@Property(name = "spec.name", value = "HeadlineClientTest")
@MicronautTest
class HeadlineClientTest {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    lateinit var headlineClient: HeadlineClient

    @Test
    fun theReactorHttpClientRetrievesTheLatestHeadline() {
        assertEquals("Micronaut Reactor released", headlineClient.latest(server.uri).block())
    }

    @Test
    fun theReactorSseClientStreamsTheEvents() {
        assertEquals(listOf("Micronaut Reactor released", "Reactive HTTP clients", "Server sent events"),
            headlineClient.events(server.uri).collectList().block())
    }

    @Test
    fun theReactorStreamingHttpClientStreamsTheJsonHeadlines() {
        assertEquals(listOf("Micronaut Reactor released", "Streaming JSON"),
            headlineClient.stream(server.uri).map { it["title"] as String }.collectList().block())
    }
}
