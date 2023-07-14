--ЦСА АДМИН
-- Номер ревизии: 59981
-- Номер версии: 1.18
-- Комментарий: Связь справочника видов (групп) услуг и справочника категорий в АЛФ
alter table PAYMENT_SERVICES add CARD_OPERATION_CATEGORY number null
/
create unique index DXFK_CARD_OP_CATEGORY_ID
    on PAYMENT_SERVICES(CARD_OPERATION_CATEGORY asc)
/

-- Номер ревизии: 61560
-- Номер версии: 1.18
-- Комментарий: ФОС. схема сотрудника, работающего с письмами
alter table ACCESSSCHEMES add MAIL_MANAGEMENT char(1)
/
update ACCESSSCHEMES set MAIL_MANAGEMENT = '0'
/
alter table ACCESSSCHEMES modify MAIL_MANAGEMENT not null
/

-- Номер ревизии: 61699
-- Номер версии: 1.18
-- Комментарий: Доработки оплаты документов с помощью штрих-кодов.
alter table REGIONS add PROVIDER_CODE_MAPI varchar2(200) null
/
alter table REGIONS add PROVIDER_CODE_ATM  varchar2(200) null
/
 
-- Номер ревизии: 61916
-- Номер версии: 1.18
-- Комментарий: ФОС. Выбор сотрудника для переназначения.
create index I_ACCESSSCHEMES_MAIL on ACCESSSCHEMES (
   decode(MAIL_MANAGEMENT, '1', 1, null) ASC
)
/
create index "DXFK_LOGINS_TO_ACCESSSC" on LOGINS
(
   ACCESSSCHEME_ID
)
/

-- Номер ревизии: 62110 
-- Номер версии: 1.18
-- Комментарий: Справочник подразделений. Добавление признака активности подразделения.
alter table DEPARTMENTS add (ACTIVE char(1))
/
update DEPARTMENTS set ACTIVE='1'
/
alter table DEPARTMENTS modify (ACTIVE default '1' not null)
/

-- Номер ревизии: 62220
-- Номер версии: 1.18
-- Комментарий: BUG073185: [ISUP] Вернуть раздельное выставление тех.перерывов в блоках
drop table TECHNOBREAKS
/
drop sequence S_TECHNOBREAKS
/

-- Номер ревизии: 62336
-- Номер версии: 1.18
-- Комментарий: Доработка логики исполнения шаблонов
alter table SERVICE_PROVIDERS add PLANING_FOR_DEACTIVATE char(1)
/
alter table BILLINGS add TEMPLATE_STATE varchar2(30)
/

-- Номер ревизии: 64258
-- Номер версии: 1.18
-- Комментарий: BUG074650: [Синхронизация справочников] Ошибка при синхронизации справочников АЛФ. (часть 1)
drop table CARD_OPERATION_CATEGORIES
/
drop sequence S_CARD_OPERATION_CATEGORIES
/
drop table MERCHANT_CATEGORY_CODES
/
drop sequence S_MERCHANT_CATEGORY_CODES
/
drop table CARD_OPERATION_TYPES 
/
drop sequence S_CARD_OPERATION_TYPES
/

-- Номер ревизии: 64311
-- Номер версии: 1.18
-- Комментарий: BUG074650: [Синхронизация справочников] Ошибка при синхронизации справочников АЛФ. 
alter table PAYMENT_SERVICES drop column CARD_OPERATION_CATEGORY
/

-- Номер ревизии: 64646
-- Номер версии: 1_18_release_14.0_PSI
-- Комментарий: BUG074679: Признак «Предлагать по умолчанию» применяется к нескольким картам в ПФП.
drop index I_PFP_CARDS_DEFAULT_CARD
/
create unique index I_PFP_CARDS_DEFAULT_CARD on PFP_CARDS( decode(DEFAULT_CARD, '0', null, DEFAULT_CARD ))
/

