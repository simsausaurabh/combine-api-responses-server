package com.webengage.predict.client.http;

import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

public class CustomRequestRetryHandler extends DefaultHttpRequestRetryHandler {

    protected static final Collection<Class<? extends IOException>> ignoredExceptions =
            Arrays.asList(UnknownHostException.class, SSLException.class);

    public CustomRequestRetryHandler(final int retryCount) {
        super(retryCount, true, ignoredExceptions);
    }
}
