--���� �������� ������ ����������� ���������� � ������ ������
	update MAIL set IS_SHOW = '1' where IS_SHOW is null
	/
	update MAIL set CREATION_DATE = sysdate where CREATION_DATE is null
	/

--� ������� 27938

	--alter table ACCOUNT_LINKS add SHOW_IN_MOBILE char(1) default '1' not null --������ [����������: 8� 48s] 
	--/ �������� � ������� �������
	--alter table CARD_LINKS add SHOW_IN_MOBILE char(1) default '1' not null -- [����������: 5� 21s] 
	--/ �������� � ������� �������
	alter table LOAN_LINKS add SHOW_IN_MOBILE char(1) default '1' not null
	/

--� ������� 28026

	alter table SHOP_FIELDS
	    add (BACK_URL varchar2(1000) null)
	/
	alter table  SERVICE_PROVIDERS
	    add (BACK_URL varchar2(255) null)
	    add (AFTER_ACTION char(1) default 0 not null)
	/
	
--� ������� 28118
	alter table FAVOURITE_LINKS
		add (PATTERN_NAME  varchar2(350))
    /

--� ������� 28176
	alter table LIMITS add GROUP_RISK varchar2(6) default 'LOW' not null
	/
	alter table SERVICE_PROVIDERS add  GROUP_RISK varchar2(6) default 'LOW' not null
	/
	alter table BUSINESS_DOCUMENTS add  ADDITION_CONFIRM char(2)
	/
	alter table BUSINESS_DOCUMENTS add CONFIRM_EMPLOYEE varchar(50)
	/
	--alter table USERSLIMIT add GROUP_RISK varchar2(10) default 'LOW' not null --������ [����������: 10� 54s] 
	--/ �������� � ������� �������
	alter table BUSINESS_DOCUMENTS modify STATE_CODE varchar2(25)
	/

--� ������� 28215
	alter table EMPLOYEES add ( "CA_ADMIN" char(1) default '0' NOT null ) 
	/
	alter table ACCESSSCHEMES add ( "CA_ADMIN_SCHEME" char(1) default '0' NOT null ) 
	/

--����� ������� 28371
	update SERVICE_PROVIDERS set IS_INTERVAL_THRESHOLD = '1'
		where IS_INTERVAL_THRESHOLD is null and MIN_VALUE_THRESHOLD is not null and MAX_VALUE_THRESHOLD is not null
	/

--� ������� 28538 ����� 1.18 ���������� ������ ��. ��� ������������ ���� ����� ��������� ������:
	alter table CARD_LINKS
		add (MAIN_CARD_NUMBER varchar2(25))
	/
	
--� ������� 28601
	alter table BUSINESS_DOCUMENTS add OPERATION_UID varchar2(32) default null
	/	
	
--� ������� 28684
	alter table MONITORING_SERVER_IDLE_TIME add TYPE varchar2(10) default 'fullIdle' not null
	/
	create index IDLE_TIME_I_END_DATE
		on MONITORING_SERVER_IDLE_TIME(END_DATE)
	/
	create index IDLE_TIME_I_START_DATE
		on MONITORING_SERVER_IDLE_TIME(START_DATE)
	/
	
	alter table MONITORING_SERVER_WORK_TIME add END_PING_DATE timestamp
	/
	create index SERVER_WORK_I_END_DATE
		on MONITORING_SERVER_WORK_TIME(END_DATE)
	/
	create index SERVER_WORK_I_START_DATE
		on MONITORING_SERVER_WORK_TIME(START_DATE)
	/
	
	alter table SYSTEM_IDLE_REPORT modify TYPE varchar2(10)
	/
	update SYSTEM_IDLE_REPORT set TYPE='fullIdle' where TYPE='1'
	/
	
--� ������� 28714
	alter table CODLOG add OPERATION_UID varchar2(32) default null
	/	
--� ������� 28176
	alter table USERSLIMIT drop column TYPE--������
	/	

--� ������� 28779
	alter table CODLOG modify (MESSAGE_ANSWER_TYPE varchar2(80))
	/

--� ������� 28772
	alter table CODLOG modify (MESSAGE_TYPE varchar2(80))
	/

