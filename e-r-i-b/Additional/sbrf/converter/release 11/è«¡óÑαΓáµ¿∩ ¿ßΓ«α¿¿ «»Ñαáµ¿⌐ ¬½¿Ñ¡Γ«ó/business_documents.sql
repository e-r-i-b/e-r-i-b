create table BUSINESS_DOCUMENTS_P  (
   ID                   INTEGER                         not null,
   GROUND               VARCHAR2(1024),
   LOGIN_ID             INTEGER,
   FORM_ID              INTEGER,
   CHANGED              TIMESTAMP                       not null,
   SIGNATURE_ID         INTEGER,
   STATE_CODE           VARCHAR2(25)                    not null,
   STATE_DESCRIPTION    VARCHAR2(400)                   not null,
   CURRENCY             CHAR(3),
   DEPARTMENT_ID        INTEGER                         not null,
   DOC_NUMBER           VARCHAR2(21),
   CREATION_TYPE        CHAR(1)                         not null,
   ARCHIVE              CHAR(1),
   KIND                 VARCHAR2(2),
   CREATION_DATE        TIMESTAMP,
   COMMISSION           NUMBER(19,4),
   COMMISSION_CURRENCY  CHAR(3),
   PAYER_NAME           VARCHAR2(256),
   RECEIVER_ACCOUNT     VARCHAR2(30),
   RECEIVER_NAME        VARCHAR2(256),
   AMOUNT               NUMBER(19,4),
   EXTERNAL_ID          VARCHAR2(80),
   EXTERNAL_OFFICE_ID   VARCHAR2(64),
   STATE_MACHINE_NAME   VARCHAR2(50),
   PAYER_ACCOUNT        VARCHAR2(26),
   OPERATION_DATE       TIMESTAMP,
   ADMISSION_DATE       TIMESTAMP,
   EXECUTION_DATE       TIMESTAMP,
   DOCUMENT_DATE        TIMESTAMP,
   REFUSING_REASON      VARCHAR2(256),
   EXTERNAL_OWNER_ID    VARCHAR2(100),
   TEMPLATE_ID          INTEGER,
   DESTINATION_AMOUNT   NUMBER(19,4),
   DESTINATION_CURRENCY CHAR(3),
   COUNT_ERROR          INTEGER                        default 0 not null,
   IS_LONG_OFFER        CHAR(1)                        default '0' not null,
   CREATION_SOURCE_TYPE CHAR(1)                        default '0' not null,
   EXTENDED_FIELDS      CLOB,
   SYSTEM_NAME          VARCHAR2(128),
   SUMM_TYPE            VARCHAR2(50),
   CONFIRM_STRATEGY_TYPE VARCHAR2(18),
   ADDITION_CONFIRM     CHAR(2),
   CONFIRM_EMPLOYEE     VARCHAR2(50),
   OPERATION_UID        VARCHAR2(32),
   PROMO_CODE           VARCHAR2(10),
   BILLING_DOCUMENT_NUMBER VARCHAR2(100),
   PROVIDER_EXTERNAL_ID VARCHAR2(128),
   RECIPIENT_ID         INTEGER,
   ACCESS_AUTOPAY_SCHEMES CLOB,
   SESSION_ID           VARCHAR2(64),
   CODE_ATM             VARCHAR2(255),
   CREATED_EMPLOYEE_LOGIN_ID INTEGER,
   CONFIRMED_EMPLOYEE_LOGIN_ID INTEGER,
   LOGIN_TYPE           VARCHAR2(10)
)
partition by range (CREATION_DATE)
SUBPARTITION by HASH (LOGIN_ID) SUBPARTITIONS 16 
(
    --2012
    partition P_2011_12 values less than (to_date('2012-01-01','yyyy-mm-dd')),
    partition P_2012_01 values less than (to_date('2012-02-01','yyyy-mm-dd')),
    partition P_2012_02 values less than (to_date('2012-03-01','yyyy-mm-dd')),
    partition P_2012_03 values less than (to_date('2012-04-01','yyyy-mm-dd')),
    partition P_2012_04 values less than (to_date('2012-05-01','yyyy-mm-dd')),
    partition P_2012_05 values less than (to_date('2012-06-01','yyyy-mm-dd')),
    partition P_2012_06 values less than (to_date('2012-07-01','yyyy-mm-dd')),
    partition P_2012_07 values less than (to_date('2012-08-01','yyyy-mm-dd')),
    partition P_2012_08 values less than (to_date('2012-09-01','yyyy-mm-dd')),
    partition P_2012_09 values less than (to_date('2012-10-01','yyyy-mm-dd')),
    partition P_2012_10 values less than (to_date('2012-11-01','yyyy-mm-dd')),
    partition P_2012_11 values less than (to_date('2012-12-01','yyyy-mm-dd')),
    partition P_2012_12 values less than (to_date('2013-01-01','yyyy-mm-dd')),
    --2013
    partition P_2013_01 values less than (to_date('2013-02-01','yyyy-mm-dd')),
    partition P_2013_02 values less than (to_date('2013-03-01','yyyy-mm-dd')),
    partition P_2013_03 values less than (to_date('2013-04-01','yyyy-mm-dd')),
    partition P_2013_04 values less than (to_date('2013-05-01','yyyy-mm-dd')),
    partition P_2013_05 values less than (to_date('2013-06-01','yyyy-mm-dd')),
    --создаем страховочную секцию
    partition P_MAXVALUE values less than (MAXVALUE)
)
/
insert /*+append*/ into BUSINESS_DOCUMENTS_P 
	select 
	   ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID,  nvl(STATE_CODE, 'ERROR'), nvl(STATE_DESCRIPTION, '!'), CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, COMMISSION_CURRENCY, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, SYSTEM_NAME, SUMM_TYPE, CONFIRM_STRATEGY_TYPE, ADDITION_CONFIRM, CONFIRM_EMPLOYEE, OPERATION_UID, PROMO_CODE, BILLING_DOCUMENT_NUMBER, PROVIDER_EXTERNAL_ID, RECIPIENT_ID, ACCESS_AUTOPAY_SCHEMES, SESSION_ID, CODE_ATM, CREATED_EMPLOYEE_LOGIN_ID, CONFIRMED_EMPLOYEE_LOGIN_ID, LOGIN_TYPE
	from BUSINESS_DOCUMENTS
