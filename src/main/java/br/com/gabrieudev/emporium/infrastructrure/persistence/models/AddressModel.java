package br.com.gabrieudev.emporium.infrastructrure.persistence.models;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "addresses")
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "city", nullable = false)
    private String city;
    
    @Column(name = "country", nullable = false)
    private String country;
    
    @Column(name = "postal_code", nullable = false)
    private String postalCode;
    
    @Column(name = "state", nullable = false)
    private String state;
    
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    public static AddressModel from(Address address) {
        return new ModelMapper().map(address, AddressModel.class);
    }

    public void update(Address address) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(address, this);
    }

    public Address toDomainObj() {
        return new ModelMapper().map(this, Address.class);
    }
}
