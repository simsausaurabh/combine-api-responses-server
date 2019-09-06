package com.webengage.predict.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.webengage.predict.application.database.bo.UrlBo;
import com.webengage.predict.application.config.PredictConfiguration;
import com.webengage.predict.application.exception.PredictExceptionMapper;
import com.webengage.predict.application.module.PredictModule;
import com.webengage.predict.application.resource.UrlDataResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PredictApplication extends Application<PredictConfiguration> {
    /**
     * Hibernate bundle.
     */
    private final HibernateBundle<PredictConfiguration> hibernate = new
            ScanningHibernateBundle<PredictConfiguration>(UrlBo.class.getPackage().getName()) {
                @Override
                public DataSourceFactory getDataSourceFactory(PredictConfiguration predictConfiguration) {
                    return predictConfiguration.getDatabase();
                }
    };

    @Override
    public void initialize(final Bootstrap<PredictConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(new MigrationsBundle<PredictConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(PredictConfiguration predictConfiguration) {
                return predictConfiguration.getDatabase();
            }
        });

        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
    }

    public static void main(String[] args) throws Exception {
        new PredictApplication().run(args);
    }

    @Override
    public void run(PredictConfiguration predictConfiguration, Environment environment) throws Exception {
        ObjectMapper mapper = environment.getObjectMapper();
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(mapper);
        environment.jersey().register(jacksonProvider);
        environment.jersey().register(UrlDataResource.class);
        environment.jersey().register(new PredictModule(environment, predictConfiguration, hibernate));
        environment.jersey().register(PredictExceptionMapper.class);
    }
}
