package servlet.operations;

import entity.Account;
import entity.Authorization;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.AccountService;
import service.AuthorizationService;
import service.UserService;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/addAccount")
public class AddAccount extends HttpServlet {
    private final AccountService accountService=new AccountService();
    private final UserService userService=new UserService();
    private final AuthorizationService authorizationService=new AuthorizationService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("accounts/addAccount.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("name");
        Authorization authorizationByUsername = authorizationService.findAuthorizationByUsername(username);
        User user = authorizationByUsername.getUser();
        String accountName = request.getParameter("accountName");
        String cardNumber = request.getParameter("cardNumber");
        BigDecimal balance = new BigDecimal((request.getParameter("balance")));
        Account account=Account.builder()
                .accountName(accountName)
                .cardNumber(cardNumber)
                .balance(balance)
                .build();

            if (!accountService.isExistWithCardNumber(account)) {
                accountService.saveAccount(account);
                Account accountFromDB = accountService.findAccountByID(account.getId());
                userService.addAccountToUser(user.getId(),accountFromDB);

                response.sendRedirect("/accounts");
            } else {
                request.setAttribute("already_exist", "Account is already exist!");
                request.getRequestDispatcher("accounts/addAccount.jsp").forward(request, response);
            }
    }
}

