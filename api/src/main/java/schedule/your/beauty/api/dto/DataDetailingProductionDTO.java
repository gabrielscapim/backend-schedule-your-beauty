package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.Production;

public record DataDetailingProductionDTO(
        int id,
        String name,
        int price
) {
    public DataDetailingProductionDTO(Production production) {
        this(
                production.getId(),
                production.getName(),
                production.getPrice()
        );
    }
}
