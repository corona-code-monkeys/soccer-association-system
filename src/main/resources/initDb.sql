CREATE TABLE `sas`.`user` (
  `user_id` INT AUTO_INCREMENT,
  `user_name` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `full_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE);
  
CREATE TABLE `sas`.`user_role` (
    `user_id` INT NOT NULL,
    `user_name` VARCHAR(45) NOT NULL,
    `user_role` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`user_id` , `user_role`),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);

CREATE TABLE `sas`.`referee` (
    `user_id` INT NOT NULL,
    `level` INT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);

CREATE TABLE `sas`.`team_owner` (
    `user_id` INT NOT NULL,
    `team_name` VARCHAR(45) NOT NULL,
    `nominated_by` INT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id),
        FOREIGN KEY (nominated_by)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);
  
CREATE TABLE `sas`.`team_manager` (
    `user_id` INT NOT NULL,
    `team_name` VARCHAR(45) NULL,
    `nominated_by` INT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id), 
        FOREIGN KEY (nominated_by)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);

CREATE TABLE `sas`.`player` (
    `user_id` INT NOT NULL,
    `team_name` VARCHAR(45) NULL,
    `date_of_birth` DATE NULL,
    `field_role` VARCHAR(45) NULL,
    `personal_page_id` INT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);

CREATE TABLE `sas`.`coach` (
    `user_id` INT NOT NULL,
    `level` INT NULL,
    `field_role` VARCHAR(45) NULL,
    `team_name` VARCHAR(45) NOT NULL,
    `personal_page_id` INT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);

CREATE TABLE `sas`.`fan` (
    `user_id` INT NOT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);
  
CREATE TABLE `sas`.`association_representative` (
    `user_id` INT NOT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);

CREATE TABLE `sas`.`system_admin` (
    `user_id` INT NOT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);

CREATE TABLE `sas`.`goal` (
    `game_id` INT NOT NULL,
    `game_date` DATE NOT NULL,
    `game_minute` INT NOT NULL,
    `game_event_id` INT NOT NULL,
    `player_user_id` INT NOT NULL,
    `team_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`game_id` , `game_event_id`)
);

CREATE TABLE `sas`.`injury` (
    `game_id` INT NOT NULL,
    `game_date` DATE NOT NULL,
    `game_minute` INT NOT NULL,
    `game_event_id` INT NOT NULL,
    `player_user_id` INT NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`game_id` , `game_event_id`)
);

CREATE TABLE `sas`.`player_substitution` (
    `game_id` INT NOT NULL,
    `game_event_id` INT NOT NULL,
    `game_date` DATE NOT NULL,
    `game_minute` INT NOT NULL,
    `player_in_user_id` INT NOT NULL,
    `player_out_user_id` INT NOT NULL,
    PRIMARY KEY (`game_id` , `game_event_id`)
);

CREATE TABLE `sas`.`offence` (
    `game_id` INT NOT NULL,
    `game_event_id` INT NOT NULL,
    `game_date` DATE NOT NULL,
    `game_minute` INT NOT NULL,
    `player_committed_user_id` INT NOT NULL,
    `player_againt_user_id` INT NULL,
    `description` VARCHAR(255) NULL,
    PRIMARY KEY (`game_id` , `game_event_id`)
);

CREATE TABLE `sas`.`offside` (
    `game_id` INT NOT NULL,
    `game_event_id` INT NOT NULL,
    `game_date` DATE NOT NULL,
    `game_minute` INT NOT NULL,
    `player_in_offside_user_id` INT NOT NULL,
    `team_in_favor_id` INT NULL,
    PRIMARY KEY (`game_id` , `game_event_id`)
);

CREATE TABLE `sas`.`ticket` (
    `game_id` INT NOT NULL,
    `game_event_id` INT NOT NULL,
    `game_date` DATE NOT NULL,
    `game_minute` INT NOT NULL,
    `player_against_user_id` INT NOT NULL,
    `referee_pulled_user_id` INT NULL,
    `type` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`game_id` , `game_event_id`)
);

