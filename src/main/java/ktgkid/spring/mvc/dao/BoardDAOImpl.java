package ktgkid.spring.mvc.dao;


import ktgkid.spring.mvc.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class BoardDAOImpl implements BoardDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    

    @Override
    public int insertBoard(BoardVO bvo) {
        String sql = "insert into board (title, userid, content) values (?, ?, ?)";
        Object[] params = new Object[] {
                bvo.getTitle(), bvo.getUserid(), bvo.getContent()
        };

        return jdbcTemplate.update(sql, params);
    }
}
