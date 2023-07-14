--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = SRB_IKFL2
/

-- Номер ревизии: 62800
-- Комментарий: Доработка идентификатции внутренних ошибок
alter table SRB_IKFL2.EXCEPTION_ENTRY modify HASH varchar2(277)
/
alter table SRB_IKFL2.EXCEPTIONS_LOG modify HASH varchar2(277)
/
alter table SRB_IKFL2.EXCEPTION_COUNTERS modify EXCEPTION_HASH varchar2(277)
/

-- Можно просто удалить данные либо выполнить скрипт ниже
update SRB_IKFL2.EXCEPTIONS_LOG el set (el.HASH) = (select ee.HASH||'|'||ee.APPLICATION from SRB_IKFL2.EXCEPTION_ENTRY ee where ee.HASH = el.HASH)
/
update SRB_IKFL2.EXCEPTION_COUNTERS el set (el.EXCEPTION_HASH) = (select ee.HASH||'|'||ee.APPLICATION from SRB_IKFL2.EXCEPTION_ENTRY ee where ee.HASH = el.EXCEPTION_HASH)
/
update SRB_IKFL2.EXCEPTION_ENTRY set HASH = HASH||'|'||APPLICATION
/

-- Номер ревизии: 63687
-- Комментарий: Доработать доступность поставщиков в новом канале и запросы sql с учетом этих признаков
alter table SRB_IKFL2.SERVICE_PROVIDERS add (
	IS_AUTOPAYMENT_SUPPORTED_S_API  char(1),
	VISIBLE_AUTOPAYMENTS_FOR_S_API  char(1), 
	VISIBLE_PAYMENTS_FOR_S_API      char(1),
	AVAILABLE_PAYMENTS_FOR_S_API    char(1)
)	
/ 

update SRB_IKFL2.SERVICE_PROVIDERS set IS_AUTOPAYMENT_SUPPORTED_S_API = '0', VISIBLE_AUTOPAYMENTS_FOR_S_API = '0', VISIBLE_PAYMENTS_FOR_S_API = '0', AVAILABLE_PAYMENTS_FOR_S_API ='1'
/

alter table SRB_IKFL2.SERVICE_PROVIDERS modify (
	IS_AUTOPAYMENT_SUPPORTED_S_API  default '0' not null,
	VISIBLE_AUTOPAYMENTS_FOR_S_API  default '0' not null,
	VISIBLE_PAYMENTS_FOR_S_API      default '0' not null,
	AVAILABLE_PAYMENTS_FOR_S_API    default '1' not null
)
/

-- Номер ревизии: 65995
-- Комментарий: Иерархическое администрирование. Сущность группы сервисов
alter table SRB_IKFL2.SERVICES add constraint I_SERVICES_KEY unique (SERVICE_KEY)
/

alter table SRB_IKFL2.SERVICES_GROUPS_SERVICES
   add constraint FK_SERVICES_TO_SERVICE foreign key (SERVICE_KEY)
      references SRB_IKFL2.SERVICES (SERVICE_KEY)
      on delete cascade
/

-- Номер ревизии: 66183
-- Комментарий: Доработать справочники платформ
alter table SRB_IKFL2.MOBILE_PLATFORMS modify (
	VERSION    null,
	ERROR_TEXT null
)
/

-- Номер ревизии: 66197
-- Комментарий: Merged revision(s) 66196 from versions/v.1.18_release_14.0_PSI: BUG071135  [ЕРМБ смс-канал] Не срабатывает проверка для кода подтверждения операции 
-- Номер ревизии: 66672
-- Комментарий: CHG073948  ЕРМБ. Нет проверки номера, с которого приходит подтверждение операции.
delete from SRB_IKFL2.CONFIRM_BEANS
/

