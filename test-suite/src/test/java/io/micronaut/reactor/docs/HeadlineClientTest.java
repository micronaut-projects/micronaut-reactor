package io.micronaut.reactor.docs;

import io.micronaut.context.annotation.Property;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Property(name = "spec.name", value = "HeadlineClientTest")
@MicronautTest
class HeadlineClientTest {

    @Inject
    EmbeddedServer server;

    @Inject
    HeadlineClient headlineClient;

    @Test
    void theReactorHttpClientRetrievesTheLatestHeadline() {
        assertEquals("Micronaut Reactor released", headlineClient.latest(server.getURI()).block());
    }

    @Test
    void theReactorSseClientStreamsTheEvents() {
        assertEquals(List.of("Micronaut Reactor released", "Reactive HTTP clients", "Server sent events"),
                headlineClient.events(server.getURI()).collectList().block());
    }

    @Test
    void theReactorStreamingHttpClientStreamsTheJsonHeadlines() {
        assertEquals(List.of("Micronaut Reactor released", "Streaming JSON"),
                headlineClient.stream(server.getURI()).map(headline -> headline.get("title")).collectList().block());
    }
}
