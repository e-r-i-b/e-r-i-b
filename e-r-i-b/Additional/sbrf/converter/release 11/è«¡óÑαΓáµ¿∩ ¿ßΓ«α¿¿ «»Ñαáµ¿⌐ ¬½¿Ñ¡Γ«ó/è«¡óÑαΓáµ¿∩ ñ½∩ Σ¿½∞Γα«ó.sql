-- Номер ревизии: 49797
-- Комментарий: ENH043574: Доработать фильтр истории операций

update business_documents bd
   set bd.receiver_name = 'Сбербанк России'
 where bd.receiver_name is null
   and bd.form_id in (select pf.id from paymentforms pf
                       where pf.name in ('VirtualCardClaim','LoanCardProduct','LoanCardOffer',
                                         'LoanProduct','LoanOffer','LoyaltyProgramRegistrationClaim',
                                         'LoanPayment'))
/
update business_documents bd
   set bd.receiver_name = 'Пенсионный фонд РФ'
 where bd.receiver_name is null
   and bd.form_id = (select pf.id from paymentforms pf where pf.name = 'PFRStatementClaim')
/

update business_documents bd
   set bd.receiver_name = trim((select max(def.value) from document_extended_fields def 
                                 where def.name = 'to-account-name' and def.payment_id = bd.id))
 where bd.receiver_name is null
   and bd.form_id in (select pf.id from paymentforms pf where pf.name in ('IMAPayment','InternalPayment'))
/
   
update business_documents bd
   set bd.receiver_name = trim((select max(def.value) from document_extended_fields def 
                                 where def.name = 'deposit-name' and def.payment_id = bd.id))
 where bd.receiver_name is null
   and bd.form_id in (select pf.id from paymentforms pf where pf.name in ('AccountOpeningClaim','AccountOpeningClaimWithClose'))
/
   
update business_documents bd
   set bd.receiver_name = trim((select max(def.value) from document_extended_fields def 
                                 where def.name = 'buyIMAProduct' and def.payment_id = bd.id))
 where bd.receiver_name is null
   and bd.form_id = (select pf.id from paymentforms pf where pf.name = 'IMAOpeningClaim')
/