package cl.praxis.startup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddressDTO {
    private int addressId;
    private String name;
    private String numbering;
    private int userId;
}