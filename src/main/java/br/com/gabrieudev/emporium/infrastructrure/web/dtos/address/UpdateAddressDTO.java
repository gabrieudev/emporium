package br.com.gabrieudev.emporium.infrastructrure.web.dtos.address;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.emporium.domain.entities.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressDTO {
    @Schema(
        description = "Identificador do endereço",
        example = "9a8a2c83-5c8e-4e5b-9b4e-2c83a5c8e5b4",
        required = true
    )
    @NotNull(message = "Identificador obrigatório")
    private UUID id;

    @Schema(
        description = "Nome do endereço",
        example = "Casa",
        required = true
    )
    @NotBlank(message = "Nome obrigatório")
    private String name;
    
    @Schema(
        description = "Cidade",
        example = "Rio de Janeiro",
        required = true
    )
    @NotBlank(message = "Cidade obrigatória")
    private String city;
    
    @Schema(
        description = "Pais",
        example = "Brasil",
        required = true
    )
    @NotBlank(message = "Pais obrigatório")
    private String country;
    
    @Schema(
        description = "CEP",
        example = "12345-678",
        required = true
    )
    @NotBlank(message = "CEP obrigatório")
    private String postalCode;
    
    @Schema(
        description = "Estado",
        example = "RJ",
        required = true
    )
    @NotBlank(message = "Estado obrigatório")
    private String state;
    
    @Schema(
        description = "Endereço principal",
        example = "true",
        required = true
    )
    @NotNull(message = "Endereço principal obrigatório")
    private Boolean isDefault;

    public Address toDomainObj() {
        return new ModelMapper().map(this, Address.class);
    }
}
