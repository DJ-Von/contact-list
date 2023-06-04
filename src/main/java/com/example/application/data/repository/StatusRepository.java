package com.example.application.data.repository;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query("select c from Status c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Status> search(@Param("searchTerm") String searchTerm);

}