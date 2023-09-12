package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataDeitailingProductionDTO;
import schedule.your.beauty.api.repository.ProductionRepository;

import java.util.ArrayList;

@Service
public class ProductionService {

    @Autowired
    ProductionRepository productionRepository;

    public Iterable<DataDeitailingProductionDTO> getAllProductions() {
        var productionsFromRepository = productionRepository.findAll();
        ArrayList<DataDeitailingProductionDTO> productions = new ArrayList<>();

        productionsFromRepository.forEach(production -> productions.add(new DataDeitailingProductionDTO(production)));

        return productions;
    }
}
