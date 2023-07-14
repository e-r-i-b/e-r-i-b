--Номер ревизии: 41457
--версионность платежных форм
alter table PAYMENTFORMS drop ( 
	HTML_TRANSFORMATION,
	PRINT_TRANSFORMATION,
	MOBILE_TRANSFORMATION,
	ATM_TRANSFORMATION
)
/

--ЕРМБ:
alter table LOAN_LINKS add (
   ERMB_NOTIFICATION    char(1),
   ERMB_SMS_ALIAS       varchar2(20)
)
create sequence S_PARALLEL_JOB_NUM
    increment by 1
    start with 1
    nocache
/

--Запускаем батник: LOAN_LINKS\start.bat

drop sequence S_PARALLEL_JOB_NUM
/

alter table LOAN_LINKS modify (ERMB_NOTIFICATION default 1 not null)
/

alter table CARD_LINKS add (
	ERMB_NOTIFICATION    char(1),
	ERMB_SMS_ALIAS       varchar2(20)
)
/
create sequence S_PARALLEL_JOB_NUM
    increment by 1
    start with 1
    nocache
/

--Запускаем батник: CARD_LINKS\start.bat

drop sequence S_PARALLEL_JOB_NUM
/

alter table CARD_LINKS modify  (ERMB_NOTIFICATION default 1 not null)
/

alter table ACCOUNT_LINKS add (
   ERMB_NOTIFICATION    char(1),
   ERMB_SMS_ALIAS       varchar2(20)
)
/
create sequence S_PARALLEL_JOB_NUM
    increment by 1
    start with 1
    nocache
/

--Запускаем батник: ACCOUNT_LINKS\start.bat

drop sequence S_PARALLEL_JOB_NUM
/

alter table ACCOUNT_LINKS modify (ERMB_NOTIFICATION default 1 not null)
/ 

--USERS
alter table USERS add ( TRUSTED char(1) null )
/
create sequence S_PARALLEL_JOB_NUM
    increment by 1
    start with 1
    nocache
/

--Запускаем батник: USERS\start.bat

drop sequence S_PARALLEL_JOB_NUM
/
alter table USERS add ( TRUSTED char(1) default '1' not null )
/

-- Номер ревизии: 45954
-- Срок действия карты при открытии ОМС и Подтверждение операций чековым паролем.
alter table CARD_LINKS  add (
	GFL_TB         varchar2(4),
	GFL_OSB        varchar2(4),
	GFL_VSP        varchar2(7),
	MB_USER_ID     varchar2(30)
)
/

--Номер ревизии: 44104
--Изменить текст смс при изменении моб. кошелька. 
alter table PROFILE add (
	MOBILE_WALLET_AMOUNT     number(19,4),
    MOBILE_WALLET_CURRENCY   char(3)
)
/

--Номер ревизии: 45234
--Управление механизмом виджетов в профиле клиента
--TODO параллель
alter table PROFILE add SHOW_WIDGET char(1) null 
/
create sequence S_PARALLEL_JOB_NUM
    increment by 1
    start with 1
    nocache
/

--Запускаем батник: PROFILE\start.bat

drop sequence S_PARALLEL_JOB_NUM
/

alter table PROFILE modify SHOW_WIDGET char(1) default '1' not null
/

--Номер ревизии: 44276
--Привязка рабочих календарей к подразделениям банка
alter table CALENDARS add DEPARTMENT_ID integer
/

create index "DXFK_CALENDARS_TO_DEPARTMENTS" on CALENDARS
(
   DEPARTMENT_ID
)
/

alter table CALENDARS
   add constraint FK_CALENDARS_TO_DEPARTMENTS foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
      on delete set null
/

--Номер ревизии: 44737
--проблема при загрузке курсов валют
--Скрипт переворачивает обратно инвертированные курсы Рубль (643) -> XXX
update RATE R
set R.FROM_CURRENCY = R.TO_CURRENCY,
    R.FROM_VALUE = R.TO_VALUE,
    R.RATE_TYPE = 4
