package toyProject.sixWordsWriter.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    @GetMapping("/member/new")
    public String join(){
        return "member/new";
    }

    @GetMapping("/member/login")
    public String login(){
        return "member/login";
    }


}
