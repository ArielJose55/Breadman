-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-11-2017 a las 07:36:29
-- Versión del servidor: 5.7.17
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `breadman`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `codigo_categoria` int(11) NOT NULL,
  `categoria` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`codigo_categoria`, `categoria`) VALUES
(1, 'Bebidas'),
(3, 'Panes de Queso'),
(2, 'Panes Integrales');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingredientes`
--

CREATE TABLE `ingredientes` (
  `codigo_ingrediente` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `id_insumo` int(11) NOT NULL,
  `cantidad_requerida` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `insumos`
--

CREATE TABLE `insumos` (
  `codigo_insumo` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `cantidad_almacenada` float NOT NULL,
  `unidad` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `insumos`
--

INSERT INTO `insumos` (`codigo_insumo`, `nombre`, `cantidad_almacenada`, `unidad`) VALUES
(1, 'Harina de Grigo haz de oro', 10, 'Kilogramos'),
(2, 'Harina de Soya integral', 10, 'Kilogramos'),
(3, 'Aceite de Oliva ', 10, 'Litros'),
(4, 'Azucar Blanca', 100, 'Kilogramos'),
(5, 'Azucar Morena', 100, 'Kilogramos'),
(6, 'Levadura', 20, 'Kilogramos'),
(7, 'Cereal de Avena', 20, 'Kilogramos'),
(8, 'Leche Desactosada', 30, 'Litros');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventarios`
--

