package specialisation.demo.neo4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import specialisation.demo.neo4j.config.Neo4jConfig;

import java.util.List;

@RestController
@RequestMapping("/network")
@ConditionalOnBean(Neo4jConfig.class)
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<List<CustomerEntity>> retrieveCustomers(
        @RequestParam String firstName,
        @RequestParam String lastName
    ) {
        return ResponseEntity.ok(repository.fetch(firstName, lastName));
    }

    @PostMapping("/customer")
    ResponseEntity<CustomerEntity> createCustomer(
        @RequestBody CustomerRequest request
    ) {
        return ResponseEntity.ok(repository.store(request));
    }

    @DeleteMapping("/customer")
    ResponseEntity<Void> deleteCustomer(
        @RequestParam Long id
    ) {
        repository.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/friendship")
    ResponseEntity<FriendshipRelation> createFriendship(
        @RequestBody FriendshipRequest request
    ) {
        return ResponseEntity.ok(repository.store(request));
    }
}
