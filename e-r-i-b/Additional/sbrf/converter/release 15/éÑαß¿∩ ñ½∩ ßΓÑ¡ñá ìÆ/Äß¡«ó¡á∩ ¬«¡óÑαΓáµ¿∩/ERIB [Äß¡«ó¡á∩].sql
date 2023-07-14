--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = SRB_IKFL
/

-- Номер ревизии: 62800
-- Комментарий: Доработка идентификатции внутренних ошибок
alter table SRB_IKFL.EXCEPTION_ENTRY modify HASH varchar2(277)
/
alter table SRB_IKFL.EXCEPTIONS_LOG modify HASH varchar2(277)
/
alter table SRB_IKFL.EXCEPTION_COUNTERS modify EXCEPTION_HASH varchar2(277)
/

-- Можно просто удалить данные либо выполнить скрипт ниже
update SRB_IKFL.EXCEPTIONS_LOG el set (el.HASH) = (select ee.HASH||'|'||ee.APPLICATION from SRB_IKFL.EXCEPTION_ENTRY ee where ee.HASH = el.HASH)
/
update SRB_IKFL.EXCEPTION_COUNTERS el set (el.EXCEPTION_HASH) = (select ee.HASH||'|'||ee.APPLICATION from SRB_IKFL.EXCEPTION_ENTRY ee where ee.HASH = el.EXCEPTION_HASH)
/
update SRB_IKFL.EXCEPTION_ENTRY set HASH = HASH||'|'||APPLICATION
/

-- Номер ревизии: 63687
-- Комментарий: Доработать доступность поставщиков в новом канале и запросы sql с учетом этих признаков
alter table SRB_IKFL.SERVICE_PROVIDERS add (
	IS_AUTOPAYMENT_SUPPORTED_S_API  char(1),
	VISIBLE_AUTOPAYMENTS_FOR_S_API  char(1), 
	VISIBLE_PAYMENTS_FOR_S_API      char(1),
	AVAILABLE_PAYMENTS_FOR_S_API    char(1)
)	
/ 

update SRB_IKFL.SERVICE_PROVIDERS set IS_AUTOPAYMENT_SUPPORTED_S_API = '0', VISIBLE_AUTOPAYMENTS_FOR_S_API = '0', VISIBLE_PAYMENTS_FOR_S_API = '0', AVAILABLE_PAYMENTS_FOR_S_API ='1'
/

alter table SRB_IKFL.SERVICE_PROVIDERS modify (
	IS_AUTOPAYMENT_SUPPORTED_S_API  default '0' not null,
	VISIBLE_AUTOPAYMENTS_FOR_S_API  default '0' not null,
	VISIBLE_PAYMENTS_FOR_S_API      default '0' not null,
	AVAILABLE_PAYMENTS_FOR_S_API    default '1' not null
)
/

-- Номер ревизии: 65995
-- Комментарий: Иерархическое администрирование. Сущность группы сервисов
alter table SRB_IKFL.SERVICES add constraint I_SERVICES_KEY unique (SERVICE_KEY)
/

alter table SRB_IKFL.SERVICES_GROUPS_SERVICES
   add constraint FK_SERVICES_TO_SERVICE foreign key (SERVICE_KEY)
      references SRB_IKFL.SERVICES (SERVICE_KEY)
      on delete cascade
/

-- Номер ревизии: 66183
-- Комментарий: Доработать справочники платформ
alter table SRB_IKFL.MOBILE_PLATFORMS modify (
	VERSION    null,
	ERROR_TEXT null
)
/

-- Номер ревизии: 66197
-- Комментарий: Merged revision(s) 66196 from versions/v.1.18_release_14.0_PSI: BUG071135  [ЕРМБ смс-канал] Не срабатывает проверка для кода подтверждения операции 
-- Номер ревизии: 66672
-- Комментарий: CHG073948  ЕРМБ. Нет проверки номера, с которого приходит подтверждение операции.
delete from SRB_IKFL.CONFIRM_BEANS
/

