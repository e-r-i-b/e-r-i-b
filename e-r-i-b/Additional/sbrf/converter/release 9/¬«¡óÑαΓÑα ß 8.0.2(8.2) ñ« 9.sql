--
-- Новые таблицы 9-го релиза
--
-- Загрузка файла «Описание расчетов» в АРМ сотрудника
create table FILES  (
   ID                   integer                         not null,
   FILE_NAME            varchar2(256),
   DATA                 blob,
   constraint PK_FILES primary key (ID)
)
/
create sequence S_FILES
/

-- Функционал групп риска
create table GROUPS_RISK  (
   ID                   integer                         not null,
   NAME                 varchar2(100)                   not null,
   constraint PK_GROUPS_RISK primary key (ID),
   constraint UNIQUE_NAME unique (NAME)
)
/
create sequence S_GROUPS_RISK
/

-- Справочник ОМС (для открытия)
create table IMAPRODUCT  (
   ID                   integer                         not null,
   NAME                 varchar2(255)                   not null,
   TYPE                 integer                         not null,
   SUBTYPE              integer                         not null,
   CURRENCY             char(3)                         not null,
   CONTRACT_TEMPLATE    clob                            not null,
   constraint PK_IMAPRODUCT primary key (ID)
)
/
comment on table IMAPRODUCT is 'ОМС, загруженные из справочника ЦАС НСИ'
/
create sequence S_IMAPRODUCT
/

-- Линк для хранения информации о программе лояльности
create table LOYALTY_PROGRAM_LINKS  (
   ID                   integer                         not null,
   EXTERNAL_ID          varchar2(80)                    not null,
   LOGIN_ID             integer                         not null,
   SHOW_IN_MAIN         char(1)                         not null,
   SHOW_OPERATIONS      char(1)                         not null,
   STATE                varchar2(10)                    not null,
   constraint PK_LOYALTY_PROGRAM_LINKS primary key (ID),
   constraint AK_LOYALTY_LOGIN unique (LOGIN_ID)
)
/
create sequence S_LOYALTY_PROGRAM_LINKS
/

-- Заявка на подключение к МБ
create table MB_REGISTRATION_CLAIMS  (
   ID                   integer                         not null,
   USER_ID              integer,
   "DATE"               timestamp                       not null,
   TARIFF               char                            not null,
   DEPARTMENT_TB        varchar2(4)                     not null,
   PHONE_NUMBER         varchar2(32)                    not null,
   CARD_NUMBER          varchar2(32)                    not null,
   constraint PK_MB_REGISTRATION_CLAIMS primary key (ID)
)
/
comment on column MB_REGISTRATION_CLAIMS.USER_ID is 'Идентификатор клиента, оставившего заявку'
/
comment on column MB_REGISTRATION_CLAIMS."DATE" is 'Дата подключения'
/
comment on column MB_REGISTRATION_CLAIMS.DEPARTMENT_TB is 'ТБ клиента, оставившего заявку на подключение'
/
comment on column MB_REGISTRATION_CLAIMS.PHONE_NUMBER is 'Номер телефона в заявке на подключение'
/
comment on column MB_REGISTRATION_CLAIMS.CARD_NUMBER is 'Номер карты в заявке на подключение'
/
create index IDX_MB_REGCLAIM_DATE on MB_REGISTRATION_CLAIMS ( "DATE" ASC )
/
create index "DXFK_MB_REGCLAIM_USER" on MB_REGISTRATION_CLAIMS( USER_ID )
/
alter table MB_REGISTRATION_CLAIMS
   add constraint FK_MB_REGCLAIM_USER foreign key (USER_ID)
      references USERS (ID)
      on delete set null
/
create sequence S_MB_REGISTRATION_CLAIMS
/

-- Настройку отображения продуктов в зависимости от сегмента клиента
create table PFP_CP_TARGET_GROUPS_BUNDLE  (
   PRODUCT_ID           integer                         not null,
   SEGMENT_CODE         varchar2(20)                    not null,
   constraint PK_PFP_CP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)
