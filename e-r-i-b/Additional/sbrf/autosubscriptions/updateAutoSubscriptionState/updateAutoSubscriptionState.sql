CREATE OR REPLACE PROCEDURE updateAutoSubscriptionState(
pRequestId BUSINESS_DOCUMENTS.ID%type, -- идентификатор заявки, переданный ЕРИБ (AutoSubscriptionRec\AutoSubscriptionInfo\RequestId ) в запросaх ASM, ASSM.
pResult BUSINESS_DOCUMENTS.STATE_CODE%type, -- результат исполнения: 'EXECUTED' - для исполненной, 'REFUSED' - для отказанной
pReason BUSINESS_DOCUMENTS.STATE_DESCRIPTION%type := NULL -- описание статуса/причина отказа. для результата 'EXECUTED' игнорируется. для 'REFUSED' должна быть задана
) is
  reason VARCHAR2(4000) := pReason;
begin
    dbms_output.enable;
    if (pResult != 'EXECUTED' and pResult != 'REFUSED') then 
        dbms_output.put_line(pRequestId||': недействительный результат исполнения заявки '||pResult||'. Ожидается EXECUTED - для исполненной, REFUSED - для отказанной.');
        return;
    end if;

    if (pResult = 'EXECUTED') then 
        reason := 'Заявка успешно принята на исполнение в банке';
    end if;

    if (pResult = 'REFUSED' and reason is NULL) then
        dbms_output.put_line(pRequestId||': не задана причина отказа для отказанной заявки');
        return;
    end if;
    update BUSINESS_DOCUMENTS set STATE_CODE=pResult, STATE_DESCRIPTION=reason  where id=pRequestId and IS_LONG_OFFER=1 and FORM_ID in (select ID from PAYMENTFORMS where NAME in ('RurPayJurSB','EditAutoSubscriptionPayment','CloseAutoSubscriptionPayment','DelayAutoSubscriptionPayment','RecoveryAutoSubscriptionPayment'));
    if (SQL%ROWCOUNT = 0) then 
        dbms_output.put_line(pRequestId||': заявка не обновлена: неверный идентификатор заявки(RequestId)');
        return;
    end if;
end;
