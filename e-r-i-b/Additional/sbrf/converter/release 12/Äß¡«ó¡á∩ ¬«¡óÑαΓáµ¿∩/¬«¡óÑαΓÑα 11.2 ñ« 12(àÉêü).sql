--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

-- Номер ревизии: 50504
-- Комментарий: ЕРМБ. Подключение/настройка услуги дла клиента ч.1
alter table  ERMB_PROFILE add (
	LOCK_DESCRIPTION varchar2(256),
	LOCK_TIME timestamp
)
/
-- Номер ревизии: 50827
-- Комментарий:   BUG055422: АС Филиал - в профиле старый идентификатор карты 
create index "DXFK_FOREG_PRODUCT" on ERMB_PROFILE
(
   FOREG_PRODUCT_ID
) tablespace INDX
/

alter table ERMB_PROFILE
   add constraint FK_FOREG_PRODUCT foreign key (FOREG_PRODUCT_ID)
      references CARD_LINKS (ID)
      on delete set null
/

-- Номер ревизии: 50757
-- Комментарий: ЕРМБ. Подключение/настройка услуги дла клиента ч.2
alter table ERMB_TMP_PHONE drop column MOBILE_PHONE_OPERATOR
/
alter table ERMB_CLIENT_PHONE drop column MOBILE_PHONE_OPERATOR
/

-- Номер ревизии: 52031
alter table ERMB_PROFILE modify TIME_START default('00:00:00') not null
/
alter table ERMB_PROFILE modify TIME_END default('23:59:59') not null
/
alter table ERMB_PROFILE modify DAYS_OF_WEEK default('SAT,FRI,WED,TUE,MON,SUN,THU') not null
/

-- Номер ревизии: 50799 TODO!
-- Комментарий:   ENH047420: Проассоциировать иконки форм с самими формами
update PAYMENT_SERVICES set IMAGE_NAME = replace(IMAGE_NAME,'.png','.jpg')
/
update PAYMENT_SERVICES_OLD set IMAGE_NAME = replace(IMAGE_NAME,'.png','.jpg')
/

-- Номер ревизии: 51870
-- Комментарий: Доработка репликации подразделений : Подготовка к реализации основного алгоритма (изменение формы редактирования подразделения, базы)
alter table OPERATIONDESCRIPTORS modify OPERATIONKEY varchar2(50)
/
alter table DEPARTMENTS add AUTOMATION_TYPE varchar2(15)
/

-- Номер ревизии: 52011
-- Комментарий: ЕРМБ: перенести смс-текстовки в БД
alter table SMS_RESOURCES modify TEXT null
/

-- Номер ревизии: 52198
-- Комментарий: BUG058827: Ошибка в маппинге системных ошибок 
delete from EXCEPTION_ENTRY where KIND = 'I' and APPLICATION not in ('PhizIA', 'PhizIC', 'mobile', 'mobile2', 'mobile3', 'mobile4', 'mobile5', 'mobile6', 'atm', 'Gate', 'Scheduler')
/

-- Номер ревизии: 53014
-- Комментарий: Расширение состава данных, передаваемых в запросах, в комплексном типе данных "Информация о карте"(модель БД)
alter table STORED_CARD add (
	CARD_BONUS_SIGN   varchar2(5),
	CARD_LEVEL        varchar2(5)
)
/

-- Номер ревизии: 53055
-- Комментарий: Доработка репликации подразделений(Создание джоба для выполнения фоновых задач)
delete from QRTZ_SIMPLE_TRIGGERS where TRIGGER_NAME = 'PerformBackgroundTasksTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from QRTZ_TRIGGERS where TRIGGER_NAME = 'PerformBackgroundTasksTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from QRTZ_JOB_DETAILS where JOB_NAME = 'PerformBackgroundTasksJob' and JOB_GROUP = 'DEFAULT'
/

-- Номер ревизии: 53222
-- Комментарий: Доработка справочника вкладов.
alter table DEPOSITDESCRIPTIONS add (
	CAPITALIZATION char(1) default 0 not null,
	MINIMUM_BALANCE char(1) default 0 not null
)
/

