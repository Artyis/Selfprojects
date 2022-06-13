package ru.gor.app.models.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float score;
    private String message;
    private Date date;
    @ManyToOne(cascade = CascadeType.ALL)
    private Critic critic;
    @ManyToOne(cascade = CascadeType.ALL)
    private Film film;

}
