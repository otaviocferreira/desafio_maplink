package com.maplink.desafio.service;

import com.maplink.desafio.dto.ServicoDto;
import com.maplink.desafio.entity.Servico;
import com.maplink.desafio.mapper.ServicoMapperImpl;
import com.maplink.desafio.repository.ServicoRepository;
import com.maplink.desafio.service.impl.ServicoServiceImpl;
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

import static com.maplink.desafio.mother.ServicoMother.getServico;
import static com.maplink.desafio.mother.ServicoMother.getServicoDto;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith({MockitoExtension.class})
public class ServicoServiceUnitTest {

    @Mock
    private ServicoRepository servicoRepository;

    @Spy
    private ServicoMapperImpl servicoMapper;

    @InjectMocks
    private ServicoServiceImpl servicoService;

    @Captor
    ArgumentCaptor<Servico> servicoCaptor;

    @Test
    public void dadoUmServicoQuandoAindaNaoSalvoDadoUmaRequisicaoParaSalvarEntaoServicoSalvoComSucesso() {
        ServicoDto servicoDto = getServicoDto();
        final Servico servico = getServico();
        servico.setId(1L);

        Mockito.when(servicoRepository.findById(servico.getId())).thenReturn(Optional.of(servico));
        Mockito.when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        ServicoDto servicoSalvo = servicoService.salvar(servicoDto);

        Assertions.assertThat(servicoSalvo).isNotNull();
        Assertions.assertThat(servicoSalvo.getId()).isEqualTo(servico.getId());
    }

    @Test
    public void dadoUmServicoSalvoQuandoAlteradoDadoUmaSolicitacaoDeAlteracaoEntaoServicoAtualizadoComSucesso() {
        ServicoDto servicoDto = getServicoDto();
        servicoDto.setId(1L);
        servicoDto.setDescricao("Nova descricao");
        final Servico servico = getServico();
        servico.setId(1L);

        Mockito.when(servicoRepository.findById(servico.getId())).thenReturn(Optional.of(servico));

        servicoService.atualizar(servicoDto);

        Mockito.verify(servicoRepository).save(servicoCaptor.capture());
        Servico servicoAtualizado = servicoCaptor.getValue();

        Assertions.assertThat(servicoAtualizado).isNotNull();
        Assertions.assertThat(servicoAtualizado.getDescricao()).isEqualTo(servicoDto.getDescricao());
    }

    @Test
    public void dadoUmServicoSalvoQuandoDeletadoDadoUmaSolicitacaoDeDelecaoEntaoServicoDeletadoComSucesso() {
        final Servico servico = getServico();
        servico.setId(1L);

        Mockito.when(servicoRepository.findById(servico.getId())).thenReturn(Optional.of(servico));

        servicoService.deletar(servico.getId());

        Mockito.verify(servicoRepository).delete(servicoCaptor.capture());
        Servico servicoDeletado = servicoCaptor.getValue();

        Assertions.assertThat(servicoDeletado).isNotNull();
        Assertions.assertThat(servicoDeletado.getId()).isEqualTo(servico.getId());
    }

    @Test
    public void dadoUmServicoQuandoSalvoDadoUmaBuscaPorIdEntaoServicoRetornaComSucesso() {
        final Servico servico = getServico();
        servico.setId(1L);

        Mockito.when(servicoRepository.findById(servico.getId())).thenReturn(Optional.of(servico));

        ServicoDto servicoObtido = servicoService.obter(servico.getId());

        Assertions.assertThat(servicoObtido).isNotNull();
        Assertions.assertThat(servicoObtido.getId()).isEqualTo(servico.getId());
    }

    @Test
    public void dadoAlgunsServicosQuandoJaExistentesDadoUmaBuscaPorTodosEntaoServicosRetornamComSucesso() {
        final Servico servico1 = getServico();
        servico1.setId(1L);
        final Servico servico2 = getServico();
        servico2.setId(2L);
        final Servico servico3 = getServico();
        servico3.setId(3L);

        List<Servico> servicos = new ArrayList<>();

        servicos.add(servico1);
        servicos.add(servico2);
        servicos.add(servico3);

        Mockito.when(servicoRepository.findAll()).thenReturn(servicos);

        List<ServicoDto> servicoDtos = servicoService.obterTodos();

        Assertions.assertThat(servicoDtos).isNotNull();
        Assertions.assertThat(servicoDtos.isEmpty()).isFalse();
        Assertions.assertThat(servicoDtos.size()).isEqualTo(3);
        Assertions.assertThat(servicoDtos.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(servicoDtos.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(servicoDtos.get(2).getId()).isEqualTo(3);
    }
}
