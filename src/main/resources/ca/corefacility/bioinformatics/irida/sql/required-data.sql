INSERT INTO system_role (`name`,`description`) VALUES ('ROLE_USER','A basic user in the system.');
INSERT INTO system_role (`name`,`description`) VALUES ('ROLE_ADMIN','An administrative user in the system.');
INSERT INTO system_role (`name`,`description`) VALUES ('ROLE_CLIENT','A client tool in the system.');
INSERT INTO system_role (`name`,`description`) VALUES ('ROLE_MANAGER','A manager role in the system.');
INSERT INTO system_role (`name`,`description`) VALUES ('ROLE_SEQUENCER','A sequencer role in the system.');

-- user account required for integration tests
INSERT INTO user (`createdDate`, `modifiedDate`, `email`, `firstName`, `lastName`, `locale`, `password`, `phoneNumber`, `username`, `enabled`, `system_role`, `credentialsNonExpired`) VALUES (now(), now() , 'franklin.bristow@phac-aspc.gc.ca', 'Franklin', 'Bristow', 'en', '$2a$10$yvzFLxWA9m2wNQmHpJtWT.MRZv8qV8Mo3EMB6HTkDnUbi9aBrbWWW', '7029', 'fbristow', 1, 'ROLE_USER', 1);
INSERT INTO user (`createdDate`, `modifiedDate`, `email`, `firstName`, `lastName`, `locale`, `password`, `phoneNumber`, `username`, `enabled`, `system_role`, `credentialsNonExpired`) VALUES (now(), now() , 'josh.adam@phac-aspc.gc.ca', 'Josh', 'Adam', 'en', '$2a$10$yvzFLxWA9m2wNQmHpJtWT.MRZv8qV8Mo3EMB6HTkDnUbi9aBrbWWW', '7418', 'josh', 1, 'ROLE_USER', 1);
INSERT INTO user (`createdDate`, `modifiedDate`, `email`, `firstName`, `lastName`, `locale`, `password`, `phoneNumber`, `username`, `enabled`, `system_role`, `credentialsNonExpired`) VALUES (now(), now() , 'thomas.matthews@phac-aspc.gc.ca', 'Tom', 'Matthews', 'en', '$2a$10$yvzFLxWA9m2wNQmHpJtWT.MRZv8qV8Mo3EMB6HTkDnUbi9aBrbWWW', '7418', 'tom', 1, 'ROLE_USER', 1);
INSERT INTO user (`createdDate`, `modifiedDate`, `email`, `firstName`, `lastName`, `locale`, `password`, `phoneNumber`, `username`, `enabled`, `system_role`, `credentialsNonExpired`) VALUES (now(), now() , 'admin@nowhere.ca', 'Admin', 'Admin', 'en', '$2a$10$yvzFLxWA9m2wNQmHpJtWT.MRZv8qV8Mo3EMB6HTkDnUbi9aBrbWWW', '0000', 'admin', 1, 'ROLE_ADMIN', 1);
INSERT INTO user (`createdDate`, `modifiedDate`, `email`, `firstName`, `lastName`, `locale`, `password`, `phoneNumber`, `username`, `enabled`, `system_role`, `credentialsNonExpired`) VALUES (now(), now() , 'test@nowhere.ca', 'Test', 'User', 'en', '$2a$10$yvzFLxWA9m2wNQmHpJtWT.MRZv8qV8Mo3EMB6HTkDnUbi9aBrbWWW', '1234', 'test', 1, 'ROLE_USER', 1);
INSERT INTO user (`createdDate`, `modifiedDate`, `email`, `firstName`, `lastName`, `locale`, `password`, `phoneNumber`, `username`, `enabled`, `system_role`, `credentialsNonExpired`) VALUES (now(), now() , 'disabled-guy@nowhere.ca', 'Disabled', 'Guy', 'en', '$2a$10$yvzFLxWA9m2wNQmHpJtWT.MRZv8qV8Mo3EMB6HTkDnUbi9aBrbWWW', '0000', 'disabledguy', 0, 'ROLE_USER', 1);
INSERT INTO user (`createdDate`, `modifiedDate`, `email`, `firstName`, `lastName`, `locale`, `password`, `phoneNumber`, `username`, `enabled`, `system_role`, `credentialsNonExpired`) VALUES (now(), now() , 'manager@nowhere.ca', 'Mr.', 'Manager', 'en', '$2a$10$yvzFLxWA9m2wNQmHpJtWT.MRZv8qV8Mo3EMB6HTkDnUbi9aBrbWWW', '1234', 'manager', 1, 'ROLE_MANAGER', 1);

