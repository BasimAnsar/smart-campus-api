package com.campus;

import com.campus.config.ApplicationConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        // Register ApplicationConfig explicitly
        final ResourceConfig config = ResourceConfig.forApplication(new ApplicationConfig())
                .packages("com.campus");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    public static void main(String[] args) {
        final HttpServer server = startServer();
        System.out.println("Server running at " + BASE_URI + "api/v1");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            server.shutdownNow();
        }
    }
}