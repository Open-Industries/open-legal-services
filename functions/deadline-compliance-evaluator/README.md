# Deadline Compliance Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares missed deadlines and on-time percentage against matter targets; one finding per breach.

Invoke: `POST /deadlineComplianceEvaluator` with the KPI payload.
