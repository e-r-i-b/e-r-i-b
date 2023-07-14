--В ревизии 28420
	begin
	    for rec in (
	                    select id from (
	                        select irj.*, (count(*) over (partition by session_id))as cnt, case when id=min(id) over (partition by session_id) then 'true' else 'false' end as is_min_id 
	                        from input_register_journal irj
	                    )
	                    where cnt > 1 and  is_min_id='false'
	    ) 
	    loop
	        delete from input_register_journal where id=rec.id;
	    end loop;
	end;
	/
	drop index SES_CLOSE_REGISTER_JOURNAL
	/
	create unique index INDEX_REGISTER_JOURNAL_SESSION on INPUT_REGISTER_JOURNAL (
	    SESSION_ID ASC
	)
	/

    -- TODO удаление дублей
	 create unique index UK_LOGIN_GROUP_RISK on USERSLIMIT (
	   LOGIN_ID ASC,
	   GROUP_RISK ASC
	)
	/

--В ревизии 30296	
	create index I_USERS_DEP_STATE_CR_TYPE
		on USERS(STATUS ASC, DEPARTMENT_ID ASC, CREATION_TYPE ASC)
	/
	create index I_DEPARTMENTS_OFFICE
		on DEPARTMENTS(OFFICE ASC)
	/
	create index I_DEPARTMENTS_OSB
		on DEPARTMENTS(OSB ASC)
	/
	create index I_DEPARTMENTS_TB
		on DEPARTMENTS(TB ASC)
	/
	