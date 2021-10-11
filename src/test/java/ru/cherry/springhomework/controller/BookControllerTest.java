package ru.cherry.springhomework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.cherry.springhomework.dao.BookRepository;
import ru.cherry.springhomework.domain.Book;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {
    @Autowired
    private BookController bookController;
    @MockBean
    private BookRepository bookRepository;
    private WebTestClient webTestClient;

    Book book1 = new Book("1", "Book-1", "author-1", "genre-1");
    Book book2 = new Book("2", "Book-2", "author-2", "genre-2");

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(bookController).build();
        Flux<Book> result = Flux.create(books -> {
            books.next(book1);
            books.next(book2);
            books.complete();
        });
        when(bookRepository.findAll()).thenReturn(result);
        when(bookRepository.save(book1)).thenReturn(Mono.just(book1));
        when(bookRepository.findById("1")).thenReturn(Mono.just(book1));
    }

    @Test
    public void findAllBooksTest() throws Exception {
        webTestClient
                .get()
                .uri("/api/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class).hasSize(2).contains(book1, book2);
    }

    @Test
    void viewBookTest() {
        webTestClient
                .get()
                .uri("/api/books/view/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class).hasSize(1).contains(book1);

    }

    @Test
    void editBookTest() {
        webTestClient
                .post()
                .uri("/api/books/save")
                .body(BodyInserters.fromObject(book1))
                .exchange()
                .expectStatus()
                .isOk();
    }
}