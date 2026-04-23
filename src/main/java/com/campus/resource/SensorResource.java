package com.campus.resource;

import com.campus.model.Sensor;
import com.campus.model.Room;
import com.campus.repository.DataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    // GET all sensors
    @GET
    public Collection<Sensor> getAllSensors() {
        return DataStore.sensors.values();
    }

    // POST create sensor
    @POST
    public Response createSensor(Sensor sensor) {

        if (sensor.getId() == null || sensor.getRoomId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing required fields")
                    .build();
        }

        if (DataStore.sensors.containsKey(sensor.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Sensor already exists")
                    .build();
        }

        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room does not exist")
                    .build();
        }

        DataStore.sensors.put(sensor.getId(), sensor);
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    // GET sensor by ID
    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") String id) {
        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        return Response.ok(sensor).build();
    }

    // DELETE sensor
    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        // Remove sensor from room
        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room != null) {
            room.getSensorIds().remove(id);
        }

        DataStore.sensors.remove(id);

        return Response.ok("Sensor deleted").build();
    }
}