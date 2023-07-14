/*
--добавление вспомог-го поля
alter table ips_transaction add (external_id varchar2(20));
--обновление нового поля из старого
update ips_transaction tr
   set tr.external_id = tr.id;
 
alter table ips_transaction drop column id;
alter table ips_transaction add (id varchar2(20));
 
comment on column IPS_TRANSACTION.id is 'Идентификатор транзакции в ИПС';

update ips_transaction tr
   set tr.id = tr.external_id;

alter table ips_transaction modify (id not null);

alter table IPS_TRANSACTION add constraint PK_ID primary key (ID)

alter table ips_transaction drop column external_id;
*/
