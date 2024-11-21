CREATE TABLE IF NOT EXISTS `consumer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(1000) NOT NULL,
   `lastName` VARCHAR(1000) NOT NULL,
   `email` VARCHAR(1000) NOT NULL,
   `password` VARCHAR(1000) NOT NULL,
   `salt` VARCHAR(1000) NOT NULL,
   `userType` VARCHAR(1000) NOT NULL,
   `phoneNumber`INT(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `service_provider` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(1000) NOT NULL,
   `lastName` VARCHAR(1000) NOT NULL,
   `email` VARCHAR(1000) NOT NULL,
   `password` VARCHAR(1000) NOT NULL,
   `salt` VARCHAR(1000) NOT NULL,
   `userType` VARCHAR(1000) NOT NULL,
   `phoneNumber`INT(11) NOT NULL,
   `serviceProviderType` VARCHAR(1000) NOT NULL,
   `isVerified` TINYINT(4) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `admin` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(1000) NOT NULL,
   `lastName` VARCHAR(1000) NOT NULL,
   `email` VARCHAR(1000) NOT NULL,
   `password` VARCHAR(1000) NOT NULL,
   `userType` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;