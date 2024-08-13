package MiMiA98.atm.service;

import MiMiA98.atm.dao.UserAccountDAO;
import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.CheckingAccount;
import MiMiA98.atm.entity.SavingsAccount;
import MiMiA98.atm.entity.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @Mock
    private UserAccountDAO userAccountDAO;

    @InjectMocks
    private UserAccountService userAccountService;

    @BeforeEach
    public void setUp() {
        userAccountService = new UserAccountService(userAccountDAO);
    }

    @Test
    void createUserAccount_validInputs_passesMethodCallVerification() {
        String id = "user1";
        String name = "abc";

        userAccountService.createUserAccount(id, name);

        verify(userAccountDAO).createUserAccount(id, name);
    }

    @Test
    void createUserAccount_nullAccountNumber_throwsException() {
        String name = "abc";
        assertThrows(IllegalArgumentException.class, () -> userAccountService.createUserAccount(null, name));
    }

    @Test
    void createUserAccount_emptyAccountNumber_throwsException() {
        String name = "abc";
        assertThrows(IllegalArgumentException.class, () -> userAccountService.createUserAccount("", name));
    }

    @Test
    void createUserAccount_nullName_throwsException() {
        String id = "user1";
        assertThrows(IllegalArgumentException.class, () -> userAccountService.createUserAccount(id, null));
    }

    @Test
    void createUserAccount_emptyName_throwsException() {
        String id = "user1";
        assertThrows(IllegalArgumentException.class, () -> userAccountService.createUserAccount(id, ""));
    }

    @Test
    void getUserAccount_validAccountNumber_passesMethodCallVerification() {
        String id = "user1";

        userAccountService.getUserAccount(id);

        verify(userAccountDAO).readUserAccount(id);
    }

    @Test
    void getUserAccount_validAccountNumber_resultNotNull() {
        String id = "user1";
        String name = "abc";
        UserAccount userAccount = new UserAccount(id, name);
        when(userAccountDAO.readUserAccount(id)).thenReturn(userAccount);

        UserAccount result = userAccountService.getUserAccount(id);

        assertNotNull(result);
    }

    @Test
    void getUserAccount_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> userAccountService.getUserAccount(null));
    }

    @Test
    void getUserAccount_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> userAccountService.getUserAccount(""));
    }

    @Test
    void getAllAssociatedAccounts_validAccountNumber_passesMethodCallVerification() {
        String id = "user1";

        userAccountService.getAllAssociatedAccounts(id);

        verify(userAccountDAO).readAllAssociatedAccounts(id);
    }

    @Test
    void getAllAssociatedAccounts_validAccountNumber_resultNotNull() {
        String id = "user1";
        List<BankAccount> accounts = Arrays.asList(new CheckingAccount(), new SavingsAccount());
        when(userAccountDAO.readAllAssociatedAccounts(id)).thenReturn(accounts);

        List<BankAccount> result = userAccountService.getAllAssociatedAccounts(id);

        assertNotNull(result);
    }

    @Test
    void getAllAssociatedAccounts_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> userAccountService.getAllAssociatedAccounts(null));
    }

    @Test
    void getAllAssociatedAccounts_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> userAccountService.getAllAssociatedAccounts(""));
    }

    @Test
    void updateUserName_validInputs_passesMethodCallVerification() {
        String id = "user1";
        String name = "abc";

        userAccountService.updateUserName(id, name);

        verify(userAccountDAO).updateUserName(id, name);
    }

    @Test
    void updateUserName_nullAccountNumber_throwsException() {
        String name = "abc";
        assertThrows(IllegalArgumentException.class, () -> userAccountService.updateUserName(null, name));
    }

    @Test
    void updateUserName_emptyAccountNumber_ThrowsException() {
        String name = "abc";
        assertThrows(IllegalArgumentException.class, () -> userAccountService.updateUserName("", name));
    }

    @Test
    void updateUserName_nullName_throwsException() {
        String id = "user1";
        assertThrows(IllegalArgumentException.class, () -> userAccountService.updateUserName(id, null));
    }

    @Test
    void updateUserName_emptyName_ThrowsException() {
        String id = "user1";
        assertThrows(IllegalArgumentException.class, () -> userAccountService.updateUserName(id, ""));
    }

    @Test
    void deleteUserAccount_validAccountNumber_passesMethodCallVerification() {
        String id = "user1";

        userAccountService.deleteUserAccount(id);

        verify(userAccountDAO).deleteUserAccount(id);
    }

    @Test
    void deleteUserAccount_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> userAccountService.deleteUserAccount(null));
    }

    @Test
    void deleteUserAccount_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> userAccountService.deleteUserAccount(""));

    }
}