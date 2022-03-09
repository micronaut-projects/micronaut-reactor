package io.micronaut.reactor.http.client

import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class RxClientCloseSpec extends Specification {
    def "confirm ReactorHttpClient can be stopped"() {
        given:
        def client = ReactorHttpClient.create(new URL("http://localhost"))

        expect:
        client.isRunning()

        when:
        client.stop()
        then:
        new PollingConditions().eventually {
            !client.isRunning()
        }
    }

    def "confirm ReactorHttpClient can be closed"() {
        given:
        def client = ReactorHttpClient.create(new URL("http://localhost"))

        expect:
        client.isRunning()

        when:
        client.close()
        then:
        new PollingConditions().eventually {
            !client.isRunning()
        }
    }
}
