/* Administración de Bases de Datos 2018/2019 */
/******* Transacciones *******/

/* Decrementamos el stock y lo añadimos al pedido */
start transaction;
update products set stock = stock - 1 where code = 'qwerty';
insert into orders_products (order_id, product_id, qty, price) values (
	(select id from orders where code = 'order1'),
	(select id from products where code = 'qwerty'),
	1, (select price - IFNULL(discount, 0) from products where code = 'qwerty'));
commit;

/* Eliminamos un producto del pedido, aumentando el stock correspondiente */
start transaction;
delete from orders_products
	where order_id =
	(select id from orders where code = 'order1')
	and product_id =
	(select id from products where code = 'qwerty');
update products set stock = stock + 1 where code = 'qwerty';
commit;

/* Eliminar un proveedor */
start transaction;
delete from suppliers_products
	where supplier_id =
	(select id from suppliers where cif = '789456123');
delete from suppliers where cif = '789456123';
commit;