--� ������� 28832
	alter table MAIL add RECIPIENT_TYPE varchar2(10)
	/
	alter table MAIL add RECIPIENT_NAME varchar2(128)
	/
	alter table MAIL add RECIPIENT_ID integer
	/	 
	delete from RECIPIENTS where MAIL_ID is null
	/	
	insert into MAIL_RECIPIENTS(MAIL_ID, RECIPIENT_ID) select recipient.MAIL_ID, recipient.ID from recipients recipient
	/
	update MAIL mail set (RECIPIENT_TYPE, RECIPIENT_NAME, RECIPIENT_ID) = 
	(
	   select recipient.TYPE, recipient.RECIPIENT_NAME, recipient.RECIPIENT_ID 
	   from RECIPIENTS recipient 
	   where recipient.LIST_INDEX = 0 and mail.id=recipient.MAIL_ID
	)
	/
	delete from (select recipient.* from RECIPIENTS recipient, MAIL mail where mail.state like '%DRAFT' and mail.ID = recipient.MAIL_ID)
	/
	alter table recipients drop constraint FK_RECIPIENT_TO_MAIL
	/	
		
--� ������� 28890
	alter table recipients drop column MAIL_ID
	/
	alter table recipients drop column LIST_INDEX
	/	
	
--� ������� 28909
	begin
	-- ������ �������� �� �������������� ���������
	    delete from counters where lower(name) in ('logentry','systemlogentry','messaginglogentry','unload_product','document_number');

	-- ������������� �������� ����������� ���������
	    execute immediate 'rename s_document_number to SC_document_number_'||TO_CHAR(SYSDATE, 'yyMMdd');
	    execute immediate 'rename s_unload_product to sc_unload_product_'||TO_CHAR(SYSDATE, 'yyMMdd');
	    execute immediate 'rename S_UNLOAD_VIRTUAL_CARD_CLAIM to SC_UNL_V_CARDCLAIM_'||TO_CHAR(SYSDATE, 'yyMMdd');
    end;
	-- ������� �������� �� ���������
    /
    begin
	    FOR rec IN (SELECT * FROM counters) LOOP
	        execute immediate 'create sequence SC_'||rec.name||' minvalue 1 start with '||(rec.value+1)||' maxvalue 9223372036854775807 cycle nocache';
	    END LOOP;
    end;
    /
    begin
	-- ������� ������� �� ����������
	    --execute immediate 'drop table counters'; --TODO ������� ������
	-- userlog_num
	    execute immediate 'rename s_userlog_num to sc_userlog_num';
	-- systemlog_num
	    execute immediate 'rename s_systemlog_num to sc_systemlog_num';
	-- codlog_num
	    execute immediate 'rename s_codlog_num to sc_codlog_num';
	end;
    /
--� ������� 29013
	alter table BUSINESS_DOCUMENTS modify CONFIRM_STRATEGY_TYPE varchar2(18)
	/
	
--� ������� 29142
	alter table SERVICE_PROVIDERS add ( CHECK_ORDER char(1) default 0 not null ) 
	/

--� ������� 29161
	alter table PAYMENTFORMS add MOBILE_TRANSFORMATION clob
	/
	
--� ������� 29392
	alter table SECURITY add TEMP number
	/
	update SECURITY set TEMP=to_number(NOMINAL_AMOUNT) -- TODO ORA-01722: invalid number
	/
	update SECURITY set NOMINAL_AMOUNT=''
	/
	alter table SECURITY modify NOMINAL_AMOUNT number 
	/
	update SECURITY set NOMINAL_AMOUNT=TEMP
	/
	alter table SECURITY drop column TEMP
	/
	
--� ������� 29509 - 29641	
	alter table BUSINESS_DOCUMENTS add PROMO_CODE varchar2(10)
	/

--� ������� 29552
	--alter table PROFILE add ( SHOW_BANNER char(1 byte) default '1' not null )	-- ������ [����������: 14� 23s] 
	--/ �������� � ������� �������

--������������ ��������
	insert into EXTERNAL_SYSTEM values (s_external_system.nextval, '����', 'esb_system', 'ESB', 99, null ) 
	/
 
	update SECURITY s set (NOMINAL_CURRENCY) = (
		select CODE from CURRENCIES c where s.NOMINAL_CURRENCY = c.NUMERICCODE
	) 
	where NOMINAL_CURRENCY not in (select CODE from CURRENCIES)
    /
