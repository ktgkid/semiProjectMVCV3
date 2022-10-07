package ktgkid.spring.mvc.sevice;

import com.fasterxml.jackson.core.JsonProcessingException;
import ktgkid.spring.mvc.controller.MemberController;
import ktgkid.spring.mvc.vo.MemberVO;
import org.springframework.web.bind.annotation.PathVariable;

public interface MemberService {
    boolean newMember(MemberVO mvo);

    boolean checkLogin(MemberVO mvo);

    MemberVO readOneMember(String uid);

    int checkUid(String uid);

    String findZipcode(String dong) throws JsonProcessingException;
}
