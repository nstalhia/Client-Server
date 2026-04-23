/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 
Name: Janefa Jeba
Student ID: w2080916

*/
package com.mycompany.smartcampusapi.filters;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger logger =
            Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) {

        logger.info("REQUEST: " +
                requestContext.getMethod() + " " +
                requestContext.getUriInfo().getPath());
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {

        logger.info("RESPONSE STATUS: " + responseContext.getStatus());
    }
}