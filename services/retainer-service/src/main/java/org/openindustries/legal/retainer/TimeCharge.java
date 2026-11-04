package org.openindustries.legal.retainer;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a RetainerAccount. */
public class TimeCharge {
    public UUID id;
    public UUID accountId;
    public String activityType;
    public long amountMinor;
    public long remainingAfter;
    public Instant recordedAt;

    public static TimeCharge of(UUID accountId, String activityType, long amountMinor, long remainingAfter) {
        TimeCharge r = new TimeCharge();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.activityType = activityType;
        r.amountMinor = amountMinor;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
