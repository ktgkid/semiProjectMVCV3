package ktgkid.spring.mvc.sevice;

import ktgkid.spring.mvc.vo.BoardVO;

import java.util.List;

public interface BoardService {

    boolean newBoard(BoardVO bvo);

    List<BoardVO> readBoard();
}


