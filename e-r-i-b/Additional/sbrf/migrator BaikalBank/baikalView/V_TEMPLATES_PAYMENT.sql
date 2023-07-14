
CREATE OR REPLACE VIEW V_TEMPLATES_PAYMENT
AS
select  t."CLN_ID" AS CLIENT_ID,
        t."DICTALIAS" AS TEMPLATE_NAME,
        t."NEWNUM",
        t."CORACC" AS RECEIVER_COR_ACC,
        t."FIRMNAME" AS RECEIVER_NAME,
        t."FIRMACC" AS RECEIVER_ACCOUNT,
        t."FIRMINN" AS RECEIVER_INN,
        t."UNIQ_NUM",
        t."PBOULFIO",
        t."TMPL_XML",
        XMLTYPE(nvl(t."TMPL_XML",'<a>n</a>')) AS XML_DATA,
        t."ACC_CUR",
        c.cln_lname AS PAYER_LNAME, 
        c.cln_fname AS PAYER_FNAME, 
        c.cln_mname AS PAYER_MNAME
from ESKADM1.T_CLIENT_DICT_OTHER t
join ESKADM1.T_CLIENT c on t.cln_id = c.cln_id