-- League

CREATE TABLE `sas`.`league` (
    `league_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`league_name`)
);

CREATE TABLE `sas`.`season` (
    `season_year` INT NOT NULL,
    PRIMARY KEY (`season_year`)
);

CREATE TABLE `sas`.`policies` (
    `season_year` INT NOT NULL,
    `league_name` VARCHAR(45) NOT NULL,
    `league_rank_policy` VARCHAR(45) NOT NULL,
    `points_policy` VARCHAR(45) NOT NULL,
    `games_policy` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`season_year` , `league_name`)
);

CREATE TABLE `sas`.`league_score` (
  `season_year` INT NOT NULL,
  `league_name` VARCHAR(45) NOT NULL,
  `team_name` VARCHAR(45) NOT NULL,
  `goal_scored` INT NOT NULL,
  `goal_conceed` INT NOT NULL,
  `goal_diff` INT GENERATED ALWAYS AS (goal_scored-goal_conceed) VIRTUAL,
  `num_of_games` INT NOT NULL,
  `num_of_wins` INT NOT NULL,
  `num_of_draws` INT NOT NULL,
  `num_of_loses` INT GENERATED ALWAYS AS (num_of_games-num_of_wins-num_of_draws) VIRTUAL,
  PRIMARY KEY (`season_year`, `league_name`, `team_name`));

-- Game

CREATE TABLE `sas`.`game` (
    `game_id` INT AUTO_INCREMENT,
    `date` DATE NOT NULL,
    `season_year` INT NOT NULL,
    `league_name` VARCHAR(45) NULL,
    `host_team_name` VARCHAR(45) NOT NULL,
    `guest_team_name` VARCHAR(45) NOT NULL,
    `host_score` INT NOT NULL,
    `guest_score` INT NOT NULL,
    `stadium_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`game_id`)
);

-- Team

CREATE TABLE `sas`.`team` (
    `team_name` VARCHAR(45) NOT NULL,
    `manager_user_id` INT NULL,
    `coach_user_id` INT,
    `isActive` BOOLEAN NOT NULL,
    `isRegistered` BOOLEAN NOT NULL,
    `personal_page_id` INT NULL,
    PRIMARY KEY (`team_name`)
);
 
CREATE TABLE `sas`.`team_budget` (
    `team_name` VARCHAR(45) NOT NULL,
    `season_year` INT NOT NULL,
    `budget` DOUBLE NULL,
    PRIMARY KEY (`team_name` , `season_year`)
);
  

CREATE TABLE `sas`.`team_owners` (
    `team_name` VARCHAR(45) NOT NULL,
    `owner_user_id` INT NOT NULL,
    PRIMARY KEY (`team_name` , `owner_user_id`)
);
  
CREATE TABLE `sas`.`game_referees` (
    `game_id` INT NOT NULL,
    `referee_user_id` INT NOT NULL,
    PRIMARY KEY (`game_id` , `referee_user_id`)
);
  

CREATE TABLE `sas`.`team_players` (
    `team_name` VARCHAR(45) NOT NULL,
    `player_user_id` INT NOT NULL,
    PRIMARY KEY (`team_name` , `player_user_id`)
);

CREATE TABLE `sas`.`team_games` (
    `team_name` VARCHAR(45) NOT NULL,
    `season_year` INT NOT NULL,
    `league_name` VARCHAR(45) NOT NULL,
    `game_id` INT NOT NULL,
    PRIMARY KEY (`team_name` , `game_id`)
);

CREATE TABLE `sas`.`facilities` (
    `home_team_name` VARCHAR(45) NOT NULL,
    `facilitey_name` VARCHAR(45) NOT NULL,
    `type` VARCHAR(45) NOT NULL,
    `location` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`facilitey_name`)
);

