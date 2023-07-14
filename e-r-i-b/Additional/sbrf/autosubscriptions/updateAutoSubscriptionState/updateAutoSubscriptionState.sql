CREATE OR REPLACE PROCEDURE updateAutoSubscriptionState(
pRequestId BUSINESS_DOCUMENTS.ID%type, -- ������������� ������, ���������� ���� (AutoSubscriptionRec\AutoSubscriptionInfo\RequestId ) � ������a� ASM, ASSM.
pResult BUSINESS_DOCUMENTS.STATE_CODE%type, -- ��������� ����������: 'EXECUTED' - ��� �����������, 'REFUSED' - ��� ����������
pReason BUSINESS_DOCUMENTS.STATE_DESCRIPTION%type := NULL -- �������� �������/������� ������. ��� ���������� 'EXECUTED' ������������. ��� 'REFUSED' ������ ���� ������
) is
  reason VARCHAR2(4000) := pReason;
begin
    dbms_output.enable;
    if (pResult != 'EXECUTED' and pResult != 'REFUSED') then 
        dbms_output.put_line(pRequestId||': ���������������� ��������� ���������� ������ '||pResult||'. ��������� EXECUTED - ��� �����������, REFUSED - ��� ����������.');
        return;
    end if;

    if (pResult = 'EXECUTED') then 
        reason := '������ ������� ������� �� ���������� � �����';
    end if;

    if (pResult = 'REFUSED' and reason is NULL) then
        dbms_output.put_line(pRequestId||': �� ������ ������� ������ ��� ���������� ������');
        return;
    end if;
    update BUSINESS_DOCUMENTS set STATE_CODE=pResult, STATE_DESCRIPTION=reason  where id=pRequestId and IS_LONG_OFFER=1 and FORM_ID in (select ID from PAYMENTFORMS where NAME in ('RurPayJurSB','EditAutoSubscriptionPayment','CloseAutoSubscriptionPayment','DelayAutoSubscriptionPayment','RecoveryAutoSubscriptionPayment'));
    if (SQL%ROWCOUNT = 0) then 
        dbms_output.put_line(pRequestId||': ������ �� ���������: �������� ������������� ������(RequestId)');
        return;
    end if;
end;
