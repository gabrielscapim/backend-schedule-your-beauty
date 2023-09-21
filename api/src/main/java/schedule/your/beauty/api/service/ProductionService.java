package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataDetailingProductionDTO;
import schedule.your.beauty.api.repository.ProductionRepository;

import java.util.ArrayList;

@Service
public class ProductionService {

    @Autowired
    ProductionRepository productionRepository;

    public Iterable<DataDetailingProductionDTO> getAllProductions() {
        var productionsFromRepository = productionRepository.findAll();
        ArrayList<DataDetailingProductionDTO> productions = new ArrayList<>();

        productionsFromRepository.forEach(production -> productions.add(new DataDetailingProductionDTO(production)));

        return productions;
    }
}
