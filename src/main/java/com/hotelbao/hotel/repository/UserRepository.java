package com.hotelbao.hotel.repository;

import com.hotelbao.hotel.entities.User;
import com.hotelbao.hotel.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmailAndPassword(String email, String password);

    @Query(nativeQuery = true,
            value = """
                    SELECT u.username,
                         u.password,
                         r.id as role_id,
                         r.authority
                    FROM tb_user u
                    INNER JOIN tb_user_role ur ON u.id = ur.user_id
                    INNER JOIN tb_role r ON r.id = ur.role_id
                    WHERE u.email = :email
                    """

    )
    List<UserDetailsProjection> searchUserAndRoleByUsername(String username);
}