alter table SRB_IKFL2.CONFIRM_BEANS add (
	OVERDUE_TIME timestamp not null,
	PHONE varchar2(20) not null
)
/
drop index SRB_IKFL2.IDX_CONFIRM_EXPIRE
/
drop index SRB_IKFL2.UNIQ_CONFIRM_LOGIN_CODE
/
create index SRB_IKFL2.IDX_CONFIRM_OVERDUE on SRB_IKFL2.CONFIRM_BEANS (
	OVERDUE_TIME asc
) tablespace USER_DATA_IDX
/
create unique index SRB_IKFL2.UI_LOGIN_CONFIRM_CODE_PRIMARY on SRB_IKFL2.CONFIRM_BEANS (
	LOGIN_ID ASC, 
	PRIMARY_CONFIRM_CODE ASC
) tablespace USER_DATA_IDX
/

create unique index SRB_IKFL2.UI_LOGIN_CONFIRM_CODE_SEC on SRB_IKFL2.CONFIRM_BEANS (
	LOGIN_ID ASC, 
	SECONDARY_CONFIRM_CODE ASC
) tablespace USER_DATA_IDX
/   
   
delete from SRB_IKFL2.QRTZ_ERMB_SIMPLE_TRIGGERS where TRIGGER_NAME = 'ExpiredConfirmBeanRemoverTrigger'
/
delete from SRB_IKFL2.QRTZ_ERMB_TRIGGERS where TRIGGER_NAME = 'ExpiredConfirmBeanRemoverTrigger'
/
delete from SRB_IKFL2.QRTZ_ERMB_JOB_DETAILS where JOB_NAME = 'ExpiredConfirmBeanRemoverJob'
/

-- Номер ревизии: 66288
-- Комментарий: BUG076892: [ЕРМБ] ошибка при работе FppUnloadJob
update SRB_IKFL2.ERMB_TARIF set CHARGE_PERIOD = 1 where CHARGE_PERIOD = 0
/

-- Номер ревизии: 66397
-- Комментарий: Доработать видимость продуктов клиента
/*
	На проме делаем [ERIB] CARD_LINKS.sql и [ERIB] ACCOUNT_LINKS.sql
*/

alter table SRB_IKFL2.LOAN_LINKS add SHOW_IN_SOCIAL char(1)
/
update SRB_IKFL2.LOAN_LINKS set SHOW_IN_SOCIAL = SHOW_IN_MOBILE
/
alter table SRB_IKFL2.LOAN_LINKS modify SHOW_IN_SOCIAL not null
/

alter table SRB_IKFL2.IMACCOUNT_LINKS add SHOW_IN_SOCIAL char(1)
/
update SRB_IKFL2.IMACCOUNT_LINKS set SHOW_IN_SOCIAL = SHOW_IN_MOBILE
/
alter table SRB_IKFL2.IMACCOUNT_LINKS modify SHOW_IN_SOCIAL not null
/

-- Номер ревизии: 66506
-- Комментарий: BUG069346: Не отображать ссылку Выберите отделение, если нет доступных ВСП.
create index SRB_IKFL2.IDX_DEP_TB_IS_CRED_CARD_OFFICE on SRB_IKFL2.DEPARTMENTS(
	IS_CREDIT_CARD_OFFICE, 
	TB
)  tablespace USER_METADATA_IDX
/

-- Номер ревизии: 66581
-- Комментарий: Параметризация дополнительного соглашешения к Сберегательному счету
alter table SRB_IKFL2.DEPOSITGLOBALS add PERCENT_RATES_TRANSFORMATION CLOB
/

-- Номер ревизии: 66619
-- Комментарий: Справочник тарифных планов
delete from SRB_IKFL2.TARIF_PLAN_CONFIGS
/
alter table SRB_IKFL2.TARIF_PLAN_CONFIGS add (
	NAME        varchar2(100) not null,
	DATE_BEGIN  timestamp(6)  not null,
	DATE_END    timestamp(6)
)
/

alter table SRB_IKFL2.TARIF_PLAN_CONFIGS add STATE char(1)
/
update SRB_IKFL2.TARIF_PLAN_CONFIGS set STATE = '1'
/
alter table SRB_IKFL2.TARIF_PLAN_CONFIGS modify STATE default '0' not null
/

/*
	На проме делаем PROFILE.sql и USERS.sql
*/


