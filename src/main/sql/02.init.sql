use product;


insert into tb_role(role_id, role_name) values('ROLE_ADMIN', '시스템관리자롤');

insert into tb_role(role_id, role_name) values('ROLE_OWNER', '사장님롤');

insert into tb_role_hierarchy(child_id, parent_id) values('ROLE_ADMIN', 'ROLE_OWNER');

ALTER TABLE tb_user AUTO_INCREMENT = 5;

INSERT INTO product.tb_user
(user_id, login_id, user_name, password, user_type, status, email, mobile, description, reg_id, reg_dttm, upd_id, upd_dttm)
VALUES(1, 'honggildong', '홍길동', '$2a$10$Hku6Djz9Mc/7nr4qVmYnSukk8oPM3mwxLl9i9lA9NmFycxwF2/HMi', 'OWNER', 'ACTIVE', 'hong@email.com', 'honggildong', '테스트데이터', NULL, now(), NULL, NULL);

INSERT INTO product.tb_user
(user_id, login_id, user_name, password, user_type, status, email, mobile, description, reg_id, reg_dttm, upd_id, upd_dttm)
VALUES(2, 'leesunsin', '이순신', '$2a$10$Zb7GT.x5mqHL.R6adBF3tOIwpq4ZokxcUM066D4R9RrmRQqc91rFq', 'OWNER', 'ACTIVE', 'lee@email.com', 'leesunsin', '', NULL, now(), NULL, NULL);

INSERT INTO product.tb_user
(user_id, login_id, user_name, password, user_type, status, email, mobile, description, reg_id, reg_dttm, upd_id, upd_dttm)
VALUES(3, 'ganggamchan', '강감찬', '$2a$10$P3R.wHfbGcH4bwkzYLHr2uAvORBwUojTmKcUZ6dV.XHM1Sd5clkPG', 'OWNER', 'ACTIVE', 'kang@email.com', 'ganggamchan', '', NULL, now(), NULL, NULL);

INSERT INTO product.tb_user
(user_id, login_id, user_name, password, user_type, status, email, mobile, description, reg_id, reg_dttm, upd_id, upd_dttm)
VALUES(4, 'admin', '관리자', '$2a$10$dLPnpm945j/aV/jGRLWNMuFRR5g36vrcxYszxfSbq7Xn/9woHywXu', 'ADMIN', 'ACTIVE', 'admin@email.com', '01042345678', '', NULL, now(), NULL, NULL);

INSERT INTO product.tb_user_role
(role_id, user_id)
VALUES('ROLE_OWNER', 1);

INSERT INTO product.tb_user_role
(role_id, user_id)
VALUES('ROLE_OWNER', 2);

INSERT INTO product.tb_user_role
(role_id, user_id)
VALUES('ROLE_OWNER', 3);

INSERT INTO product.tb_user_role
(role_id, user_id)
VALUES('ROLE_ADMIN', 4);

ALTER TABLE tb_product AUTO_INCREMENT = 37;

