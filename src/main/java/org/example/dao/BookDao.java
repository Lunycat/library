package org.example.dao;

import org.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(Long id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id = ?",
                new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO books (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(Long id, Book book) {
        jdbcTemplate.update("UPDATE books SET title = ?, author = ?, year = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }
}
