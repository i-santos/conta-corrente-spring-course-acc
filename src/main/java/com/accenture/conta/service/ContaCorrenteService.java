package com.accenture.conta.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.accenture.conta.entity.ContaCorrente;
import com.accenture.conta.repository.ContaCorrenteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaCorrenteService {

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    public List<ContaCorrente> index(Long clientId) {
        return contaCorrenteRepository.findAll().stream()
                .filter(c -> c.getCliente().getId().longValue() == clientId.longValue()).collect(Collectors.toList());
    }

    public Optional<ContaCorrente> show(Long id) {
        return contaCorrenteRepository.findById(id);
    }

    public ContaCorrente store(ContaCorrente contaCorrente) {
        return contaCorrenteRepository.save(contaCorrente);
    }

    public ContaCorrente update(ContaCorrente contaCorrente) {
        return contaCorrenteRepository.save(contaCorrente);
    }

    public void delete(ContaCorrente contaCorrente) {
        contaCorrenteRepository.delete(contaCorrente);
    }
}
