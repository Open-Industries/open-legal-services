package org.openindustries.legal.matter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateLegalMatterRequest(
        @NotBlank @Pattern(regexp = "LITIGATION|CORPORATE|LABOR|FAMILY") String matterType,
        @NotBlank String clientReference,
        @NotBlank String jurisdictionCode) {
}
