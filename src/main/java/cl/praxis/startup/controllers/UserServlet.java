package cl.praxis.startup.controllers;

import cl.praxis.startup.model.UserDTO;
import cl.praxis.startup.services.UserService;
import cl.praxis.startup.services.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {
    private UserService objUserService;

    public void init() {
        objUserService = new UserServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }

        switch (action) {
            case "login":
                loginUser(request, response);
                break;
            case "register":
                registerView(request, response);
                break;
            case "insert":
                registerUser(request, response);
                break;
            default:
                loginView(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void loginView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("name");
        String password = request.getParameter("password");

        UserDTO userFound = objUserService.filterUser(email, password);

        if(userFound != null) {
            request.setAttribute("user", userFound);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            String msgError = "Usuario no encontrado, intente nuevamente";
            request.setAttribute("msgError", msgError);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void registerView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        Date createdAt = new Date();
        String nick = request.getParameter("nick");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        int weight = Integer.parseInt(request.getParameter("weight"));
        Date updatedAt = new Date();

        UserDTO newUser = new UserDTO(email, createdAt, nick, name, password, weight, updatedAt);
        objUserService.insertUser(newUser);
        response.sendRedirect("user");
    }
}