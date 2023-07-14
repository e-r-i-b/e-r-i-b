Конвертер предназанчен для приведения структуры и содержания БД с версии 1.18 к 1.18 биллинги с карт (по состоянию на 02.12.2010)
Порядок действий:
1. выполнить скрипт:
	alter table OPERATIONDESCRIPTORS modify OPERATIONKEY varchar2(45)
2. Выполнить ант таски:
   _Load2_Operations с опцией удаления неизвестных операций; !!!
   _Load3_Dictionaries;
   _Load4_PaymentForms;
   _Load5_DepositGlobal;
   _Load6_Distributions;
   _Load7_ErrorMessages;
   _Load8_Skins;
   _Load11_SystemPaymentServices;

3. Настроить систему на БД версии 1.18
4. Положить файл converter_1.18.sql в папку AntBuilds\db-data
5. Положить файлы converter.ant  и convert.bat в папку AntBuilds.
6. Настроить в файле convert.bat пути до ANT и JDK.
7. Запустить конвертацию, выполнив файл convert.bat

Внесены изменения в след. таблицы: поставщики услуг, регионы, группы услуг, добавлена таблица связка группы услуг - поставщики услуг.
После работы конвертера появится файл логов convert.log, в нем содержится лог конвертиации БД (изменения структуры и приведения данных).

Следующие скрипты выполнить вручную. Вместо {N} поставить значение

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



После запуска приложения и привязки поставщиков выполнить следующие скрипты:

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