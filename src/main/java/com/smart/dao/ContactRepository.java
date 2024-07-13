package com.smart.dao;

import com.smart.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    //Pagination

    @Query("FROM Contact AS c WHERE c.user.id = :userId")
    //current page- page
    //Contacts per page- 5
    public Page<Contact> findContactByUser(@Param("userId") int userId, Pageable pageable);

}
