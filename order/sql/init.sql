CREATE TABLE `order_info`
(
  `id`          int(11)  NOT NULL AUTO_INCREMENT,
  `orderNumber` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `isPay`       int(50)      DEFAULT NULL COMMENT '0 未支付，1已支付',
  `payId`       varchar(100) DEFAULT NULL,
  `userId`      int(50)      DEFAULT NULL,
  `created`     datetime NOT NULL,
  `updated`     datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;