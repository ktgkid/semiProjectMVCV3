package ktgkid.spring.mvc.dao;

import ktgkid.spring.mvc.vo.MemberVO;
import ktgkid.spring.mvc.vo.Zipcode;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO{
    //@Autowired bean 태그에 정의한 경우 생략가능. Spring 5.0 이상가능

    @Autowired
    private SqlSession sqlSession;   // myBatis 3

    /*@Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;
    private NamedParameterJdbcTemplate jdbcNameTemplate;*/


    /*private RowMapper<MemberVO> memberMapper = BeanPropertyRowMapper.newInstance(MemberVO.class);*/
    /*private RowMapper<Zipcode> zipcodeMapper = BeanPropertyRowMapper.newInstance(Zipcode.class);

    public MemberDAOImpl(DataSource dataSource) {
        // 고급화 단순 기술. (insert 만 가능)
        simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingColumns("userid", "passwd", "name", "email");
        // sql 구문 생략가능.
        jdbcNameTemplate = new NamedParameterJdbcTemplate(dataSource);
    }*/

    @Override
    public int insertMember(MemberVO mvo) {
        return sqlSession.insert("member.insertMember", mvo);  // myBatis 3

        /*SqlParameterSource params = new BeanPropertySqlParameterSource(mvo);

        return simpleInsert.execute(params);*/

        /*String sql = "insert into member (userid, passwd, name, email) values (?, ?, ?, ?)"; *//* 암기사항. *//*

        Object[] params = new Object[] {
          mvo.getUserid(), mvo.getPasswd(), mvo.getName(), mvo.getEmail()
        };

        return jdbcTemplate.update(sql, params);*/
    }

    @Override
    public MemberVO selectOneMember(String uid) {
        /*String sql = " select userid, name, email, regdate from member where userid = ? ";

        Object[] param = { uid };

        RowMapper<MemberVO> memberMapper = (rs, num) -> {
            MemberVO m = new MemberVO();

            m.setUserid(rs.getString("userid"));
            m.setName(rs.getString("name"));
            m.setEmail(rs.getString("email"));
            m.setRegdate(rs.getString("regdate"));

            return m;
        };

        return jdbcTemplate.queryForObject(sql, param, memberMapper);*/

        return sqlSession.selectOne("member.selectOneMember", uid);   // myBatis 3
    }




    @Override
    public int selectOneMember(MemberVO m) {
        /*String sql = " select count(mno) cnt from member where userid = ? and passwd = ? ";

        Object[] params = { m.getUserid(), m.getPasswd() };

        return jdbcTemplate.queryForObject(sql, params, Integer.class);*/

        return sqlSession.selectOne("member.selectCountMember", m);  // myBatis 3
    }

    @Override
    public int selectCountUserid(String uid) {
        /*String sql = " select count(mno) cnt from member where userid = ? ";

        Object[] param = new Object[] { uid };

        return jdbcTemplate.queryForObject(sql, param, Integer.class);*/

        return sqlSession.selectOne("member.selectCountUserid", uid);  // myBatis 3
    }

    @Override
    public List<Zipcode> selectZipcode(String dong) {
        /*String sql = " select * from zipcode_2013 where dong like :dong ";

        Map<String, Object> param = new HashMap<>();
        param.put("dong", dong);

        return jdbcNameTemplate.query(sql, param, zipcodeMapper);*/

        return sqlSession.selectList("member.selectZipcode", dong);  // myBatis 3
    }

    /*// 콜백 메소드 정의 : mapRow
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
    }*/
}
