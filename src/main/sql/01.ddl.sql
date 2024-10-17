DROP DATABASE product;
DROP USER 'product'@'%';

create database product character set utf8mb4 collate utf8mb4_general_ci;

CREATE USER 'product'@'localhost' IDENTIFIED BY 'product@1234';
CREATE USER 'product'@'%' IDENTIFIED BY 'product@1234';

GRANT ALL PRIVILEGES ON product.* TO 'product'@'localhost';
GRANT ALL PRIVILEGES ON product.* TO 'product'@'%';

use product;

CREATE TABLE `tb_api_log` (
`api_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '아이디',
`client_ip` varchar(40) NOT NULL COMMENT '클라이언트IP',
`url` text NOT NULL COMMENT '주소',
`content_type` varchar(128) DEFAULT NULL COMMENT 'Content-Type',
`http_method` varchar(7) NOT NULL COMMENT 'HTTP 메서드',
`query_string` text COMMENT '쿼리스트링',
`request_body` text COMMENT '요청본문',
`response_body` text NOT NULL COMMENT '응답본문',
`reg_dttm` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성시간',
PRIMARY KEY (`api_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='요청 로그 테이블';


CREATE TABLE `tb_user` (
`user_id` bigint(20) AUTO_INCREMENT COMMENT '유저아이디',
`login_id` varchar(20) NOT NULL COMMENT '로그인아이디',
`user_name` varchar(50) NOT NULL COMMENT '이름',
`password` varchar(80) NOT NULL COMMENT '비밀번호',
`user_type` varchar(20) NOT NULL COMMENT '타입',
`status` enum('ACTIVE','DELETED') NOT NULL DEFAULT 'ACTIVE' COMMENT '상태',
`email` varchar(100) DEFAULT NULL COMMENT '이메일',
`mobile` varchar(11) NOT NULL COMMENT '핸드폰번호',
`description` text COMMENT '설명',
`reg_id` bigint(20) DEFAULT NULL COMMENT '등록자아이디',
`reg_dttm` DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록시간',
`upd_id` bigint(20) DEFAULT NULL COMMENT '수정자아이디',
`upd_dttm` datetime(6) DEFAULT NULL COMMENT '수정 시간',
PRIMARY KEY (`user_id`),
UNIQUE KEY `uniq_tb_user_mobile` (`mobile`),
UNIQUE KEY `uniq_tb_user_email` (`email`),
UNIQUE KEY `uniq_tb_user_login_id` (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 정보 테이블';


CREATE TABLE `tb_role` (
  `role_id` varchar(30) NOT null COMMENT '롤아이디',
  `role_name` varchar(30) DEFAULT NULL COMMENT '롤명',
  `description` text COMMENT '설명',
  `reg_id` bigint(20) DEFAULT null COMMENT '등록자아이디',
  `upd_id` bigint(20) DEFAULT null COMMENT '수정자아읻;',
  `reg_dttm` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록시간',
  `upd_dttm` datetime(6) DEFAULT null COMMENT '수정시간',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='롤 테이블';


CREATE TABLE `tb_user_role` (
  `role_id` varchar(30) NOT null COMMENT '롤아이디',
  `user_id` bigint(20) NOT null COMMENT '유저아이디',
  `reg_id` bigint(20) DEFAULT null COMMENT '등록자아이디',
  `reg_dttm` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록시간',
  PRIMARY KEY (`role_id`,`user_id`),
  INDEX idx_tb_user_role_user_id (`user_id`),
  CONSTRAINT fk_tb_user_role_user_id FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`),
  CONSTRAINT fk_tb_user_role_role_id FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='유저롤 테이블';


CREATE TABLE `tb_role_hierarchy` (
  `child_id` varchar(30) NOT null COMMENT '상위아이디',
  `parent_id` varchar(30) NOT null COMMENT '하위아이디',
  `reg_id` bigint(20) DEFAULT null COMMENT '등록자아이디',
  `reg_dttm` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록시간',
  PRIMARY KEY (`child_id`,`parent_id`),
  CONSTRAINT fk_tb_role_hierarchy_child_id FOREIGN KEY (`child_id`) REFERENCES `tb_role` (`role_id`),
  CONSTRAINT fk_tb_role_hierarchy_parent_id FOREIGN KEY (`parent_id`) REFERENCES `tb_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='롤하이어라키 테이블';


CREATE TABLE `tb_product`(
 `product_id` bigint(20) AUTO_INCREMENT COMMENT '상품아이디',
 `user_id` bigint(20) NOT NULL COMMENT '유저아이디',
 `category` enum('COFFEE','TEA', 'DESSERT', 'BAKERY') NOT NULL COMMENT '카테고리',
 `price` int(11) NOT NULL COMMENT '가격',
 `cost` int(11) NOT NULL COMMENT '원가',
 `product_name` varchar(255) NOT NULL COMMENT '이름',
 `product_name_initials` varchar(255) NOT NULL COMMENT '상품 이름의 초성',
 `description` text NOT NULL COMMENT '설명',
 `barcode` varchar(50) NOT NULL COMMENT '바코드',
 `expiration_date` date  NOT NULL COMMENT '유통기한',
 `size` enum('small','large') NOT NULL COMMENT '사이즈',
 `reg_id` bigint(20) DEFAULT NULL COMMENT '등록자아이디',
 `reg_dttm` DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록시간',
 `upd_id` bigint(20) DEFAULT NULL COMMENT '수정자아이디',
 `upd_dttm` datetime(6) DEFAULT NULL COMMENT '수정 시간',
 PRIMARY KEY (`product_id`),
 UNIQUE KEY `uniq_tb_product_user_product_size` (`user_id`,`product_name`,`size`),
 UNIQUE KEY `uniq_barcode` (`barcode`),
 KEY `idx_product_user` (`product_id`,`user_id`),
 CONSTRAINT fk_tb_product_user_id FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`),
 FULLTEXT KEY `product_name` (`product_name`) WITH PARSER ngram,
 FULLTEXT KEY `product_name_initials` (`product_name_initials`) WITH PARSER ngram
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='상품 테이블';

