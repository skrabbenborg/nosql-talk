package specialisation.demo.neo4j;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartyRepository {

    private final UserRepository userRepository;

    private final SessionFactory neo4j;

    public PartyRepository(UserRepository userRepository, SessionFactory neo4j) {
        this.userRepository = userRepository;
        this.neo4j = neo4j;
    }

    public Optional<Party> fetch(Long id) {
        return Optional.ofNullable(neo4j.openSession().load(Party.class, id));
    }

    public Party createParty(Long userId, String description) {
        User user = userRepository.fetch(userId).orElseThrow(() -> new IllegalArgumentException(("User not found")));
        Party party = createPartyNode(description);

        var relation = UserOrganisesPartyRelation.builder()
            .user(user)
            .party(party)
            .build();

        neo4j.openSession().save(relation);

        return party;
    }

    public UserInvitedToParty inviteToParty(Long userId, Long partyId) {
        User user = userRepository.fetch(userId).orElseThrow(() -> new IllegalArgumentException(("User not found")));
        Party party = fetch(partyId).orElseThrow(() -> new IllegalArgumentException(("Party not found")));

        var relation = UserInvitedToParty.builder()
            .user(user)
            .party(party)
            .build();

        neo4j.openSession().save(relation);

        return relation;
    }

    private Party createPartyNode(String description) {
        var introduction = Party.builder()
            .value(description)
            .build();

        neo4j.openSession().save(introduction);

        return introduction;
    }
}