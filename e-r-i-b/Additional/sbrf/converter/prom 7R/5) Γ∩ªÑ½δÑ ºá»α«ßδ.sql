--� ������� 28714
	create index I_OPERATION_UID
		on CODLOG(OPERATION_UID)	
	/
	
--� ������� 29013 --[����������: 1� 11� 17s] 
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
	--30 ���.
--� ������� 29829
	create index BUSDOC_IND_LOGIN_CR_DATE on BUSINESS_DOCUMENTS (
	   CREATION_DATE DESC,
	   LOGIN_ID ASC
	)
	/
	drop index IND_CR_DATE
	/

--� ������� 29867
	create index I_PROVIDER_EXTERNAL_ID on BUSINESS_DOCUMENTS (
	   PROVIDER_EXTERNAL_ID ASC
	)
	/
	create index  BUSINESS_DOCUMENTS_I_REC_ID on BUSINESS_DOCUMENTS (
	   RECIPIENT_ID
	)
	/
	
--� ������� 29909 �� �������� �� ����
/*
	create index INDEX_DESCKEY_SUCCESS
		on USERLOG(DESCRIPTION_KEY, SUCCESS) online
	/
*/

--� ������� 27938
	alter table ACCOUNT_LINKS add SHOW_IN_MOBILE char(1) default '1' not null --������ [����������: 8� 48s] 
	/
	alter table CARD_LINKS add SHOW_IN_MOBILE char(1) default '1' not null -- [����������: 5� 21s] 
	/

--� ������� 28176
	alter table USERSLIMIT add GROUP_RISK varchar2(10) default 'LOW' not null --������ [����������: 10� 54s] 
	/
	
--� ������� 29552
	alter table PROFILE add ( SHOW_BANNER char(1 byte) default '1' not null )	-- ������ [����������: 14� 23s] 
	/	
