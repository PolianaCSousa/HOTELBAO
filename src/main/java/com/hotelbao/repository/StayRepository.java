package com.hotelbao.repository;

import com.hotelbao.entities.Stay;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Long> {

//    @Query(nativeQuery = true,
//            value = """
//            SELECT s.*
//                        FROM tb_stay s
//                        INNER JOIN tb_user u ON s.user_id = u.id
//            """
//    )
//    List<Stay> findAll();

    List<Stay> findByUserId(Long userId);
}
