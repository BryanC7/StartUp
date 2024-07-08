package cl.praxis.startup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private int userId;
    private String email;
    private Date createdAt;
    private String nick;
    private String name;
    private String password;
    private int weight;
    private Date updatedAt;

    public UserDTO(String email, Date createdAt, String nick, String name, String password, int weight, Date updatedAt) {
        this.email = email;
        this.createdAt = createdAt;
        this.nick = nick;
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.updatedAt = updatedAt;
    }
}