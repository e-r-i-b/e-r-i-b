				����������

������� �������� ������� �� ��������� �������:
1) ��� ��������� ������� ACCOUNT_LINKS � ���� IS_CHANGED ����������� ���� ��������� �������� '0'  

2) ��� ���������� ���� LOGIN_ID � ������� CODLOG, ���� ������� ��� NULL, �.�. �������� ��� ���� ����� ��������; 
 
3) � ������� RECEIVERS ������ ���� ����������� ������� ���������� � 'ACTIVE' 
    !!!����� ������� ������, ���������� ���������/�� ��������� ���������� ������� �� ��������� ���� v1.12 �� �������.

4) � ������� DEPARTMENTS
	a) � �������� ������ ���������� ������ ������������� ������ ���;
	b) ��� ���������� ���� TIME_ZONE ����������� �������� 0 (���������� �����);
5)
   � ������� BUSINESS_DOCUMENTS
	a) ��� ���������� ���� CREATION_TYPE ����������� �������� 1;	
	b) ��� ���������� ���� ARCHIVE       ����������� �������� 0;
	c) ��� ���������� ���� STATE_MACHINE_NAME ����������� �������� PaymentStateMachine;	

6) ���� our ������� RUSBANKS �� ��������� (�������� ����� ��������)

7) ��������� ��������� � ������� DOCUMENT_EXTENDED_FIELDS �������������� �������� ��������� ��������:
	a) ConvertCurrencyPayment  ��������: course, type, payer-account-type, receiver-account-type;
	b) LossPassbookApplication �������:  deposit-account-type;
	c) PurchaseCurrencyPayment ��������: course, payer-account-type, receiver-account-type, recalculated-amount-changed;		 
	d) RurPayJurSB ��������: receiver-pay-type, receiver-description; 
	e) RurPayment ��������: payer-account-type, receiver-account-type;
	f) SaleCurrencyPayment ��������: course, type, payer-account-type, receiver-account-type, recalculated-amount-changed;


!!! 
� ����� settings.properties ��� ���� ��������� ��������� 
  ##����������� ��������� �����
  #����� ��, ������ �������������� ������ ����
  com.rssl.gate.office.code.region.number = 40

 ��������� ������� �� 2� ������
1) 
 a. ���������� �������� Ant �������� - ���� converter.ant (Additional\sbdf\converter\)
 b. � ����� AntBuilds\db-data\ �������� sql ������ - ���� converter-v1_12.sql (Additional\sbdf\converter\)
 c. ��������� ��������� ����� sbrf.PhizIC.properties (��������� �����������, �������� �������� � ������� ����).
 d. ��������� ���� Convert_DB_v1.12   
    ��������� ���� _Load2_Operations � ������ �������� ����������� ��������; !!!
    ��������� ���� _Load3_Dictionaries;
    ��������� ���� _Load4_PaymentForms;
    ��������� ���� _Load5_DepositGlobal;
    ��������� ���� _Load6_Distributions;
    ��������� ���� _Load7_ErrorMessages;
    ��������� ���� _Load8_Skins;
2)
 ��������� ��������� AdditionalQueries.sql
 ����� ����������� ��������� ���������� �������� ��� ����� � �������� ���������.

 ��� �������� � ���:
 a. ��������� ��������� ������� �������, ������������ �� �������� ����� ����� ��������������� �� ���������� ��������������.
 b. ����������� ������� ������������� ID ������� ������� (� ���� OFFICE_ID (������� DEPARTMENTS), � ����� ���������� ����������� � ������� EXTERNAL_SYSTEMS_LINKS).
 c. ����������� ID ������� ������� � �����: CLIENT_ID (������� USERS), EXTERNAL_ID (������ ACCOUNT_LINKS, CARD_LINKS) 

 d. � ������ 1.16 ��������� ����� �������:

		RecallPayment,BlockingCardClaim - ��� ��������,
		GorodManagement                 - ��� �����������,
		ExternalSystemsManager          - ��� ���������������

	��� ������������� ���������� ������ � ����� ��������� ��������, �����������, ���������������
	���������� ���������������� ��������������� ������. ��� ������� ��������� � ����� ������ ��������� (��� ��� �������).
  e. ��������� �������� �������������


!!! ���������
1. ��� �������� �������� ������������� ���� ��������� ��������� ������������� ������ ���.
   ���������� �� ��������������� �� ���������� ��������������.
2. � ��������� �������� ������� ������������� ������������� �������� �������������� ������ ���, � ��� ������ ���� 
   � ���� ���� OFFICE null ��� ���� OFFICE ����� ���� OSB. � ���� ����������� 2 ������, ����� ������������� �������� 
   �������������� ������ ��� ��� �������, ��� ���� OFFICE �� ����� ���� OSB. ��� ������������ ��������� �8038 (OFFICE_ID 40|8038|8)
   � ����������� ��������� �2631 (OFFICE_ID 40|2631|631) ��� ��� ���������� ������� �������� �� ���������� ���� �������, 
   ������� ��������� ��������� ������������� � ������������� �����������. 
      
!!! ���������� ��������
1. ���������� ��������������� ������������� ������ ��� (�������������� ���� OFFI�E �� � ����� SUBBRANCH ����� office.xml)
2. ���������� ��������� ������� �������� �������� S_USERLOG � �������� �������� LogEntry � ������� COUNTERS 
   �������� � ������� �������������� ����, ������������ �� �����. 
   
   UPDATE COUNTERS SET VALUE = '488058' WHERE NAME = 'LogEntry'; 
   DROP SEQUENCE S_USERLOG;
   CREATE SEQUENCE S_USERLOG
                   START WITH 488136
                   MAXVALUE 999999999999999999999999999
                   MINVALUE 1
                   NOCYCLE
                   CACHE 20
                   NOORDER;    




