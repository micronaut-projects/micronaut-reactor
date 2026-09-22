package io.micronaut.reactor.docs;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Headline(String title) {
}