alter table SRB_IKFL.CONFIRM_BEANS add (
	OVERDUE_TIME timestamp not null,
	PHONE varchar2(20) not null
)
/
drop index SRB_IKFL.IDX_CONFIRM_EXPIRE
/
drop index SRB_IKFL.UNIQ_CONFIRM_LOGIN_CODE
/
create index SRB_IKFL.IDX_CONFIRM_OVERDUE on SRB_IKFL.CONFIRM_BEANS (
	OVERDUE_TIME asc
) tablespace INDX
/
create unique index SRB_IKFL.UI_LOGIN_CONFIRM_CODE_PRIMARY on SRB_IKFL.CONFIRM_BEANS (
	LOGIN_ID ASC, 
	PRIMARY_CONFIRM_CODE ASC
) tablespace INDX
/

create unique index SRB_IKFL.UI_LOGIN_CONFIRM_CODE_SEC on SRB_IKFL.CONFIRM_BEANS (
	LOGIN_ID ASC, 
	SECONDARY_CONFIRM_CODE ASC
) tablespace INDX
/   
   
delete from SRB_IKFL.QRTZ_ERMB_SIMPLE_TRIGGERS where TRIGGER_NAME = 'ExpiredConfirmBeanRemoverTrigger'
/
delete from SRB_IKFL.QRTZ_ERMB_TRIGGERS where TRIGGER_NAME = 'ExpiredConfirmBeanRemoverTrigger'
/
delete from SRB_IKFL.QRTZ_ERMB_JOB_DETAILS where JOB_NAME = 'ExpiredConfirmBeanRemoverJob'
/

-- Номер ревизии: 66288
-- Комментарий: BUG076892: [ЕРМБ] ошибка при работе FppUnloadJob
update SRB_IKFL.ERMB_TARIF set CHARGE_PERIOD = 1 where CHARGE_PERIOD = 0
/

-- Номер ревизии: 66397
-- Комментарий: Доработать видимость продуктов клиента
/*
	На проме делаем [ERIB] CARD_LINKS.sql и [ERIB] ACCOUNT_LINKS.sql

	для внутреннего теста:
	
alter table SRB_IKFL.CARD_LINKS add SHOW_IN_SOCIAL char(1)
/
update SRB_IKFL.CARD_LINKS set SHOW_IN_SOCIAL = '1'
/
alter table SRB_IKFL.CARD_LINKS MODIFY SHOW_IN_SOCIAL not null
/

alter table SRB_IKFL.ACCOUNT_LINKS add SHOW_IN_SOCIAL char(1)
/
update SRB_IKFL.ACCOUNT_LINKS set SHOW_IN_SOCIAL = '1'
/
alter table SRB_IKFL.ACCOUNT_LINKS MODIFY SHOW_IN_SOCIAL not null
/
	
*/

alter table SRB_IKFL.LOAN_LINKS add SHOW_IN_SOCIAL char(1)
/
update SRB_IKFL.LOAN_LINKS set SHOW_IN_SOCIAL = '1'
/
alter table SRB_IKFL.LOAN_LINKS modify SHOW_IN_SOCIAL not null
/

alter table SRB_IKFL.IMACCOUNT_LINKS add SHOW_IN_SOCIAL char(1)
/
update SRB_IKFL.IMACCOUNT_LINKS set SHOW_IN_SOCIAL = '1'
/
alter table SRB_IKFL.IMACCOUNT_LINKS modify SHOW_IN_SOCIAL not null
/

-- Номер ревизии: 66506
-- Комментарий: BUG069346: Не отображать ссылку Выберите отделение, если нет доступных ВСП.
create index SRB_IKFL.IDX_DEP_TB_IS_CRED_CARD_OFFICE on SRB_IKFL.DEPARTMENTS(
	IS_CREDIT_CARD_OFFICE, 
	TB
)  tablespace INDX
/

-- Номер ревизии: 66581
-- Комментарий: Параметризация дополнительного соглашешения к Сберегательному счету
alter table SRB_IKFL.DEPOSITGLOBALS add SRB_IKFL.PERCENT_RATES_TRANSFORMATION CLOB
/

