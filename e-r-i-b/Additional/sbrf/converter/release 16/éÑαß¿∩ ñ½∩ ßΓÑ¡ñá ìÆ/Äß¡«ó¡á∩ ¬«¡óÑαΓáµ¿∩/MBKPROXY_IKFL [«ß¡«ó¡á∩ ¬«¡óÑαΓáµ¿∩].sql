/*
	Схема: MBKPROXY_IKFL
	Табличное пространство таблиц: MBKPROXY_DATA
	Табличное пространство индексов: MBKPROXY_IDX
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = MBKPROXY_IKFL
/

-- Номер ревизии: 71973
-- Комментарий: Унификация формата телефона - вспомогательные таблицы ермб
update MBKPROXY_IKFL.ERMB_PHONES set PHONE_NUMBER = substr(PHONE_NUMBER, 2)
/
alter table MBKPROXY_IKFL.ERMB_PHONES modify PHONE_NUMBER varchar2(10)
/
