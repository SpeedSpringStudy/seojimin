package com.spring.study.repository;

import com.spring.study.domain.entity.Member;
import com.spring.study.domain.entity.Wish;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Page<Wish> findAllByMember(Member member, Pageable pageable);
}
