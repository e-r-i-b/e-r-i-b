declare
    GROUP_RISK_1 number:=795;--	������ 1 (���������� ������ �����)
    GROUP_RISK_2 number:=796;--	������ 2 (������� ������ �����)
    GROUP_RISK_3 number:=798;--	������ 3 (����� � ���.�����)
    GROUP_RISK_4 number:=797;--	������ 4 (������������� ��������)
    gr_id number;
begin
---���������� ������ ����� (��1.)
    gr_id:=GROUP_RISK_1; --������ 1 (���������� ������ �����)
	
    --�������� ������
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 0, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'INTERNET_CLIENT', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
	insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 10000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'INTERNET_CLIENT', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
	insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 100000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'INTERNET_CLIENT', 'LOW' from DEPARTMENTS d where osb is null and office is null;

	--��������� ����������
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)  
        select S_LIMITS.nextval, systimestamp, systimestamp, 0, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'MOBILE_API', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)
        select S_LIMITS.nextval, systimestamp, systimestamp, 3000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'MOBILE_API', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)
        select S_LIMITS.nextval, systimestamp, systimestamp, 30000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'MOBILE_API', 'LOW' from DEPARTMENTS d where osb is null and office is null;
		
    --���������� ����������������   
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)
        select S_LIMITS.nextval, systimestamp, systimestamp, 100000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'SELF_SERVICE_DEVICE', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)
        select S_LIMITS.nextval, systimestamp, systimestamp, 100000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'SELF_SERVICE_DEVICE', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)
        select S_LIMITS.nextval, systimestamp, systimestamp, 100000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'SELF_SERVICE_DEVICE', 'LOW' from DEPARTMENTS d where osb is null and office is null;
        
---������� ������ ����� (��2.) 
    gr_id:=GROUP_RISK_2; --������ 2 (������� ������ �����)

    --�������� ������
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 0, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'INTERNET_CLIENT', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'INTERNET_CLIENT', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 10000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'INTERNET_CLIENT', 'LOW' from DEPARTMENTS d where osb is null and office is null;

	--��������� ����������
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 0, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'MOBILE_API', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'MOBILE_API', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 10000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'MOBILE_API', 'LOW' from DEPARTMENTS d where osb is null and office is null;

    --���������� ����������������   
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)
        select S_LIMITS.nextval, systimestamp, systimestamp, 10000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'SELF_SERVICE_DEVICE', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)
        select S_LIMITS.nextval, systimestamp, systimestamp, 10000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'SELF_SERVICE_DEVICE', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)
        select S_LIMITS.nextval, systimestamp, systimestamp, 10000, 'RUB', 'AMOUNT_IN_DAY','NEED_ADDITIONAL_CONFIRN','GROUP_RISK',d.ID, gr_id, null, 'SELF_SERVICE_DEVICE', 'LOW' from DEPARTMENTS d where osb is null and office is null;
		
---������������� ��������
    gr_id:=GROUP_RISK_4; --������ 4 (������������� ��������)

    --�������� ������
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 100, 'INTERNET_CLIENT', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 100, 'INTERNET_CLIENT', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 100, 'INTERNET_CLIENT', 'LOW' from DEPARTMENTS d where osb is null and office is null;

	--��������� ����������
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 100, 'MOBILE_API', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)  
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 100, 'MOBILE_API', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)  
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 100, 'MOBILE_API', 'LOW' from DEPARTMENTS d where osb is null and office is null;

	--���������� ����������������   
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 100, 'SELF_SERVICE_DEVICE', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)  
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 100, 'SELF_SERVICE_DEVICE', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE)  
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 100, 'SELF_SERVICE_DEVICE', 'LOW' from DEPARTMENTS d where osb is null and office is null;
        
---���.�����
    gr_id:=GROUP_RISK_3; --������ 3 (����� � ���.�����)
	
    --�������� ������
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 2, 'INTERNET_CLIENT', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000000, 'RUB', 'AMOUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, null, 'INTERNET_CLIENT', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 2, 'INTERNET_CLIENT', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000000, 'RUB', 'AMOUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, null, 'INTERNET_CLIENT', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 2, 'INTERNET_CLIENT', 'LOW' from DEPARTMENTS d where osb is null and office is null;
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000000, 'RUB', 'AMOUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, null, 'INTERNET_CLIENT', 'LOW' from DEPARTMENTS d where osb is null and office is null;

	--��������� ����������
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 2, 'MOBILE_API', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000000, 'RUB', 'AMOUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, null, 'MOBILE_API', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 2, 'MOBILE_API', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000000, 'RUB', 'AMOUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, null, 'MOBILE_API', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 2, 'MOBILE_API', 'LOW' from DEPARTMENTS d where osb is null and office is null;
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000000, 'RUB', 'AMOUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, null, 'MOBILE_API', 'LOW' from DEPARTMENTS d where osb is null and office is null;

	--���������� ����������������
	--��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 2, 'SELF_SERVICE_DEVICE', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000000, 'RUB', 'AMOUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, null, 'SELF_SERVICE_DEVICE', 'HIGHT' from DEPARTMENTS d where osb is null and office is null;
    --��������:������� �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 2, 'SELF_SERVICE_DEVICE', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000000, 'RUB', 'AMOUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, null, 'SELF_SERVICE_DEVICE', 'MIDDLE' from DEPARTMENTS d where osb is null and office is null;
    --��������:������ �������
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, null, null, 'OPERATION_COUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, 2, 'SELF_SERVICE_DEVICE', 'LOW' from DEPARTMENTS d where osb is null and office is null;
    insert into LIMITS (ID, CREATION_DATE, START_DATE, AMOUNT, CURRENCY, RESTRICTION_TYPE, OPERATION_TYPE, LIMIT_TYPE, DEPARTMENT_ID, GROUP_RISK_ID, OPERATION_COUNT, CHANNEL_TYPE, SECURITY_TYPE) 
        select S_LIMITS.nextval, systimestamp, systimestamp, 1000000, 'RUB', 'AMOUNT_IN_DAY','IMPOSSIBLE_PERFORM_OPERATION','GROUP_RISK',d.ID, gr_id, null, 'SELF_SERVICE_DEVICE', 'LOW' from DEPARTMENTS d where osb is null and office is null;
        
end;