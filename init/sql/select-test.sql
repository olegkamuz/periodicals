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


SELECT COUNT(*) FROM `magazine`


SELECT c.id, c.name, c.start_date, c.end_date, c.description,  t.name theme_name,  t.id theme_id,  u.first_name,  u.last_name,  u.login,
       u.id user_id, (SELECT COUNT(*) FROM students_courses sc WHERE sc.course_id = c.id ) students_count
FROM
    courses c
    LEFT JOIN users u ON c.tutor_id = u.id
    INNER JOIN themes t ON c.theme_id = t.id
WHERE (c.theme_id = ? OR ? = 0)
  AND (c.tutor_id = ? OR ? = 0)
  AND (? NOT IN (SELECT stud_id FROM students_courses uc WHERE c.id = uc.course_id) OR ? = 0)
  AND DATEDIFF(CURDATE(), start_date) < 0    %s
LIMIT ?, ?;

смотреть на WHERE


SELECT * FROM `magazine` LIMIT 3 OFFSET 3

























