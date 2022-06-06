package ru.gor.app.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.gor.app.models.dto.client.ClientDto;
import ru.gor.app.models.dto.client.ListClientsDto;
import ru.gor.app.models.entity.Client;
import ru.gor.app.repository.ClientRepository;

@AllArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ListClientsDto getAllClientsDTO(){
        return new ListClientsDto(clientRepository.findAll());
    }
    public void saveClient(ClientDto clientDto){
        Client client = new Client();
        BeanUtils.copyProperties(clientDto,client);
        clientRepository.save(client);
    }
    public ClientDto getClientById(Integer id){
        Client client = clientRepository.getClientById(id);
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(client,clientDto);
        return  clientDto;
    }
    public void editClient(ClientDto clientDto, Integer id){
        Client client = clientRepository.getClientById(id);
        BeanUtils.copyProperties(clientDto,client);
        clientRepository.save(client);
    }
    public void deleteClient(int id) {
        Client client = clientRepository.getClientById(id);
        clientRepository.delete(client);
    }


}