--� ������� 29704
	update PROFILE set SHOW_BANNER = '1' where SHOW_BANNER is null
	/

--� ������� 29704 ������� � ������� 29932
/*
	alter table IMACCOUNT_LINKS add (CURRENCY_ID VARCHAR2(3))
	/
	alter table IMACCOUNT_LINKS
	   add constraint FK_IMACCOUNT_CURRENCY foreign key (CURRENCY_ID)
	      references CURRENCIES (ID)
	/
*/	
--� ������� 29867 TODO ��� �� �����
/*
	alter table BUSINESS_DOCUMENTS add (
		BILLING_DOCUMENT_NUMBER varchar(100),
		PROVIDER_EXTERNAL_ID varchar2(128),
		RECIPIENT_ID integer
	)	
	/
*/

--� ������� 29932
	alter table DEPARTMENTS
	    add ( SEND_SMS_METHOD varchar2(16) null)
	/	    

	update DEPARTMENTS
	    set SEND_SMS_METHOD = 'MOBILE_BANK_ONLY'
	    where PARENT_DEPARTMENT is null
	/
	
--� ������� 29932
	alter table IMACCOUNT_LINKS add (CURRENCY_CODE varchar2(4))
	/
	
--� ������� 30038 TODO �������� ������ �� �����
	alter table FILTER_PARAMETERS_FIELDS add VALUE_CLOB CLOB
	/
	update FILTER_PARAMETERS_FIELDS set VALUE_CLOB=VALUE
	/
	alter table FILTER_PARAMETERS_FIELDS drop column VALUE
	/
	alter table FILTER_PARAMETERS_FIELDS rename column VALUE_CLOB to VALUE
	/
	
--� ������� 30179
	alter table  NOTIFICATIONS add MAIL_ID INTEGER
	/
	create index DXFK_NOTIFICATIONS_TO_MAIL on NOTIFICATIONS (
	   MAIL_ID
	)
	/
	alter table NOTIFICATIONS
	   add constraint FK_NOTIFICATIONS_TO_MAIL foreign key (MAIL_ID)
	      references MAIL (ID)
	/

--� ������� 30214 TODO �������� � �������
	--alter table EXTERNAL_SYSTEM modify ADAPTER_CODE null	
	--/
	
--� ������� 30288
	alter table UGROUP_SKINS drop constraint FK_GROUP_ID
	/

	alter table UGROUP_SKINS
	   add constraint FK_GROUP_ID foreign key (GROUP_ID)
	      references UGROUP (ID)
	      on delete cascade
	/	
	
--� ������� 30300
	alter table ORDERS add (
		STATUS varchar2(10) default 'NULL' null,
		NOTIFICATION_COUNT number(22,0) default 0 not null,
		STATUS_DISCRIPTION varchar2(255) 
	)
	/

	begin   
	    for ord in (select IS_NOTIFICATION, ID from ORDERS) loop
	        if (ord.IS_NOTIFICATION = 1) then
	            update ORDERS o
	            set o.STATUS = 'OK'
	            where o.id = ord.id;
	        else
	            update ORDERS o
	            set o.STATUS = 'NOT_SEND'
	            where o.id = ord.id;
	        end if;
	    end loop;
	end;
	/	
	alter table ORDERS drop column IS_NOTIFICATION	
	/	
	
--� ������� 30301
	alter table UGROUP_SKINS drop constraint FK_UGROUP_SKINS_SKIN_ID
	/

	alter table UGROUP_SKINS
	   add constraint FK_UGROUP_SKINS_SKIN_ID foreign key (SKIN_ID)
	      references SKINS (ID)
	      on delete cascade
	/	

--� ������� 30456
	alter table CARD_OPERATION_CATEGORIES modify ( NAME varchar2(100) )
	/

-- � �����
	update FIELD_DESCRIPTIONS
	set TYPE = 'money'
	where TYPE != 'money' and IS_SUM = 1	
--��������� ������ � ����� ������ �� ������� EXTERNAL_SYSTEM_ROUTE_INFO !!!
	