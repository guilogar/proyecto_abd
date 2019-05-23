/* Administración de Bases de Datos 2018/2019 */
/******* Semilla *******/

INSERT INTO suppliers (id, cif, business, phone, email, address) 
VALUES ('1', '123456789', 'Marvel', '987654321', 'marvel@marvel.com', 'Calle Falsa 123, Springfield');

INSERT INTO suppliers (id, cif, business, phone, email, address) 
VALUES ('2', '456789213', 'Marvel', '654987321', 'empresa2@empresa2.com', 'Calle Verdadera 123, Cádiz');

INSERT INTO suppliers (id, cif, business, phone, email, address) 
VALUES ('3', '369258147', 'Marvel', '666555444', 'empresa1@empresa1.com', 'Calle Jerezana 123, Jerez');


INSERT INTO products (id, code, name, description, date, price, discount, stock) 
VALUES ('1', 'prod1', 'Harry Potter I ', 'primera parte de la saga', '2019-05-23', '20', '10', '25');

INSERT INTO products (id, code, name, description, date, price, discount, stock) 
VALUES ('2', 'prod2', 'Harry Potter II ', 'segunda parte de la saga', '2019-05-23', '20', '10', '25');

INSERT INTO products (id, code, name, description, date, price, discount, stock) 
VALUES ('3', 'prod3', 'Harry Potter III ', 'tercera parte de la saga', '2019-05-23', '20', '10', '25');


INSERT INTO suppliers_products (product_id, supplier_id) VALUES ('1', '1');
INSERT INTO suppliers_products (product_id, supplier_id) VALUES ('2', '2');
INSERT INTO suppliers_products (product_id, supplier_id) VALUES ('3', '3');


INSERT INTO categories (id, category_id, name) VALUES ('1', NULL, 'Películas');
INSERT INTO categories (id, category_id, name) VALUES ('2', '1', 'Películas de Ciencia Ficción');
INSERT INTO categories (id, category_id, name) VALUES ('3', NULL, 'Juegos');


INSERT INTO products_categories (category_id, product_id) VALUES ('1', '1');
INSERT INTO products_categories (category_id, product_id) VALUES ('1', '1');
INSERT INTO products_categories (category_id, product_id) VALUES ('1', '1');


INSERT INTO users (id, name, surname, dni, phone, email) 
VALUES ('1', 'Pepito', 'Grillo', '789456123', '666555444', 'pepito@example.com');

INSERT INTO users (id, name, surname, dni, phone, email) 
VALUES ('1', 'Donald', 'Trump', '456897123', '787878456', 'pepito@example.com');

INSERT INTO users (id, name, surname, dni, phone, email) 
VALUES ('1', 'Will', 'Smith', '123456789', '147147147', 'pepito@example.com');


INSERT INTO roles (id, name) VALUES ('1', 'administrador');

INSERT INTO roles (id, name) VALUES ('2', 'colaborador');

INSERT INTO roles (id, name) VALUES ('3', 'eventual');


INSERT INTO users_roles (rol_id, user_id) VALUES ('1', '2');

INSERT INTO users_roles (rol_id, user_id) VALUES ('2', '1');

INSERT INTO users_roles (rol_id, user_id) VALUES ('3', '3');


INSERT INTO workers (id, user_id, iban, ss) 
VALUES ('1', '1', 'CAIXA-99999999', 'SS121212');

INSERT INTO workers (id, user_id, iban, ss) 
VALUES ('2', '2', 'CAIXA-233213213', 'SS434343');

INSERT INTO workers (id, user_id, iban, ss) 
VALUES ('3', '3', 'CAIXA-123312132', 'SS566556');


INSERT INTO salaries (id, worker_id, amount, date) 
VALUES ('1', '1', '10000', '2019-02-06');

INSERT INTO salaries (id, worker_id, amount, date) 
VALUES ('2', '1', '50000', '2019-04-12');

INSERT INTO salaries (id, worker_id, amount, date) 
VALUES ('3', '1', '999', '2019-06-09');
 

INSERT INTO clients (id, user_id, address) 
VALUES ('1', '1', 'Calle falsa 123');

INSERT INTO clients (id, user_id, address) 
VALUES ('2', '2', 'Calle verdadera 123');

INSERT INTO clients (id, user_id, address) 
VALUES ('3', '3', 'Calle inventada');



INSERT INTO statuses (id, name) VALUES ('1', 'Reservado');

INSERT INTO statuses (id, name) VALUES ('2', 'Finalizado');

INSERT INTO statuses (id, name) VALUES ('3', 'Cancelado');


INSERT INTO orders (id, worker_id, client_id, status_id, code, created_at, start_at, end_at, delivered_at, discount, is_percent)
VALUES ('1', '1', '1', '1', 'codeorder1', '2019-05-23', '2019-05-24', '2019-05-25', '2019-05-25', NULL, '0');

INSERT INTO orders (id, worker_id, client_id, status_id, code, created_at, start_at, end_at, delivered_at, discount, is_percent)
VALUES ('2', '2', '3', '2', 'codeorder2', '2019-05-23', '2019-05-24', '2019-05-25', '2019-05-25', NULL, '1');

INSERT INTO orders (id, worker_id, client_id, status_id, code, created_at, start_at, end_at, delivered_at, discount, is_percent)
VALUES ('3', '2', '2', '3', 'codeorder3', '2019-05-23', '2019-05-24', '2019-05-25', '2019-05-25', '10', '0');


INSERT INTO orders_products (order_id, product_id, qty, price)
VALUES ('1', '1', '1', '20');

INSERT INTO orders_products (order_id, product_id, qty, price)
VALUES ('2', '2', '2', '40');

INSERT INTO orders_products (order_id, product_id, qty, price)
VALUES ('1', '3', '1', '30');