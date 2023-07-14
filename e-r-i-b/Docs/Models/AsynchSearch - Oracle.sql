/*==============================================================*/
/* Database name:  AsynchSearch                                 */
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     03.09.2013 15:53:33                          */
/*==============================================================*/


DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_SERVPROV_ASYNCSEARCH'; 
    EXECUTE IMMEDIATE('drop index IDX_SERVPROV_ASYNCSEARCH');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDERS_ASYNC_SEARCH'; 
    EXECUTE IMMEDIATE('drop table SERVICE_PROVIDERS_ASYNC_SEARCH cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_SERV_PROV_ASYNC_SEARCH'; 
    EXECUTE IMMEDIATE('drop sequence S_SERV_PROV_ASYNC_SEARCH');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

create sequence S_SERV_PROV_ASYNC_SEARCH
go

/*==============================================================*/
/* Table: SERVICE_PROVIDERS_ASYNC_SEARCH                        */
/*==============================================================*/
create table SERVICE_PROVIDERS_ASYNC_SEARCH 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(250),
   ALIAS                VARCHAR2(250),
   LEGAL_NAME           VARCHAR2(250),
   ACCOUNT              VARCHAR2(30),
   INN                  VARCHAR2(12),
   REGION_ID_LIST       VARCHAR2(250),
   IS_MOBILEBANK_ALLOWED CHAR(1),
   IS_AUTOPAYMENT_SUPPORTED CHAR(1),
   ACCOUNT_TYPE         VARCHAR2(20),
   EXTERNAL_SYSTEM_NAME VARCHAR2(100),
   IS_TEMPLATE_SUPPORTED CHAR(1),
   IS_ALLOW_PAYMENTS    CHAR(1),
   STATE                VARCHAR2(20),
   constraint PK_SERVICE_PROVIDERS_ASYNC_SEA primary key (ID)
)
go

comment on table SERVICE_PROVIDERS_ASYNC_SEARCH is
'Данные поставщиков услуг адаптированные для живого поиска'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.ID is
'ИД поставщика'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.NAME is
'Название поставщика'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.ALIAS is
'Алиас поставщика'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.LEGAL_NAME is
'Юридическое наименование поставщика'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.ACCOUNT is
'Счет поставщика'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.INN is
'ИНН'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.REGION_ID_LIST is
'Регионы поставщика'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.IS_MOBILEBANK_ALLOWED is
'Поддерживает мобильный банк'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.IS_AUTOPAYMENT_SUPPORTED is
'Поддерживает автоплатежи'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.ACCOUNT_TYPE is
'Тип счета при оплате'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.IS_TEMPLATE_SUPPORTED is
'Поддерживает шаблоны'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.IS_ALLOW_PAYMENTS is
'Поддерживает платежи'
go

comment on column SERVICE_PROVIDERS_ASYNC_SEARCH.STATE is
'Состояние поставщика (активен или нет)'
go

/*==============================================================*/
/* Index: IDX_SERVPROV_ASYNCSEARCH                              */
/*==============================================================*/
create index IDX_SERVPROV_ASYNCSEARCH on SERVICE_PROVIDERS_ASYNC_SEARCH (
   STATE ASC,
   ACCOUNT_TYPE ASC,
   IS_ALLOW_PAYMENTS ASC
)
go

