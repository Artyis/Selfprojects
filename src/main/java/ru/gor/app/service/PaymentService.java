package ru.gor.app.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.gor.app.models.dto.payment.PaymentDto;
import ru.gor.app.models.dto.payment.PaymentHistoryDto;
import ru.gor.app.models.entity.Client;
import ru.gor.app.models.entity.Payment;
import ru.gor.app.repository.ClientRepository;
import ru.gor.app.repository.PaymentRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository repository;
    private final ClientRepository clientRepository;

    public PaymentDto getPaymentDto(int id) {
        Client sender = clientRepository.getClientById(id);
        List<Client> users = clientRepository.getWithoutClient(id);
        return new PaymentDto(sender, users);
    }
    public void addPayment(PaymentDto paymentDto, int id) {
        Payment payment = new Payment();
        Client sender = clientRepository.getReferenceById(id);
        Client payer = clientRepository.getReferenceById(paymentDto.getPayee_id());
        sender.setBalance(sender.getBalance() - paymentDto.getSum());
        payer.setBalance(payer.getBalance() + paymentDto.getSum());
        Date date = Date.valueOf(LocalDate.now());
        payment.setDate(date);
        payment.setSender(sender);
        payment.setSum(paymentDto.getSum());
        payment.setPayee(payer);
        repository.save(payment);
    }
    public PaymentHistoryDto getHistoryDto(int id, int filter) {
        List<Payment> inPay;
        List<Payment> outPay;
        List<Payment> allPayments = repository.findAll();

        inPay = allPayments.stream().
                filter(p -> p.getPayee().getId() == id).
                collect(Collectors.toList());
        outPay = allPayments.stream().
                filter(payment -> payment.getSender().getId() == id).
                collect(Collectors.toList());

        if(filter != 0){
            inPay = inPay.stream().
                    filter(p -> p.getSender().getId() == filter).
                    collect(Collectors.toList());
            outPay = outPay.stream().
                    filter(payment -> payment.getPayee().getId() == filter).
                    collect(Collectors.toList());
        }

        return new PaymentHistoryDto(inPay, outPay, clientRepository.getWithoutClient(id), filter);
    }
    public BindingResult getAllowPayment(BindingResult bindingResult, PaymentDto paymentDto) {
        if(paymentDto.getBalance()-paymentDto.getSum() > 0){
            return bindingResult;
        }
        bindingResult.rejectValue("sum", "Not Enough Cash", "Недостаточно средств");
        return bindingResult;
    }








}
