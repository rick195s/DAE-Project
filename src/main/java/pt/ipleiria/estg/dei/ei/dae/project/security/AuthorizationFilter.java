package pt.ipleiria.estg.dei.ei.dae.project.security;

import org.jboss.resteasy.core.ResourceMethodInvoker;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.ErrorDTO;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;

@Provider
@Authenticated
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    private static final Response ACCESS_DENIED = Response.status(401).entity(new ErrorDTO("Access denied for this resource")).build();
    private static final Response ACCESS_FORBIDDEN = Response.status(403).entity(new ErrorDTO("Access forbidden for this resource")).build();

    @Context
    private SecurityContext securityContext;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        var methodInvoker = (ResourceMethodInvoker) containerRequestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();

        var resource = method.getDeclaringClass();

        // If authenticated, access granted for all roles
        if (resource.isAnnotationPresent(PermitAll.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }

            //Verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                var roles = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                //Is user valid?
                if(roles.stream().anyMatch(securityContext::isUserInRole)) {
                    return;
                }

                containerRequestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }
        }

        // Access denied for all
        if(resource.isAnnotationPresent(DenyAll.class)) {
            if (method.isAnnotationPresent(PermitAll.class)) {
                return;
            }

            //Verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed beanRolesAnnotation = method.getAnnotation(RolesAllowed.class);
                var roles = new HashSet<>(Arrays.asList(beanRolesAnnotation.value()));

                //Is user valid?
                if(roles.stream().anyMatch(securityContext::isUserInRole)) {
                    return;
                }
            }

            containerRequestContext.abortWith(ACCESS_DENIED);
            return;
        }

        if (resource.isAnnotationPresent(RolesAllowed.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }

            if (method.isAnnotationPresent(PermitAll.class)) {
                return;
            }

            RolesAllowed rolesAnnotation = resource.getAnnotation(RolesAllowed.class);
            var roles = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

            //Verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                roles.addAll(Arrays.asList(rolesAnnotation.value()));
            }

            //Is user valid?
            if(roles.stream().anyMatch(securityContext::isUserInRole)) {
                return;
            }

            containerRequestContext.abortWith(ACCESS_FORBIDDEN);
            return;
        }

        if (method.isAnnotationPresent(DenyAll.class)) {
            containerRequestContext.abortWith(ACCESS_DENIED);
            return;
        }

        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        //Verify user access
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            var roles = new HashSet<>(Arrays.asList(method.getAnnotation(RolesAllowed.class).value()));

            //Is user valid?
            if(roles.stream().anyMatch(securityContext::isUserInRole)) {
                return;
            }

            containerRequestContext.abortWith(ACCESS_FORBIDDEN);
        }
    }

}
