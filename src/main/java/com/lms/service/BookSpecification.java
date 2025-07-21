package com.lms.service;

import com.lms.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) -> title == null ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase(Locale.ROOT) + "%");
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) -> author == null ? null : cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase(Locale.ROOT) + "%");
    }
}
