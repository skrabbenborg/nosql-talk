package specialisation.demo.neo4j;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.neo4j.config.Neo4jConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neo4j.ogm.cypher.ComparisonOperator.EQUALS;

@Slf4j
@Service
@ConditionalOnBean(Neo4jConfig.class)
public class CustomerRepository {

    private final SessionFactory neo4j;

    public CustomerRepository(SessionFactory neo4j) {
        this.neo4j = neo4j;
    }

    public CustomerEntity store(CustomerRequest request) {
        CustomerEntity entity = CustomerEntity.builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .age(request.age())
            .build();

        neo4j.openSession().save(entity);

        log.info("Stored {}", entity);

        return entity;
    }

    public FriendshipRelation store(FriendshipRequest request) {
        CustomerEntity customerOne = fetch(request.customerOne());
        CustomerEntity customerTwo = fetch(request.customerTwo());

        FriendshipRelation relation = FriendshipRelation.builder()
            .customerOne(customerOne)
            .customerTwo(customerTwo)
            .build();

        neo4j.openSession().save(relation);

        log.info("Stored {}", relation);

        return relation;
    }

    public List<CustomerEntity> fetch(String firstName, String lastName) {
        Filter firstNameFilter = new Filter("firstName", EQUALS, firstName);
        Filter lastNameFilter = new Filter("lastName", EQUALS, lastName);
        Filters filters = new Filters(firstNameFilter).and(lastNameFilter);

        return List.copyOf(neo4j.openSession().loadAll(CustomerEntity.class, filters));
    }

    // TODO query can likely be rewritten using Filters
    public void deleteCustomer(Long id) {
        String query = "MATCH (customer:CustomerEntity) WHERE id(customer) = $id DETACH DELETE customer";

        Map<String, Long> parameters = new HashMap<>();
        parameters.put("id", id);

        neo4j.openSession().query(query, parameters);
    }

    // TODO query can likely be rewritten using Filters
    private CustomerEntity fetch(Long id) {
        String query = "MATCH (customer:CustomerEntity) WHERE id(customer) = $id RETURN customer";

        Map<String, Long> parameters = new HashMap<>();
        parameters.put("id", id);

        return neo4j.openSession().queryForObject(CustomerEntity.class, query, parameters);
    }
}


