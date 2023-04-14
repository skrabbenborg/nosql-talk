package specialisation.demo.neo4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import specialisation.demo.neo4j.config.Neo4jConfig;

@RestController
@RequestMapping("/social/party")
@ConditionalOnBean(Neo4jConfig.class)
public class PartyController {

    private final PartyRepository repository;

    public PartyController(PartyRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<Party> createParty(@RequestParam Long userId, @RequestParam String description) {
        return ResponseEntity.ok(repository.createParty(userId, description));
    }

    @PostMapping("/invite")
    ResponseEntity<PartyInvitedUser> inviteToParty(@RequestParam Long userId, @RequestParam Long partyId) {
        return ResponseEntity.ok(repository.inviteToParty(userId, partyId));
    }
}