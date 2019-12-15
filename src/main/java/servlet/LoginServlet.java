package servlet;

import com.google.gson.Gson;
import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService;

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("email");
        String password = req.getParameter("password");
        User testUser = new User(login, password);

        if (login.equals("") || password.equals("")) {
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().println("Вы не ввели логин или пароль!");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        else if(userService.authUser(testUser)){
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().println("Вы успешно авторизованы!");
            resp.getWriter().println("Список зарегистрированных " + userService.getAllUsers().toString());
            resp.getWriter().println("Список авторизованных" + userService.getAllAuth());
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        } else{
            resp.setContentType("text/html; charset=utf-8");

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = PageGenerator.createPageVariablesMap(req);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().println(PageGenerator.getInstance().getPage("authPage.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}