package ktgkid.spring.mvc.dao;


import ktgkid.spring.mvc.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // * 동적 질의문
    // 조건에 따라 실행할 질의문의 형태가 바뀌는 것
    // 제목으로 검색 : select * from board where title = ?
    // 작성자로 검색 : select * from board where userid = ?
    // 본문으로 검색 : select * from board where content = ?
    // => select * from ? where ? = ? (실행 X)
    // 테이블명, 컬럼명은 매개변수화 할 수 없음.
    @Override
    public List<BoardVO> selectBoard(String fkey, String fval, int snum) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select bno, title, userid, regdate, view from board ");

        if (fkey.equals("title")) {
            sql.append(" where title = :fval ");
        }
        else if (fkey.equals("userid")) {
            sql.append(" where userid = :fval ");
        }
        else if (fkey.equals("content")) {
            sql.append(" where content = :fval ");
        }

        sql.append(" order by bno desc limit :snum, 25 ");
        /*String sql = " select bno, title, userid, regdate, view from board order by bno desc limit :snum, 25 ";*/

        Map<String, Object> params = new HashMap<>();
        params.put("snum", snum);
        params.put("fval", fval);

        return jdbcNamedTemplate.query(sql.toString(), params, boardMapper);
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

    @Override
    public int selectCountBoard(String fkey, String fval) {

        String sql = " select ceil(count(bno)/25) pages from board; ";


        return jdbcTemplate.queryForObject(sql, null, Integer.class);

    }
}
