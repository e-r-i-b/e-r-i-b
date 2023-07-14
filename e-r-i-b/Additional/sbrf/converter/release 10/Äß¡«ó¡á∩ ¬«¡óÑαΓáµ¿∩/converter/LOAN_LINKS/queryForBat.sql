spool log.txt

select systimestamp from dual
/

declare
    jobn number; 
begin
	select S_PARALLEL_JOB_NUM.nextval into jobn from dual;
	
    dbms_output.enable;

    jobn:=(jobn-1)*&1;

    for j in 1..&1 loop

		jobn:=jobn+1;
        
		execute immediate 'update LOAN_LINKS set ERMB_NOTIFICATION = 1 
            where 
                id>=:min_id
            and id< :max_id' using (jobn-1)*&2, jobn*&2;
			
        commit;

     end loop;
end;
/
select  systimestamp from dual
/

spool off
exit;