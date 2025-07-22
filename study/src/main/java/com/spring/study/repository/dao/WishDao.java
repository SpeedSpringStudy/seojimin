package com.spring.study.repository.dao;

import com.spring.study.domain.entity.Wish;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WishDao {

    private final JdbcTemplate jdbcTemplate;

    // 장바구니 상품 추가
    public List<Wish> createWish(Long memberId, Long productId, int quantity){
        String sql = "INSERT INTO wish (memberId, productId, quantity) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, memberId, productId, quantity);
        return findWishes(memberId);
    }

    // 장바구니 상품 삭제
    public List<Wish> delete(Long wishId, Long memberId) {
        String deleteSql = "DELETE FROM wish WHERE id = ? AND memberId = ?";
        jdbcTemplate.update(deleteSql, wishId, memberId);
        return findWishes(memberId);
    }

    // 장바구니 상품 수량 조절
    public List<Wish> updateQuantity(Long memberId, int quantity, Long wishId){
        if (quantity <= 0) {
            // 수량이 0 이하인 경우 삭제
            delete(wishId, memberId);
            return findWishes(memberId);
        }
        // 수량 변경
        String updateSql = "UPDATE wish SET quantity = ? WHERE id = ? AND memberId = ?";
        jdbcTemplate.update(updateSql, quantity, wishId, memberId);
        return findWishes(memberId);
    }

    // 장바구니 조회
    public List<Wish> findWishes(Long memberId) {
        String sql = "SELECT * FROM wish WHERE memberId = ?";
        return jdbcTemplate.query(sql, new Object[]{memberId}, wishRowMapper());
    }

    public Wish findWish(Long wishId) {
        String sql = "SELECT * FROM wish WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{wishId}, wishRowMapper());
    }


    private RowMapper<Wish> wishRowMapper() {
        return (rs, rowNum) -> {
            Wish w = new Wish();
            w.setId(rs.getLong("id"));
            w.setMemberId(rs.getLong("memberId"));
            w.setProductId(rs.getLong("productId"));
            w.setQuantity(rs.getInt("quantity"));
            return w;
        };
    }
}