-- Номер ревизии: 66839   
-- Комментарий: Веб представление настройки ТБ и канала 
--              Убрать заполнение поля APPLICATION для внешних ошибок
alter table SRB_IKFL2.EXCEPTION_ENTRY drop column MESSAGE
/
alter table SRB_IKFL2.EXCEPTION_ENTRY modify APPLICATION null
/

CREATE OR REPLACE PROCEDURE SRB_IKFL2.updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in clob, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   PRAGMA AUTONOMOUS_TRANSACTION;
   updateExceptionInformationOFF NUMBER;
BEGIN
    SELECT count(1) into updateExceptionInformationOFF from PROPERTIES where PROPERTY_KEY='com.rssl.phizic.business.exception.updateExceptionInformation' AND PROPERTY_VALUE = 'OFF' AND CATEGORY='iccs.properties';

    IF updateExceptionInformationOFF > 0 THEN
        ROLLBACK;
        RETURN;
    END IF;

    INSERT INTO EXCEPTIONS_LOG(CREATION_DATE, HASH) values (SYSDATE, exceptionHash);

    INSERT INTO EXCEPTION_ENTRY (ID, KIND, HASH, APPLICATION, OPERATION, DETAIL, SYSTEM, ERROR_CODE) 
         SELECT S_EXCEPTION_ENTRY.nextval, exceptionKind, exceptionHash, exceptionApplication, exceptionOperation, exceptionDetail, exceptionSystem, exceptionErrorCode FROM DUAL
        WHERE NOT EXISTS (SELECT 1 FROM EXCEPTION_ENTRY WHERE EXCEPTION_ENTRY.HASH = exceptionHash);

    COMMIT;
END;
/

-- Номер ревизии: 67255
-- Комментарий: Stand-In. Доработка схем прав резервного блока
update SRB_IKFL2.DEFAULT_ACCESS_SCHEMES set CREATION_TYPE = 'CARD_TEMPORARY' where CREATION_TYPE = 'TEMPORARY'
/

insert into SRB_IKFL2.DEFAULT_ACCESS_SCHEMES (ID, CREATION_TYPE, SCHEME_ID, DEPARTMENT_TB)
	select SRB_IKFL2.S_DEFAULT_ACCESS_SCHEMES.nextval, 'UDBO_TEMPORARY', SCHEME_ID, null from SRB_IKFL2.DEFAULT_ACCESS_SCHEMES where CREATION_TYPE = 'CARD_TEMPORARY'
/

-- Номер ревизии: 67299
-- Комментарий: Проверки уникальности телефонов и карт в МБК и ЕРМБ. Модель.
alter table SRB_IKFL2.ERMB_CLIENT_PHONE add CREATION_DATE timestamp
/
update SRB_IKFL2.ERMB_CLIENT_PHONE set CREATION_DATE = sysdate
/
alter table SRB_IKFL2.ERMB_CLIENT_PHONE modify CREATION_DATE not null
/

-- Номер ревизии: 67518
-- Комментарий: Изменение логики загрузки справочника БИК(Модель БД)
alter table SRB_IKFL2.RUSBANKS add DATE_CH timestamp
/

-- Номер ревизии: 67764
-- Комментарий: Профиль. Адресная книга (хранение адресной книги)
alter table SRB_IKFL2.ADDRESS_BOOKS
   add constraint FK_AB_LOGIN_ID foreign key (LOGIN_ID)
      references SRB_IKFL2.LOGINS (ID)
/

-- Номер ревизии: 67767
-- Комментарий: Кредитный профиль клиента
alter table SRB_IKFL2.USER_CREDIT_PROFILE
   add constraint FK_USER_CREDIT_PROFILE_USERS foreign key (USER_ID)
      references SRB_IKFL2.USERS (ID)
/

-- Номер ревизии: 67779
-- Комментарий: Удалить функционал завязанный на неиспользуемую таблицу «CURBANKS»
alter table SRB_IKFL2.CURBANKS rename to FORDEL$CURBANKS
/

