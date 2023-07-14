create index IND_RECEPTIONTIMES on RECEPTIONTIMES( 
   PAYMENTFORMNAME asc 
) 
go

--��������� ��� �����: JurPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountRUSTaxPayment' as PAYMENTFORMNAME,
      '������ ������� �� �����' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'JurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountJurIntraBankTransfer' as PAYMENTFORMNAME,
      '������� ��. ���� c� ����� �� ���� ������ ��������� ������' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'JurPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountJurTransfer',
      PAYMENTFORMDESCRIPTION = '������� ��. ���� c� ����� �� ���� � ������ ����'
   where PAYMENTFORMNAME = 'JurPayment'
go
 
--��������� ��� �����: RecallDepositaryClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'RecallDepositaryClaim',
      PAYMENTFORMDESCRIPTION = '����� ��������� �� �����������'
   where PAYMENTFORMNAME = 'RecallDepositaryClaim'
go
 
--��������� ��� �����: RecallPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'RecallPayment'
go
 
--��������� ��� �����: LossPassbookApplication
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'LossPassbookApplicationClaim',
      PAYMENTFORMDESCRIPTION = '������ �� ������ �������������� ������'
   where PAYMENTFORMNAME = 'LossPassbookApplication'
go
 
--��������� ��� �����: LoanCardProduct
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'LoanCardProduct'
go
 
--��������� ��� �����: BlockingCardClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'CardBlockingClaim',
      PAYMENTFORMDESCRIPTION = '������ �� ���������� �����'
   where PAYMENTFORMNAME = 'BlockingCardClaim'
go
 
--��������� ��� �����: RefuseAutoPaymentPayment
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'RefuseAutoPayment',
      PAYMENTFORMDESCRIPTION = '������ ���������� �����������'
   where PAYMENTFORMNAME = 'RefuseAutoPaymentPayment'
go
 
--��������� ��� �����: VirtualCardClaim
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'VirtualCardClaim'
go
 
--��������� ��� �����: ExternalProviderPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'ExternalProviderPayment'
go
 
--��������� ��� �����: LoanOffer
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'LoanOffer'
go
 
--��������� ��� �����: RurPayJurSB
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountPaymentSystemPayment' as PAYMENTFORMNAME,
      '������ ������� � ����� �� �����' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayJurSB'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountPaymentSystemPaymentLongOfer' as PAYMENTFORMNAME,
      '���������� �������� (������ ������� � ����� �� �����)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayJurSB'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardPaymentSystemPayment' as PAYMENTFORMNAME,
      '������ ������� � ����� � �����' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayJurSB'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'CardPaymentSystemPaymentLongOffer',
      PAYMENTFORMDESCRIPTION = '���������� �������� (������ ������� � ����� � �����)'
   where PAYMENTFORMNAME = 'RurPayJurSB'
go

--��������� ��� �����: LoanCardOffer
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'LoanCardOffer'
go
 
--��������� ��� �����: InternalPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'ClientAccountsLongOffer' as PAYMENTFORMNAME,
      '���������� �������� (������� ����� �������)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'InternalPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardToAccountLongOffer' as PAYMENTFORMNAME,
      '���������� �������� (������� � ����� �� ����)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'InternalPayment'
go

insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardToAccountTransfer' as PAYMENTFORMNAME,
      '������� � ����� �� ����' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'InternalPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountToCardTransfer' as PAYMENTFORMNAME,
      '������� �� ����� �� �����' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'InternalPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'ClientAccountsTransfer',
      PAYMENTFORMDESCRIPTION = '������� ����� �������'
   where PAYMENTFORMNAME = 'InternalPayment'
go
 
--��������� ��� �����: AccountOpeningClaim
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountOpeningFromCardClaim' as PAYMENTFORMNAME,
      '�������� ������ (������� ������� � �����)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'AccountOpeningClaim'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'DemandAccountOpeningClaim' as PAYMENTFORMNAME,
      '�������� ����������� ������ ��� ��������������� ������' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'AccountOpeningClaim'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountOpeningClaim',
      PAYMENTFORMDESCRIPTION = '�������� ������ (������� ������� �� �����)'
   where PAYMENTFORMNAME = 'AccountOpeningClaim'
go
 
--��������� ��� �����: CreateAutoPaymentPayment
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'CreateAutoPayment',
      PAYMENTFORMDESCRIPTION = '�������� �����������'
   where PAYMENTFORMNAME = 'CreateAutoPaymentPayment'
go
 
--��������� ��� �����: RefuseLongOffer
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'RefuseLongOffer',
      PAYMENTFORMDESCRIPTION = '������ ���������� ���������� ��������'
   where PAYMENTFORMNAME = 'RefuseLongOffer'
go
 
--��������� ��� �����: LoanPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'LoanTransferLongOffer' as PAYMENTFORMNAME,
      '���������� �������� (��������� �������)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'LoanPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'LoanTransfer',
      PAYMENTFORMDESCRIPTION = '��������� �������'
   where PAYMENTFORMNAME = 'LoanPayment'
