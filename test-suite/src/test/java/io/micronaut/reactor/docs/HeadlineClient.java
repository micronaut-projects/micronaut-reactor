package io.micronaut.reactor.docs;

// tag::imports[]
import io.micronaut.http.HttpRequest;
import io.micronaut.http.sse.Event;
import io.micronaut.reactor.http.client.ReactorHttpClient;
import io.micronaut.reactor.http.client.ReactorSseClient;
import io.micronaut.reactor.http.client.ReactorStreamingHttpClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
// end::imports[]

// tag::class[]
@Singleton
public class HeadlineClient {

    @Inject ReactorHttpClient httpClient; // regular client
    @Inject ReactorSseClient sseClient; // server sent events
    @Inject ReactorStreamingHttpClient streamingClient; // streaming

    public Mono<String> latest(URI server) {
        return httpClient.retrieve(HttpRequest.GET(server.resolve("/headlines/latest")));
    }

    public Flux<String> events(URI server) {
        return sseClient.eventStream(HttpRequest.GET(server.resolve("/headlines/events")), String.class)
                .map(Event::getData);
    }

    public Flux<Map<String, Object>> stream(URI server) {
        return streamingClient.jsonStream(HttpRequest.GET(server.resolve("/headlines/stream")));
    }
}
// end::class[]