/
			
--создаем праймари кей
create unique index IDX_PK_BD on BUSINESS_DOCUMENTS_P(ID) global partition by HASH(ID) PARTITIONS 8 parallel 16 online
/
alter index IDX_PK_BD noparallel
/

alter table BUSINESS_DOCUMENTS_P add constraint PK_BD primary key (ID) using index IDX_PK_BD 
/

--создаем таблицу гармошку-размазню
create table DOCUMENT_EXTENDED_FIELDS_P  (
   ID                   INTEGER                         not null,
   PAYMENT_ID           INTEGER                         not null,
   NAME                 VARCHAR2(64)                    not null,
   TYPE                 VARCHAR2(16)                    not null,
   VALUE                VARCHAR2(4000),
   IS_CHANGED           CHAR(1),

   constraint FK_DEF_TO_BD foreign key (PAYMENT_ID) references BUSINESS_DOCUMENTS_P(ID)
)
partition by REFERENCE(FK_DEF_TO_BD)
/

insert /*+append*/ into DOCUMENT_EXTENDED_FIELDS_P select * from DOCUMENT_EXTENDED_FIELDS
/

--индекс для обеспечения ссылочной целостности с BUSINESS_DOCUMENTS_P REFERENCE(FK_DEF_TO_BD)
create index IDX_FK_DEF_TO_BD on DOCUMENT_EXTENDED_FIELDS_P(PAYMENT_ID) global partition by HASH(PAYMENT_ID) PARTITIONS 16 parallel 16 
/
alter index IDX_FK_DEF_TO_BD noparallel
/
 
--индекс для обновления записей в DOCUMENT_EXTENDED_FIELDS
create unique index IDX_PK_DEF on DOCUMENT_EXTENDED_FIELDS_P(ID) global partition by HASH(ID) PARTITIONS 16 parallel 16 
/
alter index IDX_PK_DEF noparallel
/

--создаем индексы для ФК
create index IDX_FK_BD_TO_SIGNATURES on BUSINESS_DOCUMENTS_P(SIGNATURE_ID) global partition by HASH(SIGNATURE_ID) PARTITIONS 8
parallel 32 online
/
alter index IDX_FK_BD_TO_SIGNATURES noparallel
/
create index IDX_FK_BD_TO_PAYMENTFORMS on BUSINESS_DOCUMENTS_P(FORM_ID) global partition by HASH(FORM_ID) PARTITIONS 8
parallel 32 online
/
alter index IDX_FK_BD_TO_PAYMENTFORMS noparallel
/
create index IDX_FK_BD_TO_LOGINS on BUSINESS_DOCUMENTS_P(LOGIN_ID) global partition by HASH(LOGIN_ID) PARTITIONS 8
parallel 32 online
/
alter index IDX_FK_BD_TO_LOGINS noparallel
/

