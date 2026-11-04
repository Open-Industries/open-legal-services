package org.openindustries.legal.deadlines;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class DeadlineRuleRepository {
    private final ConcurrentHashMap<UUID, DeadlineRule> store = new ConcurrentHashMap<>();

    public DeadlineRule save(DeadlineRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<DeadlineRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<DeadlineRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<DeadlineRule> activeFor(String matterType) {
        return store.values().stream()
                .filter(r -> r.matterType.equals(matterType) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
