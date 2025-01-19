package br.com.gabrieudev.emporium.infrastructrure.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLinkReponse {
    private String url;
}
