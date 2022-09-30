package ktgkid.spring.mvc.dao;


import ktgkid.spring.mvc.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class BoardDAOImpl implements BoardDAO {
    //@Autowired bean 태그에 정의한 경우 생략가능. Spring 5.0 이상가능

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    public BoardDAOImpl(DataSource dataSource) {
        // 고급화 단순 기술. (insert 만 가능)
        simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("board")
                .usingColumns("title", "userid", "content");
        // sql 구문 생략가능.
    }

    @Override
    public int insertBoard(BoardVO bvo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(bvo);

        return simpleInsert.execute(params);

        /*String sql = "insert into board (title, userid, content) values (?, ?, ?)";
        Object[] params = new Object[] {
                bvo.getTitle(), bvo.getUserid(), bvo.getContent()
        };

        return jdbcTemplate.update(sql, params);*/
    }
}
