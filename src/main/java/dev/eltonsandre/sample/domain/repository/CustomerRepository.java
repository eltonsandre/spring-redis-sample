package dev.eltonsandre.sample.domain.repository;

import dev.eltonsandre.sample.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CustomerRepository {

    private static final AtomicLong idGenerator = new AtomicLong(124);
    private static final Map<String, Customer> CUSTOMER_REPOSITORY_MOCK = new HashMap<>(Map.of(
            "120", Customer.builder().id("120").name("Elton Sandré").document("325452345").build(),
            "121", Customer.builder().id("121").name("John Connor").document("234542345").build(),
            "122", Customer.builder().id("122").name("Sarah Connor").document("456732425").build(),
            "123", Customer.builder().id("123").name("Gilson Jr").document("673242345").build()
    ));

    public List<Customer> findAll() {
        return CUSTOMER_REPOSITORY_MOCK.values().stream().toList();
    }

    public Customer findById(String id) {
        return CUSTOMER_REPOSITORY_MOCK.getOrDefault(id, null);
    }

    public Customer insert(final Customer customer) {
        String id = String.valueOf(idGenerator.getAndIncrement());
        Customer build = customer.toBuilder().id(id).build();
        CUSTOMER_REPOSITORY_MOCK.put(id, build);
        return build;
    }

    public void update(final Customer customer) {
        CUSTOMER_REPOSITORY_MOCK.put(customer.getId(), customer);
    }

}
