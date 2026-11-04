package org.openindustries.legal.retainer;

import java.util.UUID;

/**
 * RetainerAccount aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class RetainerAccount {
    public UUID id;
        public java.util.UUID matterId;
    public long retainerBalanceMinor;
    public String status;

    public static RetainerAccount open(java.util.UUID matterId, long retainerBalanceMinor) {
        RetainerAccount a = new RetainerAccount();
        a.id = UUID.randomUUID();
        a.matterId = matterId;
        a.retainerBalanceMinor = retainerBalanceMinor;
        a.status = "ACTIVE";
        return a;
    }
}
