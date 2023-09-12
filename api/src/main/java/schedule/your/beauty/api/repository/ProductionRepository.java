package schedule.your.beauty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import schedule.your.beauty.api.model.Production;

public interface ProductionRepository extends JpaRepository<Production, Integer> {

}
