set pages 0 feed off
set trimspool on
set term off
set verify off
set linesize 200

spool off

/*Даты начала и окончания отчетного периода и ИД форм открытия и закрытия вкладов*/
column report_begin_date new_value rep_begin_date
column report_end_date new_value rep_end_date
column acc_opening_form_id new_value acc_opening
column acc_opening_with_close_form_id new_value acc_opening_with_close
column acc_closing_form_id new_value acc_closing

select nvl('&2', to_char(trunc(sysdate,'mm'), 'DD.MM.YYYY')) as report_begin_date from dual;
select nvl('&3', to_char(last_day(sysdate), 'DD.MM.YYYY')) as report_end_date from dual;
select pf.id as acc_opening_form_id from paymentforms pf where pf.name in ('AccountOpeningClaim');
select pf.id as acc_opening_with_close_form_id from paymentforms pf where pf.name in ('AccountOpeningClaimWithClose');
select pf.id as acc_closing_form_id from paymentforms pf where pf.name in ('AccountClosingPayment');

spool &1\&7-&rep_end_date..csv

with available_business_doc as (select bd.id document_id,  bd.form_id, to_char(bd.admission_date, 'dd.mm.yyyy hh24:mi:ss') admission_date
                                     , bd.receiver_account account_number, bd.destination_amount, bd.destination_currency, bd.login_id
                                     , (case bd.form_id when &acc_closing then 'закрытие ' else 'открытие ' end ||'вклада') operation_type
                                  from business_documents bd
                                 where bd.form_id in ('&acc_opening','&acc_opening_with_close','&acc_closing')
                                   and bd.state_code = 'EXECUTED'
                                   and bd.creation_date >= to_date('&rep_begin_date','dd.mm.yyyy') 
                                   and bd.creation_date < to_date('&rep_end_date','dd.mm.yyyy'))
select replace('"Дата проведения операции","ТБ","ОСБ","ВСП","Клиент","Дата рождения","Тип операции","Срок вклада",'||
               '"Наименование вклада","Номер счета вклада онлайн","Валюта вклада","Первоначальная сумма вклада"',',',nvl('&4',','))
  from dual
union all
select *
  from (select replace('"'||d.admission_date||'"#'|| 
		               '"'||l.last_logon_card_tb||'"#'|| 
		               '"'||l.last_logon_card_osb||'"#'|| 
		               '"'||l.last_logon_card_vsp||'"#'|| 
		               '"'||u.sur_name||' '||u.first_name||' '||u.patr_name||'"#'|| 
		               '"'||to_char(u.birthday, 'dd.mm.yyyy')||'"#'|| 
		               '"'||d.operation_type||'"#'|| 
		               '"'||d.period||'"#'|| 
		               '"'||d.deposit_name||'"#'|| 
		               '"'||d.account_number||'"#'|| 
		               '"'||d.currency||'"#'|| 
		               '"'||d.destination_amount||'"','#',nvl('&4',','))
		  from (select document_id, operation_type, admission_date, account_number, destination_amount, login_id
		             , (case upper(destination_currency) 
		                  when 'USD' then '$'
		                  when 'RUB' then 'руб.'
		                  when 'EUR' then 'евро.' 
		                else destination_currency end) currency
		             , account_tb, account_vsp, deposit_name, period
		          from (select dense_rank() over(partition by d.document_id order by d.def_id) row_num
		                     , d.document_id, d.def_id, d.operation_type, d.admission_date, d.account_number
		                     , d.destination_amount, d.destination_currency, d.login_id
		                     , max(account_tb) over(partition by document_id) account_tb
		                     , max(account_vsp) over(partition by document_id) account_vsp
		                     , max(deposit_name) over(partition by document_id) deposit_name
		                     , nvl(max(period_years) over(partition by document_id),0) ||' лет, '||
		                       nvl(max(period_months) over(partition by document_id),0) ||' месяцев, '||
		                       nvl(max(period_days) over(partition by document_id),0) ||' дней' period
		                  from (select abd.document_id, def.id def_id, abd.operation_type, abd.admission_date, abd.account_number
		                             , abd.destination_amount, abd.destination_currency, abd.login_id
		                             , case when def.name = 'region' then def.value end account_tb
		                             , case when def.name = 'branch' then def.value end account_osb
		                             , case when def.name = 'officeVSP' then def.value end account_vsp
		                             , case when def.name = 'deposit-name' then def.value end deposit_name
		                             , case when def.name = 'period-years' then def.value end period_years
		                             , case when def.name = 'period-months' then def.value end period_months
		                             , case when def.name = 'period-days' then def.value end period_days
		                          from available_business_doc abd left join document_extended_fields def on def.payment_id = abd.document_id 
																	and def.name in ('deposit-name','region','branch','officeVSP','period-years','period-months','period-days')) d) d
		         where d.row_num = 1) d
		     , logins l
		     , users u
		 where l.id = d.login_id
		   and u.login_id = d.login_id
		   and u.status = 'A'
		   and ltrim(nvl(account_tb,l.last_logon_card_tb),'0') = ltrim('&5','0')
		   and ltrim(nvl(account_vsp,l.last_logon_card_vsp),'0') = ltrim('&6','0')
		order by d.login_id, d.admission_date desc);

spool off;
exit;