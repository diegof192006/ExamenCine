package Diego.Ferreiro.ExamenCine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RepartoRequestDTO(
        @NotNull Long actorId,
        @NotBlank String personaje,
        @PositiveOrZero double salario) {
}
