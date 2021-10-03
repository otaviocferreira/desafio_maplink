package com.maplink.desafio.controller;

import com.maplink.desafio.dto.ClienteDto;
import com.maplink.desafio.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Clientes"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @ApiOperation("Obtem um cliente através do seu ID")
    @GetMapping("/{id}")
    public ClienteDto obter(@PathVariable Long id) {
        return clienteService.obter(id);
    }

    @ApiOperation("Obtem todos os clientes")
    @GetMapping
    public List<ClienteDto> obterTodos() {
        return clienteService.obterTodos();
    }

    @ApiOperation("Salva um novo cliente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDto salvar(@RequestBody ClienteDto clienteDto) {
        return clienteService.salvar(clienteDto);
    }

    @ApiOperation("Atualiza um cliente")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@RequestBody ClienteDto clienteDto) {
        clienteService.atualizar(clienteDto);
    }

    @ApiOperation("Deleta um cliente através do seu ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleter(@PathVariable Long id) {
        clienteService.deletar(id);
    }
}
