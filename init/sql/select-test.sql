CONNECT 'jdbc:derby://localhost:1527/st4db;user=test;password=test';

SELECT * FROM users;
SELECT * FROM orders;

SELECT o.id, u.first_name, u.last_name, o.bill, s.name
	FROM users u, orders o, statuses s
	WHERE o.user_id=u.id AND o.status_id=s.id;

DISCONNECT;

SELECT sum(price) AS total FROM magazine WHERE id IN ('5','6')



select m.name, m.price, m.image, t.name from subscription s join magazine m on m.id = s.magazine_id join theme t on t.id = m.theme_id where user_id = 3

UPDATE `magazine` SET name='Ideal Home', price=210.00, image='ideal-home.jpgFROMIDEA', theme_id=1 WHERE id=1;

INSERT INTO `magazine` VALUES(id='DEFAULT', name='Golf Monthly', price=250, image='golf-monthly.jpg', theme_id=2;

INSERT INTO `magazine` VALUES (DEFAULT, 'Golf Monthly', 250, 'golf-monthly.jpg', 2);

INSERT INTO `magazine` VALUES(DEFAULT, 'Golf Monthly', 250, 'golf-monthly.jpg', 2


