CREATE TABLE `sas`.`transactions` (
    `transaction_id` INT AUTO_INCREMENT,
    `team_name` VARCHAR(200) NOT NULL,
    `amount` DOUBLE NOT NULL,
    `type` VARCHAR(45) NOT NULL,
    `team_owner_reported_id` INT NOT NULL,
    `date` DATE NOT NULL,
    `description` VARCHAR(45) NULL,
    PRIMARY KEY (`transaction_id`)
);
  
CREATE TABLE `sas`.`personal_page` (
    `personal_page_id` INT AUTO_INCREMENT,
    `description` VARCHAR(200) NULL,
    PRIMARY KEY (`personal_page_id`)
);
  
CREATE TABLE `sas`.`followers` (
    `personal_page_id` INT NOT NULL,
    `follower_user_id` INT NOT NULL,
    PRIMARY KEY (`personal_page_id` , `follower_user_id`)
);
  
CREATE TABLE `sas`.`privileges` (
    `role` VARCHAR(45) NOT NULL,
    `privilege` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`role` , `privilege`)
);

SET @@global.time_zone = '+03:00';
SET @@session.time_zone = '+03:00';
  
insert into privileges (role, privilege) values ('Guest', 'viewPages+LS');
insert into privileges (role, privilege) values ('Guest', 'searchByNCK');
insert into privileges (role, privilege) values ('Fan', 'followPage');
insert into privileges (role, privilege) values ('Fan', 'getNoti');
insert into privileges (role, privilege) values ('Fan', 'viewSHistory');
insert into privileges (role, privilege) values ('Fan', 'editPDetails');
insert into privileges (role, privilege) values ('Player', 'editPDetails');
insert into privileges (role, privilege) values ('Player', 'uploadPContent');
insert into privileges (role, privilege) values ('Coach', 'editPDetails');
insert into privileges (role, privilege) values ('Coach', 'uploadPContent');
insert into privileges (role, privilege) values ('TeamOwner', 'add/removeA');
insert into privileges (role, privilege) values ('TeamOwner', 'add/removeTO');
insert into privileges (role, privilege) values ('TeamOwner', 'add/removeTM');
insert into privileges (role, privilege) values ('TeamOwner', 'editTMPriv');
insert into privileges (role, privilege) values ('TeamOwner', 'closeTNP');
insert into privileges (role, privilege) values ('TeamOwner', 'addTrans');
insert into privileges (role, privilege) values ('TeamOwner', 'editPDetails');
insert into privileges (role, privilege) values ('TeamManager', 'editPDetails');
insert into privileges (role, privilege) values ('TeamManagerApproved', 'add/removeA');
insert into privileges (role, privilege) values ('TeamManagerApproved', 'editPDetails');
insert into privileges (role, privilege) values ('SystemAdmin', 'editPDetails');
insert into privileges (role, privilege) values ('SystemAdmin', 'closeTP');
insert into privileges (role, privilege) values ('SystemAdmin', 'removeRegistered');
insert into privileges (role, privilege) values ('SystemAdmin', 'view/replyReport');
insert into privileges (role, privilege) values ('SystemAdmin', 'viewSysSettings');
insert into privileges (role, privilege) values ('SystemAdmin', 'enableRS');
insert into privileges (role, privilege) values ('AssociationRepresentative', 'editPDetails');
insert into privileges (role, privilege) values ('AssociationRepresentative', 'defineL');
insert into privileges (role, privilege) values ('AssociationRepresentative', 'defineSL');
insert into privileges (role, privilege) values ('AssociationRepresentative', 'add/removeR');
insert into privileges (role, privilege) values ('AssociationRepresentative', 'setRinL');
insert into privileges (role, privilege) values ('AssociationRepresentative', 'define/changePolicy');
insert into privileges (role, privilege) values ('AssociationRepresentative', 'enableGScheduling');
insert into privileges (role, privilege) values ('Referee', 'editPDetails');
insert into privileges (role, privilege) values ('Referee', 'viewMyGames');
insert into privileges (role, privilege) values ('Referee', 'editGameEvent');
  