��������� ������������ ��� ���������� ��������� � ���������� �� � ������ 1.18 � ����� (����� v1.18_SBRF_06_08_2010) � ��������������� 1.18 (�� ��������� �� 07.10.2010)
������� ��������:
1. ��������� ������� �� �� ������ 1.18
2. �������� ���� converter-v1_18_psi.sql � ����� AntBuilds\db-data
3. �������� ����� converter.ant  � convert.bat � ����� AntBuilds.
4. ��������� � ����� convert.bat ���� �� ANT � JDK.
5. ��������� �����������, �������� ���� convert.bat
6. ��������� ��� �����:
   _Load2_Operations � ������ �������� ����������� ��������; !!!
   _Load3_Dictionaries;
   _Load4_PaymentForms;
   _Load5_DepositGlobal;
   _Load6_Distributions;
   _Load7_ErrorMessages;
   _Load8_Skins;
   _Load11_SystemPaymentServices;

����� ������ ���������� �������� ���� ����� convert.log, � ��� ���������� ��� ������������ �� (��������� ��������� � ���������� ������).

���������
1. (!!!)  ��������� ������� ����� ��������� ��� ������������� system. ��� �� ������� � ���� ���������� converter-v1_18_psi.sql. ������� �� ����� ���������� �������� (������ <��� �����> ����� ���������� ��� ����� �������������� ��).

create or replace view <���_�����>.CHILDREN_DEPARTMENT_BY_TB as
SELECT replace(sys_connect_by_path(decode(level, 1, children.PARENT_DEPARTMENT), '~'), '~') AS ROOT,
children.ID
FROM <���_�����>.DEPARTMENTS children
CONNECT BY PRIOR children.id = children.parent_department
go
