CREATE TABLE `trx` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `contentId` int(11) DEFAULT NULL COMMENT '内容ID',
  `personId` int(11) DEFAULT NULL COMMENT '用户ID',
  `price` int(11) DEFAULT NULL COMMENT '购买价格',
  `time` bigint(20) DEFAULT NULL COMMENT '购买时间',
  `buy_num` int(11) NOT NULL COMMENT '购买商品数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
INSERT INTO `trx` VALUES ('21', '4', '2', '100', '1486561715575', '1');
INSERT INTO `trx` VALUES ('22', '1', '2', '5', '1487164177908', '3');
INSERT INTO `trx` VALUES ('23', '2', '2', '100', '1487249003964', '1');






CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userName` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码md5加密',
  `nickName` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `userType` tinyint(3) DEFAULT NULL COMMENT '类型，买家0，卖家1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `person` VALUES ('1', 'seller', '981c57a5cfb0f868e064904b8745766f', 'seller', '1');
INSERT INTO `person` VALUES ('2', 'buyer', '37254660e226ea65ce6f1efd54233424', 'buyer', '0');


CREATE TABLE `content` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `price` bigint(20) DEFAULT NULL COMMENT '当前价格',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `icon` varchar(200) DEFAULT NULL COMMENT '图片',
  `summary` varchar(200) DEFAULT NULL COMMENT '摘要',
  `text` varchar(200) DEFAULT NULL COMMENT '正文',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;

INSERT INTO `content` VALUES ('182', '100', 'asdasd', 'http://pic.weifengke.com/attachments/1/1441/74c5afcb1b549cd8ab68184b57280d07.jpg', 'asdas', 'asdas');
INSERT INTO `content` VALUES ('183', '100', 'asdas', 'http://59.110.141.65:8080/exam/pic/8fc3075b90c152e4dcebf9ef327e4b87.jpeg', 'dfasd', 'sadasda');
INSERT INTO `content` VALUES ('184', '100', 'sadsa', 'http://59.110.141.65:8080/exam/pic/931f0b7d3672f281cfa65f90b3077784.jpeg', 'asdasd', 'fwa');
INSERT INTO `content` VALUES ('185', '100', 'asdas', 'http://59.110.141.65:8080/exam/pic/461715bf6544ced8e3ce41a3331e2c5e.jpeg', 'asdas', 'dfgsafgg');
INSERT INTO `content` VALUES ('190', '852', '去玩儿', 'http://59.110.141.65:8080/exam/pic/92b2d9d4846fb70fa8312cd533cefc66.jpeg', '去玩儿', '秋日怒');
INSERT INTO `content` VALUES ('193', '555', '美美', 'http://59.110.141.65:8080/exam/pic/62576068b979ea59b4b3e38cc6b85893.jpeg', '亮亮', '亮亮');