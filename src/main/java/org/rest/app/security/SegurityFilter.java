package org.rest.app.security;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SegurityFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    private static final String SECURED_URL_PREFIX = "secured";

    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("entro");
        System.out.printf(String.valueOf(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)));
        if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX))
        {
            System.out.println("Entro al primer if");
            List<String> authHeader  = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
            if (authHeader != null && authHeader.size() >0)
            {
                System.out.println("Entro al segundo if");
                String authToken = authHeader.get(0);
                System.out.println(authToken);
                authToken = authToken.replace(AUTHORIZATION_HEADER_PREFIX,"");
                System.out.println(authToken);
                String decodedString = Base64.decodeAsString(authToken);
                System.out.println(decodedString);
                StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
                String username = tokenizer.nextToken();
                System.out.println(username);
                String password = tokenizer.nextToken();
                System.out.println(password);

                if ("user".equals(username) && "password".equals(password))
                {
                    return;
                }
            }

            Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
                    .entity("User cannot access the resource.").build();
            requestContext.abortWith(unauthorizedStatus);
        }

    }
}