-- Номер ревизии: 53232
-- Комментарий: Актуализация модели БД
alter table SMS_RESOURCES add ( PRIORITY integer null ) 
/

-- Номер ревизии: 53385
-- Комментарий: mAPI 7.0. "Избранное" в мобильном приложении
alter table PROFILE add (STASH varchar2(500))
/

-- Номер ревизии: 53459
-- Комментарий: Доработка АРМ сотрудника при работе со справочником вкладов: просмотр и редактирование информации по вкладу
alter table DEPOSITGLOBALS add (VISIBILITY_TRANSFORMATION clob)
/

-- Номер ревизии: 53735
-- Комментарий: Добавление приоритета в таблицу MESSAGE_TEMPLATES
alter table MESSAGE_TEMPLATES add ( PRIORITY integer null ) 
/

-- Номер ревизии: 54044 
-- Комментарий: Печать реквизитов банка для вкладов (модель БД)
alter table RUSBANKS add (
   PARTICIPANT_CODE  varchar2(8),
   INN               varchar2(12),
   KPP               varchar2(12)
)
/

-- Номер ревизии: 54338
delete from PAYMENT_FORM_TRANSFORMATIONS where type in ('mobile2', 'mobile3', 'mobile4')

-- Номер ревизии: 54356
-- Комментарий: BUG061025: Доработать отображение таблицы Мои кредиты
alter table PFP_PERSON_LOAN add IMAGE_ID integer null
/

-- Номер ревизии: 54363
-- Комментарий: CSAAdmin. Работа со схемами прав
alter table ACCESSSCHEMES add EXTERNAL_ID integer
/

-- Номер ревизии: 54378
-- Комментарий: Доработка интерфейсов по продуктам (сортировка). Доработка сущности линков.

alter table ACCOUNT_LINKS add (POSITION_NUMBER integer)
/
alter table CARD_LINKS    add (POSITION_NUMBER integer)
/
alter table DEPO_ACCOUNT_LINKS add (POSITION_NUMBER integer)
/
alter table IMACCOUNT_LINKS    add (POSITION_NUMBER integer)
/
alter table LOAN_LINKS    add (POSITION_NUMBER integer)
/

-- Номер ревизии: 54486
-- Комментарий: BUG062286: Значения ВСП и ОСБ в запросе на открытие вклада в ВСП КМ
alter table USERS add (
    MANAGER_TB varchar2(8),
    MANAGER_OSB varchar2(8),
    MANAGER_VSP varchar2(8)
)

alter table PERSONAL_SUBSCRIPTION_DATA add ( MAIL_FORMAT varchar2(16) null ) 
/

-- Номер ревизии: 54525
-- Комментарий: ENH061998: Переделать логику получения бонусных баллов
-- Номер ревизии: 55501
-- Комментарий: ENH058779: Переместить блок "Спасибо" в общий список продуктов
alter table LOYALTY_PROGRAM_LINKS add (
	BALANCE      number(19,2),
	UPDATE_TIME  timestamp,
	SHOW_IN_MAIN char(1) default '1' not null
)
/

-- Номер ревизии: 54670
-- Комментарий: Реализовать механизм периодической очистки старых операций в АЛФ
create index IDX_OPERATION_DATE on CARD_OPERATIONS (
   OPERATION_DATE ASC
) tablespace INDX
/

-- Номер ревизии: 54584
-- Комментарий: Реализовать справочник категорий операций в АРМ сотрудника
alter table CARD_OPERATION_CATEGORIES add ( 
	VISIBLE                  char(1) default '1' not null,
	FOR_INTERNAL_OPERATIONS  char(1) default '0' not null,
	EXTERNALIDT              varchar2(100) 
)
/
update CARD_OPERATION_CATEGORIES set EXTERNALIDT = EXTERNALID 
/
alter table CARD_OPERATION_CATEGORIES drop column EXTERNALID
/
alter table CARD_OPERATION_CATEGORIES rename column EXTERNALIDT to EXTERNALID 
/ 

