
insert into scientific_area (id, name) values (1, "Informatika");
insert into scientific_area (id, name) values(2, "Geografija");
insert into scientific_area (id, name) values(3, "Medicina");
insert into scientific_area (id, name) values(4, "Hemija");
insert into scientific_area (id, name) values(5, "Matematika");
insert into scientific_area (id, name) values(6, "Fizika");

insert into role (id, name) values (1, "ROLE_ADMIN");
insert into role (id, name) values (2, "ROLE_USER");
insert into role (id, name) values (3, "ROLE_REVIEWER");
insert into role (id, name) values (4, "ROLE_EDITOR");

insert into payment_type values (1, "Banka");
insert into payment_type values (2, "PayPal");
insert into payment_type values (3, "BitCoin");

insert into user_custom values (1, 1, "Novi Sad", "marko@gmail.com", 1, 1, "Marko", "Markovic", 1, "$2a$10$6alt.VA7aTEDancolVdNyufUxU4dgSDGVBptdyndbychHYIet8T66", "Srbija", "T1", "marko");
insert into user_custom values (2,  1, "Novi Sad", "anja@gmail.com", 1, 1, "Anja","Anjic", 1, "$2a$10$ZlNPXv4iJFDDWfJnvtddg.kyBucxn39z2kk9/2XUELa32BoD/TYvm", "Srbija", "T2", "anja");
insert into user_custom values (3,  1, "Novi Sad", "maja@gmail.com", 1, 1, "Maja","Majic", 1, "$2a$10$W2RRAVqR./XXCzV8r96DN.K99r0EIP6blg0OVpFivThHUGHNm6v2S", "Srbija", "T3", "maja");
insert into user_custom values (4,  1, "Novi Sad", "iva@gmail.com", 1, 0, "Iva","Ivic", 1, "$2a$10$9pzdyS44uHPbq3sKHHiux.itS8zgXse1ulSVduYLoZiI77Xbdk6vi", "Srbija", "T3", "iva");


insert into user_roles values (1,1);
insert into user_roles values (2,4);
insert into user_roles values (3,3);
insert into user_roles values (3,4);
insert into user_roles values (4,2);

insert into user_custom_areas values (2, 1);
insert into user_custom_areas values (3, 3);
insert into user_custom_areas values (3, 5);
insert into user_custom_areas values (4, 2);

insert into magazine values (1, 1, "Citaocima", 6589332, "Matematika u nauci", 2);
insert into magazine values (2, 1, "Autorima", 5412365, "Nacionalna geografija", 3);

insert into magazine_areas values (1, 3);
insert into magazine_areas values (1, 1);
insert into magazine_areas values (1, 5);

insert into magazine_areas values (2, 1);
insert into magazine_areas values (2, 2);
