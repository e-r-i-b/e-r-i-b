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


