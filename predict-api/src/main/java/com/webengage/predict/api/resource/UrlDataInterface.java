package com.webengage.predict.api.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;

@Path("/urldata")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UrlDataInterface {

    @Path("/")
    @GET
    @Timed
    Response getCombinedResponse(@QueryParam("id") Integer id);

    @Path("/")
    @POST
    @Timed
    Response addUrls(@QueryParam("url1") String url1,
                    @QueryParam("url2") String url2,
                    @QueryParam("url3") String url3);
}
