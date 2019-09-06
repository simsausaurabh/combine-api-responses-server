package com.webengage.predict.application.database.bc;

import javax.ws.rs.core.Response;

public interface UrlBc {
    Response addUrls(String url1, String url2, String url3);
    Response getCombinedResponse(Integer id);
}
