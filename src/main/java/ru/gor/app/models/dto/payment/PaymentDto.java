package ru.gor.app.models.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gor.app.models.entity.Client;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private float sum;
    private String name;
    private String phone;
    private float balance;
    private int payee_id;
    private List<Client> clientList;

    public PaymentDto(Client sender, List<Client> clients){
        this.name=sender.getName();
        this.phone=sender.getPhone();
        this.balance=sender.getBalance();
        this.clientList=clients;
        this.sum=0;
        this.payee_id=0;
    }
}
