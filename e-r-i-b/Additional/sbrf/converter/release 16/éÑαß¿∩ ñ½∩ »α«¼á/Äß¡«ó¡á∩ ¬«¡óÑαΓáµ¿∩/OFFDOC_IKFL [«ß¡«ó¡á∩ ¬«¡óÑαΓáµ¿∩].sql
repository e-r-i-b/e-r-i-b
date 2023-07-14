/*
	Схема: OFFDOC_IKFL
	Табличное пространство таблиц: OFFLINE_DOC_DATA
	Табличное пространство индексов: OFFLINE_DOC_IDX
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = OFFDOC_IKFL
/

-- Номер ревизии: 72543
-- Комментарий: В таблице исходящих запросов сделан необязательным OPER_UID, добавлен TB
alter table OFFDOC_IKFL.OUTGOING_REQUESTS modify(OPER_UID null)
/
alter table OFFDOC_IKFL.OUTGOING_REQUESTS rename column PERSON_ID to LOGIN_ID
/
alter table OFFDOC_IKFL.OUTGOING_REQUESTS add (TB varchar2(4) null)
/