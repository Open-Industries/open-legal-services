# ADR-0005: Privacy by design; all reference data is synthetic

| Field | Value |
|---|---|
| Status | Accepted |
| Date | 2026-11-03 |
| Standard traceability | ISO/IEC/IEEE 42010 (architecture decision record) |

## Context

A public reference core must be safe to clone, demo and audit. ISO 27002 A.5.34.

## Decision

Client references are validated at intake and never stored or returned in clear — only a masked form is kept. Matter content and documents' contents are never stored by this core (metadata only); all data is synthetic. Attorney–client privilege boundaries are adopters' professional duty. Internal keys are UUIDs and no real personal or confidential data ever enters this repository.

## Consequences

Correlation with production records happens through the adopter's governed processes, not this API.