-- Номер ревизии: 68125
-- Комментарий: BUG074679: Признак «Предлагать по умолчанию» применяется к нескольким картам в ПФП
drop index SRB_IKFL2.I_PFP_CARDS_DEFAULT_CARD
/
create unique index SRB_IKFL2.I_PFP_CARDS_DEFAULT_CARD on SRB_IKFL2.PFP_CARDS(
    decode(DEFAULT_CARD, '0', null, DEFAULT_CARD)
) tablespace USER_DATA_IDX
/

-- Номер ревизии: 68139
-- Комментарий: Атрибуты для пуш уведомлений
alter table SRB_IKFL2.PUSH_PARAMS add (ATTRIBUTES clob)
/

-- Номер ревизии: 68186
-- Комментарий: Корзина платежей. Реализация джоба автоматического создания подписок.
create index SRB_IKFL2.IDX_INVSUB_STATE_CREATION on SRB_IKFL2.INVOICE_SUBSCRIPTIONS (
   STATE ASC,
   START_DATE ASC
) tablespace USER_DATA_IDX
/

alter table SRB_IKFL2.INVOICE_SUBSCRIPTIONS add (
	CONFIRM_STRATEGY_TYPE varchar2(18),
	CREATION_TYPE char(1)
)	
/
update SRB_IKFL2.INVOICE_SUBSCRIPTIONS set CREATION_TYPE = '0' where STATE = 'AUTO'
/
update SRB_IKFL2.INVOICE_SUBSCRIPTIONS set CREATION_TYPE = '1', CONFIRM_STRATEGY_TYPE = 'sms' where STATE <> 'AUTO'
/
alter table SRB_IKFL2.INVOICE_SUBSCRIPTIONS modify CREATION_TYPE not null
/

-- Номер ревизии: 68331
-- Комментарий: Синхронизация УАК
drop table SRB_IKFL2.INCOGNITO_PROFILES
/
alter table SRB_IKFL2.INCOGNITO_PHONES add LOGIN_ID integer
/
--формальность, таблица пустая
update SRB_IKFL2.INCOGNITO_PHONES ip set ip.LOGIN_ID = (select LOGIN_ID from SRB_IKFL2.USERS u where u.ID = ip.PROFILE_ID )
/
create index SRB_IKFL2.IDX_LOGIN_INCOGNITO on SRB_IKFL2.INCOGNITO_PHONES (LOGIN_ID asc) tablespace USER_DATA_IDX
/
alter index SRB_IKFL2.I_INCOGNITO_PHONES rename to IDX_PH_INCOGNITO;
/
alter table SRB_IKFL2.INCOGNITO_PHONES drop column PROFILE_ID
/

-- Номер ревизии 68387
-- Комментарий: Удалён Job 'DocumentConverterJob' и триггер 'DocumentConverterJobTrigger'  за ненадобностью. Сделано в рамках ENH077739
delete from SRB_IKFL2.QRTZ_SIMPLE_TRIGGERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL2.QRTZ_CRON_TRIGGERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL2.QRTZ_BLOB_TRIGGERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL2.QRTZ_TRIGGER_LISTENERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL2.QRTZ_TRIGGERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL2.QRTZ_JOB_LISTENERS where JOB_NAME = 'DocumentConverterJob' and JOB_GROUP = 'DEFAULT'
/
delete from QRTZ_JOB_DETAILS where JOB_NAME = 'DocumentConverterJob' and JOB_GROUP = 'DEFAULT'
/

-- Номер ревизии 68396
-- Комментарий: Доработка журналов регистрации и платежей.
alter table SRB_IKFL2.INPUT_REGISTER_JOURNAL add DEVICE_INFO varchar2(255)
/

-- Номер ревизии 68377
-- Комментарий: Синхронизация УАК. Настройки УАК
alter table SRB_IKFL2.MOBILE_PLATFORMS add SHOW_SB_ATTRIBUTE char(1)
/
update SRB_IKFL2.MOBILE_PLATFORMS set SHOW_SB_ATTRIBUTE='1'
/
alter table SRB_IKFL2.MOBILE_PLATFORMS modify SHOW_SB_ATTRIBUTE default '1' not null 
/

