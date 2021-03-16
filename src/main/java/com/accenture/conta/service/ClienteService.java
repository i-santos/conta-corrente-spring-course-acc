package com.accenture.conta.service;

import java.util.List;
import java.util.Optional;

import com.accenture.conta.entity.Cliente;
import com.accenture.conta.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> index() {
        return this.clienteRepository.findAll();
    }

    public Cliente store(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public Optional<Cliente> show(Long id) {
        return this.clienteRepository.findById(id);
    }

    public Cliente update(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public void delete(Cliente cliente) {
        this.clienteRepository.delete(cliente);
    }
    
}
