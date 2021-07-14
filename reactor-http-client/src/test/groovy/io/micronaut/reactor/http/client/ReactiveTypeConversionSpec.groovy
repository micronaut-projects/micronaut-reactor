package io.micronaut.reactor.http.client

import io.micronaut.core.convert.ConversionService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.Completable
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
        from                                             | target
        Completable.complete()                           | Mono
        Completable.complete()                           | Flux
        io.reactivex.rxjava3.core.Completable.complete() | Mono
        io.reactivex.rxjava3.core.Completable.complete() | Flux
    }
}