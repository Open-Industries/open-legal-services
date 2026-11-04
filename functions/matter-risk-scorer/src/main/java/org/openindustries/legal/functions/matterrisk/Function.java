package org.openindustries.legal.functions.matterrisk;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores matter risk signals (deadline pressure, document gaps, conflicts) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String matterId, int deadlinePressureIndex, int documentGapIndex, int conflictIndex) {
    }

    public record Score(String matterId, int score, String decision, String rationale) {
    }

    @Funq
    public Score matterRiskScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.deadlinePressureIndex() >= 70) {
            score += 40;
            why.append("deadlinePressureIndex >= 70; ");
        }
        if (event.documentGapIndex() >= 60) {
            score += 30;
            why.append("documentGapIndex >= 60; ");
        }
        if (event.conflictIndex() >= 50) {
            score += 30;
            why.append("conflictIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.matterId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
