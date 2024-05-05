START TRANSACTION;
-- Insert data table user
INSERT INTO `user` (`id`,`user_name`,`email`,`created_date`,`updated_date`) VALUES (1,'user1','user1@gmail.com',CURRENT_TIMESTAMP,NULL);
INSERT INTO `user` (`id`,`user_name`,`email`,`created_date`,`updated_date`) VALUES (2,'user2','user2@gmail.com',CURRENT_TIMESTAMP,NULL);
INSERT INTO `user` (`id`,`user_name`,`email`,`created_date`,`updated_date`) VALUES (3,'user3','user3@gmail.com',CURRENT_TIMESTAMP,NULL);
INSERT INTO `user` (`id`,`user_name`,`email`,`created_date`,`updated_date`) VALUES (4,'user4','user4@gmail.com',CURRENT_TIMESTAMP,NULL);

-- Insert data table addressroom
INSERT INTO `address` (`id`,`street`,`city`,`state`,`postal_code`,`country`,`created_date`,`updated_date`) VALUES (1,'18 Nguyễn Văn Trỗi, Phường 7','Hồ Chí Minh','HCM','70000','VietNam',CURRENT_TIMESTAMP,NULL);
INSERT INTO `address` (`id`,`street`,`city`,`state`,`postal_code`,`country`,`created_date`,`updated_date`) VALUES (2,'29 Hồ Văn Huê, Phú Nhuận','Hồ Chí Minh','HCM','70000','VietNam',CURRENT_TIMESTAMP,NULL);

-- Insert data table hotel
INSERT INTO `hotel` (`id`,`name`,`phone`,`status`,`address_id`,`created_date`,`updated_date`,`location`) VALUES (1,'DT','840292930','ACTIVE',1,CURRENT_TIMESTAMP,NULL,'18 Nguyễn Văn Trỗi, Hồ Chí Minh');
INSERT INTO `hotel` (`id`,`name`,`phone`,`status`,`address_id`,`created_date`,`updated_date`,`location`) VALUES (2,'ABC','84292930','ACTIVE',2,CURRENT_TIMESTAMP,NULL,'29 Hồ Văn Huê, Hồ Chí Minh');

-- Insert data table room
INSERT INTO `room` (`id`,`hotel_id`,`price`,`status`,`created_date`,`updated_date`) VALUES (1,1,100000,'AVAILABLE',CURRENT_TIMESTAMP,NULL);
INSERT INTO `room` (`id`,`hotel_id`,`price`,`status`,`created_date`,`updated_date`) VALUES (2,1,200000,'AVAILABLE',CURRENT_TIMESTAMP,NULL);
INSERT INTO `room` (`id`,`hotel_id`,`price`,`status`,`created_date`,`updated_date`) VALUES (3,1,189900,'AVAILABLE',CURRENT_TIMESTAMP,NULL);
INSERT INTO `room` (`id`,`hotel_id`,`price`,`status`,`created_date`,`updated_date`) VALUES (4,1,300000,'AVAILABLE',CURRENT_TIMESTAMP,NULL);
INSERT INTO `room` (`id`,`hotel_id`,`price`,`status`,`created_date`,`updated_date`) VALUES (5,1,677000,'AVAILABLE',CURRENT_TIMESTAMP,NULL);
INSERT INTO `room` (`id`,`hotel_id`,`price`,`status`,`created_date`,`updated_date`) VALUES (6,1,800000,'AVAILABLE',CURRENT_TIMESTAMP,NULL);
INSERT INTO `room` (`id`,`hotel_id`,`price`,`status`,`created_date`,`updated_date`) VALUES (7,1,1000000,'UNDER_MAINTENANCE',CURRENT_TIMESTAMP,NULL);
INSERT INTO `room` (`id`,`hotel_id`,`price`,`status`,`created_date`,`updated_date`) VALUES (8,2,500000,'AVAILABLE',CURRENT_TIMESTAMP,NULL);
INSERT INTO `room` (`id`,`hotel_id`,`price`,`status`,`created_date`,`updated_date`) VALUES (9,2,300000,'AVAILABLE',CURRENT_TIMESTAMP,NULL);
COMMIT;