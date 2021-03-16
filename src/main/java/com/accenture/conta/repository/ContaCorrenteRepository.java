package com.accenture.conta.repository;

import com.accenture.conta.entity.ContaCorrente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {
    
}
