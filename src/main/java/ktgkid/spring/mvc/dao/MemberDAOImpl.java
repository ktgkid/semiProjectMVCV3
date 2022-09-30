package ktgkid.spring.mvc.dao;

import ktgkid.spring.mvc.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO{
    //@Autowired bean 태그에 정의한 경우 생략가능. Spring 5.0 이상가능

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    public MemberDAOImpl(DataSource dataSource) {
        // 고급화 단순 기술. (insert 만 가능)
        simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingColumns("userid", "passwd", "name", "email");
        // sql 구문 생략가능.
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
}
