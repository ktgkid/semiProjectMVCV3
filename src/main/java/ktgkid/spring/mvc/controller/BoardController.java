package ktgkid.spring.mvc.controller;

import ktgkid.spring.mvc.sevice.BoardService;
import ktgkid.spring.mvc.vo.BoardVO;
import ktgkid.spring.mvc.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class BoardController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    // bean 클래스로 정의한 경우 @Autowired 어노테이션 생략 가능 Spring 5.0 이상가능
    @Autowired
    private BoardService bsrv;

    @GetMapping("/list")
    public String list(Model m){
        m.addAttribute("bdlist", bsrv.readBoard());

        return "board/list";
    }

    @GetMapping("/view")
    public String view(){
        return "board/view";
    }

    @GetMapping("/write")
    public String write(){

        return "board/write";
    }

    @PostMapping("/write")
    public String writeok(BoardVO bvo){

        LOGGER.info("작성완료");

        bsrv.newBoard(bvo);

        return "redirect:/list";
    }
}
