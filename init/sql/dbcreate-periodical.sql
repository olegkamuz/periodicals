SET FOREIGN_KEY_CHECKS=0;
DROP DATABASE `periodical`;
SET FOREIGN_KEY_CHECKS=1;
CREATE DATABASE `periodical` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_ru_0900_ai_ci ;
USE `periodical`;

CREATE TABLE IF NOT EXISTS `periodical`.`user` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `login` VARCHAR(45) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `balance` DECIMAL(10,2) NULL DEFAULT 0.00,
    `role_id` INT UNSIGNED NOT NULL,
    `blocked` TINYINT(1) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_ru_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `periodical`.`role` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `user_id` INT UNSIGNED,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    INDEX `fk_role_user_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_role_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `periodical`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_ru_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `periodical`.`theme` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `periodical`.`magazine` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10,2) UNSIGNED NOT NULL DEFAULT 0.00,
    `image` LONGBLOB NULL,
    `theme_id` INT UNSIGNED,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_magazine_1`
        FOREIGN KEY (`theme_id`)
        REFERENCES `periodical`.`theme` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_ru_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `periodical`.`Subscription` (
    `user_id` INT UNSIGNED NOT NULL,
    `magazine_id` INT UNSIGNED NOT NULL,
    INDEX `fk_Subscription_1_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_Subscription_2_idx` (`magazine_id` ASC) VISIBLE,
    PRIMARY KEY (`user_id`, `magazine_id`),
    CONSTRAINT `fk_user`
        FOREIGN KEY (`user_id`)
        REFERENCES `periodical`.`user` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT `fk_magazine`
        FOREIGN KEY (`magazine_id`)
        REFERENCES `periodical`.`magazine` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_ru_0900_ai_ci;
