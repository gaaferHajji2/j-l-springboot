CREATE TABLE `users` (
	`id` BIGINT NOT NULL,
	`name` VARCHAR(255) NOT NULL DEFAULT '',
	`email` VARCHAR(255) NOT NULL DEFAULT '',
	`password` VARCHAR(255) NOT NULL DEFAULT '',
	PRIMARY KEY (`id`)
);

CREATE TABLE `addresses` (
	`id` BIGINT NOT NULL,
	`street` VARCHAR(255) NOT NULL DEFAULT '',
	`city` VARCHAR(255) NOT NULL DEFAULT '',
	`zip` VARCHAR(255) NOT NULL DEFAULT '',
	`user_id` BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`),
	constraint addresses_users_fk
	foreign key(user_id) references users (id)

)