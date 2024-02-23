package example.services;

import example.models.Person;
import example.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        return optionalPerson.get();
    }

    public Person findByIdFetchEager(int id) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        Hibernate.initialize(optionalPerson.get().getBooks());
        return optionalPerson.get();
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        Person newPerson = findById(id);

        newPerson.setName(person.getName());
        newPerson.setYear(person.getYear());
    }

    @Transactional
    public void delete(int id) {
        Person person = findById(id);

        peopleRepository.delete(person);
    }
}
