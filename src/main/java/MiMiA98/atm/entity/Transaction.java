package MiMiA98.atm.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Entity
public class Transaction {

    public enum Type {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private Type transactionType;
    @ManyToOne
    @JoinColumn(name = "senderBankAccount_Id")
    private BankAccount senderBankAccount;
    @ManyToOne
    @JoinColumn(name = "receiverBankAccount_Id")
    private BankAccount receiverBankAccount;
    private BigDecimal transactionAmount;

    public Transaction() {
    }

    public Transaction(Type transactionType, BankAccount senderAccount, BankAccount receiverAccount, BigDecimal transactionAmount) {
        this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        this.transactionType = transactionType;
        this.senderBankAccount = senderAccount;
        this.receiverBankAccount = receiverAccount;
        this.transactionAmount = transactionAmount;
    }

    public String toStringBasic() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedDate = timestamp.format(formatter);

        String transactionAmountString;
        switch (transactionType) {
            case Type.WITHDRAWAL -> transactionAmountString = "-" + transactionAmount;
            case Type.DEPOSIT -> transactionAmountString = "+" + transactionAmount;
            default -> transactionAmountString = String.valueOf(transactionAmount);
        }

        return "Transaction time: " + formatedDate +
                " Transaction type: " + transactionType +
                " Sender: " + (senderBankAccount == null ? "ATM" : senderBankAccount.getAccountNumber()) +
                " Receiver: " + (receiverBankAccount == null ? "ATM" : receiverBankAccount.getAccountNumber()) +
                " Transaction amount: " + transactionAmountString;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", timestamp=" + timestamp +
                ", transactionType=" + transactionType +
                ", senderAccount=" + (senderBankAccount == null ? "ATM" : senderBankAccount.getAccountNumber()) +
                ", receiverAccount=" + (receiverBankAccount == null ? "ATM" : receiverBankAccount.getAccountNumber()) +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}

