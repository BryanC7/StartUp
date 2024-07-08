package cl.praxis.startup.services.impl;

import cl.praxis.startup.dao.UserDAO;
import cl.praxis.startup.dao.impl.UserDAOImpl;
import cl.praxis.startup.model.UserDTO;
import cl.praxis.startup.services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDAO OBJ_USER_DAO;

    public UserServiceImpl() {
        this.OBJ_USER_DAO = new UserDAOImpl();
    }

    @Override
    public UserDTO selectUser(int id) { return OBJ_USER_DAO.selectUser(id); }

    @Override
    public UserDTO filterUser(String email, String password) {
        UserDTO userFound = null;
        List<UserDTO> users = OBJ_USER_DAO.selectAllUsers();

        for (UserDTO user : users) {
            if(user.getEmail().equals(email)) {
                if(user.getPassword().equals(password)) {
                    System.out.println(userFound);
                    userFound = user;
                }
            }
        }

        return userFound;
    }

    @Override
    public UserDTO insertUser(UserDTO user) {
        return OBJ_USER_DAO.insertUser(user);
    }
}