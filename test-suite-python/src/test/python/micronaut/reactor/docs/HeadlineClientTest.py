from typing import Annotated

from jakarta.inject import Inject
from micronaut.context.annotation import Property
from micronaut.runtime.server import EmbeddedServer
from micronaut.test.extensions.junit5.annotation import MicronautTest
from org.junit.jupiter.api import Test

from .HeadlineClient import HeadlineClient


@Property(name="spec.name", value="HeadlineClientTest")
@MicronautTest
class HeadlineClientTest:

    server: Annotated[EmbeddedServer, Inject]
    headline_client: Annotated[HeadlineClient, Inject]

    @Test
    def the_reactor_http_client_retrieves_the_latest_headline(self) -> None:
        assert self.headline_client.latest(self.server.getURI()).block() == "Micronaut Reactor released"

    @Test
    def the_reactor_sse_client_streams_the_events(self) -> None:
        events = self.headline_client.events(self.server.getURI()).collectList().block()
        assert list(events) == ["Micronaut Reactor released", "Reactive HTTP clients", "Server sent events"]

    @Test
    def the_reactor_streaming_http_client_streams_the_json_headlines(self) -> None:
        headlines = self.headline_client.stream(self.server.getURI()).collectList().block()
        assert [headline.get("title") for headline in headlines] == ["Micronaut Reactor released", "Streaming JSON"]