-- projects required for integration tests
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 1');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 2');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 3');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 4');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 5');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 6');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 7');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 8');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 9');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 10');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 11');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 12');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 13');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 14');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 15');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 16');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 17');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 18');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 19');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 20');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 21');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 22');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 23');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 24');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 25');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 26');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 27');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 28');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 29');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 30');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 31');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 32');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 33');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 34');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 35');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 36');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 37');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 38');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 39');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 40');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 41');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 42');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 43');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 44');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 45');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 46');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 47');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 48');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 49');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 50');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 51');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 52');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 53');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 54');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 55');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 56');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 57');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 58');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 59');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 60');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 61');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 62');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 63');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 64');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 65');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 66');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 67');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 68');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 69');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 70');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 71');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 72');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 73');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 74');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 75');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 76');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 77');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 78');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 79');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 80');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 81');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 82');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 83');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 84');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 85');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 86');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 87');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 88');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 89');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 90');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 91');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 92');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 93');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 94');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 95');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 96');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 97');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 98');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 99');
INSERT INTO project (`createdDate`, `modifiedDate`, `name`) VALUES (now(), now() , 'Project 100');

-- relationship between projects and users
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 1, 2, 'PROJECT_OWNER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 2, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 3, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 4, 2, 'PROJECT_OWNER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 5, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 6, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 7, 2, 'PROJECT_OWNER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 8, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 9, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 10, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 11, 2, 'PROJECT_OWNER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 12, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 13, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 14, 2, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 1, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 2, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 3, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 4, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 5, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 6, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 7, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 8, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 9, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 10, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 11, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 12, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 13, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 14, 5, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 1, 1, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 2, 1, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 3, 1, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 4, 1, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 5, 1, 'PROJECT_USER');
INSERT INTO project_user (`createdDate`, `project_id`, `user_id`, `projectRole`) VALUES (now(), 100, 1, 'PROJECT_USER');

-- samples
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 1', 'sample1', 'The first sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 2', 'sample2', 'The second sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 3', 'sample3', 'The third sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 4', 'sample4', 'The fourth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 5', 'sample5', 'The fifth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 6', 'sample6', 'The sixth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 7', 'sample7', 'The seventh sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 8', 'sample8', 'The eigth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 9', 'sample9', 'The nineth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 10', 'sample10', 'The tenth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 11', 'sample11', 'The eleventh sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 12', 'sample12', 'The twelveth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 13', 'sample13', 'The thirteenth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 14', 'sample14', 'The fourteenth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 15', 'sample15', 'The fifteenth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 16', 'sample16', 'The sixteenth sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 17', 'sample17', 'The 17th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 18', 'sample18', 'The 18th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 19', 'sample19', 'The 19th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 20', 'sample20', 'The 20th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 21', 'sample21', 'The 21st sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 22', 'sample22', 'The 22nd sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 23', 'sample23', 'The 23rd sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 24', 'sample24', 'The 24th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 25', 'sample25', 'The 25th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 26', 'sample26', 'The 26th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 27', 'sample27', 'The 27th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 28', 'sample28', 'The 28th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 29', 'sample29', 'The 29th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 30', 'sample30', 'The 30th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 31', 'sample31', 'The 31st sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 32', 'sample32', 'The 32nd sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 33', 'sample33', 'The 33rd sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 34', 'sample34', 'The 34th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 35', 'sample35', 'The 35th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 36', 'sample36', 'The 36th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 37', 'sample37', 'The 37th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 38', 'sample38', 'The 38th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 39', 'sample39', 'The 39th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 40', 'sample40', 'The 40th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 41', 'sample41', 'The 41st sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 42', 'sample42', 'The 42nd sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 43', 'sample43', 'The 43rd sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 44', 'sample44', 'The 44th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 45', 'sample45', 'The 45th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 46', 'sample46', 'The 46th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 47', 'sample47', 'The 47th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 48', 'sample48', 'The 48th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 49', 'sample49', 'The 49th sample');
INSERT INTO sample (`createdDate`, `modifiedDate`, `sampleName`, `externalSampleId`, `description`) VALUES (now(), now() , 'Sample 50', 'sample50', 'The 50th sample');
-- sample relationship
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 5, 1);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 2);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 3);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 4);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 5, 5);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 6);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 7);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 8);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 9);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 10);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 11);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 12);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 13);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 14);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 15);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 16);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 17);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 18);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 19);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 20);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 21);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 22);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 23);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 24);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 25);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 26);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 27);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 28);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 29);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 30);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 31);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 32);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 33);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 34);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 35);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 36);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 37);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 38);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 39);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 40);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 41);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 42);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 43);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 44);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 45);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 46);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 47);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 48);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 49);
INSERT INTO project_sample (`createdDate`, `project_id`, `sample_id`) VALUES (now(), 4, 50);