-- Номер ревизии: 54701
-- Номер версии: 1.18
-- Комментарий: Новые поля в справочнике СПООБК

alter table DEPARTMENTS_RECORDING_TMP add (
	DATE_SUC  timestamp,
	DESPATCH  varchar2(11)
)
/

alter table DEPARTMENTS_RECORDING add (
	DATE_SUC  timestamp,
	DESPATCH  varchar2(11)
)
/

-- Номер ревизии: 54715
-- Комментарий: АЛФ. Админ. Создание и редактирование категорий карточных операций
alter table MERCHANT_CATEGORY_CODES add (
	INCOME_OPERATION_CATEGORY_IDT   varchar2(100),
	OUTCOME_OPERATION_CATEGORY_IDT  varchar2(100)
)	
/
update MERCHANT_CATEGORY_CODES set INCOME_OPERATION_CATEGORY_IDT = INCOME_OPERATION_CATEGORY_ID 
/
update MERCHANT_CATEGORY_CODES set OUTCOME_OPERATION_CATEGORY_IDT = OUTCOME_OPERATION_CATEGORY_ID 
/

alter table MERCHANT_CATEGORY_CODES drop (
	INCOME_OPERATION_CATEGORY_ID,
	OUTCOME_OPERATION_CATEGORY_ID
)
/

alter table MERCHANT_CATEGORY_CODES rename column INCOME_OPERATION_CATEGORY_IDT to INCOME_OPERATION_CATEGORY_ID
/
alter table MERCHANT_CATEGORY_CODES rename column OUTCOME_OPERATION_CATEGORY_IDT to OUTCOME_OPERATION_CATEGORY_ID
/


-- Номер ревизии: 54753
update MERCHANT_CATEGORY_CODES set INCOME_OPERATION_CATEGORY_ID=null where INCOME_OPERATION_CATEGORY_ID in (select EXTERNALID from CARD_OPERATION_CATEGORIES where IS_DEFAULT=1 and INCOME=1)
/
update MERCHANT_CATEGORY_CODES set OUTCOME_OPERATION_CATEGORY_ID=null where OUTCOME_OPERATION_CATEGORY_ID in (select EXTERNALID from CARD_OPERATION_CATEGORIES where IS_DEFAULT=1 and INCOME=0)
/
delete MERCHANT_CATEGORY_CODES where INCOME_OPERATION_CATEGORY_ID is null and OUTCOME_OPERATION_CATEGORY_ID is null
/

-- Номер ревизии: 54747
-- Комментарий: Изменение джоба уточнения статуса документов.
alter table PAYMENT_EXECUTION_RECORDS add (NEXT_PROCESS_DATE timestamp)
/

-- Номер ревизии: 54788
-- Комментарий: Реализовать ограничение на количество попыток выполнить одну заявку.
alter table CARD_OPERATION_CLAIMS add EXECUTION_ATTEMPT_NUM number default '0' not null
/

-- Номер ревизии: 54809
-- Комментарий: Реализовать создание адаптера для ИПС
alter table ADAPTERS add ADAPTER_TYPE varchar2(8)
/
update ADAPTERS set ADAPTER_TYPE='NONE'
/
update ADAPTERS set ADAPTER_TYPE='ESB' where IS_ESB ='1'
/
alter table ADAPTERS modify ADAPTER_TYPE not null
/
alter table ADAPTERS drop column IS_ESB
/

-- Номер ревизии: 54856
-- Комментарий: Доработка настройки оповещений клиентом
-- Комментарий: Сбор статистики по использованию АЛФ.Отчет «Подключение АЛФ».
alter table PROFILE add ( 
	START_USING_PERSONAL_FINANCE   date
) 
/

create or replace view DATES_ADD_PERSONAL_FINANCE as 
select 
    START_USING_PERSONAL_FINANCE, TB, count(*) cnt 
from PROFILE profile 
    join USERS users on profile.LOGIN_ID = users.LOGIN_ID 
    join DEPARTMENTS departments on departments.ID = users.DEPARTMENT_ID
