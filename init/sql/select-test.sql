CONNECT 'jdbc:derby://localhost:1527/st4db;user=test;password=test';

SELECT * FROM users;
SELECT * FROM orders;

SELECT o.id, u.first_name, u.last_name, o.bill, s.name
	FROM users u, orders o, statuses s
	WHERE o.user_id=u.id AND o.status_id=s.id;

DISCONNECT;

SELECT sum(price) AS total FROM magazine WHERE id IN ('5','6')



INSERT INTO `user` VALUES (DEFAULT, 'Oleg', '1', DEFAULT, 'Олег', 'Камуз', NULL, 1, 0)


