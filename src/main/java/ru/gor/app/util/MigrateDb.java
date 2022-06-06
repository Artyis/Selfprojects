package ru.gor.app.util;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.gor.app.models.entity.Client;
import ru.gor.app.models.entity.Payment;
import ru.gor.app.repository.ClientRepository;
import ru.gor.app.repository.PaymentRepository;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class MigrateDb {
    private final PaymentRepository repository;
    private final ClientRepository clientRepository;

    @PostConstruct
    public void init() {
        List<Client> list = new ArrayList<>();
        list.add(new Client(1, "Борис", "+79025552555", 10000));
        list.add(new Client(3, "Федор", "+79034434533", 5582));
        list.add(new Client(4, "Боб", "+79033334583", 1130));

        List<Payment> paymentList = new ArrayList<>();

        paymentList.add(new Payment(1, 2000f,"Message", Date.valueOf(LocalDate.of(2022,6,4)), list.get(0), list.get(1)));
        paymentList.add(new Payment(2, 614f,"Message", Date.valueOf(LocalDate.of(2022,6,3)), list.get(1), list.get(2)));
        paymentList.add(new Payment(3, 1345f,"Message", Date.valueOf(LocalDate.of(2022,6,3)), list.get(2), list.get(0)));
        paymentList.add(new Payment(4, 20000f,"Message", Date.valueOf(LocalDate.of(2022,5,5)), list.get(1), list.get(0)));
        paymentList.add(new Payment(5, 30f,"Message", Date.valueOf(LocalDate.of(2022,4,20)), list.get(0), list.get(2)));
        paymentList.add(new Payment(6, 2405f,"Message", Date.valueOf(LocalDate.of(2022,4,15)), list.get(2), list.get(1)));
        paymentList.add(new Payment(7, 433f,"Message", Date.valueOf(LocalDate.of(2022,4,10)), list.get(1), list.get(0)));
        paymentList.add(new Payment(8, 203f,"Message", Date.valueOf(LocalDate.of(2022,4,1)), list.get(2), list.get(1)));
        paymentList.add(new Payment(9, 21577f,"Message", Date.valueOf(LocalDate.of(2022,3,20)), list.get(0), list.get(2)));
        paymentList.add(new Payment(10, 2000f,"Message", Date.valueOf(LocalDate.of(2022,3,10)), list.get(2), list.get(1)));


        for (Client client : list) {
            if (!clientRepository.existsById(client.getId())) {
                clientRepository.save(client);
            }
        }
        for (Payment pay : paymentList) {
            if(!repository.existsById(pay.getId())) {
                repository.save(pay);
            }
        }
    }

}
