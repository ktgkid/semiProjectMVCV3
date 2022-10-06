package ktgkid.spring.mvc.dao;

import ktgkid.spring.mvc.vo.BoardVO;

import java.util.List;

public interface BoardDAO {
    int insertBoard(BoardVO bvo);

    List<BoardVO> selectBoard(String fkey, String fval, int snum);

    BoardVO selectOneBoard(String bno);

    int selectCountBoard(String fkey, String fval);

    int deleteBoard(String bno);

    int updateBoard(BoardVO bvo);
}