CREATE TABLE `inventarios` (
  `codigo_inventario` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `cantidad_max` int(11) NOT NULL,
  `cantidad_min` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `inventarios`
--

INSERT INTO `inventarios` (`codigo_inventario`, `id_producto`, `cantidad`, `cantidad_max`, `cantidad_min`) VALUES
(1, 1, 23, 30, 5),
(2, 4, 23, 35, 5),
(3, 3, 18, 30, 5),
(4, 2, 0, 30, 5),
(5, 12, 9, 20, 5),
(6, 5, 2, 20, 5),
(7, 6, 9, 30, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lotes`
--

CREATE TABLE `lotes` (
  `codigo_lote` int(11) NOT NULL,
  `cedula` int(11) NOT NULL,
  `id_panadero` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `id_inventario` int(11) NOT NULL,
  `id_codigo_orden_produccion` int(11) NOT NULL,
  `cantidad_producida` int(11) NOT NULL,
  `fecha_de_produccion` date NOT NULL,
  `fecha_de_vencimiento` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `lotes`
--

INSERT INTO `lotes` (`codigo_lote`, `cedula`, `id_panadero`, `id_producto`, `id_inventario`, `id_codigo_orden_produccion`, `cantidad_producida`, `fecha_de_produccion`, `fecha_de_vencimiento`) VALUES
(1, 101011, 1, 1, 1, 1, 20, '2017-11-16', '2017-11-19'),
(2, 101011, 1, 4, 2, 3, 20, '2017-11-16', '2017-11-26'),
(3, 101011, 1, 3, 3, 4, 15, '2017-11-16', '2017-11-27'),
(4, 101011, 1, 2, 4, 5, 20, '2017-11-19', '2017-11-22'),
(5, 101011, 1, 12, 5, 7, 20, '2017-11-19', '2017-11-22'),
(6, 101011, 1, 1, 1, 6, 30, '2017-11-19', '2017-11-24'),
(7, 101011, 1, 1, 1, 2, 10, '2017-11-17', '2017-11-20'),
(8, 101011, 1, 4, 2, 8, 30, '2017-11-18', '2017-11-22'),
(9, 101011, 1, 6, 7, 12, 25, '2017-11-20', NULL),
(10, 101011, 1, 5, 6, 11, 20, '2017-11-20', NULL),
(11, 101011, 1, 3, 3, 10, 20, '2017-11-20', NULL);

--
-- Disparadores `lotes`
--
DELIMITER $$
CREATE TRIGGER `lotes_AFTER_INSERT` AFTER INSERT ON `lotes` FOR EACH ROW BEGIN
	UPDATE insumos, ingredientes SET insumos.cantidad_almacenada=insumos.cantidad_almacenada-(ingredientes.cantidad_requerida*NEW.cantidad_producida) WHERE ingredientes.id_insumo=insumos.codigo_insumo AND ingredientes.id_producto=NEW.id_producto;
	UPDATE inventarios SET inventarios.cantidad = inventarios.cantidad + NEW.cantidad_producida WHERE inventarios.codigo_inventario = NEW.id_inventario;
    UPDATE ordenes_producciones SET ordenes_producciones.estado = 4 WHERE ordenes_producciones.codigo_orden_produccion = NEW.id_codigo_orden_produccion;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenes_producciones`
--

CREATE TABLE `ordenes_producciones` (
  `codigo_orden_produccion` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `id_panadero` int(11) NOT NULL,
  `cedula` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `fecha_prevista` date NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ordenes_producciones`
--

INSERT INTO `ordenes_producciones` (`codigo_orden_produccion`, `id_producto`, `id_panadero`, `cedula`, `cantidad`, `fecha_prevista`, `estado`) VALUES
(1, 1, 1, 101011, 20, '2017-11-16', 4),
(2, 1, 1, 101011, 30, '2017-11-17', 4),
(3, 4, 1, 101011, 20, '2017-11-16', 4),
(4, 3, 1, 101011, 15, '2017-11-16', 4),
(5, 2, 1, 101011, 20, '2017-11-19', 4),
(6, 1, 1, 101011, 20, '2017-11-19', 4),
(7, 12, 1, 101011, 20, '2017-11-19', 4),
(8, 4, 1, 101011, 30, '2017-11-18', 4),
(9, 3, 1, 101011, 20, '2017-11-21', 1),
(10, 3, 1, 101011, 20, '2017-11-20', 4),
(11, 5, 1, 101011, 20, '2017-11-20', 4),
(12, 6, 1, 101011, 25, '2017-11-20', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_de_compra_insumos`
--

CREATE TABLE `orden_de_compra_insumos` (
  `codigo_compra_insumo` int(11) NOT NULL,
  `id_insumo` int(11) NOT NULL,
  `nit_proveedor` int(11) NOT NULL,
  `cantidad_de_compra` float NOT NULL,
  `fecha_de_compra` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `orden_de_compra_insumos`
--

INSERT INTO `orden_de_compra_insumos` (`codigo_compra_insumo`, `id_insumo`, `nit_proveedor`, `cantidad_de_compra`, `fecha_de_compra`) VALUES
(1, 1, 1, 10, '2017-11-18'),
(2, 3, 2, 10, '2017-11-18'),
(3, 2, 3, 10, '2017-11-18'),
(4, 4, 4, 100, '2017-11-19'),
(5, 5, 4, 100, '2017-11-19'),
(6, 7, 6, 20, '2017-11-17'),
(7, 8, 5, 30, '2017-11-16'),
(8, 6, 7, 20, '2017-11-16');

--
-- Disparadores `orden_de_compra_insumos`
--
DELIMITER $$
CREATE TRIGGER `orden_de_compra_insumos_AFTER_INSERT` AFTER INSERT ON `orden_de_compra_insumos` FOR EACH ROW BEGIN
	UPDATE insumos SET insumos.cantidad_almacenada=insumos.cantidad_almacenada+NEW.cantidad_de_compra WHERE insumos.codigo_insumo = NEW.id_insumo;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `panaderos`
--

CREATE TABLE `panaderos` (
  `codigo_panadero` int(11) NOT NULL,
  `cedula` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `panaderos`
--

INSERT INTO `panaderos` (`codigo_panadero`, `cedula`) VALUES
(1, 101011);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `codigo_producto` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `precio_de_venta` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`codigo_producto`, `id_categoria`, `nombre`, `precio_de_venta`) VALUES
(1, 1, 'Avena', 1200),
(2, 1, 'Limonada', 1000),
(3, 2, 'Croazan Salado', 800),
(4, 2, 'Croazan Dulce', 800),
(5, 3, 'Pan de Bono', 500),
(6, 3, 'Dona', 1000),
(11, 3, 'Pan de Mantequilla y Queso', 600),
(12, 2, 'Tostada', 500);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `codigo_proveedor` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `direccion` varchar(30) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`codigo_proveedor`, `nombre`, `direccion`, `telefono`) VALUES
(1, 'Harinera del Valle', 'Cali - Valle', '6245568'),
(2, 'Aceites Mar del Plata', NULL, NULL),
(3, 'Harinera de la Costa', NULL, NULL),
(4, 'Azuar Manuelita', NULL, NULL),
(5, 'Colanta S.A', NULL, NULL),
(6, 'Cereales Montiel S.A.', NULL, NULL),
(7, 'Almacenes Exito', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitudes_de_compra`
--

CREATE TABLE `solicitudes_de_compra` (
  `codigo_solicitud` int(11) NOT NULL,
  `cantidad_solicitada` float DEFAULT NULL,
  `fecha_maxima_de_compra` date DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `id_insumo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `cedula` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `username` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`cedula`, `nombre`, `username`, `email`, `password`, `role`) VALUES
(101010, 'Maria Alexandra Torres Guerrero', 'Maria_Alexandra', 'maria_ventas@breadman.com', '12345', 3),
(101011, 'Jose Mario Gonzales Mendoza', 'Jose_Mario', 'jose_produccion@breadman.com', '12345', 2),
(101012, 'Juan Sebastian Hernandez Meza', 'Juan_Sebastian', 'juan_admin@produccion.com', '12345', 1),
(101013, 'Marcos David Mendoza Orozco', 'Marco_David', 'marcos_sales@breadman.com', '12345', 3),
(101014, 'Maria Laura Gomez Flores', 'Maria_Laura', 'maria_sales@breadman.com', '12345', 3),
(101015, 'Amilkar Ruiz Lopez', 'Amilkar', 'amilkar_sales@breadman.com', '12345', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedores`
--

CREATE TABLE `vendedores` (
  `codigo_vendedor` int(11) NOT NULL,
  `cedula` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `vendedores`
--

INSERT INTO `vendedores` (`codigo_vendedor`, `cedula`) VALUES
(1, 101010),
(2, 101013),
(3, 101014),
(4, 101015);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `codigo_venta` int(11) NOT NULL,
  `cedula` int(11) NOT NULL,
  `id_vendedor` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `id_inventario` int(11) NOT NULL,
  `cantidad_comprada` int(11) NOT NULL,
  `valor_compra` float NOT NULL,
  `fecha_de_compra` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`codigo_venta`, `cedula`, `id_vendedor`, `id_producto`, `id_inventario`, `cantidad_comprada`, `valor_compra`, `fecha_de_compra`) VALUES
(5, 101010, 1, 1, 1, 5, 6000, '2017-11-16'),
(6, 101010, 1, 3, 3, 2, 1600, '2017-11-16'),
(7, 101010, 1, 4, 2, 3, 2400, '2017-11-16'),
(8, 101010, 1, 4, 2, 3, 2400, '2017-11-16'),
(9, 101010, 1, 3, 3, 3, 2400, '2017-11-18'),
(10, 101010, 1, 4, 2, 2, 1600, '2017-11-18'),
(11, 101010, 1, 1, 1, 2, 2400, '2017-11-18'),
(12, 101015, 4, 1, 1, 1, 1200, '2017-11-18'),
(13, 101015, 4, 3, 3, 1, 800, '2017-11-18'),
(14, 101015, 4, 1, 1, 3, 3600, '2017-11-18'),
(15, 101014, 3, 3, 3, 3, 2400, '2017-11-19'),
(16, 101014, 3, 4, 2, 2, 1600, '2017-11-19'),
(17, 101014, 3, 1, 1, 3, 3600, '2017-11-18'),
(18, 101015, 4, 4, 2, 4, 3200, '2017-11-17'),
(19, 101014, 3, 1, 1, 4, 4800, '2017-11-16'),
(20, 101015, 4, 2, 4, 2, 2000, '2017-11-19'),
(21, 101015, 4, 4, 2, 3, 2400, '2017-11-19'),
(22, 101010, 1, 2, 4, 2, 2000, '2017-11-19'),
(23, 101010, 1, 1, 1, 2, 2400, '2017-11-19'),
(24, 101010, 1, 1, 1, 3, 3600, '2017-11-17'),
(25, 101010, 1, 12, 5, 3, 1500, '2017-11-17'),
(26, 101010, 1, 4, 2, 3, 2400, '2017-11-17'),
(27, 101015, 4, 4, 2, 3, 3200, '2017-11-16'),
(28, 101010, 1, 12, 5, 2, 1000, '2017-11-20'),
(29, 101010, 1, 1, 1, 2, 2400, '2017-11-20'),
(30, 101010, 1, 5, 6, 3, 1500, '2017-11-20'),
(31, 101010, 1, 6, 7, 2, 2000, '2017-11-20'),
(32, 101010, 1, 2, 4, 1, 1000, '2017-11-20'),
(33, 101014, 3, 2, 4, 4, 4000, '2017-11-17'),
(35, 101014, 3, 4, 2, 4, 3200, '2017-11-21'),
(36, 101014, 3, 1, 1, 3, 3600, '2017-11-21'),
(37, 101015, 4, 12, 5, 4, 2000, '2017-11-21'),
(38, 101010, 1, 2, 4, 3, 3000, '2017-11-21'),
(39, 101010, 1, 3, 3, 5, 4000, '2017-11-21'),
(40, 101010, 1, 5, 6, 3, 1500, '2017-11-21'),
(41, 101010, 1, 1, 1, 3, 3600, '2017-11-21'),
(42, 101014, 3, 5, 6, 2, 1000, '2017-11-22'),
(43, 101014, 3, 2, 4, 2, 2000, '2017-11-22'),
(44, 101014, 3, 6, 7, 3, 3000, '2017-11-22'),
(45, 101014, 3, 5, 6, 3, 1500, '2017-11-22'),
(46, 101014, 3, 6, 7, 4, 4000, '2017-11-22'),
(47, 101014, 3, 12, 5, 2, 1000, '2017-11-22'),
(48, 101015, 4, 2, 4, 3, 3000, '2017-11-22'),
(49, 101015, 4, 6, 7, 4, 4000, '2017-11-22'),
(50, 101010, 1, 1, 1, 4, 4800, '2017-11-22'),
(51, 101010, 1, 2, 4, 3, 3000, '2017-11-22'),
(52, 101010, 1, 5, 6, 4, 2000, '2017-11-22'),
(53, 101010, 1, 6, 7, 3, 3000, '2017-11-22'),
(54, 101010, 1, 1, 1, 1, 1200, '2017-11-22'),
(56, 101014, 3, 3, 3, 1, 800, '2017-11-20'),
(57, 101014, 3, 3, 3, 2, 1600, '2017-11-20');

--
-- Disparadores `ventas`
--
DELIMITER $$
CREATE TRIGGER `ventas_AFTER_INSERT` AFTER INSERT ON `ventas` FOR EACH ROW BEGIN
	UPDATE inventarios SET inventarios.cantidad = inventarios.cantidad - NEW.cantidad_comprada WHERE inventarios.codigo_inventario = NEW.id_inventario;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `ventas_BEFORE_INSERT` BEFORE INSERT ON `ventas` FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);
    DECLARE cantidad_restante INT DEFAULT NULL;
    
    SELECT inventarios.cantidad FROM inventarios WHERE inventarios.codigo_inventario=NEW.id_inventario INTO cantidad_restante;
    
	IF cantidad_restante < NEW.cantidad_comprada THEN
    	SET msg = concat('No hay sufucientes productos en inventario ', NEW.id_inventario);
        signal sqlstate '45000' set message_text = msg;
    END IF;
END
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`codigo_categoria`),
  ADD UNIQUE KEY `categoria_UNIQUE` (`categoria`);

--
-- Indices de la tabla `ingredientes`
--
ALTER TABLE `ingredientes`
  ADD PRIMARY KEY (`codigo_ingrediente`),
  ADD KEY `fk_ingredientes_productos1_idx` (`id_producto`),
  ADD KEY `fk_ingredientes_insumos1_idx` (`id_insumo`);

--
-- Indices de la tabla `insumos`
--
ALTER TABLE `insumos`
  ADD PRIMARY KEY (`codigo_insumo`);

--
-- Indices de la tabla `inventarios`
--
ALTER TABLE `inventarios`
  ADD PRIMARY KEY (`codigo_inventario`,`id_producto`),
  ADD KEY `fk_inventarios_productos1_idx` (`id_producto`);

--
-- Indices de la tabla `lotes`
--
ALTER TABLE `lotes`
  ADD PRIMARY KEY (`codigo_lote`),
  ADD KEY `fk_lotes_panaderos1_idx` (`id_panadero`,`cedula`),
  ADD KEY `fk_lotes_inventarios1_idx` (`id_inventario`,`id_producto`),
  ADD KEY `fk_lotes_ordenes_producciones1_idx` (`id_codigo_orden_produccion`);

--
-- Indices de la tabla `ordenes_producciones`
--
ALTER TABLE `ordenes_producciones`
  ADD PRIMARY KEY (`codigo_orden_produccion`),
  ADD KEY `fk_ordenes_producciones_productos1_idx` (`id_producto`),
  ADD KEY `fk_ordenes_producciones_panaderos1_idx` (`id_panadero`,`cedula`);

--
-- Indices de la tabla `orden_de_compra_insumos`
--
ALTER TABLE `orden_de_compra_insumos`
  ADD PRIMARY KEY (`codigo_compra_insumo`),
  ADD KEY `fk_orden_de_compra_insumos_insumos1_idx` (`id_insumo`),
  ADD KEY `fk_orden_de_compra_insumos_proveedores1_idx` (`nit_proveedor`);

--
-- Indices de la tabla `panaderos`
--
ALTER TABLE `panaderos`
  ADD PRIMARY KEY (`codigo_panadero`,`cedula`),
  ADD KEY `fk_panaderos_usuarios1_idx` (`cedula`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`codigo_producto`),
  ADD UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  ADD KEY `fk_productos_categorias1_idx` (`id_categoria`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`codigo_proveedor`);

--
-- Indices de la tabla `solicitudes_de_compra`
--
ALTER TABLE `solicitudes_de_compra`
  ADD PRIMARY KEY (`codigo_solicitud`),
  ADD KEY `fk_solicitudes_de_compra_insumos1_idx` (`id_insumo`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`cedula`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`);

--
-- Indices de la tabla `vendedores`
--
ALTER TABLE `vendedores`
  ADD PRIMARY KEY (`codigo_vendedor`,`cedula`),
  ADD KEY `fk_vendedores_usuarios_idx` (`cedula`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`codigo_venta`),
  ADD KEY `fk_ventas_vendedores1_idx` (`id_vendedor`,`cedula`),
  ADD KEY `fk_ventas_inventarios1_idx` (`id_inventario`,`id_producto`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `codigo_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `ingredientes`
--
ALTER TABLE `ingredientes`
  MODIFY `codigo_ingrediente` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `insumos`
--
ALTER TABLE `insumos`
  MODIFY `codigo_insumo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de la tabla `inventarios`
--
ALTER TABLE `inventarios`
  MODIFY `codigo_inventario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `lotes`
--
ALTER TABLE `lotes`
  MODIFY `codigo_lote` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT de la tabla `ordenes_producciones`
--
ALTER TABLE `ordenes_producciones`
  MODIFY `codigo_orden_produccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT de la tabla `orden_de_compra_insumos`
--
ALTER TABLE `orden_de_compra_insumos`
  MODIFY `codigo_compra_insumo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de la tabla `panaderos`
--
ALTER TABLE `panaderos`
  MODIFY `codigo_panadero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `codigo_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `codigo_proveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `vendedores`
--
ALTER TABLE `vendedores`
  MODIFY `codigo_vendedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `codigo_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ingredientes`
--
ALTER TABLE `ingredientes`
  ADD CONSTRAINT `fk_ingredientes_insumos1` FOREIGN KEY (`id_insumo`) REFERENCES `insumos` (`codigo_insumo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ingredientes_productos1` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`codigo_producto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `inventarios`
--
ALTER TABLE `inventarios`
  ADD CONSTRAINT `fk_inventarios_productos1` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`codigo_producto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `lotes`
--
ALTER TABLE `lotes`
  ADD CONSTRAINT `fk_lotes_inventarios1` FOREIGN KEY (`id_inventario`,`id_producto`) REFERENCES `inventarios` (`codigo_inventario`, `id_producto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_lotes_ordenes_producciones1` FOREIGN KEY (`id_codigo_orden_produccion`) REFERENCES `ordenes_producciones` (`codigo_orden_produccion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_lotes_panaderos1` FOREIGN KEY (`id_panadero`,`cedula`) REFERENCES `panaderos` (`codigo_panadero`, `cedula`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ordenes_producciones`
--
ALTER TABLE `ordenes_producciones`
  ADD CONSTRAINT `fk_ordenes_producciones_panaderos1` FOREIGN KEY (`id_panadero`,`cedula`) REFERENCES `panaderos` (`codigo_panadero`, `cedula`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ordenes_producciones_productos1` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`codigo_producto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `orden_de_compra_insumos`
--
ALTER TABLE `orden_de_compra_insumos`
  ADD CONSTRAINT `fk_orden_de_compra_insumos_insumos1` FOREIGN KEY (`id_insumo`) REFERENCES `insumos` (`codigo_insumo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_orden_de_compra_insumos_proveedores1` FOREIGN KEY (`nit_proveedor`) REFERENCES `proveedores` (`codigo_proveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `panaderos`
--
ALTER TABLE `panaderos`
  ADD CONSTRAINT `fk_panaderos_usuarios1` FOREIGN KEY (`cedula`) REFERENCES `usuarios` (`cedula`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos_categorias1` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`codigo_categoria`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `solicitudes_de_compra`
--
ALTER TABLE `solicitudes_de_compra`
  ADD CONSTRAINT `fk_solicitudes_de_compra_insumos1` FOREIGN KEY (`id_insumo`) REFERENCES `insumos` (`codigo_insumo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vendedores`
--
ALTER TABLE `vendedores`
  ADD CONSTRAINT `fk_vendedores_usuarios` FOREIGN KEY (`cedula`) REFERENCES `usuarios` (`cedula`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `fk_ventas_inventarios1` FOREIGN KEY (`id_inventario`,`id_producto`) REFERENCES `inventarios` (`codigo_inventario`, `id_producto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ventas_vendedores1` FOREIGN KEY (`id_vendedor`,`cedula`) REFERENCES `vendedores` (`codigo_vendedor`, `cedula`) ON DELETE NO ACTION ON UPDATE NO ACTION;

DELIMITER $$
--
-- Eventos
--
CREATE DEFINER=`root`@`localhost` EVENT `actualizar_orden_vencidad` ON SCHEDULE EVERY 1 DAY STARTS '2017-11-20 23:59:59' ON COMPLETION PRESERVE DISABLE DO UPDATE ordenes_producciones SET ordenes_producciones = 2 WHERE ordenes_producciones = 3 AND ordenes_producciones.fecha_prevista < NOW()$$

CREATE DEFINER=`root`@`localhost` EVENT `actualizar_orden_actual` ON SCHEDULE EVERY 1 DAY STARTS '2017-11-20 23:59:00' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE ordenes_producciones SET ordenes_producciones = 3 WHERE ordenes_producciones = 1 AND ordenes_producciones.fecha_prevista = NOW()$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
