package org.openindustries.legal.matter;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/matters")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Legal Matters", description = "Matter registry; the client reference is masked at intake.")
public class LegalMatterResource {

    @Inject
    LegalMatterService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a legalmatter")
    public Response create(@Valid CreateLegalMatterRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<LegalMatter> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public LegalMatter get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/suspend")
    @Operation(summary = "Suspend this legalmatter (lifecycle transition, no body)")
    public LegalMatter suspend(@PathParam("id") UUID id) {
        return service.suspend(id);
    }

    @POST
    @Path("/{id}/close")
    @Operation(summary = "Close this legalmatter (lifecycle transition, no body)")
    public LegalMatter close(@PathParam("id") UUID id) {
        return service.close(id);
    }
}
