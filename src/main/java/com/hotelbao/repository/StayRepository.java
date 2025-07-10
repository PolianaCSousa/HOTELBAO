package com.hotelbao.repository;

import com.hotelbao.dtos.StayDTO;
import com.hotelbao.entities.Stay;
import com.hotelbao.projections.RoomDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Long> {

    @Query(nativeQuery = true,
            value = """
            SELECT room.price as price, room.description FROM tb_room room
                        JOIN tb_stay stay ON room.id = stay.room_id
                        WHERE stay.user_id = :id
                        GROUP BY stay.user_id, room.description
                        ORDER BY price DESC LIMIT 1
            """
    )
    RoomDetailsProjection getMaxPrice(Long id);


    @Query(nativeQuery = true,
            value = """

                    SELECT room.price, room.description FROM tb_room room
                        JOIN tb_stay stay ON room.id = stay.room_id
                        WHERE stay.user_id = :id
                        GROUP BY stay.user_id, room.description
                        ORDER BY price ASC LIMIT 1
            
            """
    )
    RoomDetailsProjection getMinPrice(Long id);


    @Query(nativeQuery = true,
    value = """

            SELECT SUM(room.price) as price FROM tb_room room
                        JOIN tb_stay stay ON room.id = stay.room_id
                        WHERE stay.user_id = :id
                        GROUP BY stay.user_id
        """)
    RoomDetailsProjection getSumPrice(Long id);


    @Query(nativeQuery = true,
        value = """
            SELECT *
                    FROM tb_stay s 
                    WHERE ( 
                            (s.end_date >= :endDate AND s.start_date <= :endDate)
                    OR 
                            (s.end_date >= :startDate AND s.start_date <= :startDate)
                    )
                    AND s.room_id = :roomId
        """)
    Stay getRoomDate(
                        @Param("roomId") Long roomId,
                        @Param("endDate") LocalDateTime endDate,
                        @Param("startDate") LocalDateTime startDate);


    List<Stay> findAll();

    List<Stay> findByUserId(Long userId);

    List<Stay> findByRoomId(Long roomId);

}
