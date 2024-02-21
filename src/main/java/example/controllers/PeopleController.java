package example.controllers;

import example.dao.BookDAO;
import example.dao.PersonDAO;
import example.models.Book;
import example.models.Person;

import jakarta.validation.Valid;

import example.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;
    private final BookDAO bookDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("{id}")
    public String info(@PathVariable("id") int id, Model model) {
        Person person = personDAO.get(id).get();
        List<Book> books = bookDAO.findByPersonId(id);
        model.addAttribute("person", person);
        model.addAttribute("books", books);

        return "people/info";
    }

    @GetMapping("new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String save(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Person person = personDAO.get(id).get();
        model.addAttribute("person", person);
        return "people/edit";
    }

    @PatchMapping("{id}")
    public String save(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDAO.update(id, person);
        return "redirect:/people/{id}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
