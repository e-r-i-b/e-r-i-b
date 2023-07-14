--ЦСА АДМИН
--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
-- Номер ревизии: 61560
-- Номер версии: 1.18
-- Комментарий: ФОС. схема сотрудника, работающего с письмами
alter table CSAADMIN_IKFL.ACCESSSCHEMES add MAIL_MANAGEMENT char(1)
/
update CSAADMIN_IKFL.ACCESSSCHEMES set MAIL_MANAGEMENT = '0'
/
alter table CSAADMIN_IKFL.ACCESSSCHEMES modify MAIL_MANAGEMENT not null
/

-- Номер ревизии: 61699
-- Номер версии: 1.18
-- Комментарий: Доработки оплаты документов с помощью штрих-кодов.
alter table CSAADMIN_IKFL.REGIONS add PROVIDER_CODE_MAPI varchar2(200) null
/
alter table CSAADMIN_IKFL.REGIONS add PROVIDER_CODE_ATM  varchar2(200) null
/
 
-- Номер ревизии: 61916
-- Номер версии: 1.18
-- Комментарий: ФОС. Выбор сотрудника для переназначения.
create index CSAADMIN_IKFL.I_ACCESSSCHEMES_MAIL on CSAADMIN_IKFL.ACCESSSCHEMES (
   decode(MAIL_MANAGEMENT, '1', 1, null) ASC
) tablespace CSA_ADM_IDX
/
create index CSAADMIN_IKFL."DXFK_LOGINS_TO_ACCESSSC" on CSAADMIN_IKFL.LOGINS
(
   ACCESSSCHEME_ID
) tablespace CSA_ADM_IDX
/

-- Номер ревизии: 62110 
-- Номер версии: 1.18
-- Комментарий: Справочник подразделений. Добавление признака активности подразделения.
alter table CSAADMIN_IKFL.DEPARTMENTS add (ACTIVE char(1))
/
update CSAADMIN_IKFL.DEPARTMENTS set ACTIVE='1'
/
alter table CSAADMIN_IKFL.DEPARTMENTS modify (ACTIVE default '1' not null)
/

-- Номер ревизии: 62220
-- Номер версии: 1.18
-- Комментарий: BUG073185: [ISUP] Вернуть раздельное выставление тех.перерывов в блоках
drop table CSAADMIN_IKFL.TECHNOBREAKS
/
drop sequence CSAADMIN_IKFL.S_TECHNOBREAKS
/

-- Номер ревизии: 62336
-- Номер версии: 1.18
-- Комментарий: Доработка логики исполнения шаблонов
alter table CSAADMIN_IKFL.SERVICE_PROVIDERS add PLANING_FOR_DEACTIVATE char(1)
/
update CSAADMIN_IKFL.SERVICE_PROVIDERS set PLANING_FOR_DEACTIVATE = 0
/
alter table CSAADMIN_IKFL.SERVICE_PROVIDERS modify PLANING_FOR_DEACTIVATE not null
/
alter table CSAADMIN_IKFL.BILLINGS add TEMPLATE_STATE varchar2(30)
/

-- Номер ревизии: 64646
-- Номер версии: 1_18_release_14.0_PSI
-- Комментарий: BUG074679: Признак «Предлагать по умолчанию» применяется к нескольким картам в ПФП.
drop index CSAADMIN_IKFL.I_PFP_CARDS_DEFAULT_CARD
/
create unique index CSAADMIN_IKFL.I_PFP_CARDS_DEFAULT_CARD on CSAADMIN_IKFL.PFP_CARDS( 
	decode(DEFAULT_CARD, '0', null, DEFAULT_CARD )
) tablespace CSA_ADM_IDX
/

-- Номер ревизии: 57966 
-- Комментарий: BUG075805: Фоновая репликация подразделений : Невозможно удалить задачу Новая 
alter table CSAADMIN_IKFL.DEPARTMENTS_TASKS_CONTENT drop constraint FK_DEPARTME_FK_CONTEN_DEPARTME
/

alter table CSAADMIN_IKFL.DEPARTMENTS_TASKS_CONTENT
   add constraint FK_CONTENT_TO_DEP_TASKS foreign key (REPLICA_TASKS_ID)
      references CSAADMIN_IKFL.DEPARTMENTS_REPLICA_TASKS (ID)
      on delete cascade enable novalidate
/


-- Номер ревизии: 65154, 65156
-- Номер версии: 1.18, v.1.18_release_14.0_PSI
-- Комментарий: BUG075776: Репликация поставщиков услуг: ошибка при репликации ПУ, у которого есть алиасы ЕРМБ
create index CSAADMIN_IKFL."DXFK_SMS_ALIAS_TO_PROVIDER" on CSAADMIN_IKFL.PROVIDER_SMS_ALIAS (
   SERVICE_PROVIDER_ID
) tablespace CSA_ADM_IDX
/

alter table CSAADMIN_IKFL.PROVIDER_SMS_ALIAS
add constraint FK_SMS_ALIAS_TO_PROVIDER foreign key (SERVICE_PROVIDER_ID)
    references CSAADMIN_IKFL.SERVICE_PROVIDERS (ID)
		on delete cascade
/

alter table CSAADMIN_IKFL.PROVIDER_SMS_ALIAS_FIELD drop constraint FK_PROVIDER_FK_ALIAS__PROVIDER
/

alter table CSAADMIN_IKFL.PROVIDER_SMS_ALIAS_FIELD
add constraint FK_PROVIDER_FK_ALIAS__PROVIDER foreign key (PROVIDER_SMS_ALIAS_ID)
    references CSAADMIN_IKFL.PROVIDER_SMS_ALIAS (ID)
    on delete cascade
/

alter table CSAADMIN_IKFL.PROVIDER_SMS_ALIAS_FIELD drop constraint CSAADMIN_IKFL.FK_PROVIDER_FK_ALIAS__FIELD_DE
/

alter table CSAADMIN_IKFL.PROVIDER_SMS_ALIAS_FIELD
add constraint FK_PROVIDER_FK_ALIAS__FIELD_DE foreign key (FIELD_DESCRIPTION_ID)
    references CSAADMIN_IKFL.FIELD_DESCRIPTIONS (ID)
    on delete cascade
/


/* Сбор статистики
begin dbms_stats.gather_table_stats( ownname => 'CSAADMIN_IKFL', tabname => 'PFP_CARDS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSAADMIN_IKFL', tabname => 'BILLINGS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSAADMIN_IKFL', tabname => 'SERVICE_PROVIDERS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSAADMIN_IKFL', tabname => 'DEPARTMENTS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSAADMIN_IKFL', tabname => 'ACCESSSCHEMES', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSAADMIN_IKFL', tabname => 'REGIONS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSAADMIN_IKFL', tabname => 'LOGINS', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSAADMIN_IKFL', tabname => 'PROVIDER_SMS_ALIAS_FIELD', degree => 32, cascade => true); end;
begin dbms_stats.gather_table_stats( ownname => 'CSAADMIN_IKFL', tabname => 'PROVIDER_SMS_ALIAS', degree => 32, cascade => true); end;
*/
