package io.micronaut.reactor.http.client

import io.micronaut.core.annotation.Creator
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import reactor.core.publisher.Flux
import spock.lang.Specification
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Inject
import javax.validation.constraints.NotBlank

@MicronautTest
class FluxEventSpec extends Specification {

    @Inject
    @Client("/")
    ReactorStreamingHttpClient client

    void "test reactor flux events"() {
        when:
        Hello hello = client.jsonStream(HttpRequest.GET('/flux/hello/fred'), Hello).blockFirst()

        then:
        hello.name == "test1"
        hello.number == 1
    }

    @Controller("/")
    static class HelloController {

        @Produces(MediaType.APPLICATION_JSON_STREAM) // add 'application/stream+json'
        @Get("/flux/hello/{name}")
        Flux<Hello> hello(@NotBlank String name) {

            List<Hello> list = new ArrayList<>()

            list.add(new Hello("test1", 1))
            list.add(new Hello("test2", 2))

            return Flux.fromIterable(list).doOnComplete( {->
                System.out.println("response should be closed here!")
            })
        }
    }

    @Serdeable
    static class Hello {
        String name
        int number

        @Creator
        Hello(String name, int number) {
            this.name = name
            this.number = number
        }

        Hello() {
        }
    }
}
