package com.maplink.desafio.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplink.desafio.dto.ServicoDto;
import com.maplink.desafio.entity.Servico;
import com.maplink.desafio.repository.ServicoRepository;
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

import java.util.List;
import java.util.Optional;

import static com.maplink.desafio.mother.ServicoMother.getServicoDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ServicoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServicoRepository servicoRepository;

    @Test
    public void dadoUmServicoQuandoSalvoDadoUmaSolicitacaoDeBuscaEntaoServicoEncontradoComSucesso() throws Exception {
        final ServicoDto servicoDto = getServicoDto();

        MockHttpServletResponse response = mockMvc.perform(post("/v1/servicos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(servicoDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        ServicoDto servicoCriado = objectMapper.readValue(response.getContentAsString(), ServicoDto.class);

        Optional<Servico> servicoSalvo = servicoRepository.findById(servicoCriado.getId());

        if (servicoSalvo.isPresent()) {
            Assertions.assertThat(servicoSalvo.get().getCodigo()).isEqualTo(servicoCriado.getCodigo());
        } else {
            Assertions.fail("Servico deveria existir na base de dados.");
        }
    }

    @Test
    public void dadoUmServicoQuandoSalvoDadoUmaSolicitacaoDeAlteracaoEntaoServicoAlteradoComSucesso() throws Exception {
        final ServicoDto servicoDto = getServicoDto();

        MockHttpServletResponse responseCriado = mockMvc.perform(post("/v1/servicos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(servicoDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        ServicoDto servicoCriado = objectMapper.readValue(responseCriado.getContentAsString(), ServicoDto.class);

        Assertions.assertThat(servicoCriado.getCodigo()).isEqualTo(servicoDto.getCodigo());

        servicoCriado.setCodigo("Outro Codigo");

        MockHttpServletResponse responseAlterado = mockMvc.perform(put("/v1/servicos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(servicoCriado)))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        Optional<Servico> servicoSalvo = servicoRepository.findById(servicoCriado.getId());

        if (servicoSalvo.isPresent()) {
            Assertions.assertThat(servicoSalvo.get().getCodigo()).isEqualTo(servicoCriado.getCodigo());
        } else {
            Assertions.fail("Servico deveria existir na base de dados.");
        }
    }

    @Test
    public void dadoUmServicoQuandoSalvoDadoUmaSolicitacaoDeDelecaoEntaoServicoDeletadoComSucesso() throws Exception {
        final ServicoDto servicoDto = getServicoDto();

        MockHttpServletResponse responseCriado = mockMvc.perform(post("/v1/servicos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(servicoDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        ServicoDto servicoCriado = objectMapper.readValue(responseCriado.getContentAsString(), ServicoDto.class);

        mockMvc.perform(delete("/v1/servicos/" + servicoCriado.getId()))
                .andExpect(status().isNoContent());

        Optional<Servico> servicoSalvo = servicoRepository.findById(servicoCriado.getId());

        if (servicoSalvo.isPresent()) {
            Assertions.fail("Servico deveria ter sido deletado na base de dados.");
        }
    }

    @Test
    public void dadoUmServicoQuandoBuscadoDadoUmaSolicitacaoDeBuscaEntaoServicoEncontradoComSucesso() throws Exception {
        final ServicoDto servicoDto = getServicoDto();

        MockHttpServletResponse responseCriado = mockMvc.perform(post("/v1/servicos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(servicoDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        ServicoDto servicoCriado = objectMapper.readValue(responseCriado.getContentAsString(), ServicoDto.class);

        MockHttpServletResponse responseObtido = mockMvc.perform(get("/v1/servicos/" + servicoCriado.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        ServicoDto servicoObtido = objectMapper.readValue(responseObtido.getContentAsString(), ServicoDto.class);

        Assertions.assertThat(servicoObtido).isNotNull();
        Assertions.assertThat(servicoObtido.getId()).isEqualTo(servicoCriado.getId());
    }

    @Test
    public void dadoServicosQuandoBuscadosDadoUmaSolicitacaoDeBuscaPorTodosEntaoServicosEncontradosComSucesso() throws Exception {
        final ServicoDto servicoDto = getServicoDto();
        final ServicoDto servicoDto2 = getServicoDto();

        mockMvc.perform(post("/v1/servicos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(servicoDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/v1/servicos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(servicoDto2)))
                .andExpect(status().isCreated());

        MockHttpServletResponse responseObtido = mockMvc.perform(get("/v1/servicos"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<ServicoDto> servicos = objectMapper.readValue(responseObtido.getContentAsString(), new TypeReference<List<ServicoDto>>() {
        });

        Assertions.assertThat(servicos).isNotNull();
        Assertions.assertThat(servicos.isEmpty()).isFalse();
        Assertions.assertThat(servicos.size()).isEqualTo(2);
    }
}
