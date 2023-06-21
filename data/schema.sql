CREATE SCHEMA IF NOT EXISTS `hellobody` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

create database if not exists `hellobodydb`;

USE `hellobodydb` ;

USE hellobody;

Drop  TABLE IF EXISTS  `user` ;
CREATE TABLE IF NOT EXISTS  `user` (
  	`uId` VARCHAR(100) PRIMARY KEY,
    `pw` VARCHAR(32) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
	`nick` VARCHAR(45) NULL,
    `lId` VARCHAR(200) NOT NULL DEFAULT 0,
    `mId` TINYINT NOT NULL DEFAULT 0,
    `aId` TINYINT NOT NULL DEFAULT 0,
    `rDate` DATE NOT NULL,
    `vDate` DATE NOT NULL,
    `rBadge` VARCHAR(255) NULL ,
    `rExercise` VARCHAR(255) NULL ,
    `rImg` VARCHAR(255) NULL,
    `adCom` TEXT NULL
)ENGINE=InnoDB;

DROP TABLE IF EXISTS  `league` ;
CREATE TABLE IF NOT EXISTS  `league` (
  	`lId` VARCHAR(100) PRIMARY KEY,
    `region` VARCHAR(50) NOT NULL,
    `area` VARCHAR(50) NOT NULL
)ENGINE=InnoDB;

Drop TABLE IF EXISTS  `certification` ;
CREATE TABLE IF NOT EXISTS `certification` (
  `exName` VARCHAR(100) NOT NULL PRIMARY KEY,
  `exUnit` VARCHAR(32) NOT NULL,
  `score` VARCHAR(45) NOT NULL,
  `cRate` DOUBLE NOT NULL,
  `exDef` TEXT NOT NULL,
  `exStd` TEXT NOT NULL
  );
  
Drop TABLE IF EXISTS  `record` ;
CREATE TABLE IF NOT EXISTS `record` (
  `cId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `uId` VARCHAR(100) NOT NULL,
  `mId` VARCHAR(100) NULL,
  `mCom` TEXT NULL,
  `exName` VARCHAR(100) NOT NULL,
  `exScore` INT NOT NULL,
  `pof` INT DEFAULT 0,
  `respect` INT DEFAULT 0,
  `cVideo` TEXT NOT NULL,
  `uDate` DATE NOT NULL,
  `cDate` DATE NULL
  );
  
Drop  TABLE IF EXISTS  `master` ;
CREATE TABLE IF NOT EXISTS `master` (
  `uId` VARCHAR(100) NOT NULL PRIMARY KEY,
  `career` VARCHAR(32) NOT NULL,
  `job` VARCHAR(45) NOT NULL
  );
  
DROP TABLE IF EXISTS  `badge` ;
CREATE TABLE IF NOT EXISTS `badge` (
  `bName` VARCHAR(45) NOT NULL PRIMARY KEY,
  `bDesc` TEXT NOT NULL,
  `bGrade` VARCHAR(45) NOT NULL,
  `bImg` TEXT NOT NULL
  );

UPDATE record 
SEt cDate=null WHERE mId="";
  
DROP TABLE IF EXISTS  `userBadge` ;
CREATE TABLE IF NOT EXISTS `userBadge` (
  `bId` INT NOT NULL PRIMARY KEY,
  `uId` VARCHAR(255) NOT NULL,
  `bName` VARCHAR(45) NOT NULL,
  `achDate` DATE NOT NULL
  );
  
  
DROP TABLE IF EXISTS  `review` ;
CREATE TABLE IF NOT EXISTS `review` (
  `rId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `cId` INT NOT NULL,
  `uId` VARCHAR(255) NOT NULL,
  `rDate` DATE NOT NULL,
  `rContent` TEXT NOT NULL  
  );
  

INSERT INTO `user`
VALUES
('ssafy', '1234', '김싸피', '김싸피', "대전광역시 유성구", 1, 1, '2023-05-21', '2023-05-21','3대운동_마스터 맨몸운동_마스터 체력왕', '데드리프트 오버헤드프레스 플랭크', 'default.png', '한계다 싶을때 한개 더'),
('yllh325@gmail.com', '1234', '연제경', '녹차왕', 31, 1, 1, '2023-05-21', '2023-05-21','3대운동_마스터 맨몸운동_마스터 체력왕', '데드리프트 오버헤드프레스 플랭크', 'default.png', '한계다 싶을때 한개 더');


select * 
from record;

SELECT @@sql_mode;
SET SESSION sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

SELECT r.*
FROM record r
WHERE (r.exName, r.exScore) IN (
    SELECT exName, MAX(exScore)
    FROM record
    GROUP BY exName
);
SELECT uId FROM user WHERE lId = "대전광역시 유성구";
SELECT *
		FROM record
		WHERE uId IN (SELECT uId FROM user WHERE lId = "대전광역시 유성구")
		AND exName = "레그프레스" AND pof = 1 ORDER BY exScore DESC;

DROP TABLE IF EXISTS  `review` ;
CREATE TABLE IF NOT EXISTS `review` (
  `rId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `cId` INT NOT NULL,
  `uId` VARCHAR(255) NOT NULL,
  `rDate` DATE NOT NULL,
  `rContent` TEXT NOT NULL  
  );
  
  INSERT INTO `review`(`cId`,`uId`,`rDate`,`rContent`)
  VALUES (1,"연제경","2023-05-25","좋은데?"),
  (1,"이세훈","2023-05-22","잘자"),
  (1,"김소연","2023-05-21","좋아요.."),
  (1,"송소연","2023-05-14","얘들아~"),
  (1,"강현","2023-05-22","o.~"),
  (1,"이은성","2023-05-01","형~"),
  (1,"박기현","2023-05-09","지인짜 안돼"),
  (1,"김라현","2023-05-09","??"),
  (1,"유민국","2023-05-09","물론이죠"),
  (1,"최규호","2023-05-15","응애..");

 INSERT INTO `record` (`uId`,
  `mId`,
  `mCom`,
  `exName`,
  `exScore`,
  `pof`,
  `respect`,
  `cVideo`,
  `uDate`,
  `cDate`)
VALUES
("yllh325","choon","슈웃","벤치프레스",95,1,999,"002.mp4","2023-11-03","2023-11-03"),
("asdasd","yeon","좋은데?","바벨컬",40,1,1475,"002.mp4","2023-11-03","2023-11-03"),
("qweqwe","choon","슈웃","플랭크",300,1,12,"002.mp4","2023-11-03","2023-11-03"),
("zxczxc","yeon","좋은데","스쿼트",135,1,1,"002.mp4","2023-11-03","2023-11-03"),
("dsf","yeon","좋은데","데드리프트",150,1,45,"002.mp4","2023-11-03","2023-11-03"),
("eeeeee","yeon","좋은데","3KM",400,1,76,"002.mp4","2023-11-03","2023-11-03"),
("yllh124","yeon","좋은데","오버헤드프레스",873,1,100,"002.mp4","2023-11-03","2023-11-03"),
("yllh325","choon","슈웃","10KM",1200,1,22,"002.mp4","2023-11-03","2023-11-03"),
("yllh325","choon","슈웃","레그프레스",340,1,99999,"002.mp4","2023-11-03","2023-11-03");