where R.FROM_CURRENCY='643'
   and R.RATE_TYPE = 3
   and exists (
       -- для перевёрнутого курса д.б. брат по RATE_TYPE 
       select 1 from RATE RR
       where RR.FROM_CURRENCY = R.TO_CURRENCY
          and RR.TO_CURRENCY = R.FROM_CURRENCY
          and RR.RATE_TYPE = R.RATE_TYPE
          and RR.EFF_DATE = R.EFF_DATE
          and NVL(RR.ORDER_NUMBER, '0') = NVL(R.ORDER_NUMBER, '0')
          and RR.DEPARTMENT_ID = R.DEPARTMENT_ID
          and RR.ID <> R.ID
)
/

update RATE R
set R.FROM_CURRENCY = R.TO_CURRENCY,
    R.FROM_VALUE = R.TO_VALUE,
    R.RATE_TYPE = 0
where R.FROM_CURRENCY='643'
   and R.RATE_TYPE = 0
   and exists (
       -- для перевёрнутого курса д.б. брат по RATE_TYPE 
       select 1 from RATE RR
       where RR.FROM_CURRENCY = R.TO_CURRENCY
          and RR.TO_CURRENCY = R.FROM_CURRENCY
          and RR.RATE_TYPE = R.RATE_TYPE
          and RR.EFF_DATE = R.EFF_DATE
          and NVL(RR.ORDER_NUMBER, '0') = NVL(R.ORDER_NUMBER, '0')
          and RR.DEPARTMENT_ID = R.DEPARTMENT_ID
          and RR.ID <> R.ID
)
/

--Номер ревизии: 44981
--Реализовать поддержку отдельной БД для PhizICLog
alter table USERLOG add USER_ID varchar2(50)
/
alter table SYSTEMLOG add USER_ID varchar2(50)
/
--В ревизии 46065 (1.18)
alter table CODLOG add (DOCUMENT_ID number)
/


--Номер ревизии: 45153
--Обеспечение возможности оплаты услуг при недоступности IQWave / шины (модель БД)
alter table SERVICE_PROVIDERS add (IS_OFFLINE_AVAILABLE char(1) default '0' not null)
/

--Номер ревизии: 45159
--Доработака формы "Редактирование узла"
alter table NODES add (type varchar2(20) null)
/
update NODES set type = 'COD'
/
alter table NODES modify type varchar2(20) not null
/
-- Номер ревизии:  45678
-- Редактирование настроек адаптерова София-ВМС 
alter table NODES add (PREFIX varchar2(128) null)
/

--Номер ревизии: 45365
--Комментарий: доработка обмена с АС Автоплатежи

alter table BUSINESS_DOCUMENTS add (CONFIRMED_EMPLOYEE_LOGIN_ID integer)
/
alter table BUSINESS_DOCUMENTS rename column EMPLOYEE_LOGIN_ID to CREATED_EMPLOYEE_LOGIN_ID
/
drop index DXFK_EMP_DEPARTMENTS_TO_PAYMEN
/
alter table BUSINESS_DOCUMENTS rename column EMPLOYEE_DEPARTMENT_ID to EMP_D_ID_1010101
/
alter table BUSINESS_DOCUMENTS rename column EMPLOYEE_FIO to EMP_FIO_1010101
/
alter table BUSINESS_DOCUMENTS SET UNUSED column EMP_D_ID_1010101
/
alter table BUSINESS_DOCUMENTS SET UNUSED column EMP_FIO_1010101
/
--Если время позволяет, то удаляем инвалидные поля для освобождения места
--alter table BUSINESS_DOCUMENTS drop unused columns checkpoint 10000

-- Номер ревизии: 45935 
-- ограничение входа клиента
alter table BUSINESS_DOCUMENTS add (LOGIN_TYPE varchar2(10));
/

