package br.edu.univas.api.plano.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.edu.univas.api.plano.vo.Customer;

@Repository
public class CustomerRepository {

    private Map<Long, Customer> my_database = new HashMap<>();

    public void create(Customer customer) {
        my_database.put(customer.getCpf(), customer);
    }

    public Collection<Customer> listAll() {
        return my_database.values();
    }

    public Customer getByCpf(long cpf) {
        return my_database.get(cpf);
    }

    public void update(Customer customer) {
        my_database.put(customer.getCpf(), customer);
    }

    public void delete(Long cpf) {
        my_database.remove(cpf);
    }

    public void deleteDependents(Long cpf) {
        if(my_database.entrySet() != null && !my_database.isEmpty()) {
            for (Map.Entry<Long, Customer> customer : my_database.entrySet()) {
                if (customer.getValue().getCpfTitular() == cpf) {
                    my_database.remove(customer.getValue().getCpf());
                }
            }
        }
        
    }

    public Collection<Customer> getDependents(Long cpf) {
        List<Customer> dependents = new ArrayList<>();
        for (Map.Entry<Long, Customer> customer : my_database.entrySet()) {
            if (customer.getValue().getCpfTitular() == cpf) {
                dependents.add(customer.getValue());
            }
        }
        return dependents;
    }

}
