package org.openindustries.legal.document;

import java.util.UUID;

/** LegalDocument aggregate — Document catalogue — metadata only, never content. */
public class LegalDocument {
    public UUID id;
        public String name;
        public String documentType;
        public int pages;
    public String status;

    public static LegalDocument create(String name, String documentType, int pages) {
        LegalDocument e = new LegalDocument();
        e.id = UUID.randomUUID();
        e.name = name;
        e.documentType = documentType;
        e.pages = pages;
        e.status = "DRAFT";
        return e;
    }
}
