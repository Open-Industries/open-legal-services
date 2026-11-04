# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Legal Matters → Retainers → Deadline Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `matter-service` | 8081 | Practice management; confidentiality by design |
| `retainer-service` | 8082 | Client-funds (trust accounting) discipline |
| `deadline-rules-service` | 8083 | Procedural deadline governance as auditable rules |
| `document-catalog-service` | 8084 | Akoma Ntoso metadata posture |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/matter-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
