package org.openindustries.legal.document;

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

@Path("/api/v1/documents")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Legal Documents", description = "Document catalogue — metadata only, never content.")
public class LegalDocumentResource {

    @Inject
    LegalDocumentService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a legaldocument")
    public Response create(@Valid CreateLegalDocumentRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<LegalDocument> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public LegalDocument get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/finalize")
    @Operation(summary = "Finalize (lifecycle transition, no body)")
    public LegalDocument finalize(@PathParam("id") UUID id) {
        return service.finalize(id);
    }
}