where SHOW_PERSONAL_FINANCE = '1'
group by START_USING_PERSONAL_FINANCE, TB
/


-- Номер ревизии: 54894
-- Комментарий: Доработка механизма рассылки оповещений
insert into DISTRIBUTION_CHANNEL(ID,DISTRIBUTION_ID,CHANNEL,LIST_INDEX) select id, DISTRIBUTION_ID, CHANNEL,LIST_INDEX from MESSAGE_TEMPLATES
/

alter table MESSAGE_TEMPLATES drop constraint TEMPLATE_TO_DISTRIBUTION
/

alter table SUBSCRIPTION_TEMPLATES drop constraint FK_SUBTEM_TO_TEMPLATE
/

drop sequence S_MESSAGE_TEMPLATES
/

drop index CHANNEL_UK
/

drop table MESSAGE_TEMPLATES
/

alter table DISTRIBUTION_CHANNEL
   add constraint TEMPLATE_TO_DISTRIBUTION foreign key (DISTRIBUTION_ID)
      references DISTRIBUTIONS (ID)
/

alter table SUBSCRIPTION_TEMPLATES
   add constraint FK_SUBTEM_TO_TEMPLATE foreign key (TEMPLATE_ID)
      references DISTRIBUTION_CHANNEL (ID)
/


-- Номер ревизии: 54967 TODO default 0 ! 
-- Комментарий: Доработать ограничения на перебор логинов (ЗНИ 189437)
alter table USERS add (
	CHECK_LOGIN_COUNT         integer,
	LAST_FAILURE_LOGIN_CHECK  date
)
/

-- Номер ревизии: 54985
-- Комментарий: Реализация журнала входов в ЦСА
alter table LOGINS add (CSA_USER_ALIAS varchar2(32))
/

-- Номер ревизии: 55033
-- Комментарий: CHG058580: Несоответствие данных в Расходах. 
alter table CARD_OPERATIONS add LOAD_DATE timestamp default sysdate not null 
/

create index "IDX_CARDOP_LOAD_DATE" on CARD_OPERATIONS
(
    LOAD_DATE
) tablespace INDX
/

-- Номер ревизии: 55084
-- Комментарий: Отчет «Популярные цели»
alter table ACCOUNT_TARGETS add CREATION_DATE timestamp default sysdate not null 
/

create index "IDX_ACCOUNT_TARGETS_DATE" on ACCOUNT_TARGETS
(
    CREATION_DATE DESC
) tablespace INDX
/

create or replace view DATES_ADD_ACCOUNT_TARGETS as 
select 
    trunc(targets.CREATION_DATE) target_date, departments.TB tb, targets.LOGIN_ID client, targets.NAME target_name 
from ACCOUNT_TARGETS targets 
    join USERS users on targets.LOGIN_ID = users.LOGIN_ID 
    join DEPARTMENTS departments on departments.ID = users.DEPARTMENT_ID
/

-- Номер ревизии: 55147
-- Комментарий: CHG063276: Изменение алгоритма определения БИКа и корр. счета для печати реквизитов счета.
alter table TB_DETAILS add OFF_CODE varchar2(4)
/

-- Номер ревизии: 55280
-- Комментарий: BUG062142: Некорректная работа RemoveOldNotConfirmedDocumentsJob
delete FROM QRTZ_CRON_TRIGGERS  WHERE TRIGGER_NAME = 'RemoveOldNotConfirmedDocumentsTrigger'
/
delete  FROM QRTZ_TRIGGERS      WHERE TRIGGER_NAME = 'RemoveOldNotConfirmedDocumentsTrigger'
/
delete  FROM QRTZ_JOB_DETAILS  WHERE JOB_NAME ='RemoveOldNotConfirmedDocumentsJob'
/

-- Номер ревизии: 55317
-- Комментарий: Задача "Лимит на получателя ЕРМБ"
alter table USERS_LIMITS_JOURNAL  add (EXTERNAL_CARD varchar2(18))
/
alter table USERS_LIMITS_JOURNAL  add (EXTERNAL_PHONE varchar2(16))
/

