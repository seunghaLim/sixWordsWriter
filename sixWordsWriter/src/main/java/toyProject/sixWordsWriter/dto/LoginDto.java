package toyProject.sixWordsWriter.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginDto {

    @NotBlank
    String loginId;

    @NotBlank
    String password;
}
