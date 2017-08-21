package org.rest.app.security;
import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;


@Provider
public class LoginFilter implements ContainerRequestFilter, ContainerResponseFilter {


    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        System.out.println("Response filter");
        System.out.println("Headers: " + responseContext.getHeaders());
    }


    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("Request filter");
        System.out.println("Headers: " + requestContext.getHeaders());


    }

}
