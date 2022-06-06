package ru.gor.app.serviceTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gor.app.exeption.ClientException;
import ru.gor.app.models.dto.client.ClientDto;
import ru.gor.app.models.entity.Client;
import ru.gor.app.repository.ClientRepository;
import ru.gor.app.service.ClientService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest extends Assert {
    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    private static Client client;

    @BeforeClass
    public static void prepareTestData() {
        client = Client
                .builder()
                .id(45)
                .name("Витек")
                .phone("+79653822516")
                .balance(800.45f)
                .build();
    }

    @Before
    public void init() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void saveNewUser() throws ClientException {
        Client client = Client.builder()
                .name("Mike")
                .phone("+79631591514")
                .balance(480f)
                .build();

        ClientDto clientDto = ClientDto.builder()
                .name(client.getName())
                .phone(client.getPhone())
                .balance(client.getBalance())
                .build();

        clientService.saveClient(clientDto);
        verify(clientRepository).save(any());

    }


    @Test
    public void getOneUserDto() {
        when(clientRepository.getReferenceById(any(Integer.class))).thenReturn(client);

       ClientDto usr = clientService.getClientById(client.getId());

        assertEquals("Name not equal", usr.getName(), client.getName());
        assertEquals("Number not equal", usr.getPhone(), client.getPhone());
        assertEquals("Balance not equal", usr.getBalance(), client.getBalance(), 0);

    }

    @Test
    public void editUser() {
        when(clientRepository.getReferenceById(any(Integer.class))).thenReturn(client);
        when(clientRepository.save(Mockito.any(Client.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Client userForUpdate = Client.builder()
                .name("Александр")
                .phone("+79428965510")
                .balance(38.45f)
                .build();
        ClientDto editDto = ClientDto.builder()
                .name(userForUpdate.getName())
                .phone(userForUpdate.getPhone())
                .balance(userForUpdate.getBalance())
                .build();

        clientService.editClient (editDto, client.getId());
        Client resultUser = clientRepository.getReferenceById(client.getId());

        assertNotNull(resultUser);
        assertSame(resultUser.getId(), client.getId());
        assertEquals("name not equal", resultUser.getName(), userForUpdate.getName());
        assertEquals("number not equal", resultUser.getPhone(), userForUpdate.getPhone());
        assertEquals("balance not equal", resultUser.getBalance(), userForUpdate.getBalance(), 0);
    }

    @Test
    public void deleteUser() {
        when(clientRepository.getReferenceById(any(Integer.class))).thenReturn(client);

        clientService.deleteClient(client.getId());
        verify(clientRepository).delete(client);
    }

    @Test
    public void getAllUserDto() {
        clientService.getAllClientsDTO().getClientList();
        verify(clientRepository).findAll();
    }

}
