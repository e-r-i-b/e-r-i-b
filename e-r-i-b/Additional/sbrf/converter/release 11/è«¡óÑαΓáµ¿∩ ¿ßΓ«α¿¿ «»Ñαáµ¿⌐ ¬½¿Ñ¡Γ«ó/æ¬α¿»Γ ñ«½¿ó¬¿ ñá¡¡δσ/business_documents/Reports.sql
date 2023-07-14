set linesize 1000
set verify off
set term off

declare
    first_idx number:=0;
    last_idx number:=0;  
	step number:=2000000;	--кол-во id в одной обработке, одного потока
begin

	first_idx:=0;
	
    for j in 1..20 loop
	
        last_idx := first_idx + step;
		
		insert /*+noappend*/ into business_documents_p 
		    select 
		       ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID,  nvl(STATE_CODE, 'ERROR'), nvl(STATE_DESCRIPTION, '!'),
			   CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, COMMISSION_CURRENCY,
			   PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT,
			   OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, 
			   DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, SYSTEM_NAME, SUMM_TYPE, CONFIRM_STRATEGY_TYPE, 
			   ADDITION_CONFIRM, CONFIRM_EMPLOYEE, 
			   case when KIND='P' and OPERATION_UUID is not null then OPERATION_UUID else OPERATION_UID end as OPERATION_UID,
			   PROMO_CODE, BILLING_DOCUMENT_NUMBER, PROVIDER_EXTERNAL_ID, RECIPIENT_ID, ACCESS_AUTOPAY_SCHEMES, SESSION_ID, 
			   CODE_ATM, CREATED_EMPLOYEE_LOGIN_ID, CONFIRMED_EMPLOYEE_LOGIN_ID, LOGIN_TYPE
		    from business_documents
		    where id > first_idx and id <= last_idx and mod(id,4)=&1;

        first_idx :=last_idx;
		
        commit;
    end loop;
end;
/
exit
/