-- Номер ревизии 68439
-- Комментарий: Многоязычность: 1)новости 2)баннеры(добавлен мультиязычный кеш и доработан механизм генерируемых запросов)
create index SRB_IKFL2.ADVERTISINGS_BY_TB_IDX on SRB_IKFL2.ADVERTISINGS_DEPARTMENTS (
   TB ASC,
   ADVERTISING_ID ASC
) tablespace USER_METADATA_IDX
/

-- Номер ревизии 68549
-- Комментарий: Корзина. Напоминания
alter table SRB_IKFL2.PAYMENTS_DOCUMENTS add (
	REMINDER_TYPE             varchar2(16),
	REMINDER_ONCE_DATE        timestamp,
	REMINDER_DAY_OF_MONTH     integer,
	REMINDER_MONTH_OF_QUARTER integer,
	REMINDER_CREATED_DATE     timestamp	
)
/

-- Номер ревизии: 68982 
-- Комментарий: Изменение отображения комиссии по операция в АС СБОЛ
alter table SRB_IKFL2.COMMISSIONS_SETTINGS add SHOW_COMMISSION_RUB char(1) 
/
update SRB_IKFL2.COMMISSIONS_SETTINGS set SHOW_COMMISSION_RUB='0'
/
alter table SRB_IKFL2.COMMISSIONS_SETTINGS modify SHOW_COMMISSION_RUB not null
/

-- Номер ревизии: 69477
-- Комментарий: ENH079915: [Карты] В детальной информации по карте в поле "Дата формирования отчета" использовать NextReportDate
alter table SRB_IKFL2.STORED_CARD add NEXT_REPORT_DATE timestamp(6)
/
   
-- Номер ревизии: 67915
-- Комментарий: Редактирование локалезависимых сущностей(Регионы)
alter table SRB_IKFL2.REGIONS add UUID VARCHAR2(32)
/
create unique index SRB_IKFL2.I_REGIONS_UUID on SRB_IKFL2.REGIONS(
    UUID
) tablespace USER_METADATA_IDX
/
update SRB_IKFL2.REGIONS set UUID = sys_guid()
/
alter table SRB_IKFL2.REGIONS modify UUID not null
/

-- Номер ревизии: 69382
-- Комментарий: Добавление механизма технологических перерывов в ЦСА
alter table SRB_IKFL2.TECHNOBREAKS add UUID varchar2(32)
/
create unique index SRB_IKFL2.UI_TECHNOBREAKS on SRB_IKFL2.TECHNOBREAKS (
   UUID ASC
) tablespace SYSTEM_DATA_IDX
/
update SRB_IKFL2.TECHNOBREAKS set UUID = sys_guid()
/
alter table SRB_IKFL2.TECHNOBREAKS modify UUID not null
/

-- Номер ревизии: 68972 
-- Комментарий: Наполнение адресной книги. Из справочника доверенных получателей 
alter table SRB_IKFL2.RECENTLY_FILLED_FIELD_DATA rename to FORDEL$RECENTLY_F_F_DATA
/ 
drop sequence SRB_IKFL2.S_RECENTLY_FILLED_FIELD_DATA
/


-- Номер ревизии: 67798
-- Комментарий: Профиль. Адресная книга (первоначальное заполнение)
insert into SRB_IKFL2.ADDRESS_BOOKS( ID,	LOGIN_ID, FULL_NAME, PHONE, SBERBANK_CLIENT, INCOGNITO, CATEGORY, TRUSTED, FREQUENCY_P2P, FREQUENCY_PAY, ADDED_BY, STATUS )
select 
    S_ADDRESS_BOOK.nextval as ID,
    LOGIN_ID as LOGIN_ID, 
    NAME as FULL_NAME,
    PHONE as PHONE, 
    0 as SBERBANK_CLIENT, 
    0 as INCOGNITO, 
    'NONE' as CATEGORY, 
    0 as TRUSTED, 
    0 as FREQUENCY_P2P, 
    0 as FREQUENCY_PAY, 
    'MOBILE' as ADDED_BY, 
    'ACTIVE' as STATUS 
from SRB_IKFL2.CONTACTS
/

