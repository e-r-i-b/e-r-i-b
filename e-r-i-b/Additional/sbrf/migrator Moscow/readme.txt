   ��������� ��� �������� �������� � ����������� ����������� � ������������ ������, ���� ���� (������ 1.18)

   ����� �������� ���������� ����������:
       1) � ���� ���� ������� view (�� ����� Views)
       2) � ���� ���� ������� ����������� � ��������� ���� ������ (���� ����) promLink (��� ��� ��������� � ���� ���������) ���� public, ���� ������ ��� ����� ����

		create public database link PROMLINK connect to USER_NAME identified by USER_PASSWORD 
			using '(DESCRIPTION =  (ADDRESS = (PROTOCOL = TCP)(HOST = IP_SERVERA)(PORT = 1521))
				(CONNECT_DATA =
      					(SERVER = DEDICATED)
					(SID = SID_NAME)
				)
			)'	

       3) � ����� ���� ��������� ������� init.sql, crypto.sql � utils.sql. (�� ��� system)
       4) ��������� ������ migratorMoscow.pck (��� ��������� ���� ���������� ������ �� �������� ��������� � ��������� ���� ������ � ����������� ���������)
       5) ���������� ��������� ���������� � ������� converter_config 

							COD_TB	               -- ��� �������� ��� �������� ��������� ���������
							SUFFIX                 -- ������� ��� ������ �����������, ��� ����������
							SCHEME_ADMIN           -- ����� ������� ���������� �������� ��������� ����� (ID �����) 
							SCHEME_EMPLOYER        -- ����� ������� ���������� �������� ����� �� ���������/������������� �������� (ID �����) 
							SCHEME_CLIENT_SBOL     -- ����� ���� ������� (ID �����) 
							SCHEME_CLIENT_UDBO     -- ����� ���� ������� (ID �����) 
							SCHEME_CLIENT_CARD     -- ����� ���������� ������� (ID �����) 
							CONVERTER_DELAY        -- �������� ����� ����������� ������ ��������  
							COUNT_FOR_CONVERTING   -- ���-�� �������� � ������
							ADAPTER                -- ������� (������������)
							REGION_ID              -- ������ � ������� ������� (��� �������� �����) (ID)
							DEFAULT_DEPARTMENT     -- �������������, � �������� ����������� ���������� ���� �� ����� ���������� �� ��, ��� � ��� (ID)
							BILLING_ID             -- ID ���������� ������� ��� �������� (id IQW)							  
							STOP                   -- ������� ������ ��������� �������� �������� (0-��������, 1-������������� ��������)
							RGN_CODE               -- ��� ������� �� ���� �� � �������� ���������� �������

       6) ��������� ������� ���� �� ���������� �������: dbms_lock, dbms_lob (� ���� ����), � migration (����� � ����������� ����������� ������ �������� � ���� ����)
		���� ���� ���, ��������� ��� system - �� : 
								- � ���� ���� - grant execute to dbms_lock to user_name
								- � ���� ���� - grant execute to dbms_lob to user_name
								- � ���� ���� - grant execute to shema_name.migration to user_name (user_name ���� ������������, ��� �������� ��������� View � �������� ����������� � Database Link)        		

       7) ��� ����������� ������� ������ �����������/�������� ������� ��������� ����� ��� ����������� converter_set - id, id_sbol
	      � ������� ������ �������� ��������� � ������� start.sql

              ��� �������� �������� � ������ ��������� ID ��������/����������� �� ���� ���� � login_id ���� ��� ��������/�����������, ������� ����� �������
              ��� �������� �������� � ������ ��������� ID ��������/����������� �� ���� ����

   ���� ��������� � ��������: converter_clients, converter_employees, converter_log(������ ������) 
	   � �������� ������ ������: ��� �� ������� �������� ������ � �������� ������ oracle (���+��������)

   � ������� converter_accessschemes �������� ������� ������������ ���� ���� ����������� ���� � ����, ��� ��������� �������

   ��� ��������� ���������� � �������� ���������� � ������� converter_config ���� ���� STOP
	   ���� ��� ���������� � "1" �� �� ��������� ����������� �������� ��������������� �������/���������� ������� ���������������

   �� ��������� �������� ����������� ��������� ������ � ����������� ����������� � ����, ��� ��������� �� ������� � �������:

			select '�����:' as TLOGIN, CE.LOGIN, '������:' as TPASSWORD, CE."PASSWORD", '���������:' as TEMPLOYEE, E.FIRST_NAME, E.PATR_NAME, E.SUR_NAME 
				from CONVERTER_EMPLOYEES CE
					inner join EMPLOYEES E on E.LOGIN_ID = CE.ID_ERIB
						where STATE = 'O'
		