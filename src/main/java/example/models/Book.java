package example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min = 2, max = 50, message = "Размер имени должен быть между 2 и 50 символами")
    @Column(name = "name")
    private String name;

    @Size(min = 2, max = 50, message = "Размер имени должен быть между 2 и 50 символами")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "У нас не музей")
    @Max(value = 2024, message = "Ванга?")
    @Column(name = "year")
    private int year;

    @Column(name = "date_of_capture")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfCapture;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getFullName() {
        return getName() + ", " + getAuthor() + ", " + getYear();
    }

    public boolean isOverdue() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        Date tenDaysAgo = calendar.getTime();

        return dateOfCapture.before(tenDaysAgo);
    }

    public Date getDateOfCapture() {
        return dateOfCapture;
    }

    public void setDateOfCapture(Date dateOfCapture) {
        this.dateOfCapture = dateOfCapture;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
