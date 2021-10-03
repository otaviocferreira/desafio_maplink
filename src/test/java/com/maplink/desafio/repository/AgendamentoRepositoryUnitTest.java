package com.maplink.desafio.repository;

import com.maplink.desafio.entity.Agendamento;
import com.maplink.desafio.entity.Cliente;
import com.maplink.desafio.entity.Servico;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.maplink.desafio.mother.AgendamentoMother.getAgendamento;
import static com.maplink.desafio.mother.ClienteMother.getCliente;
import static com.maplink.desafio.mother.ServicoMother.getServico;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AgendamentoRepositoryUnitTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Test
    public void dadoUmServicoQuandoSalvoNoBancoDadoUmaBuscaPorIdEntaoServicoRetornaComSucesso() {
        final Cliente cliente = getCliente();
        final Servico servico = getServico();
        final Agendamento agendamento = getAgendamento();

        Cliente clienteSalvo = clienteRepository.save(cliente);
        Servico servicoSalvo = servicoRepository.save(servico);

        agendamento.setCliente(clienteSalvo);
        agendamento.setServico(servicoSalvo);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        Optional<Agendamento> agendamentoRecuperado = agendamentoRepository.findById(agendamentoSalvo.getId());

        Assertions.assertThat(agendamentoRecuperado.isPresent()).isTrue();
        Assertions.assertThat(agendamentoRecuperado.get().getId()).isEqualTo(agendamentoSalvo.getId());
        Assertions.assertThat(agendamentoRecuperado.get().getCliente().getId()).isEqualTo(agendamentoSalvo.getCliente().getId());
        Assertions.assertThat(agendamentoRecuperado.get().getServico().getId()).isEqualTo(agendamentoSalvo.getServico().getId());
    }

    @Test
    public void dadoUmServicoSalvoQuandoAlteradoNoBancoDadoUmaBuscaPorIdEntaoAgendamentoRetornaAlteradoComSucesso() throws InterruptedException {
        final Cliente cliente = getCliente();
        final Servico servico = getServico();

        LocalDateTime now = LocalDateTime.now();
        final Agendamento agendamento = getAgendamento();

        TimeUnit.MILLISECONDS.sleep(1000);

        Cliente clienteSalvo = clienteRepository.save(cliente);
        Servico servicoSalvo = servicoRepository.save(servico);

        agendamento.setCliente(clienteSalvo);
        agendamento.setServico(servicoSalvo);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        Optional<Agendamento> agendamentoRecuperado = agendamentoRepository.findById(agendamentoSalvo.getId());

        if (agendamentoRecuperado.isPresent()) {
            Agendamento agendamentoAlterado = agendamentoRecuperado.get();
            agendamentoAlterado.setDataHora(LocalDateTime.now());

            agendamentoRepository.save(agendamentoAlterado);

            agendamentoRecuperado = agendamentoRepository.findById(agendamentoAlterado.getId());
        }

        Assertions.assertThat(agendamentoRecuperado.isPresent()).isTrue();
        Assertions.assertThat(agendamentoRecuperado.get().getId()).isEqualTo(agendamentoSalvo.getId());
        Assertions.assertThat(agendamentoRecuperado.get().getDataHora()).isAfter(now);
    }

    @Test
    public void dadoUmServicoSalvoQuandoDeletadoDoBancoDadoUmaBuscaPorIdEntaoNaoRetornaNada() {
        Cliente cliente = getCliente();
        Servico servico = getServico();
        Agendamento agendamento = getAgendamento();

        Cliente clienteSalvo = clienteRepository.save(cliente);
        Servico servicoSalvo = servicoRepository.save(servico);

        agendamento.setCliente(clienteSalvo);
        agendamento.setServico(servicoSalvo);

        agendamentoRepository.save(agendamento);

        cliente = getCliente();
        servico = getServico();
        agendamento = getAgendamento();

        clienteSalvo = clienteRepository.save(cliente);
        servicoSalvo = servicoRepository.save(servico);

        agendamento.setCliente(clienteSalvo);
        agendamento.setServico(servicoSalvo);

        agendamentoRepository.save(agendamento);

        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        Assertions.assertThat(agendamentos).isNotNull();
        Assertions.assertThat(agendamentos.isEmpty()).isFalse();
        Assertions.assertThat(agendamentos.size()).isEqualTo(2);
    }
}
