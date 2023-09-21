package schedule.your.beauty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import schedule.your.beauty.api.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query(value = "SELECT \n" +
            "    id, name, number\n" +
            "FROM\n" +
            "    clients\n" +
            "WHERE\n" +
            "    number = :clientNumber", nativeQuery = true)
    Client findClientByNumber(@Param("clientNumber") String clientNumber);

}
