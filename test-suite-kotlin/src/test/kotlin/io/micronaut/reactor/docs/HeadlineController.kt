package io.micronaut.reactor.docs

import io.micronaut.context.annotation.Requires
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.sse.Event
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

/**
 * The server the [HeadlineClient] example talks to.
 */
@Requires(property = "spec.name", value = "HeadlineClientTest")
@Controller("/headlines")
class HeadlineController {

    @Get("/latest")
    fun latest(): String = "Micronaut Reactor released"

    @Get(value = "/events", produces = [MediaType.TEXT_EVENT_STREAM])
    fun events(): Publisher<Event<String>> =
        Flux.just("Micronaut Reactor released", "Reactive HTTP clients", "Server sent events").map { Event.of(it) }

    @Get(value = "/stream", produces = [MediaType.APPLICATION_JSON_STREAM])
    fun stream(): Publisher<Headline> =
        Flux.just(Headline("Micronaut Reactor released"), Headline("Streaming JSON"))
}
