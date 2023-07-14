/*
**  ����� ��������, ��������� �������� ����������: ������� ��� ��������� ������� �����������, 
**  ���������� ������������� ��������, �������� ����� �������������.
**  ����� ���������� ������� (�������) �������������, � �������� ����� ������������� �������
**  ����� ������� ����������� ��������� ������� ���������� �� ������ � ��������������
**  ��������� ��� �������� ������ ���� ������� � ��� �����, � ������� �������� ���� (�� ��� system)
*/
DECLARE
    -- ��������� ��� ��������� ���������� 
    s_suffix VARCHAR2(5) := 'BB';            -- ������� ��� ������ �����������, ��� ����������  
    schemeEmployer Number :=21;              -- ����� ������� ���������� �������� ����� �� ���������/������������� �������� (ID �����) 
    schemeClient Number :=177125;            -- ����� ������� ��� ������� (ID �����)
    convertDelay Number :=0.1;               -- �������� ����� ������������� (� ��������) 
    countForConverting Number :=10;          -- �� ������� �������� �������������� (������)
    adapter VARCHAR2(64) := 'cod-bajkal';    -- ������� (������������)
	regionID Number:=1;                      -- ������ � ������� ������� (��� �������� �����)
	defaultDepartment Number:=10449;         -- �������������, � �������� ����������� ���������� ���� �� ����� ���������� �� ��, ��� � ��� (ID)
	billingID Number:=21;                    -- ID ���������� ������� ��� �������� (id IQW)		
BEGIN
    -- ����������� ���������
    UPDATE CONVERTER_CONFIG 
        SET "suffix"=s_suffix, "scheme_Employer"=schemeEmployer, "scheme_Client"=schemeClient, "convert_Delay"=convertDelay, "count_For_Converting"=countForConverting, "adapter"=adapter, "region_id"=regionID, "default_Department"=defaultDepartment, "billing_id"=billingID, "stop"='0'
            WHERE "id" = '1';

    -- ������������ ����������� 1-����; 0-������ ���������
     --converter.MigrateEmployees('1');
    -- ������������ �������� 1-����; 0-������ ���������
    converter.MigrateClients('0'); 
    -- �������� ��������
    -- DeleteClients;
    -- �������� �����������
     --converter.DeleteEmployees;

    -- ���������� ������� ��� ����������������� ����������� � ����
	/*  �������� ���������� ����������� ������� � ����
		select '�����:' as TLOGIN, CE.LOGIN, '������:' as TPASSWORD, CE."PASSWORD", '���������:' as TEMPLOYEE,  EM.USR_NAME,' �������������: '||EM.TB||' '||EM.OSB||' '||EM.OFFICE
			from CONVERTER_EMPLOYEES CE
				left join V_EMPLOYEES@psiLink EM on EM.ID=CE.ID_SBOL
					where STATE = 'O'
	*/
END;