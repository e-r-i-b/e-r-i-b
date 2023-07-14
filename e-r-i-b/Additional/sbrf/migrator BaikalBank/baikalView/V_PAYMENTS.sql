
CREATE OR REPLACE VIEW V_PAYMENTS
AS
select 
                                d."DOC_ID" AS ID,
                                d."DOC_TYPE" AS KIND,
                               d."DOC_DATE" AS DOCUMENT_DATE,
                                d."DOC_NUM" AS DOC_NUMBER,
                                d."SUMMA" AS AMOUNT,
                                d."DOC_TIME" AS CREATION_DATE,
                                XMLTYPE(d."DOC_XML") AS XML_DATA,
                                d."DOC_STATUS" AS STATE_CODE,
                                d."CLN_ID" AS CLIENT_ID,
                                d."DOC_VERSION",
                                d."DEBIT_ACC" AS PAYER_ACCOUNT,
                                d."DEBIT_CARD" AS PAYER_CARD,
                                d."OPERATION_VALUTE",
-- Статусы в T_DOC.DOC_STATUS:
-- OK - исполнен
-- RF - отказан
-- RC - отозван клиентом
-- остальные статусы - "В работе" или технические, эти документы мигрировать нельзя
-- поля таблицы T_DOC_Q - запросы к АБС
                                q.doc_time AS ADMISSION_DATE, 
                                XMLTYPE(q.doc_xml) AS XML_DATA_Q, 
-- поля таблицы T_DOC_A - ответы АБС
                                a.doc_time AS OPERATION_DATE, 
                                XMLTYPE(nvl(a.doc_xml,'<a>n</a>')) AS XML_DATA_A 

from ESKADM1.T_DOC d
join ESKADM1.T_DOC_Q q on q.doc_id = d.doc_id
left outer join ESKADM1.T_DOC_A a on a.q_id = q.q_id
where d.cln_id is not NULL

