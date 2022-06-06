package ru.gor.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gor.app.exeption.ClientException;
import ru.gor.app.models.entity.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    default Client getClientById(int id) throws ClientException {
        return findById(id).orElseThrow(() -> new ClientException("Client not found by Id"));
    }
    default List<Client> getWithoutClient(int id){
        List<Client> users =  findAll();
        users.remove(getClientById(id));
        return users;
    }


}
