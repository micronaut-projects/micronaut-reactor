package io.micronaut.reactor.docs

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class Headline {
    final String title

    Headline(String title) {
        this.title = title
    }
}
