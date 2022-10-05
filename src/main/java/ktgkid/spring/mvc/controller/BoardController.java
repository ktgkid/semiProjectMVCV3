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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
public class BoardController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    // bean 클래스로 정의한 경우 @Autowired 어노테이션 생략 가능 Spring 5.0 이상가능
    @Autowired
    private BoardService bsrv;

    /* 페이징 처리 */
    /* 페이지당 게시물 수 perPage : 25 */
    /* 총 페이지 수 : 전체게시물 수 / 페이지당 게시물 수 */
    /* 총 페이지 수 pages : ceil(getTotalPage / perPage) */
    /* 2 = 50 / 25, 3 = 51 / 25 */

    /* 페이지 별 읽어 올 게시글 범위 */
    /* 총 게시글이 55건 이라 할 때 */
    /* 1page : 1번째 ~ 25번째 게시글 읽어옴 */
    /* 2page : 26번째 ~ 50번째 게시글 읽어옴 */
    /* 3page : 51번째 ~ 75번째 게시글 읽어옴 */
    /* ... */
    /* i page : m 번째 ~ n 번째 게시글 읽어옴 */
    /* m = (i - 1) * 25 + 1 */

    /* 현재 페이지에 따라서 보여줄 페이지 블럭 결정 */
    /* ex) 총 페이지 수가 27일때 */
    /* cpg = 1 : 1 2 3 4 5 6 7 8 9 10 */
    /* cpg = 5 : 1 2 3 4 5 6 7 8 9 10 */
    /* cpg = 9 : 1 2 3 4 5 6 7 8 9 10 */
    /* cpg = 10 : 1 2 3 4 5 6 7 8 9 10 */
    /* cpg = 11 : 11 12 13 14 15 16 17 18 19 20 */
    /* cpg = 17 : 11 12 13 14 15 16 17 18 19 20 */
    /* cpg = 23 : 21 22 23 24 25 26 27 */
    /* cpg = 26 : 21 22 23 24 25 26 27 */
    /* cpg = n : ? ?+1 ?+2 ... ?+9 */
    /* stpgn = ((cpg - 1) / 10) * 10 + 1 */

    @GetMapping("/list")
    public String list(Model m, String cpg, String fkey, String fval){

        int perPage = 25;
        if (cpg == null || cpg.equals("")) cpg = "1";  // http://localhost:8080/list?cpg=1 -> http://localhost:8080/list 문제 없게끔.
        if (fkey == null) fkey = "1";
        int cpage = Integer.parseInt(cpg);

        int snum = (cpage - 1) * perPage;
        int stpgn = ((cpage - 1) / 10) * 10 + 1;

        m.addAttribute("pages", bsrv.readCountBoard(fkey, fval));
        m.addAttribute("bdlist", bsrv.readBoard(fkey, fval, snum));
        m.addAttribute("stpgn", stpgn);
        //m.addAttribute("cpg", Integer.parseInt(cpg));

        return "board/list";
    }

    @GetMapping("/view")
    public ModelAndView view(ModelAndView mv, String bno){

        mv.setViewName("board/view");
        mv.addObject("bd", bsrv.readOneBoard(bno));

        return mv;
    }

    // 로그인 안했다면 -> redirect:/login
    // 로그인 했다면 -> board/write
    @GetMapping("/write")
    public String write(HttpSession sess){
        String returnPage = "board/write";

        if (sess.getAttribute("m") == null) {
            returnPage = "redirect:/login";
        }
        return returnPage;
    }

    @PostMapping("/write")
    public String writeok(BoardVO bvo){

        LOGGER.info("작성완료");

        bsrv.newBoard(bvo);

        return "redirect:/list?cpg=1";
    }
    /*@GetMapping("/del")
    public String delete(BoardVO bvo){

        return "redirect:/list";
    }*/
}
