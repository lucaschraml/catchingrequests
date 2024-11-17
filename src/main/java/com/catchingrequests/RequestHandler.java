package com.catchingrequests;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

public class RequestHandler extends Handler.Abstract {

    private static final String NEW_LINE = "\n";

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        sendMessage(requestToMessage(request), response, callback);
        return true;
    }

    private void sendMessage(String message, Response response, Callback callback) {
        Content.Sink.write(response, true, message, callback);
    }

    private String requestToMessage(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getMethod());
        sb.append(NEW_LINE);
        sb.append(request.getHttpURI());
        sb.append(NEW_LINE);
       
        printRequestHeaders(request, sb);

        printRequestBody(request, sb);

        return sb.toString();
    }

    private void printRequestHeaders(Request request, StringBuilder sb) {
        if(!Boolean.parseBoolean(System.getProperty("debug.header", "true"))) {
            return;
        }
        sb.append("Request-Headers:");
        sb.append(NEW_LINE);
        HttpFields fields = request.getHeaders();
        Iterator<HttpField> it = fields.iterator();
        while (it.hasNext()) {
            HttpField field = it.next();
            if(field.getName().equals("Content-Type") && Boolean.parseBoolean(System.getProperty("debug.body", "true")))  {
                continue;
            }

            sb.append(field.getName());
            sb.append(": ");
            sb.append(field.getValue());
            sb.append(NEW_LINE);
        }
    }

    private void printRequestBody(Request request, StringBuilder sb) {
        if(!Boolean.parseBoolean(System.getProperty("debug.body", "true"))) {
            return;
        }
        
        sb.append("Content-Type: " + request.getHeaders().get("Content-Type"));
        CompletableFuture<String> completable = Content.Source.asStringAsync(request, StandardCharsets.UTF_8);
        completable.whenComplete((requestContent, failure) -> {
            if (failure == null) {
                String test = new String(requestContent.getBytes());
                sb.append(test);
            }

        });
        try {
            completable.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
