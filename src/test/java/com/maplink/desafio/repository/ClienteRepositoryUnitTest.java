package com.maplink.desafio.repository;

import com.maplink.desafio.entity.Cliente;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.maplink.desafio.mother.ClienteMother.getCliente;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClienteRepositoryUnitTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void dadoUmClienteQuandoSalvoNoBancoDadoUmaBuscaPorIdEntaoClienteRetornaComSucesso() {
        final Cliente cliente = getCliente();

        Cliente clienteSalvo = clienteRepository.save(cliente);

        Optional<Cliente> clienteRecuperado = clienteRepository.findById(clienteSalvo.getId());

        Assertions.assertThat(clienteRecuperado.isPresent()).isTrue();
        Assertions.assertThat(clienteRecuperado.get().getId()).isEqualTo(clienteSalvo.getId());
    }

    @Test
    public void dadoUmClienteSalvoQuandoAlteradoNoBancoDadoUmaBuscaPorIdEntaoClienteRetornaAlteradoComSucesso() {
        final Cliente cliente = getCliente();
        final String novoNome = "John Alterado";

        Cliente clienteSalvo = clienteRepository.save(cliente);

        Optional<Cliente> clienteRecuperado = clienteRepository.findById(clienteSalvo.getId());

        if (clienteRecuperado.isPresent()) {
            Cliente clienteAlterado = clienteRecuperado.get();
            clienteAlterado.setNome(novoNome);

            clienteRepository.save(clienteAlterado);

            clienteRecuperado = clienteRepository.findById(clienteSalvo.getId());
        }

        Assertions.assertThat(clienteRecuperado.isPresent()).isTrue();
        Assertions.assertThat(clienteRecuperado.get().getId()).isEqualTo(clienteSalvo.getId());
        Assertions.assertThat(clienteRecuperado.get().getNome()).isEqualTo(novoNome);
    }

    @Test
    public void dadoUmClienteSalvoQuandoDeletadoDoBancoDadoUmaBuscaPorIdEntaoNaoRetornaNada() {
        final Cliente cliente = getCliente();

        Cliente clienteSalvo = clienteRepository.save(cliente);

        Optional<Cliente> clienteRecuperado = clienteRepository.findById(clienteSalvo.getId());

        if (clienteRecuperado.isPresent()) {
            clienteRepository.delete(clienteRecuperado.get());

            clienteRecuperado = clienteRepository.findById(clienteSalvo.getId());
        }

        Assertions.assertThat(clienteRecuperado.isPresent()).isFalse();
    }
}