-- Номер ревизии: 55352
-- Комментарий: CHG063922: Удалить таблицу MB_SMS_ID.
rename S_MB_SMS_ID to SC_MB_SMS_ID
/
drop table MB_SMS_ID
/

-- Номер ревизии: 55572
-- Комментарий:   CHG064088: Поместить ссылку на бонусную программу вниз Избранного
alter table FAVOURITE_LINKS add ONCLICK_FUNCTION varchar2(300)
/
update FAVOURITE_LINKS set ONCLICK_FUNCTION='openBusinessmanRegistrationWindow(event);'  where NAME='Стань предпринимателем'
/

-- Номер ревизии: актуально для любой ревизии
-- Комментарий: BUG061035: Исключение при добавлении кредитного продукта
alter table PFP_PERSON_LOAN modify NAME varchar2(256)
/

-- Номер ревизии: 55206
alter table ADAPTERS add (LISTENER_URL varchar2(128))
/

/* Не делаем!
-- Номер ревизии: 55364
-- Комментарий: BUG061589: Исключение в Маппинге системных ошибок 
delete from EXCEPTION_COUNTERS where EXCEPTION_HASH in (select HASH from EXCEPTION_ENTRY where APPLICATION  = 'Gate')
/
delete from EXCEPTION_ENTRY where APPLICATION ='Gate'
/
-- Номер ревизии: 55507
-- Комментарий: BUG061367: Ошибка в маппинге внешних ошибок. 
delete from EXCEPTION_COUNTERS where EXCEPTION_HASH in (select HASH from EXCEPTION_ENTRY where KIND='E' and SYSTEM is null)
/
delete from EXCEPTION_ENTRY where KIND='E' and SYSTEM is null
/
*/
-- Номер ревизии: 55808
-- Комментарий: BUG057697: Некорректный фильтр справочника маппинга внешних ошибок. 
truncate table EXCEPTION_COUNTERS
/
truncate table EXCEPTION_ENTRY
/

-- Номер ревизии: 55693
-- Комментарий: BUG057697: Некорректный фильтр справочника маппинга внешних ошибок.
alter table EXCEPTION_ENTRY modify SYSTEM varchar2(64)
/
alter table EXCEPTION_ENTRY modify HASH varchar2(256)
/
alter table EXCEPTION_COUNTERS modify EXCEPTION_HASH varchar2(256)
/

-- Номер ревизии: 55812  
alter table PFP_CARDS modify (NAME varchar2(80))
/
alter table PFP_CARDS modify (DESCRIPTION varchar2(170))
/

-- Номер ревизии: 54421
-- Комментарий: ENH062018: Настройка отображения окна саморегистрации
alter table PROFILE add REG_WINDOW_SHOW_COUNT integer
/




----Тяжелые скрипты:

-- Номер ревизии: 51604
-- Комментарий: ENH057379 Доработки по шаблонам документов (Модель БД)
alter session enable parallel DML
/
alter table BUSINESS_DOCUMENTS modify (ADDITION_CONFIRM varchar2(2))
/
update /*+parallel(bd, 64)*/ BUSINESS_DOCUMENTS bd 
	set ADDITION_CONFIRM=decode(ADDITION_CONFIRM, 'CC', '1', 'SD', '4') 
		where ADDITION_CONFIRM is not null
/

-- Номер ревизии: 50865
-- Комментарий: установка типа формы платежа "IMA_PAYMENT" шаблонам по "покупка\продажа металлов"
update /*+parallel(PAYMENTS_DOCUMENTS, 64)*/ PAYMENTS_DOCUMENTS set FORM_TYPE = 'IMA_PAYMENT' where FORM_TYPE = 'INTERNAL_TRANSFER' and (DESTINATION_CURRENCY = 'A99' or DESTINATION_CURRENCY = 'A98' or DESTINATION_CURRENCY = 'A76' or DESTINATION_CURRENCY = 'A33' or CHARGEOFF_CURRENCY = 'A99' or CHARGEOFF_CURRENCY = 'A98' or CHARGEOFF_CURRENCY = 'A76' or CHARGEOFF_CURRENCY = 'A33')
/

