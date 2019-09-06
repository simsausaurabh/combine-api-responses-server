package com.webengage.predict.application.exception;

import com.webengage.predict.api.model.ResponseStatus;
import com.webengage.predict.api.response.PredictResponseDto;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class PredictExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(PredictResponseDto.<String>build(ResponseStatus.STATUS_ERROR, null, "Unknown error occurred")).build();
    }
}
