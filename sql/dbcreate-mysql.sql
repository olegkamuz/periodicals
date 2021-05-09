DROP DATABASE st4db;
CREATE DATABASE st4db;

USE st4db;

DROP TABLE orders_menu;
DROP TABLE menu;
DROP TABLE orders;
DROP TABLE categories;
DROP TABLE statuses;
DROP TABLE users;
DROP TABLE roles;
CREATE TABLE roles (
  id int NOT NULL,
  name varchar(10) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (name)
);
INSERT INTO roles values ( 0, 'admin' );
INSERT INTO roles values ( 1, 'client' );
CREATE TABLE users (
  id int noT NULL AUTO_INCREMENT,
  login varchar(10) NOT NULL,
  password varchar(10) NOT NULL,
  first_name varchar(20) NOT NULL,
  last_name varchar(20) NOT NULL,
  locale_name varchar(20),
  role_id int NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (login),
  FOREIGN KEY (role_id)
  REFERENCES roles (id) ON DELETE CASCADE ON UPDATE RESTRICT
);
INSERT INTO users VALUES ( DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov', NULL, 0 );
INSERT INTO users VALUES ( DEFAULT, 'client', 'client', 'Petr', 'Petrov', NULL, 1 );
INSERT INTO users VALUES ( DEFAULT, 'петров', 'петров', 'Иван', 'Петров', NULL, 1 );
CREATE TABLE statuses (
  id int NOT NULL,
  name varchar(10) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (name)
);
INSERT INTO statuses VALUES ( 0, 'opened' );
INSERT INTO statuses VALUES ( 1, 'confirmed' );
INSERT INTO statuses VALUES ( 2, 'paid' );
INSERT INTO statuses VALUES ( 3, 'closed' );
CREATE TABLE categories (
  id int NOT NULL,
  name varchar(10) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (name)
);
INSERT INTO categories VALUES ( 1, 'Hot dishes' );
INSERT INTO categories VALUES ( 2, 'Starters' );
INSERT INTO categories VALUES ( 3, 'Desserts' );
INSERT INTO categories VALUES ( 4, 'Beverages' );
CREATE TABLE orders (
  id int not NULL AUTO_INCREMENT,
  bill int nOT NULL DEFAULT 0,
  user_id int NOT NULL,
  status_id int NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (status_id)
  REFERENCES statuses (id)
);
INSERT INTO orders VALUES ( DEFAULT, 0, 2, 0 );
INSERT INTO orders VALUES ( DEFAULT, 0, 2, 3 );
CREATE TABLE menu (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  price int NOT NULL,
  category_id int NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (category_id)
  REFERENCES categories (id)
);
INSERT INTO menu VALUES ( DEFAULT, 'Borsch', 210, 1 );
INSERT INTO menu VALUES ( DEFAULT, 'Kharcho', 210, 1 );
INSERT INTO menu VALUES ( DEFAULT, 'Solyanka', 250, 1 );
INSERT INTO menu VALUES ( DEFAULT, 'Juice', 70, 4 );
INSERT INTO menu VALUES ( DEFAULT, 'Tea', 50, 4 );
INSERT INTO menu VALUES ( DEFAULT, 'Coffee', 100, 4 );
INSERT INTO menu VALUES ( DEFAULT, 'Salmon salad', 250, 2 );
INSERT INTO menu VALUES ( DEFAULT, 'Fish plate', 200, 2 );
INSERT INTO menu VALUES ( DEFAULT, 'Fruit plate', 160, 3 );
INSERT INTO menu VALUES ( DEFAULT, 'Strawberries and cream', 260, 3 );
CREATE TABLE orders_menu (
  order_id int NOT NULL,
  menu_id int NOT NULL,
  FOREIGN KEY (order_id)
  REFERENCES orders (id),
  FOREIGN KEY (menu_id)
  REFERENCES menu (id)
);
INSERT INTO orders_menu VALUES ( 1, 1 );
INSERT INTO orders_menu VALUES ( 1, 7 );
INSERT INTO orders_menu VALUES ( 1, 5 );
INSERT INTO orders_menu VALUES ( 2, 1 );
INSERT INTO orders_menu VALUES ( 2, 7 );