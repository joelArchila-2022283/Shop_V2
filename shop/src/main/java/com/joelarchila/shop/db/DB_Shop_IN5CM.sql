drop database if exists Shop_in5cm;
create database Shop_in5cm;
use Shop_in5cm;
 
----------------------- ENTIDADES ------------------------------------
create table Clientes(
    dpi_cliente int auto_increment not null,
    nombre_cliente varchar(50),
    apellido_cliente varchar(50),
    direccion varchar(100),
    estado int,
    primary key (dpi_cliente)
);
create table Usuarios(
    codigo_usuario int auto_increment not null,
    username varchar(45),
    password varchar(45),
    email varchar(60),
    rol varchar(45),
    estado int,
    primary key (codigo_usuario)
);
create table Productos(
    codigo_producto int auto_increment not null,
    nombre_producto varchar(60),
    precio decimal(10,2),
    stock int,
    estado int,
    primary key (codigo_producto)
);
create table Ventas(
    codigo_venta int auto_increment not null,
    fecha_venta date,
    total decimal(10,2),
    estado int,
    Clientes_dpi_cliente int,
    Usuarios_codigo_usuario int,
    primary key (codigo_venta),
    constraint fk_Ventas_Clientes foreign key (Clientes_dpi_cliente) references Clientes(dpi_cliente),
    constraint fk_Ventas_Usuarios foreign key (Usuarios_codigo_usuario) references Usuarios(codigo_usuario)
);
create table DetalleVenta(
    codigo_detalle_venta int auto_increment not null,
    cantidad int,
    precio_unitario decimal(10,2),
    subtotal decimal(10,2),
    Productos_codigo_producto int,
    Ventas_codigo_venta int,
    primary key (codigo_detalle_venta),
    constraint fk_Detalle_Productos foreign key (Productos_codigo_producto) references Productos(codigo_producto),
    constraint fk_Detalle_Ventas foreign key (Ventas_codigo_venta) references Ventas(codigo_venta)
);
-- _________________________________________________________________________________

------------------------------------------------------------
-- PROCEDIMIENTOS ALMACENADOS PARA LA TABLA CLIENTES
------------------------------------------------------------

delimiter $$
create procedure sp_agregar_cliente(in p_nombre varchar(50), in p_apellido varchar(50), in p_direccion varchar(100), in p_estado int)
begin
    insert into Clientes (nombre_cliente, apellido_cliente, direccion, estado) 
    values (p_nombre, p_apellido, p_direccion, p_estado);
end$$
delimiter ;

delimiter $$
create procedure sp_listar_clientes()
begin
    select * from Clientes;
end$$
delimiter ;

delimiter $$
create procedure sp_buscar_cliente_id(in p_dpi int)
begin
    select * from Clientes where dpi_cliente = p_dpi;
end$$
delimiter ;

delimiter $$
create procedure sp_editar_cliente(in p_dpi int, in p_nombre varchar(50), in p_apellido varchar(50), in p_direccion varchar(100), in p_estado int)
begin
    update Clientes 
    set nombre_cliente = p_nombre, apellido_cliente = p_apellido, direccion = p_direccion, estado = p_estado
    where dpi_cliente = p_dpi;
end$$
delimiter ;

delimiter $$
create procedure sp_eliminar_cliente(in p_dpi int)
begin
    delete from Clientes where dpi_cliente = p_dpi;
end$$
delimiter ;


------------------------------------------------------------
-- PROCEDIMIENTOS ALMACENADOS PARA LA TABLA USUARIOS
------------------------------------------------------------

delimiter $$
	create procedure sp_agregar_usuario(in p_username varchar(45), in p_password varchar(45), in p_email varchar(60), in p_rol varchar(45), in p_estado int)
	begin
		insert into Usuarios (username, password, email, rol, estado) 
		values (p_username, p_password, p_email, p_rol, p_estado);
	end$$
delimiter ;

delimiter $$
	create procedure sp_listar_usuarios()
	begin
		select * from Usuarios;
	end$$
delimiter ;

delimiter $$
	create procedure sp_buscar_usuario_id(in p_codigo int)
	begin
		select * from Usuarios where codigo_usuario = p_codigo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_editar_usuario(in p_codigo int, in p_username varchar(45), in p_password varchar(45), in p_email varchar(60), in p_rol varchar(45), in p_estado int)
	begin
		update Usuarios 
		set username = p_username, password = p_password, email = p_email, rol = p_rol, estado = p_estado
		where codigo_usuario = p_codigo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_eliminar_usuario(in p_codigo int)
	begin
		delete from Usuarios where codigo_usuario = p_codigo;
	end$$
