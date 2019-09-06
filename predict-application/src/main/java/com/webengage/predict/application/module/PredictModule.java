package com.webengage.predict.application.module;

import com.webengage.predict.api.resource.UrlDataInterface;
import com.webengage.predict.api.response.PredictResponseDto;
import com.webengage.predict.api.validation.ApiRequestValidation;
import com.webengage.predict.application.config.PredictConfiguration;
import com.webengage.predict.application.database.bc.UrlBc;
import com.webengage.predict.application.database.bc.impl.UrlBcImpl;
import com.webengage.predict.application.database.dao.UrlDao;
import com.webengage.predict.application.database.dao.impl.UrlDaoImpl;
import com.webengage.predict.application.factory.PredictResponseClientFactory;
import com.webengage.predict.application.resource.UrlDataResource;

import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.hibernate.SessionFactory;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;

public class PredictModule extends AbstractBinder {

    private Environment environment;
    private PredictConfiguration predictConfiguration;
    private HibernateBundle hibernate;

    public PredictModule(Environment environment, PredictConfiguration predictConfiguration, HibernateBundle hibernate) {
        this.environment = environment;
        this.predictConfiguration = predictConfiguration;
        this.hibernate = hibernate;
    }

    @Override
    protected void configure() {
        bind(this.environment).to(Environment.class);
        bind(this.predictConfiguration).to(PredictConfiguration.class);
        bind(UrlDataResource.class).to(UrlDataInterface.class).in(Singleton.class);
        bind(UrlBcImpl.class).to(UrlBc.class).in(Singleton.class);
        bind(UrlDaoImpl.class).to(UrlDao.class).in(Singleton.class);
        bindFactory(PredictResponseClientFactory.class).to(Client.class).in(Singleton.class);
        bind(this.hibernate.getSessionFactory()).to(SessionFactory.class);
        bind(ApiRequestValidation.class).to(ApiRequestValidation.class).in(Singleton.class);
    }
}