-- Номер ревизии: 53821 
-- Комментарий: АС ФСБ: доработка согласно спецификации 1.07 (ч.1)
alter table CARD_LINKS    add (SHOW_IN_SMS char(1) null)
/
alter table ACCOUNT_LINKS add (SHOW_IN_SMS char(1) null)
/
alter table LOAN_LINKS    add (SHOW_IN_SMS char(1) null)
/

update /*+parallel(CARD_LINKS, 64)*/ CARD_LINKS    set SHOW_IN_SMS = SHOW_IN_SYSTEM
/
update /*+parallel(ACCOUNT_LINKS, 64) */ ACCOUNT_LINKS set SHOW_IN_SMS = SHOW_IN_SYSTEM
/
update /*+parallel(LOAN_LINKS, 64)*/ LOAN_LINKS    set SHOW_IN_SMS = SHOW_IN_SYSTEM
/

alter table CARD_LINKS    modify (SHOW_IN_SMS default 1 not null)
/
alter table ACCOUNT_LINKS modify (SHOW_IN_SMS default 1 not null)
/
alter table LOAN_LINKS    modify (SHOW_IN_SMS default 1 not null)
/
-- Номер ревизии: 54736
-- Комментарий: ENH060014: Реализовать страницу со списком "избранного"
alter table FAVOURITE_LINKS add TYPE_LINK char(1) default 'U' not null
/

-- Номер ревизии: 54873
-- Комментарий: Сбор статистики по использованию АЛФ.Отчет «Подключение АЛФ».
update /*+parallel(PROFILE, 64)*/ PROFILE set START_USING_PERSONAL_FINANCE = trunc (SYSDATE) where SHOW_PERSONAL_FINANCE = '1'
/

-- Номер ревизии: 54515
-- Комментарий: Удаление контактных данных из класса Person,
-- замена их использования на использования аналогичных полей из PersonSubscriptionData,
-- добавление в PersonSubscriptionData поля mailFormat.
alter table USERS set unused ( 
	E_MAIL, 
	MOBILE_PHONE, 
	SMS_FORMAT 
)
/

-- Номер ревизии: 55052
-- Комментарий: CHG062715: Реализовать механизм управления контентом промо-блока Автоплатежей, отображаемого в клиентском приложении ЕРИБ
alter table SERVICE_PROVIDER_REGIONS  add SHOW_IN_PROMO_BLOCK  CHAR(1) default '0' not null
/

/*==============================================================*/
/* Index: USR_LIMITS_CARD_IND                                   */
/*==============================================================*/
create index USR_LIMITS_CARD_IND on USERS_LIMITS_JOURNAL (
   EXTERNAL_CARD ASC,
   CREATION_DATE ASC
) parallel 32 tablespace INDX
/
alter index USR_LIMITS_CARD_IND noparallel
/

/*==============================================================*/
/* Index: USR_LIMITS_PHONE_IND                                  */
/*==============================================================*/
create index USR_LIMITS_PHONE_IND on USERS_LIMITS_JOURNAL (
   EXTERNAL_PHONE ASC,
   CREATION_DATE ASC
) parallel 32 tablespace INDX
/
alter index USR_LIMITS_PHONE_IND noparallel
/

-- Номер ревизии: 55299
-- Комментарий: CHG058213: Сообщение при фильтрации по неключевым данным ДР и статусу операции(бредовый индекс в БД)
drop index BIRTHDAY_INDEX
/
drop index FIO_PERSON_INDEX
/
drop index USER_BIRTHDAY_TRIMMED_FIO 
/

create index FIO_BD_PERSON_INDEX on USERS (
   upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME),' ',''),'-','')) ASC,
   BIRTHDAY ASC
) parallel 32 tablespace INDX
/
alter index FIO_BD_PERSON_INDEX noparallel
/

