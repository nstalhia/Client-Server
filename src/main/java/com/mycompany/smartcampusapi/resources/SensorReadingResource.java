/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampusapi.resources;

import com.mycompany.smartcampusapi.model.Sensor;
import com.mycompany.smartcampusapi.model.SensorReading;
import com.mycompany.smartcampusapi.exceptions.LinkedResourceNotFoundException;
import com.mycompany.smartcampusapi.exceptions.SensorUnavailableException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private String sensorId;

    private static Map<String, List<SensorReading>> readings = new HashMap<>();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

   
    @GET
    public Response getReadings() {
        return Response.ok(
                readings.getOrDefault(sensorId, new ArrayList<>())
        ).build();
    }

    @POST
    public Response addReading(SensorReading reading) {

        Sensor sensor = SensorResource.sensors.get(sensorId);

      
        if (sensor == null) {
            throw new LinkedResourceNotFoundException("Sensor not found");
        }

        
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is under maintenance");
        }

        if (reading == null) {
            throw new BadRequestException("Reading cannot be null");
        }

      
        reading.setId(UUID.randomUUID().toString());
        reading.setTimestamp(System.currentTimeMillis());

    
        readings.putIfAbsent(sensorId, new ArrayList<>());
        readings.get(sensorId).add(reading);

       
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}