--Номер ревизии: 45374
--Добавление кода для выгрузки файла в СПООБК. 
alter table CREDIT_CARD_PRODUCTS add(CARD_TYPE_CODE number)
/

--Номер ревизии: 45426
--Подтверждение введенных лимитов (ЗНИ 152412)
alter table LIMITS add STATE varchar2(10)  default 'CONFIRMED' not null
/

--Номер ревизии: 45431
--Комментарий: Редактирование бизнес настроек
alter table BUSINESS_PROPERTIES add (value varchar2(256) null)
/
alter table BUSINESS_PROPERTIES add constraint UNIQUE_KEY unique (KEY,KIND)
/

-- Номер ревизии:  45574
-- Вынести параметры из конфигурационных файлов в АРМ.  Уникальный индекс к полям PROPERTY_KEY и CATEGORY таблицы PROPERTIES
create unique index PROPERTIES_UNIQ on PROPERTIES (
   PROPERTY_KEY ASC,
   CATEGORY ASC
)
/

-- Номер ревизии: 45642
-- Изменён механизм выгрузки файла "Описание расчетов" для ПФП.
drop table FILES
/
drop sequence S_FILES
/

-- Номер ревизии: 45654
-- Возможность отключения режима оффлайновости 
-- Автоматическое определение недоступности внешних систем
alter table TECHNOBREAKS add (
	IS_ALLOWED_OFFLINE_PAY char(1) default '0' not null,
	IS_AUTO_ENABLED        char(1) default '0' not null
)
/

-- Номер ревизии: 45706
-- Увеличен размер столбца, в соответствии с размером поля поставщика
alter table FIELD_DESCRIPTIONS modify (INITIAL_VALUE nvarchar2 (1024))
/

-- Номер ревизии: 45805
-- Интеграция с АС "Филиал СБ". Реализация queryProfile. Добавление создания пользователя.
--TODO индексы по полям(проверить инвалидацию)
drop index FIO_PERSON_INDEX
/
drop index USERS_TRIMMED_FIO
/
drop index USERS_BIRTHDAY_TRIMMED_FIO
/
drop index AGREEMENT_NUMBER_INDEX
/

alter table USERS modify (FIRST_NAME varchar2(120))
/
alter table USERS modify (SUR_NAME varchar2(120))
/
alter table USERS modify (PATR_NAME varchar2(120))
/

create index FIO_PERSON_INDEX on USERS
(upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME),' ',''),'-','')))
  tablespace INDX parallel 64
/

create index USERS_TRIMMED_FIO on USERS (UPPER(TRIM(SUR_NAME)||TRIM(FIRST_NAME)||TRIM(PATR_NAME)), BIRTHDAY)
  tablespace INDX parallel 64
/

create index USERS_BIRTHDAY_TRIMMED_FIO on USERS (BIRTHDAY, UPPER(REPLACE(SUR_NAME,' ','')||REPLACE(FIRST_NAME,' ','')||REPLACE(PATR_NAME,' ','')))
  tablespace INDX parallel 64
/


create index AGREEMENT_NUMBER_INDEX on USERS (UPPER(SUR_NAME), UPPER(FIRST_NAME), NVL(UPPER(AGREEMENT_NUMBER),'x'))
  tablespace INDX parallel 64
/ 

alter index FIO_PERSON_INDEX noparallel
/
alter index USERS_TRIMMED_FIO noparallel
/
alter index USERS_BIRTHDAY_TRIMMED_FIO noparallel
/
alter index AGREEMENT_NUMBER_INDEX noparallel 
/
--Номер ревизии: 45346
alter table USERS modify MANAGER_ID varchar2(20)
/
alter table DOCUMENTS modify ( "DOC_ISSUE_BY" varchar2(255) )
/

-- Номер ревизии: 46013
-- Неверное время окончания ПФП 
alter table PERSONAL_FINANCE_PROFILE add (START_PLANING_DATE TIMESTAMP )
/
update PERSONAL_FINANCE_PROFILE set START_PLANING_DATE = EXECUTION_DATE
/

