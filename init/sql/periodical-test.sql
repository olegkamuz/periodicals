use mysql;
UPDATE user SET authentication_string=PASSWORD("ThePassword") WHERE user='root';
UPDATE user SET PASSWORD FOR root = 'ThePassword';
UPDATE user SET PASSWORD FOR 'root' = PASSWORD('ThePassword');