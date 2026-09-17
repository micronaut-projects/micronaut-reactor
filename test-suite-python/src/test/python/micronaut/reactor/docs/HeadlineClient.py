# tag::imports[]
from typing import Annotated

from jakarta.inject import Inject, Singleton
from java.lang import String
from java.net import URI
from micronaut.http import HttpRequest
from micronaut.reactor.http.client import ReactorHttpClient, ReactorSseClient, ReactorStreamingHttpClient
from reactor.core.publisher import Flux, Mono
# end::imports[]


# tag::class[]
@Singleton
class HeadlineClient:

    http_client: Annotated[ReactorHttpClient, Inject]  # regular client
    sse_client: Annotated[ReactorSseClient, Inject]  # server sent events
    streaming_client: Annotated[ReactorStreamingHttpClient, Inject]  # streaming

    def latest(self, server: URI) -> Mono[str]:
        return self.http_client.retrieve(HttpRequest.GET(server.resolve("/headlines/latest")))

    def events(self, server: URI) -> Flux[str]:
        return (self.sse_client.eventStream(HttpRequest.GET(server.resolve("/headlines/events")), String)
                .map(lambda event: event.getData()))

    def stream(self, server: URI) -> Flux[dict[str, object]]:
        return self.streaming_client.jsonStream(HttpRequest.GET(server.resolve("/headlines/stream")))
# end::class[]
