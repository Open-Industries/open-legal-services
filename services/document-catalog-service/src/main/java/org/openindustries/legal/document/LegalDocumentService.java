package org.openindustries.legal.document;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LegalDocumentService {

    @Inject
    LegalDocumentRepository repository;

    public LegalDocument create(CreateLegalDocumentRequest request) {
        return repository.save(LegalDocument.create(request.name(), request.documentType(), request.pages()));
    }

    public LegalDocument get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "LegalDocument not found: " + id));
    }

    public List<LegalDocument> list() {
        return repository.findAll();
    }

    public LegalDocument finalize(UUID id) {
        LegalDocument entity = get(id);
        if (!"DRAFT".equals(entity.status)) {
            throw new DomainException(409, "Only a DRAFT legaldocument can transition via finalize");
        }
        entity.status = "FINAL";
        return repository.save(entity);
    }
}
