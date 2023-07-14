
CREATE OR REPLACE VIEW V_EMPLOYEES
AS
select 
    u."USR_ID" AS ID,
    u."USR_NAME",
    u."USR_LOGIN" AS LOGIN,
    u."USR_DELETED",
    u."USR_LOCKED",
    u."USR_LOCK_REASON",
    s."SBB_TB" AS TB,
    s."SBB_OSB" AS OSB,
    s."SBB_FILIAL" AS OFFICE,
-- поля T_USER_ROLE
r.role_id as "ROLE", 
r.role_name as T_USER_ROLE_role_name, 
r.role_descr as T_USER_ROLE_role_descr, 
r.role_not_editable as T_USER_ROLE_role_not_editable, 
r.role_deleted as T_USER_ROLE_role_deleted
from ESKADM1.T_USER u
left outer join ESKADM1.T_ESK_SBERBANK s on s.ESK_ID = u.SBB_ID
left outer join ESKADM1.T_USER_ROLE r on r.role_id = u.role_id

