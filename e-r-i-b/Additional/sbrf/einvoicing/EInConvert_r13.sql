--E-invoicing 13 релиз
-- @einvoicing1 - dblink из базы EINVOICING на дазу ЕРИБ

--заполняем профиль пользователя
--выполняется на базе ЕРИБ
INSERT INTO SHOP_PROFILES@einvoicing1(ID, FIRST_NAME, SUR_NAME, PATR_NAME, PASSPORT, BIRTHDATE, TB) 
select distinct u.ID, 
       u.FIRST_NAME as FIRSTNAME, u.SUR_NAME as SURNAME, u.PATR_NAME as PATRNAME, 
       case 
          when pw.ID is not null THEN pw.DOC_SERIES || pw.DOC_NUMBER
          when rprf.ID is not null THEN rprf.DOC_SERIES || rprf.DOC_NUMBER
          else doc.DOC_SERIES || doc.DOC_NUMBER
       end as PASSPORT,
        u.BIRTHDAY as BIRTHDATE,
       d.TB as TB 
from ORDERS ord, DEPARTMENTS d, USERS u
left join (select ID, DOC_SERIES, DOC_NUMBER, PERSON_ID from DOCUMENTS where DOC_TYPE = 'PASSPORT_WAY') pw on pw.PERSON_ID = u.ID
left join (select ID, DOC_SERIES, DOC_NUMBER, PERSON_ID from DOCUMENTS where DOC_TYPE = 'REGULAR_PASSPORT_RF') rprf on rprf.PERSON_ID = u.ID
left join (select ID, DOC_SERIES, DOC_NUMBER, PERSON_ID from DOCUMENTS where DOC_TYPE not in ('PASSPORT_WAY', 'REGULAR_PASSPORT_RF')) doc on doc.PERSON_ID = u.ID 
where u.DEPARTMENT_ID = d.ID and ord.USER_ID = u.ID
go

--заполняем информацию о заказах
--выполняется на базе ЕРИБ в NODE_ID прописать идентификатор блока
INSERT INTO SHOP_ORDERS@einvoicing1
        (ID,  UUID,   EXTERNAL_ID,    TYPE,        ORDER_DATE, STATE,   PROFILE_ID, PHONE,                   RECEIVER_CODE,  RECEIVER_NAME,   AMOUNT,   CURRENCY,   DESCRIPTION,   RECEIVER_ACCOUNT,   BIC,   CORR_ACCOUNT,            INN,   KPP,   PRINT_DESCRIPTION, BACK_URL,   NODE_ID,  KIND) 
(select distinct  o.ID, o.UUID, o.EXTENDED_ID, o.ORDER_TYPE, o.ORDER_DATE, 'STATE',  o.USER_ID,  substr(f.MOBILE_CHECKOUT_PHONE, -1, 10) , o.SYSTEM_NAME,  o.RECEIVER_NAME, o.AMOUNT, o.CURRENCY, o.DESCRIPTION, o.RECEIVER_ACCOUNT, o.BIC, o.CORRESPONDENT_ACCOUNT, o.INN, o.KPP, o.PRINT_DESC       , f.BACK_URL, 1, CASE WHEN f.AIRLINE_RESERV_ID is null THEN 'INTERNET_SHOP' ELSE 'AEROFLOT' 	END KIND    
from orders o left join SHOP_FIELDS f on  o.id = f.ORDER_ID 
where o.SYSTEM_NAME != 'FNS' and o.SYSTEM_NAME != 'UEC')
go 

--собираем детальную информацию о заказе
--выполняется на базе EINVOICING
create table temp1 
(
   ORDER_ID        INTEGER ,
   FIELD           VARCHAR2(4000)
)
go
--выполняется на базе ЕРИБ
update SHOP_ORDERS@einvoicing1
set DETAIL_INFO=(select AIRLINE_RESERVATION from SHOP_FIELDS where SHOP_ORDERS.ID = SHOP_FIELDS.ORDER_ID)
where KIND='AEROFLOT'
go

