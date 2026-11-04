package org.openindustries.legal.deadlines;

import java.util.UUID;

/**
 * DeadlineRule aggregate — a decision rule of this core: for one matterType, it guarantees a
 * capacity (maxFilingWindowDays) and a threshold (maxExtensionCount), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class DeadlineRule {
    public UUID id;
    public String name;
    public String matterType;
    public long maxFilingWindowDays;
    public int maxExtensionCount;
    public int priority;
    public String status;

    public static DeadlineRule create(String name, String matterType, long maxFilingWindowDays, int maxExtensionCount, int priority) {
        DeadlineRule r = new DeadlineRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.matterType = matterType;
        r.maxFilingWindowDays = maxFilingWindowDays;
        r.maxExtensionCount = maxExtensionCount;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
