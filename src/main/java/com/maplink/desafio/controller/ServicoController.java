package com.maplink.desafio.controller;

import com.maplink.desafio.dto.ServicoDto;
import com.maplink.desafio.service.ServicoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Servicos"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    @ApiOperation("Obtem um servico através do seu ID")
    @GetMapping("/{id}")
    public ServicoDto obter(@PathVariable Long id) {
        return servicoService.obter(id);
    }

    @ApiOperation("Obtem todos os servicos")
    @GetMapping
    public List<ServicoDto> obterTodos() {
        return servicoService.obterTodos();
    }

    @ApiOperation("Salva um novo servico")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoDto salvar(@RequestBody ServicoDto servicoDto) {
        return servicoService.salvar(servicoDto);
    }

    @ApiOperation("Atualiza um servico")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@RequestBody ServicoDto servicoDto) {
        servicoService.atualizar(servicoDto);
    }

    @ApiOperation("Deleta um servico através do seu ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleter(@PathVariable Long id) {
        servicoService.deletar(id);
    }
}
