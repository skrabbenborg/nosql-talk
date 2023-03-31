package specialisation.demo.neo4j;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;
import specialisation.demo.neo4j.config.Neo4jConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.neo4j.ogm.cypher.ComparisonOperator.EQUALS;

@Slf4j
@Repository
@ConditionalOnBean(Neo4jConfig.class)
public class UserRepository {

    private final SessionFactory neo4j;

    public UserRepository(SessionFactory neo4j) {
        this.neo4j = neo4j;
    }

    public Optional<User> fetch(Long id) {
        return Optional.ofNullable(neo4j.openSession().load(User.class, id));
    }

    public List<User> fetch(String firstName, String lastName) {
        Filter firstNameFilter = new Filter("firstName", EQUALS, firstName);
        Filter lastNameFilter = new Filter("lastName", EQUALS, lastName);
        Filters filters = new Filters(firstNameFilter).and(lastNameFilter);
        return List.copyOf(neo4j.openSession().loadAll(User.class, filters));
    }

    public User store(UserRequest request) {
        User entity = User.builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .age(request.age())
            .build();

        neo4j.openSession().save(entity);

        log.info("Stored {}", entity);

        return entity;
    }

    public void delete(Long id) {
        String query = "MATCH (user:User) WHERE id(user) = $id DETACH DELETE user";

        Map<String, Long> parameters = new HashMap<>();
        parameters.put("id", id);

        neo4j.openSession().query(query, parameters);
    }

    public UserFriendshipRelation addFriendship(Long userOneId, Long userTwoId) {
        User userOne = fetch(userOneId).orElseThrow(() -> new IllegalArgumentException("First user not found"));
        User userTwo = fetch(userTwoId).orElseThrow(() -> new IllegalArgumentException("Second user not found"));

        UserFriendshipRelation relation = UserFriendshipRelation.builder()
            .userOne(userOne)
            .userTwo(userTwo)
            .build();

        neo4j.openSession().save(relation);

        log.info("Stored {}", relation);

        return relation;
    }
}