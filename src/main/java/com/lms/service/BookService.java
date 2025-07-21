package com.lms.service;

import com.lms.dto.BookRequestDto;
import com.lms.entity.Book;
import com.lms.repository.BookRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book create(BookRequestDto bookRequestDto) {
        if (Boolean.TRUE.equals(bookRepository.existsBookByIsbnNumber(bookRequestDto.getIsbnNumber()))) {
            return null;
        }
        return bookRepository.save(Book.builder()
                .title(bookRequestDto.getBookTitle())
                .author(bookRequestDto.getAuthorName())
                .isbnNumber(bookRequestDto.getIsbnNumber())
                .build());

    }

    public Book update(BookRequestDto bookRequestDto) {
        return bookRepository.findById(bookRequestDto.getBookId())
                .map(bookEntityDto -> {
                    bookEntityDto.setTitle(bookRequestDto.getBookTitle() != null ? bookRequestDto.getBookTitle() : bookEntityDto.getTitle());
                    bookEntityDto.setAuthor(bookRequestDto.getAuthorName() != null ? bookRequestDto.getAuthorName() : bookEntityDto.getAuthor());
                    bookEntityDto.setIsbnNumber(bookRequestDto.getIsbnNumber() != null ? bookRequestDto.getIsbnNumber() : bookEntityDto.getIsbnNumber());
                    return bookRepository.save(bookEntityDto);
                }).orElse(null);
    }

    public Page<Book> findByPaginationAndSorting(Specification<Book> spec, Pageable pageable) {
        return bookRepository.findAll(spec,pageable);
    }


}
