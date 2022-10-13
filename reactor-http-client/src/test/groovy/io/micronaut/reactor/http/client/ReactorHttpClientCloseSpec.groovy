package io.micronaut.reactor.http.client

import spock.lang.PendingFeature
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class ReactorHttpClientCloseSpec extends Specification {
    @PendingFeature
    void "confirm ReactorHttpClient can be stopped"() {
        given:
        ReactorHttpClient client = ReactorHttpClient.create(new URL("http://localhost"))

        expect:
        client.isRunning()

        when:
        client.stop()

        then:
        new PollingConditions().eventually {
            !client.isRunning()
        }

        when:
        client.start()

        then:
        new PollingConditions().eventually {
            client.isRunning()
        }

        cleanup:
        client.close()
    }

    void "confirm ReactorHttpClient can be closed"() {
        given:
        ReactorHttpClient client = ReactorHttpClient.create(new URL("http://localhost"))

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
