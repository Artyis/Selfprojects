package ru.gor.app.models.entity;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
@Entity
@Table (name="clients", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Введите имя")
    private String name;
    private String phone;
    private float balance;

}
