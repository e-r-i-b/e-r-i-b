--ревизия 51709
alter table CSA_CONNECTORS add (REGISTRATION_TYPE varchar2(8))
/
alter session enable parallel DML
/
update /*+parallel 64*/ CSA_CONNECTORS
set REGISTRATION_TYPE = case
    when TYPE = 'MAPI' or (TYPE = 'CSA' and CREATION_DATE >= to_date('30.06.2013', 'dd.mm.yyyy')) then 'SELF'
    else 'REMOTE'
end 
/
alter table CSA_CONNECTORS modify REGISTRATION_TYPE not null novalidate
/

