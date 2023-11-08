package schedule.your.beauty.api.unit.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import schedule.your.beauty.api.model.Production;
import schedule.your.beauty.api.repository.ProductionRepository;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProductionRepositoryTests {

  @Autowired
  ProductionRepository productionRepository;

  @Autowired
  EntityManager entityManager;

  @Test
  void findProductionByNameSucess() {
    this.addProduction();
    Production foundedProduction = this.productionRepository.findProductionByName("Penteado");

    assertThat(foundedProduction.getName()).isEqualTo("Penteado");
  }

  @Test
  void findProductionByNameFail() {
    Production foundedProduction = this.productionRepository.findProductionByName("Penteado");

    assertThat(foundedProduction).isEqualTo(null);
  }

  private Production addProduction() {
    Production production = new Production("Penteado", 50);
    this.entityManager.persist(production);

    return production;
  }
}
