package example.controllers;

import example.dao.PersonDAO;
import example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
    private final PersonDAO personDAO;

    @Autowired
    public TestController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String test() {
        personDAO.update(7, new Person("new", 1999));
        return "test";
    }
}
