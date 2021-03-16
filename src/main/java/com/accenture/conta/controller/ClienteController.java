package com.accenture.conta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.accenture.conta.entity.Cliente;
import com.accenture.conta.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> index() {
        return new ResponseEntity<>(this.clienteService.index(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> show(@PathVariable("id") Long id) {

        Optional<Cliente> cliente = clienteService.show(id);

        if (cliente.isPresent()) {
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", 404);
        response.put("message", "not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Cliente> store(@RequestBody Cliente cliente) {
        return new ResponseEntity<>(this.clienteService.store(cliente), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return new ResponseEntity<>(this.clienteService.update(cliente), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.show(id);
        Map<String, Object> response = new HashMap<>();

        if (cliente.isPresent()) {
            clienteService.delete(cliente.get());
            response.put("status", 200);
            response.put("message", "ok");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("status", 404);
        response.put("message", "not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
