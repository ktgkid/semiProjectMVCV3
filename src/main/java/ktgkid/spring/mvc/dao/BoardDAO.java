package ktgkid.spring.mvc.dao;

import ktgkid.spring.mvc.vo.BoardVO;

import java.util.List;

public interface BoardDAO {
    int insertBoard(BoardVO bvo);

    List<BoardVO> selectBoard(int snum);

    BoardVO selectOneBoard(String bno);
}
