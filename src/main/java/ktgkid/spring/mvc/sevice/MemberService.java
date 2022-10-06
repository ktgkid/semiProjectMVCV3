package ktgkid.spring.mvc.sevice;

import ktgkid.spring.mvc.controller.MemberController;
import ktgkid.spring.mvc.vo.MemberVO;
import org.springframework.web.bind.annotation.PathVariable;

public interface MemberService {
    boolean newMember(MemberVO mvo);


    MemberVO readOneMember();


    boolean checkLogin(MemberVO mvo);

    MemberVO readOneMember(String uid);

    String checkUid(String uid);
}
