package br.edu.univas.api.plano.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.univas.api.plano.repository.CustomerRepository;
import br.edu.univas.api.plano.vo.Customer;

@RestController
@RequestMapping("/customers")
public class PlanoController {

    @Autowired
    private CustomerRepository repository;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        repository.create(customer);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Customer> update(@PathVariable Long cpf, @RequestBody Customer customer) {
        Customer oldCustomer = repository.getByCpf(cpf);
        if (oldCustomer != null) {
            customer.setCpf(cpf);
            repository.update(customer);
            return ResponseEntity.ok(customer);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Customer> delete(@PathVariable Long cpf) {
        Customer customer = repository.getByCpf(cpf);
        if (customer != null) {
            repository.delete(cpf);
            repository.deleteDependents(cpf);
            return ResponseEntity.ok(customer);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Customer>> listAll() {
        return ResponseEntity.ok(repository.listAll());
    }
    
    @GetMapping("/{cpf}")
    public ResponseEntity<Customer> getByCpf(@PathVariable Long cpf){
        Customer customer = repository.getByCpf(cpf);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }

        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{cpf}/dependents")
    public ResponseEntity<Collection<Customer>> getDependentsByCpf(@PathVariable Long cpf){
        Customer customer = repository.getByCpf(cpf);
        if (customer != null) {
            Collection<Customer> dependents = repository.getDependents(cpf);
            if (!dependents.isEmpty()) {
                return ResponseEntity.ok(dependents);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

}