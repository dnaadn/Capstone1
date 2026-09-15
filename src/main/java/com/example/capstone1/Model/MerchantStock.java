package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty
    private String id;

    @NotEmpty
    private String productid;

    @NotEmpty
    private String merchantid;

    @NotNull
    @Min(value = 10, message = "Stock have to be more than 10")
    private Integer stock;

}
