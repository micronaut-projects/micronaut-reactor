package io.micronaut.reactor.docs

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Headline(val title: String)