go
 
--��������� ��� �����: IMAPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'IMAToAccountTransfer' as PAYMENTFORMNAME,
      '������� � ��� �� �����' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'IMAPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountToIMATransfer',
      PAYMENTFORMDESCRIPTION = '������� �� ������ �� ���'
   where PAYMENTFORMNAME = 'IMAPayment'
go
 
--��������� ��� �����: TaxPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'TaxPayment'
go
 
--��������� ��� �����: SecurityRegistrationClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'SecurityRegistrationClaim',
      PAYMENTFORMDESCRIPTION = '������ �� ����������� ������ ������'
   where PAYMENTFORMNAME = 'SecurityRegistrationClaim'
go
 
--��������� ��� �����: AccountClosingPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountClosingPaymentToCard' as PAYMENTFORMNAME,
      '�������� ������ (������� ������� �� �����)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'AccountClosingPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountClosingPaymentToAccount',
      PAYMENTFORMDESCRIPTION = '�������� ������ (������� ������� �� ����)'
   where PAYMENTFORMNAME = 'AccountClosingPayment'
go
 
--��������� ��� �����: DepositorFormClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'DepositorFormClaim',
      PAYMENTFORMDESCRIPTION = '��������� �� ��������� �������� ������'
   where PAYMENTFORMNAME = 'DepositorFormClaim'
go
 
--��������� ��� �����: SaleCurrencyPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'SaleCurrencyPayment'
go
 
--��������� ��� �����: EditAutoPaymentPayment
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'EditAutoPayment',
      PAYMENTFORMDESCRIPTION = '�������������� �����������'
   where PAYMENTFORMNAME = 'EditAutoPaymentPayment'
go
 
--��������� ��� �����: FNSPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'FNSPayment'
go
 
--��������� ��� �����: LoanProduct
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'LoanProduct'
go
 
--��������� ��� �����: PFRStatementClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'PFRStatementClaim',
      PAYMENTFORMDESCRIPTION = '������ �� ��������� ������� �� ���'
   where PAYMENTFORMNAME = 'PFRStatementClaim'
go
 
--��������� ��� �����: RurPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardIntraBankPaymentLongOffer' as PAYMENTFORMNAME,
      '���������� �������� (������� ���. ���� c ����� �� ���� ������ ��������� ������)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardIntraBankPayment' as PAYMENTFORMNAME,
      '������� ���. ���� c ����� �� ���� ������ ��������� ������' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardRUSPaymentLongOffer' as PAYMENTFORMNAME,
      '���������� �������� (������� ���. ���� c ����� �� ���� � ������ ����)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'CardRUSPayment' as PAYMENTFORMNAME,
      '������� ���. ���� c ����� �� ���� � ������ ����' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountIntraBankPaymentLongOffer' as PAYMENTFORMNAME,
      '���������� �������� (������� ���. ���� ������ ��������� ������)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountIntraBankPayment' as PAYMENTFORMNAME,
      '������� ���. ���� ������ ��������� ������' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'AccountRUSPaymentLongOffer' as PAYMENTFORMNAME,
      '���������� �������� (������� ���. ���� c� ����� �� ���� � ������ ����)' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'RurPayment'
go
 
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'AccountRUSPayment',
      PAYMENTFORMDESCRIPTION = '������� ���. ���� c� ����� �� ���� � ������ ����'
   where PAYMENTFORMNAME = 'RurPayment'
go
 
--��������� ��� �����: ConvertCurrencyPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'ConvertCurrencyPayment'
go
 
--��������� ��� �����: SecuritiesTransferClaim
update RECEPTIONTIMES
   set PAYMENTFORMNAME = 'SecuritiesTransferClaim',
      PAYMENTFORMDESCRIPTION = '��������� �� �������/����� �������� ������ �����'
   where PAYMENTFORMNAME = 'SecuritiesTransferClaim'
go
 
--��������� ��� �����: PurchaseCurrencyPayment
delete from RECEPTIONTIMES 
   where PAYMENTFORMNAME = 'PurchaseCurrencyPayment'
go
 
--����������� ���������� ����������
--��������� ����� ��� ���������: ChargeOffPayment
insert into RECEPTIONTIMES 
   select S_RECEPTIONTIMES.nextval as ID, department_id, calendar_id,
      time_start, time_end, use_parent_settings, 
      'ChargeOffPayment' as PAYMENTFORMNAME,
      '�������� ����� �� ������������' as PAYMENTFORMDESCRIPTION
   from RECEPTIONTIMES
   where PAYMENTFORMNAME = 'CardPaymentSystemPayment'
go
 
--��������� ������
alter table RECEPTIONTIMES rename column PAYMENTFORMNAME to PaymentType
go
 
alter table RECEPTIONTIMES rename column PAYMENTFORMDESCRIPTION to PaymentTypeDescription
go
 
drop index IND_RECEPTIONTIMES
go
