/*
	Запускается перед запуском конвертера, для создания логов и объектов
*/
	CREATE SEQUENCE S_CONVERTER_CLIENTS	INCREMENT BY 1 START WITH 1 MAXVALUE 999999999999 MINVALUE 1 NOCYCLE
    /
	CREATE SEQUENCE S_CONVERTER_EMPLOYEES INCREMENT BY 1 START WITH 1 MAXVALUE 999999999999 MINVALUE 1 NOCYCLE
    /
	CREATE SEQUENCE S_CONVERTER_SET_NUMBER	INCREMENT BY 1 START WITH 1 MAXVALUE 999999999999 MINVALUE 1 NOCYCLE	
	/
	CREATE SEQUENCE S_CONVERTER_SET	INCREMENT BY 1 START WITH 1 MAXVALUE 999999999999 MINVALUE 1 NOCYCLE	
	/
    CREATE SEQUENCE  S_CONVERTER_LOG INCREMENT BY 1 START WITH 1 MAXVALUE 999999999999 MINVALUE 1 NOCYCLE 
	/
	
	-- Создает таблицу с конфигурацией конвертера и заполняет параметрами
    CREATE TABLE CONVERTER_CONFIG ( 
                            "id"					NUMBER(15) NOT NULL,
							"suffix"            	VARCHAR2(5) NULL,
							"scheme_Admin"       	NUMBER(15) NULL,
							"scheme_Employer"    	NUMBER(15) NULL,
							"scheme_Client"      	NUMBER(15) NULL,
							"convert_Delay"      	NUMBER(15) NULL,
							"count_For_Converting"	NUMBER(15) NULL,
							"adapter"           	VARCHAR2(32) NULL,
							"region_id"          	NUMBER(15) NULL,
							"default_Department" 	NUMBER(15) NULL, 
							"billing_id"			NUMBER(15) NULL,
							"stop"				 	CHAR(1) NULL, 
							CONSTRAINT CONVERTER_CONFIG_UNIQUE_ID UNIQUE ("id")							
							)
	/
	-- Таблица сконвертированных клиентов							
    CREATE TABLE CONVERTER_LOG ( 
                            "ID"					NUMBER(15) NOT NULL,
							"TIME"					TIMESTAMP NULL,
							"DESCRIPTION"    		CLOB NULL,
							CONSTRAINT CONVERTER_LOG_UNIQUE_ID UNIQUE ("ID")
							)
	/						
	-- Таблица сконвертированных клиентов							
    CREATE TABLE CONVERTER_CLIENTS ( 
                            "ID"					NUMBER(15) NOT NULL,
							"ID_SBOL"            	VARCHAR2(32) NULL,
							"ID_ERIB"       		NUMBER(15) NULL,
							"LOGIN"					VARCHAR2(20) NULL,
							"TIME_START"			TIMESTAMP NULL,
							"TIME_STOP"				TIMESTAMP NULL,
							"SET_NUMBER"    		NUMBER(15) NULL,
							"STATE"      			CHAR(1) NULL ,
							"STATE_DESCRIPTION"     CLOB NULL,
							"CHECK"					CHAR(1) NULL,
							"CHECK_DESCRIPTION"    CLOB NULL,
							CONSTRAINT CONVERTER_CLIENTS_UNIQUE_ID UNIQUE ("ID")
							)
	/						
	-- Таблица сконвертированных сотрудников
    CREATE TABLE CONVERTER_EMPLOYEES ( 
                            "ID"					NUMBER(15) NOT NULL,
							"ID_SBOL"            	VARCHAR2(32) NULL,
							"ID_ERIB"       		NUMBER(15) NULL,
							"LOGIN"					VARCHAR2(20) NULL,
							"PASSWORD"				VARCHAR2(32) NULL,
							"TIME_START"			TIMESTAMP NULL,
							"TIME_STOP"				TIMESTAMP NULL,
							"SET_NUMBER"    		NUMBER(15) NULL,
							"STATE"      			CHAR(1) NULL ,
							"STATE_DESCRIPTION"     CLOB NULL,
							"CHECK"					CHAR(1) NULL,
							"CHECK__DESCRIPTION"    CLOB NULL,
							CONSTRAINT CONVERTER_EMPLOYEES_UNIQUE_ID UNIQUE ("ID")							
							)
	/						
	-- набор для конвертации (id сотрудника/клиента)						
    CREATE TABLE CONVERTER_SET ( 
                            "ID"					NUMBER(15) NOT NULL,
							"ID_SBOL"               VARCHAR2(32) NULL,
							"LOGIN"               	VARCHAR2(50) NULL,
							CONSTRAINT CONVERTER_SET_UNIQUE_ID UNIQUE ("ID")							
							)
    /
	-- таблица соответствия схем прав сотрудников банка
    CREATE TABLE CONVERTER_ACCESSSCHEMES ( 
                            "ID"							NUMBER(15) NOT NULL,
							"ID_SCHEME_SBOL"        		NUMBER(15) NULL,
							"ID_SCHEME_SBOL_DESCRIPTION" 	VARCHAR2(512) NULL,
							"ID_SCHEME_ERIB"				NUMBER(15) NOT NULL,		
							CONSTRAINT CONVERTER_SCHEMES_UNIQUE_ID UNIQUE ("ID")							
							)
    /	
	-- Создает таблицу соответствия для поставщиков услуг ИСППН "Город"
    CREATE TABLE CONVERTER_SERVICES ( 
                            "ID"					NUMBER(15) NOT NULL,
							"ABONENT_ID"           	VARCHAR2(20) NULL,
							"SERVICE_ID" 			VARCHAR2(20) NULL,
							CONSTRAINT CONVERTER_SERVICES_UNIQUE_ID UNIQUE ("ID")							
							)
    /

	INSERT INTO CONVERTER_CONFIG VALUES(1, '', 0, 0, 0, 0, 0, '', 0, 0, 0, '0')
	/