-- Номер ревизии: 66619
-- Комментарий: Справочник тарифных планов
delete from SRB_IKFL.TARIF_PLAN_CONFIGS
/
alter table SRB_IKFL.TARIF_PLAN_CONFIGS add (
	NAME        varchar2(100) not null,
	DATE_BEGIN  timestamp(6)  not null,
	DATE_END    timestamp(6)
)
/

alter table SRB_IKFL.TARIF_PLAN_CONFIGS add STATE char(1)
/
update SRB_IKFL.TARIF_PLAN_CONFIGS set STATE = '1'
/
alter table SRB_IKFL.TARIF_PLAN_CONFIGS modify STATE default '0' not null
/

/*
	На проме делаем PROFILE.sql и USERS.sql
	
	для внутреннего теста:
	
update SRB_IKFL.PROFILE 
	set TARIF_PLAN_CODE = 
		case 
			when TARIF_PLAN_CODE = 'PREMIER' then '1'
			when TARIF_PLAN_CODE = 'GOLD'    then '2'
			when TARIF_PLAN_CODE = 'FIRST'   then '3'
			else null
		end
/		

update SRB_IKFL.USERS 
	set TARIF_PLAN_CODE = 
		case 
			when TARIF_PLAN_CODE = 'PREMIER' then '1'
			when TARIF_PLAN_CODE = 'GOLD'    then '2'
			when TARIF_PLAN_CODE = 'FIRST'   then '3'
			else null
		end
/	
*/

-- Номер ревизии: 66839   
-- Комментарий: Веб представление настройки ТБ и канала 
--              Убрать заполнение поля APPLICATION для внешних ошибок
alter table SRB_IKFL.EXCEPTION_ENTRY drop column MESSAGE
/
alter table SRB_IKFL.EXCEPTION_ENTRY modify APPLICATION null
/

CREATE OR REPLACE PROCEDURE updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in clob, exceptionSystem in varchar, exceptionErrorCode in varchar) as
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
update SRB_IKFL.DEFAULT_ACCESS_SCHEMES set CREATION_TYPE = 'CARD_TEMPORARY' where CREATION_TYPE = 'TEMPORARY'
/

insert into SRB_IKFL.DEFAULT_ACCESS_SCHEMES (ID, CREATION_TYPE, SCHEME_ID, DEPARTMENT_TB)
	select SRB_IKFL.S_DEFAULT_ACCESS_SCHEMES.nextval, 'UDBO_TEMPORARY', SCHEME_ID, null from SRB_IKFL.DEFAULT_ACCESS_SCHEMES where CREATION_TYPE = 'CARD_TEMPORARY'
/

-- Номер ревизии: 67299
-- Комментарий: Проверки уникальности телефонов и карт в МБК и ЕРМБ. Модель.
alter table SRB_IKFL.ERMB_CLIENT_PHONE add CREATION_DATE timestamp
/
update SRB_IKFL.ERMB_CLIENT_PHONE set CREATION_DATE = sysdate
/
alter table SRB_IKFL.ERMB_CLIENT_PHONE modify CREATION_DATE not null
/

-- Номер ревизии: 67518
-- Комментарий: Изменение логики загрузки справочника БИК(Модель БД)
alter table SRB_IKFL.RUSBANKS add DATE_CH timestamp
/

-- Номер ревизии: 67764
-- Комментарий: Профиль. Адресная книга (хранение адресной книги)
alter table SRB_IKFL.ADDRESS_BOOKS
   add constraint FK_AB_LOGIN_ID foreign key (LOGIN_ID)
      references SRB_IKFL.LOGINS (ID)
/

-- Номер ревизии: 67798
-- Комментарий: Профиль. Адресная книга (первоначальное заполнение)
insert into SRB_IKFL.ADDRESS_BOOKS( ID,	LOGIN_ID, FULL_NAME, PHONE, SBERBANK_CLIENT, INCOGNITO, CATEGORY, TRUSTED, FREQUENCY_P2P, FREQUENCY_PAY, ADDED_BY, STATUS )
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
from SRB_IKFL.CONTACTS
/

alter table SRB_IKFL.CONTACTS rename to SRB_IKFL.FORDEL$CONTACTS
/
drop table SRB_IKFL.CONTACTS_SYNC rename to SRB_IKFL.FORDEL$CONTACTS_SYNC
/

