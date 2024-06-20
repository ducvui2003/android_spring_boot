package com.commic.v1.dto.requests;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CommentCreationRequestDTO {
    @NotNull(message = "PARAMETER_MISSING")
    Integer chapterId;
    @NotNull(message = "PARAMETER_MISSING")
    String content;
}
