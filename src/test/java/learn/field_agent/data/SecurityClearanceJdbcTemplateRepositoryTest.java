package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 4;

    @Autowired
    SecurityClearanceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<SecurityClearance> securityClearances = repository.findAll();
        assertNotNull(securityClearances);
        assertTrue(securityClearances.size() >= 2);
    }

    @Test
    void shouldFindById() {
        SecurityClearance secret = new SecurityClearance("Secret");
        secret.setSecurityClearanceId(1);
        SecurityClearance topSecret = new SecurityClearance("Top Secret");
        topSecret.setSecurityClearanceId(2);

        SecurityClearance actual = repository.findById(1);
        assertEquals(secret, actual);

        actual = repository.findById(2);
        assertEquals(topSecret, actual);
    }

    @Test
    void shouldAdd() {
        SecurityClearance securityClearance = makeSecurityClearance();
        SecurityClearance actual = repository.add(securityClearance);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getSecurityClearanceId());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(2);
        assertTrue(repository.update(securityClearance));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }

    @Test
    void shouldReturnProperConnections() {
        assertEquals(12, repository.getConnections(1));
    }

    @Test
    void shouldReturnNoConnections() {
        assertEquals(0, repository.getConnections(3));
    }

    private SecurityClearance makeSecurityClearance() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setName("O5");
        return securityClearance;
    }
}