--Снять органичения на связку таблиц
--Эти скрипты выполняются на базе EInvoicing
alter table ORDERS
   drop constraint FK_ORDERS_REFERENCE_E_IN_PER
go

alter table ORDER_RECALLS
   drop constraint FK_ORDER_RE_REFERENCE_SHOP_FIE
go

alter table SHOP_ADDITIONAL_FIELDS
   drop constraint FK_SHOP_ADD_REFERENCE_SHOP_FIE 
go

alter table SHOP_FIELDS
   drop constraint FK_SHOP_FIE_REFERENCE_ORDERS
go

alter table WITHDRAW_DOCUMENTS
   drop constraint FK_WITHDRAW_REFERENCE_SHOP_FIE
go

alter table WITHDRAW_DOCUMENTS
   drop constraint FK_WITHDRAW_REFERENCE_PAYMENTS
go

alter table WITHDRAW_EXTENDED_FIELDS
   drop constraint FK_WITHDRAW_REFERENCE_WITHDRAW
go

--Эти скрипты выполняются в базе основного приложения
--Копирование частичных заказов
insert into ORDERS@einvoicing select ord.*, null as CHECKED from ORDERS ord where ORDER_TYPE='P'
go

--Копирование данных заказов.
insert into ORDERS@einvoicing select ord.*, null AS CHECKED from ORDERS ord, SHOP_FIELDS sf where sf.ORDER_ID = ord.ID
go

--Копирование полей заказов.
insert into SHOP_FIELDS@einvocing (ID, ORDER_ID, BACK_URL, AIRLINE_RESERV_ID, AIRLINE_RESERV_EXPIRATION, AIRLINE_RESERVATION, CANCELED,MOBILE_CHECKOUT_STATE, MOBILE_CHECKOUT_PHONE) select ID, ORDER_ID, BACK_URL, AIRLINE_RESERV_ID, AIRLINE_RESERV_EXPIRATION, AIRLINE_RESERVATION, CANCELED,MOBILE_CHECKOUT_STATE, MOBILE_CHECKOUT_PHONE from SHOP_FIELDS sf
go

--Копирование дополнительных полей заказа.
insert into SHOP_ADDITIONAL_FIELDS@einvocing select saf.* from SHOP_ADDITIONAL_FIELDS saf
go

--Обновление информации о платежах
insert into PAYMENTS_INFO@einvoicing (ID, TICKET_INFO, TICKET_DESC, TICKET_STATUS, ORDER_UUID) select bd.ID as ID, ti.VALUE as TICKET_INFO, td.VALUE as TICKET_DESC, ts.VALUE as TICKET_STATUS, ord.UUID as ORDER_UUID from BUSINESS_DOCUMENTS bd, BUSINESS_DOCUMENTS_TO_ORDERS bdto, ORDERS ord, PAYMENTFORMS f,
(select PAYMENT_ID, VALUE from DOCUMENT_EXTENDED_FIELDS where NAME = 'tickets-info') ti,
(select PAYMENT_ID, VALUE from DOCUMENT_EXTENDED_FIELDS where NAME = 'tickets-desc') td,
(select PAYMENT_ID, VALUE from DOCUMENT_EXTENDED_FIELDS where NAME = 'tickets-status') ts
where bdto.ORDER_UUID = ord.UUID and bd.ID = bdto.BUSINESS_DOCUMENT_ID and ti.PAYMENT_ID = bd.ID and td.PAYMENT_ID = bd.ID and ts.PAYMENT_ID = bd.ID and f.NAME = 'AirlineReservationPayment' and f.ID = bd.FORM_ID and bd.EXTERNAL_ID is not null and ti.VALUE is not null
go

