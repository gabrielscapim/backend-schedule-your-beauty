package schedule.your.beauty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import schedule.your.beauty.api.model.Production;

public interface ProductionRepository extends JpaRepository<Production, Integer> {
    @Query(value = "SELECT *\n" +
            "FROM\n" +
            "    productions\n" +
            "WHERE\n" +
            "    name = :productionName", nativeQuery = true)
    Production findProductionByName(@Param("productionName") String productionName);

}