--ФК документов
alter table BUSINESS_DOCUMENTS_P
   add constraint FK_BD_TO_DEPARTMENTS foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
/

alter table BUSINESS_DOCUMENTS_P
   add constraint FK_BD_TO_SIGNATURES foreign key (SIGNATURE_ID)
      references SIGNATURES (ID)
/
alter table BUSINESS_DOCUMENTS_P
   add constraint FK_BD_TO_PAYMENTFORMS foreign key (FORM_ID)
      references PAYMENTFORMS (ID)
/
alter table BUSINESS_DOCUMENTS_P
   add constraint FK_BD_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
/


--Остальные индексы
create index IDX_BD_OD on BUSINESS_DOCUMENTS_P (
   OPERATION_DATE ASC
) global
parallel 32 online
/
alter index IDX_BD_OD noparallel
/

--для синхронизации документов с ВС
create index IDX_BD_EK on BUSINESS_DOCUMENTS_P (
   EXTERNAL_ID ASC,
   KIND ASC
) global
parallel 32 online
/
alter index IDX_BD_EK noparallel
/

--для выгрузки ЖБТ  и предположительно отчетов
create index IDX_BD_EDK on BUSINESS_DOCUMENTS_P (
   EXECUTION_DATE ASC,
   KIND ASC
) global
parallel 32 online
/
alter index IDX_BD_EDK noparallel
/

--индекс для клиентского приложения, блок/виджет последние операции
create index IDX_BD_LEDK on BUSINESS_DOCUMENTS_P (
   LOGIN_ID ASC,
   EXECUTION_DATE ASC,
   KIND ASC
) global
parallel 32 online
/
alter index IDX_BD_LEDK noparallel
/
--Основной индекс для джобов
create index IDX_BD_SK_BAD on BUSINESS_DOCUMENTS_P (
   STATE_CODE ASC,
   KIND ASC
) global
parallel 32 online
/
alter index IDX_BD_SK_BAD noparallel
/
--Для аудита
create index IDX_BD_SK_SCDF_BAD on BUSINESS_DOCUMENTS_P (
   STATE_CODE ASC,
   CREATION_DATE ASC,
   FORM_ID ASC
) global
parallel 32 online
/
alter index IDX_BD_SK_SCDF_BAD noparallel
/
--Для аудита
create index IDX_BD_NCD_BAD on BUSINESS_DOCUMENTS_P (
   DOC_NUMBER ASC,
   CREATION_DATE ASC
) global
parallel 32 online
/
alter index IDX_BD_NCD_BAD noparallel
/
--Для аудита
create index IDX_BD_ECD on BUSINESS_DOCUMENTS_P (
   CONFIRMED_EMPLOYEE_LOGIN_ID ASC,
   CREATION_DATE ASC
) global
parallel 32 online
/
alter index IDX_BD_ECD noparallel
/
--основной индекс для клиентского приложения и сотрудников КЦ
create index IDX_BD_LCDK on BUSINESS_DOCUMENTS_P (
   LOGIN_ID ASC,
   CREATION_DATE ASC,
   KIND ASC
) local
parallel 32 online
/
alter index IDX_BD_LCDK noparallel
/
--для градусников увисас и всяких отчетов, подсчитывающих созданные документы
create index IDX_BD_CD on BUSINESS_DOCUMENTS_P (
   CREATION_DATE ASC
) global partition by range (CREATION_DATE)
(
partition ARCHIVE values less than (to_date('2013-04-17','yyyy-mm-dd')),
partition P_MAXVALUE values less than (MAXVALUE)
)
parallel 32 online
/
alter index IDX_BD_CD noparallel
/


-- Переключение таблиц:

-- дропаем ссылающиеся констрейны
alter table BUSINESS_DOCUMENTS_RES
   drop constraint FK_RESOURCES_TO_BUSINESS_DOCUM
/

alter table STATEMENTS
   drop constraint FK_STATEMENT_TO_BUSINESS_DOCUM
/

