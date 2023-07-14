create or replace package CONVERT_TEMPLATES_11R as

  --Возвращает тип операции по номеру формы:
  function getOperation(
    formId in number,
    recipientId in number
  ) return varchar2;

  --Возвращает тип операции по номеру формы:
  function getKind(
    formId in number,
    recipientId in number
  ) return varchar2;

  --для оплаты между моими счетами, конверсия
  function getClass(
    paymentId       in number,
    formId          in number,
    payerAccount    in varchar2,
    receiverAccount in varchar2
  ) return varchar2;

  --конвертация шаблонов платежей
  procedure convertTemplates(
    streamNumber in number          --номер потока конвертации
  );
  
  --получение нового имени поля для доп. реквизитов
  function getFieldName(
    name     in varchar2,
    formType in varchar2
  ) return varchar2;

  --актуализация документов, если данные по ним изменились после их переливки в новую структуру
  procedure sinchronizeDocuments(
    startId   in number,            --ID документа, с котороко начинаем актуализацию
    cuntSteps in number,            --кол-во шагов
    stepSize  in number             --размер шага
  );
  
  --актуализация документов по результатам работы триггера
  procedure sinchronizeDocTrigger(
    startId   in number,            --ID документа, с котороко начинаем актуализацию
    cuntSteps in number,            --кол-во шагов
    stepSize  in number             --размер шага
  );
     
