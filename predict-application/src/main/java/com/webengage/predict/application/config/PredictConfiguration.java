package com.webengage.predict.application.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
public class PredictConfiguration extends Configuration {

    @Valid
    @NotNull
    private JerseyClientConfiguration eventReporterJerseyClient = new JerseyClientConfiguration();

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();
}
