package com.catchingrequests;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        System.setProperty("org.eclipse.jetty.LEVEL", "DEBUG");

        int port =  Integer.parseInt(System.getProperty("server.port", "8080"));
        String logFilePath = System.getProperty("server.logsfile", "./logs/request.log");

        RequestServer server2 = new RequestServer(port, logFilePath, new RequestHandler());
        server2.start();
        System.out.println("Server started");

    }
}
