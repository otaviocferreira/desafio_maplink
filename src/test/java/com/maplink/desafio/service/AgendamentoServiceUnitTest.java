package com.maplink.desafio.service;

import com.maplink.desafio.dto.AgendamentoDto;
import com.maplink.desafio.dto.AgendamentoRemarcacaoDto;
import com.maplink.desafio.entity.Agendamento;
import com.maplink.desafio.mapper.AgendamentoMapper;
import com.maplink.desafio.mapper.AgendamentoMapperImpl;
import com.maplink.desafio.repository.AgendamentoRepository;
import com.maplink.desafio.service.impl.AgendamentoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.maplink.desafio.mother.AgendamentoMother.*;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith({MockitoExtension.class})
public class AgendamentoServiceUnitTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Spy
    private AgendamentoMapperImpl agendamentoMapper;

    @InjectMocks
    private AgendamentoServiceImpl agendamentoService;

    @Captor
    ArgumentCaptor<Agendamento> agendamentoCaptor;

    @Test
    public void dadoUmAgendamentoQuandoAindaNaoSalvoDadoUmaRequisicaoParaSalvarEntaoAgendamentoSalvoComSucesso() {
        AgendamentoDto agendamentoDto = getAgendamentoDto();
        final Agendamento agendamento = getAgendamento();
        agendamento.setId(1L);

        Mockito.when(agendamentoRepository.findById(agendamento.getId())).thenReturn(Optional.of(agendamento));
        Mockito.when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        AgendamentoDto agendamentoSalvo = agendamentoService.salvar(agendamentoDto);

        Assertions.assertThat(agendamentoSalvo).isNotNull();
        Assertions.assertThat(agendamentoSalvo.getId()).isEqualTo(agendamento.getId());
    }

    @Test
    public void dadoUmAgendamentoSalvoQuandoRemarcadoDadoUmaSolicitacaoDeRemarcacaoEntaoAgendamentoRemarcadoComSucesso() throws InterruptedException {
        final Agendamento agendamento = getAgendamento();
        agendamento.setId(1L);
        LocalDateTime now = LocalDateTime.now();

        TimeUnit.MILLISECONDS.sleep(1000);

        AgendamentoRemarcacaoDto agendamentoDto = getAgendamentoRemarcacaoDto();
        agendamentoDto.setId(1L);

        Mockito.when(agendamentoRepository.findById(agendamento.getId())).thenReturn(Optional.of(agendamento));

        agendamentoService.remarcar(agendamentoDto);

        Mockito.verify(agendamentoRepository).save(agendamentoCaptor.capture());
        Agendamento agendamentoRemarcado = agendamentoCaptor.getValue();

        Assertions.assertThat(agendamentoRemarcado).isNotNull();
        Assertions.assertThat(agendamentoRemarcado.getDataHora()).isAfter(now);
    }

    @Test
    public void dadoAlgunsAgendamentosQuandoJaExistentesDadoUmaBuscaPorTodosEntaoAgendamentosRetornamComSucesso() {
        final Agendamento agendamento1 = getAgendamento();
        agendamento1.setId(1L);
        final Agendamento agendamento2 = getAgendamento();
        agendamento2.setId(2L);
        final Agendamento agendamento3 = getAgendamento();
        agendamento3.setId(3L);

        List<Agendamento> servicos = new ArrayList<>();

        servicos.add(agendamento1);
        servicos.add(agendamento2);
        servicos.add(agendamento3);

        Mockito.when(agendamentoRepository.findAll()).thenReturn(servicos);

        List<AgendamentoDto> agendamentoDtos = agendamentoService.obterTodos();

        Assertions.assertThat(agendamentoDtos).isNotNull();
        Assertions.assertThat(agendamentoDtos.isEmpty()).isFalse();
        Assertions.assertThat(agendamentoDtos.size()).isEqualTo(3);
        Assertions.assertThat(agendamentoDtos.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(agendamentoDtos.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(agendamentoDtos.get(2).getId()).isEqualTo(3);
    }
}