end CONVERT_TEMPLATES_11R;
/
create or replace package body CONVERT_TEMPLATES_11R as

  CONVERT_CURRENCY_TRANSFER constant number:=2;    --ConvertCurrencyPayment
  INTERNAL_TRANSFER         constant number:=3;    --InternalPayment
  LOAN_PAYMENT              constant number:=82;   --LoanPayment
  EXTERNAL_PAYMENT_TRANSFER constant number:=6;    --RurPayJurSB
  SECURITIES_TRANSFER_CLAIM constant number:=84;   --SecuritiesTransferClaim
  INDIVIDUAL_TRANSFER       constant number:=7;    --RurPayment
  JURIDICAL_TRANSFER        constant number:=107;  --JurPayment
  IMA_PAYMENT               constant number:=110;  --IMAPayment

  RESOURCE_TYPE_CARD              constant varchar2(1):= 'C';
  RESOURCE_TYPE_ACCOUNT           constant varchar2(1):= 'A';
  RESOURCE_TYPE_IM_ACCOUNT        constant varchar2(1):= 'I';
  --RESOURCE_TYPE_EXTERNAL_CARD     constant varchar2(1):= 'E';

  --Возвращает тип операции по номеру формы:
  function getOperation(formId in number, recipientId in number) return varchar2 is
  begin
    return case
      when formId=CONVERT_CURRENCY_TRANSFER then 'CONVERT_CURRENCY_TRANSFER'  --'ConvertCurrencyPayment'
      when formId=INTERNAL_TRANSFER then 'INTERNAL_TRANSFER'                  --'InternalPayment'
      when formId=LOAN_PAYMENT then 'LOAN_PAYMENT'                            --'LoanPayment'
      when (formId=EXTERNAL_PAYMENT_TRANSFER and recipientId is null) then 'EXTERNAL_PAYMENT_SYSTEM_TRANSFER'      --'RurPayJurSB'
      when (formId=EXTERNAL_PAYMENT_TRANSFER and recipientId is not null) then 'INTERNAL_PAYMENT_SYSTEM_TRANSFER'  --'RurPayJurSB'
      when formId=SECURITIES_TRANSFER_CLAIM then 'SECURITIES_TRANSFER_CLAIM'  --'SecuritiesTransferClaim'
      when formId=INDIVIDUAL_TRANSFER then 'INDIVIDUAL_TRANSFER'              --'RurPayment'
      when formId=JURIDICAL_TRANSFER then 'JURIDICAL_TRANSFER'                --'JurPayment'
      when formId=IMA_PAYMENT then 'IMA_PAYMENT'                              --'IMAPayment'
      else 'JURIDICAL_TRANSFER'
    end;
  end;

  --Возвращает тип операции по номеру формы:
  function getKind(formId in number, recipientId in number) return varchar2 is
  begin
    return case
      when formId=CONVERT_CURRENCY_TRANSFER then 'A'  --'ConvertCurrencyPayment'
      when formId=INTERNAL_TRANSFER then 'A'          --'InternalPayment'
      when formId=LOAN_PAYMENT then 'D'               --'LoanPayment'
      when formId=EXTERNAL_PAYMENT_TRANSFER and recipientId is null then 'C'    --'RurPayJurSB'
      when formId=EXTERNAL_PAYMENT_TRANSFER and recipientId is not null then 'C'--'RurPayJurSB'
      when formId=SECURITIES_TRANSFER_CLAIM then 'E'  --'SecuritiesTransferClaim'
      when formId=INDIVIDUAL_TRANSFER then 'B'        --'RurPayment'
      when formId=JURIDICAL_TRANSFER then 'B'         --'JurPayment'
      when formId=IMA_PAYMENT then 'A'                --'IMAPayment'
      else 'B'
    end;
  end;
  
  --мапинг доп. реквизитов
  function getFieldName(name in varchar2, formType in varchar2) return varchar2 is
  begin
    return case
      when name='additional-info' then 'additionalInfo'
      when name='agreement-number' then 'agreementNumber'
      when name='AUTHORIZE_CODE' then 'authorizeCode'
      when name='bank-details' then 'bankDetails'
      when name='convertion-rate' then 'course'
      when name='corr-depo-account' then 'corrDepoAccount'
      when name='corr-depo-account-owner' then 'corrDepoAccountOwner'
      when name='corr-depositary' then 'corrDepositary'
      when name='count-of-sheet' then 'countOfSheet'
      when name='currency-code' then 'currencyCode'
      when name='debt-rec-number' then 'debtRecNumber'
      when name='delivery-type' then 'deliveryType'
      when name='depo-account' then 'depoAccount'
      when name='depo-external-id' then 'depoExternalId'
      when name='depo-link-id' then 'depoLinkId'
      when name='depositor' then 'depositor'
      when name='destination-amount' then 'buyAmount'
      when name='destination-amount-currency' then 'buyAmountCurrency'
      when name='division-number' then 'divisionNumber'
      when name='division-type' then 'divisionType'
      when name='exact-amount' then 'exactAmount'
      when name='external-card-number' then 'externalCardNumber'
      when name='from-account-name' then 'fromAccountName'
      when name='from-account-type' then 'fromAccountType'
      when name='from-resource' then 'fromResource'
      when name='from-resource-currency' then 'fromResourceCurrency'
      when name='from-resource-link' then 'fromResourceLink'
      when name='from-resource-type' then 'fromResourceType'
      when name='ground' then 'ground'
      when name='inside-code' then 'insideCode'
      when name='InternalPayment' then 'ConvertCurrencyPayment'
      when name='is-card-transfer' then 'isCardTransfer'
      when name='is-external-card' then 'externalCard'
      when name='is-our-bank' then 'isOurBank'
      when name='loan' then 'loan'
      when name='loan-account-number' then 'loanAccountNumber'
      when name='loan-amount' then 'loanAmount'
      when name='loan-currency' then 'loanCurrency'
      when name='loan-external-id' then 'loanExternalId'
      when name='loan-link-id' then 'loanLinkId'
      when name='loan-name' then 'loanName'
      when name='loan-type' then 'loanType'
      when name='manager-fio' then 'managerFIO'
      when name='message-to-receiver' then 'messageToReceiver'
      when name='message-to-receiver-status' then 'messageToReceiverStatus'
      when name='mobile-number' then 'externalPhoneNumber'
      when name='name-on-bill' then 'nameOnBill'
      when name='nominal-amount' then 'nominalAmount'
      when name='office' then 'office'
      when name='operation-code' then 'operationCode'
      when name='operation-desc' then 'operationDesc'
      when name='operation-initiator' then 'operationInitiator'
      when name='operation-reason' then 'operationReason'
      when name='operation-sub-type' then 'operationSubType'
      when name='operation-type' then 'operationType'
      when name='payer-account' then 'fromAccountSelect'
      when name='person-region-name' then 'personRegionName'
      when name='provider-billing-code' then 'billingCode'
      when name='reason-for-additional-confirm' then 'reasonForAdditionalConfirm'
      when name='receiver-account-internal' then 'receiverAccountInternal'
      when name='receiver-address' then 'receiverAddress'
      when name='receiver-bic' then 'receiverBIC'
      when name='receiver-cor-account' then 'receiverCorAccount'
      when name='receiver-description' then 'receiverDescription'
      when name='receiver-first-name' then 'receiverFirstName'
      when name='receiverId' then 'receiverId'
      when name='receiver-inn' then 'receiverINN'
      when name='receiver-kpp' then 'receiverKPP'
      when name='receiver-name' then 'receiverName'
      when name='receiver-patr-name' then 'receiverPatrName'
      when name='receiver-phone-number' then 'phoneNumber'
      when name='receiver-subtype' then 'receiverSubType'
      when name='receiver-surname' then 'receiverSurname'
      when name='receiver-type' then 'receiverType'
      when name='recipient' then 'recipient'
      when name='registration-number' then 'registrationNumber'
      when name='resource-currency' then 'resourceCurrency'
      when name='security-count' then 'securityCount'
      when name='security-name' then 'securityName'
      when name='state' then 'state'
      when name='storage-method' then 'storageMethod'
      when name='tarif-plan-code-type' then 'tarifPlanCodeType'
      when name='tax-document-date' then 'taxDocumentDate'
      when name='tax-document-number' then 'taxDocumentNumber'
      when name='tax-ground' then 'taxGround'
      when name='tax-kbk' then 'taxKBK'
      when name='tax-okato' then 'taxOKATO'
      when name='tax-payment' then 'taxPayment'
      when name='tax-period' then 'taxPeriod'
      when name='tax-period1' then 'taxPeriod1'
      when name='tax-period2' then 'taxPeriod2'
      when name='tax-period3' then 'taxPeriod3'
      when name='tax-status' then 'taxStatus'
      when name='tax-type' then 'taxType'
      when name='to-account-name' then 'toAccountName'
      when name='to-account-type' then 'toAccountType'
      when name='to-resource' then 'toResource'
      when name='to-resource-type' then 'toResourceType'
      when name='value-of-exact-amount' then 'valueOfExactAmount'
      when (name='amount' and formType='LOAN_PAYMENT') then 'amount'
      when (name='amount' and formType!='LOAN_PAYMENT') then 'sellAmount'
      when (name='amount-currency' and formType='LOAN_PAYMENT') then 'amountCurrency'
      when (name='amount-currency' and formType!='LOAN_PAYMENT') then 'sellAmountCurrency'
      when (name='receiver-account' and formType='IMA_PAYMENT') then 'toAccountSelect'
      when (name='receiver-account' and formType!='IMA_PAYMENT') then 'receiverAccount'
      when (name='receiver-bank' and formType='INDIVIDUAL_TRANSFER') then 'bank'
      when (name='receiver-bank' and formType!='INDIVIDUAL_TRANSFER') then 'receiverBankName'
      else name
    end;
  end;
      
  --тип счета
  function getResourceType(accountRes in varchar2, formId in number) return varchar is
    acclen number:=length(accountRes);
    accBal varchar2(5);
  begin
    if (accountRes is null) then
      return null;
    end if;

    if (formId != IMA_PAYMENT) then
      if (acclen > 19) then
        return RESOURCE_TYPE_ACCOUNT;
      else
        return RESOURCE_TYPE_CARD;
      end if;
    else
      if (acclen > 19) then
        if (substr(accountRes,1,3) = '203') then
          return RESOURCE_TYPE_IM_ACCOUNT;
        else
          return RESOURCE_TYPE_ACCOUNT;
        end if;
      else
        return RESOURCE_TYPE_CARD;
      end if;
    end if;

    return null;
  end;

  --признак налогового платежа
  function isTaxPayment(paymentId number) return varchar2 is
    res varchar2(8);
  begin
    select nvl(lower(value), 'false') into res from document_extended_fields where payment_id=paymentId and name='tax-payment';
    return res;
  exception
    when NO_DATA_FOUND then
      return 'false';
  end;

  --признак внутребанковской операции
  function isOurBank(paymentId number) return varchar2 is
    res varchar2(8);
  begin
    select nvl(lower(value), 'false') into res from document_extended_fields where payment_id=paymentId and name='tax-payment';
    return res;
  exception
    when NO_DATA_FOUND then
      return 'false';
  end;

  --для оплаты между моими счетами, конверсия
  function getClass(
    paymentId       in number,
    formId          in number,
    payerAccount    in varchar2,
    receiverAccount in varchar2
  ) return varchar2 is --класс операции
    chargeOffResourceType   varchar2(32);
    destinationResourceType varchar2(32);
  begin

    chargeOffResourceType   := getResourceType(payerAccount, formId);
    destinationResourceType := getResourceType(receiverAccount, formId);

    if ( formId=CONVERT_CURRENCY_TRANSFER and formId=INTERNAL_TRANSFER ) then
      --для оплаты между моими счетами, конверсия
      if (chargeOffResourceType is null) then
        return null;
      end if;

      if (chargeOffResourceType = RESOURCE_TYPE_CARD and destinationResourceType = RESOURCE_TYPE_CARD) then
        return 'com.rssl.phizic.gate.payments.InternalCardsTransfer';
      end if;

      if (chargeOffResourceType = RESOURCE_TYPE_CARD and destinationResourceType = RESOURCE_TYPE_ACCOUNT) then
        return 'com.rssl.phizic.gate.payments.CardToAccountTransfer';
      end if;

      if (chargeOffResourceType = RESOURCE_TYPE_ACCOUNT and destinationResourceType = RESOURCE_TYPE_CARD) then
        return 'com.rssl.phizic.gate.payments.AccountToCardTransfer';
      end if;

      if (chargeOffResourceType = RESOURCE_TYPE_ACCOUNT and destinationResourceType = RESOURCE_TYPE_ACCOUNT) then
        return 'com.rssl.phizic.gate.payments.ClientAccountsTransfer';
      end if;
    end if;

    if (formId=IMA_PAYMENT) then
      --оплата металических счетов
      -- ОМС -> счёт
      if (chargeOffResourceType = RESOURCE_TYPE_IM_ACCOUNT and destinationResourceType = RESOURCE_TYPE_ACCOUNT) then
        return 'com.rssl.phizic.gate.payments.IMAToAccountTransfer';
      end if;

      -- счёт -> ОМС
      if (chargeOffResourceType = RESOURCE_TYPE_ACCOUNT and destinationResourceType = RESOURCE_TYPE_IM_ACCOUNT) then
        return 'com.rssl.phizic.gate.payments.AccountToIMATransfer';
      end if;

      -- ОМС -> карта
      if (chargeOffResourceType = RESOURCE_TYPE_IM_ACCOUNT and destinationResourceType = RESOURCE_TYPE_CARD) then
        return 'com.rssl.phizic.gate.payments.IMAToCardTransfer';
      end if;

      -- карта -> ОМС
      if (chargeOffResourceType = RESOURCE_TYPE_CARD and destinationResourceType = RESOURCE_TYPE_IM_ACCOUNT) then
        return 'com.rssl.phizic.gate.payments.CardToIMATransfer';
      end if;

    end if;

    if (formId=JURIDICAL_TRANSFER or formId is null) then
    --оплата организации
      if (chargeOffResourceType = RESOURCE_TYPE_CARD) then
        --если налоговый платеж c карты
        if (isTaxPayment(paymentId)='true') then
          return 'com.rssl.phizic.gate.payments.CardRUSTaxPayment';
        end if;

        if (isOurBank(paymentId)='true') then
          --на счет внутри банка
          return 'com.rssl.phizic.gate.payments.CardJurIntraBankTransfer';
        end if;

        --на счет в другой банк через платежную систему России
        return 'com.rssl.phizic.gate.payments.CardJurTransfer';

      end if;

      --перевод юр. лицу со счета
      if (chargeOffResourceType = RESOURCE_TYPE_ACCOUNT or chargeOffResourceType is null) then
          --если налоговый платеж со счета
          if(isTaxPayment(paymentId)='true') then
            return 'com.rssl.phizic.gate.payments.AccountRUSTaxPayment';
          end if;

          if (isOurBank(paymentId)='true') then
            --на счет внутри банка
            return 'com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer';
          end if;
          --на счет в другой банк через платежную систему России
          return 'com.rssl.phizic.gate.payments.AccountJurTransfer';

      end if;
    end if;

    if (formId=INDIVIDUAL_TRANSFER) then
      --перевод частному лицу
      if (chargeOffResourceType is null) then
        return null;
      end if;

      --перевод физ. лицу с карты
      if (chargeOffResourceType = RESOURCE_TYPE_CARD) then

        --на карту
        if (destinationResourceType = RESOURCE_TYPE_CARD) then
          -- если перевод внутрибанковский
          if (isOurBank(paymentId)='true') then
            return 'com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank';
          else
            return 'com.rssl.phizic.gate.payments.ExternalCardsTransferToOtherBank';
          end if;
        end if;

        --на счет внутри банка
        if (isOurBank(paymentId)='true') then
          return 'com.rssl.phizic.gate.payments.CardIntraBankPayment';
        end if;
        --на счет в другой банк через платежную систему России
        return 'com.rssl.phizic.gate.payments.CardRUSPayment';
      end if;

      --перевод физ. лицу со счета
      if (chargeOffResourceType = RESOURCE_TYPE_ACCOUNT) then
        if (isOurBank(paymentId)='true') then
          return 'com.rssl.phizic.gate.payments.AccountIntraBankPayment';
        end if;
        --на счет в другой банк через платежную систему России
        return 'com.rssl.phizic.gate.payments.AccountRUSPayment';
      end if;
    end if;

    if (formId=EXTERNAL_PAYMENT_TRANSFER) then
      --биллинговая оплата
      if (chargeOffResourceType is null) then
        return null;
      end if;

      if (chargeOffResourceType = RESOURCE_TYPE_ACCOUNT) then
        return 'com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment';
      elsif (chargeOffResourceType = RESOURCE_TYPE_CARD) then
        return 'com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment';
      end if;
    end if;

    if (formId=LOAN_PAYMENT) then
      --оплата кредита
      return 'com.rssl.phizic.gate.payments.LoanTransfer';
    end if;

    if (formId=SECURITIES_TRANSFER_CLAIM) then
      --ценные бымаги
      return 'com.rssl.phizic.gate.claims.SecuritiesTransferClaim';
    end if;

    return null;
  end;
  
  procedure log(text in varchar2) is
  begin
    --dbms_output.put_line(text);
    null;
  end;
  
  --удаление дублей имен шаблонов в рамках профиля
  procedure correctTemplateName(clientGUID in varchar2) is
  begin
    log(systimestamp);
    for tmpl in (
      select id, template_name, rnk from (
        select 
          t.id, 
          t.template_name,
          rank() over (partition by t.client_guid, t.template_name order by id) rnk
        from payments_documents t
        where client_guid=clientGUID
      ) where rnk > 1
    ) loop
      update payments_documents set template_name=tmpl.template_name||' ('||tmpl.rnk||') ' 
      where id=tmpl.id;
    end loop;
    log(systimestamp);
  end;
  
  procedure log(
      mid         in number, 
      mstate_code in varchar2, 
      mchanged    in timestamp,    
      sid         in number, 
      sstate_code in varchar2, 
      schanged    in timestamp,
      state       in varchar2
  ) is
  begin
    insert into temp_sinchronize_log values(mid, mstate_code, mchanged, sid, sstate_code, schanged, sysdate, state);
  end;
  
  --конвертация шаблонов
  procedure convertTemplates(streamNumber in number) is
    ordIndex              number:=0;
    oldLoginID            number;
    templateName          varchar2(256);
    templateIsUse         varchar2(1);
    templateShowInMobile  varchar2(1);
    formType              varchar2(32);  
    clientGUID            varchar2(32);  
  begin
    for rec in (select BUSINESS_DOCUMENT_ID as paymentId, LOGIN_ID from TEMP_COV_TEMPLATE where stream=streamNumber order by login_id, business_document_id) loop
      begin

        --зачищаем дубли имен шаблонов
        if (oldLoginID != rec.login_id and oldLoginID is not null) then
           correctTemplateName(clientGUID);
           commit;
        end if;

        savepoint start_convert_template;
        
        --если шаблон другого клиента, скидываем индекс
        if (oldLoginID != rec.login_id or oldLoginID is null) then
          ordIndex := 1; 
          select ID into clientGUID from USERS where login_id=rec.login_id;
        --если шаблон того же клиента - увеличиваем индекс
        else 
          ordIndex := ordIndex + 1;
        end if;
        
        oldLoginID:=rec.login_id;

        --Настройки шаблона
        select TEMPLATE_NAME, IS_USE, SHOW_IN_MOBILE into templateName, templateIsUse, templateShowInMobile from (
          select TEMPLATE_NAME, IS_USE, SHOW_IN_MOBILE, rank() over (order by ID desc) rnk
            from BUSINESS_DOCUMENTS_RES where BUSINESS_DOCUMENT_ID=rec.paymentId
        ) where rnk < 2;

        --основные реквизиты
        insert into payments_documents(
          ID,KIND,CHANGED,EXTERNAL_ID,DOCUMENT_NUMBER,
          CLIENT_CREATION_DATE,CLIENT_CREATION_CHANNEL,CLIENT_OPERATION_DATE,CLIENT_OPERATION_CHANNEL,
          EMPLOYEE_OPERATION_DATE,EMPLOYEE_OPERATION_CHANNEL,CLIENT_GUID,CREATED_EMPLOYEE_GUID,CREATED_EMPLOYEE_FULL_NAME,
          CONFIRMED_EMPLOYEE_GUID,CONFIRMED_EMPLOYEE_FULL_NAME,
          OPERATION_UID,
          STATE_CODE,STATE_DESCRIPTION,
          FORM_TYPE,
          CHARGEOFF_RESOURCE,
          DESTINATION_RESOURCE,
          GROUND,CHARGEOFF_AMOUNT,CHARGEOFF_CURRENCY,DESTINATION_AMOUNT,DESTINATION_CURRENCY,
          SUMM_TYPE,RECEIVER_NAME,INTERNAL_RECEIVER_ID,RECEIVER_POINT_CODE,EXTENDED_FIELDS,
          CLASS_TYPE,
          TEMPLATE_NAME,TEMPLATE_USE_IN_MAPI,TEMPLATE_USE_IN_ATM,TEMPLATE_USE_IN_ERMB,TEMPLATE_USE_IN_ERIB,
          TEMPLATE_IS_VISIBLE,TEMPLATE_ORDER_IND,TEMPLATE_STATE_CODE,TEMPLATE_STATE_DESCRIPTION,
          REGION_ID,AGENCY_ID,BRANCH_ID
        )
        select 
          bd.id, 
          getKind(bd.form_id, bd.recipient_id),
          bd.changed, bd.external_id, bd.doc_number,
          bd.creation_date, bd.creation_type, bd.operation_date, bd.creation_type,
          bd.operation_date, 1, u.id, e.id, e.sur_name||' '||e.first_name||' '||e.patr_name,
          (select id from employees where login_id=bd.confirmed_employee_login_id), bd.confirm_employee,
          case  
            when bd.kind='P' and bd.operation_uuid is not null then bd.operation_uuid
            else bd.operation_uid
          end as operation_uid, 
          bd.state_code, bd.state_description, 
          getOperation(bd.form_id, bd.recipient_id),
          bd.payer_account, 
          bd.receiver_account, 
          bd.ground, bd.amount, bd.currency, bd.destination_amount, bd.destination_currency,
          bd.summ_type, bd.receiver_name, bd.recipient_id, bd.provider_external_id, bd.extended_fields, 
          getClass(bd.id,bd.form_id,bd.payer_account,bd.receiver_account),
          nvl(templateName, ' '), templateShowInMobile , '1', '1', '1', 
          templateIsUse, 
          ordIndex,
          'ACTIVE', ' ', 
          d.tb, d.osb, d.office
        from BUSINESS_DOCUMENTS bd
