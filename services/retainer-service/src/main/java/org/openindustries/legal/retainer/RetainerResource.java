package org.openindustries.legal.retainer;

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

@Path("/api/v1/retainers")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Retainers", description = "Guarded retainer balances per matter and the time charges against them.")
public class RetainerResource {

    @Inject
    RetainerService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a retaineraccount")
    public Response open(@Valid OpenRetainerAccountRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public RetainerAccount get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/charges")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public TimeCharge consume(@PathParam("id") UUID id, @Valid CreateTimeChargeRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/charges")
    @Operation(summary = "List consumption records of an account")
    public List<TimeCharge> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}
