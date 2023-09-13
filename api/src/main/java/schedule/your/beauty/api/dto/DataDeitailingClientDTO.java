package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.Client;

public record DataDeitailingClientDTO(
        int id,
        String name,
        String number
) {

    public DataDeitailingClientDTO(Client client) {
        this(
                client.getId(),
                client.getName(),
                client.getNumber()
        );
    }
}
