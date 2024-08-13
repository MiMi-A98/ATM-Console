package MiMiA98.atm.service;

import MiMiA98.atm.dao.UserAccountDAO;
import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.UserAccount;

import java.util.List;

public class UserAccountService {

    private final UserAccountDAO userAccountDAO;

    public UserAccountService() {
        this.userAccountDAO = new UserAccountDAO();
    }

    public UserAccountService(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    public void createUserAccount(String id, String userName) {
        if (id == null || id.isEmpty() || userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("ID and username cannot be null or empty");
        }
        userAccountDAO.createUserAccount(id, userName);
    }

    public UserAccount getUserAccount(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return userAccountDAO.readUserAccount(id);
    }

    public List<BankAccount> getAllAssociatedAccounts(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return userAccountDAO.readAllAssociatedAccounts(id);
    }

    public void updateUserName(String id, String newUserName) {
        if (id == null || id.isEmpty() || newUserName == null || newUserName.isEmpty()) {
            throw new IllegalArgumentException("ID and new username cannot be null or empty");
        }
        userAccountDAO.updateUserName(id, newUserName);
    }

    public void deleteUserAccount(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        userAccountDAO.deleteUserAccount(id);
    }
}
