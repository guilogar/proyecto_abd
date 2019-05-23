--------------------------------------------------

create user principal identified by 'principal';
grant all privileges on videoclub.* to principal;

--------------------------------------------------

create user principalSecundario identified by 'principalSecundario';
grant all privileges on videoclub.orders to principalSecundario;

--------------------------------------------------

create user colaborador identified by 'colaborador';
grant select, update on videoclub.* to colaborador;


--------------------------------------------------

create user eventual identified by 'eventual';
grant select on videoclub.* to eventual;

--------------------------------------------------

create user junior identified by 'junior';
grant select, insert, update, delete on videoclub.clients to junior;

--------------------------------------------------

create user externo identified by 'externo';
grant select on videoclub.suppliers to externo;
grant select on videoclub.suppliers_products to externo;

--------------------------------------------------

create user externoSecundario identified by 'externoSecundario';
grant select on videoclub.statuses to externoSecundario;

--------------------------------------------------
