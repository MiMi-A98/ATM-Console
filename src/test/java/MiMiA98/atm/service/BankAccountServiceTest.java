package MiMiA98.atm.service;

import MiMiA98.atm.dao.BankAccountDAO;
import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    BankAccountDAO bankAccountDAO = new BankAccountDAO();

    @Spy
    @InjectMocks
    BankAccountService bankAccountService = new BankAccountService(bankAccountDAO) {
        @Override
        public BankAccount getBankAccount(String accountNumber) {
            return null;
        }

        @Override
        void updateBalance(String accountNumber, BigDecimal newBalance) {
        }

        @Override
        void doDeposit(BankAccount bankAccount, BigDecimal depositAmount) {
        }

        @Override
        void doWithdraw(BankAccount bankAccount, BigDecimal withdrawAmount) {
        }

        @Override
        void doTransfer(BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal transferAmount) {
        }
    };


    @BeforeEach
    void setUp() {
    }

    @Test
    void deposit_validBankAccount_doesNotThrowError() {
        BankAccount bankAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal depositAmount = BigDecimal.valueOf(100);

        when(bankAccountService.getBankAccount(bankAccount.getAccountNumber())).thenReturn(bankAccount);

        assertDoesNotThrow(() -> bankAccountService.deposit(bankAccount, depositAmount));
    }

    @Test
    void deposit_nullBankAccount_throwsException() {
        BigDecimal depositAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> bankAccountService.deposit(null, depositAmount));
    }

    @Test
    void deposit_nullDepositAmount_throwsException() {
        BankAccount bankAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.deposit(bankAccount, null));
    }

    @Test
    void deposit_zeroDepositAmount_throwsException() {
        BankAccount bankAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal depositAmount = BigDecimal.valueOf(0);

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.deposit(bankAccount, depositAmount));
    }

    @Test
    void deposit_negativeDepositAmount_throwsException() {
        BankAccount bankAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal depositAmount = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class, () -> bankAccountService.deposit(bankAccount, depositAmount));
    }

    @Test
    void withdraw_validBankAccount_doesNotThrowError() {
        BankAccount bankAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal withdrawAmount = BigDecimal.valueOf(100);

        when(bankAccountService.getBankAccount(bankAccount.getAccountNumber())).thenReturn(bankAccount);

        assertDoesNotThrow(() -> bankAccountService.withdraw(bankAccount, withdrawAmount));
    }

    @Test
    void withdraw_nullBankAccount_throwsException() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> bankAccountService.withdraw(null, withdrawAmount));
    }

    @Test
    void withdraw_nullWithdrawAmount_throwsException() {
        BankAccount bankAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.withdraw(bankAccount, null));
    }

    @Test
    void withdraw_zeroWithdrawAmount_throwsException() {
        BankAccount bankAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal withdrawAmount = BigDecimal.valueOf(0);

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.withdraw(bankAccount, withdrawAmount));
    }

    @Test
    void withdraw_negativeWithdrawAmount_throwsException() {
        BankAccount bankAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal withdrawAmount = BigDecimal.valueOf(-100);

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.withdraw(bankAccount, withdrawAmount));
    }

    @Test
    void transfer_validInputs_doesNotThrowError() {
        BankAccount sourceAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BankAccount destinationAccount = generateBankAccount("s1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal transferAmount = BigDecimal.valueOf(100);

        assertDoesNotThrow(() -> bankAccountService.transfer(sourceAccount, destinationAccount, transferAmount));
    }

    @Test
    void transfer_nullSourceAccount_throwsException() {
        BankAccount destinationAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal transferAmount = BigDecimal.valueOf(100);

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.transfer(null, destinationAccount, transferAmount));
    }

    @Test
    void transfer_nullDestinationAccount_throwsException() {
        BankAccount sourceAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal transferAmount = BigDecimal.valueOf(100);

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.transfer(sourceAccount, null, transferAmount));
    }

    @Test
    void transfer_nullTransferAmount_throwsException() {
        BankAccount sourceAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BankAccount destinationAccount = generateBankAccount("s1", "USD", BigDecimal.valueOf(100), new UserAccount());

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.transfer(sourceAccount, destinationAccount, null));
    }

    @Test
    void transfer_sourceAccountEqualDestinationAccount_throwsException() {
        BankAccount sourceAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal transferAmount = BigDecimal.valueOf(100);

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.transfer(sourceAccount, sourceAccount, transferAmount));
    }

    @Test
    void transfer_zeroTransferAmount_throwsException() {
        BankAccount sourceAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BankAccount destinationAccount = generateBankAccount("s1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal transferAmount = BigDecimal.valueOf(0);

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.transfer(sourceAccount, destinationAccount, transferAmount));
    }

    @Test
    void transfer_negativeTransferAmount_throwsException() {
        BankAccount sourceAccount = generateBankAccount("c1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BankAccount destinationAccount = generateBankAccount("s1", "USD", BigDecimal.valueOf(100), new UserAccount());
        BigDecimal transferAmount = BigDecimal.valueOf(-100);

        assertThrows(IllegalArgumentException.class, () -> bankAccountService.transfer(sourceAccount, destinationAccount, transferAmount));
    }

    private static BankAccount generateBankAccount(String accountNumber, String currency, BigDecimal balance, UserAccount userAccount) {
        return new BankAccount(accountNumber, currency, balance, userAccount) {
            @Override
            public String toStringBasic() {
                return "Test";
            }
        };
    }
}