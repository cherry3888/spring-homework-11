package ru.cherry.springhomework.changelog;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.cherry.springhomework.domain.Book;

@ChangeLog
public class DatabaseChangeLog {

    @ChangeSet(order = "000", id = "dropDb", author = "victor", runAlways = true)
    public void dropDb(MongoDatabase db) {
        System.out.println("dropDB - 001");
        db.drop();
    }

    @ChangeSet(order = "001", id = "insertBooks", author = "Victor", runAlways = true)
    public void insertBooks(MongockTemplate template) {
        template.save(new Book("1", "Book-1", "Author-1", "Genre-1"));
        template.save(new Book("2", "Book-2", "Author-2", "Genre-2"));
        template.save(new Book("3", "Book-3", "Author=3", "Genre-3"));
    }

}
