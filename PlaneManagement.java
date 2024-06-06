import java.io.File;// Importing FileWriter class to write data to files
import java.util.InputMismatchException;// Importing InputMismatchException class to handle mismatched input types
import java.util.Scanner;// Importing Scanner class to read input from the console
import java.io.IOException;// Importing IOException class to handle input/output exceptions
public class PlaneManagement {

    private static final int NUM_ROWS = 4;//Number of rows
    private static final int Seats_per_row = 14;//Number of seats per row
    private static final int Available = 0;//Integer to representing an available seat status
    private static final int Unavailable = -1;// Integer value for an unavailable seat status
    private static final int sold = 1;//Integer value for a sold seat status
    private static final Integer[][] seats = new Integer[NUM_ROWS][Seats_per_row];//two dimentional array for seats in plane
    private static final Ticket[] tickets = new Ticket[NUM_ROWS * Seats_per_row];//array of storing ticket objects

    //welcome messege
    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management application!");

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < Seats_per_row; j++) {
                seats[i][j] = Available;
                if ((i == 1 || i == 2) && (j == 12 || j == 13)) {
                    seats[i][j] = Unavailable;
                }
            }
        }

        Scanner input = new Scanner(System.in);
        int choice;
        do {
            //displaying the menu
            System.out.println("**************************************************");
            System.out.println("*                  MENU OPTIONS                  *");
            System.out.println("**************************************************");

            System.out.println("1. Buy a seat");
            System.out.println("2. Cancel a seat");
            System.out.println("3. Find first available seat");
            System.out.println("4. Show seating plan");
            System.out.println("5. Print tickets information and total sale");
            System.out.println("6. Search for a ticket");
            System.out.println("0. Quit");
            System.out.println("*".repeat(36));
            System.out.print("Please select an option: ");
            try {
                choice = input.nextInt();
                // Switching based on user's choice
                switch (choice) {
                    case 1:
                        buy_seat(input);// Calling method to buy a seat
                        break;
                    case 2:
                        cancel_seat(input);// Calling method to cancel a seat
                        break;
                    case 3:
                        find_first_available();// Calling method to find the first available seat
                        break;
                    case 4:
                        show_seating_plan();// Calling method to display seating plan
                        break;
                    case 5:
                        print_tickets_info();// Calling method to print tickets information
                        break;
                    case 6:
                        search_ticket(input);// Calling method to search for a ticket
                        break;
                    case 0:
                        System.out.println("Exiting...Thanks for using our service");// Exiting the program
                        break;
                    default:
                        System.out.println("Invalid choice");// Handling non-integer input
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.next();
                choice = -1;
            }
        } while (choice != 0);// Looping until the user chooses to quit
        input.close();// Closing the Scanner object
    }
    /*
    enables the user to purchase a seat by entering the seat number and row letter.
    uses the seat index to determine the pricing.
    uses the passenger details provided to create a Person object.
    combines the Person object and the computed price to create a Ticket object.
    changes the status of the seat to sold.
    saves the Ticket object at the appropriate index in the tickets array.
    */
    private static void buy_seat(Scanner input) {
        char rowLetter = get_validRow(input);
        int row = get_rowNumber(rowLetter);
        int seatIndex = get_seatIndex(input);

        if (seats[row][seatIndex] == sold || seats[row][seatIndex] == Unavailable) {
            System.out.println("Seat already sold or unavailable. Please try again.");
            return;
        }
        // Prompting user to enter person information
        System.out.print("Enter person name: ");
        String name = input.next();
        System.out.print("Enter person surname: ");
        String surname = input.next();
        System.out.print("Enter person email: ");
        String email = input.next();
        Person person = new Person(name, surname, email);
        double price;
        if (seatIndex < 5) {
            price = 200.0;// First row
        } else if (seatIndex < 9) {
            price = 150.0;// Second row
        } else {
            price = 100.0;// Third and fourth rows
        }
        Ticket ticket = new Ticket(rowLetter, seatIndex, price, person);
        // Marking seat as sold and saving ticket information
        seats[row][seatIndex] = sold;
        tickets[row * Seats_per_row + seatIndex] = ticket;
        System.out.println("Seat purchased successfully! Ticket information saved to txt file");
        Ticket.save(ticket);
    }

    private static void cancel_seat(Scanner input) {
        /*
    gives the option for the user to change their seat by providing the seat number and row letter.
    Activates the available seat status.
    pulls the ticket from the tickets array that corresponds to the given seat.
    Deletes the file linked to the ticket, if one is present.
    sets the ticket to null in the tickets array.

    */
        char rowLetter = get_validRow(input);
        int row = get_rowNumber(rowLetter);
        int seatIndex = get_seatIndex(input);
        seats[row][seatIndex] = Available;
        Ticket canceledTicket = tickets[row * Seats_per_row + seatIndex];
        if (canceledTicket != null) {
            File cancel = new File(canceledTicket.getFileName());
            cancel.delete();
            tickets[row * Seats_per_row + seatIndex] = null;
        } else {
            System.out.println("Invalid seat or seat not sold");
        }


        System.out.println("Canceled successfully!");
    }
    // Method to find the first available seat
    //If an available seat is found, prints the seat location and exits the method.
    //If no available seats are found, prints a message indicating that there are no available seats.
    private static void find_first_available() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < Seats_per_row; j++) {
                if (seats[i][j] == Available) {
                    char rowLetter = (char) ('A' + i);
                    System.out.println("First available seat is " + rowLetter + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }
    // Method to display the seating plan
    private static void show_seating_plan() {
        System.out.println("Seating Plan:");
        for (int i = 0; i < NUM_ROWS; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < Seats_per_row; j++) {
                if ((i == 1 || i == 2) && (j == 12 || j == 13)) {
                    seats[i][j] = null; // Setting specific seats to null
                } else {
                    System.out.print(seats[i][j] == Available ? "O " : "X ");
                }
            }
            System.out.println();
        }
    }
    // Method to print tickets information and total sale
    //Prints information about all sold tickets and calculates the total sale amount.
    //Iterates through the 'tickets' array, printing details of each sold ticket.
    // Calculates the total sale amount by summing up the prices of all sold tickets.
    // Prints the total sale amount after printing ticket details.
    private static void print_tickets_info() {
        double totalAmount = 0;
        System.out.println("Tickets sold:");
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                ticket.print_info(ticket);
                totalAmount += ticket.getPrice();
            }
        }
        System.out.println("Total Amount: £" + totalAmount);
    }
    // Method to search for a ticket
    private static void search_ticket(Scanner input) {
        char rowLetter = get_validRow(input);
        int row = get_rowNumber(rowLetter);
        int seatIndex = get_seatIndex(input);
        Ticket ticket = tickets[row * Seats_per_row + seatIndex];
        //If a ticket is found, prints the ticket details.
        //If no ticket is found, prints a message indicating that the seat is available.
        if (ticket != null) {
            System.out.println("Ticket Found:");
            ticket.print_info(ticket);
        } else {
            System.out.println("This seat is available.");
        }
    }

    //asking the user to enter a valid row letter and returns the validated row letter.



    private static char get_validRow(Scanner input) {
        String rowLetterS;
        char rowLetter = 0;
        do {
            System.out.print("Enter row letter (A-D): ");
            rowLetterS = input.next().toUpperCase();
            if (rowLetterS.length() > 1){
                System.out.println("Invalid row.");
                continue;
            }
            rowLetter = rowLetterS.charAt(0);
            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row.");
            }
        } while (rowLetter < 'A' || rowLetter > 'D');
        return rowLetter;
    }

    //Converts the given row letter to its row number (0-3)


    private static int get_rowNumber(char rowLetter) {
        return rowLetter - 'A';
    }

    //asking the user to enter a seat number and validates the input.

    private static int get_seatIndex(Scanner input) {
        int seatIndex;
        do {
            System.out.print("Enter seat number (1-14): ");
            try {
                seatIndex = input.nextInt() - 1;
                if (seatIndex < 0 || seatIndex >= Seats_per_row) {
                    System.out.println("Invalid seat number.");
                } else {
                    return seatIndex;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid seat number.");
                input.next(); // Consume the invalid input
            }
        } while (true);
    }

}
