--ревизия 50447
--ревизия 51114
alter table ORDERS add(
	ADDITIONAL_FIELDS CLOB,
	PRINT_DESC        VARCHAR(200)
)
/

--ревизия 50687
create table ORDER_RECALLS  (
   CLAIM_ID             INTEGER                         not null,
   NOTIFICATION_STATUS  VARCHAR2(10)                    not null,
   NOTIFICATION_TIME    TIMESTAMP,
   NOTIFICATION_COUNT   INTEGER               default 0 not null,
   SHOP_ORDER_ID        INTEGER                         not null,
   STATUS_DESCRIPTION   VARCHAR2(255),
   constraint PK_ORDER_RECALLS primary key (CLAIM_ID)
)
/

create sequence S_ORDER_RECALLS
/

create index ORDER_REC_NOTIFY_TIME on ORDER_RECALLS (
   NOTIFICATION_TIME ASC
)
/

create index "DXFK_ORDER_REC_REF_SHOP_FIE" on ORDER_RECALLS
(
   SHOP_ORDER_ID
)
/

alter table ORDER_RECALLS
  add constraint FK_ORDER_RE_FK_ORDER__SHOP_FIE foreign key (SHOP_ORDER_ID)
      references SHOP_FIELDS (ID)
      on delete cascade
/


--ревизия 50844
alter table SHOP_FIELDS add (
	MOBILE_CHECKOUT_STATE VARCHAR(20), 
	MOBILE_CHECKOUT_PHONE VARCHAR(30)
)	
/
create index MOB_CHCKOUT_STTE_IDX on SHOP_FIELDS( MOBILE_CHECKOUT_STATE asc )
/
alter table SERVICE_PROVIDERS add MOBILE_CHECKOUT_AVAILABLE char(1) default '0' not null
/

--ревизия 50836 
drop table ORDER_INFO
/
drop index ORDERS_UUID
/
alter table BUSINESS_DOCUMENTS add ORDER_UUID varchar2(255)
/

alter session enable parallel DDL
/
alter session enable parallel DML
/
create unique index ORDERS_UUID on ORDERS(UUID) parallel 64
/
alter index ORDERS_UUID noparallel
/
merge /*+parallel 64*/ into BUSINESS_DOCUMENTS bd 
using ORDERS o
on (o.PAYMENT_ID=bd.ID)
when matched then update set 
    bd.ORDER_UUID=o.UUID
/

--ревизия 51968
create index RATE_IDX_FROMCUR_DEP_RATETYPE on RATE (
   FROM_CURRENCY ASC,
   DEPARTMENT_ID ASC,
   RATE_TYPE ASC
)
/

--ревизия 51998 
alter table INSURANCE_LINKS modify INSURANCE_NAME varchar(256)
/

--ревизия 52201
delete from EXCEPTION_ENTRY
where 
	kind = 'I' 
and APPLICATION not in ('PhizIA', 'PhizIC', 'mobile', 'mobile2', 'mobile3', 'mobile4', 'mobile5', 'mobile6', 'atm', 'Gate', 'Scheduler')
/