--выполняется на базе ЕРИБ
insert into temp1@einvoicing1(ORDER_ID, FIELD)
select SHOP_FIELDS.ORDER_ID, ('<field><name>'||PRODUCT_NAME||'</name><description>'||DESCRIPTION||'</description><count>'||COUNT||'</count><price><amount>'||AMOUNT||'</amount><currency>'||CURRENCY||'</currency></price></field>') 
from SHOP_ADDITIONAL_FIELDS, SHOP_FIELDS 
where SHOP_ADDITIONAL_FIELDS.SHOP_ORDER_ID = SHOP_FIELDS.ID 
go
--выполняется на базе EINVOICING
update SHOP_ORDERS set DETAIL_INFO = (
select
 '<fields>'||wm_concat(FIELD)||'</fields>'
from temp1
where temp1.ORDER_ID = SHOP_ORDERS.id
group by ORDER_ID
)
go
--выполняется на базе EINVOICING
update SHOP_ORDERS set DETAIL_INFO = REPLACE(DETAIL_INFO, '>,<', '><')
go 
--выполняется на базе EINVOICING
drop table temp1
go

insert into DOCUMENT_EXTENDED_FIELDS (ID, PAYMENT_ID, NAME, TYPE, VALUE, IS_CHANGED) 
select 
    S_DOCUMENT_EXTENDED_FIELDS.nextVal as ID, 
    bdto.BUSINESS_DOCUMENT_ID as PAYMENT_ID,
    'orderFields' as NAME,
    'string' as TYPE,
    so.DETAIL_INFO as VALUE,
    0 as IS_CHANGED
from
    BUSINESS_DOCUMENTS_TO_ORDERS bdto, 
    BUSINESS_DOCUMENTS bd,
    SHOP_ORDERS so
where
    bd.ID = bdto.BUSINESS_DOCUMENT_ID and
    bd.KIND = 'P' and
    bdto.ORDER_UUID = so.UUID and
    so.KIND = 'INTERNET_SHOP'
go

--выполняется на базе ЕРИБ
update SHOP_ORDERS@einvoicing1 so set  STATE=
(
select distinct 
case 
when o.USER_ID is null                then 'CREATED'
when bdo.BUSINESS_DOCUMENT_ID is null and sf.CANCELED='1' then 'CANCELED'
when bdo.BUSINESS_DOCUMENT_ID is null then 'RELATED'
when bd.STATE_CODE='INITIAL' or bd.STATE_CODE='SAVED'  then 'PAYMENT'
when bd.STATE_CODE='ERROR' then 'ERROR'
when bd.STATE_CODE='EXECUTED' then 'EXECUTED'
when bd.STATE_CODE='REFUSED'then 'REFUSED'
when not(orr.CLAIM_ID is null) then 'PARTIAL_REFUND'
when bd.STATE_CODE='RECALLED' then 'REFUND'
else 'WRITE_OFF'
end STATE
from ORDERS o left join SHOP_FIELDS sf on o.id=sf.ORDER_ID left join ORDER_RECALLS orr on orr.SHOP_ORDER_ID=sf.id left join BUSINESS_DOCUMENTS_TO_ORDERS bdo on bdo.ORDER_UUID = o.UUID left join BUSINESS_DOCUMENTS bd on bdo.BUSINESS_DOCUMENT_ID = bd.ID
where o.SYSTEM_NAME != 'FNS' and o.SYSTEM_NAME != 'UEC' and so.id = o.id
) 
go 
--выполняется на базе ЕРИБ
update SHOP_ORDERS@einvoicing1 so set UTRRNO=
(select bd.BILLING_DOCUMENT_NUMBER
from ORDERS o left join BUSINESS_DOCUMENTS_TO_ORDERS bdo on bdo.ORDER_UUID = o.UUID left join BUSINESS_DOCUMENTS bd on bdo.BUSINESS_DOCUMENT_ID = bd.ID
where o.SYSTEM_NAME != 'FNS' and o.SYSTEM_NAME != 'UEC' and so.id = o.id) 


-- заполняем информацию об оповещениях
--выполняется на базе ЕРИБ
INSERT INTO SHOP_NOTIFICATIONS@einvoicing1 ("DATE", UUID, EXTERNAL_ID, NOTIF_STATUS, NOTIF_DATE, NOTIF_COUNT, NOTIF_STATUS_DESCRIPTION, STATE) 
    (select sysdate, UUID, EXTENDED_ID, 
CASE 
    when STATUS='OK' then 'NOTIFIED'  
    when STATUS='NOT_SEND' then 'CREATED' 
    when STATUS='SUSPENDED' or STATUS='ERROR' then 'ERROR' 
end NOTIF_STATUS,
NOTIFICATION_TIME, 
NOTIFICATION_COUNT,
STATUS_DISCRIPTION,
'STATE'  
from ORDERS where SYSTEM_NAME != 'FNS' and SYSTEM_NAME != 'UEC')
GO
--выполняется на базе EINVOICING
update SHOP_NOTIFICATIONS set UTRRNO=(SELECT UTRRNO  from SHOP_ORDERS where SHOP_NOTIFICATIONS.UUID=SHOP_ORDERS.UUID), STATE=(SELECT STATE  from SHOP_ORDERS where SHOP_NOTIFICATIONS.UUID=SHOP_ORDERS.UUID)
go

