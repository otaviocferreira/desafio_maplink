package com.maplink.desafio.repository;

import com.maplink.desafio.entity.Agendamento;
import com.maplink.desafio.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