-- Номер ревизии: 55440
-- Комментарий: ENH061752: Обработка доступа ко всем подразделениям в схемах прав сотрудников при работе с подразделениями(ч1 - список тербанков)
drop index I_DEPARTMENTS_BANK_INFO
/

create unique index I_DEPARTMENTS_BANK_INFO on DEPARTMENTS (
   TB ASC,
   nvl(OSB, 'NULL') ASC,
   nvl(OFFICE, 'NULL') ASC,
   ID ASC
) parallel 32 tablespace INDX
/

alter index I_DEPARTMENTS_BANK_INFO noparallel
/

-- Номер ревизии: 55793
-- Комментарий: ENH046236: Убрать "девочку" из АРМ-клиента.
alter table PROFILE set unused (SHOW_ASSISTANT)
/

update /*+parallel(profile, 64)*/ profile set IS_BANK_OFFER_VIEWED = 1 where IS_BANK_OFFER_VIEWED is null or IS_BANK_OFFER_VIEWED != 1
/

-- Номер ревизии: 55748
-- Комментарий: BUG064354: Неверно отображаются статусы интернет-заказов после конвертации
insert into BUSINESS_DOCUMENTS_TO_ORDERS (BUSINESS_DOCUMENT_ID, ORDER_UUID) 
select bd.ID, bd.ORDER_UUID 
	from BUSINESS_DOCUMENTS bd
	inner join PAYMENTFORMS frm on bd.FORM_ID = frm.ID and frm.NAME in ('AirlineReservationPayment', 'ExternalProviderPayment')
		where bd.ORDER_UUID is not null 
/

create index BUS_DOC_ORD_UUID_IDX on BUSINESS_DOCUMENTS_TO_ORDERS(
   ORDER_UUID asc 
)
/

create unique index PK_BUSINESS_DOCUMENTS_TO_ORDER on BUSINESS_DOCUMENTS_TO_ORDERS(BUSINESS_DOCUMENT_ID, ORDER_UUID) tablespace INDX  
/

alter table BUSINESS_DOCUMENTS_TO_ORDERS
   add constraint PK_BUSINESS_DOCUMENTS_TO_ORDER primary key(BUSINESS_DOCUMENT_ID, ORDER_UUID) 
	using index PK_BUSINESS_DOCUMENTS_TO_ORDER
/ 

alter table BUSINESS_DOCUMENTS_TO_ORDERS
   add constraint FK_BUSINESS_REFERENCE_BUSINESS foreign key (BUSINESS_DOCUMENT_ID)
      references BUSINESS_DOCUMENTS (ID)
/

alter table BUSINESS_DOCUMENTS set unused (ORDER_UUID)
/
alter table ORDERS set unused (PAYMENT_ID)
/

--простановка времени приема документов:
insert into RECEPTIONTIMES(ID, DEPARTMENT_ID, CALENDAR_ID, TIME_START, TIME_END, USE_PARENT_SETTINGS, PAYMENTTYPE, PAYMENTTYPEDESCRIPTION) 
 select S_RECEPTIONTIMES.nextval, DEPARTMENT_ID, CALENDAR_ID, TIME_START, TIME_END, USE_PARENT_SETTINGS, 'ChangeDepositMinimumBalanceClaim', 'Изменение неснижаемого остатка' from RECEPTIONTIMES 
  where PAYMENTTYPE='AccountOpeningClaim'
/

insert into RECEPTIONTIMES(ID, DEPARTMENT_ID, CALENDAR_ID, TIME_START, TIME_END, USE_PARENT_SETTINGS, PAYMENTTYPE, PAYMENTTYPEDESCRIPTION) 
 select S_RECEPTIONTIMES.nextval, DEPARTMENT_ID, CALENDAR_ID, TIME_START, TIME_END, USE_PARENT_SETTINGS, 'AccountChangeInterestDestinationClaim', 'Дополнительное соглашение к договору о вкладе' from RECEPTIONTIMES 
  where PAYMENTTYPE='AccountOpeningClaim'
/




