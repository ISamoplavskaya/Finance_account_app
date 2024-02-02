package servlet.accounts;

import entity.Account;
import entity.Authorization;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.AuthorizationService;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/accounts")
public class Accounts extends HttpServlet {
   private final UserService userService=new UserService();
   private final AuthorizationService authorizationService=new AuthorizationService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("name");
        Authorization authorizationByUsername = authorizationService.findAuthorizationByUsername(username);
        User user = authorizationByUsername.getUser();
        List<Account> allAccountsByUser = userService.getAllAccountsByUser(user.getId());
        req.setAttribute("listAccounts", allAccountsByUser);
        req.getRequestDispatcher("accounts/accounts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