delimiter ;


------------------------------------------------------------
-- PROCEDIMIENTOS ALMACENADOS PARA LA TABLA PRODUCTOS
------------------------------------------------------------

delimiter $$
	create procedure sp_agregar_producto(in p_nombre varchar(60), in p_precio decimal(10,2), in p_stock int, in p_estado int)
	begin
		insert into Productos (nombre_producto, precio, stock, estado) 
		values (p_nombre, p_precio, p_stock, p_estado);
	end$$
delimiter ;

delimiter $$
	create procedure sp_listar_productos()
	begin
		select * from Productos;
	end$$
delimiter ;

delimiter $$
	create procedure sp_buscar_producto_id(in p_codigo int)
	begin
		select * from Productos where codigo_producto = p_codigo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_editar_producto(in p_codigo int, in p_nombre varchar(60), in p_precio decimal(10,2), in p_stock int, in p_estado int)
	begin
		update Productos 
		set nombre_producto = p_nombre, precio = p_precio, stock = p_stock, estado = p_estado
		where codigo_producto = p_codigo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_eliminar_producto(in p_codigo int)
	begin
		delete from Productos where codigo_producto = p_codigo;
	end$$
delimiter ;


------------------------------------------------------------
-- PROCEDIMIENTOS ALMACENADOS PARA LA TABLA VENTAS
------------------------------------------------------------

delimiter $$
	create procedure sp_agregar_venta(in p_fecha date, in p_total decimal(10,2), in p_estado int, in p_dpi_cliente int, in p_codigo_usuario int)
	begin
		insert into Ventas (fecha_venta, total, estado, Clientes_dpi_cliente, Usuarios_codigo_usuario) 
		values (p_fecha, p_total, p_estado, p_dpi_cliente, p_codigo_usuario);
	end$$
delimiter ;

delimiter $$
	create procedure sp_listar_ventas()
	begin
		select * from Ventas;
	end$$
delimiter ;

delimiter $$
	create procedure sp_buscar_venta_id(in p_codigo int)
	begin
		select * from Ventas where codigo_venta = p_codigo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_editar_venta(in p_codigo int, in p_fecha date, in p_total decimal(10,2), in p_estado int, in p_dpi_cliente int, in p_codigo_usuario int)
	begin
		update Ventas 
		set fecha_venta = p_fecha, total = p_total, estado = p_estado, Clientes_dpi_cliente = p_dpi_cliente, Usuarios_codigo_usuario = p_codigo_usuario
		where codigo_venta = p_codigo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_eliminar_venta(in p_codigo int)
	begin
		delete from Ventas where codigo_venta = p_codigo;
	end$$
delimiter ;


------------------------------------------------------------
-- PROCEDIMIENTOS ALMACENADOS PARA LA TABLA DETALLE VENTA
------------------------------------------------------------

delimiter $$
	create procedure sp_agregar_detalle(in p_cantidad int, in p_precio decimal(10,2), in p_subtotal decimal(10,2), in p_codigo_producto int, in p_codigo_venta int)
	begin
		insert into DetalleVenta (cantidad, precio_unitario, subtotal, Productos_codigo_producto, Ventas_codigo_venta) 
		values (p_cantidad, p_precio, p_subtotal, p_codigo_producto, p_codigo_venta);
	end$$
delimiter ;

delimiter $$
	create procedure sp_listar_detalles()
	begin
		select * from DetalleVenta;
	end$$
delimiter ;

delimiter $$
	create procedure sp_buscar_detalle_id(in p_codigo int)
	begin
		select * from DetalleVenta where codigo_detalle_venta = p_codigo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_editar_detalle(in p_codigo int, in p_cantidad int, in p_precio decimal(10,2), in p_subtotal decimal(10,2), in p_codigo_producto int, in p_codigo_venta int)
	begin
		update DetalleVenta 
		set cantidad = p_cantidad, precio_unitario = p_precio, subtotal = p_subtotal, Productos_codigo_producto = p_codigo_producto, Ventas_codigo_venta = p_codigo_venta
		where codigo_detalle_venta = p_codigo;
	end$$
delimiter ;

delimiter $$
	create procedure sp_eliminar_detalle(in p_codigo int)
	begin
		delete from DetalleVenta where codigo_detalle_venta = p_codigo;
	end$$
delimiter ;

-- --------------------- REGISTROS ----------------------------------------------------------------

