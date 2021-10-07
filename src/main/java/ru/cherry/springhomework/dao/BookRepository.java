package ru.cherry.springhomework.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.cherry.springhomework.domain.Book;


public interface BookRepository extends ReactiveMongoRepository<Book, String> {

}