alter table SRB_IKFL2.CONTACTS rename to FORDEL$CONTACTS
/
alter table SRB_IKFL2.CONTACTS_SYNC rename to FORDEL$CONTACTS_SYNC
/

-- Номер ревизии: 69528
-- Комментарий:  BUG078393: [ISUP] Оптимизировать запрос лимитов. 
alter table SRB_IKFL2.LIMITS add (END_DATE timestamp)
/

update SRB_IKFL2.LIMITS up_l 
set up_l.END_DATE = 
    case 
        when up_l.START_DATE =
        (
            SELECT MAX(l.START_DATE)
            FROM SRB_IKFL2.LIMITS l
            WHERE   up_l.TB = l.TB and
                l.STATE = 'CONFIRMED' and
                current_date >= l.START_DATE and
                up_l.LIMIT_TYPE = l.LIMIT_TYPE and
                (up_l.GROUP_RISK_ID = l.GROUP_RISK_ID OR up_l.GROUP_RISK_ID IS NULL AND l.GROUP_RISK_ID IS NULL) and
                up_l.CHANNEL_TYPE  = l.CHANNEL_TYPE and
                up_l.RESTRICTION_TYPE = l.RESTRICTION_TYPE and
                (up_l.SECURITY_TYPE = l.SECURITY_TYPE OR up_l.GROUP_RISK_ID IS NULL AND l.GROUP_RISK_ID IS NULL)
        )
        then
            (
                select entered_l.START_DATE 
                from SRB_IKFL2.LIMITS entered_l 
                where 
                    entered_l.ID != up_l.ID and
                    entered_l.TB = up_l.TB and
                    entered_l.STATE = 'CONFIRMED' and
                    entered_l.LIMIT_TYPE = up_l.LIMIT_TYPE and
                    (up_l.GROUP_RISK_ID = entered_l.GROUP_RISK_ID OR up_l.GROUP_RISK_ID IS NULL AND entered_l.GROUP_RISK_ID IS NULL) and
                    up_l.CHANNEL_TYPE  = entered_l.CHANNEL_TYPE and
                    up_l.RESTRICTION_TYPE = entered_l.RESTRICTION_TYPE and
                    (up_l.SECURITY_TYPE = entered_l.SECURITY_TYPE OR up_l.GROUP_RISK_ID IS NULL AND entered_l.GROUP_RISK_ID IS NULL) and
                    entered_l.START_DATE > current_date
            )
        else
            up_l.START_DATE
    end
where 
    up_l.STATE = 'CONFIRMED' and
    up_l.START_DATE <= current_date
/

-- Номер ревизии: 69597
-- Комментарий:  BUG079463  [ЕРМБ смс-канал] ошибка при переводе на карту другого клиента , через смс канал (1. уникальность СМС)
update SRB_IKFL2.CONFIRM_BEANS
   set SECONDARY_CONFIRM_CODE = PRIMARY_CONFIRM_CODE
 where SECONDARY_CONFIRM_CODE is null
/
alter table SRB_IKFL2.CONFIRM_BEANS modify SECONDARY_CONFIRM_CODE not null
/

-- Номер ревизии: 69811
-- Комментарий: BUG077513  Настройка услуги "Мобильный банк". Grace-период обновляется при каждой смене тарифа.
alter table SRB_IKFL2.ERMB_PROFILE add GRACE_PERIOD_DATE timestamp
/
update SRB_IKFL2.ERMB_PROFILE
   set GRACE_PERIOD_DATE = add_months (CONNECTION_DATE, (select GRACE_PERIOD from SRB_IKFL2.ERMB_TARIF where ID = ERMB_TARIF_ID) )
/   
alter table SRB_IKFL2.ERMB_PROFILE modify GRACE_PERIOD_DATE not null
/

-- Номер ревизии: 69906
-- Комментарий: BUG078978: Добавить группу настроек по новому каналу в блок "Настройка видимости в каналах" 
alter table SRB_IKFL2.PAYMENT_SERVICES add SHOW_IN_SOCIAL char(1)
/
update SRB_IKFL2.PAYMENT_SERVICES set SHOW_IN_SOCIAL = '1'
/
alter table SRB_IKFL2.PAYMENT_SERVICES modify SHOW_IN_SOCIAL default '1' not null 
/

