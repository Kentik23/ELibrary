package example.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Book {
    private int id;

    @Size(min = 2, max = 50, message = "Размер имени должен быть между 2 и 50 символами")
    private String name;

    @Size(min = 2, max = 50, message = "Размер имени должен быть между 2 и 50 символами")
    private String author;

    @Min(value = 0, message = "У нас не музей")
    @Max(value = 2024, message = "Ванга?")
    private int year;
    private int person_id;

    public Book() {
    }

    public Book(String name, String author, int year, int person_id) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.person_id = person_id;
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

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", person_id=" + person_id +
                '}';
    }
}
