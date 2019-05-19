/* Administración de Bases de Datos 2018/2019 */
/******* ÍNDICES *******/

alter table users add index dni_idx (dni);

alter table orders add index order_code_idx (code);

alter table products add index product_code_idx (code);