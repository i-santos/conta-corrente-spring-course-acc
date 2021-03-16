package com.accenture.conta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.accenture.conta.dto.contaCorrente.ContaCorrenteOperationDTO;
import com.accenture.conta.entity.Cliente;
import com.accenture.conta.entity.ContaCorrente;
import com.accenture.conta.service.ClienteService;
import com.accenture.conta.service.ContaCorrenteService;

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
@RequestMapping("clientes/{clientId}/contas")
public class ContaCorrenteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @GetMapping
    public ResponseEntity<List<ContaCorrente>> index(@PathVariable Long clientId) {
        return new ResponseEntity<>(this.contaCorrenteService.index(clientId), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> show(@PathVariable Long clientId, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        Optional<ContaCorrente> conta = contaCorrenteService.show(id);
        if (conta.isPresent()) {

            ContaCorrente mConta = conta.get();

            if (mConta.getCliente().getId().longValue() != clientId.longValue()) {
                response.put("status", 404);
                response.put("message", "Unauthorized");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(mConta, HttpStatus.OK);
        }

        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("message", "Unauthorized");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<Object> store(@PathVariable Long clientId, @RequestBody ContaCorrente contaCorrente) {

        Optional<Cliente> cliente = clienteService.show(clientId);
        if (cliente.isPresent()) {
            Cliente mCliente = cliente.get();
            mCliente.setId(clientId);
            contaCorrente.setCliente(mCliente);
            return new ResponseEntity<>(contaCorrenteService.store(contaCorrente), HttpStatus.OK);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", 404);
        response.put("message", "Unauthorized");

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("{id}")
    public ResponseEntity<Object> processOperation(@PathVariable Long clientId, @PathVariable Long id,
            @RequestBody ContaCorrenteOperationDTO operationDTO) {

        Map<String, Object> response = new HashMap<>();

        Optional<ContaCorrente> contaCorrente = contaCorrenteService.show(id);
        if (contaCorrente.isPresent()) {
            ContaCorrente conta = contaCorrente.get();

            if (conta.getCliente().getId().longValue() != clientId.longValue()) {
                response.put("status", 404);
                response.put("message", "Unauthorized");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Double amount = operationDTO.getAmount();
            Double saldo = conta.getSaldo();
            String op = operationDTO.getOperation();

            switch (op) {
            case "saque":

                if (saldo < amount) {
                    response.put("status", HttpStatus.BAD_REQUEST.value());
                    response.put("message", "Saldo insuficiente");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                conta.setSaldo(saldo - amount);

                return new ResponseEntity<>(contaCorrenteService.store(conta), HttpStatus.OK);

            case "deposito":

                conta.setSaldo(saldo + amount);

                return new ResponseEntity<>(contaCorrenteService.store(conta), HttpStatus.OK);

            default:
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("message", "Operação inválida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

            }
        }

        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("message", "Unauthorized");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ContaCorrente> update(@PathVariable Long id, @RequestBody ContaCorrente contaCorrente) {

        contaCorrente.setId(id);
        return new ResponseEntity<>(contaCorrenteService.update(contaCorrente), HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        Optional<ContaCorrente> contaCorrente = contaCorrenteService.show(id);
        if (contaCorrente.isPresent()) {
            contaCorrenteService.delete(contaCorrente.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
