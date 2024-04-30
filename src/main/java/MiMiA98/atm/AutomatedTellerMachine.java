package MiMiA98.atm;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
        if (card.isBlocked()) {
            display("Card is blocked!");
            logout();
        }

        display("Welcome to ATM!");
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 3; i++) {

            display("Add card PIN: ");
            String pin = scanner.nextLine();

            if (card.getPin().equals(pin)) {
                display("Welcome!");
                isUserLoggedIn = true;
                this.card = card;

                if (!card.isActivated()) {
                    display("Please change your PIN to activate the card!");
                    changePin();
                    card.setActivated(true);
                    login(card);
                }

                break;
            } else if (i == 3) {
                display("""
                        PIN tries exceeded!
                        Your card has been blocked!
                        """);
                card.setBlocked(true);
                logout();
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
                2 - Transfer
                3 - View bank accounts info
                4 - Change PIN
                5 - Quit
                """);

        int option = scanner.nextInt();

        switch (option) {
            case 1 -> displayCheckingAccount();
            case 2 -> transferMoney();
            case 3 -> viewAccountsInfo();
            case 4 -> changePin();
            case 5 -> logout();
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

    private void viewBalance() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }
        display("Your balance is: " + card.getCheckingAccount().getBalance());
    }

    private void withdraw() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        viewBalance();

        Scanner scan = new Scanner(System.in);
        display("Enter withdraw amount!");
        BigDecimal withdrawAmount = BigDecimal.valueOf(scan.nextDouble());

        CheckingAccount checkingAccount = card.getCheckingAccount();

        try {
            checkingAccount.withdraw(withdrawAmount);
            display("Operation processed successfully!");
            display("Your new balance is: " + checkingAccount.getBalance());

        } catch (IllegalArgumentException ex) {
            display("Withdraw amount is too high! Your total balance is: " + checkingAccount.getBalance());
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

    private void deposit() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        Scanner scan = new Scanner(System.in);
        display("Enter deposit amount!");
        BigDecimal depositAmount = BigDecimal.valueOf(scan.nextDouble());
        CheckingAccount checkingAccount = card.getCheckingAccount();

        checkingAccount.deposit(depositAmount);
        display("Your total balance is: " + checkingAccount.getBalance());
    }

    private void changePin() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        Scanner scan = new Scanner(System.in);
        display("Enter new pin.");
        String newPin = scan.nextLine();

        try {
            card.setPin(newPin);
        } catch (IllegalArgumentException ex) {
            display("Pin should contain only digits and have a length of four characters!");
            display("""
                    Do you want to retry?
                    1 - Yes
                    2 - No
                    """);

            int option = scan.nextInt();

            switch (option) {
                case 1 -> changePin();
                case 2 -> logout();
                default -> {
                    display("Invalid option! Try again");
                    changePin();
                }
            }
        }

    }

    public void transferMoney() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        BankAccount transferFromAccount = chooseTransferFromAccount();
        BankAccount transferToAccount = chooseTransferToAccount(transferFromAccount);

        Scanner scanner = new Scanner(System.in);
        display("Enter transfer amount!");
        BigDecimal transferAmount = BigDecimal.valueOf(scanner.nextDouble());

        transferFromAccount.transfer(transferAmount, transferToAccount);

    }

    public BankAccount chooseTransferFromAccount() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        UserAccount userAccount = card.getCheckingAccount().getUserAccount();
        Collection<BankAccount> bankAccounts = userAccount.getBankAccounts();

        Map<Integer, BankAccount> bankAccountsMap = new HashMap<>();
        int count = 1;
        for (BankAccount bankAccount : bankAccounts) {
            if ((bankAccount instanceof FixedTermAccount) && !((FixedTermAccount) bankAccount).isMatured()) {
                continue;
            }
            bankAccountsMap.put(count, bankAccount);
            count++;
        }

        BankAccount transferFromAccount = null;

        Scanner scanner = new Scanner(System.in);
        display("Choose account from which you want to transfer!");

        for (Map.Entry<Integer, BankAccount> mapEntry : bankAccountsMap.entrySet()) {
            int key = mapEntry.getKey();
            BankAccount bankAccount = mapEntry.getValue();
            display(String.format("%s - %s %nAccount number: %s %nBalance: %s", key, bankAccount.getClass().getSimpleName(), bankAccount.getAccountNumber(), bankAccount.getBalance()));
        }

        int accountCode = scanner.nextInt();

        if (bankAccountsMap.containsKey(accountCode)) {
            transferFromAccount = bankAccountsMap.get(accountCode);

        } else {
            display("Invalid option! Try again");
            chooseTransferFromAccount();
        }

        return transferFromAccount;
    }

    private BankAccount chooseTransferToAccount(BankAccount transferFromAccount) {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        UserAccount userAccount = card.getCheckingAccount().getUserAccount();
        Collection<BankAccount> bankAccounts = userAccount.getBankAccounts();

        Map<Integer, BankAccount> bankAccountsMap = new HashMap<>();
        int count = 1;
        for (BankAccount bankAccount : bankAccounts) {
            if (!(bankAccount instanceof FixedTermAccount) || !bankAccount.equals(transferFromAccount)) {
                bankAccountsMap.put(count, bankAccount);
                count++;
            }
        }

        BankAccount transferToAccount = null;

        Scanner scanner = new Scanner(System.in);
        display("Choose account to which you want to transfer!");

        for (Map.Entry<Integer, BankAccount> bankAccount : bankAccountsMap.entrySet()) {
            System.out.println(bankAccount);
        }

        int accountCode = scanner.nextInt();

        if (bankAccountsMap.containsKey(accountCode)) {
            transferToAccount = bankAccountsMap.get(accountCode);

        } else {
            display("Invalid option! Try again");
            chooseTransferToAccount(transferFromAccount);
        }

        return transferToAccount;
    }


    private void viewAccountsInfo() {
        if (!isUserLoggedIn) {
            throw new IllegalStateException("User is not logged in!");
        }

        UserAccount userAccount = card.getCheckingAccount().getUserAccount();
        Collection<BankAccount> bankAccountsMap = userAccount.getBankAccounts();

        for (BankAccount bankAccount : bankAccountsMap) {
            System.out.println(bankAccount);
        }

    }
}