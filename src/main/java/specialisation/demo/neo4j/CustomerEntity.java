package specialisation.demo.neo4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class CustomerEntity {

    @Id
    @GeneratedValue
    Long id;

    @Property(name = "firstName")
    String firstName;

    @Property(name = "lastName")
    String lastName;

    @Property(name = "age")
    Integer age;
}
