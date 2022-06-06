package ru.gor.app.integrTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import ru.gor.app.exeption.ClientException;
import ru.gor.app.models.dto.client.ClientDto;
import ru.gor.app.models.dto.client.ListClientsDto;
import ru.gor.app.models.entity.Client;
import ru.gor.app.repository.ClientRepository;
import ru.gor.app.service.ClientService;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientTest extends Assert {
    @Autowired
    private ClientRepository rep;
    private ClientService service;
    private List<Client> list;

    @Before
    public void Setup() throws ClientException {
        service = new ClientService(rep);
        for (int i = 0; i < 5; i++) {
            ClientDto client = createRandomUser();
            service.saveClient(client);
        }
        list = rep.findAll();
    }

    private ClientDto createRandomUser() {
        int length = (int) (Math.random() * 10 + 2);
        int prefix = (int) (Math.random() * 105 + 900);
        int suffix = (int) (Math.random() * 10000000);
        boolean useLetters = true;
        boolean useNumbers = false;
        String name = RandomStringUtils.random(length, useLetters, useNumbers);
        String number = "+7" + String.valueOf(prefix) + String.valueOf(suffix);
        ClientDto user = ClientDto.builder()
                .name(name)
                .phone(number)
                .balance((float) (Math.random() * 100000))
                .build();
        return user;
    }

    @Test
    public void testDb() {
        ClientDto c1 = new ClientDto(1, "Boby", "+79626302408", 80f);
        ClientDto c2 = new ClientDto(2, "Booko", "+99626452408", 7f);
        service.saveClient(c1);
        service.saveClient(c2);
        ListClientsDto teams = service.getAllClientsDTO();
        assertEquals(list.size() + 2, teams.getClientList().size());
    }

    @Test
    public void getAllUserDtoTest() {
        List<Client> actualList = service.getAllClientsDTO().getClientList();
        assertEquals("List is not equal", list, actualList);
    }

    @Test
    public void saveNewUserTest() {
        ClientDto userForCreate = createRandomUser();

        service.saveClient(userForCreate);
        List<Client> actualList = service.getAllClientsDTO().getClientList();
        assertEquals("Пользователь не добавился", list.size(), actualList.size(), 1);
        Client actualUser = actualList.get(actualList.size() - 1);

        for (Client client:list) {
            if (client.equals(actualUser)) {
                actualUser = null;
            }
        }
        assertNotNull("Object not found", actualUser);

        assertEquals(userForCreate.getName(), actualUser.getName());
        assertEquals(userForCreate.getPhone(), actualUser.getPhone());
        assertEquals(userForCreate.getBalance(), actualUser.getBalance(), 0);
    }

    @Test
    public void getOneUserTest() {
        int userCount = new Random().nextInt(0, list.size() - 1);
        Client userExpected = list.get(userCount);
        int userId = userExpected.getId();

        ClientDto dto = service.getClientById(userId);

        Client userActual = Client.builder()
                .id(userExpected.getId())
                .name(dto.getName())
                .phone(dto.getPhone())
                .balance(dto.getBalance())
                .build();

        assertEquals(userExpected.getName(), userActual.getName());
        assertEquals(userExpected.getPhone(), userActual.getPhone());
        assertEquals(userExpected.getBalance(), userActual.getBalance(), 0);
    }

    @Test(expected = ClientException.class)
    public void getOneUser_withNotValidIdTest() {
        int id = 88;
        try {
            service.getClientById(id);
        } catch (ClientException exception) {
            throw exception;
        }
    }

    @Test
    public void editUserTest() {
        ClientDto clientForEdit = createRandomUser();
        Client userOld = list.get(new Random().nextInt(0, list.size() - 1));
        service.editClient(clientForEdit,userOld.getId());
        ClientDto userActual = service.getClientById(userOld.getId());
        assertNotNull("Object not found", userActual);

        assertEquals(clientForEdit.getName(), userActual.getName());
        assertEquals(clientForEdit.getPhone(), userActual.getPhone());
        assertEquals(clientForEdit.getBalance(), userActual.getBalance(), 0);
    }

    @Test(expected = ClientException.class)
    public void editUser_withNotValidId() throws ClientException {
        ClientDto userForEdit = createRandomUser();
        Client userOld = list.get(new Random().nextInt(0, list.size() - 1));
        int userId = 88;
        service.editClient(userForEdit,userId);
    }

    @Test
    public void deleteUserTest() {
        Client userForDelete = list.get(new Random().nextInt(0, list.size() - 1));
        int userId = userForDelete.getId();
        service.deleteClient(userId);

        List<Client> actualList = service.getAllClientsDTO().getClientList();
        assertEquals(actualList.size(), list.size(), 1);

        for (Client user : actualList) {
            assertNotEquals(userForDelete, user);
        }
    }

    @Test(expected = ClientException.class)
    public void deleteUser_withNotValidIdTest() {
        int id = 88;
        try {
            service.deleteClient(id);
        } catch (ClientException exception) {
            throw exception;
        }
    }

}
