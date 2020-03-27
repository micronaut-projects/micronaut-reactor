package io.micronaut.reactor.http.client


import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import reactor.core.publisher.Mono
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class JsonBodyBindingSpec extends Specification {

    @Inject
    @Client("/")
    ReactorHttpClient reactorHttpClient

    void "test mono argument handling"() {
        when:
        def json = '{"message":"foo"}'
        def response = reactorHttpClient.exchange(
                HttpRequest.POST('/json/mono', json), String
        ).blockFirst()

        then:
        response.body() == "$json".toString()
    }


    @Controller(value = "/json", produces = io.micronaut.http.MediaType.APPLICATION_JSON)
    static class JsonController {


        @Post("/mono")
        Mono<String> mono(@Body Mono<String> message) {
            message
        }

    }

}
