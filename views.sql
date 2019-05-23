/*Para tener control sobre los trabajadores y sus ventas a los clientes*/
CREATE VIEW producto_status AS
SELECT
  client_id,
  worker_id,
  name
FROM
  orders
  INNER JOIN statuses ON orders.status_id = statuses.id;


/*Para ver una tabla con datos basicos y roles de los usuarios*/
CREATE VIEW usuario_rol AS
SELECT
  name,
  surname,
  dni,
  address,
  rol_id
FROM
  users
  INNER JOIN user_roles ON users.id = user_roles.user_id;


/*Obtiene todos los datos bancarios por si el encargado de realizar las transferencias de sueldo lo necesita*/
CREATE VIEW datos_bancarios AS
SELECT
  name,
  surname,
  phone,
  ss,
  dni,
  amount,
  iban
FROM
  workers
  INNER JOIN salary ON workers.id = salary.worker_id;


/*Almacena el nombre del producto con el nombre del proveedor*/
CREATE VIEW producto_proveedor AS
SELECT
	business
FROM
	suppliers_products LEFT JOIN suppliers ON suppliers_products.supplier_id = suppliers.id
UNION
SELECT
	name
FROM
	suppliers_products RIGHT JOIN products ON suppliers_products.product_id = products.id


/*Vista con todos todos los pedidos de cada cliente*/
CREATE VIEW pedidios_cliente AS
SELECT
  name,
  surname,
  address,
  dni,
  orders.id
FROM
  clients
  LEFT JOIN orders ON client.id = orders.client_id;


-- /////////////////////////////////////////////////////////////////////////////
--                            OPTIMIZACIONES
-- /////////////////////////////////////////////////////////////////////////////

-- 1º Optimización
-- query_cache_size = 64M   -- query cache of 64M
-- query_cache_type = 2     -- only use cache in SELECT

-- 2º Optimización

-- create table statuses(
-- 	id int primary key,
-- 	name varchar(255)
-- );
--
-- create table orders(
-- 	id bigint primary key,
-- 	worker_id bigint,
-- 	client_id bigint,
-- 	status_id int,
-- 	code varchar(255),
-- 	created_at date,
-- 	start_at date,
-- 	end_at date,
-- 	delivered_at date,
-- 	discount double,
-- 	is_percent tinyint,
-- 	foreign key (worker_id) references workers(id),
-- 	foreign key (client_id) references clients(id),
-- 	foreign key (status_id) references statuses(id)
-- );

-- Lo anterior cambia a:

-- create table orders(
-- 	id bigint primary key,
-- 	worker_id bigint,
-- 	client_id bigint,
-- 	status enum('Bueno', 'Malo', 'Regular'),
-- 	code varchar(255),
-- 	created_at date,
-- 	start_at date,
-- 	end_at date,
-- 	delivered_at date,
-- 	discount double,
-- 	is_percent tinyint,
-- 	foreign key (worker_id) references workers(id),
-- 	foreign key (client_id) references clients(id),
-- 	foreign key (status_id) references statuses(id)
-- );

-- 3º Optimización

-- create table users(
-- 	id bigint primary key,
-- 	name varchar(255),
-- 	surname varchar(255),
-- 	dni varchar(9),
-- 	phone varchar(255),
-- 	email varchar(255)
-- );
--
-- create table roles(
-- 	id bigint primary key,
-- 	name varchar(255)
-- );
--
-- create table users_roles(
-- 	rol_id bigint,
-- 	user_id bigint,
-- 	foreign key (rol_id) references roles(id),
-- 	foreign key (user_id) references users(id)
-- );

-- Lo anterior cambia a:

-- create table roles(
-- 	id bigint primary key,
-- 	name varchar(255)
-- );

-- create table users(
-- 	id bigint primary key,
-- 	name varchar(255),
-- 	surname varchar(255),
-- 	dni varchar(9),
-- 	phone varchar(255),
-- 	email varchar(255),
--  rol_id bigint,
--  foreign key (rol_id) references roles(id)
-- );

-- 4º Optimización

-- start transaction;
-- update products set stock = stock - 1 where code = 'qwerty';
-- insert into orders_products (order_id, product_id, qty, price) values (
-- 	(select id from orders where code = 'order1'),
-- 	(select id from products where code = 'qwerty'),
-- 	1, (select price - IFNULL(discount, 0) from products where code = 'qwerty'));
-- commit;

-- Lo anterior cambia a:

-- start transaction;
-- update products set stock = stock - 1 where code = 'qwerty';
--
-- select @id_qwerty:=id, @price_qwerty:=price - IFNULL(discount, 0) from products where code = 'qwerty';
-- insert into orders_products (order_id, product_id, qty, price) values (
-- 	(select id from orders where code = 'order1'),
-- 	@id_qwerty, 1, @price_qwerty);
-- commit;
