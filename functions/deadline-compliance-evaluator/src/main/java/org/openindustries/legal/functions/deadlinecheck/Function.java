package org.openindustries.legal.functions.deadlinecheck;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares missed deadlines and on-time percentage against matter targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String matterId, int measuredMissedDeadlines, int targetMissedDeadlines,
                      double measuredOnTimePct, double targetOnTimePct) {
    }

    public record Verdict(String matterId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict deadlineComplianceEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredMissedDeadlines() > kpi.targetMissedDeadlines()) {
            findings.add("missedDeadlines " + kpi.measuredMissedDeadlines() + " exceeds target " + kpi.targetMissedDeadlines());
        }
        if (kpi.measuredOnTimePct() < kpi.targetOnTimePct()) {
            findings.add("onTimePct " + kpi.measuredOnTimePct() + " below target " + kpi.targetOnTimePct());
        }
        return new Verdict(kpi.matterId(), findings.isEmpty(), findings);
    }
}
