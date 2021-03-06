package toyProject.sixWordsWriter.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MemberDto {

    @NotBlank
    private String name;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}
