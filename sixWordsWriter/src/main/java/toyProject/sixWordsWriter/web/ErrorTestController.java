package toyProject.sixWordsWriter.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ErrorTestController {

    @GetMapping("/500")
    public String error500(){
        throw new RuntimeException("에러발생!");
    }

    @GetMapping("/400")
    public String error400(){
        throw new IllegalArgumentException("에러발생!");
    }


}
