create index IND_RECEPTIONTIMES on RECEPTIONTIMES( 
   PAYMENTFORMNAME asc 
) 
go

--Конвертор для формы: JurPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountRUSTaxPayment' as PAYMENTFORMNAME,
      'Оплата налогов со счета' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'JurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountJurIntraBankTransfer' as PAYMENTFORMNAME,
      'Перевод юр. лицу cо счета на счет внутри Сбербанка России' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'JurPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountJurTransfer',
      PAYMENTFORMDESCRIPTION = 'Перевод юр. лицу cо счета на счет в другой банк'
   where PAYMENTFORMNAME = 'JurPayment'
go
 
--Конвертор для формы: RecallDepositaryClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'RecallDepositaryClaim',
      PAYMENTFORMDESCRIPTION = 'Отзыв документа из депозитария'
   where PAYMENTFORMNAME = 'RecallDepositaryClaim'
go
 
--Конвертор для формы: RecallPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'RecallPayment'
go
 
--Конвертор для формы: LossPassbookApplication
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'LossPassbookApplicationClaim',
      PAYMENTFORMDESCRIPTION = 'Заявка об утрате сберегательной книжки'
   where PAYMENTFORMNAME = 'LossPassbookApplication'
go
 
--Конвертор для формы: LoanCardProduct
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'LoanCardProduct'
go
 
--Конвертор для формы: BlockingCardClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'CardBlockingClaim',
      PAYMENTFORMDESCRIPTION = 'Заявка на блокировку карты'
   where PAYMENTFORMNAME = 'BlockingCardClaim'
go
 
--Конвертор для формы: RefuseAutoPaymentPayment
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'RefuseAutoPayment',
      PAYMENTFORMDESCRIPTION = 'Отмена выполнения автоплатежа'
   where PAYMENTFORMNAME = 'RefuseAutoPaymentPayment'
go
 
--Конвертор для формы: VirtualCardClaim
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'VirtualCardClaim'
go
 
--Конвертор для формы: ExternalProviderPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'ExternalProviderPayment'
go
 
--Конвертор для формы: LoanOffer
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'LoanOffer'
go
 
--Конвертор для формы: RurPayJurSB
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountPaymentSystemPayment' as PAYMENTFORMNAME,
      'Оплата товаров и услуг со счета' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayJurSB'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountPaymentSystemPaymentLongOfer' as PAYMENTFORMNAME,
      'регулярная операция (оплата товаров и услуг со счета)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayJurSB'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardPaymentSystemPayment' as PAYMENTFORMNAME,
      'Оплата товаров и услуг с карты' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayJurSB'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'CardPaymentSystemPaymentLongOffer',
      PAYMENTFORMDESCRIPTION = 'Регулярная операция (оплата товаров и услуг с карты)'
   where PAYMENTFORMNAME = 'RurPayJurSB'
go

--Конвертор для формы: LoanCardOffer
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'LoanCardOffer'
go
 
--Конвертор для формы: InternalPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'ClientAccountsLongOffer' as PAYMENTFORMNAME,
      'Регулярная операция (перевод между счетами)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'InternalPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardToAccountLongOffer' as PAYMENTFORMNAME,
      'Регулярная операция (перевод с карты на счет)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'InternalPayment'
go

insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardToAccountTransfer' as PAYMENTFORMNAME,
      'Перевод с карты на счет' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'InternalPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountToCardTransfer' as PAYMENTFORMNAME,
      'Перевод со счета на карту' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'InternalPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'ClientAccountsTransfer',
      PAYMENTFORMDESCRIPTION = 'Перевод между счетами'
   where PAYMENTFORMNAME = 'InternalPayment'
go
 
--Конвертор для формы: AccountOpeningClaim
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountOpeningFromCardClaim' as PAYMENTFORMNAME,
      'Открытие вклада (перевод остатка с карты)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'AccountOpeningClaim'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'DemandAccountOpeningClaim' as PAYMENTFORMNAME,
      'Открытие бессрочного вклада без первоначального взноса' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'AccountOpeningClaim'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountOpeningClaim',
      PAYMENTFORMDESCRIPTION = 'Открытие вклада (перевод остатка со счета)'
   where PAYMENTFORMNAME = 'AccountOpeningClaim'
go
 
--Конвертор для формы: CreateAutoPaymentPayment
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'CreateAutoPayment',
      PAYMENTFORMDESCRIPTION = 'Создание автоплатежа'
   where PAYMENTFORMNAME = 'CreateAutoPaymentPayment'
go
 
--Конвертор для формы: RefuseLongOffer
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'RefuseLongOffer',
      PAYMENTFORMDESCRIPTION = 'Отмена выполнения регулярной операции'
   where PAYMENTFORMNAME = 'RefuseLongOffer'
