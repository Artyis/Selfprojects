package ru.gor.app.models.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Critic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String info;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "critic")
    private List<Review> reviewList;
}
