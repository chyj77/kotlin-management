INSERT INTO user(user_id, user_name,password)VALUES ('admin', 'admin','admin');
INSERT INTO user(user_id, user_name,password)VALUES ('sa', 'sa', 'sa');
INSERT INTO user(user_id, user_name,password)VALUES ('guest', 'guest', 'guest');
INSERT INTO role(role_id, role_name)VALUES (1, 'sa');
INSERT INTO role(role_id, role_name)VALUES (2, 'admin');
INSERT INTO role(role_id, role_name)VALUES (3, 'guest');
INSERT INTO user_role(role_id, user_id)VALUES (1, 'sa');
INSERT INTO user_role(role_id, user_id)VALUES (2, 'admin');
INSERT INTO user_role(role_id, user_id)VALUES (3, 'guest');