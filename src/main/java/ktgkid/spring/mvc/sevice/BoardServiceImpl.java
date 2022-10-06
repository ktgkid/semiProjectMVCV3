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
    public List<BoardVO> readBoard(String fkey, String fval, int snum) {

        return bdao.selectBoard(fkey, fval, snum);
    }

    @Override
    public BoardVO readOneBoard(String bno) {
        return bdao.selectOneBoard(bno);
    }

    @Override
    public int readCountBoard(String fkey, String fval) {

        return bdao.selectCountBoard(fkey, fval);
    }

    @Override
    public boolean removeBoard(String bno) {
        boolean isDelete = false;

        if (bdao.deleteBoard(bno) > 0) isDelete = true;

        return isDelete;
    }

    @Override
    public boolean modifyBoard(BoardVO bvo) {
        boolean isUpdate = false;

        if (bdao.updateBoard(bvo) > 0) isUpdate = true;

        return isUpdate;
    }


}
