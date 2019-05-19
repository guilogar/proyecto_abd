/* Administración de Bases de Datos 2018/2019 */
/******* Funciones y procedicmientos *******/

/* Flag necesaria para poder crear funciones */
SET GLOBAL log_bin_trust_function_creators = 1;

/* Calcula el beneficio actual, aplicando la reducción del iva */
delimiter //
create function Benefit () returns double
	begin
		declare benefit double;
		declare iva double;
		set iva = 21.0;
		select sum(qty * price) into benefit from orders_products;
		set benefit = benefit - (benefit * iva / 100);
		return benefit;
	end;//
delimiter ;

/* Devuelve los productos con stock inferior al argumento */
delimiter //
create procedure checkStockLower (in s int)
	begin
		select code, name, stock from products where stock < s;
	end;//
delimiter ;

/* Comprueba si existe un producto */
delimiter //
create function productExist(cadena varchar(255)) returns boolean
begin
    declare num_rows int;
    declare existe   boolean;
    set     num_rows     = (select count(*) from products);
    set     existe       = false;

    if(num_rows >= 1) then
        set existe = true;
    end if;

    return existe;
end //
delimiter ;
delimiter ;