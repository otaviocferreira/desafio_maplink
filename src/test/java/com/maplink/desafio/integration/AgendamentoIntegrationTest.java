package com.maplink.desafio.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplink.desafio.dto.AgendamentoDto;
import com.maplink.desafio.dto.AgendamentoRemarcacaoDto;
import com.maplink.desafio.entity.Agendamento;
import com.maplink.desafio.repository.AgendamentoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.maplink.desafio.mother.AgendamentoMother.getAgendamentoDto;
import static com.maplink.desafio.mother.AgendamentoMother.getAgendamentoRemarcacaoDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AgendamentoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Test
    public void dadoUmAgendamentoQuandoSalvoDadoUmaSolicitacaoDeBuscaEntaoAgendamentoEncontradoComSucesso() throws Exception {
        final AgendamentoDto agendamentoDto = getAgendamentoDto();

        MockHttpServletResponse response = mockMvc.perform(post("/v1/agendamentos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(agendamentoDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        AgendamentoDto agendamentoCriado = objectMapper.readValue(response.getContentAsString(), AgendamentoDto.class);

        Optional<Agendamento> agendamentoSalvo = agendamentoRepository.findById(agendamentoCriado.getId());

        if (agendamentoSalvo.isPresent()) {
            Assertions.assertThat(agendamentoSalvo.get().getObservacao()).isEqualTo(agendamentoCriado.getObservacao());
        } else {
            Assertions.fail("Agendamento deveria existir na base de dados.");
        }
    }

    @Test
    public void dadoUmAgendamentoQuandoRemarcadoDadoUmaSolicitacaoDeRemarcacaoEntaoAgendamentoRemarcadoComSucesso() throws Exception {
        final AgendamentoDto agendamentoDto = getAgendamentoDto();
        final AgendamentoRemarcacaoDto agendamentoRemarcacaoDto = getAgendamentoRemarcacaoDto();

        MockHttpServletResponse responseCriado = mockMvc.perform(post("/v1/agendamentos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(agendamentoDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        AgendamentoDto agendamentoCriado = objectMapper.readValue(responseCriado.getContentAsString(), AgendamentoDto.class);

        LocalDateTime novaData = agendamentoCriado.getDataHora().plusDays(1);

        agendamentoRemarcacaoDto.setId(agendamentoCriado.getId());
        agendamentoRemarcacaoDto.setDataHora(novaData);

        MockHttpServletResponse responseRemarcado = mockMvc.perform(put("/v1/agendamentos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(agendamentoRemarcacaoDto)))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        Optional<Agendamento> agendamentoRemarcado = agendamentoRepository.findById(agendamentoCriado.getId());

        if (agendamentoRemarcado.isPresent()) {
            Assertions.assertThat(agendamentoRemarcado.get().getDataHora()).isAfter(agendamentoCriado.getDataHora());
        } else {
            Assertions.fail("Agendamento deveria existir na base de dados.");
        }
    }

    @Test
    public void dadoAgendamentosQuandoBuscadosDadoUmaSolicitacaoDeBuscaPorTodosEntaoAgendamentosEncontradosComSucesso() throws Exception {
        final AgendamentoDto agendamentoDto = getAgendamentoDto();
        final AgendamentoDto agendamentoDto2 = getAgendamentoDto();

        mockMvc.perform(post("/v1/agendamentos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(agendamentoDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/v1/agendamentos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(agendamentoDto2)))
                .andExpect(status().isCreated());

        MockHttpServletResponse responseObtido = mockMvc.perform(get("/v1/agendamentos"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<AgendamentoDto> agendamentos = objectMapper.readValue(responseObtido.getContentAsString(), new TypeReference<List<AgendamentoDto>>() {
        });

        Assertions.assertThat(agendamentos).isNotNull();
        Assertions.assertThat(agendamentos.isEmpty()).isFalse();
        Assertions.assertThat(agendamentos.size()).isEqualTo(2);
    }
}
