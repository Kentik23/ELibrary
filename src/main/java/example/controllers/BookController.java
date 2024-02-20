package example.controllers;

import example.dao.BookDAO;
import example.dao.PersonDAO;
import example.models.Book;
import example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("{id}")
    public String info(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.get(id).get();
        model.addAttribute("book", book);
        if (book.getPerson_id() != 0) {
            Person person = personDAO.get(book.getPerson_id()).get();
            model.addAttribute("personName", person.getName());
        } else
            model.addAttribute("people", personDAO.index());

        return "books/info";
    }

    @GetMapping("new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String save(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.get(id).get();
        model.addAttribute("book", book);
        return "books/edit";
    }

    @GetMapping("{id}/free")
    public String free(@PathVariable("id") int id) {
        Book book = bookDAO.get(id).get();
        book.setPerson_id(0);
        bookDAO.update(id, book);
        return "redirect:/books/{id}";
    }

    @PatchMapping("{id}")
    public String save(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @GetMapping("{id}/setPerson")
    public String setPerson(@PathVariable("id") int bookId, @RequestParam("id") int personId) {
        bookDAO.setPersonId(bookId, personId);
        return "redirect:/books/{id}";
    }
}
