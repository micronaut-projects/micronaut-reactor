package io.micronaut.reactor.convert;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.convert.TypeConverterRegistrar;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;
import java.util.Optional;

/**
 * Converter registrar for Reactor.
 *
 * @author graemerocher
 * @since 2.0
 */
@Singleton
@Internal
@Requires(sdk = Requires.Sdk.MICRONAUT, version = "2.0.0")
@Requires(classes = Flux.class)
class ReactorConverterRegistrar implements TypeConverterRegistrar {
    @Override
    public void register(ConversionService<?> conversionService) {
        conversionService.addConverter(
                Mono.class,
                Maybe.class,
                (object, targetType, context) -> Optional.of(Flowable.fromPublisher(object).firstElement())
        );
        conversionService.addConverter(
                Publisher.class,
                Flux.class,
                (object, targetType, context) -> Optional.of(Flux.from(object))
        );
        conversionService.addConverter(
                Publisher.class,
                Mono.class,
                (object, targetType, context) -> Optional.of(Mono.from(object))
        );
        conversionService.addConverter(
                Object.class,
                Flux.class,
                (object, targetType, context) -> Optional.of(Flux.just(object))
        );
        conversionService.addConverter(
                Object.class,
                Mono.class,
                (object, targetType, context) -> Optional.of(Mono.just(object))
        );
    }
}
