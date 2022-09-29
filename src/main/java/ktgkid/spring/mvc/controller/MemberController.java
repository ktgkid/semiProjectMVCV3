package ktgkid.spring.mvc.controller;

import ktgkid.spring.mvc.sevice.MemberService;
import ktgkid.spring.mvc.sevice.MemberServiceImpl;
import ktgkid.spring.mvc.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService msrv;
    /*private MemberServiceImpl memberService;*/

    // 로그 유형 : trace, debug, info, warn, error
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());
    @GetMapping("/join")
    public String join(){

        LOGGER.info("join!");

        return "join/join";
    }

    @PostMapping("/join")
    public String joinok(MemberVO mvo){

        LOGGER.info("joinok! {}", mvo);

        // 회원정보 저장.
        if (msrv.newMember(mvo))
            LOGGER.info("회원가입성공");

        return "redirect:/login"; /* 페이지가 바뀌게끔. */
    }

    @GetMapping("/login")
    public String login(){
        return "join/login";
    }

    @PostMapping("/login")
    public String loginok(){

        return "redirect:/myinfo"; /* 페이지가 바뀌게끔. */
    }

    @GetMapping("/myinfo")
    public String myinfo(){
        return "join/myinfo";
    }
}
