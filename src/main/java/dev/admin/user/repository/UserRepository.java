package dev.admin.user.repository;

import dev.admin.user.dto.response.UserSimpleResponseDto;
import dev.admin.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
    SELECT new dev.admin.user.dto.response.UserSimpleResponseDto(
        u.id, u.name, u.email, u.phoneNumber,u.isDormant, u.createdAt
    )
    FROM User u
    WHERE (:keyword IS NULL 
        OR u.name LIKE CONCAT('%', :keyword, '%') 
        OR u.email LIKE CONCAT('%', :keyword, '%'))
""")
    Page<UserSimpleResponseDto> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


}
