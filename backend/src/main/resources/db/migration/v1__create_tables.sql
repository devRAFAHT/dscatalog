CREATE DATABASE IF NOT EXISTS `dscatalog` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dscatalog`;

/* Table structure for table `tb_category` */
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `created_at` timestamp NULL DEFAULT NULL,
                               `name` varchar(255) DEFAULT NULL,
                               `update_at` timestamp NULL DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/* Table structure for table `tb_product` */
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `date` timestamp NULL DEFAULT NULL,
                              `description` text,
                              `img_url` varchar(255) DEFAULT NULL,
                              `name` varchar(255) DEFAULT NULL,
                              `price` double DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/* Table structure for table `tb_product_category` */
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category` (
                                       `product_id` bigint NOT NULL,
                                       `category_id` bigint NOT NULL,
                                       PRIMARY KEY (`product_id`,`category_id`),
                                       KEY `FK5r4sbavb4nkd9xpl0f095qs2a` (`category_id`),
                                       CONSTRAINT `FK5r4sbavb4nkd9xpl0f095qs2a` FOREIGN KEY (`category_id`) REFERENCES `tb_category` (`id`),
                                       CONSTRAINT `FKgbof0jclmaf8wn2alsoexxq3u` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/* Table structure for table `tb_role` */
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `authority` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/* Table structure for table `tb_user` */
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `email` varchar(255) DEFAULT NULL,
                           `first_name` varchar(255) DEFAULT NULL,
                           `last_name` varchar(255) DEFAULT NULL,
                           `password` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK_4vih17mube9j7cqyjlfbcrk4m` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/* Table structure for table `tb_user_role` */
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
                                `user_id` bigint NOT NULL,
                                `role_id` bigint NOT NULL,
                                PRIMARY KEY (`user_id`,`role_id`),
                                KEY `FKea2ootw6b6bb0xt3ptl28bymv` (`role_id`),
                                CONSTRAINT `FK7vn3h53d0tqdimm8cp45gc0kl` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
                                CONSTRAINT `FKea2ootw6b6bb0xt3ptl28bymv` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
