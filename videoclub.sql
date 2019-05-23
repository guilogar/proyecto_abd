/* Administración de Bases de Datos 2018/2019 */

create database if not exists videoclub;
use videoclub;

create table users(
	id bigint primary key,
	name varchar(255),
	surname varchar(255),
	dni varchar(9),
	phone varchar(255),
	email varchar(255)
);

create table roles(
	id bigint primary key,
	name varchar(255)
);

create table users_roles(
	rol_id bigint,
	user_id bigint,
	foreign key (rol_id) references roles(id),
	foreign key (user_id) references users(id)
);

create table workers(
	id bigint primary key,
	name varchar(255),
	surname varchar(255),
	dni varchar(9),
	phone varchar(255),
	email varchar(255),
	iban varchar(255),
	ss varchar(255)
);

create table salaries(
	id bigint primary key,
	worker_id bigint,
	amount double,
	`date` date,
	foreign key (worker_id) references workers(id)
);

create table clients(
	id bigint primary key,
	user_id bigint,
	address varchar(255),
	foreign key (user_id) references users(id)
);

create table statuses(
	id int primary key,
	name varchar(255)
);

create table orders(
	id bigint primary key,
	worker_id bigint,
	client_id bigint,
	status_id int,
	code varchar(255),
	created_at date,
	start_at date,
	end_at date,
	delivered_at date,
	discount double,
	is_percent tinyint,
	foreign key (worker_id) references workers(id),
	foreign key (client_id) references clients(id),
	foreign key (status_id) references statuses(id)
);

create table categories(
	id bigint primary key,
	category_id bigint,
	name varchar(255),
	foreign key (category_id) references categories(id)
);

create table products(
	id bigint primary key,
	code varchar(255),
	name varchar(255),
	description	varchar(1024),
	`date` date,
	price double,
	discount double,
	stock int
);

create table products_categories(
	category_id bigint,
	product_id bigint,
	foreign key (category_id) references categories(id),
	foreign key (product_id) references products(id)
);

create table orders_products(
	order_id bigint,
	product_id bigint,
	qty int,
	price double,
	foreign key (order_id) references orders(id),
	foreign key (product_id) references products(id)
);

create table suppliers(
	id bigint primary key,
	cif varchar(255),
	business varchar(255),
	phone varchar(255),
	email varchar(255),
	address varchar(255)
);

create table suppliers_products(
	product_id bigint,
	supplier_id bigint,
	foreign key (product_id) references products(id),
	foreign key (supplier_id) references suppliers(id)
);
