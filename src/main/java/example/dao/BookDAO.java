package example.dao;

import example.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> get(int id) {
        return jdbcTemplate.query("select * from book where id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public List<Book> findByPersonId(int id) {
        return jdbcTemplate.query("select * from book where person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public int setPersonId(int bookId, int personId) {
        return jdbcTemplate.update("update book set person_id = ? where id = ?", personId, bookId);
    }

    public int update(int id, Book book) {
        return jdbcTemplate.update("update book set name = ?, author = ?, year = ?, person_id = ? where id = ?", book.getName(), book.getAuthor(), book.getYear(), book.getPerson_id(), id);
    }

    public int save(Book book) {
        return jdbcTemplate.update("insert into book(name, author, year, person_id) values(?, ?, ?, ?)", book.getName(), book.getAuthor(), book.getYear(), book.getPerson_id());
    }

    public int delete(int id) {
        return jdbcTemplate.update("delete from book where id = ?", id);
    }
}
