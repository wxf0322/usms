INSERT INTO USMS_USERS
(id, login_name, password, salt, enabled, time_created)
VALUES
  (USMS_USERS_SEQ.nextval,
   'admin',
   'd3c59d25033dbf980d29554025c23a75',
   '8d78869f470951332959580424d4bf4f',
   1,
   sysdate);

INSERT INTO USMS_APPLICATIONS
(id, label, name, client_id, client_secret, enabled)
VALUES
  (USMS_APPLICATIONS_SEQ.nextval,
   '消防网格',
   'net.evecom.gsmp.fr',
   'c1ebe466-1cdc-4bd3-ab69-77c3561b9dee',
   'd8346ea2-6017-43ed-ad68-19c0f971738b',
   1);

INSERT INTO USMS_ROLES (id, name, label, enabled) VALUES (USMS_ROLES_SEQ.NEXTVAL, 'admin', 'admin', 1)

INSERT INTO USMS_PRIVILEGES (id, name, label, enabled) VALUES (USMS_PRIVILEGES_SEQ.NEXTVAL, 'pri', 'pri', 1);

INSERT INTO USMS_OPERATIONS (id, label, name, opt_type, enabled, application_id)
VALUES (USMS_OPERATIONS_SEQ.NEXTVAL, '查看xxx', 'net.evecom.gsmp.fr.xxx', 1, 1, USMS_APPLICATIONS_SEQ.currval);
INSERT INTO USMS_OPERATIONS (id, label, name, opt_type, enabled, application_id)
VALUES (USMS_OPERATIONS_SEQ.NEXTVAL, '编辑XXX', 'net.evecom.gsmp.fda.xxx', 1, 1, USMS_APPLICATIONS_SEQ.currval);

INSERT INTO USMS_USER_ROLE (user_id, role_id)
VALUES (USMS_USERS_SEQ.currval, USMS_ROLES_SEQ.currval);
INSERT INTO USMS_PRIVILEGE_ROLE (role_id, priv_id)
VALUES (USMS_ROLES_SEQ.currval, USMS_PRIVILEGES_SEQ.currval);

INSERT INTO USMS_PRIVILEGE_OPERATION (priv_id, oper_id)
VALUES (USMS_PRIVILEGES_SEQ.currval, USMS_OPERATIONS_SEQ.currval - 1);
INSERT INTO USMS_PRIVILEGE_OPERATION (priv_id, oper_id)
VALUES (USMS_PRIVILEGES_SEQ.currval, USMS_OPERATIONS_SEQ.currval);

INSERT INTO USMS_STAFFS (id, mobile, sex, offical_post) VALUES (2, '13806011111', 1, '网格员');

INSERT INTO usms_institutions (id, label, name, tree_level, parent_id, manual_sn)
VALUES (usms_institutions_seq.nextval, '指挥中心', 'zhzx', 1, 0, 1);
INSERT INTO usms_institutions (id, label, name, tree_level, parent_id, manual_sn)
VALUES (usms_institutions_seq.nextval, '调度中心', 'ddzx', 1, 0, 1);

