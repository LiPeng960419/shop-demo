CREATE TABLE `mb_user` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `username` varchar(50) NOT NULL COMMENT '用户名',
   `password` varchar(32) NOT NULL COMMENT '密码，加密存储',
   `phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
   `email` varchar(50) DEFAULT NULL COMMENT '注册邮箱',
   `created` datetime NOT NULL,
   `updated` datetime NOT NULL,
   `openid` varchar(256) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';