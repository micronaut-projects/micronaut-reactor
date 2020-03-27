package io.micronaut.reactor.http.client

import io.micronaut.context.annotation.Property
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.context.ServerRequestContext
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.test.annotation.MicronautTest
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject
import javax.inject.Named
import java.util.concurrent.ExecutorService

@MicronautTest
@Property(name = 'micronaut.executors.io.type', value =  'FIXED')
@Property(name = 'micronaut.executors.io.nThreads', value =  '2')
class ServerRequestContextSpec extends Specification {

    @Inject TestClient testClient

    @Unroll
    void "test server request context is available for #method"() {
        expect:
        testClient."$method"() == uri

        where:
        method          | uri
        "rxjava"        | '/test-context/rxjava'
        "reactor"       | '/test-context/reactor'
    }

    @Client('/test-context')
    @Consumes(MediaType.TEXT_PLAIN)
    static interface TestClient {

        @Get("/method")
        String method()

        @Get("/rxjava")
        String rxjava()

        @Get("/reactor")
        String reactor()

        @Get("/thread")
        String thread()

        @Get("/error")
        String error()

        @Get("/handler-error")
        String handlerError()
    }

    @Controller('/test-context')
    @Produces(MediaType.TEXT_PLAIN)
    static class TestContextController {

        @Inject
        @Named(TaskExecutors.IO)
        ExecutorService executorService

        @Get("/rxjava")
        Single<String> rxjava() {
            Single.fromCallable({ ->
                def request = ServerRequestContext.currentRequest().orElseThrow { -> new RuntimeException("no request") }
                request.uri
            }).subscribeOn(Schedulers.computation())
        }

        @Get("/reactor")
        Mono<String> reactor() {
            Mono.fromCallable({ ->
                def request = ServerRequestContext.currentRequest().orElseThrow { -> new RuntimeException("no request") }
                request.uri
            }).subscribeOn(reactor.core.scheduler.Schedulers.elastic())
        }

    }
}
