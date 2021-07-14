package io.micronaut.reactor.http.client

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.context.ServerRequestContext
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import spock.lang.Specification
import jakarta.inject.Inject
import jakarta.inject.Named

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService

@MicronautTest
@Property(name = 'micronaut.executors.io.type', value =  'FIXED')
@Property(name = 'micronaut.executors.io.nThreads', value =  '2')
class ServerRequestContextSpec extends Specification {

    @Inject TestClient testClient

    void "test server request context is available for #method"() {
        expect:
        testClient.reactor() == '/test-context/reactor'
    }

    @Client('/test-context')
    @Consumes(MediaType.TEXT_PLAIN)
    static interface TestClient {

        @Get("/reactor")
        String reactor()
    }

    @Controller('/test-context')
    @Produces(MediaType.TEXT_PLAIN)
    static class TestContextController {

        @Inject
        @Named(TaskExecutors.IO)
        ExecutorService executorService

        @Get("/reactor")
        Mono<String> reactor() {
            Mono.fromCallable(new Callable<String>() {
                @Override
                String call() throws Exception {
                    HttpRequest<?> request = ServerRequestContext.currentRequest().orElseThrow { -> new RuntimeException("no request") }
                    request.uri
                }
            }).subscribeOn(Schedulers.boundedElastic())
        }
    }
}
