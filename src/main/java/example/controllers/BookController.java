package example.controllers;

import example.models.Book;
import example.models.Person;
import example.services.BookService;
import example.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookService bookService;
    private PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "books_per_page", defaultValue = "0") int booksPerPage,
                        @RequestParam(value = "sort_by_year", defaultValue = "false") boolean sortByYear) {
        List<Book> books = bookService.findAll(page, booksPerPage, sortByYear);

        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("{id}")
    public String info(@PathVariable("id") int id, Model model) {
        Book book = bookService.findByIdFetchEager(id);
        model.addAttribute("book", book);

        Person person = book.getPerson();
        if (person != null) {
            model.addAttribute("personName", person.getName());
        } else
            model.addAttribute("people", peopleService.findAll());

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

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("{id}/free")
    public String free(@PathVariable("id") int id) {
        bookService.free(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("{id}/setPerson")
    public String setPerson(@PathVariable("id") int bookId, @RequestParam("id") int personId) {
        bookService.setPersonId(bookId, personId);
        return "redirect:/books/{id}";
    }
}