alter table USERS_LIMITS_JOURNAL
   drop constraint FK_USR_LIMIT_TO_DOCS
/


-- помещаем таблицы в архив
alter table BUSINESS_DOCUMENTS rename to BUSINESS_DOCUMENTS_ARCH
/
alter table DOCUMENT_EXTENDED_FIELDS rename to DOCUMENT_EXTENDED_FIELDS_ARCH
/
alter table BUSINESS_DOCUMENTS_P rename to BUSINESS_DOCUMENTS
/
alter table DOCUMENT_EXTENDED_FIELDS_P rename to DOCUMENT_EXTENDED_FIELDS
/


--создаем констрейны ФК
alter table BUSINESS_DOCUMENTS_RES
   add constraint FK_RESOURCES_TO_BUSINESS_DOCUM foreign key (BUSINESS_DOCUMENT_ID)
      references BUSINESS_DOCUMENTS (ID)
/

alter table STATEMENTS
   add constraint FK_STATEMENT_TO_BUSINESS_DOCUM foreign key (CLAIM_ID)
      references BUSINESS_DOCUMENTS (ID)
/

alter table USERS_LIMITS_JOURNAL
   add constraint FK_USR_LIMIT_TO_DOCS foreign key (DOCUMENT_ID)
      references BUSINESS_DOCUMENTS (ID)
      on delete cascade
/

alter table BUSINESS_DOCUMENTS add constraint BD_AMOUNT_CHEK check ((AMOUNT IS NULL OR AMOUNT>=0) and (DESTINATION_AMOUNT IS NULL or DESTINATION_AMOUNT>=0)) novalidate
/

--вычистка старых платежей:
create table tmp_bd_convert_8_r (
    id number,
    description varchar2(1024),
    constraint pk_tmp_bd_convert_8_r primary key(id)
)
/

insert into tmp_bd_convert_8_r(id) select /*+ parallel (bd, 32)*/ id from BUSINESS_DOCUMENTS bd where not(((AMOUNT IS NULL OR AMOUNT>=0) AND (DESTINATION_AMOUNT is  NULL OR DESTINATION_AMOUNT>=0)))
/
--обновление документов найденных при подготовке к конвертации
declare
  templatesProcessed number:=0;
  paymentsProcessed  number:=0;
  reason             varchar2(100):= 'Платеж не может быть исполнен, пожалуйста, совершите новый';
begin
   dbms_output.enable(500000);
   for r in ( select bd.id, bd.state_code 
                from BUSINESS_DOCUMENTS bd 
                inner join tmp_bd_convert_8_r tmp on tmp.id=bd.id
                where DESTINATION_AMOUNT<0 or AMOUNT<0 ) 
   loop
     if (r.state_code in ('TEMPLATE', 'DRAFTTEMPLATE', 'WAIT_CONFIRM_TEMPLATE', 'SAVED_TEMPLATE')) then
        update BUSINESS_DOCUMENTS set DESTINATION_AMOUNT = null, DESTINATION_CURRENCY=null, AMOUNT = null, CURRENCY= null where id=r.id;
        update tmp_bd_convert_8_r set description = 'Удалили сумму у шаблона '||r.id where id=r.id;
        templatesProcessed := templatesProcessed + 1;
     else 
		if (r.state_code in ('DELETED', 'REFUSED')) then 
			update BUSINESS_DOCUMENTS set DESTINATION_AMOUNT = abs(DESTINATION_AMOUNT), AMOUNT = abs(AMOUNT) where id=r.id;
            update tmp_bd_convert_8_r set description = 'Сделали положительной суммы у удаленного или отказанного платежа '||r.id where id=r.id;
			paymentsProcessed := paymentsProcessed + 1;
		 else 
			update BUSINESS_DOCUMENTS set DESTINATION_AMOUNT = abs(DESTINATION_AMOUNT), AMOUNT = abs(AMOUNT), state_code='REFUSED', STATE_DESCRIPTION=reason, REFUSING_REASON=reason where id=r.id;
            update tmp_bd_convert_8_r set description = 'Сделали положительной суммы и отказали платеж '||r.id where id=r.id;
			paymentsProcessed := paymentsProcessed + 1;
		 end if; 
	 end if;
   end loop;
   dbms_output.put_line('Обновлено шаблонов:'||templatesProcessed);
   dbms_output.put_line('Обновлено платежей:'||paymentsProcessed);
end;
/

