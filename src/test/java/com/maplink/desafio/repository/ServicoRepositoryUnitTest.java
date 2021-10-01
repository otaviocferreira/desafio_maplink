package com.maplink.desafio.repository;

import com.maplink.desafio.entity.Servico;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.maplink.desafio.mother.ServicoMother.getServico;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ServicoRepositoryUnitTest {

    @Autowired
    private ServicoRepository servicoRepository;

    @Test
    public void dadoUmServicoQuandoSalvoNoBancoDadoUmaBuscaPorIdEntaoServicoRetornaComSucesso() {
        final Servico servico = getServico();

        Servico servicoSalvo = servicoRepository.save(servico);

        Optional<Servico> servicoRecuperado = servicoRepository.findById(servicoSalvo.getId());

        Assertions.assertThat(servicoRecuperado.isPresent()).isTrue();
        Assertions.assertThat(servicoRecuperado.get().getId()).isEqualTo(servicoSalvo.getId());
    }

    @Test
    public void dadoUmServicoSalvoQuandoAlteradoNoBancoDadoUmaBuscaPorIdEntaoServicoRetornaAlteradoComSucesso() {
        final Servico servico = getServico();
        final String novoNome = "Servico Alterado";

        Servico servicoSalvo = servicoRepository.save(servico);

        Optional<Servico> servicoRecuperado = servicoRepository.findById(servicoSalvo.getId());

        if (servicoRecuperado.isPresent()) {
            Servico servicoAlterado = servicoRecuperado.get();
            servicoAlterado.setDescricao(novoNome);

            servicoRepository.save(servicoAlterado);

            servicoRecuperado = servicoRepository.findById(servicoSalvo.getId());
        }

        Assertions.assertThat(servicoRecuperado.isPresent()).isTrue();
        Assertions.assertThat(servicoRecuperado.get().getId()).isEqualTo(servicoSalvo.getId());
        Assertions.assertThat(servicoRecuperado.get().getDescricao()).isEqualTo(novoNome);
    }

    @Test
    public void dadoUmServicoSalvoQuandoDeletadoDoBancoDadoUmaBuscaPorIdEntaoNaoRetornaNada() {
        final Servico servico = getServico();

        Servico servicoSalvo = servicoRepository.save(servico);

        Optional<Servico> servicoRecuperado = servicoRepository.findById(servicoSalvo.getId());

        if (servicoRecuperado.isPresent()) {
            servicoRepository.delete(servicoRecuperado.get());

            servicoRecuperado = servicoRepository.findById(servicoSalvo.getId());
        }

        Assertions.assertThat(servicoRecuperado.isPresent()).isFalse();
    }
}
