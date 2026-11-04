package org.openindustries.legal.retainer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenRetainerAccountRequest(
        @NotNull java.util.UUID matterId,
        @PositiveOrZero long retainerBalanceMinor) {
}