-- Номер ревизии: 69974
-- Комментарий: ENH078242: [ISUP] Полный просмотр таблицы CardOperationClaim
create index SRB_IKFL2.I_CARD_OPERATION_CLAIMS_STATUS on SRB_IKFL2.CARD_OPERATION_CLAIMS(
    decode(STATUS, 'TIMEOUT', 1, 'PROCESSING', 2, 'WAITING', 3, 'AUTO_WAITING', 4)
) tablespace USER_DATA_IDX parallel 32 nologging
/
alter index SRB_IKFL2.I_CARD_OPERATION_CLAIMS_STATUS noparallel logging
/

-- Номер ревизии: 70165
-- Комментарий: BUG077756: Ошибка при поиске расширенной заявки на кредит 
update SRB_IKFL2.BUSINESS_DOCUMENTS
	set STATE_CODE = 'EXECUTED' 
		where STATE_CODE = 'ADOPTED' and KIND = 'EL'
/

-- Номер ревизии: 70274
-- Комментарий: BUG081648: Исправить отображение предложений по кредиту 
alter index SRB_IKFL2.DXFK_LOAN_OFFER rename to FORDEL$DXFK_LOAN_OFFER
/

create index SRB_IKFL2.DXFK_LOAN_OFFER on SRB_IKFL2.LOAN_OFFER (
   upper(replace(SUR_NAME, ' ', '')||replace(FIRST_NAME, ' ', '')||replace(PATR_NAME, ' ', '')) asc,
   BIRTHDAY ASC,
   upper(replace(PASPORT_NUMBER, ' ', '')||replace(PASPORT_SERIES, ' ', '')) asc,
   TB ASC
) tablespace USER_DATA_IDX parallel 32 nologging
/
alter index SRB_IKFL2.DXFK_LOAN_OFFER noparallel logging
/

drop index SRB_IKFL2.FORDEL$DXFK_LOAN_OFFER
/
alter table SRB_IKFL2.LOAN_OFFER drop ( LOGIN_ID, DEPARTMENT_ID)  
/

-- Номер ревизии: 66619
-- Комментарий: Справочник тарифных планов
update SRB_IKFL2.RATE set TARIF_PLAN_CODE = '1' where TARIF_PLAN_CODE = 'PREMIER'
/
update SRB_IKFL2.RATE set TARIF_PLAN_CODE = '2' where TARIF_PLAN_CODE = 'GOLD'
/
update SRB_IKFL2.RATE set TARIF_PLAN_CODE = '3' where TARIF_PLAN_CODE = 'FIRST'
/
update SRB_IKFL2.RATE set TARIF_PLAN_CODE = null where TARIF_PLAN_CODE = 'UNKNOWN'
/

-- Номер ревизии: 70427
-- Комментарий: BUG081606: [БКИ] Ошибка при сохранении изменения в настройках времени отправки запроса
delete from SRB_IKFL2.PROPERTIES
where 
   PROPERTY_KEY='com.rssl.iccs.loanreport.bureau.job.lastTry.period.to'
or PROPERTY_KEY='com.rssl.iccs.loanreport.bureau.job.lastTry.period.from'
or PROPERTY_KEY='com.rssl.iccs.loanreport.bureau.job.start.time'
/

--Скрипт для добавления адаптера МБК:
insert into SRB_IKFL2.ADAPTERS(ID, UUID, NAME, ADAPTER_TYPE, LISTENER_URL) 
        values(SRB_IKFL2.S_ADAPTERS.nextval, 'phiz-mbk', 'МБК', 'ESB', '')
/

--Скрипт для установки параметров отображения комиссий, с учетом доработок.
update 
    SRB_IKFL2.COMMISSIONS_SETTINGS
