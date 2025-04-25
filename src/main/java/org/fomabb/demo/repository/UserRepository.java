package org.fomabb.demo.repository;

import org.fomabb.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPrimaryEmail(String primaryEmail);

    boolean existsByPrimaryEmail(String primaryEmail);

    Optional<User> findByEmailDataEmail(String email);

    @Query("""
            SELECT u FROM User u
            WHERE
                u.dateOfBirth > :dateOfBirth
                AND (
                    lower(u.name) LIKE lower(concat(:q, '%'))
                    OR EXISTS (SELECT 1 FROM u.emailData e WHERE e.email = :q)
                    OR EXISTS (SELECT 1 FROM u.phoneData p WHERE p.phone = :q)
                )
            """)
    Page<User> searchByQueryWithDate(@Param("q") String query, @Param("dateOfBirth") Date dateOfBirth, Pageable pageable);

    @Query("""
            SELECT u FROM User u
            WHERE
                lower(u.name) LIKE lower(concat(:q, '%'))
                OR EXISTS (SELECT 1 FROM u.emailData e WHERE e.email = :q)
                OR EXISTS (SELECT 1 FROM u.phoneData p WHERE p.phone = :q)
            """)
    Page<User> searchByQueryWithoutDate(@Param("q") String query, Pageable pageable);
}