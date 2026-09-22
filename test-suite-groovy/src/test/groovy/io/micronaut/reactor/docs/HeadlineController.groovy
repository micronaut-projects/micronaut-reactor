package io.micronaut.reactor.docs

import io.micronaut.context.annotation.Requires
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.sse.Event
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

/**
 * The server the {@link HeadlineClient} example talks to.
 */
@Requires(property = "spec.name", value = "HeadlineClientSpec")
@Controller("/headlines")
class HeadlineController {

    @Get("/latest")
    String latest() {
        "Micronaut Reactor released"
    }

    @Get(value = "/events", produces = MediaType.TEXT_EVENT_STREAM)
    Publisher<Event<String>> events() {
        Flux.just("Micronaut Reactor released", "Reactive HTTP clients", "Server sent events").map(Event::of)
    }

    @Get(value = "/stream", produces = MediaType.APPLICATION_JSON_STREAM)
    Publisher<Headline> stream() {
        Flux.just(new Headline("Micronaut Reactor released"), new Headline("Streaming JSON"))
    }
}
