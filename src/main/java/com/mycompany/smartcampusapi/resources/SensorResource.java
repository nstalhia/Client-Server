/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 Name: Janefa Jeba
Student ID: w2080916

*/
package com.mycompany.smartcampusapi.resources;

import com.mycompany.smartcampusapi.model.Sensor;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    public static Map<String, Sensor> sensors = new HashMap<>();

  
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadings() {
        return new SensorReadingResource();
    }

    @POST
    public Response createSensor(Sensor sensor) {

        if (sensor == null) {
            throw new BadRequestException("Sensor cannot be null");
        }

        if (sensor.getType() == null || sensor.getType().isEmpty()) {
            throw new BadRequestException("Sensor type required");
        }

        if (sensor.getCurrentValue() == null) {
            throw new BadRequestException("Current value required");
        }

        if (sensor.getRoomId() == null || sensor.getRoomId().isEmpty()) {
            throw new BadRequestException("Room ID required");
        }

        if (!SensorRoomResource.rooms.containsKey(sensor.getRoomId())) {
            throw new BadRequestException("Room does not exist");
        }

        if (sensor.getStatus() == null || sensor.getStatus().isEmpty()) {
            sensor.setStatus("active");
        }

        String id = UUID.randomUUID().toString();
        sensor.setId(id);

        sensors.put(id, sensor);

        SensorRoomResource.rooms
                .get(sensor.getRoomId())
                .getSensorIds()
                .add(id);

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    @GET
    public Response getSensors(@QueryParam("type") String type) {

        Collection<Sensor> all = sensors.values();

        if (type == null || type.isEmpty()) {
            return Response.ok(all).build();
        }

        List<Sensor> filtered = new ArrayList<>();

        for (Sensor s : all) {
            if (s.getType() != null && s.getType().equalsIgnoreCase(type)) {
                filtered.add(s);
            }
        }

        return Response.ok(filtered).build();
    }

    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") String id) {

        Sensor sensor = sensors.get(id);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found");
        }

        return Response.ok(sensor).build();
    }
}