package org.openindustries.legal.retainer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RetainerService {

    @Inject
    RetainerRepository repository;

    public RetainerAccount open(OpenRetainerAccountRequest request) {
        return repository.saveAccount(RetainerAccount.open(request.matterId(), request.retainerBalanceMinor()));
    }

    public RetainerAccount get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "RetainerAccount not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public TimeCharge consume(UUID accountId, CreateTimeChargeRequest request) {
        RetainerAccount account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "RetainerAccount is not ACTIVE");
        }
        if (request.amountMinor() > account.retainerBalanceMinor) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.amountMinor()
                    + " exceeds available " + account.retainerBalanceMinor);
        }
        account.retainerBalanceMinor -= request.amountMinor();
        repository.saveAccount(account);
        return repository.saveRecord(TimeCharge.of(accountId, request.activityType(), request.amountMinor(), account.retainerBalanceMinor));
    }

    public List<TimeCharge> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
