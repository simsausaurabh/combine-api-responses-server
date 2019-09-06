package com.webengage.predict.api.exception;

import com.webengage.predict.api.model.ResponseStatus;
import com.webengage.predict.api.response.PredictResponseDto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public enum PredictApiException {

    NOT_FOUND(Response.Status.NOT_FOUND.getStatusCode()),
    INTERNAL_SERVER_ERROR(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()),
    BAD_GATEWAY(Response.Status.BAD_GATEWAY.getStatusCode()),
    BAD_REQUEST(Response.Status.BAD_REQUEST.getStatusCode());

    private Integer responseCode;

    PredictApiException(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public void throwException(String message) throws WebApplicationException {
        throw new WebApplicationException(Response.status(this.responseCode).entity(PredictResponseDto.<String>build(ResponseStatus.STATUS_ERROR, message, null)).build());
    }
}
