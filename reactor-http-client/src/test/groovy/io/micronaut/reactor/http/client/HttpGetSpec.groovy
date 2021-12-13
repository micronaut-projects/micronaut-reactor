package io.micronaut.reactor.http.client

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import reactor.core.publisher.Mono
import spock.lang.Specification

import jakarta.inject.Inject

@MicronautTest
class HttpGetSpec extends Specification{

    @Inject
    @Client("/")
    ReactorHttpClient client

    void "test mono empty list returns ok"() {
        when:
        HttpResponse response = client.exchange(HttpRequest.GET("/get/emptyList/mono"), Argument.listOf(Book)).block()

        then:
        noExceptionThrown()
        response.status == HttpStatus.OK
        response.body().isEmpty()
    }

    static class Book {
        String title
    }

    @Controller("/get")
    static class GetController {

        @Get("/emptyList/mono")
        Mono<List<Book>> emptyListMono() {
            return Mono.just([])
        }
    }
}
