set linesize 1000
set verify off
set term off

declare
  first_idx number:=0;
  last_idx number:=0;  
  step number:=4000000;
begin

  for j in 1..10 loop

    last_id:= first_idx + step;

    insert /*+noappend*/ into document_extended_fields_pc 
      select * from document_extended_fields
        where payment_id > first_idx and payment_id <= last_idx and mod(payment_id,4)=&1;
    
    first_idx := last_idx;

    commit;
  end loop;
end;
/
exit
/