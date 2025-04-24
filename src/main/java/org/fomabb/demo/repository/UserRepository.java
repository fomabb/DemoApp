package org.fomabb.demo.repository;

import org.fomabb.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPrimaryEmail(String primaryEmail);

    boolean existsByPrimaryEmail(String primaryEmail);

    Optional<User> findByEmailDataEmail(String email);

    @Query("""
            select u from User u where u.name=:q
            """)
    Page<User> searchByQuery(@Param("q") String query, Pageable pageable);
}

//select u from User u where (:query IS NULL OR u.dateOfBirth > :query)
//and (:query is null or u.name LIKE CONCAT(:name, '%'))
//and (:query is null or u.emailData=:query)
//and (:query is null or u.phoneData=:query)