set pages 0 feed off
set trimspool on
set term off
set verify off
set linesize 300

spool off

/*Даты начала и окончания отчетного периода*/
column report_begin_date new_value rep_begin_date
column report_end_date new_value rep_end_date

select nvl('&2', to_char(trunc(sysdate,'mm'), 'DD.MM.YYYY')) as report_begin_date from dual;
select nvl('&3', to_char(last_day(sysdate), 'DD.MM.YYYY')) as report_end_date from dual;

spool &1\&5-&rep_end_date..csv

with errors as (select * 
                  from (
                        SELECT document_id, errorsXml
                             , creation_date, changed, receiver_account, destination_amount
                             , destination_currency, login_id
                             , max(Account_Tb) over(partition by er.document_id) Account_Tb
                             , max(Account_OCB) over(partition by er.document_id) Account_OCB
                             , max(Account_VSP) over(partition by er.document_id) Account_VSP
                             , max(deposit_name) over(partition by er.document_id) deposit_name
                             , max(from_resource) over(partition by er.document_id) from_resource                             
                          FROM (
                                select bd.id document_id, bd.creation_date, bd.changed, bd.receiver_account,
                                       bd.destination_amount, bd.destination_currency, bd.login_id,
                                       case when def.name = 'claim-error-msg' then XMLType(def.value) end errorsXml,
                                       case when def.name = 'region' then def.value end Account_Tb,
                                       case when def.name = 'branch' then def.value end Account_OCB,
                                       case when def.name = 'officeVSP' then def.value end Account_VSP,
                                       case when def.name = 'deposit-name' then def.value end deposit_name,
                                       case when def.name = 'from-resource' then def.value end from_resource
                                  from paymentforms pf
                                       join business_documents bd on bd.form_id = pf.id
                                       join document_extended_fields def on def.payment_id = bd.id
                                 where pf.name in ('AccountOpeningClaim','AccountOpeningClaimWithClose')
                                   and bd.state_code in ('EXECUTED', 'REFUSED')
                                   and def.name in ('claim-error-msg','region','branch','officeVSP','deposit-name','from-resource')
                                   and to_date(nvl('&rep_begin_date',trunc(sysdate,'mm')),'dd.mm.yyyy') <= trunc(bd.changed)
								   and trunc(bd.changed) <= to_date(nvl('&rep_end_date',trunc(sysdate)),'dd.mm.yyyy')
                               ) er
                       )
                 where errorsXml is not null
               )
select replace('"Дата","OP","ID","Идентификатор промоутера\КМ\УС","ТБ промоутера\КМ\УС","ОСБ промоутера\КМ\УС","ВСП промоутера\КМ\УС",'||
               '"Причина ошибки","Открыт в ВСП (промоутер\УС\счет списания или карта входа)","ТБ счета","ОСБ счета","ВСП счета",'||
               '"Наименование вклада","Первоначальная сумма вклада","Фамилия клиента","Имя клиента","Отчество клиента",'||
               '"Серия номер ДУЛ","Номер карты или счета списания","Номер счета карты списания"',',',nvl('&4',','))  
  from dual
union all
SELECT replace('"'||to_char(er.changed,'dd.mm.yyyy hh24:mi:ss')||'"#'||
               '"'||er.op ||'"#'||
               '"'||er.id ||'"#'||
               '"'||er.curator_id ||'"#'||
               '"'||lpad(er.tb,4,'0') ||'"#'||
               '"'||lpad(er.osb,4,'0') ||'"#'||
               '"'||lpad(er.vsp,4,'0') ||'"#'||
               '"'||er.error_description||'"#'||
               '"'||er.open_in||'"#'||
               '"'||er.Account_Tb ||'"#'||
               '"'||er.Account_OCB ||'"#'||
               '"'||er.Account_VSP ||'"#'||
               '"'||er.deposit_name||'"#'||
               '"'||er.destination_amount||
                    (case upper(er.destination_currency) 
                         when 'USD' then ' $'
                         when 'RUB' then ' руб.'
                         when 'EUR' then ' евро.' 
                     else er.destination_currency end)||'"#'||
               '"'||er.sur_name ||'"#'||
               '"'||er.first_name ||'"#'||
               '"'||er.patr_name ||'"#'||
               '"'||er.doc_ser_num ||'"#'||
               '"'||er.from_resource||'"#'||
			   '"'||er.card_primary_account||'"','#',nvl('&4',','))
  FROM (select er.changed, er.receiver_account, er.deposit_name, er.destination_amount
             , er.destination_currency, u.sur_name, u.first_name, u.patr_name, d.doc_series||d.doc_number doc_ser_num
             , er.from_resource, er.Account_Tb, er.Account_OCB, er.Account_VSP
             , extractValue(er.errorsXml,'//op') op
             , extractValue(er.errorsXml,'//id') id
             , extractValue(er.errorsXml,'//curator-id') curator_id
             , extractValue(er.errorsXml,'//tb') tb
             , extractValue(er.errorsXml,'//osb') osb
             , extractValue(er.errorsXml,'//vsp') vsp
             , extractValue(er.errorsXml,'//description') error_description
             , extractValue(er.errorsXml,'//open-in') open_in
			 , extractValue(er.errorsXml,'//card-primary-account') card_primary_account
         from errors er
           join users u on u.login_id = er.login_id
           join documents d on d.person_id =  u.id
         where d.doc_main = '1'
        order by er.document_id) er;

spool off;
exit;