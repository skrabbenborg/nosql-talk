package specialisation.demo.neo4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RelationshipEntity(type = "FRIENDSHIP")
public class UserFriendshipRelation {

    @Id
    @GeneratedValue
    Long relationshipId;

    @StartNode
    User userOne;

    @EndNode
    User userTwo;
}