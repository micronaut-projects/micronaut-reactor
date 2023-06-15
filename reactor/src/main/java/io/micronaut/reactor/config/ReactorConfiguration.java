/*
 * Copyright 2017-2023 original authors
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
package io.micronaut.reactor.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.Nullable;

/**
 * The Reactor configuration.
 *
 * @author Denis Stepanov
 * @see 3.0.0
 */
@Internal
@ConfigurationProperties("reactor")
public final class ReactorConfiguration {

    @Nullable
    private Boolean enableAutomaticContextPropagation;
    @Nullable
    private Boolean enableScheduleHookContextPropagation;

    public Boolean getEnableAutomaticContextPropagation() {
        return enableAutomaticContextPropagation;
    }

    public void setEnableAutomaticContextPropagation(Boolean enableAutomaticContextPropagation) {
        this.enableAutomaticContextPropagation = enableAutomaticContextPropagation;
    }

    public Boolean getEnableScheduleHookContextPropagation() {
        return enableScheduleHookContextPropagation;
    }

    public void setEnableScheduleHookContextPropagation(Boolean enableScheduleHookContextPropagation) {
        this.enableScheduleHookContextPropagation = enableScheduleHookContextPropagation;
    }
}
