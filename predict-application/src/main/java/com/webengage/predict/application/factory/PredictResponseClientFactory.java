package com.webengage.predict.application.factory;

import com.codahale.metrics.httpclient.HttpClientMetricNameStrategies;
import com.webengage.predict.application.config.PredictConfiguration;
import com.webengage.predict.client.http.CustomRequestRetryHandler;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.api.Factory;
import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import java.util.concurrent.*;

public class PredictResponseClientFactory implements Factory<Client> {

    @Inject
    private PredictConfiguration config;

    @Inject
    private Environment env;

    @Override
    public Client provide() {
        ExecutorService executorService = env.lifecycle()
                .executorService("jersey-client-%d")
                .minThreads(config.getEventReporterJerseyClient().getMinThreads())
                .maxThreads(config.getEventReporterJerseyClient().getMaxThreads())
                .workQueue(new ArrayBlockingQueue<>(config.getEventReporterJerseyClient().getWorkQueueSize()))
                .rejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();

        final Client client =
                new JerseyClientBuilder(env)
                        .using(env)
                        .using(executorService)
                        .using(new CustomRequestRetryHandler(config.getEventReporterJerseyClient().getRetries()))
                        .using(config.getEventReporterJerseyClient())
                        .using(HttpClientMetricNameStrategies.HOST_AND_METHOD)
                        .build("event_enhc_event_reporter_jersey_client");

        return client;
    }

    @Override
    public void dispose(Client client) { }
}
