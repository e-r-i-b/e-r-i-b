--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/

-- Номер ревизии: 59525
-- Номер версии: 1.18
-- Комментарий: ИОК
drop table INVOICE_IKFL.SHOP_ADDITIONAL_FIELDS
/
drop sequence INVOICE_IKFL.S_SHOP_ADDITIONAL_FIELDS
/
drop table INVOICE_IKFL.ORDER_RECALLS
/
drop sequence INVOICE_IKFL.S_ORDER_RECALLS
/
drop table INVOICE_IKFL.WITHDRAW_EXTENDED_FIELDS 
/
drop sequence INVOICE_IKFL.S_WITHDRAW_EXTENDED_FIELDS
/
drop table INVOICE_IKFL.WITHDRAW_DOCUMENTS 
/
drop sequence INVOICE_IKFL.S_WITHDRAW_DOCUMENTS
/
drop table INVOICE_IKFL.PAYMENTS_INFO 
/
drop sequence INVOICE_IKFL.S_PAYMENTS_INFO
/
drop table INVOICE_IKFL.SHOP_FIELDS 
/
drop sequence INVOICE_IKFL.S_SHOP_FIELDS
/
drop table INVOICE_IKFL.ORDERS 
/
drop sequence INVOICE_IKFL.S_ORDERS
/
drop table INVOICE_IKFL.E_IN_PERSONS 
/
drop sequence INVOICE_IKFL.S_E_IN_PERSONS
/

-- Номер ревизии: 62402
-- Номер версии: 1.18
-- Комментарий: Добавление интернет-заказов в функционал "счетов к оплате"
alter table INVOICE_IKFL."SHOP_ORDERS" add ( "DELAYED_PAY_DATE" timestamp null ) 
/

delete from INVOICE_IKFL.qrtz_simple_triggers
where trigger_name = 'OrderMobileCheckoutCheckTrigger'
/
delete from INVOICE_IKFL.QRTZ_TRIGGERS
where trigger_name = 'OrderMobileCheckoutCheckTrigger'
/
delete from INVOICE_IKFL.QRTZ_JOB_DETAILS
where job_name = 'OrderMobileCheckoutCheckJob'
/
/* Сбор статистики
begin dbms_stats.gather_table_stats( ownname => 'INVOICE_IKFL', tabname => 'SHOP_ORDERS', degree => 32, cascade => true); end;
*/