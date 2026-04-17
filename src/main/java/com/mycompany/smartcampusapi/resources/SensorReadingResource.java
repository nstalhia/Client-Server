/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.resources;

import com.mycompany.smartcampusapi.model.Sensor;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private String sensorId;

    private static Map<String, List<Map<String, Object>>> readings = new HashMap<>();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getReadings() {

        List<Map<String, Object>> list =
                readings.getOrDefault(sensorId, new ArrayList<>());

        return Response.ok(list).build();
    }

    @POST
    public Response addReading(Map<String, Object> reading) {

        try {
            Sensor sensor = SensorResource.sensors.get(sensorId);

            if (sensor == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Sensor not found\"}")
                        .build();
            }

            if (reading == null || !reading.containsKey("value")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"Missing value\"}")
                        .build();
            }

            double value = Double.parseDouble(reading.get("value").toString());

            readings.putIfAbsent(sensorId, new ArrayList<>());
            readings.get(sensorId).add(reading);

            sensor.setCurrentValue(value);

            return Response.status(Response.Status.CREATED)
                    .entity(reading)
                    .build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Reading processing failed\"}")
                    .build();
        }
    }
}