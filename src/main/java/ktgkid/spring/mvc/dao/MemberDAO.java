package ktgkid.spring.mvc.dao;

import ktgkid.spring.mvc.vo.MemberVO;

public interface MemberDAO {
    int insertMember(MemberVO mvo);

    MemberVO selectOneMember();
}
