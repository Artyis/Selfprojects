INSERT INTO public.critic (id, name, surname, information) VALUES (DEFAULT, 'Nam1', 'Sur1', 'desc1');
INSERT INTO public.critic (id, name, surname, information) VALUES (DEFAULT, 'Nam2', 'Sur2', 'desc2');
INSERT INTO public.critic (id, name, surname, information) VALUES (DEFAULT, 'Nam3', 'Sur3', 'desc3');

INSERT INTO public.genre (id, name, description) VALUES (DEFAULT, 'genre1', 'desc1');
INSERT INTO public.genre (id, name, description) VALUES (DEFAULT, 'genre2', 'desc2');
INSERT INTO public.genre (id, name, description) VALUES (DEFAULT, 'genre3', 'desc3');
INSERT INTO public.genre (id, name, description) VALUES (DEFAULT, 'genre4', 'desc4');
INSERT INTO public.genre (id, name, description) VALUES (DEFAULT, 'genre5', 'desc5');
INSERT INTO public.genre (id, name, description) VALUES (DEFAULT, 'genre6', 'desc6');
INSERT INTO public.genre (id, name, description) VALUES (DEFAULT, 'genre7', 'desc7');
INSERT INTO public.genre (id, name, description) VALUES (DEFAULT, 'genre8', 'desc8');
INSERT INTO public.genre (id, name, description) VALUES (DEFAULT, 'genre9', 'desc9');

INSERT INTO public.film (id, name, description, length, release, score) VALUES (DEFAULT, 'Film1', 'description1',189 ,1999, 5);
INSERT INTO public.film (id, name, description, length, release, score) VALUES (DEFAULT, 'Film1', 'description2',188 ,1998, 5);
INSERT INTO public.film (id, name, description, length, release, score) VALUES (DEFAULT, 'Film1', 'description3',187 ,1997, 5);
INSERT INTO public.film (id, name, description, length, release, score) VALUES (DEFAULT, 'Film1', 'description4',186 ,1996, 5);
INSERT INTO public.film (id, name, description, length, release, score) VALUES (DEFAULT, 'Film1', 'description5',185 ,1995, 3);
INSERT INTO public.film (id, name, description, length, release, score) VALUES (DEFAULT, 'Film1', 'description6',184 ,1994, 5);


INSERT INTO public.genre_film (film_id, genre_id) VALUES (1,2);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (1,3);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (2,3);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (3,2);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (3,3);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (4,3);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (4,5);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (4,6);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (4,9);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (5,1);
INSERT INTO public.genre_film  (film_id, genre_id) VALUES (5,3);
I