package cinema;

import java.util.Scanner;

public class Cinema {

    static int purchasedTickets;
    static int currentIncome;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of rows:\n> ");
        int rows = sc.nextInt();
        System.out.print("Enter the number of seats in each row:\n> ");
        int seats = sc.nextInt();

        //Create 2D seat arrangement
        String[][] rowSeats = new String[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                rowSeats[i][j] = "S";
            }
        }

        //Start the transaction
        int input = -1;
        do {
            menu();
            System.out.print("> ");
            input = sc.nextInt();
            switch (input) {
                case 1 -> printCinema(rowSeats);
                case 2 -> buyTicket(sc, rows, seats, rowSeats);
                case 3 -> statistics(rows, seats, rowSeats);
                case 0 -> System.out.println("Exiting...");
            }
        } while (input != 0);

        //Print initial Cinema layout
        printCinema(rowSeats);
}

    public static void printCinema(String[][] rowSeats) {
        int rows = rowSeats.length;
        int seats = rowSeats[0].length;

        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 1; i <= seats; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < seats; j++) {
                System.out.print(rowSeats[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void menu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static void statistics(int rows, int seats, String[][] rowSeats) {

        System.out.println("Number of purchased tickets: " + purchasedTickets);
        double percent = (purchasedTickets / (double) (rows * seats)) * 100;
        System.out.printf("Percentage: %.2f%%\n",percent);
        System.out.println("Current income: $" + currentIncome);

        int totalIncome;
        if (rows * seats <= 60) {
            totalIncome = rows * seats * 10;
        } else {
            int frontHalf = rows / 2;
            int backHalf = rows - frontHalf;
            totalIncome = (frontHalf * seats * 10) + (backHalf * seats * 8);
        }

        System.out.println("Total income: $" + totalIncome );
    }

    public static void buyTicket(Scanner sc, int rows, int seats, String[][] rowSeats) {

        int rowNumber = -1;
        int seatNumber = -1;
        boolean validInput = false;

        while (!validInput) {
            //Prompt for seat selection
            System.out.print("Enter a row number:\n> ");
            rowNumber = sc.nextInt();
            System.out.print("Enter a seat number in that row:\n> ");
            seatNumber = sc.nextInt();

            if (rowNumber < 1 || rowNumber > rows || seatNumber <  1 || seatNumber > seats) {
                System.out.println("Wrong input!");
            } else if (rowSeats[rowNumber - 1][seatNumber - 1].equals("B")) {
                System.out.println("That ticket has already been purchased!");
            } else {
                validInput = true;
            }
        }

        //Determine the ticket price based on number of rows and seats
        int ticketPrice;
        if (rows * seats <= 60) {
            ticketPrice = 10;
        } else {
            int frontHalf = rows / 2;
            ticketPrice = (rowNumber <= frontHalf) ? 10 : 8;
        }
        purchasedTickets++;
        currentIncome += ticketPrice;
        System.out.println("Ticket price is: $" + ticketPrice);

        //Mark the seat as booked
        rowSeats[rowNumber - 1][seatNumber - 1] = "B";
    }
}