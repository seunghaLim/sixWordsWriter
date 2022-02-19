package toyProject.sixWordsWriter.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemberDto {

    @NotNull
    private String name;
    @NotNull
    private String loginId;
    @NotNull
    private String password;
}
