package io.micronaut.reactor.http.client

import brave.SpanCustomizer
import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.tracing.annotation.ContinueSpan
import io.micronaut.tracing.annotation.NewSpan
import io.micronaut.tracing.annotation.SpanTag
import io.reactivex.Single
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
@Property(name = 'tracing.zipkin.enabled', value = StringUtils.TRUE)
@Property(name = 'tracing.zipkin.sampler.probability', value = "1")
@Property(name = 'tracing.instrument-threads', value = StringUtils.TRUE)
class TraceInterceptorSpec extends Specification {

    @Inject TracedService tracedService
    @Inject TestReporter reporter



    void "test trace mono"() {
        when:
        String result = tracedService.mono("test").block()
        PollingConditions conditions = new PollingConditions(timeout: 3)

        then:
        conditions.eventually {
            result == "test"
            reporter.spans[0].tags().get("more.stuff") == 'test'
            reporter.spans[0].tags().get("class") == 'TracedService'
            reporter.spans[0].tags().get("method") == 'mono'
            reporter.spans[0].tags().get("foo") == "bar"
            reporter.spans[0].name() == 'trace-mono'
        }
    }

    @Singleton
    static class TracedService {

        @Inject SpanCustomizer spanCustomizer
        @NewSpan("my-trace")
        String methodOne(@SpanTag("foo.bar") String name) {
            methodTwo(name)
        }

        @ContinueSpan
        String methodTwo(@SpanTag("foo.baz") String another) {
            methodThree(another).blockingGet()
        }

        @NewSpan("trace-rx")
        Single<String> methodThree(@SpanTag("more.stuff") String name) {
            return Single.just(name)
        }

        @NewSpan("trace-mono")
        Mono<String> mono(@SpanTag("more.stuff") String name) {
            return Mono.fromCallable({
                spanCustomizer.tag("foo", "bar")
                return name
            }).subscribeOn(Schedulers.elastic())
        }


    }
}

import zipkin2.Span
import zipkin2.reporter.Reporter

/**
 * @author graemerocher
 * @since 1.0
 */
@Singleton
class TestReporter implements Reporter<Span> {
    List<Span> spans = []
    @Override
    void report(Span span) {
        spans.add(span)
    }
}
