package com.lms.repository;

import com.lms.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
}
