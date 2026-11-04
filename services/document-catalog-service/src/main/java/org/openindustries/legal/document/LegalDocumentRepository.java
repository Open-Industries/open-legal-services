package org.openindustries.legal.document;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class LegalDocumentRepository {
    private final ConcurrentHashMap<UUID, LegalDocument> store = new ConcurrentHashMap<>();

    public LegalDocument save(LegalDocument entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<LegalDocument> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<LegalDocument> findAll() {
        return List.copyOf(store.values());
    }
}
