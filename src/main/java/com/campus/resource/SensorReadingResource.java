package com.campus.resource;

import com.campus.model.Sensor;
import com.campus.model.SensorReading;
import com.campus.repository.DataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    // ADD reading to a sensor
    @POST
    @Path("/{sensorId}")
    public Response addReading(@PathParam("sensorId") String sensorId, SensorReading reading) {

        // Check sensor exists
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sensor does not exist")
                    .build();
        }

        // Add reading to list
        DataStore.readings.putIfAbsent(sensorId, new ArrayList<>());
        DataStore.readings.get(sensorId).add(reading);

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }

    // GET all readings of a sensor
    @GET
    @Path("/{sensorId}")
    public Response getReadings(@PathParam("sensorId") String sensorId) {

        List<SensorReading> list = DataStore.readings.get(sensorId);

        if (list == null) {
            return Response.ok(new ArrayList<>()).build();
        }

        return Response.ok(list).build();
    }
}