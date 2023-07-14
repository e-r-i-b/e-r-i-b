
CREATE OR REPLACE VIEW V_TEMPLATES_ACCOUNT
AS
select  t."CLN_ID" AS CLIENT_ID,
        t."DICTALIAS" AS TEMPLATE_NAME,
        t."FIO" AS RECEIVER_NAME,
        t."SB_ACC" AS RECEIVER_ACCOUNT,
        t."INN",
        t."SUMMA" AS AMOUNT,
        t."NONRESCODE",
        t."PURPOSE" AS GROUND,
        t."IS_NOT_SBER",
        t."BIK",
        t."CORACC",
        t."ACC_NAME",
        t."ACC_CUR",
        t."SB_ACCOLD",
        c.cln_lname AS PAYER_LNAME, 
        c.cln_fname AS PAYER_FNAME, 
        c.cln_mname AS PAYER_MNAME
from ESKADM1.T_CLIENT_DICT_SBER t
join ESKADM1.T_CLIENT c on t.cln_id = c.cln_id

