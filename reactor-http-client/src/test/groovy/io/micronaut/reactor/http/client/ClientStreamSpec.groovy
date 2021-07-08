package io.micronaut.reactor.http.client


import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import reactor.core.publisher.Flux
import spock.lang.Specification

import jakarta.inject.Inject

@MicronautTest
class ClientStreamSpec extends Specification {

    @Inject BookClient bookClient

    void "test stream array of json objects"() {
        when:
        List<Book> books = bookClient.arrayStream().collectList().block()

        then:
        books.size() == 2
        books[0].title == "The Stand"
        books[1].title == "The Shining"
    }

    void "test stream json stream of objects"() {
        when:
        List<Book> books = bookClient.jsonStream().collectList().block()

        then:
        books.size() == 2
        books[0].title == "The Stand"
        books[1].title == "The Shining"

    }


    @Client('/reactor/stream')
    static interface BookClient extends BookApi {

    }

    @Controller("/reactor/stream")
    static class StreamController implements BookApi {

        @Override
        Flux<Book> arrayStream() {
            return Flux.just(
                    new Book(title: "The Stand"),
                    new Book(title: "The Shining"),
            )
        }

        @Override
        Flux<Book> jsonStream() {
            return Flux.just(
                    new Book(title: "The Stand"),
                    new Book(title: "The Shining"),
            )
        }
    }

    static interface BookApi {
        @Get("/array")
        Flux<Book> arrayStream()

        @Get(value = "/json", processes = MediaType.APPLICATION_JSON_STREAM)
        Flux<Book> jsonStream()
    }

    static class Book {
        String title
    }
}
