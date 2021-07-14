/*
 * Copyright 2017-2021 original authors
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

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.convert.TypeConverterRegistrar;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;
import java.util.Optional;

/**
 * Registers converters from Reactor Types to Rx Java 3 types.
 * For example from Mono to Maybe.
 * @author Sergio del Amo
 * @since 2.0.0
 */
@Singleton
@Internal
@Requires(classes = {Maybe.class, Flowable.class})
public class ReactorToRxJava3ConverterRegistrar implements TypeConverterRegistrar {
    @Override
    public void register(ConversionService<?> conversionService) {
        conversionService.addConverter(Mono.class,
                Maybe.class,
                (object, targetType, context) -> Optional.of(Flowable.fromPublisher(object).firstElement())
        );
    }
}
