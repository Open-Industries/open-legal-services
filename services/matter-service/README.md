# Matter Service

Bounded context: **Legal Matters** — Matter registry; the client reference is masked at intake.

Alignment: Practice management; confidentiality by design. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/matters` |
| Owns data | LegalMatter records |
| Publishes | `matter.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/matter-service quarkus:dev`.