--        inner join business_documents_res res on res.business_document_id=bd.id
        inner join USERS u on u.login_id=bd.login_id
        left join EMPLOYEES e on e.login_id=bd.created_employee_login_id
        left join DEPARTMENTS d on d.id=bd.department_id
        where bd.id=rec.paymentId;
        
        --доп. реквизиты
        select /*+index(payments_documents pk_payments_documents)*/ FORM_TYPE into formType from payments_documents where id=rec.paymentId;

        insert into PAYMENTS_DOCUMENTS_EXT( ID, KIND, NAME, VALUE, PAYMENT_ID)
        select 
          ID, 
          type,
          getFieldName(name, formType),
          value, payment_id 
        from DOCUMENT_EXTENDED_FIELDS where PAYMENT_ID=rec.paymentId;

        update TEMP_COV_TEMPLATE set CONVERT_STATE='Ok' where LOGIN_ID=rec.login_id and BUSINESS_DOCUMENT_ID=rec.paymentId;

        commit;

      exception
        when others then
          rollback to start_convert_template;
          log(SQLERRM);
          update TEMP_COV_TEMPLATE set CONVERT_STATE='Error' where LOGIN_ID=rec.login_id and BUSINESS_DOCUMENT_ID=rec.paymentId;
      end;      
               
    end loop;   
  end;

  --обновление документа
  procedure updateDocument(docId in number) is
  begin
    
    --вычищаем доп. реквизиты документа в новой структуре                      
    delete from DOCUMENT_EXTENDED_FIELDS_P where PAYMENT_ID=docId;
    --вычищаем осн. реквизиты документа в новой структуре                      
    delete from BUSINESS_DOCUMENTS_P where ID=docId;
    
    --переносим данные заново
    insert /*+noappend*/ into BUSINESS_DOCUMENTS_P 
    select 
      ID, GROUND, LOGIN_ID, FORM_ID, CHANGED, SIGNATURE_ID,  nvl(STATE_CODE, 'ERROR'), nvl(STATE_DESCRIPTION, '!'), CURRENCY, DEPARTMENT_ID, DOC_NUMBER, CREATION_TYPE, ARCHIVE, KIND, CREATION_DATE, COMMISSION, COMMISSION_CURRENCY, PAYER_NAME, RECEIVER_ACCOUNT, RECEIVER_NAME, AMOUNT, EXTERNAL_ID, EXTERNAL_OFFICE_ID, STATE_MACHINE_NAME, PAYER_ACCOUNT, OPERATION_DATE, ADMISSION_DATE, EXECUTION_DATE, DOCUMENT_DATE, REFUSING_REASON, EXTERNAL_OWNER_ID, TEMPLATE_ID, DESTINATION_AMOUNT, DESTINATION_CURRENCY, COUNT_ERROR, IS_LONG_OFFER, CREATION_SOURCE_TYPE, EXTENDED_FIELDS, SYSTEM_NAME, SUMM_TYPE, CONFIRM_STRATEGY_TYPE, ADDITION_CONFIRM, CONFIRM_EMPLOYEE, 
      case  
        when KIND='P' and OPERATION_UUID is not null then OPERATION_UUID
        else OPERATION_UID
      end as OPERATION_UID, 
      PROMO_CODE, BILLING_DOCUMENT_NUMBER, PROVIDER_EXTERNAL_ID, RECIPIENT_ID, ACCESS_AUTOPAY_SCHEMES, SESSION_ID, CODE_ATM, CREATED_EMPLOYEE_LOGIN_ID, CONFIRMED_EMPLOYEE_LOGIN_ID, LOGIN_TYPE
    from BUSINESS_DOCUMENTS
    where id=docId;

    insert /*+noappend*/ into DOCUMENT_EXTENDED_FIELDS_P 
    select * from DOCUMENT_EXTENDED_FIELDS
      where PAYMENT_ID=docId;
      
  end;

  --обновление шаблона
  procedure updateTemplate(docId in number) is
    ordIndex              number:=0;
    oldLoginID            number;
    templateName          varchar2(256);
    templateIsUse         varchar2(1);
    templateShowInMobile  varchar2(1);
    formType              varchar2(32);   
    stateCode             varchar2(32); 
    isNeedConvert         char(1):='0';
    clientGUID            varchar2(32);     
  begin
    begin
      --доп. реквизиты
      select /*+index(payments_documents pk_payments_documents)*/ FORM_TYPE, TEMPLATE_ORDER_IND, CLIENT_GUID into formType, ordIndex, clientGUID from payments_documents where id=docId;
      isNeedConvert:='1';
    exception
      when NO_DATA_FOUND then isNeedConvert:='0';    
    end;

    if (isNeedConvert = '1') then
      --вычищаем доп. реквизиты документа в новой структуре                      
      delete from PAYMENTS_DOCUMENTS_EXT where PAYMENT_ID=docId;
      --вычищаем осн. реквизиты документа в новой структуре                      
      delete from PAYMENTS_DOCUMENTS where ID=docId;    

      --Настройки шаблона
      select TEMPLATE_NAME, IS_USE, SHOW_IN_MOBILE into templateName, templateIsUse, templateShowInMobile from (
        select TEMPLATE_NAME, IS_USE, SHOW_IN_MOBILE, rank() over (order by id desc) rnk
          from BUSINESS_DOCUMENTS_RES where BUSINESS_DOCUMENT_ID=docId
      ) where rnk < 2;

      --основные реквизиты
      insert into PAYMENTS_DOCUMENTS(
        ID,KIND,CHANGED,EXTERNAL_ID,DOCUMENT_NUMBER,
        CLIENT_CREATION_DATE,CLIENT_CREATION_CHANNEL,CLIENT_OPERATION_DATE,CLIENT_OPERATION_CHANNEL,
        EMPLOYEE_OPERATION_DATE,EMPLOYEE_OPERATION_CHANNEL,CLIENT_GUID,CREATED_EMPLOYEE_GUID,CREATED_EMPLOYEE_FULL_NAME,
        CONFIRMED_EMPLOYEE_GUID,CONFIRMED_EMPLOYEE_FULL_NAME,
        OPERATION_UID,
        STATE_CODE,STATE_DESCRIPTION,
        FORM_TYPE,
        CHARGEOFF_RESOURCE,
        DESTINATION_RESOURCE,
        GROUND,CHARGEOFF_AMOUNT,CHARGEOFF_CURRENCY,DESTINATION_AMOUNT,DESTINATION_CURRENCY,
        SUMM_TYPE,RECEIVER_NAME,INTERNAL_RECEIVER_ID,RECEIVER_POINT_CODE,EXTENDED_FIELDS,
        CLASS_TYPE,
        TEMPLATE_NAME,TEMPLATE_USE_IN_MAPI,TEMPLATE_USE_IN_ATM,TEMPLATE_USE_IN_ERMB,TEMPLATE_USE_IN_ERIB,
        TEMPLATE_IS_VISIBLE,TEMPLATE_ORDER_IND,TEMPLATE_STATE_CODE,TEMPLATE_STATE_DESCRIPTION,
        REGION_ID,AGENCY_ID,BRANCH_ID
      )
      select 
        bd.id, 
        getKind(bd.form_id, bd.recipient_id),
        bd.changed, bd.external_id, bd.doc_number,
        bd.creation_date, bd.creation_type, bd.operation_date, bd.creation_type,
        bd.operation_date, 1, u.id, e.id, e.sur_name||' '||e.first_name||' '||e.patr_name,
        (select id from employees where login_id=bd.confirmed_employee_login_id), bd.confirm_employee,
        case  
          when bd.kind='P' and bd.operation_uuid is not null then bd.operation_uuid
          else bd.operation_uid
        end as operation_uid, 
        bd.state_code, bd.state_description, 
        getOperation(bd.form_id, bd.recipient_id),
        bd.payer_account, 
        bd.receiver_account, 
        bd.ground, bd.amount, bd.currency, bd.destination_amount, bd.destination_currency,
        bd.summ_type, bd.receiver_name, bd.recipient_id, bd.provider_external_id, bd.extended_fields, 
        getClass(bd.id,bd.form_id,bd.payer_account,bd.receiver_account),
        nvl(templateName, ' '), templateShowInMobile , '1', '1', '1', 
        templateIsUse, 
        ordIndex,
        'ACTIVE', ' ', 
        d.tb, d.osb, d.office
      from BUSINESS_DOCUMENTS bd