/
create sequence S_PFP_CP_TARGET_GROUPS_BUNDLE
/

create table PFP_IP_TARGET_GROUPS_BUNDLE  (
   PRODUCT_ID           integer                         not null,
   SEGMENT_CODE         varchar2(20)                    not null,
   constraint PK_PFP_IP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)
/
create sequence S_PFP_IP_TARGET_GROUPS_BUNDLE
/

create table PFP_SP_TARGET_GROUPS_BUNDLE  (
   PRODUCT_ID           integer                         not null,
   SEGMENT_CODE         varchar2(20)                    not null,
   constraint PK_PFP_SP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)
/
create sequence S_PFP_SP_TARGET_GROUPS_BUNDLE
/

-- Журнал операций для лимитов
create table TRANSACTIONS_JOURNAL  (
   ID                   integer                         not null,
   LOGIN_ID             integer,
   CREATION_DATE        date,
   JOURNAL_TYPE         varchar2(32),
   APPLICATION          varchar2(32),
   OPERATION_ID         integer,
   OPERATION_ALLOWED    varchar2(32),
   OP_AMOUNT            number(10,2),
   OP_AMOUNT_CURRENCY   char(3),
   constraint PK_TRANSACTIONS_JOURNAL primary key (ID)
)
/
create sequence S_TRANSACTIONS_JOURNAL
/

--
-- Конвертация структуры
--

-- SERVICE_PROVIDERS
alter table SERVICE_PROVIDERS add ( 
	SORT_PRIORITY integer,	                      -- Сортировка поставщиков
	ATM_AVAILABLE char(1) default '0' not null,   --Признак возможности совершения оплаты через УС  
	IS_BAR_SUPPORTED char(1) default '0' not null,
    GROUP_RISK_ID integer,
    IMAGE_HELP_ID integer 	
)
/
update SERVICE_PROVIDERS set SORT_PRIORITY = 0
/
alter table SERVICE_PROVIDERS modify (SORT_PRIORITY not null)
/
-- Настройка графической подсказки на форме оплаты ПУ 
alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_HELP_TO_IMAGES foreign key (IMAGE_HELP_ID)
      references IMAGES (ID)
/
alter table SERVICE_PROVIDERS add constraint FK_S_PROVIDERS_TO_GROUPS_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID)
/
alter table IMAGES modify IMAGE_HELP varchar2(100)
/
create index "DXFK_S_PROVIDERS_HELP_TO_IMAGE" on SERVICE_PROVIDERS
(
   IMAGE_HELP_ID
)
/

-- PAYMENTFORMS
-- добавление АТМ. 
alter table PAYMENTFORMS add ATM_TRANSFORMATION clob
/
 
-- DEPARTMENTS
-- Поле возможности заказа омс в офисе
alter table DEPARTMENTS add (IS_OPEN_IMA_OFFICE char(1) default 0 not null)
/
 
-- PFP_INSURANCE_PRODUCTS
-- Доработка справочника продуктов в ПФП
alter table PFP_INSURANCE_PRODUCTS add (
	MIN_INCOME number(7,2),
	MAX_INCOME number(7,2),
	DEFAULT_INCOME number(7,2)
)
/
alter table PFP_PRODUCTS add (
	MIN_INCOME number(7,2),
	MAX_INCOME number(7,2),
	DEFAULT_INCOME number(7,2)
)	
/

-- PFP_BASE_PRODUCT
-- Добавление доходности продукта на форму добавление продукта
alter table PFP_BASE_PRODUCT add PRODUCT_INCOME number(7,2)
/

--PERSONAL_FINANCE_PROFILE
alter table PERSONAL_FINANCE_PROFILE add (
	PLAN_DATE TIMESTAMP,
	PLAN_MONEY_AMOUNT NUMBER(19,4),
	PLAN_MONEY_CURRENCY CHAR(3)
)
/

