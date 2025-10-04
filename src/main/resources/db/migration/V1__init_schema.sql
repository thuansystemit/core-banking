-- V1__init_schema.sql
-- Core Banking Schema Initialization for MySQL

CREATE TABLE `country` (
    `pk` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `country_code` CHAR(3) NOT NULL,
    `country_name` VARCHAR(100) NOT NULL UNIQUE
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

--  USER TABLE
CREATE TABLE `user` (
    `pk` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_name` VARCHAR(100) NOT NULL UNIQUE,
    `full_name` VARCHAR(200) NOT NULL,
    `email` VARCHAR(150) NOT NULL,
    `phone` VARCHAR(50),
    `status` INT NOT NULL DEFAULT 1,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `createdBy` VARCHAR(150) NOT NULL,
    `updatedBy` VARCHAR(150) NOT NULL,
    `country_code` CHAR(3) NOT NULL
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

-- ACCOUNT TABLE
CREATE TABLE `account` (
    `pk` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `account_number` VARCHAR(50) NOT NULL UNIQUE,
    `user_fk` BIGINT NOT NULL,
    `balance` DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    `currency` INT NOT NULL DEFAULT 1,
    `type` INT  NOT NULL DEFAULT 1,
    `status` INT NOT NULL DEFAULT 0,
    `version` BIGINT NOT NULL DEFAULT 0,
    `createdBy` VARCHAR(150) NOT NULL,
    `updatedBy` VARCHAR(150) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_account_user` FOREIGN KEY (`user_fk`) REFERENCES `user`(pk)
        ON DELETE CASCADE
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

-- TRANSACTION TABLE
CREATE TABLE `transaction` (
    `pk` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `transaction_reference` VARCHAR(50) NOT NULL UNIQUE,
    `account_fk` BIGINT NOT NULL,
    `amount` DECIMAL(18,2) NOT NULL,
    `type` INT NOT NULL,
    `description` VARCHAR(255),
    `status` INT NOT NULL,
    `createdBy` VARCHAR(150) NOT NULL,
    `updatedBy` VARCHAR(150) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_transaction_account` FOREIGN KEY (`account_fk`) REFERENCES `account`(`pk`)
        ON DELETE CASCADE
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

--  INDEXES
CREATE INDEX `idx_user_email` ON `user`(`email`);
CREATE INDEX `idx_account_user` ON `account`(`user_fk`);
CREATE INDEX `idx_account_number` ON `account`(`account_number`);
CREATE INDEX `idx_transaction_account` ON `transaction`(`account_fk`);
CREATE INDEX `idx_transaction_type` ON `transaction`(`type`);


