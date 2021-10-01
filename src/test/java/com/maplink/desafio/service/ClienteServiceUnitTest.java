package com.maplink.desafio.service;

import com.maplink.desafio.dto.ClienteDto;
import com.maplink.desafio.entity.Cliente;
import com.maplink.desafio.mapper.ClienteMapperImpl;
import com.maplink.desafio.repository.ClienteRepository;
import com.maplink.desafio.service.impl.ClienteServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.maplink.desafio.mother.ClienteMother.getCliente;
import static com.maplink.desafio.mother.ClienteMother.getClienteDto;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith({MockitoExtension.class})
public class ClienteServiceUnitTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Spy
    private ClienteMapperImpl clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Captor
    ArgumentCaptor<Cliente> clienteCaptor;

    @Test
    public void dadoUmClienteQuandoAindaNaoSalvoDadoUmaRequisicaoParaSalvarEntaoClienteSalvoComSucesso() {
        ClienteDto clienteDto = getClienteDto();
        final Cliente cliente = getCliente();
        cliente.setId(1L);

        Mockito.when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        Mockito.when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDto clienteSalvo = clienteService.salvar(clienteDto);

        Assertions.assertThat(clienteSalvo).isNotNull();
        Assertions.assertThat(clienteSalvo.getId()).isEqualTo(cliente.getId());
    }

    @Test
    public void dadoUmClienteSalvoQuandoAlteradoDadoUmaSolicitacaoDeAlteracaoEntaoClienteAtualizadoComSucesso() {
        ClienteDto clienteDto = getClienteDto();
        clienteDto.setId(1L);
        clienteDto.setNome("Novo nome");
        final Cliente cliente = getCliente();
        cliente.setId(1L);

        Mockito.when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.atualizar(clienteDto);

        Mockito.verify(clienteRepository).save(clienteCaptor.capture());
        Cliente clienteAtualizado = clienteCaptor.getValue();

        Assertions.assertThat(clienteAtualizado).isNotNull();
        Assertions.assertThat(clienteAtualizado.getNome()).isEqualTo(clienteDto.getNome());
    }

    @Test
    public void dadoUmClienteSalvoQuandoDeletadoDadoUmaSolicitacaoDeDelecaoEntaoClienteDeletadoComSucesso() {
        final Cliente cliente = getCliente();
        cliente.setId(1L);

        Mockito.when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.deletar(cliente.getId());

        Mockito.verify(clienteRepository).delete(clienteCaptor.capture());
        Cliente clienteDeletado = clienteCaptor.getValue();

        Assertions.assertThat(clienteDeletado).isNotNull();
        Assertions.assertThat(clienteDeletado.getId()).isEqualTo(cliente.getId());
    }

    @Test
    public void dadoUmClienteQuandoSalvoDadoUmaBuscaPorIdEntaoClienteRetornaComSucesso() {
        final Cliente cliente = getCliente();
        cliente.setId(1L);

        Mockito.when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        ClienteDto clienteObtido = clienteService.obter(cliente.getId());

        Assertions.assertThat(clienteObtido).isNotNull();
        Assertions.assertThat(clienteObtido.getId()).isEqualTo(cliente.getId());
    }

    @Test
    public void dadoAlgunsClientesQuandoJaExistentesDadoUmaBuscaPorTodosEntaoClientesRetornamComSucesso() {
        final Cliente cliente1 = getCliente();
        cliente1.setId(1L);
        final Cliente cliente2 = getCliente();
        cliente2.setId(2L);
        final Cliente cliente3 = getCliente();
        cliente3.setId(3L);

        List<Cliente> clientes = new ArrayList<>();

        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);

        Mockito.when(clienteRepository.findAll()).thenReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.obterTodos();

        Assertions.assertThat(clienteDtos).isNotNull();
        Assertions.assertThat(clienteDtos.isEmpty()).isFalse();
        Assertions.assertThat(clienteDtos.size()).isEqualTo(3);
        Assertions.assertThat(clienteDtos.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(clienteDtos.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(clienteDtos.get(2).getId()).isEqualTo(3);
    }
}
