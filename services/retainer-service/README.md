# Retainer Service

Bounded context: **Retainers** — Guarded retainer balances per matter and the time charges against them.

Alignment: Client-funds (trust accounting) discipline. Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/retainers` |
| Owns data | RetainerAccount accounts, TimeCharge records |
| Publishes | `retainer.consumed`, `retainer.insufficient` |

Run locally: `mvn -pl services/retainer-service quarkus:dev`.
