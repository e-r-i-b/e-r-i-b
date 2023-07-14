create or replace view V_TEMPLATES_CPAYMENT
as
select t.CLN_ID as CLIENT_ID,                             -- id ������� �� ���� ��
    t.TEMPLATE_NAME,                                      -- ��� �������    
    --t.SERVICE_ID as CODE_SERVICE,                         -- ��� ������ � ����������� �������
    --tps.PAYEE_CODE as CODE_RECIPIENT_BS,                  -- ��� ���������� � ����������� �������
    XMLTYPE(nvl(t.TEMPLATE_XML,'<a>n</a>')) as XML_DATA,  -- ��� ���� � ��������� �������
    tps.JUR_NAME, -- ��� ����������
    tps.CURRENT_ACCOUNT, 
    tps.BIC,
    tps.INN,
    tps.KPP,
    tps.CORR_ACCOUNT,
    --tps.IS_ACTIVE,
    tbs.BS_CODE                                           --��� ���������� ������� (urn:sbrfsystems:54-asbil)   
from ESKADM1.T_NB_TEMPLATE t
left join ESKADM1.T_NB_PAYEE_SERVICE tps on tps.BS_ID=t.BS_ID and tps.SBOL_ID=t.SBOL_ID  and tps.SERVICE_ID=t.SERVICE_ID
left join ESKADM1.T_NB_BILL_SYSTEM tbs on tbs.BS_ID=t.BS_ID