-- LOGINS
-- Доработка входа клиента: поиск клиента по карте Сбера
alter table LOGINS add LAST_LOGON_VISITING_MODE varchar2(40)
/

-- PERSON_TARGET
-- Добавить привязку иконок целей на форму "Цели и средства". 
alter table PERSON_TARGET add IMAGE_ID integer
/
alter table PERSON_TARGET  
	add constraint FK_PERSON_TARGET_TO_IMAGES foreign key (IMAGE_ID)
    references  IMAGES(ID) 
/

-- USERS
-- Новые поля в профиле клиента ( код сегмента, к которому относят клиента; код тарифного плана; дата подключения тарифного плана; номер менеджера)
alter table USERS add (
	SEGMENT_CODE			varchar2(20),
	TARIF_PLAN_CODE			varchar2(20),
	TARIF_PLAN_CONNECT_DATE date,
	MANAGER_ID 				integer
)	
/

--EMPLOYEES
alter table EMPLOYEES add (
	MANAGER_PHONE varchar2(20),
	MANAGER_EMAIL varchar2(40),
	MANAGER_LEAD_EMAIL varchar2(40)
)
/

-- Доработка функционала суточных кумулятивных лимитов по группам риска

-- Доработка стратегий подтверждения (лимиты)
delete from PAYMENTS_GROUP_RISK
/
alter table PAYMENTS_GROUP_RISK drop column PAYMENT_TYPE
/
alter table PAYMENTS_GROUP_RISK drop column GROUP_RISK
/
alter table PAYMENTS_GROUP_RISK add (
    KIND varchar(3) not null,
    GROUP_RISK_ID integer not null
)
/

--добавление новых связей
alter table LIMITS modify TYPE varchar2(33)
/

alter table LIMITS add (
    GROUP_RISK_ID integer null,
	OPERATION_TYPE varchar2(30) null,
	RESTRICTION_TYPE varchar2(16) null,
	OPERATION_COUNT integer null
)
/
alter table LIMITS add constraint FK_LIMITS_TO_GROUPS_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID) on delete cascade 
/      

-- создание групп риска, конвертация поставщиков
declare
    gr_id number;
    SOC_CARD_AMOUNT number:=1000000;
    SOC_CARD_COUNT number:=2;
    CONVERSION_COUNT number:=100;
begin
    ---Низкая группа риска
    select S_GROUPS_RISK.nextval into gr_id from dual;
    insert into GROUPS_RISK values gr_id, 'Низкая группа риска';
    --Поставщики
    update SERVICE_PROVIDERS set GROUP_RISK_ID=gr_id where GROUP_RISK = 'LOW';
    --Лимиты
    update LIMITS set GROUP_RISK_ID = gr_id where GROUP_RISK = 'LOW';

    ---Средняя группа риска
    select S_GROUPS_RISK.nextval into gr_id from dual;
    insert into GROUPS_RISK values gr_id, 'Средняя группа риска';
    --Поставщики
    update SERVICE_PROVIDERS set GROUP_RISK_ID=gr_id where GROUP_RISK = 'MEDIUM';
    --Лимиты
    update LIMITS set GROUP_RISK_ID = gr_id where GROUP_RISK = 'MEDIUM';

    ---Высокая группа риска
    select S_GROUPS_RISK.nextval into gr_id from dual;
    insert into GROUPS_RISK values gr_id, 'Высокая группа риска';
    --Поставщики
    update SERVICE_PROVIDERS set GROUP_RISK_ID=gr_id where GROUP_RISK = 'HIGH';
    --Лимиты
    update LIMITS set GROUP_RISK_ID = gr_id where GROUP_RISK = 'HIGH';

    ---Самая низкая группа риска
    select S_GROUPS_RISK.nextval into gr_id from dual;
    insert into GROUPS_RISK values gr_id, 'Самая низкая группа риска';
    --Поставщики
    update SERVICE_PROVIDERS set GROUP_RISK_ID=gr_id where GROUP_RISK = 'LOWEST';
    --Лимиты
    update LIMITS set GROUP_RISK_ID = gr_id where GROUP_RISK = 'LOWEST';

    insert into PAYMENTS_GROUP_RISK (ID, DEPARTMENT_ID, KIND, GROUP_RISK_ID) 
        select (S_PAYMENTS_GROUP_RISK.nextval, id, 'S', gr_id) from DEPARTMENTS where osb is null and office is null;
    insert into PAYMENTS_GROUP_RISK (ID, DEPARTMENT_ID, KIND, GROUP_RISK_ID) 
        select (S_PAYMENTS_GROUP_RISK.nextval, id, 'J', gr_id) from DEPARTMENTS where osb is null and office is null;
    insert into PAYMENTS_GROUP_RISK (ID, DEPARTMENT_ID, KIND, GROUP_RISK_ID) 
        select (S_PAYMENTS_GROUP_RISK.nextval, id, 'EP', gr_id) from DEPARTMENTS where osb is null and office is null;
    insert into PAYMENTS_GROUP_RISK (ID, DEPARTMENT_ID, KIND, GROUP_RISK_ID) 
        select (S_PAYMENTS_GROUP_RISK.nextval, id, 'IP', gr_id) from DEPARTMENTS where osb is null and office is null;

    ---TODO СОЦ.КАРТА и конверсионные операции	
