package com.maplink.desafio.controller;

import com.maplink.desafio.dto.AgendamentoDto;
import com.maplink.desafio.dto.AgendamentoRemarcacaoDto;
import com.maplink.desafio.service.AgendamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Agendamentos"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @ApiOperation("Obtem todos os agendamentos")
    @GetMapping
    public List<AgendamentoDto> obterTodos() {
        return agendamentoService.obterTodos();
    }

    @ApiOperation("Salva um novo agendamento")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendamentoDto salvar(@RequestBody AgendamentoDto servicoDto) {
        return agendamentoService.salvar(servicoDto);
    }

    @ApiOperation("Remarca um agendamento")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remarcar(@RequestBody AgendamentoRemarcacaoDto agendamentoRemarcacaoDto) {
        agendamentoService.remarcar(agendamentoRemarcacaoDto);
    }
}
