package org.openindustries.legal.retainer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateTimeChargeRequest(
        @NotBlank @Pattern(regexp = "RESEARCH|DRAFTING|HEARING|NEGOTIATION") String activityType,
        @Positive long amountMinor) {
}
