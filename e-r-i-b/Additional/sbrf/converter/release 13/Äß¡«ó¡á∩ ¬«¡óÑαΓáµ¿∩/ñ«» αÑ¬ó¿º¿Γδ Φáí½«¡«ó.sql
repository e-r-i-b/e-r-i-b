alter session enable parallel dml
/
alter session enable parallel ddl
/

create table PAYMENTS_DOCUMENTS_EXT$T 
(
   ID                   INTEGER              not null,
   KIND                 VARCHAR(50)          not null,
   NAME                 VARCHAR2(64)         not null,
   VALUE                VARCHAR2(4000),
   PAYMENT_ID           INTEGER              not null,
   CHANGED              CHAR(1)              default '0' not null
)
/

insert /*+append parallel(en, 32)*/into PAYMENTS_DOCUMENTS_EXT$T en  (ID, KIND, NAME, VALUE, PAYMENT_ID, CHANGED)
    select /*+parallel(ext, 128)*/ ID, KIND, NAME, VALUE, PAYMENT_ID, '0' as CHANGED from PAYMENTS_DOCUMENTS_EXT ext
/

alter table PAYMENTS_DOCUMENTS_EXT rename constraint PK_PAYMENTS_DOCUMENTS_EXT to PK_PAYMENTS_DOCUMENTS_EXT$O
/
alter table PAYMENTS_DOCUMENTS_EXT rename constraint FK_TEMPLATE_EXT_TO_TEMPLATE to FK_TEMPLATE_EXT_TO_TEMPLATE$O
/
alter index PK_PAYMENTS_DOCUMENTS_EXT rename to PK_PAYMENTS_DOCUMENTS_EXT$O
/
alter index DXFK_TEMPLATE_EXT_TO_TEMPLATE rename to DXFK_TEMPLATE_EXT_TO_TEMPLATE$O
/

create unique index PK_PAYMENTS_DOCUMENTS_EXT on PAYMENTS_DOCUMENTS_EXT$T(ID) parallel 128 tablespace INDX
/
create index DXFK_TEMPLATE_EXT_TO_TEMPLATE
    on PAYMENTS_DOCUMENTS_EXT$T(PAYMENT_ID) parallel 128 tablespace INDX
/

alter table PAYMENTS_DOCUMENTS_EXT$T
    add constraint PK_PAYMENTS_DOCUMENTS_EXT primary key(ID) using index PK_PAYMENTS_DOCUMENTS_EXT 
/
alter table PAYMENTS_DOCUMENTS_EXT$T add constraint FK_TEMPLATE_EXT_TO_TEMPLATE foreign key(PAYMENT_ID) 
    references PAYMENTS_DOCUMENTS(ID) on delete cascade novalidate
/

alter table PAYMENTS_DOCUMENTS_EXT rename to PAYMENTS_DOCUMENTS_EXT$O
/
alter table PAYMENTS_DOCUMENTS_EXT$T rename to PAYMENTS_DOCUMENTS_EXT
/

alter table  PAYMENTS_DOCUMENTS_EXT noparallel
/
alter index  PK_PAYMENTS_DOCUMENTS_EXT noparallel
/
alter index  DXFK_TEMPLATE_EXT_TO_TEMPLATE noparallel
/