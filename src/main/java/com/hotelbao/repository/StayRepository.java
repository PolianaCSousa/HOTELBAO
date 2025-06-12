package com.hotelbao.repository;

import com.hotelbao.entities.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
}