-- заполняем информацию об отказанных заявках
--выполняется на базе ЕРИБ
INSERT INTO BUSINESS_DOCUMENTS_TO_ORDERS(BUSINESS_DOCUMENT_ID, ORDER_UUID) 
   select orr.CLAIM_ID, o.UUID from ORDERS o join SHOP_FIELDS sf on o.id=sf.ORDER_ID join ORDER_RECALLS orr on orr.SHOP_ORDER_ID=sf.id
GO
--выполняется на базе ЕРИБ
alter table ORDER_RECALLS  add UUID varchar2(32)
go
--выполняется на базе ЕРИБ
update ORDER_RECALLS set UUID=DBMS_RANDOM.STRING('X', 32)
go
--выполняется на базе ЕРИБ
INSERT INTO SHOP_RECALLS@einvoicing1(UUID, RECEIVER_CODE, RECALL_DATE,      ORDER_UUID, EXTERNAL_ID, UTRRNO, STATE, type) 
select LOWER(orr.UUID), o.SYSTEM_NAME, bd.CREATION_DATE, o.UUID,    def.VALUE,    bd.BILLING_DOCUMENT_NUMBER,
case 
when bd.STATE_CODE='ERROR' then 'ERROR'
when bd.STATE_CODE='EXECUTED' then 'EXECUTED'
when bd.STATE_CODE='REFUSED'then 'REFUSED'
else 'CREATED'
end STATE_CODE,
case 
when bd.KIND='RG' then 'PARTIAL'
when bd.KIND='RO' then 'FULL'
end STATE_CODE 
from ORDER_RECALLS orr join orders o on o.id = orr.SHOP_ORDER_ID left join BUSINESS_DOCUMENTS bd on bd.id= orr.CLAIM_ID
left join DOCUMENT_EXTENDED_FIELDS def on def.PAYMENT_ID = bd.ID and def.NAME='external-document-id'
go
-- оповещения об отказах
--выполняется на базе ЕРИБ
INSERT INTO SHOP_NOTIFICATIONS@einvoicing1 (CREATE_DATE, UUID, EXTERNAL_ID, NOTIF_STATUS, NOTIF_DATE, NOTIF_COUNT, NOTIF_STATUS_DESCRIPTION, STATE) 
    (select sysdate, LOWER(UUID), 'external_id', 
CASE 
    when NOTIFICATION_STATUS='OK' then 'NOTIFIED'  
    when NOTIFICATION_STATUS='NOT_SEND' then 'CREATED' 
    when NOTIFICATION_STATUS='SUSPENDED' or NOTIFICATION_STATUS='ERROR' then 'ERROR' 
end NOTIF_STATUS,
NOTIFICATION_TIME, 
NOTIFICATION_COUNT,
STATUS_DESCRIPTION,
'STATE'  
from ORDER_RECALLS )
GO
--выполняется на базе ЕРИБ
alter table ORDER_RECALLS  drop column UUID
go
--выполняется на базе EINVOICING
update SHOP_NOTIFICATIONS set UTRRNO=(SELECT UTRRNO  from SHOP_RECALLS where SHOP_NOTIFICATIONS.UUID=SHOP_RECALLS.UUID) 
where EXTERNAL_ID='external_id' 
go
--выполняется на базе EINVOICING
update SHOP_NOTIFICATIONS set STATE=(SELECT STATE  from SHOP_RECALLS where SHOP_NOTIFICATIONS.UUID=SHOP_RECALLS.UUID)
where EXTERNAL_ID='external_id' 
go
--выполняется на базе EINVOICING
update SHOP_NOTIFICATIONS set EXTERNAL_ID = (SELECT EXTERNAL_ID  from SHOP_RECALLS where SHOP_NOTIFICATIONS.UUID=SHOP_RECALLS.UUID)
where EXTERNAL_ID='external_id' 
go

--Необходимо счетчики базы EINVOICING пересоздать, так чтобы счетчики выдавили следующее за максимальным id значение.
--SHOP_PROFILES, SHOP_NOTIFICATIONS, SHOP_RECALLS, SHOP_ORDERS.

--удалить лишние записи из основной БД








