# Python Docs Disabled Test Inventory

This file tracks the Python documentation examples of Micronaut Reactor under `test-suite-python/src/test/python/micronaut/reactor/docs`
that are disabled, or that carry a workaround because the direct port of the Java example does not compile or does not behave
like the Java example yet (Python compiler gaps). It is the bug-fixing task list for the Python compiler
(`micronaut-inject-python` / `micronaut-context-python`); every row references a `TODO(python)` comment in the sources.

The Python examples are compiled by every build and their tests run with `./gradlew pythonCheck -Ppython-ci`
(the "Python CI" GitHub workflow).

## Reconciliation

- Last generated active `@Disabled` count: 0.
- Last generated command: `rg -n "@Disabled\(" test-suite-python/src/test/python`.
- Last full-suite command: `./gradlew :test-suite-python:test -Ppython-ci`.
- Last full-suite result: build successful, 3 tests executed (1 test class), 0 skipped.

## Migration Rules

- The snippet classes live in `io.micronaut.reactor.docs` in every language: a Python source package cannot be the imported
  Java package `micronaut.reactor` itself.
- The Reactor clients are imported from `micronaut.reactor.http.client` and injected as
  `Annotated[ReactorHttpClient, Inject]` class attributes; `Mono` / `Flux` are imported from `reactor.core.publisher` and used
  as the return types of the example's methods, which are only called from Python.
- The `Class<B>` argument of `ReactorSseClient.eventStream(request, String.class)` is the real `java.lang.String` class imported
  with `from java.lang import String` (a Python `str` is not accepted at runtime).
- The `HeadlineController` the example talks to returns `Publisher[...]` (not `Flux`) from its routes, as every bridged
  reactive method does; its JSON stream emits `@Serdeable @dataclass` `Headline` objects.
- A Python test class is a `@MicronautTest` with `@Test` methods and plain `assert` statements; the tests block on the
  `Mono` / `Flux` returned by the example against the injected `EmbeddedServer`.

## Active `@Disabled` Tests

None.

## Workarounds in the Sources

None.

## `java.type` usages

None.
