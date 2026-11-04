package org.openindustries.legal.retainer;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class RetainerRepository {
    private final ConcurrentHashMap<UUID, RetainerAccount> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<TimeCharge> records = new CopyOnWriteArrayList<>();

    public RetainerAccount saveAccount(RetainerAccount account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<RetainerAccount> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public TimeCharge saveRecord(TimeCharge record) {
        records.add(record);
        return record;
    }

    public List<TimeCharge> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
