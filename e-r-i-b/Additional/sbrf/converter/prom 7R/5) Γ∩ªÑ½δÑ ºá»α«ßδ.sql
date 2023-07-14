--В ревизии 28714
	create index I_OPERATION_UID
		on CODLOG(OPERATION_UID)	
	/
	
--В ревизии 29013 --[Выполнение: 1ч 11м 17s] 
	update BUSINESS_DOCUMENTS set confirm_strategy_type = 'sms'
		where confirm_strategy_type = 'sms '
	/
	
    declare
        i number;
    begin
        i:=0;
        for rec in (
            select bd.id            
            from business_documents bd        
            where kind in ('H','P') and state_code = 'TEMPLATE'
        ) loop
            update BUSINESS_DOCUMENTS set STATE_CODE = 'WAIT_CONFIRM_TEMPLATE' where id=rec.id and STATE_CODE = 'TEMPLATE';
            i:=i+1;
            if ( i > 10000 ) then 
                i:=0;
                commit;
            end if;
        end loop;
        commit;
    end;
	--30 мин.
--В ревизии 29829
	create index BUSDOC_IND_LOGIN_CR_DATE on BUSINESS_DOCUMENTS (
	   CREATION_DATE DESC,
	   LOGIN_ID ASC
	)
	/
	drop index IND_CR_DATE
	/

--В ревизии 29867
	create index I_PROVIDER_EXTERNAL_ID on BUSINESS_DOCUMENTS (
	   PROVIDER_EXTERNAL_ID ASC
	)
	/
	create index  BUSINESS_DOCUMENTS_I_REC_ID on BUSINESS_DOCUMENTS (
	   RECIPIENT_ID
	)
	/
	
--В ревизии 29909 не проносим на пром
/*
	create index INDEX_DESCKEY_SUCCESS
		on USERLOG(DESCRIPTION_KEY, SUCCESS) online
	/
*/

--В ревизии 27938
	alter table ACCOUNT_LINKS add SHOW_IN_MOBILE char(1) default '1' not null --долгий [Выполнение: 8м 48s] 
	/
	alter table CARD_LINKS add SHOW_IN_MOBILE char(1) default '1' not null -- [Выполнение: 5м 21s] 
	/

--В ревизии 28176
	alter table USERSLIMIT add GROUP_RISK varchar2(10) default 'LOW' not null --долгий [Выполнение: 10м 54s] 
	/
	
--В ревизии 29552
	alter table PROFILE add ( SHOW_BANNER char(1 byte) default '1' not null )	-- долгий [Выполнение: 14м 23s] 
	/	