--В ревизии 45106 
alter table OPERATION_CONFIRM_LOG add IMSI_CHECK char(1) null
/

--Номер ревизии: 45431
--Комментарий: Редактирование бизнес настроек
	
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'limitInfoPersonPayment','C','10');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'bankLoanLink','C','http://sberbank.ru/ru/person/credits/');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'bankCardLink','C','http://sberbank.ru/moscow/ru/person/bank_cards/special/');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'changeSimSmsMessage','C','В целях безопасности Сбербанк приостановил отправку SMS-паролей в связи с заменой SIM-карты до момента Вашего обращения в Контактный Центр Сбербанка по телефону 8-800-555-5550');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'accountsKindsForbiddenClosing','C','81');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'newsCount','C','3');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'clearOrderDays','C','15');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'orderNotificationCount','C','20');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'clearNotConfirmDocumentsPeriod','C','30');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'clearWaitConfirmDocumentsPeriod','C','30');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'RUB','D','3000');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'RUR','D','3000');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'EUR','D','100');
insert into BUSINESS_PROPERTIES(ID, KEY, KIND, VALUE) values(S_BUSINESS_PROPERTIES.nextval,'USD','D','100');

--	
update DEPARTMENTS set IS_CREDIT_CARD_OFFICE = '0'
	where IS_CREDIT_CARD_OFFICE = '1' and OFFICE is null or OFFICE = ''
/

--Изменение работы с SMS паролями
alter table SMSPASSWORDS rename to SMSPASSWORDS_03_2013
/
alter table SMSPASSWORDS_03_2013 rename constraint PK_SMSPASSWORDS to ILDI_SMS_C1
/
alter table SMSPASSWORDS_03_2013 rename constraint FK_SMSPSW_TO_LOGINS to ILDI_SMS_C2
/
alter index PK_SMSPASSWORDS rename to ODLI_SMS_I1 
/
alter index DXFK_SMSPSW_TO_LOGINS rename to ODLI_SMS_I2
/
alter index SMSPASSWORDS_I_ISSUE_DATE rename to ODLI_SMS_I3
/

create table SMSPASSWORDS  (
   ID                   integer                         not null,
   LOGIN_ID             integer,
   ISSUE_DATE           timestamp                       not null,
   EXPIRE_DATE          timestamp                       not null,
   STATE                char(1)                         not null,
   WRONG_ATTEMPTS       integer                         not null,
   HASH                 clob,
   ENTITY_TYPE          varchar2(128)                   not null,
   ENTITY_ID            integer                         not null,
   SESSION_ID           varchar2(64),
   constraint PK_SMSPASSWORDS primary key (ID)
)
/

create index SMSPASSWORDS_I_ISSUE_DATE on SMSPASSWORDS (
   ISSUE_DATE ASC,
   STATE ASC
)
/

create index SMSPASSWORDS_I_SESSION_ID on SMSPASSWORDS (
   SESSION_ID ASC
)
/

--unique index
create unique index NODE_ADAPTERS_ADAPTER_ID_INDX on NODE_ADAPTERS (
   ADAPTER_ID ASC
)

--ресурсы
update PAYMENT_SERVICES set image_name = regexp_replace(image_name, '\.png$','.jpg') where image_name like '%.png'
/

alter table SETTING_LOAN modify(DIRECTORY_AUTO null)
/

alter table SETTING_LOAN modify(FILE_NAME_AUTO  null)
/

	
--Постконвертация:
--Можно стартовать приложения, выполнять конвертацию фоном
create sequence S_PARALLEL_JOB_NUM
    increment by 1
    start with 1
    nocache
/

--Запускаем батник: OPERATION_CONFIRM_LOG\start.bat

drop sequence S_PARALLEL_JOB_NUM
/

alter table OPERATION_CONFIRM_LOG modify IMSI_CHECK char(1) default 0 not null
/

	