declare
    codVal varchar2(512);
    tmp varchar2(512);
begin
    dbms_output.enable(1000000);
    for rec in (
        select 
            bd.id, 
            regexp_replace(bd.external_id, '(^.{1,})(\|)(.{1,}$)', '\1') as external_id,
            bd.external_id as external_id_orig,
            bd.changed - interval '1' minute as st_date, 
            bd.changed + interval '10' minute as end_date,
            d.tb
        from business_documents bd
        inner join departments d on d.id=bd.department_id and tb in (40, 38, 99, 70, 13, 60)       
        where 
            bd.changed>=to_date('01.06.11', 'dd.mm.yy') and
            bd.state_code='DISPATCHED' and
            bd.kind='P' and 
            bd.external_id not like '%@%' and
            bd.external_id not like '%iqwave%' 
        order by tb, operation_date 
    ) loop
        begin
            select extractvalue(xmltype(coalesce(MESSAGE_DEMAND, to_clob('<a>0</a>'))), '/*/messageDate') into codVal
            from codlog 
            where 
                application='Gate' and
                start_date >=rec.st_date and
                start_date <=rec.end_date and
                lower(message_demand_id) = rec.external_id and
                rownum <2;
            codVal:='@'||codVal;
        exception
            when no_data_found then
                codVal:='';
        end;
        tmp:=regexp_replace(rec.external_id_orig, '(^.{1,})(\|)(.{1,}$)', '\1'||codVal||'\2\3');
        dbms_output.put_line(rec.tb||' '||rec.st_date||' '||rec.id||' '||rec.external_id||' '||tmp);
    end loop;
end;