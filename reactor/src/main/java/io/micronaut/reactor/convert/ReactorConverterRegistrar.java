/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.reactor.convert;

import java.util.Optional;

import org.reactivestreams.Publisher;

import io.micronaut.core.annotation.TypeHint;
import io.micronaut.core.convert.MutableConversionService;
import io.micronaut.core.convert.TypeConverterRegistrar;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Converter registrar for Reactor.
 *
 * @author graemerocher
 * @since 2.0
 */
@TypeHint({Flux.class, Mono.class})
public final class ReactorConverterRegistrar implements TypeConverterRegistrar {
    @Override
    public void register(MutableConversionService conversionService) {
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