INSERT INTO product.tb_product
(product_id, user_id, category, price, cost, product_name, product_name_initials, description, barcode, expiration_date, `size`, reg_id, reg_dttm, upd_id, upd_dttm)
VALUES
    (1, 1, 'COFFEE', 4000, 1000, '아이스 아메리카노', 'ㅇㅇㅅ ㅇㅁㄹㅋㄴ', '아메리카노', '1910902818', '2024-10-03', 'small', 1, NOW(), NULL, NULL),
    (2, 1, 'TEA', 3500, 900, '레몬티', 'ㄹㅁㅌ', '상큼한 레몬티', '1910902819', '2024-10-04', 'large', 1, NOW(), NULL, NULL),
    (3, 1, 'DESSERT', 5000, 2000, '초코케이크', 'ㅊㅋㅋㅇㅋ', '달콤한 초코케이크', '1910902820', '2024-10-05', 'small', 1, NOW(), NULL, NULL),
    (4, 1, 'BAKERY', 3000, 1200, '크로와상', 'ㅋㄹㅇㅅ', '버터 향이 풍부한 크로와상', '1910902821', '2024-10-06', 'large', 1, NOW(), NULL, NULL),
    (5, 1, 'COFFEE', 4500, 1100, '카페라떼', 'ㅋㅍㄹㅌ', '부드러운 카페라떼', '1910902822', '2024-10-07', 'small', 1, NOW(), NULL, NULL),
    (6, 1, 'TEA', 3700, 950, '얼그레이티', 'ㅇㄹㄱㄹㅇ', '향이 깊은 얼그레이티', '1910902823', '2024-10-08', 'large', 1, NOW(), NULL, NULL),
    (7, 1, 'DESSERT', 5500, 2200, '티라미수', 'ㅌㄹㅁㅅ', '부드러운 티라미수', '1910902824', '2024-10-09', 'small', 1, NOW(), NULL, NULL),
    (8, 1, 'BAKERY', 3500, 1300, '브리오쉬', 'ㅂㄹㅇㅅ', '풍미가 좋은 브리오쉬', '1910902825', '2024-10-10', 'large', 1, NOW(), NULL, NULL),
    (9, 1, 'COFFEE', 5000, 1200, '카라멜 마키아토', 'ㅋㄹㅁ ㅁㅋㅇㅌ', '달콤한 카라멜 마키아토', '1910902826', '2024-10-11', 'small', 1, NOW(), NULL, NULL),
    (10, 1, 'TEA', 3600, 950, '페퍼민트티', 'ㅍㅍㅁㅌ', '상쾌한 페퍼민트티', '1910902827', '2024-10-12', 'large', 1, NOW(), NULL, NULL),
    (11, 1, 'DESSERT', 6000, 2300, '레드벨벳 케이크', 'ㄹㄷㅂㅂ ㅋㅇㅋ', '진한 레드벨벳 케이크', '1910902828', '2024-10-13', 'small', 1, NOW(), NULL, NULL),
    (12, 1, 'BAKERY', 3200, 1250, '데니쉬', 'ㄷㄴㅅ', '바삭한 데니쉬', '1910902829', '2024-10-14', 'large', 1, NOW(), NULL, NULL);


INSERT INTO product.tb_product
(product_id, user_id, category, price, cost, product_name, product_name_initials, description, barcode, expiration_date, `size`, reg_id, reg_dttm, upd_id, upd_dttm)
VALUES
    (13, 2, 'COFFEE', 4800, 1150, '바닐라 라떼', 'ㅂㄴㄹ ㄹㅌ', '달콤한 바닐라 라떼', '1910902830', '2024-10-15', 'small', 1, NOW(), NULL, NULL),
    (14, 2, 'TEA', 3400, 890, '자스민 티', 'ㅈㅅㅁㅌ', '향긋한 자스민 티', '1910902831', '2024-10-16', 'large', 1, NOW(), NULL, NULL),
    (15, 2, 'DESSERT', 5300, 2100, '치즈케이크', 'ㅊㅈㅋㅇㅋ', '부드러운 치즈케이크', '1910902832', '2024-10-17', 'small', 1, NOW(), NULL, NULL),
    (16, 2, 'BAKERY', 3300, 1250, '롤빵', 'ㄹㅃ', '부드러운 롤빵', '1910902833', '2024-10-18', 'large', 1, NOW(), NULL, NULL),
    (17, 2, 'COFFEE', 5100, 1200, '카페모카', 'ㅋㅍㅁㅋ', '달콤한 카페모카', '1910902834', '2024-10-19', 'small', 1, NOW(), NULL, NULL),
    (18, 2, 'TEA', 3200, 850, '녹차', 'ㄴㅊ', '건강한 녹차', '1910902835', '2024-10-20', 'large', 1, NOW(), NULL, NULL),
    (19, 2, 'DESSERT', 5800, 2200, '마카롱', 'ㅁㅋㄹㅇ', '달콤한 마카롱', '1910902836', '2024-10-21', 'small', 1, NOW(), NULL, NULL),
    (20, 2, 'BAKERY', 3100, 1200, '바게트', 'ㅂㄱㅌ', '고소한 바게트', '1910902837', '2024-10-22', 'large', 1, NOW(), NULL, NULL),
    (21, 2, 'COFFEE', 5200, 1250, '카라멜 라떼', 'ㅋㄹㅁ ㄹㅌ', '달콤한 카라멜 라떼', '1910902838', '2024-10-23', 'small', 1, NOW(), NULL, NULL),
    (22, 2, 'TEA', 3500, 900, '아이스티', 'ㅇㅇㅅㅌ', '시원한 아이스티', '1910902839', '2024-10-24', 'large', 1, NOW(), NULL, NULL),
    (23, 2, 'DESSERT', 6200, 2500, '레몬 파운드케이크', 'ㄹㅁ ㅍㅇㄷㅋㅇㅋ', '상큼한 레몬 파운드케이크', '1910902840', '2024-10-25', 'small', 1, NOW(), NULL, NULL),
    (24, 2, 'BAKERY', 3600, 1350, '크림치즈 베이글', 'ㅋㄹㅁㅊㅈ ㅂㅇㄱ', '부드러운 크림치즈 베이글', '1910902841', '2024-10-26', 'large', 1, NOW(), NULL, NULL),
    (25, 2, 'COFFEE', 4700, 1150, '에스프레소', 'ㅇㅅㅍㄹㅅㅅ', '진한 에스프레소', '1910902842', '2024-10-27', 'small', 1, NOW(), NULL, NULL);


