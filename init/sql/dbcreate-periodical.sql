SET FOREIGN_KEY_CHECKS = 0;
DROP DATABASE `periodical`;
CREATE DATABASE `periodical` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_ru_0900_ai_ci;
USE `periodical`;


CREATE TABLE IF NOT EXISTS `periodical`.`role`
(
    `id`   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_ru_0900_ai_ci;

INSERT INTO `role`
VALUES (1, 'admin');
INSERT INTO `role`
VALUES (2, 'client');

CREATE TABLE IF NOT EXISTS `periodical`.`user`
(
    `id`          INT UNSIGNED        NOT NULL AUTO_INCREMENT,
    `login`       VARCHAR(45)         NOT NULL,
    `password`    VARCHAR(255)        NOT NULL,
    `balance`     DECIMAL(10, 2)      NULL DEFAULT 0.00,
    `first_name`  varchar(20)         NOT NULL,
    `last_name`   varchar(20)         NOT NULL,
    `locale_name` VARCHAR(20),
    `role_id`     INT UNSIGNED        NOT NULL,
    `blocked`     TINYINT(1) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_role_user`
        FOREIGN KEY (`role_id`)
            REFERENCES `periodical`.`role` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_ru_0900_ai_ci;
INSERT INTO `user`
VALUES (DEFAULT, 'admin', 'admin', DEFAULT, 'Ivanov', 'Ivan', NULL, 0, 0);
INSERT INTO `user`
VALUES (DEFAULT, 'client', 'client', DEFAULT, 'Петр', 'Петров', NULL, 1, 0);
INSERT INTO `user`
VALUES (DEFAULT, 'петров', 'петров', 10000.00, 'Иван', 'Петров', NULL, 1, 0);

CREATE TABLE IF NOT EXISTS `periodical`.`theme`
(
    `id`   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;

INSERT INTO `theme`
VALUES (1, 'Interior');
INSERT INTO `theme`
VALUES (2, 'Sport');
INSERT INTO `theme`
VALUES (3, 'IT world');
INSERT INTO `theme`
VALUES (4, 'Music');

CREATE TABLE IF NOT EXISTS `periodical`.`magazine`
(
    `id`       INT UNSIGNED            NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(255)            NOT NULL,
    `price`    DECIMAL(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
    `image`    VARCHAR(255)            NOT NULL,
    `theme_id` INT UNSIGNED,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_magazine_1`
        FOREIGN KEY (`theme_id`)
            REFERENCES `periodical`.`theme` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_ru_0900_ai_ci;
INSERT INTO `magazine`
VALUES (DEFAULT, 'Ideal Home', 210, 'ideal-home.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Country Homes and interiors', 210, 'country-homes-and-interiors.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Livingetc', 250, 'livingetc.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Classic Rock', 70, 'classic-rock.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Bass Player', 50, 'bass-player.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Guitar Technique', 100, 'guitar-technique.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Golf Monthly', 250, 'golf-monthly.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Procycling', 200, 'procycling.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Maximum PC', 160, 'maximum-pc.jpg', 3);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Linux Format', 260, 'linux-format.jpg', 3);

CREATE TABLE IF NOT EXISTS `periodical`.`subscription`
(
    `user_id`     INT UNSIGNED NOT NULL,
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
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_ru_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
