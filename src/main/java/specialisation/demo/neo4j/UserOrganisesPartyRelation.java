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
@RelationshipEntity(type = "ORGANISES")
public class UserOrganisesPartyRelation {

    @Id
    @GeneratedValue
    Long relationshipId;

    @StartNode
    User user;

    @EndNode
    Party party;
}