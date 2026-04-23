/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 
Name: Janefa Jeba
Student ID: w2080916
*/
package com.mycompany.smartcampusapi.resources;

import com.mycompany.smartcampusapi.model.SensorRoom;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorRoomResource {

    public static Map<String, SensorRoom> rooms = new HashMap<>();

    @GET
    public Response getAllRooms() {
        return Response.ok(rooms.values()).build();
    }

    @POST
    public Response createRoom(SensorRoom room) {

        if (room == null || room.getId() == null || room.getId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Room ID required\"}")
                    .build();
        }

        rooms.put(room.getId(), room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {

        SensorRoom room = rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Room not found\"}")
                    .build();
        }

        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        SensorRoom room = rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Room not found\"}")
                    .build();
        }

        rooms.remove(id);

        return Response.ok("{\"message\":\"Room deleted\"}").build();
    }
}