package com.hotelbao.repository;

import com.hotelbao.entities.User;
import com.hotelbao.projections.RoomDetailsProjection;
import com.hotelbao.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    @Query(nativeQuery = true,
            value = """
                    SELECT u.username as username,
                         u.password,
                         r.id as role_id,
                         r.authority
                    FROM tb_user u
                    INNER JOIN tb_user_role ur ON u.id = ur.user_id
                    INNER JOIN tb_role r ON r.id = ur.role_id
                    WHERE u.username = :username
                    """

    )
    List<UserDetailsProjection> searchUserAndRoleByUsername(String username);

    @Query(nativeQuery = true,
    value = """
        SELECT r.description , r.price
        FROM tb_user  u
        INNER JOIN tb_stay s ON s.user_id = u.id
        INNER JOIN tb_room r ON r.id = s.room_id
        WHERE u.id = :id
        """)
    List<RoomDetailsProjection> searchUserAndRoomByUserId(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = """
    DELETE FROM tb_role
    """
    )
    void deleteAllRoles();


    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = """
    DELETE FROM tb_user_role
    """
    )
    void deleteAllUserRoles();
}

