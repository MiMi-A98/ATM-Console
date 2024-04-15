package atm;

import java.util.Scanner;

public class AutomatedTellerMachine {

    private boolean isUserLoggedIn = false;
    private Card card;

    public AutomatedTellerMachine() {
    }

    private void logout() {
        display("Good bye!");
        isUserLoggedIn = false;
        card = null;
    }

    public void login(Card card) {
        display("Welcome to ATM!");
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 3; i++) {

            display("Add card PIN: ");
            String pin = scanner.nextLine();

            if (card.getPin().equals(pin)) {
                display("Welcome!");
                isUserLoggedIn = true;
                this.card = card;
                break;
            } else if (i == 3) {
                display("""
                        PIN tries exceeded!
                        Your card has been blocked!
                        """);
            } else {
                display("Incorrect PIN! Try again!");
            }
        }
    }

    public void display(String message) {
        System.out.println(message);
    }

    public void displayMainMenu() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        Scanner scanner = new Scanner(System.in);
        display("""
                1 - Checking account
                ~~2 - Savings Account~~
                3 - Quit
                """);

        int option = scanner.nextInt();

        switch (option) {
            case 1 -> displayCheckingAccount();
            // case 2 -> displaySavingsAccount();
            case 3 -> logout();
            default -> {
                display("Invalid option! Try again");
                displayMainMenu();
            }
        }
    }

    private void displayCheckingAccount() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        Scanner scanner = new Scanner(System.in);
        display("""
                1 - View balance
                2 - Withdraw funds
                3 - Deposit
                4 - Quit
                """);
        int option = scanner.nextInt();

        switch (option) {
            case 1 -> viewBalance();
            case 2 -> withdraw();
            case 3 -> deposit();
            case 4 -> logout();
            default -> {
                display("Invalid option! Try again");
                displayCheckingAccount();
            }
        }
    }

    public void viewBalance() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }
        display("Your balance is: " + card.getBankAccount().getBalance());
    }

    public void withdraw() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        Scanner scan = new Scanner(System.in);
        display("Enter withdraw amount!");
        double withdrawAmount = scan.nextInt();

        BankAccount bankAccount = card.getBankAccount();

        try {
            bankAccount.withdraw(withdrawAmount);
            display("Operation processed successfully!");
            display("Your new balance is: " + bankAccount.getBalance());

        } catch (IllegalArgumentException ex) {
            display("Withdraw amount is too high! Your total balance is: " + bankAccount.getBalance());
            display("""
                    Do you want to retry?
                    1 - Yes
                    2 - No
                    """);
            int option = scan.nextInt();

            switch (option) {
                case 1 -> withdraw();
                case 2 -> logout();
                default -> {
                    display("Invalid option! Try again");
                    withdraw();
                }
            }

        }
    }

    public void deposit() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        Scanner scan = new Scanner(System.in);
        display("Enter deposit amount!");
        double depositAmount = scan.nextDouble();
        BankAccount bankAccount = card.getBankAccount();

        bankAccount.deposit(depositAmount);
        display("Your total balance is: " + bankAccount.getBalance());

    }

    public void changePin() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        Scanner scan = new Scanner(System.in);
        display("Enter new pin.");
        String newPin = scan.nextLine();

        // TODO: validate new PIN (4 numbers)

        card.changePin(newPin);
    }

}
