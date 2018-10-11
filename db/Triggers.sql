CREATE TRIGGER `ventas_AFTER_INSERT` AFTER INSERT ON `ventas`
 FOR EACH ROW BEGIN
	UPDATE inventarios SET inventarios.cantidad = inventarios.cantidad - NEW.cantidad_comprada WHERE inventarios.codigo_inventario = NEW.id_inventario;
END

CREATE TRIGGER `orden_de_compra_insumos_AFTER_INSERT` AFTER INSERT ON `orden_de_compra_insumos`
 FOR EACH ROW BEGIN
	UPDATE insumos SET insumos.cantidad_almacenada=insumos.cantidad_almacenada+NEW.cantidad_de_compra WHERE insumos.codigo_insumo = NEW.id_insumo;
END

CREATE TRIGGER `lotes_AFTER_INSERT` AFTER INSERT ON `lotes`
 FOR EACH ROW BEGIN
	UPDATE insumos, ingredientes SET insumos.cantidad_almacenada=insumos.cantidad_almacenada-(ingredientes.cantidad_requerida*NEW.cantidad_producida) WHERE ingredientes.id_insumo=insumos.codigo_insumo AND ingredientes.id_producto=NEW.id_producto;
	UPDATE inventarios SET inventarios.cantidad = inventarios.cantidad + NEW.cantidad_producida WHERE inventarios.codigo_inventario = NEW.id_inventario;
    UPDATE ordenes_producciones SET ordenes_producciones.estado = 4 WHERE ordenes_producciones.codigo_orden_produccion = NEW.id_codigo_orden_produccion;
END

CREATE TRIGGER `ventas_BEFORE_INSERT` BEFORE INSERT ON `ventas`
 FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);
    DECLARE cantidad_restante INT DEFAULT NULL;
    
    SELECT inventarios.cantidad FROM inventarios WHERE inventarios.codigo_inventario=NEW.id_inventario INTO cantidad_restante;
    
	IF cantidad_restante < NEW.cantidad_comprada THEN
    	SET msg = concat('No hay sufucientes productos en inventario ', NEW.id_inventario);
        signal sqlstate '45000' set message_text = msg;
    END IF;
END