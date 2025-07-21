package com.lms.controller;


import com.lms.service.BookService;
import com.lms.dto.BookRequestDto;
import com.lms.entity.Book;
import com.lms.service.BookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public ResponseEntity<Book> create(@RequestBody BookRequestDto bookRequestDto) {
        Book savedBookDto = bookService.create(bookRequestDto);
        if (savedBookDto == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN') or hasAuthority('ROLE_MEMBER')")
    public Page<Book> fetchAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String author,
                               @RequestParam(defaultValue = "id") String sortBy,
                               @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = "desc".equalsIgnoreCase(direction) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Book> spec = Specification.where(BookSpecification.hasTitle(title))
                .and(BookSpecification.hasAuthor(author));

        return bookService.findByPaginationAndSorting(spec, pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_LIBRARIAN')")
    public ResponseEntity<Book> update(@RequestBody BookRequestDto bookRequestDto) {
        Book book = bookService.update(bookRequestDto);
        if (book != null) {
            return ResponseEntity.ok().body(book);
        }
        return ResponseEntity.notFound().build();
    }

}
