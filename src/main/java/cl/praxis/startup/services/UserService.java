package cl.praxis.startup.services;

import cl.praxis.startup.model.UserDTO;

import java.util.Optional;

public interface UserService {
    UserDTO selectUser(int id);

    Optional<UserDTO> filterUser(String email, String password);
    Optional<UserDTO> filterUserRegister(String email, String nick);
    UserDTO insertUser(UserDTO user);
}