package com.campus.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class TestResource {

    @GET
    public String test() {
        return "API is working";
    }
}

