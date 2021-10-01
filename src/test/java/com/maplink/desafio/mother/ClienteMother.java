package com.maplink.desafio.mother;

import com.maplink.desafio.dto.ClienteDto;
import com.maplink.desafio.entity.Cliente;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMother {

    public static Cliente getCliente() {
        return Cliente.builder()
                .nome("John Doe")
                .cpf("12345678909")
                .build();
    }

    public static ClienteDto getClienteDto() {
        return ClienteDto.builder()
                .nome("John Doe")
                .cpf("12345678909")
                .build();
    }
}