go
 
--Конвертор для формы: LoanPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'LoanTransferLongOffer' as PAYMENTFORMNAME,
      'Регулярная операция (погашение кредита)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'LoanPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'LoanTransfer',
      PAYMENTFORMDESCRIPTION = 'Погашение кредита'
   where PAYMENTFORMNAME = 'LoanPayment'
go
 
--Конвертор для формы: IMAPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'IMAToAccountTransfer' as PAYMENTFORMNAME,
      'Перевод с ОМС на вклад' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'IMAPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountToIMATransfer',
      PAYMENTFORMDESCRIPTION = 'Перевод со вклада на ОМС'
   where PAYMENTFORMNAME = 'IMAPayment'
go
 
--Конвертор для формы: TaxPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'TaxPayment'
go
 
--Конвертор для формы: SecurityRegistrationClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'SecurityRegistrationClaim',
      PAYMENTFORMDESCRIPTION = 'Заявка на регистрацию ценной бумаги'
   where PAYMENTFORMNAME = 'SecurityRegistrationClaim'
go
 
--Конвертор для формы: AccountClosingPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountClosingPaymentToCard' as PAYMENTFORMNAME,
      'Закрытие вклада (перевод остатка на карту)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'AccountClosingPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountClosingPaymentToAccount',
      PAYMENTFORMDESCRIPTION = 'Закрытие вклада (перевод остатка на счет)'
   where PAYMENTFORMNAME = 'AccountClosingPayment'
go
 
--Конвертор для формы: DepositorFormClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'DepositorFormClaim',
      PAYMENTFORMDESCRIPTION = 'Поручение на получение анкетных данных'
   where PAYMENTFORMNAME = 'DepositorFormClaim'
go
 
--Конвертор для формы: SaleCurrencyPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'SaleCurrencyPayment'
go
 
--Конвертор для формы: EditAutoPaymentPayment
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'EditAutoPayment',
      PAYMENTFORMDESCRIPTION = 'Редактирование автоплатежа'
   where PAYMENTFORMNAME = 'EditAutoPaymentPayment'
go
 
--Конвертор для формы: FNSPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'FNSPayment'
go
 
--Конвертор для формы: LoanProduct
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'LoanProduct'
go
 
--Конвертор для формы: PFRStatementClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'PFRStatementClaim',
      PAYMENTFORMDESCRIPTION = 'Заявка на получение выписки из ПФР'
   where PAYMENTFORMNAME = 'PFRStatementClaim'
go
 
--Конвертор для формы: RurPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardIntraBankPaymentLongOffer' as PAYMENTFORMNAME,
      'Регулярная операция (перевод физ. лицу c карты на счет внутри Сбербанка России)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardIntraBankPayment' as PAYMENTFORMNAME,
      'Перевод физ. лицу c карты на счет внутри Сбербанка России' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardRUSPaymentLongOffer' as PAYMENTFORMNAME,
      'Регулярная операция (перевод физ. лицу c карты на счет в другой банк)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardRUSPayment' as PAYMENTFORMNAME,
      'Перевод физ. лицу c карты на счет в другой банк' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountIntraBankPaymentLongOffer' as PAYMENTFORMNAME,
      'Регулярная операция (перевод физ. лицу внутри Сбербанка России)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountIntraBankPayment' as PAYMENTFORMNAME,
      'Перевод физ. лицу внутри Сбербанка России' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountRUSPaymentLongOffer' as PAYMENTFORMNAME,
      'Регулярная операция (перевод физ. лицу cо счета на счет в другой банк)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountRUSPayment',
      PAYMENTFORMDESCRIPTION = 'Перевод физ. лицу cо счета на счет в другой банк'
   where PAYMENTFORMNAME = 'RurPayment'
go
 
--Конвертор для формы: ConvertCurrencyPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'ConvertCurrencyPayment'
go
 
--Конвертор для формы: SecuritiesTransferClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'SecuritiesTransferClaim',
      PAYMENTFORMDESCRIPTION = 'Поручение на перевод/прием перевода ценных бумаг'
   where PAYMENTFORMNAME = 'SecuritiesTransferClaim'
go
 
--Конвертор для формы: PurchaseCurrencyPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'PurchaseCurrencyPayment'
go
 
--Конвертация оставшихся документов
--Проверить время для документа: ChargeOffPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'ChargeOffPayment' as PAYMENTFORMNAME,
      'Списание платы за обслуживание' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'CardPaymentSystemPayment'
go
 
--Изменение таблиц
alter table RECEPTIONTIMES rename column PAYMENTFORMNAME to PaymentType
go
 
alter table RECEPTIONTIMES rename column PAYMENTFORMDESCRIPTION to PaymentTypeDescription
go
 
drop index IND_RECEPTIONTIMES
go
