package ktgkid.spring.mvc.sevice;

import ktgkid.spring.mvc.dao.BoardDAO;
import ktgkid.spring.mvc.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bsrv")
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardDAO bdao;
    //private
    @Override
    public boolean newBoard(BoardVO bvo) {
        boolean result = false;
        if (bdao.insertBoard(bvo) > 0) result = true;
        return result;
    }



    @Override
    public List<BoardVO> readBoard(int snum) {

        return bdao.selectBoard(snum);
    }

    @Override
    public BoardVO readOneBoard(String bno) {
        return bdao.selectOneBoard(bno);
    }

    @Override
    public int readCountBoard() {

        return bdao.selectCountBoard();
    }
}
