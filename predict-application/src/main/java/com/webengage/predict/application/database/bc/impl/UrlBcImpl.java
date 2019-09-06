package com.webengage.predict.application.database.bc.impl;

import com.webengage.predict.api.exception.PredictApiException;
import com.webengage.predict.api.model.ResponseStatus;
import com.webengage.predict.api.response.PredictResponseDto;
import com.webengage.predict.application.database.bc.UrlBc;
import com.webengage.predict.application.database.bo.UrlBo;
import com.webengage.predict.application.database.dao.UrlDao;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class UrlBcImpl implements UrlBc {

    @Inject private UrlDao urlDao;
    @Inject private Client client;

    @Override
    public Response addUrls(String url1, String url2, String url3) {
        UrlBo urlBo = new UrlBo();
        synchronized (urlBo) {
            urlBo.setUrl1(url1);
            urlBo.setUrl2(url2);
            urlBo.setUrl3(url3);
        }
        return this.urlDao.insert(urlBo);
    }

    @Override
    public Response getCombinedResponse(Integer id) {
        UrlBo urlBo = this.urlDao.findById(id);
        if (urlBo == null) PredictApiException.NOT_FOUND.throwException("Given id doesn't exists in our System!");

        Future<Response> responseFutureUrl1 = performGetCall(urlBo.getUrl1());
        Future<Response> responseFutureUrl2 = performGetCall(urlBo.getUrl2());
        Future<Response> responseFutureUrl3 = performGetCall(urlBo.getUrl3());

        while(responseFutureUrl1.isDone() && responseFutureUrl2.isDone() && responseFutureUrl3.isDone()) { }

        Map<String, Object> responseData = new HashMap<>();

        try {
            responseData.put("URL1", responseFutureUrl1.get().readEntity(String.class));
        } catch (Exception e) {
            PredictApiException.INTERNAL_SERVER_ERROR.throwException("Issue in obtaining data");
        }

        try {
            responseData.put("URL2", responseFutureUrl2.get().readEntity(String.class));
        } catch (Exception e) {
            PredictApiException.INTERNAL_SERVER_ERROR.throwException("Issue in obtaining data");
        }

        try {
            responseData.put("URL3", responseFutureUrl3.get().readEntity(String.class));
        } catch (Exception e) {
            PredictApiException.INTERNAL_SERVER_ERROR.throwException("Issue in obtaining data");
        }
        return  Response.ok(PredictResponseDto.<Map>build(ResponseStatus.STATUS_SUCCESS, null, responseData)).build();
    }


    private Future<Response> performGetCall(String url) {
        return client.target(url).request().async().get();
    }
}
