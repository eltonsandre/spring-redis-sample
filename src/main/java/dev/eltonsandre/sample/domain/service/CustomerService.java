package dev.eltonsandre.sample.domain.service;

import dev.eltonsandre.sample.domain.model.Customer;
import dev.eltonsandre.sample.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Cacheable(cacheNames = "customers", key = "'all'")
    public List<Customer> retrieveAll() {
        log.info("no-cache: customer");
        return customerRepository.findAll();
    }

    @Cacheable(cacheNames = "customer", key = "#id", condition = "#id != null")
    public Customer retrieve(final String id) {
        log.info("no-cache: customer");
        return customerRepository.findById(id);
    }

    @CacheEvict(cacheNames = "customers", allEntries = true, beforeInvocation = true)
    public Customer create(final Customer customer) {
        return customerRepository.insert(customer);
    }

    @CacheEvict(cacheNames = {"customers", "customer"}, allEntries = true, beforeInvocation = true)
    public void update(final Customer customer) {
        log.info("cache-evict: all-customers, customer");
        customerRepository.update(customer);
    }

}
