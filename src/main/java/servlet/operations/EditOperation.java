package servlet.operations;

import entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountService;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/editAccount")
public class EditAccount extends HttpServlet {
    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = Long.parseLong(req.getParameter("id"));
        Account accountByID = accountService.findAccountByID(accountId);
        if (accountByID != null) {
            req.setAttribute("account", accountByID);
        }
        req.getRequestDispatcher("accounts/editAccount.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = Long.parseLong(req.getParameter("accountId"));
        String accountName = req.getParameter("accountName");
        String cardNumber = req.getParameter("cardNumber");
        BigDecimal balance = new BigDecimal((req.getParameter("balance")));

        Account editedAccount = accountService.findAccountByID(accountId);
        editedAccount.setAccountName(accountName);
        editedAccount.setCardNumber(cardNumber);
        editedAccount.setBalance(balance);
        accountService.updateAccount(editedAccount);
        resp.sendRedirect(req.getContextPath() + "/accounts");
    }
}


