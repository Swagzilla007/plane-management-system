public class Person {
    private String name;
    private String surname;
    private String email;

    // Constructor to initialize Person object with name, surname, and email
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Getter methods for name,surname,email
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {this.name = name;}

    public void setSurname(String surname) {this.surname = surname;}

    public void setEmail(String email) {this.email = email;}

    public String toString() {
        return "Name: " + name + ", Surname: " + surname + ", Email: " + email;
    }
}
