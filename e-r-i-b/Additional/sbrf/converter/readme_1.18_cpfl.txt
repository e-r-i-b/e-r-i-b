��������� ������������ ��� ���������� ��������� � ���������� �� � ������ 1.18 � 1.18 �������� � ���� (�� ��������� �� 02.12.2010)
������� ��������:
1. ��������� ������:
	alter table OPERATIONDESCRIPTORS modify OPERATIONKEY varchar2(45)
2. ��������� ��� �����:
   _Load2_Operations � ������ �������� ����������� ��������; !!!
   _Load3_Dictionaries;
   _Load4_PaymentForms;
   _Load5_DepositGlobal;
   _Load6_Distributions;
   _Load7_ErrorMessages;
   _Load8_Skins;
   _Load11_SystemPaymentServices;

3. ��������� ������� �� �� ������ 1.18
4. �������� ���� converter_1.18.sql � ����� AntBuilds\db-data
5. �������� ����� converter.ant  � convert.bat � ����� AntBuilds.
6. ��������� � ����� convert.bat ���� �� ANT � JDK.
7. ��������� �����������, �������� ���� convert.bat

������� ��������� � ����. �������: ���������� �����, �������, ������ �����, ��������� ������� ������ ������ ����� - ���������� �����.
����� ������ ���������� �������� ���� ����� convert.log, � ��� ���������� ��� ������������ �� (��������� ��������� � ���������� ������).

��������� ������� ��������� �������. ������ {N} ��������� ��������

SELECT max(NUM)+100 FROM SYSTEMLOG
go

CREATE SEQUENCE S_SYSTEMLOG_NUM
    INCREMENT BY 1
    START WITH {N}
    NOMAXVALUE
    NOMINVALUE
    NOCYCLE
    CACHE 5
    NOORDER
go

SELECT max(NUM)+100 FROM USERLOG
go

CREATE SEQUENCE S_USERLOG_NUM
    INCREMENT BY 1
    START WITH {N}
    NOMAXVALUE
    NOMINVALUE
    NOCYCLE
    CACHE 5
    NOORDER
go

SELECT max(NUM)+100 FROM CODLOG
go

CREATE SEQUENCE S_CODLOG_NUM
    INCREMENT BY 1
    START WITH {N}
    NOMAXVALUE
    NOMINVALUE
    NOCYCLE
    CACHE 5
    NOORDER
go



����� ������� ���������� � �������� ����������� ��������� ��������� �������:

insert into PAYMENT_SERVICE_CATEGORIES select ID, SERVICE_GROUP from PAYMENT_SERVICES where  not(SERVICE_GROUP is null)
go
 
UPDATE PAYMENT_SERVICE_CATEGORIES  SET CATEGORY ='OTHER' where CATEGORY = '0'
go

UPDATE PAYMENT_SERVICE_CATEGORIES  SET CATEGORY ='TRANSFER' where CATEGORY = '1'
go

UPDATE PAYMENT_SERVICE_CATEGORIES  SET CATEGORY ='DEPOSITS_AND_LOANS' where CATEGORY = '2'
go

UPDATE PAYMENT_SERVICE_CATEGORIES  SET CATEGORY ='COMMUNICATION' where CATEGORY = '3'
go

UPDATE PAYMENT_SERVICE_CATEGORIES  SET CATEGORY ='TAX_PAYMENT' where CATEGORY = '4'
go

UPDATE PAYMENT_SERVICE_CATEGORIES  SET CATEGORY ='EDUCATION' where CATEGORY = '5'
go

UPDATE PAYMENT_SERVICE_CATEGORIES  SET CATEGORY ='SERVICE_PAYMENT' where CATEGORY = '6'
go

UPDATE PAYMENT_SERVICE_CATEGORIES  SET CATEGORY ='PFR' where CATEGORY = '7'
go 

alter table PAYMENT_SERVICES drop column SERVICE_GROUP
go