package ktgkid.spring.mvc.sevice;

import ktgkid.spring.mvc.dao.BoardDAO;
import ktgkid.spring.mvc.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
