package MiMiA98.atm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "checking_account")
public class CheckingAccount extends BankAccount {

    @OneToMany(mappedBy = "checkingAccount", cascade = CascadeType.ALL)
    private List<Card> cards;

    public CheckingAccount() {
        super();
    }

    public CheckingAccount(String accountNumber, String currency, UserAccount userAccount) {
        super(accountNumber, currency, BigDecimal.valueOf(0.0), userAccount);
    }

    @Override
    public String toStringBasic() {
        return "Checking account" + "\n" +
                "Currency: " + getCurrency() + "\n" +
                "Balance: " + getBalance() + "\n";
    }

    @Override
    public String toString() {
        return "Checking account" + "\n" +
                "User name: " + getUserName() + "\n" +
                "Account number: " + getAccountNumber() + "\n" +
                "Currency: " + getCurrency() + "\n" +
                "Balance: " + getBalance() + "\n";
    }
}
