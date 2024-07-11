package com.andrysheq.conveyor.dto;

import com.andrysheq.conveyor.enums.EmailTheme;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Письмо на электронную почту")
public class EmailMessage {

    @Schema(description = "Адрес почты", example = "andrey.andreych@mail.ru")
    @NotNull
    private String address;

    @Schema(description = "Тема письма", example = "SEND_SES")
    @NotNull
    private EmailTheme theme;

    @Schema(description = "Идентификатор заявки")
    @NotNull
    private Long applicationId;

}
