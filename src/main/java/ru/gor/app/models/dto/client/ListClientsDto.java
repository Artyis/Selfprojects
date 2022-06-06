package ru.gor.app.models.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gor.app.models.entity.Client;
import java.util.List;
@Data
@AllArgsConstructor
public class ListClientsDto {
    private List<Client> clientList;
}
