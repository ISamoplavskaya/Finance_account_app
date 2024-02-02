package servlet.operations;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountService;

import java.io.IOException;

@WebServlet("/remove")
public class RemoveAccount extends HttpServlet {

    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        accountService.deleteAccount(accountService.findAccountByID(id));
        response.sendRedirect(request.getContextPath() + "/accounts");
    }
}