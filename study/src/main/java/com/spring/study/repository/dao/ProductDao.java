package com.spring.study.repository.dao;

import com.spring.study.domain.Product;
import com.spring.study.repository.ProductRepository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class ProductDao implements ProductRepository {

    // DAO <-> Repository
    // SQLMapper <-> ORM
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 데이터 변경 작업(수정, 추가, 삭제)는 update()
    // 데이터 조회 작업은 query()

    @Override
    public Product save(String name, int price) {
        String sql = "INSERT INTO product (name, price) VALUES(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, price);
            return ps;
        }, keyHolder);

        return findProduct(keyHolder.getKey().longValue());
    }

    @Override
    public Product update(Long id, String name, int price) {
        String sql = "UPDATE product SET name = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, price, id);
        return findProduct(id);
    }

    @Override
    public List<Product> findProducts() {
        String sql = "SELECT id, name, price FROM product";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    @Override
    public Product findProduct(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, productRowMapper());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    /**
     * RowMapper
     * JDBC의 ResultSet에서 DB 데이터를 자바 객체로 변화하는 과정을 편리하고 재사용 가능하게 하기 위해 사용
     * ResultSet -> 결과가 줄마다 순서대로 들어있는 데이터 구조
     * RowMapper를 활용하여 각 줄마다를 원하는 도메인 객 체로 손쉽게 변환할 수 있다
     */
    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getInt("price"));
            return p;
        };
    }
}
