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
                                       case when def.name = 'claim-error-msg' then XMLType(nvl(def.value,'<report></report>')) end errorsXml,
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
                                   and bd.creation_date between :date_begin and :date_end
                               ) er
                       )
                 where errorsXml is not null
               )
SELECT to_char(er.changed,'dd.mm.yyyy hh24:mi:ss') "Дата запроса"
     , er.op "OP"
     , er.id "ID"
     , er.curator_id "Идентификатор промоутера\КМ\УС"
     , lpad(er.tb,4,'0') "ТБ промоутера\КМ\УС"
     , lpad(er.osb,4,'0') "ОСБ промоутера\КМ\УС"
     , lpad(er.vsp,4,'0') "ВСП промоутера\КМ\УС"
     , er.error_description "Причина ошибки"
     , er.open_in "Открыт в ВСП"
     , er.Account_Tb "ТБ счета"
     , er.Account_OCB "ОСБ счета"
     , er.Account_VSP "ВСП счета"
     , er.deposit_name "Наименование вклада"
     , er.destination_amount ||
      (case upper(er.destination_currency)
           when 'USD' then ' $'
           when 'RUB' then ' руб.'
           when 'EUR' then ' евро.'
         else er.destination_currency end) "Первоначальная сумма вклада"
     , er.sur_name "Фамилия клиента"
     , er.first_name "Имя клиента"
     , er.patr_name "Отчество клиента"
     , er.doc_ser_num "Серия номер ДУЛ"
     , er.from_resource "Номер карты или счета списания"
     , er.card_primary_account "Номер счета карты списания"
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
        order by er.document_id) er
