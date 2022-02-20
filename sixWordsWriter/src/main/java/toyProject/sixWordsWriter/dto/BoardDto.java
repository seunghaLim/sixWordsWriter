package toyProject.sixWordsWriter.dto;

import lombok.Data;
import toyProject.sixWordsWriter.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import toyProject.sixWordsWriter.annotation.wordsLimit;

@Data
public class BoardDto {


    @NotBlank
    @wordsLimit
    private String content;
}

