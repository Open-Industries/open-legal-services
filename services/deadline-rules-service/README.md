# Deadline Rules Service

Bounded context: **Deadline Rules** — Decision rules binding matter types to filing-window and extension envelopes.

Alignment: Procedural deadline governance as auditable rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/deadline-rules` |
| Owns data | DeadlineRule rules |
| Publishes | `deadlines.activated` |

Run locally: `mvn -pl services/deadline-rules-service quarkus:dev`.
