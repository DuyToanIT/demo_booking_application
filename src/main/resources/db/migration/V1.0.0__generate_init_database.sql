-- Create table user
CREATE TABLE IF NOT EXISTS user(
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(30) NOT NULL,
    `email` VARCHAR(30) NOT NULL,
    `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_date` DATETIME DEFAULT NULL,
    PRIMARY KEY(`id`),
    INDEX `rool` USING BTREE(`email`)
    );

-- Create table address
CREATE TABLE IF NOT EXISTS address(
     `id` INT NOT NULL AUTO_INCREMENT,
     `street` VARCHAR(100) NOT NULL,
     `city` VARCHAR(100) NOT NULL,
     `state` VARCHAR(100) NOT NULL,
     `postal_code` VARCHAR(100) NOT NULL,
     `country` VARCHAR(100) NOT NULL,
     `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `updated_date` DATETIME DEFAULT NULL,
     PRIMARY KEY(`id`)
);

-- Create table hotel
CREATE TABLE IF NOT EXISTS hotel(
     `id` INT NOT NULL AUTO_INCREMENT,
     `name` VARCHAR(100) NOT NULL,
     `phone` VARCHAR(100) NOT NULL,
     `status` VARCHAR(50) NOT NULL,
     `location` TEXT NULL,
     `address_id` INT NOT NULL,
     `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `updated_date` DATETIME DEFAULT NULL,
     PRIMARY KEY(`id`),
    CONSTRAINT `fk_hotel_address` FOREIGN KEY (`address_id`) REFERENCES `address`(`id`),
    INDEX `hotel` USING BTREE(`name`, `status`)
    );

-- Create table room
CREATE TABLE IF NOT EXISTS room(
      `id` INT NOT NULL AUTO_INCREMENT,
      `hotel_id` INT NOT NULL,
      `price` decimal(12) NOT NULL,
      `status` VARCHAR(50) NOT NULL,
      `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `updated_date` DATETIME DEFAULT NULL,
      PRIMARY KEY(`id`),
      CONSTRAINT `fk_room_hotel` FOREIGN KEY (`hotel_id`) REFERENCES `hotel`(`id`),
      INDEX `room` USING BTREE(`hotel_id`, `status`)
    );

-- Create table booking
CREATE TABLE IF NOT EXISTS booking(
     `id` INT NOT NULL AUTO_INCREMENT,
     `user_id` INT NOT NULL,
     `status` VARCHAR(50) NOT NULL,
     `total_amount` decimal(12) NOT NULL,
     `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `updated_date` DATETIME DEFAULT NULL,
     PRIMARY KEY(`id`),
    CONSTRAINT `fk_booking_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

-- Create table booking
CREATE TABLE IF NOT EXISTS booking_room(
    `id` INT NOT NULL AUTO_INCREMENT,
    `booking_id` INT NOT NULL,
    `room_id` INT NOT NULL,
    `price` decimal(12) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `check_in_date` DATE NOT NULL,
    `check_out_date` DATE DEFAULT NULL,
    `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_date` DATETIME DEFAULT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT `fk_booking_room_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking`(`id`),
    CONSTRAINT `fk_booking_room_room` FOREIGN KEY (`room_id`) REFERENCES `room`(`id`),
    INDEX `booking_room` USING BTREE(`booking_id`, `room_id`)
);
