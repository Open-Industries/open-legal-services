# Data Architecture

Data view (VP-5) of the Architecture Description.

## 1. Ownership

| Data | Owner (only writer) | Store (reference → production) |
|---|---|---|
| Legal Matters records | matter-service | in-memory → PostgreSQL `matter` |
| Retainers records | retainer-service | in-memory → PostgreSQL `retainer` |
| Deadline Rules records | deadline-rules-service | in-memory → PostgreSQL `deadlines` |
| Legal Documents records | document-catalog-service | in-memory → PostgreSQL `document` |
| Notifications records | notification-service | in-memory → PostgreSQL `notification` |

In-memory adapters exist so the platform runs anywhere with zero setup; production
persistence is configuration (`DB_KIND`, `DB_URL`) plus the JPA adapter — the domain and
contracts never change (12-Factor IV).

## 2. Privacy and identifiers

Client references are validated at intake and never stored or returned in clear — only a masked form is kept. Matter content and documents' contents are never stored by this core (metadata only); all data is synthetic. Attorney–client privilege boundaries are adopters' professional duty. Internal keys are UUIDs. All values in this repository are synthetic.

## 3. Consistency, evidence, retention

Strong within a context; eventual across contexts via CloudEvents with idempotent
consumers. Consumption and decision records are append-only evidence; corrections are
compensating records, never edits. Retention per the adopter's regulatory schedule;
personal data erasable per data-subject rights while evidential records are retained in
pseudonymized form (UUIDs only). Backups per service, encrypted, restore tested
(ISO 27002 A.8.13).
