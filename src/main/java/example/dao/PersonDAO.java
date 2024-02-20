package example.dao;

import example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> get(int id) {
        return jdbcTemplate.query("select * from person where id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Optional<Person> findByName(String name) {
        return jdbcTemplate.query("select * from person where name = ?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public int update(int id, Person person) {
        return jdbcTemplate.update("update person set name = ?, year = ? where id = ?", person.getName(), person.getYear(), id);
    }

    public int save(Person person) {
        return jdbcTemplate.update("insert into person(name, year) values(?, ?)", person.getName(), person.getYear());
    }

    public int delete(int id) {
        return jdbcTemplate.update("delete from person where id = ?", id);
    }
}
