package io.micronaut.reactor.http.client

import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class ReactorStreamingHttpClientCloseSpec extends Specification {
    void "confirm ReactorStreamingHttpClient can be stopped"() {
        given:
        ReactorHttpClient client = ReactorStreamingHttpClient.create(new URL("http://localhost"))

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

    void "confirm ReactorStreamingHttpClient can be closed"() {
        given:
        ReactorHttpClient client = ReactorStreamingHttpClient.create(new URL("http://localhost"))

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