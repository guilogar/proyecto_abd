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
  Orders.id
FROM
  clients
  LEFT JOIN orders ON client.id = orders.client_id;
