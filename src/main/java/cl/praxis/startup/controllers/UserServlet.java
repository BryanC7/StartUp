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
import java.util.Date;
import java.util.Optional;

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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<UserDTO> userFound = objUserService.filterUser(email, password);

        if(userFound.isPresent()) {
            request.setAttribute("user", userFound.get());
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            printMessage("msgError",
                    "login.jsp",
                    "Usuario no encontrado, intente nuevamente",
                    request, response);
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

        Optional<UserDTO> userFound = objUserService.filterUserRegister(email, nick);

        if(userFound.isEmpty()) {
            UserDTO newUser = new UserDTO(email, createdAt, nick, name, password, weight, updatedAt);
            objUserService.insertUser(newUser);
            printMessage("msgSuccess",
                    "login.jsp",
                    "Usuario registrado exitósamente",
                    request, response);
        } else {
            printMessage("msgError",
                        "register.jsp",
                        "Correo y/o apodo ya registrados, intenta con uno nuevo",
                        request, response);
        }
    }

    private void printMessage(String attribute, String dispatcher, String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(attribute, message);
        request.getRequestDispatcher(dispatcher).forward(request, response);
    }
}