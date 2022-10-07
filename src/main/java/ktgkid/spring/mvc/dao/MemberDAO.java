package ktgkid.spring.mvc.dao;

import ktgkid.spring.mvc.vo.MemberVO;
import ktgkid.spring.mvc.vo.Zipcode;

import java.util.List;

public interface MemberDAO {
    int insertMember(MemberVO mvo);

    MemberVO selectOneMember(String uid);

    int selectOneMember(MemberVO m);

    /*int selectCountMember(MemberVO m);  // myBatis 3*/

    int selectCountUserid(String uid);

    List<Zipcode> selectZipcode(String string);
}
