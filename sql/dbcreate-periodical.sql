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
    `first_name`  varchar(45)         NOT NULL,
    `last_name`   varchar(45)         NOT NULL,
    `locale`  varchar(45),
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
INSERT INTO `magazine`
VALUES (DEFAULT, 'Computer Music', 220, 'computer-music.jpg', 3);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Digital Camera', 170, 'digital-camera.jpg', 3);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Mac Format', 80, 'mac-format.jpg', 3);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Mac Life', 190, 'mac-life.jpg', 3);
INSERT INTO `magazine`
VALUES (DEFAULT, 'PC Gamer', 200, 'pc-gamer.jpg', 3);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Play', 210, 'play.jpg', 3);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Bow International', 120, 'bow-international.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'FourFourTwo', 90, 'fourfourtwo.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Motor Boat And Yachting', 165, 'motor-boat-and-yachting.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Mountain Bike Rider', 100, 'mountain-bike-rider.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Practical Boat Owner', 140, 'practical-boat-owner.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Yachting Monthly', 130, 'yachting-monthly.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Horses And Hound', 185, 'horses-and-hound.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Sail The British Isles', 170, 'sail-the-british-isles.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Legends Of The NBA', 115, 'legends-of-the-nba.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Ultimate Guide To The Masters', 125, 'ultimate-guide-to-the-masters.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Legends Of Tho NFL', 205, 'legends-of-the-nfl.jpg', 2);
INSERT INTO `magazine`
VALUES (DEFAULT, 'The Modern Home', 295, 'the-modern-home.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'You Perfect Garden', 125, 'you-perfect-garden.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'The Home Scandi Bible', 200, 'the-home-scandi-bible.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Victorian Houses', 205, 'victorian-houses.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Declutter Your Home', 275, 'declutter-your-home.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Home Building And Renovating', 250, 'home-building-and-renovating2.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'The Ultimate Guide To Building Your Own Home', 260, 'the-ultimate-guide-to-building-your-own-home.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Homebuilding And Renovating', 265, 'homebuilding-and-renovating.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Homes And Gardens', 295, 'homes-and-gardens.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Real Homes', 225, 'real-homes.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, '25 Beautiful Homes', 255, '25-beautiful-homes.jpg', 1);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Classic Rock Special AC/DC', 110, 'classic-rock-special-ac-dc.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Classic Rock Special Jimi Hendrix', 220, 'classic-rock-special-jimi-hendrix.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, '100 Greatest Rock Songs Of All Time', 105, '100-greatest-rock-songs-of-all-time.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Classic Rock Special Queen', 120, 'classic-rock-special-queen.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'The Producers Music Theory Handbook', 130, 'the-producers-music-theory-handbook.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Classic Rock Special Iron Maiden', 185, 'classic-rock-special-iron-maiden.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Classic Rock Special Metallica', 110, 'classic-rock-special-metallica.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Abbey Road', 180, 'abbey-road.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Fender Stratocaster Handbook', 205, 'fender-stratocaster-handbook.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'The Story Of Burice Springsteen', 110, 'the-story-of-burice-springsteen.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Guitar World', 200, 'guitar-world.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Guitarist', 150, 'guitarist.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Total Guitar', 180, 'total-guitar.jpg', 4);
INSERT INTO `magazine`
VALUES (DEFAULT, 'Metal Hammer', 120, 'metal-hammer.jpg', 4);

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