--      inner join business_documents_res res on res.business_document_id=bd.id
      inner join USERS u on u.login_id=bd.login_id
      left join EMPLOYEES e on e.login_id=bd.created_employee_login_id
      left join DEPARTMENTS d on d.id=bd.department_id
      where bd.id=docId;
      --доп.реквизиты    
      insert into PAYMENTS_DOCUMENTS_EXT( ID, KIND, NAME, VALUE, PAYMENT_ID)
      select 
        ID, 
        type,
        getFieldName(name, formType),
        value, payment_id 
      from DOCUMENT_EXTENDED_FIELDS where payment_id=docId;
      
      --зачищаем дубли имен шаблонов
      if (clientGUID is not null) then
         correctTemplateName(clientGUID);
      end if;      
    end if;               
  end;

  --актуализация документов
  procedure sinchronizeDocuments(startId in number, cuntSteps in number, stepSize in number) is
    startPos              number:=0;
    stopPos               number:=0;
  begin
    stopPos:=startId;
    
    for idx in 1..cuntSteps loop
      
      startPos:=stopPos-stepSize;
      log(startPos);
      
      for rec in (
                    select 
                      bdm.id mid, bdm.state_code mstate_code, bdm.changed mchanged,    
                      bds.id sid, bds.state_code sstate_code, bds.changed schanged
                    from srb_ikfl.business_documents bdm
                    inner join srb_ikfl.business_documents_p bds on bds.id=bdm.id
                    where 
                        bds.id >  startPos
                    and bds.id <= stopPos
                    and bdm.changed!=bds.changed
                    and bdm.state_code!=bds.state_code
      ) loop
        begin
          savepoint start_sinchronize_doc;
          --если документ переведен в статус удален, просто обновляем статус(не переливаем документ)
          if (rec.mstate_code = 'DELETED') then
            update business_documents_p set state_code=rec.mstate_code, changed=rec.mchanged where id=rec.sid;
            log( rec.mid, rec.mstate_code, rec.mchanged, rec.sid, rec.sstate_code, rec.schanged, 'U');
          elsif (rec.mstate_code like '%TEMPLATE%') then
            --обновляем документ, из которого конвертировался шаблон
            updateDocument(rec.sid);
            --обновляем шаблон
            updateTemplate(rec.sid);
            log( rec.mid, rec.mstate_code, rec.mchanged, rec.sid, rec.sstate_code, rec.schanged, 'T');
          else 
            --обновляем документ
            updateDocument(rec.sid);
            log( rec.mid, rec.mstate_code, rec.mchanged, rec.sid, rec.sstate_code, rec.schanged, 'D');
          end if;
        exception
          when others then 
            rollback to start_sinchronize_doc;
            log( rec.mid, rec.mstate_code, rec.mchanged, rec.sid, rec.sstate_code, rec.schanged, 'E');
        end;
        --фиксируем изменения
        commit;
      end loop;
      
      stopPos:=startPos;
    end loop;    
  end;
  
  --актуализация документов по результатам работы триггера
  procedure sinchronizeDocTrigger(startId in number, cuntSteps in number, stepSize in number) is
    startPos              number:=0;
    stopPos               number:=0;
  begin
    stopPos:=startId;
    
    for idx in 1..cuntSteps loop
      
      startPos:=stopPos-stepSize;
      log(startPos);
      
      for rec in ( 
                    select 
                      bdm.id mid, bdm.state_code mstate_code, bdm.changed mchanged,    
                      bds.id sid, bds.state_code sstate_code, bds.changed schanged
                    from srb_ikfl.business_documents bdm
                    inner join srb_ikfl.business_documents_p bds on bds.id=bdm.id
                    inner join srb_ikfl.temp_changed_log tr on tr.id=bdm.id
                    where 
                        bds.id >  startPos
                    and bds.id <= stopPos
                    and bdm.changed!=bds.changed
                    and bdm.state_code!=bds.state_code
                    group by
                      bdm.id, bdm.state_code, bdm.changed,    
                      bds.id, bds.state_code, bds.changed
      ) loop
        begin
          savepoint start_sinch_doc_trigger;

          --если документ переведен в статус удален, просто обновляем статус(не переливаем документ)
          if (rec.mstate_code = 'DELETED') then
            update business_documents_p set state_code=rec.mstate_code, changed=rec.mchanged where id=rec.sid;
            log( rec.mid, rec.mstate_code, rec.mchanged, rec.sid, rec.sstate_code, rec.schanged, 'U');
          elsif (rec.mstate_code like '%TEMPLATE%') then
            --обновляем документ, из которого конвертировался шаблон
            updateDocument(rec.sid);
            --обновляем шаблон
            updateTemplate(rec.sid);
            log( rec.mid, rec.mstate_code, rec.mchanged, rec.sid, rec.sstate_code, rec.schanged, 'T');
          else 
            --обновляем документ
            updateDocument(rec.sid);
            log( rec.mid, rec.mstate_code, rec.mchanged, rec.sid, rec.sstate_code, rec.schanged, 'D');
          end if;
        exception
          when others then 
            rollback to start_sinch_doc_trigger;
            log( rec.mid, rec.mstate_code, rec.mchanged, rec.sid, rec.sstate_code, rec.schanged, 'E');
        end;
        --фиксируем изменения
        commit;
      end loop;
      
      stopPos:=startPos;
    end loop;    
  end;
end CONVERT_TEMPLATES_11R;
/