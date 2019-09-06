package com.webengage.predict.application.resource;

import com.webengage.predict.api.resource.UrlDataInterface;
import com.webengage.predict.api.validation.ApiRequestValidation;
import com.webengage.predict.application.database.bc.UrlBc;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class UrlDataResource implements UrlDataInterface {

    @Inject private UrlBc urlBc;
    @Inject private ApiRequestValidation apiRequestValidation;

    @Override
    @UnitOfWork
    public Response getCombinedResponse(Integer id) {
        this.apiRequestValidation.doesGetRequestContainsId(id);
        return this.urlBc.getCombinedResponse(id);
    }

    @Override
    @UnitOfWork
    public Response addUrls(String url1, String url2, String url3) {
        this.apiRequestValidation.checkIfAllUrlsArePresentAndValid(url1, url2, url3);
        return urlBc.addUrls(url1, url2, url3);
    }
}
