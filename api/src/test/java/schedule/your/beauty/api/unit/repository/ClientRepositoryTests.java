package schedule.your.beauty.api.unit.repository;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import schedule.your.beauty.api.model.Client;
import schedule.your.beauty.api.repository.ClientRepository;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ClientRepositoryTests {

  @Autowired
  ClientRepository clientRepository;
  @Autowired
  EntityManager entityManager;

  @Test
  @DisplayName("Should get Client sucessfully from DB")
  void findClientByNumberSuccess() {
    String clientNumber = "99999999";
    this.addClient("João", clientNumber);
    Client foundedClient = this.clientRepository.findClientByNumber(clientNumber);

    assertThat(foundedClient.getNumber()).isEqualTo(clientNumber);
  }

  @Test
  @DisplayName("Should not get Client sucessfully from DB")
  void findClientByNumberFailed() {
    Client foundedClient = this.clientRepository.findClientByNumber("9");

    assertThat(foundedClient).isEqualTo(null);
  }

  private Client addClient(String name, String number) {
    Client newClient = new Client(name, number);
    this.entityManager.persist(newClient);

    return newClient;
  }
}
