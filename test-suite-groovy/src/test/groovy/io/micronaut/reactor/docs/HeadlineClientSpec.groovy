package io.micronaut.reactor.docs

import io.micronaut.context.annotation.Property
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = "spec.name", value = "HeadlineClientSpec")
@MicronautTest
class HeadlineClientSpec extends Specification {

    @Inject
    EmbeddedServer server

    @Inject
    HeadlineClient headlineClient

    void "the ReactorHttpClient retrieves the latest headline"() {
        expect:
        headlineClient.latest(server.URI).block() == "Micronaut Reactor released"
    }

    void "the ReactorSseClient streams the events"() {
        expect:
        headlineClient.events(server.URI).collectList().block() ==
                ["Micronaut Reactor released", "Reactive HTTP clients", "Server sent events"]
    }

    void "the ReactorStreamingHttpClient streams the JSON headlines"() {
        expect:
        headlineClient.stream(server.URI).map { it.title }.collectList().block() ==
                ["Micronaut Reactor released", "Streaming JSON"]
    }
}
