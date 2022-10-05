package ktgkid.spring.mvc.sevice;

import ktgkid.spring.mvc.vo.BoardVO;

import java.util.List;

public interface BoardService {

    boolean newBoard(BoardVO bvo);

    List<BoardVO> readBoard(String fkey, String fval, int snum);

    BoardVO readOneBoard(String bno);

    int readCountBoard(String fkey, String fval);

    boolean removeBoard(String bno);
}


