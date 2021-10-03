package com.maplink.desafio.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplink.desafio.dto.ClienteDto;
import com.maplink.desafio.entity.Cliente;
import com.maplink.desafio.repository.ClienteRepository;
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

import static com.maplink.desafio.mother.ClienteMother.getClienteDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void dadoUmClienteQuandoSalvoDadoUmaSolicitacaoDeBuscaEntaoClienteEncontradoComSucesso() throws Exception {
        final ClienteDto clienteDto = getClienteDto();

        MockHttpServletResponse response = mockMvc.perform(post("/v1/clientes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        ClienteDto clienteCriado = objectMapper.readValue(response.getContentAsString(), ClienteDto.class);

        Optional<Cliente> clienteSalvo = clienteRepository.findById(clienteCriado.getId());

        if (clienteSalvo.isPresent()) {
            Assertions.assertThat(clienteSalvo.get().getNome()).isEqualTo(clienteCriado.getNome());
        } else {
            Assertions.fail("Cliente deveria existir na base de dados.");
        }
    }

    @Test
    public void dadoUmClienteQuandoSalvoDadoUmaSolicitacaoDeAlteracaoEntaoClienteAlteradoComSucesso() throws Exception {
        final ClienteDto clienteDto = getClienteDto();

        MockHttpServletResponse responseCriado = mockMvc.perform(post("/v1/clientes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        ClienteDto clienteCriado = objectMapper.readValue(responseCriado.getContentAsString(), ClienteDto.class);

        Assertions.assertThat(clienteCriado.getNome()).isEqualTo(clienteDto.getNome());

        clienteCriado.setNome("Outro Nome");

        MockHttpServletResponse responseAlterado = mockMvc.perform(put("/v1/clientes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clienteCriado)))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        Optional<Cliente> clienteSalvo = clienteRepository.findById(clienteCriado.getId());

        if (clienteSalvo.isPresent()) {
            Assertions.assertThat(clienteSalvo.get().getNome()).isEqualTo(clienteCriado.getNome());
        } else {
            Assertions.fail("Cliente deveria existir na base de dados.");
        }
    }

    @Test
    public void dadoUmClienteQuandoSalvoDadoUmaSolicitacaoDeDelecaoEntaoClienteDeletadoComSucesso() throws Exception {
        final ClienteDto clienteDto = getClienteDto();

        MockHttpServletResponse responseCriado = mockMvc.perform(post("/v1/clientes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        ClienteDto clienteCriado = objectMapper.readValue(responseCriado.getContentAsString(), ClienteDto.class);

        mockMvc.perform(delete("/v1/clientes/" + clienteCriado.getId()))
                .andExpect(status().isNoContent());

        Optional<Cliente> clienteSalvo = clienteRepository.findById(clienteCriado.getId());

        if (clienteSalvo.isPresent()) {
            Assertions.fail("Cliente deveria ter sido deletado na base de dados.");
        }
    }

    @Test
    public void dadoUmClienteQuandoBuscadoDadoUmaSolicitacaoDeBuscaEntaoClienteEncontradoComSucesso() throws Exception {
        final ClienteDto clienteDto = getClienteDto();

        MockHttpServletResponse responseCriado = mockMvc.perform(post("/v1/clientes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        ClienteDto clienteCriado = objectMapper.readValue(responseCriado.getContentAsString(), ClienteDto.class);

        MockHttpServletResponse responseObtido = mockMvc.perform(get("/v1/clientes/" + clienteCriado.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        ClienteDto clienteObtido = objectMapper.readValue(responseObtido.getContentAsString(), ClienteDto.class);

        Assertions.assertThat(clienteObtido).isNotNull();
        Assertions.assertThat(clienteObtido.getId()).isEqualTo(clienteCriado.getId());
    }

    @Test
    public void dadoClientesQuandoBuscadosDadoUmaSolicitacaoDeBuscaPorTodosEntaoClientesEncontradosComSucesso() throws Exception {
        final ClienteDto clienteDto = getClienteDto();
        final ClienteDto clienteDto2 = getClienteDto();

        mockMvc.perform(post("/v1/clientes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/v1/clientes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(clienteDto2)))
                .andExpect(status().isCreated());

        MockHttpServletResponse responseObtido = mockMvc.perform(get("/v1/clientes"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<ClienteDto> clientes = objectMapper.readValue(responseObtido.getContentAsString(), new TypeReference<List<ClienteDto>>() {
        });

        Assertions.assertThat(clientes).isNotNull();
        Assertions.assertThat(clientes.isEmpty()).isFalse();
        Assertions.assertThat(clientes.size()).isEqualTo(2);
    }
}
