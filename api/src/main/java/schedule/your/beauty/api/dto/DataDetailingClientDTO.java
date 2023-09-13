package schedule.your.beauty.api.dto;

import schedule.your.beauty.api.model.Client;

public record DataDetailingClientDTO(
        int id,
        String name,
        String number
) {

    public DataDetailingClientDTO(Client client) {
        this(
                client.getId(),
                client.getName(),
                client.getNumber()
        );
    }
}
