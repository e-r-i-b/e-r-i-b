/*==============================================================*/
/* View: ACTIVELOGINS                                           */
/*==============================================================*/
create or replace view ACTIVELOGINS as
SELECT  LOGINS.USER_ID AS USER_ID, USERS.*
FROM    USERS INNER JOIN
        LOGINS ON USERS.LOGIN_ID = LOGINS.ID
WHERE   (USERS.STATUS = 'A' )
go

/*==============================================================*/
/* View: DELAYED_DOCUMENT_COUNT                                 */
/*==============================================================*/
create or replace view DELAYED_DOCUMENT_COUNT as
SELECT count(document.id) as DELAYED_DOCUMENT_COUNT, department.tb as TB 
FROM BUSINESS_DOCUMENTS document 
LEFT JOIN DEPARTMENTS department on document.department_id = department.id
WHERE STATE_CODE in ('SENDED','DISPATCH','DISPATCHED','PARTLY_EXECUTED')
AND OPERATION_DATE < sysdate - INTERVAL '30' MINUTE
GROUP BY department.tb
go

/*==============================================================*/
/* View: DISPATCH_DOCUMENT_COUNT                                */
/*==============================================================*/
create or replace view DISPATCH_DOCUMENT_COUNT as
SELECT count(document.id) as DISPATCH_DOCUMENT_COUNT, department.tb as TB 
FROM BUSINESS_DOCUMENTS document 
LEFT JOIN DEPARTMENTS department on document.department_id = department.id
WHERE STATE_CODE in ('SENDED','DISPATCH','DISPATCHED','PARTLY_EXECUTED')
GROUP BY department.tb
go

/*==============================================================*/
/* View: EMPLOYEE_COUNT                                         */
/*==============================================================*/
create or replace view EMPLOYEE_COUNT as
SELECT sum(count) as EMPLOYEE_COUNT, TB
FROM USER_COUNTER countr
WHERE MODULE = 'PhizIA'
AND UPDATE_TIME > sysdate - INTERVAL '15' MINUTE
GROUP BY TB
go

/*==============================================================*/
/* View: ERROR_DOCUMENT_COUNT_TODAY                             */
/*==============================================================*/
create or replace view ERROR_DOCUMENT_COUNT_TODAY as
SELECT count(document.id) as ERROR_DOCUMENT_COUNT_TODAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE document.CREATION_DATE >= trunc(sysdate)
and STATE_CODE = 'ERROR'
GROUP BY department.tb
go

/*==============================================================*/
/* View: ERROR_DOCUMENT_COUNT_YESTERDAY                         */
/*==============================================================*/
create or replace view ERROR_DOCUMENT_COUNT_YESTERDAY as
SELECT count(document.id) as ERROR_DOCUMENT_COUNT_YESTERDAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE CREATION_DATE between trunc(sysdate-1) and trunc(sysdate)
and STATE_CODE = 'ERROR'
GROUP BY department.tb
go

/*==============================================================*/
/* View: LOAN_PAYMENT_COUNT_TODAY                               */
/*==============================================================*/
create or replace view LOAN_PAYMENT_COUNT_TODAY as
SELECT count(document.id) as LOAN_PAYMENT_COUNT_TODAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE document.CREATION_DATE >= trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and document.KIND = 'T'
GROUP BY department.tb
go

/*==============================================================*/
/* View: LOAN_PAYMENT_COUNT_YESTERDAY                           */
/*==============================================================*/
create or replace view LOAN_PAYMENT_COUNT_YESTERDAY as
SELECT count(document.id) as LOAN_PAYMENT_COUNT_YESTERDAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE CREATION_DATE between trunc(sysdate-1) and trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and document.KIND = 'T'
GROUP BY department.tb
go

/*==============================================================*/
/* View: PAYMENT_COUNT_TODAY                                    */
/*==============================================================*/
create or replace view PAYMENT_COUNT_TODAY as
SELECT count(document.id) as PAYMENT_COUNT_TODAY, department.tb as TB
FROM BUSINESS_DOCUMENTS document
LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE document.CREATION_DATE >= trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and (document.KIND = 'P' or  (document.KIND = 'H' 
and  document.id  in (select PAYMENT_ID from DOCUMENT_EXTENDED_FIELDS fields where fields.VALUE = 'jur' and fields.name = 'receiver-type') ))
GROUP BY department.tb
go

