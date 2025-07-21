package com.lms.repository;

import com.lms.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Boolean existsBookByIsbnNumber(String isbnNumber);

}
