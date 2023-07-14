/*
	Схема: INVOICE_IKFL
	Табличное пространство таблиц: EINVOICING_DATA
	Табличное пространство индексов: EINVOICING_IDX
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = INVOICE_IKFL
/

-- Номер ревизии: 73126
-- Комментарий: Интеграция с Яндекс.Деньги. Модель БД
alter table INVOICE_IKFL.SHOP_ORDERS add (FACILITATOR_PROVIDER_CODE varchar(100) null)
/

alter table INVOICE_IKFL.SHOP_RECALLS add (
	AMOUNT               NUMBER(15,5),
	CURRENCY             CHAR(3)
)
/
