# Open Legal Services — Reference Legal Practice Core Platform

A **standard, generic legal services core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> charges against a matter's retainer never exceed the retainer balance — a charge that would overrun it is rejected (422) and records nothing; client funds accounting is always exact.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| Akoma Ntoso / LegalDocML (OASIS) | Legal document XML standard | `docs/domain/domain-standards.md` mapping (document metadata posture) |
| eIDAS | Electronic identification & signatures (EU) | Boundary: signature execution via qualified providers |
| ISO/IEC 27001/27002 | Information security | Matter confidentiality posture in `docs/security/` |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `matter-service` | 8081 | Practice management; confidentiality by design | Matter registry; the client reference is masked at intake. |
| `retainer-service` | 8082 | Client-funds (trust accounting) discipline | Guarded retainer balances per matter and the time charges against them. |
| `deadline-rules-service` | 8083 | Procedural deadline governance as auditable rules | Decision rules binding matter types to filing-window and extension envelopes. |
| `document-catalog-service` | 8084 | Akoma Ntoso metadata posture | Document catalogue — metadata only, never content. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `matter-risk-scorer` | Event / HTTP | Scores matter risk signals (deadline pressure, document gaps, conflicts) with an auditable rationale. |
| `deadline-compliance-evaluator` | Event / HTTP | Compares missed deadlines and on-time percentage against matter targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — charges against a matter's retainer never exceed the retainer balance — a charge that would overrun it is rejected (422) and records nothing; client funds accounting is always exact.
3. **Privacy by design** — Client references are validated at intake and never stored or returned in clear — only a masked form is kept. Matter content and documents' contents are never stored by this core (metadata only); all data is synthetic. Attorney–client privilege boundaries are adopters' professional duty.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/matter-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `deadline-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `retainer-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
