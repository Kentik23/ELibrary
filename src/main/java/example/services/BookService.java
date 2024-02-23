package example.services;

import example.models.Book;
import example.models.Person;
import example.repositories.BookRepository;
import example.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private BookRepository bookRepository;
    private PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        return bookRepository.findById(id).get();
    }

    public Book findByIdFetchEager(int id) {
        Book book = bookRepository.findById(id).get();
        Hibernate.initialize(book.getPerson());

        return book;
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book newBook = findById(id);

        newBook.setName(book.getName());
        newBook.setAuthor(book.getAuthor());
        newBook.setYear(book.getYear());
    }

    @Transactional
    public void delete(int id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }

    @Transactional
    public void free(int id) {
        Book book = findById(id);
        book.setPerson(null);
    }

    @Transactional
    public void setPersonId(int bookId, int personId) {
        Book book = findById(bookId);
        Person person = peopleRepository.findById(personId).get();

        book.setPerson(person);
    }
}
