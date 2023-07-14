CREATE OR REPLACE PACKAGE IKFL AS

 --�������������� ������ �� ������
  FUNCTION lnGetDate (StrDate IN VARCHAR2) RETURN Date;
                      
  -- ����������� ���� ������
  PROCEDURE ParseSystemClaimsField(ClaimParm IN OUT IKFLTCLAIMPARM,claim_rec IN OUT dclaim_dbt%ROWTYPE,ClaimNumID IN OUT NUMBER,ParamNumber IN NUMBER,IsUpdate IN OUT BOOLEAN );  
  --��������� ������ �������
  PROCEDURE ParseSystemPersonsField(ClaimParm IN IKFLTCLAIMPARM,persn_rec IN OUT dpersn_dbt%ROWTYPE,IsUpdate IN OUT BOOLEAN);      
  -- ����������� ���� ������������� ��������
  PROCEDURE ParseSystemDocumentField(ClaimParm IN IKFLTCLAIMPARM,persnidc_rec IN OUT dpersnidc_dbt%ROWTYPE,IsUpdate IN OUT BOOLEAN);
  -- ����������� ���� ������
  PROCEDURE ParseSystemAddressField(ClaimParm IN IKFLTCLAIMPARM,adress_rec IN OUT dadress_dbt%ROWTYPE,IsUpdate IN OUT BOOLEAN);  
  -- ��������� ��������� (������������)                 
  PROCEDURE ParseSystemErrorField(ClaimParm IN OUT IKFLTCLAIMPARM,ParamNumber IN NUMBER, ErrCodeID IN OUT NUMBER ,ErrDescID IN OUT NUMBER); 

  PROCEDURE ProceedUserField(ClaimID IN NUMBER, ClaimParm IN IKFLTCLAIMPARM);    
  --�������� ������� ����(tom)
  FUNCTION GetCurrentDate RETURN DATE;
    
  -- ������� ��������� ������ ����� ��������
  FUNCTION GetListTypeCrd(TypeCrdCode IN NUMBER, CrdKind IN NUMBER, UsField1 IN NUMBER, UsField2 IN NUMBER, UsField3 IN NUMBER, UsField4 IN NUMBER, UsField5 IN NUMBER) RETURN IKFLTATYPECRDLST;
  
  -- ������� ��������� ���������� �� ����� �������,CreditTypeIDs - ������ ���������������,UsFields - ������ ������� ���. �����. 
  FUNCTION GetCrdTypeInfo(CreditTypeIDs IN IKFLTANumber, UsFields IN IKFLTANumber) RETURN IKFLTACrdType;
  
  --��������� ������
  FUNCTION GetLimitValue(CreditTypeID IN NUMBER) RETURN NUMBER;

  -- ��������� �������/���������� ���������� �� ������
  -- ClaimID - �����. �������� ������, ���� �����. ����� ��� ��������������
  -- DebitorClaimID- ������ ��������,���� ������� ������ ��� ���������� ����� NULL 
  FUNCTION ModifyClaim  (ClaimParm IN OUT IKFLTACLAIMPARM, ClaimID IN OUT NUMBER, DebitorClaimID IN NUMBER,DebitorContractNumber IN NUMBER) RETURN NUMBER;  

  -- ������� ��������� �������� ������
  PROCEDURE GetClaimState (Claims IN OUT IKFLTAClaimState);

  -- ������� ��������� ������� ���������
  FUNCTION GetPlanpay (ClaimID IN NUMBER) RETURN IKFLTAPlanpay;

  -- ������� ��������� ���������� �� ������
  FUNCTION GetClaimInfo (ClaimID IN NUMBER) RETURN IKFLTAClaimParm;

  -- ������� ���������� ��������� ����������� �������� ���������� ������ �� �������
  FUNCTION lnGetRateVal (ObjectTypeID IN NUMBER, ObjectNumber IN NUMBER, RateType IN NUMBER) RETURN FLOAT;

  -- ������� ���������� ��������� ����������� �������� ����������������� ���� �� �������
  FUNCTION  GetUsFiledValue(ObjectTypeID IN NUMBER, ObjectNumber IN NUMBER, FieldType IN NUMBER) RETURN VARCHAR2;

  -- ������� ��������� �������� ���������-�������
  FUNCTION GetFieldParmID(UsFieldID IN NUMBER) RETURN VARCHAR2;

  -- ������� ��������� - ������� �� ������� �������� ������� �� ���������������� ���� ��� ���
  FUNCTION usfSaveHistory(ObjectTypeID IN NUMBER, FieldType IN NUMBER) RETURN BOOLEAN;

  -- ����������, �������������� �-���� ModifyClaim
  eNO_CLAIM_FOUND             EXCEPTION;  -- �� ������� ��������� ������ �� ID
  eINCORRECT_USERFIELD_NUMBER EXCEPTION;        -- �������� ����� ����������������� ���� (������������ ������ ID ���������)
  eINCORRECT_PARMID           EXCEPTION;  -- ������������ ID ��������� (�������� �� ��������������)
  eNO_LOANER_FOUND            EXCEPTION;  -- �� ������ �������
  eDUP_ADRESSF                EXCEPTION;  -- ����������� ����������� �����
  eDUP_ADRESS                 EXCEPTION;  -- ����������� ����������� �����
  eDUP_PAPER                  EXCEPTION;  -- ����������� ������������� ��������
  eNO_DATE                    EXCEPTION;  -- �� ���������� ���� (�������)
  eNO_DATA_PERSN              EXCEPTION;  -- �� ���������� ������ �� �������� (���, ���� ��������)
  eNO_CREDIT_TYPE             EXCEPTION;  -- �� ��������� ��� �������
  eINCORRECT_TEMPLATE         EXCEPTION;  -- ������������ ������ ��������� ������
  eNO_TEMPLATE                EXCEPTION;  -- �� ����� ������ ��������� ������
  eNO_CLAIM_DATA              EXCEPTION;  -- �� ������ ������ �� ������ ��� �����
  eINCORRECT_CLIENT_CODE      EXCEPTION;  -- �� ������� ������������ ��� �������
  eINCORRECT_DATE             EXCEPTION;  -- ������������ ����

  PRAGMA EXCEPTION_INIT (eNO_CLAIM_FOUND,              -20001);
  PRAGMA EXCEPTION_INIT (eINCORRECT_USERFIELD_NUMBER,  -20002);
  PRAGMA EXCEPTION_INIT (eINCORRECT_PARMID,            -20003);
  PRAGMA EXCEPTION_INIT (eNO_LOANER_FOUND,             -20004);
  PRAGMA EXCEPTION_INIT (eDUP_ADRESSF    ,             -20005);
  PRAGMA EXCEPTION_INIT (eDUP_ADRESS     ,             -20006);
  PRAGMA EXCEPTION_INIT (eDUP_PAPER      ,             -20007);
  PRAGMA EXCEPTION_INIT (eNO_DATE        ,             -20008);
  PRAGMA EXCEPTION_INIT (eNO_DATA_PERSN  ,             -20009);
  PRAGMA EXCEPTION_INIT (eNO_CREDIT_TYPE ,             -20010);
  PRAGMA EXCEPTION_INIT (eINCORRECT_TEMPLATE,          -20011);
  PRAGMA EXCEPTION_INIT (eNO_TEMPLATE,                 -20012);
  PRAGMA EXCEPTION_INIT (eNO_CLAIM_DATA,               -20014);
  PRAGMA EXCEPTION_INIT (eINCORRECT_CLIENT_CODE,       -20015);
  PRAGMA EXCEPTION_INIT (eINCORRECT_DATE,              -20016);

  LO_CREDIT     CONSTANT PLS_INTEGER := 1;   -- ��������� �������
  LO_CLAIM      CONSTANT PLS_INTEGER := 3;   -- ������
  LO_TYPECREDIT CONSTANT PLS_INTEGER := 7;   -- ��� �������
  NULLDATE      CONSTANT DATE        := TO_DATE ('1.1.1', 'dd.mm.yyyy');

END IKFL;
/