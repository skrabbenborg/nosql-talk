package specialisation.demo.neo4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import specialisation.demo.neo4j.config.Neo4jConfig;

import java.util.List;

@RestController
@RequestMapping("/social/user")
@ConditionalOnBean(Neo4jConfig.class)
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<List<User>> retrieveUsers(@RequestParam String firstName, @RequestParam String lastName) {
        return ResponseEntity.ok(repository.fetch(firstName, lastName));
    }

    @PostMapping
    ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(repository.store(request));
    }

    @DeleteMapping
    ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        repository.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/befriend")
    ResponseEntity<UserFriendshipRelation> createFriendship(@RequestParam Long userOne, @RequestParam Long userTwo) {
        return ResponseEntity.ok(repository.addFriendship(userOne, userTwo));
    }
}