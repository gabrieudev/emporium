package br.com.gabrieudev.emporium.infrastructrure.web.dtos.address;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Address;
import br.com.gabrieudev.emporium.infrastructrure.web.dtos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private UUID id;
    private UserDTO user;
    private String name;
    private String city;
    private String country;
    private String postalCode;
    private String state;
    private Boolean isDefault;

    public static AddressDTO from(Address address) {
        return new ModelMapper().map(address, AddressDTO.class);
    }

    public Address toDomainObj() {
        return new ModelMapper().map(this, Address.class);
    }
}
