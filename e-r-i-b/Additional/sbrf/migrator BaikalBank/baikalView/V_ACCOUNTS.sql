
CREATE OR REPLACE VIEW V_ACCOUNTS
AS
select "CLA_ID",
        "CLN_ID" AS CLIENT_ID,
        "CLA_ACC" AS ACCOUNT_NUMBER,
        "CLA_ABS",
        "FEE",
        "IS_VKLAD",
        "ACC_OWNER",
        "ACC_OWNER_INN",
        "ACC_NAME" AS ACCOUNT_NAME,
        "ACC_NAME_CLN",
        "ACC_CUR",
	    "FILIAL",
        "ACCTYPE",
        "ACCSUBTYPE",
        "CLA_ACCOLD",
        "TB",
        "OSB",
        "ACC_DISABLED",
        "ACC_ATM_DISABLED"
-- acc_name ��� ������������ ����� (���� ������), ���������� �� ��� ��� ���
-- acc_name_cln ��� ������������, ������ �����/������ ����� ��������
from ESKADM1.T_CLIENT_ACCOUNT a
