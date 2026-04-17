/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.smartcampusapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {

    @GET
    public Response getApiInfo() {

        Map<String, Object> response = new HashMap<>();

        response.put("name", "Smart Campus API");
        response.put("version", "1.0");

    Map<String, String> resources = new HashMap<>();
      resources.put("rooms", "http://localhost:8080/api/v1/rooms");
      resources.put("sensors", "http://localhost:8080/api/v1/sensors");
        
      response.put("resources", resources);

        return Response.ok(response).build();
    }
}