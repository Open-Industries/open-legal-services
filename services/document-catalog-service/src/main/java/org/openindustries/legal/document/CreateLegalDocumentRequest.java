package org.openindustries.legal.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateLegalDocumentRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "CONTRACT|BRIEF|FILING|OPINION") String documentType,
        @Positive int pages) {
}
