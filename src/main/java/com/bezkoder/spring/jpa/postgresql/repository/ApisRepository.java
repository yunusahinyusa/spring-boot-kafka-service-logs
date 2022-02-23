package com.bezkoder.spring.jpa.postgresql.repository;

import com.bezkoder.spring.jpa.postgresql.entity.ApiS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApisRepository extends JpaRepository<ApiS,Long> {
}
