with available_business_doc as (select bd.id document_id,  bd.form_id, to_char(bd.admission_date, 'dd.mm.yyyy hh24:mi:ss') admission_date
                                     , bd.receiver_account account_number, bd.destination_amount, bd.destination_currency, bd.login_id
                                     , (case bd.state_code when 'EXECUTED' then 'Успех' when 'REFUSED' then 'Ошибка' end) operation_state
                                     , (case bd.state_code when 'REFUSED' then nvl(bd.ground, bd.state_description) end) error_msg
                                  from business_documents bd
                                 where bd.form_id in (:acc_opening,:acc_opening_with_close,:change_interest_dest)
                                   and bd.state_code in ('EXECUTED','REFUSED')
                                   and bd.creation_date >= to_date(:date_begin,'dd.mm.yyyy') 
                                   and bd.creation_date < to_date(:date_end,'dd.mm.yyyy'))
select d.admission_date "Дата измен.уплаты процентов"
     , d.promoter_id "Идентификатор промоутера"
     , u.manager_id "Идентификатор КМ"
     , nvl(al.office_tb,d.account_tb) "ТБ"
     , nvl(al.office_osb,d.account_osb) "ОСБ"
     , nvl(al.office_vsp,d.account_vsp) "ВСП"
     , nvl(al.account_name, d.deposit_name) "Наименование вклада"
     , coalesce(al.account_number, d.account_number, d.interest_account_number) "Номер счета вклада "
     , d.operation_state "Статус операции"
     , d.error_msg "Текст ошибки"
     , case lower(d.destination_source)
            when 'card' then 'перечисление процентов на счет банковской карты'
            when 'account' then 'капитализация процентов на счете по вкладу'
       end "Вариант уплаты"
     , case lower(d.destination_source) when 'card' then 
            substr(d.destination_source_card,1,6)||'******'||substr(d.destination_source_card,13,4) end "Номер карты зачисл.процентов"
     , case lower(d.destination_source) when 'account' then nvl(al.account_number, d.account_number) end "Номер счета зачисл.процентов"
     , u.sur_name "Фамилия клиента"
     , u.first_name "Имя клиента"
     , u.patr_name "Отчество клиента"
     , doc.doc_series||doc.doc_number "Серия номер ДУЛ"
     , d.logon_card_number "Номер карты входа в СБОЛ"
  from (select d.admission_date, d.promoter_id, d.operation_state, d.error_msg, d.destination_source
             , d.account_id, d.destination_source_card, d.logon_card_number, d.account_tb, d.account_osb
             , d.account_vsp, d.deposit_name, d.login_id, d.account_number, d.interest_account_number
          from (select dense_rank() over(partition by d.document_id order by d.def_id) row_num  
                     , d.admission_date, d.operation_state, d.error_msg, d.account_number, d.login_id
                     , max(promoter_id) over(partition by document_id) promoter_id
                     , max(account_id) over(partition by document_id) account_id
                     , max(destination_source) over(partition by document_id) destination_source
                     , max(destination_source_card) over(partition by document_id) destination_source_card
                     , max(logon_card_number) over(partition by document_id) logon_card_number
                     , max(account_tb) over(partition by document_id) account_tb
                     , max(account_osb) over(partition by document_id) account_osb
                     , max(account_vsp) over(partition by document_id) account_vsp
                     , max(deposit_name) over(partition by document_id) deposit_name
					 , max(interest_account_number) over(partition by document_id) interest_account_number
                  from (select abd.admission_date, abd.document_id, abd.operation_state, abd.account_number, abd.error_msg
                             , abd.login_id, def.id def_id
                             , case when def.name = 'promoter-id' then def.value end promoter_id
                             , case when def.name = 'account-id' then def.value end account_id
                             , case when def.name in ('interest-destination-source','percentTransfer-source') then def.value 
                               end destination_source
                             , case when def.name in ('interest-card-number','percentCard-number') then def.value 
                               end destination_source_card
                             , case when def.name = 'logon-card-number' then def.value end logon_card_number
                             , case when def.name = 'region' then def.value end account_tb
                             , case when def.name = 'branch' then def.value end account_osb
                             , case when def.name = 'officeVSP' then def.value end account_vsp
                             , case when def.name = 'deposit-name' then def.value end deposit_name
							 , case when def.name = 'account-number' then def.value end interest_account_number
                          from available_business_doc abd
                             , document_extended_fields def
                         where def.payment_id = abd.document_id
                           and def.name in ('account-id','promoter-id','interest-destination-source','interest-card-number',
                                            'logon-card-number','percentCard-number','percentTransfer-source',
                                            'region','branch','officeVSP','deposit-name','account-number')) d) d
          where d.row_num = 1) d left join account_links al on al.id = d.account_id
     , users u
     , documents doc
 where u.login_id = d.login_id
   and upper(u.status) = 'A'
   and doc.person_id = u.id
   and doc.doc_main = '1'
order by d.admission_date desc;