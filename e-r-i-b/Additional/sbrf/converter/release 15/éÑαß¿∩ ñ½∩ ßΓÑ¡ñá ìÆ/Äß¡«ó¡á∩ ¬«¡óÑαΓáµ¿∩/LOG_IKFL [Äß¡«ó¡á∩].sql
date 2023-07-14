--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = LOG_IKFL
/

-- Номер ревизии: 67273
-- Комментарий: Уменьшение объема информации при логировании. Новые поля для таблицы MESSAGE_TRANSLATE
alter table LOG_IKFL.MESSAGE_TRANSLATE add (
    IS_NEW     char(1)       default '0' not null,
    LOG_TYPE   varchar2(20)
)
/

-- Номер ревизии: 67924
-- Номер версии: 1.18
-- Комментарий: Stand-In. Журналы логов.
create index LOG_IKFL.CL_FIO_BIRTHDAY_DATE_INDEX on LOG_IKFL.CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES parallel 32
/

create index LOG_IKFL.SL_FIO_BIRTHDAY_DATE_INDEX on LOG_IKFL.SYSTEMLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE DESC
) local tablespace ERIBLOGINDEXES parallel 32
/

create index LOG_IKFL.UL_FIO_BIRTHDAY_DATE_INDEX on LOG_IKFL.USERLOG (
    UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
    BIRTHDAY ASC,
    START_DATE DESC
) local tablespace ERIBLOGINDEXES parallel 32
/

alter index LOG_IKFL.CL_FIO_BIRTHDAY_DATE_INDEX noparallel
/
alter index LOG_IKFL.SL_FIO_BIRTHDAY_DATE_INDEX noparallel
/
alter index LOG_IKFL.UL_FIO_BIRTHDAY_DATE_INDEX noparallel
/
