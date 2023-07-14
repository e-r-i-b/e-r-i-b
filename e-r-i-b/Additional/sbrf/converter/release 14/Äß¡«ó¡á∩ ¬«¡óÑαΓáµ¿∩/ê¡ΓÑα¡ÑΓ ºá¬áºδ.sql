-- Номер ревизии: 59525
-- Номер версии: 1.18
-- Комментарий: ИОК
drop table SHOP_ADDITIONAL_FIELDS
/
drop sequence S_SHOP_ADDITIONAL_FIELDS
/
drop table ORDER_RECALLS
/
drop sequence S_ORDER_RECALLS
/
drop table WITHDRAW_EXTENDED_FIELDS 
/
drop sequence S_WITHDRAW_EXTENDED_FIELDS
/
drop table WITHDRAW_DOCUMENTS 
/
drop sequence S_WITHDRAW_DOCUMENTS
/
drop table PAYMENTS_INFO 
/
drop sequence S_PAYMENTS_INFO
/
drop table SHOP_FIELDS 
/
drop sequence S_SHOP_FIELDS
/
drop table ORDERS 
/
drop sequence S_ORDERS
/
drop table E_IN_PERSONS 
/
drop sequence S_E_IN_PERSONS
/

-- Номер ревизии: 59990
-- Номер версии: 1.18
-- Комментарий: BUG070244: Ошибка при оформлении offline заказа
alter table SHOP_ORDERS add MOBILE_CHECKOUT char(1)
/
update SHOP_ORDERS set MOBILE_CHECKOUT = '0'
/
alter table SHOP_ORDERS modify MOBILE_CHECKOUT default '0' not null
/

-- Номер ревизии: 62402
-- Номер версии: 1.18
-- Комментарий: Добавление интернет-заказов в функционал "счетов к оплате"
alter table "SHOP_ORDERS" add ( "DELAYED_PAY_DATE" timestamp null ) 
/

