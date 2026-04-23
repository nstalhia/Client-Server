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

@Path("/sensors/{sensorId}/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    @PathParam("sensorId")
    private String sensorId;

    private static Map<String, List<Double>> readings = new HashMap<>();

    @POST
    public Response addReading(Map<String, Double> body) {

        if (!SensorResource.sensors.containsKey(sensorId)) {
            throw new NotFoundException("Sensor not found");
        }

        Double value = body.get("value");

        if (value == null) {
            throw new BadRequestException("value required");
        }

        readings.putIfAbsent(sensorId, new ArrayList<>());
        readings.get(sensorId).add(value);

        // update parent sensor (important for coursework marks)
        Sensor sensor = SensorResource.sensors.get(sensorId);
        if (sensor != null) {
            sensor.setCurrentValue(value);
        }

        return Response.status(Response.Status.CREATED)
                .entity("Reading added successfully")
                .build();
    }

    @GET
    public Response getReadings() {

        List<Double> list = readings.get(sensorId);

        if (list == null) {
            return Response.ok(Collections.emptyList()).build();
        }

        return Response.ok(list).build();
    }
}