-- Номер ревизии: 67767
-- Комментарий: Кредитный профиль клиента
alter table SRB_IKFL.USER_CREDIT_PROFILE
   add constraint FK_USER_CREDIT_PROFILE_USERS foreign key (USER_ID)
      references SRB_IKFL.USERS (ID)
/

-- Номер ревизии: 67779
-- Комментарий: Удалить функционал завязанный на неиспользуемую таблицу «CURBANKS»
alter table SRB_IKFL.CURBANKS rename to SRB_IKFL.FORDEL$CURBANKS
/

-- Номер ревизии: 68125
-- Комментарий: BUG074679: Признак «Предлагать по умолчанию» применяется к нескольким картам в ПФП
drop index SRB_IKFL.I_PFP_CARDS_DEFAULT_CARD
/
create unique index SRB_IKFL.I_PFP_CARDS_DEFAULT_CARD on SRB_IKFL.PFP_CARDS(
    decode(DEFAULT_CARD, '0', null, DEFAULT_CARD)
) tablespace INDX
/

-- Номер ревизии: 68139
-- Комментарий: Атрибуты для пуш уведомлений
alter table SRB_IKFL.PUSH_PARAMS add (ATTRIBUTES clob)
/

-- Номер ревизии: 68186
-- Комментарий: Корзина платежей. Реализация джоба автоматического создания подписок.
create index SRB_IKFL.IDX_INVSUB_STATE_CREATION on SRB_IKFL.INVOICE_SUBSCRIPTIONS (
   STATE ASC,
   START_DATE ASC
) tablespace INDX
/

alter table SRB_IKFL.INVOICE_SUBSCRIPTIONS add (
	CONFIRM_STRATEGY_TYPE varchar2(18),
	CREATION_TYPE char(1)
)	
/
update SRB_IKFL.INVOICE_SUBSCRIPTIONS set CREATION_TYPE = '0' where STATE = 'AUTO'
/
update SRB_IKFL.INVOICE_SUBSCRIPTIONS set CREATION_TYPE = '1', CONFIRM_STRATEGY_TYPE = 'sms' where STATE <> 'AUTO'
/
alter table SRB_IKFL.INVOICE_SUBSCRIPTIONS modify CREATION_TYPE not null
/

-- Номер ревизии: 68331
-- Комментарий: Синхронизация УАК
drop table SRB_IKFL.INCOGNITO_PROFILES
/
alter table SRB_IKFL.INCOGNITO_PHONES add LOGIN_ID integer
/
--формальность, таблица пустая
update SRB_IKFL.INCOGNITO_PHONES ip set ip.LOGIN_ID = (select LOGIN_ID from SRB_IKFL.USERS u where u.ID = ip.PROFILE_ID )
/
create index SRB_IKFL.IDX_LOGIN_INCOGNITO on SRB_IKFL.INCOGNITO_PHONES (LOGIN_ID asc) tablespace INDX
/
create index SRB_IKFL.IDX_PH_INCOGNITO on SRB_IKFL.INCOGNITO_PHONES (PHONE_NUMBER asc) tablespace INDX
/
alter table SRB_IKFL.INCOGNITO_PHONES drop column PROFILE_ID
/

-- Номер ревизии 68387
-- Комментарий: Удалён Job 'DocumentConverterJob' и триггер 'DocumentConverterJobTrigger'  за ненадобностью. Сделано в рамках ENH077739
delete from SRB_IKFL.QRTZ_SIMPLE_TRIGGERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL.QRTZ_CRON_TRIGGERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL.QRTZ_BLOB_TRIGGERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL.QRTZ_TRIGGER_LISTENERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL.QRTZ_TRIGGERS where TRIGGER_NAME = 'DocumentConverterJobTrigger' and TRIGGER_GROUP = 'DEFAULT'
/
delete from SRB_IKFL.QRTZ_JOB_LISTENERS where JOB_NAME = 'DocumentConverterJob' and JOB_GROUP = 'DEFAULT'
/
delete from QRTZ_JOB_DETAILS where JOB_NAME = 'DocumentConverterJob' and JOB_GROUP = 'DEFAULT'
/

