package cl.praxis.startup.services;

import cl.praxis.startup.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO selectUser(int id);

    UserDTO filterUser(String email, String password);

    UserDTO insertUser(UserDTO user);
}