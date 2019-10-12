CREATE TABLE `payment_info`
(
  `id`              int(11) NOT NULL AUTO_INCREMENT,
  `userId`          int(11)       DEFAULT NULL,
  `typeId`          int(2)        DEFAULT NULL,
  `orderId`         varchar(50)   DEFAULT NULL,
  `price`           decimal(10,0) DEFAULT NULL,
  `source`          varchar(10)   DEFAULT NULL,
  `state`           int(2)        DEFAULT NULL,
  `created`         datetime      DEFAULT NULL,
  `updated`         datetime      DEFAULT NULL,
  `platformorderId` varchar(100)  DEFAULT NULL,
  `payMessage` varchar(1024)  DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;