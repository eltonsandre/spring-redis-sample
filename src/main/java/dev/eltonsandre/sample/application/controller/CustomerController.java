package dev.eltonsandre.sample.application.controller;

import dev.eltonsandre.sample.domain.model.Customer;
import dev.eltonsandre.sample.domain.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok().body(customerService.retrieveAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> retrieve(@PathVariable final String id) {
        return ObjectUtils.isEmpty(id)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(customerService.retrieve(id));
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
        if (customerService.retrieve(customer.getId()) == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customer));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<Customer> update(@Validated({PutMapping.class}) @RequestBody Customer customer) {
        if (customerService.retrieve(customer.getId()) == null) {
            return ResponseEntity.notFound().build();
        }
        customerService.update(customer);
        return ResponseEntity.noContent().build();
    }


}
