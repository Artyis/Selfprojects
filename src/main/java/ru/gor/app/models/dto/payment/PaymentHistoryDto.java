package ru.gor.app.models.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gor.app.models.entity.Client;
import ru.gor.app.models.entity.Payment;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryDto {
    List<Payment> inPay;
    List<Payment> outPay;
    List<Client> clientList;
    int filter_id;
}
