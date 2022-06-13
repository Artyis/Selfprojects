package ru.gor.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.Nullable;
import ru.gor.app.models.entity.Film;

import java.util.Collection;
import java.util.List;

public interface FilmRepository extends PagingAndSortingRepository<Film, Integer> {

    List<Film> findAll();
    @Query("""
            select distinct p from Film p inner join p.genres genres
            where upper(p.name) like upper(?1) and p.release between ?2 and ?3 and p.score between ?4 and ?5""")
    Page<Film> findByField(@Nullable String name,
                           @Nullable Integer yearStart,
                           @Nullable Integer yearEnd,
                           @Nullable Float scoreStart,
                           @Nullable Float scoreEnd,
                           Pageable page);
    @Query("""
            select distinct p from Film p inner join p.genres genres
            where upper(p.name) like upper(?1) and p.release between ?2 and ?3 and p.score between ?4 and ?5 and genres.id in ?6""")
    Page<Film> findByFieldWithGenres(@Nullable String name,
                                            @Nullable Integer yearStart,
                                            @Nullable Integer yearEnd,
                                            @Nullable Float scoreStart,
                                            @Nullable Float scoreEnd,
                                            @Nullable Collection<Integer> ids,
                                            Pageable page);
}
