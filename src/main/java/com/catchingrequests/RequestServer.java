package com.catchingrequests;

import org.eclipse.jetty.server.AsyncRequestLogWriter;
import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class RequestServer extends Server {

    public RequestServer(int port, String logFilePath, Handler.Abstract hander) {
        HttpConfiguration httpConf = new HttpConfiguration();
        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConf);

        ServerConnector connector = new ServerConnector(this, httpConnectionFactory);
        connector.setPort(port);
        this.addConnector(connector);

        AsyncRequestLogWriter requestLogWriter = new AsyncRequestLogWriter();
        requestLogWriter.setAppend(true);
        requestLogWriter.setFilename(logFilePath);
        requestLogWriter.setRetainDays(1);

        RequestLog requestLog = new CustomRequestLog(requestLogWriter,
                CustomRequestLog.EXTENDED_NCSA_FORMAT);

        this.setRequestLog(requestLog);

        this.setHandler(hander);
    }

}
