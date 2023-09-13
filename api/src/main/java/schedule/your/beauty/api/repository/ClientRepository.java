package schedule.your.beauty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import schedule.your.beauty.api.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
