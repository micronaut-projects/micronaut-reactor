from micronaut.context.annotation import Requires
from micronaut.http import MediaType
from micronaut.http.annotation import Controller, Get
from micronaut.http.sse import Event
from org.reactivestreams import Publisher
from reactor.core.publisher import Flux

from .Headline import Headline


# The server the HeadlineClient example talks to.
@Requires(property="spec.name", value="HeadlineClientTest")
@Controller("/headlines")
class HeadlineController:

    @Get("/latest")
    def latest(self) -> str:
        return "Micronaut Reactor released"

    @Get(value="/events", produces=MediaType.TEXT_EVENT_STREAM)
    def events(self) -> Publisher[Event[str]]:
        return Flux.just("Micronaut Reactor released", "Reactive HTTP clients", "Server sent events").map(lambda data: Event.of(data))

    @Get(value="/stream", produces=MediaType.APPLICATION_JSON_STREAM)
    def stream(self) -> Publisher[Headline]:
        return Flux.just(Headline("Micronaut Reactor released"), Headline("Streaming JSON"))
