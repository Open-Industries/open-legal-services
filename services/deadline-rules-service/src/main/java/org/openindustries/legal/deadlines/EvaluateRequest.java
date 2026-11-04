package org.openindustries.legal.deadlines;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "LITIGATION|CORPORATE|LABOR|FAMILY") String matterType,
        @Positive int requiredMaxExtensionCount,
        @Positive long requiredMaxFilingWindowDays) {
}