/*==============================================================*/
/* View: PAYMENT_COUNT_YESTERDAY                                */
/*==============================================================*/
create or replace view PAYMENT_COUNT_YESTERDAY as
SELECT count(document.id) as PAYMENT_COUNT_YESTERDAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document 
LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE CREATION_DATE between trunc(sysdate-1) and trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and (document.KIND = 'P' or  (document.KIND = 'H' 
and  document.id  in (select PAYMENT_ID from DOCUMENT_EXTENDED_FIELDS fields where fields.VALUE = 'jur' and fields.name = 'receiver-type') ))
GROUP BY department.tb
go

/*==============================================================*/
/* View: TODAY_AGREEMENT_COUNT                                  */
/*==============================================================*/
create or replace view TODAY_AGREEMENT_COUNT as
SELECT count(usr.id) as TODAY_AGREEMENT_COUNT, department.tb as TB
FROM USERS usr LEFT JOIN DEPARTMENTS department
ON usr.department_id = department.id
WHERE AGREEMENT_DATE >= trunc(sysdate)
GROUP BY department.tb
go

/*==============================================================*/
/* View: TODAY_DISOLV_AGREEMENT_COUNT                           */
/*==============================================================*/
create or replace view TODAY_DISOLV_AGREEMENT_COUNT as
SELECT count(usr.id) as TODAY_DISOLV_AGREEMENT_COUNT, department.tb as TB
FROM USERS usr LEFT JOIN DEPARTMENTS department
ON usr.department_id = department.id
WHERE STATUS = 'D'
AND PROLONGATION_REJECTION_DATE >= trunc(sysdate)
GROUP BY department.tb
go

/*==============================================================*/
/* View: TOTAL_AGREEMENT_COUNT                                  */
/*==============================================================*/
create or replace view TOTAL_AGREEMENT_COUNT as
SELECT count(usr.id) as TOTAL_AGREEMENT_COUNT, department.tb as TB
FROM USERS usr LEFT JOIN DEPARTMENTS department
ON usr.department_id = department.id
GROUP BY department.tb
go

/*==============================================================*/
/* View: TOTAL_DISOLV_AGREEMENT_COUNT                           */
/*==============================================================*/
create or replace view TOTAL_DISOLV_AGREEMENT_COUNT as
SELECT count(usr.id) as TOTAL_DISOLV_AGREEMENT_COUNT, department.tb as TB
FROM USERS usr LEFT JOIN DEPARTMENTS department
ON usr.department_id = department.id
WHERE STATUS = 'D'
GROUP BY department.tb
go

/*==============================================================*/
/* View: TRANSFER_COUNT_TODAY                                   */
/*==============================================================*/
create or replace view TRANSFER_COUNT_TODAY as
SELECT count(document.id) as TRANSFER_COUNT_TODAY, department.tb as TB 
FROM BUSINESS_DOCUMENTS document 
LEFT JOIN DEPARTMENTS department 
ON document.department_id = department.id
WHERE document.CREATION_DATE >= trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and (document.KIND = 'E' or  (document.KIND = 'H' 
and  document.id  in (select PAYMENT_ID from DOCUMENT_EXTENDED_FIELDS fields where fields.VALUE = 'ph' and fields.name = 'receiver-type') ))
GROUP BY department.tb
go

/*==============================================================*/
/* View: TRANSFER_COUNT_YESTERDAY                               */
/*==============================================================*/
create or replace view TRANSFER_COUNT_YESTERDAY as
SELECT count(document.id) as TRANSFER_COUNT_YESTERDAY, department.tb as TB
FROM BUSINESS_DOCUMENTS document 
LEFT JOIN DEPARTMENTS department
ON document.department_id = department.id
WHERE document.CREATION_DATE between trunc(sysdate-1) and trunc(sysdate)
and document.STATE_CODE = 'EXECUTED' and (document.KIND = 'E' or  (document.KIND = 'H' 
and  document.id  in (select PAYMENT_ID from DOCUMENT_EXTENDED_FIELDS fields where fields.VALUE = 'ph' and fields.name = 'receiver-type') ))
GROUP BY department.tb
go

/*==============================================================*/
/* View: USER_COUNT                                             */
/*==============================================================*/
create or replace view USER_COUNT as
SELECT sum(count) as USER_COUNT, TB
FROM USER_COUNTER countr
WHERE MODULE IN ('PhizIC','mobile')
AND UPDATE_TIME > sysdate - INTERVAL '15' MINUTE
GROUP BY TB
go
