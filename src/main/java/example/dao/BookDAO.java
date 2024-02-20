package example.dao;

import example.models.Book;
import example.models.Person;
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
        return jdbcTemplate.update("update book set person_id = ? where id = ?", personId == -1 ? null : personId, bookId);
    }

    public Optional<Person> getOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = person.id " +
                        "WHERE book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public int update(int id, Book book) {
        return jdbcTemplate.update("update book set name = ?, author = ?, year = ? where id = ?", book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public int save(Book book) {
        return jdbcTemplate.update("insert into book(name, author, year) values(?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public int delete(int id) {
        return jdbcTemplate.update("delete from book where id = ?", id);
    }
}
