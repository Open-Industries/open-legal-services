package org.openindustries.legal.matter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LegalMatterService {

    @Inject
    LegalMatterRepository repository;

    public LegalMatter create(CreateLegalMatterRequest request) {
        return repository.save(LegalMatter.create(request.matterType(), request.clientReference(), request.jurisdictionCode()));
    }

    public LegalMatter get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "LegalMatter not found: " + id));
    }

    public List<LegalMatter> list() {
        return repository.findAll();
    }

    public LegalMatter suspend(UUID id) {
        LegalMatter entity = get(id);
        if ("CLOSED".equals(entity.status)) {
            throw new DomainException(409, "A closed legalmatter is terminal");
        }
        if ("ON_HOLD".equals(entity.status)) {
            throw new DomainException(409, "LegalMatter already ON_HOLD");
        }
        entity.status = "ON_HOLD";
        return repository.save(entity);
    }

    public LegalMatter close(UUID id) {
        LegalMatter entity = get(id);
        if ("CLOSED".equals(entity.status)) {
            throw new DomainException(409, "A closed legalmatter is terminal");
        }
        if ("CLOSED".equals(entity.status)) {
            throw new DomainException(409, "LegalMatter already CLOSED");
        }
        entity.status = "CLOSED";
        return repository.save(entity);
    }
}
