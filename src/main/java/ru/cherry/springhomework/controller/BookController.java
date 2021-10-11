package ru.cherry.springhomework.controller;


import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.cherry.springhomework.dao.BookRepository;
import ru.cherry.springhomework.domain.Book;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/books")
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/api/books/view/{id}")
    public Mono<Book> getBook(@PathVariable String id) {
       return bookRepository.findById(id);
    }

    @GetMapping("/api/books/delete/{id}")
    public Mono<Void> deleteBook(@PathVariable String id) {
        return bookRepository.deleteById(id);
    }

    @PostMapping("/api/books/save")
    public Mono<Book> saveBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

}
