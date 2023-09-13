package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.Production;

public record DataDeitailingProductionDTO(
        int id,
        String name,
        int price
) {
    public DataDeitailingProductionDTO(Production production) {
        this(
                production.getId(),
                production.getName(),
                production.getPrice()
        );
    }
}