INSERT INTO product.tb_product
(product_id, user_id, category, price, cost, product_name, product_name_initials, description, barcode, expiration_date, `size`, reg_id, reg_dttm, upd_id, upd_dttm)
VALUES
    (26, 3, 'COFFEE', 5200, 1250, '카푸치노', 'ㅋㅍㅊㄴ', '부드러운 카푸치노', '1910902845', '2024-10-18', 'small', 1, NOW(), NULL, NULL),
    (27, 3, 'TEA', 3300, 870, '얼그레이 밀크티', 'ㅇㄹㄱㄹㅇ ㅁㅋㅌ', '고소한 얼그레이 밀크티', '1910002834', '2024-10-19', 'large', 1, NOW(), NULL, NULL),
    (28, 3, 'DESSERT', 5800, 2400, '레몬 타르트', 'ㄹㅁ ㅌㄹㅌ', '상큼한 레몬 타르트', '1918902835', '2024-10-20', 'small', 1, NOW(), NULL, NULL),
    (29, 3, 'BAKERY', 3600, 1350, '소금빵', 'ㅅㄱㅃ', '고소한 소금빵', '1910902846', '2024-10-21', 'large', 1, NOW(), NULL, NULL),
    (30, 3, 'COFFEE', 4900, 1200, '더치커피', 'ㄷㅊㅋㅍ', '깊은 향의 더치커피', '1910972837', '2024-10-22', 'small', 1, NOW(), NULL, NULL),
    (31, 3, 'TEA', 3700, 950, '캐모마일 티', 'ㅋㅁㅁㅇ ㅌ', '진정 효과가 있는 캐모마일 티', '1915902838', '2024-10-23', 'large', 1, NOW(), NULL, NULL),
    (32, 3, 'DESSERT', 5600, 2200, '블루베리 머핀', 'ㅂㄹㅂㄹ ㅁㅍ', '촉촉한 블루베리 머핀', '1910972839', '2024-10-24', 'small', 1, NOW(), NULL, NULL),
    (33, 3, 'BAKERY', 3800, 1400, '베이글', 'ㅂㅇㄱ', '부드러운 베이글', '1913902840', '2024-10-25', 'large', 1, NOW(), NULL, NULL),
    (34, 3, 'COFFEE', 4700, 1150, '아포가토', 'ㅇㅍㄱㅌ', '달콤한 아포가토', '1913902841', '2024-10-26', 'small', 1, NOW(), NULL, NULL),
    (35, 3, 'BAKERY', 5200, 1300, '호밀빵', 'ㅎㅁㅃ', '맛있는 호밀빵', '1910932842', '2024-10-26', 'small', 1, NOW(), NULL, NULL),
    (36, 3, 'DESSERT', 6800, 1450, '딸기케이크', 'ㄸㄱㅋㅇㅋ', '달콤한 딸기케이크', '1910902843', '2024-10-26', 'small', 1, NOW(), NULL, NULL);




 
