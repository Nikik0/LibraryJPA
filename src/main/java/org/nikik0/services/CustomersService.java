package org.nikik0.services;

import org.nikik0.models.Customer;
import org.nikik0.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomersService {
    private final CustomersRepository customersRepository;

    @Autowired
    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<Customer> index(){
        return customersRepository.findAll();
    }

    public Customer show(int id){
        return customersRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Customer customer){
        customersRepository.save(customer);
    }
    @Transactional
    public void update(int id, Customer customer){
        customer.setCustomerid(id);
        customersRepository.save(customer);
    }
    @Transactional
    public void delete(int id){
        customersRepository.deleteById(id);
    }
}
