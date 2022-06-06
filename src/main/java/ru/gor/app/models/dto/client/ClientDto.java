package ru.gor.app.models.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {
    private int id;
    @Size(min = 2, max = 10, message = "Длина имени от 2 до 10 символов")
    @Pattern(regexp = "[А-Яа-яA-Za-z]", message = "Имя должно иметь буквенное значение")
    private String name;
    @Pattern(regexp = "\\+7[0-9]{10}", message = "Номер должен начинаться с +7 и состоять из 10 цифр")
    private String phone;
    @PositiveOrZero(message = "Баланс должен быть положительный")
    private float balance;
}
