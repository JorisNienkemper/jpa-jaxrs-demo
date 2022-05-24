package io.openliberty.guides.event.resources;

import io.openliberty.guides.event.dao.FrameworkDao;
import io.openliberty.guides.event.models.Framework;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
@Path("frameworks")
@Transactional
public class FrameworkResource {

    @Inject
    private FrameworkDao frameworkDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFramework(Framework framework) {

        Framework frameworkByName = null;
        try {
            frameworkByName = frameworkDao.getFrameworkByName(framework.getName());
            return Response.status(Response.Status.BAD_REQUEST).entity("Entity already exists").build();
        } catch (NoResultException e) { //
            // This is expected!
        }

        frameworkDao.save(framework);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Framework> getFrameworks() {
        return frameworkDao.getFrameworks();
    }

}
