package servlet;

import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService;

    public RegistrationServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = PageGenerator.createPageVariablesMap(req);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().println(PageGenerator.getInstance().getPage("registerPage.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User testUser = new User(email, password);

        if (email == null || password == null) {
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().println("Вы не ввели логин или пароль");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;

        } else if (userService.addUser(testUser)) {
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().println("Поздравляю с регистрацией, " + email);
            resp.getWriter().println("Список зарегистрированных " + userService.getAllUsers().toString());
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        } else {
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().println("Ошибка регистрации, " + email);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
