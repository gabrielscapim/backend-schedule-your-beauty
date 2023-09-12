package schedule.your.beauty.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import schedule.your.beauty.api.service.ProductionService;

@RestController
@RequestMapping("/production")
public class ProductionController {

    @Autowired
    private ProductionService productionService;

    @GetMapping
    public ResponseEntity getAllProductions() {
        return ResponseEntity.ok(productionService.getAllProductions());
    }
}
