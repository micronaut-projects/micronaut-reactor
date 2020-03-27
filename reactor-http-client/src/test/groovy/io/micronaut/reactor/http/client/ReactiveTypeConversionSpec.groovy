package io.micronaut.reactor.http.client

import io.micronaut.core.convert.ConversionService
import io.micronaut.test.annotation.MicronautTest
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

@MicronautTest
class ReactiveTypeConversionSpec extends Specification {

    @Unroll
    void 'test converting reactive type #from.getClass().getSimpleName() to #target'() {
        expect:
        ConversionService.SHARED.convert(from, target).isPresent()

        where:
        from                   | target
        Completable.complete() | io.reactivex.Observable
        Completable.complete() | Flowable
        Completable.complete() | Mono
        Completable.complete() | Flux
        Completable.complete() | io.reactivex.Single
        Completable.complete() | Maybe
    }
}
