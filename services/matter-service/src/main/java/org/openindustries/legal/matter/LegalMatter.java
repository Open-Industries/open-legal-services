package org.openindustries.legal.matter;

import java.time.Instant;
import java.util.UUID;

/**
 * LegalMatter aggregate — Matter registry; the client reference is masked at intake.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class LegalMatter {
    public UUID id;
        public String matterType;
        public String clientReferenceMasked;
        public String jurisdictionCode;
    public String status;
    public Instant createdAt;

    public static LegalMatter create(String matterType, String clientReference, String jurisdictionCode) {
        LegalMatter e = new LegalMatter();
        e.id = UUID.randomUUID();
        e.matterType = matterType;
        e.clientReferenceMasked = mask(clientReference);
        e.jurisdictionCode = jurisdictionCode;
        e.status = "OPEN";
        e.createdAt = Instant.now();
        return e;
    }

    static String mask(String value) {
        if (value == null || value.length() < 4) {
            return "****";
        }
        return "*".repeat(value.length() - 4) + value.substring(value.length() - 4);
    }
}
