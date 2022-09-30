package ktgkid.spring.mvc.dao;

import ktgkid.spring.mvc.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO{
    //@Autowired bean 태그에 정의한 경우 생략가능. Spring 5.0 이상가능

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;
    private NamedParameterJdbcTemplate jdbcNameTemplate;

    /*private RowMapper<MemberVO> memberMapper = BeanPropertyRowMapper.newInstance(MemberVO.class);*/

    public MemberDAOImpl(DataSource dataSource) {
        // 고급화 단순 기술. (insert 만 가능)
        simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingColumns("userid", "passwd", "name", "email");
        // sql 구문 생략가능.
        jdbcNameTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int insertMember(MemberVO mvo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(mvo);

        return simpleInsert.execute(params);

        /*String sql = "insert into member (userid, passwd, name, email) values (?, ?, ?, ?)"; *//* 암기사항. *//*

        Object[] params = new Object[] {
          mvo.getUserid(), mvo.getPasswd(), mvo.getName(), mvo.getEmail()
        };

        return jdbcTemplate.update(sql, params);*/
    }

    @Override
    public MemberVO selectOneMember() {
        String sql = " select userid, name, email, regdate from member where mno = 1 ";

        RowMapper<MemberVO> memberMapper = new MemberRowMapper();

        return jdbcTemplate.queryForObject(sql, null, memberMapper);
    }

    // 콜백 메소드 정의 : mapRow
    private class MemberRowMapper implements RowMapper<MemberVO> {

        @Override
        public MemberVO mapRow(ResultSet rs, int num) throws SQLException {
            MemberVO m = new MemberVO();

            m.setUserid(rs.getString("userid"));
            m.setName(rs.getString("name"));
            m.setEmail(rs.getString("email"));
            m.setRegdate(rs.getString("regdate"));

            return m;
        }
    }
}