-- clientes
call sp_agregar_cliente('juan', 'perez', 'ciudad de guatemala', 1);
call sp_agregar_cliente('maria', 'lopez', 'antigua guatemala', 1);
call sp_agregar_cliente('carlos', 'gomez', 'quetzaltenango', 1);
call sp_agregar_cliente('ana', 'martinez', 'escuintla', 1);
call sp_agregar_cliente('luis', 'rodriguez', 'chimaltenango', 1);
call sp_agregar_cliente('elena', 'hernandez', 'sacatepéquez', 1);
call sp_agregar_cliente('pedro', 'castillo', 'petén', 1);
call sp_agregar_cliente('lucia', 'morales', 'izabal', 1);
call sp_agregar_cliente('jorge', 'ramirez', 'zacapa', 1);
call sp_agregar_cliente('sonia', 'paiz', 'alta verapaz', 1);

-- usuarios (roles: administrador, cajero, vendedor)
call sp_agregar_usuario('gcalderon', 'pass123', 'gabriel.calderon@shop.com', 'administrador', 1);
call sp_agregar_usuario('jarchila', 'admin789', 'joel.archila@shop.com', 'administrador', 1);
call sp_agregar_usuario('ecallejas', 'segura01', 'efrain.callejas@shop.com', 'administrador', 1);
call sp_agregar_usuario('mromero', 'caja2026', 'marta.romero@shop.com', 'CLIENTE', 1);
call sp_agregar_usuario('lvasquez', 'ventas15', 'luis.vasquez@shop.com', 'CLIENTE', 1);
call sp_agregar_usuario('asolorzano', 'clavereal', 'ana.solorzano@shop.com', 'CLIENTE', 1);
call sp_agregar_usuario('rgramajo', 'roberto44', 'roberto.gramajo@shop.com', 'CLIENTE', 1);
call sp_agregar_usuario('kmonzon', 'p@ssword', 'karla.monzon@shop.com', 'CLIENTE', 1);
call sp_agregar_usuario('fvillatoro', 'fher2026', 'fernando.villatoro@shop.com', 'CLIENTE', 1);
call sp_agregar_usuario('dortiz', 'diego99', 'diego.ortiz@shop.com', 'CLIENTE', 1);

-- productos
call sp_agregar_producto('leche entera 1l', 12.50, 50, 1);
call sp_agregar_producto('pan integral', 18.00, 30, 1);
call sp_agregar_producto('arroz 1lb', 5.50, 100, 1);
call sp_agregar_producto('aceite vegetal', 22.00, 40, 1);
call sp_agregar_producto('azucar 1lb', 4.50, 200, 1);
call sp_agregar_producto('cafe molido', 35.00, 25, 1);
call sp_agregar_producto('detergente 500g', 15.00, 60, 1);
call sp_agregar_producto('jabon de manos', 8.00, 80, 1);
call sp_agregar_producto('pasta dental', 14.00, 45, 1);
call sp_agregar_producto('cereal de maiz', 28.00, 20, 1);

-- ventas
call sp_agregar_venta('2026-03-01', 50.50, 1, 1, 1);
call sp_agregar_venta('2026-03-02', 25.00, 1, 2, 2);
call sp_agregar_venta('2026-03-03', 110.00, 1, 3, 3);
call sp_agregar_venta('2026-03-04', 15.50, 1, 4, 4);
call sp_agregar_venta('2026-03-05', 85.00, 1, 5, 5);
call sp_agregar_venta('2026-03-06', 45.00, 1, 6, 6);
call sp_agregar_venta('2026-03-07', 32.00, 1, 7, 7);
call sp_agregar_venta('2026-03-08', 64.00, 1, 8, 8);
call sp_agregar_venta('2026-03-09', 12.00, 1, 9, 9);
call sp_agregar_venta('2026-03-10', 95.00, 1, 10, 10);

-- detalleventa
call sp_agregar_detalle(2, 12.50, 25.00, 1, 1);
call sp_agregar_detalle(1, 18.00, 18.00, 2, 2);
call sp_agregar_detalle(4, 5.50, 22.00, 3, 3);
call sp_agregar_detalle(1, 22.00, 22.00, 4, 4);
call sp_agregar_detalle(2, 4.50, 9.00, 5, 5);
call sp_agregar_detalle(1, 35.00, 35.00, 6, 6);
call sp_agregar_detalle(2, 15.00, 30.00, 7, 7);
call sp_agregar_detalle(3, 8.00, 24.00, 8, 8);
call sp_agregar_detalle(1, 14.00, 14.00, 9, 9);
call sp_agregar_detalle(1, 28.00, 28.00, 10, 10);