-- Номер ревизии 68396
-- Комментарий: Доработка журналов регистрации и платежей.
alter table SRB_IKFL.INPUT_REGISTER_JOURNAL add DEVICE_INFO varchar2(255)
/

-- Номер ревизии 68377
-- Комментарий: Синхронизация УАК. Настройки УАК
alter table SRB_IKFL.MOBILE_PLATFORMS add SHOW_SB_ATTRIBUTE char(1)
/
update SRB_IKFL.MOBILE_PLATFORMS set SHOW_SB_ATTRIBUTE='1'
/
alter table SRB_IKFL.MOBILE_PLATFORMS modify SHOW_SB_ATTRIBUTE default '1' not null 
/

-- Номер ревизии 68439
-- Комментарий: Многоязычность: 1)новости 2)баннеры(добавлен мультиязычный кеш и доработан механизм генерируемых запросов)
create index SRB_IKFL.ADVERTISINGS_BY_TB_IDX on SRB_IKFL.ADVERTISINGS_DEPARTMENTS (
   TB ASC,
   ADVERTISING_ID ASC
) tablespace INDX
/

-- Номер ревизии 68530
-- Комментарий: ЗНИ 179118 исправление ошибки по бизнес логике изменения параметров подписки отчётов по картам
alter table SRB_IKFL.CARD_LINKS add SRB_IKFL.CONTRACT_NUMBER varchar2(64)
/

-- Номер ревизии 68549
-- Комментарий: Корзина. Напоминания
alter table SRB_IKFL.PAYMENTS_DOCUMENTS add (
	REMINDER_TYPE             varchar2(16),
	REMINDER_ONCE_DATE        timestamp,
	REMINDER_DAY_OF_MONTH     integer,
	REMINDER_MONTH_OF_QUARTER integer,
	REMINDER_CREATED_DATE     timestamp	
)
/

-- Номер ревизии: 68982 
-- Комментарий: Изменение отображения комиссии по операция в АС СБОЛ
alter table SRB_IKFL.COMMISSIONS_SETTINGS add SHOW_COMMISSION_RUB char(1) 
/
update SRB_IKFL.COMMISSIONS_SETTINGS set SHOW_COMMISSION_RUB='0'
/
alter table SRB_IKFL.COMMISSIONS_SETTINGS modify SHOW_COMMISSION_RUB not null
/

-- Номер ревизии: 69477
-- Комментарий: ENH079915: [Карты] В детальной информации по карте в поле "Дата формирования отчета" использовать NextReportDate
alter table SRB_IKFL.STORED_CARD add NEXT_REPORT_DATE timestamp(6)
/
   
-- Номер ревизии: 67915
-- Комментарий: Редактирование локалезависимых сущностей(Регионы)
alter table SRB_IKFL.REGIONS add UUID VARCHAR2(32)
/
create unique index SRB_IKFL.I_REGIONS_UUID on SRB_IKFL.REGIONS(
    UUID
) tablespace INDX
/

--TODO наполнение и синхронизация с ЦСА АДМИН!!!
--Заполнить UUID данными из ЦСААдмин
--Если CSAAdmin не включено (не многоблочный режим) выполнить закомментированное:
--update REGIONS set UUID = DBMS_RANDOM.STRING('X', 32)
--go
--Для тестов
update SRB_IKFL.REGIONS set UUID = sys_guid()
/
alter table SRB_IKFL.REGIONS modify UUID not null
/

--TODO наполнение и синхронизация с ЦСА АДМИН!!!
-- Номер ревизии: 69382
-- Комментарий: Добавление механизма технологических перерывов в ЦСА
alter table SRB_IKFL.TECHNOBREAKS add UUID varchar2(32)
/
update SRB_IKFL.TECHNOBREAKS set UUID = sys_guid()
/
alter table SRB_IKFL.TECHNOBREAKS modify UUID not null
/
create unique index SRB_IKFL.UI_TECHNOBREAKS on SRB_IKFL.TECHNOBREAKS (
   UUID ASC
)
/

