declare
		stval number:=1;
        stmt varchar2(4000):='';
		step number:= 300000;
begin
	select s_job_12_release.nextval into stval from dual;
	stmt:='update csa_profiles set incognito=''0'' where id= :1';
	
	for i in (stval-1)*step..(stval)*step-1 loop
		execute immediate stmt using i;
		commit;
	end loop;
end;
/
exit
/