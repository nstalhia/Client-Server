/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 
Name: Janefa Jeba
Student ID: w2080916

*/
package com.mycompany.smartcampusapi.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {

        ex.printStackTrace();

        return Response.status(500)
                .entity("{\"error\":\"" + ex.getClass().getSimpleName() + ": " + ex.getMessage() + "\"}")
                .type(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .build();
    }
}