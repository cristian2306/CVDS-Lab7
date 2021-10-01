-- ---------------------------------------------------
-- Tabla 'ORD_PEDIDOS'
-- ---------------------------------------------------
CREATE TABLE IF NOT EXISTS `ORD_PEDIDOS`(
  `codigo` INT NOT NULL,
  `fecha_radicacion` DATETIME NOT NULL);

-- ---------------------------------------------------
-- Tabla 'ORD_DATALLES_PEDIDO'
-- ---------------------------------------------------
CREATE TABLE IF NOT EXISTS `ORD_DETALLES_PEDIDO`(
  `pedido_fk` INT NOT NULL,
  `producto_fk` INT NOT NULL,
  `cantidad` INT NOT NULL);

-- ---------------------------------------------------
-- Tabla 'ORD_PRODUCTOS'
-- ---------------------------------------------------
CREATE TABLE IF NOT EXISTS `ORD_PRODUCTOS`(
  `codigo` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `precio` INT NOT NULL);
