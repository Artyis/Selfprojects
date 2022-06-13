package ru.gor.app.models.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int time;
    @Column(name = "release")
    private int release;
    private float score;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "genre_film",
            inverseJoinColumns = @JoinColumn(name = "genre_id"),
            joinColumns = @JoinColumn(name = "film_id"))
    private List<Genre> genres;
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
