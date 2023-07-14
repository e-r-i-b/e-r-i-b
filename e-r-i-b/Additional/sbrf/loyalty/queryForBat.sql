set pages 0 feed off
set trimspool on
set term off
set verify off

spool off

column report_date new_value rep_date
select nvl('&2',to_char(sysdate-1,'dd.mm.yyyy')) as report_date from dual;
spool &1\report-&rep_date..csv

select 
       trim('&3' from max(sys_connect_by_path(d.value, '&3' )))||'&3'||max(d.destination_amount) fields_value 
  from (
        select 
               def.value,
               def.payment_id,
               bd.document_date,
               bd.destination_amount,
               row_number() over(partition by def.payment_id order by 
               case def.name
                   when 'login-card-hash' then 1
                   when 'deposit-type' then 2
                   when 'deposit-sub-type' then 3
                   when 'open-date' then 4                   
                   when 'period-years' then 5
                   when 'period-months' then 6
                   when 'period-days' then 7
				   when 'to-resource-currency' then 8
               end) rn
         from  business_documents bd
             , document_extended_fields def
         where 		 
           bd.execution_date >= to_date('&rep_date','dd.mm.yyyy') and bd.execution_date < to_date('&rep_date','dd.mm.yyyy')+1
           and bd.form_id in (select id from paymentforms where name in ('AccountOpeningClaim', 'AccountOpeningClaimWithClose'))
           and def.payment_id = bd.id
           and def.name in ('deposit-type','deposit-sub-type','login-card-hash','open-date','to-resource-currency','period-days','period-months','period-years')           
       ) d
start with rn = 1
connect by prior rn = rn-1
and prior payment_id = payment_id
group by payment_id;

spool off;
exit;