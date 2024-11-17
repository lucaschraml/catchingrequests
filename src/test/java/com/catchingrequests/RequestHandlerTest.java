package com.catchingrequests;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpFields.ImmutableHttpFields;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;



public class RequestHandlerTest {
    
    @Test
    public void testHandel() throws Exception {
        RequestHandler handler = new RequestHandler();
        assertTrue(true);

        Request request = Mockito.mock(Request.class);
        Response response = Mockito.mock(Response.class);
        Callback callback = Mockito.mock(Callback.class);

        handler.handle(request, response, callback);
    }

    private Request getMockedRequest(String uri, String method) {
        Request request = Mockito.mock(Request.class);
        HttpFields httpFields = new HttpFields() {

            @Override
            public ListIterator<HttpField> listIterator(int arg0) {
                List<HttpField> fields = new ArrayList<>();

                return (ListIterator<HttpField>) fields.iterator();
            }

        };
        when(request.getHeaders()).thenReturn(httpFields);
        when(request.getHttpURI()).thenReturn(HttpURI.build(uri));
        when(request.getMethod()).thenReturn(method);

        return request;
    }
}
