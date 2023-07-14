				ИНСТРУКЦИЯ

Следует обратить внмание на следующие моменты:
1) при изменении таблицы ACCOUNT_LINKS в поле IS_CHANGED поумолчанию было вставлено значение '0'  

2) при добавлении поля LOGIN_ID в таблицу CODLOG, поле создано как NULL, т.к. значения для него взять неоткуда; 
 
3) в таблице RECEIVERS статус всех получателей платежа установлен в 'ACTIVE' 
    !!!очень спорный момент, определить активного/не активного получателя платежа из состояния базы v1.12 не удастся.

4) в таблице DEPARTMENTS
	a) к Сбербанк Онлайн подключены только подразделения уровня ВСП;
	b) при добавлении поля TIME_ZONE установлено значение 0 (Московское время);
5)
   в таблице BUSINESS_DOCUMENTS
	a) при добавлении поля CREATION_TYPE установлено значение 1;	
	b) при добавлении поля ARCHIVE       установлено значение 0;
	c) при добавлении поля STATE_MACHINE_NAME установлено значение PaymentStateMachine;	

6) поле our таблицы RUSBANKS не заполнено (неоткуда взять значение)

7) неудалось перенести в таблицу DOCUMENT_EXTENDED_FIELDS дополнительные атрибуты следующих платежей:
	a) ConvertCurrencyPayment  атрибуты: course, type, payer-account-type, receiver-account-type;
	b) LossPassbookApplication атрибут:  deposit-account-type;
	c) PurchaseCurrencyPayment атрибуты: course, payer-account-type, receiver-account-type, recalculated-amount-changed;		 
	d) RurPayJurSB атрибуты: receiver-pay-type, receiver-description; 
	e) RurPayment атрибуты: payer-account-type, receiver-account-type;
	f) SaleCurrencyPayment атрибуты: course, type, payer-account-type, receiver-account-type, recalculated-amount-changed;


!!! 
В файле settings.properties для сбрф поправить настройку 
  ##Специфичные настройки сбера
  #номер ТБ, котрый автоматизирует данный шлюз
  com.rssl.gate.office.code.region.number = 40

 Конвертор состоит из 2х частей
1) 
 a. Необходимо добавить Ant сценарий - файл converter.ant (Additional\sbdf\converter\)
 b. В папку AntBuilds\db-data\ положить sql скрипт - файл converter-v1_12.sql (Additional\sbdf\converter\)
 c. Проверить настройки файла sbrf.PhizIC.properties (настройки подключения, натройки основной и теневой базы).
 d. Выполнить таск Convert_DB_v1.12   
    Выполняем таск _Load2_Operations с опцией удаления неизвестных операций; !!!
    Выполняем таск _Load3_Dictionaries;
    Выполняем таск _Load4_PaymentForms;
    Выполняем таск _Load5_DepositGlobal;
    Выполняем таск _Load6_Distributions;
    Выполняем таск _Load7_ErrorMessages;
    Выполняем таск _Load8_Skins;
2)
 Выполнить процедуру AdditionalQueries.sql
 Перед выполнением процедуры необходимо заменить имя схемы в названии процедуры.

 Что делается в ней:
 a. Создается временная внешняя система, впоследствии ее описание нужно будет отредактировать из приложения администратору.
 b. Добавляется каждому подразделению ID внешней системы (в поле OFFICE_ID (таблица DEPARTMENTS), а также добавлются зависимости в таблицу EXTERNAL_SYSTEMS_LINKS).
 c. Добавляется ID внешней системы к полям: CLIENT_ID (таблица USERS), EXTERNAL_ID (таблиц ACCOUNT_LINKS, CARD_LINKS) 

 d. В версии 1.16 добавлены новые сервисы:

		RecallPayment,BlockingCardClaim - для клиентов,
		GorodManagement                 - для сотрудников,
		ExternalSystemsManager          - для администраторов

	при необходимости разрешения работы с этими сервисами клиентам, сотрудникам, администраторам
	необходимо раскоментировать соответствующий скрипт. Три скрипта находятся в самом начале процедуры (все они описаны).
  e. Создается иерархия подразделений


!!! Замечания
1. При создании иерархии подразделений было добавлено несколько подразделений уровня ОСБ.
   Необходимо их отредактировать из приложения администратора.
2. В алгоритме создания иерахии подразделений подразделение является подразделением уровня ОСБ, в том случае если 
   у него поле OFFICE null или поле OFFICE равно полю OSB. В базе встретилось 2 случая, когда подразделение является 
   подразделением уровня ОСБ при условии, что поле OFFICE не равно полю OSB. Это Балакшинское отделение №8038 (OFFICE_ID 40|8038|8)
   и Алексинское отделение №2631 (OFFICE_ID 40|2631|631) для них необходимо выбрать родителя из приложения либо вручную, 
   удалить созданное временное подразделение и перепривязать наследников. 
      
!!! Внештатные ситуации
1. Невозможно отредактировать подразделение уровня ОСБ (несоответствие поля OFFIСE бд с полем SUBBRANCH файла office.xml)
2. Необходимо поправить вручную значение сиквенса S_USERLOG и значение счетчика LogEntry в таблице COUNTERS 
   значения в скрипте соответственно свои, определяется на месте. 
   
   UPDATE COUNTERS SET VALUE = '488058' WHERE NAME = 'LogEntry'; 
   DROP SEQUENCE S_USERLOG;
   CREATE SEQUENCE S_USERLOG
                   START WITH 488136
                   MAXVALUE 999999999999999999999999999
                   MINVALUE 1
                   NOCYCLE
                   CACHE 20
                   NOORDER;    




