package io.micronaut.reactor.http.client

import io.micronaut.context.ApplicationContext
import io.micronaut.retry.annotation.Retryable
import io.micronaut.retry.event.RetryEvent
import io.micronaut.retry.event.RetryEventListener
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import reactor.core.publisher.Mono
import spock.lang.Specification

import jakarta.inject.Inject
import jakarta.inject.Singleton

@MicronautTest
class SimpleRetrySpec extends Specification {

    @Inject
    CounterService counterService

    @Inject
    MyRetryListener listener

    def setup() {
        listener.reset()
    }

    void "test simply retry with reactor"() {
        given:
        ApplicationContext context = ApplicationContext.run()
        CounterService counterService = context.getBean(CounterService)
        MyRetryListener listener = context.getBean(MyRetryListener)

        when:"A method is annotated retry"
        int result = counterService.getCountMono().block()

        then:"It executes until successful"
        listener.events.size() == 2
        result == 3

        when:"The threshold can never be met"
        listener.reset()
        counterService.countThreshold = 10
        counterService.count = 0
        def single = counterService.getCountMono()
        single.block()

        then:"The original exception is thrown"
        def e = thrown(IllegalStateException)
        e.message == "Bad count"

        cleanup:
        context.stop()
    }


    @Singleton
    static class MyRetryListener implements RetryEventListener {

        List<RetryEvent> events = []

        void reset() {
            events.clear()
        }
        @Override
        void onApplicationEvent(RetryEvent event) {
            events.add(event)
        }
    }
    @Singleton
    static class CounterService {
        int count = 0
        int countReact = 0
        int countThreshold = 3


        @Retryable(attempts = '5', delay = '5ms')
        Mono<Integer> getCountMono() {
            Mono.fromCallable({->
                countReact++
                if(countReact < countThreshold) {
                    throw new IllegalStateException("Bad count")
                }
                return countReact
            })
        }

    }
}

