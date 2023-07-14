/*
	Схема: CSAADMIN_IKFL
	Табличное пространство таблиц: CSA_ADM_DATA
	Табличное пространство индексов: CSA_ADM_IDX
*/

--workaround oracle bug 14398795
alter session set "_add_col_optim_enabled"=false
/
alter session set CURRENT_SCHEMA = CSAADMIN_IKFL
/


-- Номер ревизии: 72240
-- Комментарий: Доработка профиля сотрудника в АРМ ЕРИБ
alter table CSAADMIN_IKFL.EMPLOYEES add (SUDIR_LOGIN varchar2(100))
/
 
-- Номер ревизии: 72648
-- Комментарий: BUG083704: АРМ. Ошибка при добавлении алиасов поставщику через разделители
alter table CSAADMIN_IKFL.PROVIDER_SMS_ALIAS modify ( NAME varchar2(20) )
/

-- Номер ревизии: 72799
-- Комментарий: Интеграция с Яндекс.Деньги. Справочник поставщиков услуг. Создание формы списка
create index CSAADMIN_IKFL.IDX_SP_IS_FASILITATOR on CSAADMIN_IKFL.SERVICE_PROVIDERS (
   IS_FASILITATOR ASC
) tablespace CSA_ADM_IDX
/

-- Номер ревизии: 76100
-- Комментарий: BUG086924: [ISUP] Ошибка удаления поставщика из-за constraint
alter table CSAADMIN_IKFL.AUTOPAY_SETTINGS
   drop constraint FK_AUTOPAY__FK_AUTOPA_SERVICE_
/

alter table CSAADMIN_IKFL.AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY_SETTING_TO_PROV foreign key (RECIPIENT_ID)
      references CSAADMIN_IKFL.SERVICE_PROVIDERS (ID)
      on delete cascade enable novalidate
/
