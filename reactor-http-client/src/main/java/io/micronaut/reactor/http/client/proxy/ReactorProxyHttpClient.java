package io.micronaut.reactor.http.client.proxy;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.http.client.ProxyHttpClient;
import reactor.core.publisher.Flux;

import java.net.URL;

/**
 * Extended version of {@link ProxyHttpClient} for Project Reactor.
 *
 * @author James Kleeh
 * @since 2.1.0
 */
public interface ReactorProxyHttpClient extends ProxyHttpClient {

    @Override
    Flux<MutableHttpResponse<?>> proxy(@NonNull HttpRequest<?> request);

    /**
     * Create a new {@link ProxyHttpClient}.
     * Note that this method should only be used outside of the context of a Micronaut application.
     * The returned {@link ProxyHttpClient} is not subject to dependency injection.
     * The creator is responsible for closing the client to avoid leaking connections.
     * Within a Micronaut application use {@link jakarta.inject.Inject} to inject a client instead.
     *
     * @param url The base URL
     * @return The client
     */
    static ProxyHttpClient create(@Nullable URL url) {
        return new BridgedReactorProxyHttpClient(ProxyHttpClient.create(url));
    }

    /**
     * Create a new {@link ProxyHttpClient} with the specified configuration. Note that this method should only be used
     * outside of the context of an application. Within Micronaut use {@link jakarta.inject.Inject} to inject a client instead
     *
     * @param url The base URL
     * @param configuration the client configuration
     * @return The client
     * @since 2.2.0
     */
    static ProxyHttpClient create(@Nullable URL url, @NonNull HttpClientConfiguration configuration) {
        return new BridgedReactorProxyHttpClient(ProxyHttpClient.create(url, configuration));
    }
}
