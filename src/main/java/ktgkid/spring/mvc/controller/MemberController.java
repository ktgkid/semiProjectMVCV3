package ktgkid.spring.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ktgkid.spring.mvc.sevice.MemberService;
import ktgkid.spring.mvc.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class MemberController {

    @Autowired
    private MemberService msrv;
    /*private MemberServiceImpl memberService;*/

    // 로그 유형 : trace, debug, info, warn, error
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());


    // 로그인 안했다면 -> join/join
    // 로그인 했다면 -> join/myinfo
    @GetMapping("/join")
    public String join(HttpSession sess){
        String returnPage = "join/join";

        if (sess.getAttribute("m") != null) {
            returnPage = "redirect:/myinfo";
        }
       /* LOGGER.info("join!");*/

        return returnPage;
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

    @PostMapping("/login") // 로그인 처리.
    public String loginok(MemberVO mvo, HttpSession sess){
        String returnPage = "join/lgnfail";

        if (msrv.checkLogin(mvo)) {
            sess.setAttribute("m", mvo);  // 회원정보를 세션에 저장.
            returnPage = "redirect:/myinfo";
        }

        return returnPage; /* 페이지가 바뀌게끔. */
    }

    @GetMapping("/logout")
    public String logout(HttpSession sess){

        sess.invalidate();  // 모든 세션 제거.

        return "redirect:/";
    }

    // 로그인 상태가 아니면 -> redirect:/login
    // 로그인했다면 -> join/myinfo
    @GetMapping("/myinfo")
    public String myinfo(Model m, HttpSession sess){
        String returnPage = "join/myinfo";

        if (sess.getAttribute("m") != null) {
            MemberVO mvo = (MemberVO) sess.getAttribute("m");
            m.addAttribute("mbr", msrv.readOneMember(mvo.getUserid()));
        }else {
            returnPage = "redirect:/login";
        }
        return returnPage;
    }

    /*@GetMapping("/myinfo/{mno}")
    public String myinfo(Model m, @PathVariable("mno") int mno){
        System.out.println(mno);
        m.addAttribute("mbr", msrv.readOneMember());

        return "join/myinfo";
    }*/

    // 아이디 중복검사 - REST api 이용
    // 요청 URL : /checkuid?uid=검사할 아이디
    // 결과 = 0 : 아이디 사용가능
    // 결과 = 1 : 아이디 사용불가
    @ResponseBody
    @GetMapping("/checkuid")
    public String checkuid(String uid) {
        String result = "0";
        if (msrv.checkUid(uid) > 0) {
            result = "1";
        }

        return result;
    }

    // 우편번호 검색
    // 요청 URL : /findzip?dong=조회할_동이름
    // 요청결과 : JSON 객체
    @ResponseBody
    @GetMapping("/findzip")
    public void findzip(String dong, HttpServletResponse res) throws IOException {

        // 응답유형을 json 으로 설정
        res.setContentType("application/json; charset=UTF-8");

        // 응답결과를 view 없이 브라우저로 바로 출력
        res.getWriter().print(msrv.findZipcode(dong));

        msrv.findZipcode(dong);
    }
}