set 
    SHOW_COMMISSION=(case 
                        when PAYMENT_TYPE in ('com.rssl.phizic.gate.claims.IMAOpeningClaim', 
                                              'com.rssl.phizic.gate.claims.AccountOpeningClaim') 
                        then 0 
                        else 1 
                     end),
    SHOW_COMMISSION_RUB=
                    (case 
                        when PAYMENT_TYPE in ('com.rssl.phizic.gate.claims.IMAOpeningClaim', 
                                              'com.rssl.phizic.gate.claims.AccountOpeningClaim') 
                        then 0 
                        else 1 
                     end)
/

update SRB_IKFL2.DEPARTMENTS set TIME_ZONE = TIME_ZONE + 1
/

delete from SRB_IKFL2.PAYMENTFORMS where NAME in (
	'CardReplenishmentPayment',
	'PurchaseCurrencyPayment',
	'SaleCurrencyPayment',
	'TaxPayment',
	'ConvertCurrencyLongOfferPayment'
)
/

delete from SRB_IKFL2.CALENDARPAYMENTFORM where PAYMENTFORM_ID in (
	select ID from SRB_IKFL2.PAYMENTFORMS where NAME in (
		'CardReplenishmentPayment',
		'PurchaseCurrencyPayment',
		'SaleCurrencyPayment',
		'TaxPayment',
		'ConvertCurrencyLongOfferPayment'
	)
)
/

-- Номер ревизии: 71126
-- Комментарий: BUG082215  ЕРМБ. Мигрируют клиенты. которые мигрировать не должны. 
alter table SRB_IKFL2.PHONES modify REGISTRATION_DATE null
/

INSERT INTO SRB_IKFL2.TARIF_PLAN_CONFIGS(ID, TARIF_PLAN_CODE, NEED_SHOW_STANDART_RATE, PRIVILEGED_RATE_MESSAGE, NAME, DATE_BEGIN, DATE_END, STATE)
  VALUES(S_TARIF_PLAN_CONFIGS.nextval, '0', '1', 'При совершении операции используется льготный курс, установленный для клиентов с тарифным планом «%s»', 'отсутствует', TO_TIMESTAMP('2013-09-01 00:00:00:0','YYYY-MM-DD HH24:MI:SS:FF'), NULL, '0')
/
INSERT INTO SRB_IKFL2.TARIF_PLAN_CONFIGS(ID, TARIF_PLAN_CODE, NEED_SHOW_STANDART_RATE, PRIVILEGED_RATE_MESSAGE, NAME, DATE_BEGIN, DATE_END, STATE)
  VALUES(S_TARIF_PLAN_CONFIGS.nextval, '1', '1', 'При совершении операции используется льготный курс, установленный для клиентов с тарифным планом «[tarifPlan/]»', 'Сбербанк Премьер', TO_TIMESTAMP('2013-04-11 00:00:00:0','YYYY-MM-DD HH24:MI:SS:FF'), NULL, '0')
/
INSERT INTO SRB_IKFL2.TARIF_PLAN_CONFIGS(ID, TARIF_PLAN_CODE, NEED_SHOW_STANDART_RATE, PRIVILEGED_RATE_MESSAGE, NAME, DATE_BEGIN, DATE_END, STATE)
  VALUES(S_TARIF_PLAN_CONFIGS.nextval, '3', '1', 'При совершении операции используется льготный курс, установленный для клиентов с тарифным планом «[tarifPlan/]»', 'Сбербанк Первый', TO_TIMESTAMP('2013-12-03 00:00:00:0','YYYY-MM-DD HH24:MI:SS:FF'), NULL, '0')
/

delete from SRB_IKFL2.QRTZ_CRON_TRIGGERS
where trigger_name in (
'DocumentConverterJobTrigger',
'UpdateIncognitoPhonesJob')
/
delete from SRB_IKFL2.QRTZ_SIMPLE_TRIGGERS
where trigger_name in (
'DocumentConverterJobTrigger',
'UpdateIncognitoPhonesJob')
/
delete from SRB_IKFL2.qrtz_triggers
where trigger_name in (
'DocumentConverterJobTrigger',
'UpdateIncognitoPhonesJob')
/
delete from SRB_IKFL2.QRTZ_JOB_DETAILS
where job_name in (
'DocumentConverterJob',
'UpdateIncognitoPhonesJob')
/

