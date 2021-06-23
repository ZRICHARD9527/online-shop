package com.practice.shop.repository;

import com.practice.shop.dto.Pro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProRepository extends JpaRepository<Pro, Integer> {
}