end;
/
alter table PAYMENTS_GROUP_RISK add constraint FK_TO_PAY_GR_RSK_GROUP_RISK foreign key (GROUP_RISK_ID) references GROUPS_RISK(ID)
/
--заполнение данных по лимитам
update LIMITS set TYPE = 'GROUP_RISK' where GROUP_RISK = 'SOCCARD' or GROUP_RISK = 'LOWEST' or GROUP_RISK = 'LOW' or GROUP_RISK = 'MEDIUM' or GROUP_RISK = 'HIGH'
/
update LIMITS set TYPE = 'OBSTRUCTION'  where GROUP_RISK = 'GENERAL'
/
update LIMITS set TYPE = 'IMSI'  where GROUP_RISK = 'IMSI'
/
update LIMITS set OPERATION_TYPE = 'NEED_ADDITIONAL_CONFIRN'  where TYPE = 'GROUP_RISK'
/
update LIMITS set OPERATION_TYPE = 'READ_SIM'  where TYPE = 'IMSI'
/
update LIMITS set OPERATION_TYPE = 'IMPOSSIBLE_PERFORM_OPERATION'  where TYPE = 'OBSTRUCTION' OR GROUP_RISK = 'SOCCARD'
/
update LIMITS SET RESTRICTION_TYPE = 'AMOUNT'
/

--изменение
alter table LIMITS modify (
    TYPE varchar2(12) not null,
    OPERATION_TYPE not null,
    RESTRICTION_TYPE not null
)
/


--удаление старых записей
alter table LIMITS drop column GROUP_RISK
/
alter table SERVICE_PROVIDERS drop column GENERAL_GROUP_RISK
/
alter table SERVICE_PROVIDERS drop column MOBILE_GROUP_RISK
/

--Комментарий: добавляем всем тербанкам лимит - мобильный кошелек
declare
	new_id_limit INTEGER; --новый идентификатор
begin
	for departmentRow in 
	(
				select ID from DEPARTMENTS where PARENT_DEPARTMENT is null 
				minus
				select ID from DEPARTMENTS where ID in 
				(
					select DEPARTMENT_ID from LIMITS where TYPE = 'USER_POUCH'
				)
	)
	loop
				select S_LIMITS.nextval INTO new_id_limit from dual;
				insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, TYPE, DEPARTMENT_ID) values (new_id_limit, sysdate, sysdate,0,'RUB','AMOUNT','IMPOSSIBLE_PERFORM_OPERATION','USER_POUCH',departmentRow.ID);
	end loop;
end ;
/

update profile set SHOW_PERSONAL_FINANCE = 0 where SHOW_PERSONAL_FINANCE is null
/