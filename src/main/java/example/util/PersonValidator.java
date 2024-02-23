package example.util;


import example.models.Person;
import example.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Optional<Person> equalsNamePerson = peopleRepository.findByName(person.getName());
        if (equalsNamePerson.isPresent() && equalsNamePerson.get().getId() != person.getId()) {
            errors.rejectValue("name", "", "Это имя уже занято");
        }
    }
}
