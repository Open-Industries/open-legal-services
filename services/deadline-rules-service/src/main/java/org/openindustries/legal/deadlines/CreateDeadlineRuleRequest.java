package org.openindustries.legal.deadlines;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateDeadlineRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "LITIGATION|CORPORATE|LABOR|FAMILY") String matterType,
        @Positive long maxFilingWindowDays,
        @Positive int maxExtensionCount,
        @Min(1) @Max(100) int priority) {
}
