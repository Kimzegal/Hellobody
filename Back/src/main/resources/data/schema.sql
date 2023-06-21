DROP DATABASE hellobody;
CREATE DATABASE hellobody CHARACTER SET=utf8mb4;

USE hellobody;

DROP TABLE `user`;
CREATE TABLE `user` (
  	`userId` VARCHAR(100) PRIMARY KEY,
    `password` VARCHAR(32) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    `nickname` VARCHAR(45) NULL,
    `leagueId` INT NOT NULL DEFAULT 0,
    `masterId` INT NOT NULL DEFAULT 0,
    `adminId` INT NOT NULL DEFAULT 0,
    `registDate` DATE NOT NULL,
    `visitedDate` DATE NULL,
    `addComment` TEXT NOT NULL DEFAULT "잘 부탁드립니다."
)ENGINE=InnoDB;

INSERT INTO `user`
VALUES ('ssafy', '1234', '김싸피', '김싸피', 0, 0, 0, '2023-05-19', '2023-05-19');

select * from user;