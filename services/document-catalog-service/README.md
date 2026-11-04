# Document Catalog Service

Bounded context: **Legal Documents** — Document catalogue — metadata only, never content.

Alignment: Akoma Ntoso metadata posture. Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/documents` |
| Owns data | LegalDocument records |

Run locally: `mvn -pl services/document-catalog-service quarkus:dev`.
