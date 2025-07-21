package com.spring.study.repository.dao;

import com.spring.study.domain.entity.Member;
import com.spring.study.domain.entity.Product;
import java.sql.PreparedStatement;
import java.sql.Statement;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public Member signUp(String email, String password) {
        String sql = "INSERT INTO Member (email, password) VALUES(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, password);
            return ps;
        }, keyHolder);

        return findMember(keyHolder.getKey().longValue());
    }

    public void updateRefreshToken(Long id, String refreshToken) {
        String sql = "UPDATE member SET refreshToken = ? WHERE id = ?";
        jdbcTemplate.update(sql,refreshToken,id);
    }

    public Member findMember(Long id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, memberRowMapper());
    }

    public Member findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member m = new Member();
            m.setId(rs.getLong("id"));
            m.setEmail(rs.getString("email"));
            m.setPassword(rs.getString("password"));
            return m;
        };
    }
}
