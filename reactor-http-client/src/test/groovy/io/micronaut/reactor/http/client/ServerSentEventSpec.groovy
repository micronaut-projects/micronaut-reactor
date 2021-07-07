package io.micronaut.reactor.http.client

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Requires
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.sse.Event
import io.reactivex.Flowable
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import spock.lang.Specification

import javax.inject.Inject
import java.time.Duration
import java.time.temporal.ChronoUnit

/**
 * @author Graeme Rocher
 * @since 1.0
 */
@MicronautTest
@Property(name = 'spec.name', value = 'ServerSentEventSpec')
class ServerSentEventSpec extends Specification {

    @Inject
    @Client("/")
    ReactorSseClient sseClient

    @Inject
    SseClient client

    void "test consume event stream object"() {
        given:
        List<Event<Foo>> events = client.object().collectList().block()

        expect:
        events.size() == 4
        events.first().data == new Foo(name: "Foo 1",age:11)
    }

    void "test consume event stream string"() {
        given:
        List<Event<String>> events = client.string().collectList().block()

        expect:
        events.size() == 4
        events.first().data == 'Foo 1'
    }

    void "test consume rich event stream object"() {
        given:
        List<Event<Foo>> events = client.rich().collectList().block()

        expect:
        events.size() == 4
        events.first().data == new Foo(name: "Foo 1",age:11)
        events.first().id == "1"
        events.first().retry == Duration.ofMinutes(2)
    }

    void "test receive error from supplier"() {

        when:
        List<Event<String>> events = client.exception().collectList().block()

        then:
        events.size() == 0
    }

    @Client('/sse')
    static interface SseClient {

        @Get(value = '/object', processes = MediaType.TEXT_EVENT_STREAM)
        Flux<Event<Foo>> object()

        @Get(value = '/string', processes = MediaType.TEXT_EVENT_STREAM)
        Flux<Event<String>> string()

        @Get(value = '/rich', processes = MediaType.TEXT_EVENT_STREAM)
        Flux<Event<Foo>> rich()

        @Get(value = '/exception', processes = MediaType.TEXT_EVENT_STREAM)
        Flux<Event<String>> exception()

        @Get(value = '/on-error', processes = MediaType.TEXT_EVENT_STREAM)
        Flux<Event<String>> onError()
    }

    @Controller('/sse')
    @Requires(property = 'spec.name', value = 'ServerSentEventSpec')
    static class SseController {

        @Get('/object')
        @Produces(MediaType.TEXT_EVENT_STREAM)
        Publisher<Event> object() {
            int i = 0
            Flux.generate( { SynchronousSink<Event> emitter ->
                if (i < 4) {
                    i++
                    emitter.next(Event.of(new Foo(name: "Foo $i", age: i + 10)))
                }
                else {
                    emitter.complete()
                }
            })
        }

        @Get('/rich')
        @Produces(MediaType.TEXT_EVENT_STREAM)
        Publisher<Event> rich() {
            Integer i = 0
            Flux.generate( { SynchronousSink<Event> emitter ->
                if (i < 4) {
                    i++
                    emitter.next(
                            Event.of(new Foo(name: "Foo $i", age: i + 10))
                                    .name('foo')
                                    .id(i.toString())
                                    .comment("Foo Comment $i")
                                    .retry(Duration.of(2, ChronoUnit.MINUTES)))
                }
                else {
                    emitter.complete()
                }
            })
        }

        @Get('/string')
        @Produces(MediaType.TEXT_EVENT_STREAM)
        Publisher<Event> string() {
            int i = 0
            Flux.generate( { SynchronousSink<Event> emitter ->
                if (i < 4) {
                    i++
                    emitter.next(Event.of("Foo $i"))
                }
                else {
                    emitter.complete()
                }
            })
        }

        @Get('/exception')
        @Produces(MediaType.TEXT_EVENT_STREAM)
        Publisher<Event> exception() {
            Flux.generate( { SynchronousSink<Event> emitter ->
                throw new RuntimeException("bad things happened")
            })
        }

        @Get('on-error')
        @Produces(MediaType.TEXT_EVENT_STREAM)
        Publisher<Event> onError() {
            Flux.generate( { SynchronousSink<Event> emitter ->
                emitter.error(new RuntimeException("bad things happened"))
            })
        }
    }

    @EqualsAndHashCode
    @ToString(includePackage = false)
    static class Foo {
        String name
        Integer age
    }
}