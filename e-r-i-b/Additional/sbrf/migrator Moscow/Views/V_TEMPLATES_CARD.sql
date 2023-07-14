
CREATE OR REPLACE VIEW V_TEMPLATES_CARD
AS
select  t."CLC_ID" AS ID,
        t."CLC_CARD" AS TEMPLATE_NAME,
        t."CLN_ID" AS CLIENT_ID,
        t."CARD_CUR",
        c.cln_lname AS PAYER_LNAME, 
        c.cln_fname AS PAYER_FNAME, 
        c.cln_mname AS PAYER_MNAME,
        t."CLC_CARD" AS RECEIVER_ACCOUNT
from ESKADM1.T_CLIENT_CARD t
join ESKADM1.T_CLIENT c on t.cln_id = c.cln_id

