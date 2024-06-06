
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    // Constructor to initialize Ticket object with row, seat, price, and person information
    public Ticket(char row, int seat , double price, Person person) {
        this.row = row;
        this.seat = seat + 1;
        this.price = price;
        this.person = person;
    }
    // Getter method for row,seat,price and person
    public char getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }

    public void setRow(char row) {this.row = row;}

    public void setSeat(int seat) {this.seat = seat;}

    public void setPrice(double price) {this.price = price;}

    public void setPerson(Person person) {this.person = person;}

    public String getFileName() {
        return row + "" + seat + ".txt";
    }

    public static void print_info(Ticket ticket){
        System.out.println("Ticket: "+ ticket.getRow() + ticket.getSeat() + ", Price £" + ticket.getPrice() + "\n" + ticket.getPerson());
    }
    // Method to save ticket information to a text file
    public static void save(Ticket ticket) {
        try {
            FileWriter writer = new FileWriter(ticket.getFileName());
            writer.write(ticket.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error occurred while saving ticket information: " + e.getMessage());
        }
    }
}