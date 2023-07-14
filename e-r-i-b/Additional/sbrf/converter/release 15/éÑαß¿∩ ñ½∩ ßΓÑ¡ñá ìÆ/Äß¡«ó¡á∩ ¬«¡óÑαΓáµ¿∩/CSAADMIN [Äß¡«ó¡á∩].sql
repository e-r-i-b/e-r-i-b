--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = CSAADMIN_IKFL
/

-- Номер ревизии: 63687
-- Номер версии: 1.18
-- Комментарий: Доработать доступность поставщиков в новом канале и запросы sql с учетом этих признаков
alter table CSAADMIN_IKFL.SERVICE_PROVIDERS add (
	IS_AUTOPAYMENT_SUPPORTED_S_API  char(1),
	VISIBLE_AUTOPAYMENTS_FOR_S_API  char(1), 
	VISIBLE_PAYMENTS_FOR_S_API      char(1),
	AVAILABLE_PAYMENTS_FOR_S_API    char(1)
)	
/ 

update CSAADMIN_IKFL.SERVICE_PROVIDERS set IS_AUTOPAYMENT_SUPPORTED_S_API = '0', VISIBLE_AUTOPAYMENTS_FOR_S_API = '0', VISIBLE_PAYMENTS_FOR_S_API = '0', AVAILABLE_PAYMENTS_FOR_S_API ='1'
/

alter table CSAADMIN_IKFL.SERVICE_PROVIDERS modify (
	IS_AUTOPAYMENT_SUPPORTED_S_API  default '0' not null,
	VISIBLE_AUTOPAYMENTS_FOR_S_API  default '0' not null,
	VISIBLE_PAYMENTS_FOR_S_API      default '0' not null,
	AVAILABLE_PAYMENTS_FOR_S_API    default '1' not null
)
/

-- Номер ревизии: 67474
-- Комментарий: Параметризация дополнительного соглашешения к Сберегательному счету. Исправление ошибок
alter table CSAADMIN_IKFL.DEPOSITGLOBALS add PERCENT_RATES_TRANSFORMATION CLOB
/

-- Номер ревизии: 67660
-- Комментарий: Изменение логики загрузки справочника БИК(Модель БД)
alter table CSAADMIN_IKFL.RUSBANKS add DATE_CH timestamp
/
-- Номер ревизии: 67915
-- Комментарий: Редактирование локалезависимых сущностей(Регионы)
-- TODO наполнение блоков
alter table CSAADMIN_IKFL.REGIONS add UUID VARCHAR2(32)
/
create unique index CSAADMIN_IKFL.I_REGIONS_UUID on CSAADMIN_IKFL.REGIONS(
    UUID
)
/
update CSAADMIN_IKFL.REGIONS set UUID = DBMS_RANDOM.STRING('X', 32)
/
alter table REGIONS modify UUID not null
/

-- Номер ревизии: 69528
-- Номер версии: 1.18
-- Комментарий:  BUG078393: [ISUP] Оптимизировать запрос лимитов. 

alter table CSAADMIN_IKFL.LIMITS add (END_DATE timestamp)
/

update CSAADMIN_IKFL.LIMITS up_l 
set up_l.END_DATE = 
    case 
        when up_l.START_DATE =
        (
            SELECT MAX(l.START_DATE)
            FROM CSAADMIN_IKFL.LIMITS l
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
                from CSAADMIN_IKFL.LIMITS entered_l 
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

-- Номер ревизии: 69594
-- Номер версии: 1.18
-- Комментарий:  Блокировка учетных записей сотрудников
alter table CSAADMIN_IKFL.LOGINS add LAST_LOGON_DATE timestamp(6) 
/