--добавляем пользователей.
insert into E_IN_PERSONS@einvoicing (ID, FIRSTNAME, SURNAME, PATRNAME, BIRTHDATE, TB, PASSPORT)
select distinct u.ID, 
       u.FIRST_NAME as FIRSTNAME, u.SUR_NAME as SURNAME, u.PATR_NAME as PATRNAME, 
       u.BIRTHDAY as BIRTHDATE,
       d.TB as TB,
       case 
          when pw.ID is not null THEN pw.DOC_SERIES || pw.DOC_NUMBER
          when rprf.ID is not null THEN rprf.DOC_SERIES || rprf.DOC_NUMBER
          else doc.DOC_SERIES || doc.DOC_NUMBER
       end as PASSPORT 
from ORDERS@link_voi3 ord, DEPARTMENTS d, USERS u
left join (select ID, DOC_SERIES, DOC_NUMBER, PERSON_ID from DOCUMENTS where DOC_TYPE = 'PASSPORT_WAY') pw on pw.PERSON_ID = u.ID
left join (select ID, DOC_SERIES, DOC_NUMBER, PERSON_ID from DOCUMENTS where DOC_TYPE = 'REGULAR_PASSPORT_RF') rprf on rprf.PERSON_ID = u.ID
left join (select ID, DOC_SERIES, DOC_NUMBER, PERSON_ID from DOCUMENTS where DOC_TYPE not in ('PASSPORT_WAY', 'REGULAR_PASSPORT_RF')) doc on doc.PERSON_ID = u.ID 
where u.DEPARTMENT_ID = d.ID and ord.USER_ID = u.ID
go

insert into ORDER_RECALLS@einvoicing (CLAIM_ID, NOTIFICATION_STATUS, NOTIFICATION_TIME, NOTIFICATION_COUNT, SHOP_ORDER_ID, STATUS_DESCRIPTION)
    select bd.EXTERNAL_ID CLAIM_ID, 
           orrec.NOTIFICATION_STATUS, 
           orrec.NOTIFICATION_TIME, 
           orrec.NOTIFICATION_COUNT,
           orrec.SHOP_ORDER_ID,
           orrec.STATUS_DESCRIPTION 
    from ORDER_RECALLS orrec, BUSINESS_DOCUMENTS bd
    where orrec.CLAIM_ID = bd.ID
go

--Включить ограничения на таблицу EINVOICING.
--Эти скрипты выполняются на базе EINVOICING
alter table ORDERS
   add constraint FK_ORDERS_REFERENCE_E_IN_PER foreign key (USER_ID)
      references E_IN_PERSONS (ID)
go

alter table ORDER_RECALLS
   add constraint FK_ORDER_RE_REFERENCE_SHOP_FIE foreign key (SHOP_ORDER_ID)
      references SHOP_FIELDS (ID)
go

alter table SHOP_ADDITIONAL_FIELDS
   add constraint FK_SHOP_ADD_REFERENCE_SHOP_FIE foreign key (SHOP_ORDER_ID)
      references SHOP_FIELDS (ID)
go

alter table SHOP_FIELDS
   add constraint FK_SHOP_FIE_REFERENCE_ORDERS foreign key (ORDER_ID)
      references ORDERS (ID)
go

alter table WITHDRAW_DOCUMENTS
   add constraint FK_WITHDRAW_REFERENCE_SHOP_FIE foreign key (ORDER_INFO_ID)
      references SHOP_FIELDS (ID)
go

alter table WITHDRAW_DOCUMENTS
   add constraint FK_WITHDRAW_REFERENCE_PAYMENTS foreign key (PAYMENT_ID)
      references PAYMENTS_INFO (ID)
go

alter table WITHDRAW_EXTENDED_FIELDS
   add constraint FK_WITHDRAW_REFERENCE_WITHDRAW foreign key (WITHDRAW_ID)
      references WITHDRAW_DOCUMENTS (ID)
go

--Необходимо счетчики базы EINVOICING пересоздать, так чтобы счетчики выдавили следующее за максимальным id значение.
--ORDERS, E_IN_PERSONS, SHOP_FIELDS, SHOP_ADDITIONAL_FIELDS.

