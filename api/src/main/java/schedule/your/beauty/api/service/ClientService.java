package schedule.your.beauty.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.your.beauty.api.dto.DataDetailingClientDTO;
import schedule.your.beauty.api.repository.ClientRepository;

import java.util.ArrayList;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Iterable<DataDetailingClientDTO> getAllClients() {
        var clientsFromRepository = clientRepository.findAll();
        ArrayList<DataDetailingClientDTO> clients = new ArrayList<>();

        clientsFromRepository.forEach(client -> clients.add(new DataDetailingClientDTO(client)));

        return clients;
    }
}
