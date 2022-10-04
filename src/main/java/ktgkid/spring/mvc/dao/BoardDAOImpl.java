package ktgkid.spring.mvc.dao;


import ktgkid.spring.mvc.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Service
public class BoardDAOImpl implements BoardDAO {
    //@Autowired bean 태그에 정의한 경우 생략가능. Spring 5.0 이상가능

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;
    private NamedParameterJdbcTemplate jdbcNamedTemplate;
    private RowMapper<BoardVO> boardMapper = BeanPropertyRowMapper.newInstance(BoardVO.class); // 이 방식을 많이 씀.

    public BoardDAOImpl(DataSource dataSource) {
        // 고급화 단순 기술. (insert 만 가능)
        simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("board")
                .usingColumns("title", "userid", "content");
        // sql 구문 생략가능.

        jdbcNamedTemplate = new NamedParameterJdbcTemplate(dataSource);

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

    @Override
    public List<BoardVO> selectBoard() {
        String sql = " select bno, title, userid, regdate, view from board order by bno desc ";

        return jdbcNamedTemplate.query(sql, Collections.emptyMap(), boardMapper);
    }

    @Override
    public BoardVO selectOneBoard(String bno) {
        // 본문글에 대한 조회수 증가시키기.
        String sql = " update board set view = view + 1 where bno = ? ";
        Object[] param = { bno };
        jdbcTemplate.update(sql, param);

        // 본문글 가져오기.
        sql = " select * from board where bno = ? ";

        return jdbcTemplate.queryForObject(sql, param, boardMapper);
    }
}
