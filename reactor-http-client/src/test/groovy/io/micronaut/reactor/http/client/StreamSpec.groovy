package io.micronaut.reactor.http.client

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import io.reactivex.Flowable
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import reactor.core.publisher.Flux
import spock.lang.Specification
import io.micronaut.core.annotation.Nullable;
import javax.inject.Inject
import java.nio.charset.StandardCharsets

@MicronautTest
class StreamSpec extends Specification {

    @Inject
    StreamEchoClient myClient

    void "test receive client using byte[]"() {
        given:
        int n = 90659
        when:
        Flux<byte[]> responseFlowable = myClient.echoAsByteArrays(n, "Hello, World!")
        // reduce to the total count of !'s
        long sum = responseFlowable.reduce(0L, { long acc, byte[] bytes -> acc + bytes.count('!') }).block()
        then:
        sum == n
    }


    @Client('/stream')
    static interface StreamEchoClient {
        @Get(value = "/echo{?n,data}", consumes = MediaType.TEXT_PLAIN)
        Flux<byte[]> echoAsByteArrays(@QueryValue @Nullable int n, @QueryValue @Nullable String data);
    }

    @Controller('/stream')
    static class StreamEchoController {

        @Get(value = "/echo{?n,data}", produces = MediaType.TEXT_PLAIN)
        Flowable<byte[]> stream(@QueryValue @Nullable int n, @QueryValue @Nullable String data) {
            return Flowable.just(data.getBytes(StandardCharsets.UTF_8)).repeat(n)
        }

    }
}
