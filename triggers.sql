/*Muestra las ganancias totales del videoclub en la variable @sum*/
CREATE TRIGGER ganancias_totales BEFORE INSERT ON orders_price FOR EACH ROW
SET
@sum = @sum + NEW.price;



/*Al updatear un producto, comprueba antes que su precio debe de ser positivo para mantener la consistencia*/
CREATE TRIGGER update_products BEFORE UPDATE ON products
FOR EACH ROW
BEGIN
IF NEW.price< 0 THEN
SET NEW.price= ABS(NEW.price);
END IF;
END;



/* Nos ayuda a asegurar que el stock nunca será negativo y que la bbdd quede inconsistente*/
CREATE TRIGGER check_stock BEFORE UPDATE ON products
FOR EACH ROW
BEGIN
IF NEW.stock< 0 THEN
SET NEW.stock= 0;
END IF;
END;



/*Calcularemos la nueva cantidad de gastos referentes a los empleados en la variable @Gastos cada vez que se inserte un nuevo registro*/
CREATE TRIGGER gastos_totales
AFTER INSERT ON salaries
FOR EACH ROW
SET
@Gastos = @Gastos+ NEW.amount;
