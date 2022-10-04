package ktgkid.spring.mvc.sevice;

import ktgkid.spring.mvc.dao.MemberDAO;
import ktgkid.spring.mvc.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("msrv")
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberDAO mdao;
    @Override
    public boolean newMember(MemberVO mvo) {
        boolean isInsert = false;

        // 회원가입이 성공시 true 를 리턴.
        if (mdao.insertMember(mvo) > 0) isInsert = true;

        return isInsert;
    }

    @Override
    public MemberVO readOneMember() {

        return mdao.selectOneMember();
    }

    @Override
    public boolean checkLogin(MemberVO m) {
        boolean isLogin = false;

        // 회원이 존재한다면.
        if ((mdao.selectOneMember(m)) > 0) isLogin = true;

        return isLogin;
    }
}
