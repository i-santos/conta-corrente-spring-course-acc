package com.accenture.conta.dto.contaCorrente;

public class ContaCorrenteOperationDTO {

    private String operation;
    private Double amount;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