-- Номер ревизии: 68972 
-- Комментарий: Наполнение адресной книги. Из справочника доверенных получателей 
alter table SRB_IKFL.RECENTLY_FILLED_FIELD_DATA rename to SRB_IKFL.FORDEL$RECENTLY_FILLED_FIELD_DATA
/ 
drop sequence SRB_IKFL.S_RECENTLY_FILLED_FIELD_DATA
/


-- Номер ревизии: 69528
-- Номер версии: 1.18
-- Комментарий:  BUG078393: [ISUP] Оптимизировать запрос лимитов. 
alter table SRB_IKFL.LIMITS add (END_DATE timestamp)
/

update SRB_IKFL.LIMITS up_l 
set up_l.END_DATE = 
    case 
        when up_l.START_DATE =
        (
            SELECT MAX(l.START_DATE)
            FROM SRB_IKFL.LIMITS l
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
                from SRB_IKFL.LIMITS entered_l 
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
-- Номер версии: 1.18
-- Комментарий:  BUG079463  [ЕРМБ смс-канал] ошибка при переводе на карту другого клиента , через смс канал (1. уникальность СМС)
update SRB_IKFL.CONFIRM_BEANS
   set SECONDARY_CONFIRM_CODE = PRIMARY_CONFIRM_CODE
 where SECONDARY_CONFIRM_CODE is null
/
alter table SRB_IKFL.CONFIRM_BEANS modify SECONDARY_CONFIRM_CODE not null
/



/*Права
grant select on SRB_IKFL.EXCEPTION_MAPPINGS                to OSDBO_USER;
grant select on SRB_IKFL.EXCEPTION_MAPPING_RESTRICTIONS    to OSDBO_USER;
grant select on SRB_IKFL.SERVICES_GROUPS                   to OSDBO_USER;
grant select on SRB_IKFL.SERVICES_GROUPS_SERVICES          to OSDBO_USER;
grant select on SRB_IKFL.ERIB_LOCALES                      to OSDBO_USER; 
grant select on SRB_IKFL.UNUSUAL_IP_NOTIFICATIONS          to OSDBO_USER;
grant select on SRB_IKFL.ERIB_STATIC_MESSAGE               to OSDBO_USER;
grant select on SRB_IKFL.USER_SERVICE_INFO                 to OSDBO_USER;
grant select on SRB_IKFL.MBK_CACHE_CARDS_BY_PHONE          to OSDBO_USER;
grant select on SRB_IKFL.MBK_CACHE_CLIENT_BY_CARD          to OSDBO_USER;
grant select on SRB_IKFL.MBK_CACHE_CLIENT_BY_LOGIN         to OSDBO_USER;
grant select on SRB_IKFL.MBK_CACHE_IMSI_CHECK_RESULT       to OSDBO_USER;
grant select on SRB_IKFL.MBK_CACHE_REGISTRATIONS           to OSDBO_USER;
grant select on SRB_IKFL.MBK_CACHE_REGISTRATIONS2          to OSDBO_USER;
grant select on SRB_IKFL.MBK_CACHE_REGISTRATIONS3          to OSDBO_USER;
grant select on SRB_IKFL.FUND_INITIATOR_REQUESTS           to OSDBO_USER;
grant select on SRB_IKFL.FUND_INITIATOR_RESPONSES          to OSDBO_USER;
grant select on SRB_IKFL.FUND_SENDER_RESPONSES             to OSDBO_USER;
grant select on SRB_IKFL.FUND_GROUPS                       to OSDBO_USER;
grant select on SRB_IKFL.FUND_GROUP_PHONES                 to OSDBO_USER;
grant select on SRB_IKFL.ADVERTISING_BUTTONS_RES           to OSDBO_USER;
grant select on SRB_IKFL.ADVERTISINGS_RES                  to OSDBO_USER;
grant select on SRB_IKFL.ADDRESS_BOOKS                     to OSDBO_USER;
grant select on SRB_IKFL.USER_CREDIT_PROFILE               to OSDBO_USER;
grant select on SRB_IKFL.NEWS_RES                          to OSDBO_USER;
grant select on SRB_IKFL.CARD_OPERATION_CATEGORIES_RES     to OSDBO_USER;
grant select on SRB_IKFL.REGION_RES                        to OSDBO_USER;
grant select on SRB_IKFL.MIGRATION_INFO                    to OSDBO_USER;
grant select on SRB_IKFL.MIGRATION_THREAD_INFO             to OSDBO_USER;
grant select on SRB_IKFL.RUSBANKS_RES                      to OSDBO_USER;
grant select on SRB_IKFL.BKI_ACCOUNT_CLASS                 to OSDBO_USER;
grant select on SRB_IKFL.BKI_ACCOUNT_PAYMENT_STATUS        to OSDBO_USER;
grant select on SRB_IKFL.BKI_ADRESS_TYPE                   to OSDBO_USER;
grant select on SRB_IKFL.BKI_APPLICANT_TYPE                to OSDBO_USER;
grant select on SRB_IKFL.BKI_APPLICATION_TYPE              to OSDBO_USER;
grant select on SRB_IKFL.BKI_BANK_CONSTANT_NAME            to OSDBO_USER;
grant select on SRB_IKFL.BKI_CONSENT_INDICATOR             to OSDBO_USER;
grant select on SRB_IKFL.BKI_COUNTRY                       to OSDBO_USER;
grant select on SRB_IKFL.BKI_CREDIT_FACILITY_STATUS        to OSDBO_USER;
grant select on SRB_IKFL.BKI_CURRENT_PREVIOUS_ADDRESS      to OSDBO_USER;
grant select on SRB_IKFL.BKI_DISPUTE_INDICATOR             to OSDBO_USER;
grant select on SRB_IKFL.BKI_FINANCE_TYPE                  to OSDBO_USER;
grant select on SRB_IKFL.BKI_INSURED_LOAN                  to OSDBO_USER;
grant select on SRB_IKFL.BKI_PRIMARY_ID_TYPE               to OSDBO_USER;
grant select on SRB_IKFL.BKI_PURPOSE_OF_FINANCE    to OSDBO_USER;
grant select on SRB_IKFL.BKI_REASON_FOR_CLOSURE    to OSDBO_USER;
grant select on SRB_IKFL.BKI_REASON_FOR_ENQUIRY    to OSDBO_USER;
grant select on SRB_IKFL.BKI_REGION_CODE           to OSDBO_USER;
grant select on SRB_IKFL.BKI_SEX                   to OSDBO_USER;
grant select on SRB_IKFL.BKI_TITTLE                to OSDBO_USER;
grant select on SRB_IKFL.BKI_TYPE_OF_SCORE_CARD    to OSDBO_USER;
grant select on SRB_IKFL.BKI_TYPE_OF_SECURITY      to OSDBO_USER;
grant select on SRB_IKFL.SERVICE_PROVIDERS_RES     to OSDBO_USER;
grant select on SRB_IKFL.MOBILE_PLATFORM_RES       to OSDBO_USER;
grant select on SRB_IKFL.TECHNOBREAKS_RES          to OSDBO_USER;
grant select on SRB_IKFL.EXCEPTION_MAPPINGS_RES    to OSDBO_USER;
grant select on SRB_IKFL.MBK_CAST                  to OSDBO_USER;
grant select on SRB_IKFL.REMINDER_LINKS            to OSDBO_USER;
grant select on SRB_IKFL.PHONES_TO_AVATAR          to OSDBO_USER;
grant select on SRB_IKFL.PAYMENT_SERVICES_RES           to OSDBO_USER;
grant select on SRB_IKFL.ERMB_CLIENT_TARIFF_HISTORY     to OSDBO_USER; 
grant select on SRB_IKFL.AGGREGATION_STATE              to OSDBO_USER;
grant select on SRB_IKFL.PAYMENT_SERVICES_AGGR          to OSDBO_USER;
grant select on SRB_IKFL.SERVICE_PROVIDERS_AGGR         to OSDBO_USER;
grant select on SRB_IKFL.LOCALE_EXCEPTION_MAPPINGS      to OSDBO_USER;
grant select on SRB_IKFL.CARD_OPERATIONS_EXT_FIELDS     to OSDBO_USER;
*/