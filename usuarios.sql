--------------------------------------------------

create user principal identified by 'principal';
grant all privileges on videoclub.* to principal;

--------------------------------------------------

create user colaborador identified by 'colaborador';
grant select, update on videoclub.* to colaborador;


--------------------------------------------------

create user eventual identified by 'eventual';
grant select on videoclub.* to eventual;

--------------------------------------------------

create user junior identified by 'junior';
grant select, insert, update, delete on videoclub.clientes to junior;

--------------------------------------------------

create user externo identified by 'externo';
grant select on videoclub.alquileres to externo;
grant select on videoclub.alquieres_clientes to externo;

--------------------------------------------------
