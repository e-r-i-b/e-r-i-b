CREATE OR REPLACE PACKAGE IKFL AS

 --преобразование адреса из строки
  FUNCTION lnGetDate (StrDate IN VARCHAR2) RETURN Date;
                      
  -- Обновляемые поля заявки
  PROCEDURE ParseSystemClaimsField(ClaimParm IN OUT IKFLTCLAIMPARM,claim_rec IN OUT dclaim_dbt%ROWTYPE,ClaimNumID IN OUT NUMBER,ParamNumber IN NUMBER,IsUpdate IN OUT BOOLEAN );  
  --обновляем данные клиента
  PROCEDURE ParseSystemPersonsField(ClaimParm IN IKFLTCLAIMPARM,persn_rec IN OUT dpersn_dbt%ROWTYPE,IsUpdate IN OUT BOOLEAN);      
  -- Обновляемые поля удостоверения личности
  PROCEDURE ParseSystemDocumentField(ClaimParm IN IKFLTCLAIMPARM,persnidc_rec IN OUT dpersnidc_dbt%ROWTYPE,IsUpdate IN OUT BOOLEAN);
  -- Обновляемые поля адреса
  PROCEDURE ParseSystemAddressField(ClaimParm IN IKFLTCLAIMPARM,adress_rec IN OUT dadress_dbt%ROWTYPE,IsUpdate IN OUT BOOLEAN);  
  -- Служебные параметры (возвращаемые)                 
  PROCEDURE ParseSystemErrorField(ClaimParm IN OUT IKFLTCLAIMPARM,ParamNumber IN NUMBER, ErrCodeID IN OUT NUMBER ,ErrDescID IN OUT NUMBER); 

  PROCEDURE ProceedUserField(ClaimID IN NUMBER, ClaimParm IN IKFLTCLAIMPARM);    
  --получаем текущую дату(tom)
  FUNCTION GetCurrentDate RETURN DATE;
    
  -- Функция получения списка видов кредитов
  FUNCTION GetListTypeCrd(TypeCrdCode IN NUMBER, CrdKind IN NUMBER, UsField1 IN NUMBER, UsField2 IN NUMBER, UsField3 IN NUMBER, UsField4 IN NUMBER, UsField5 IN NUMBER) RETURN IKFLTATYPECRDLST;
  
  -- Функция получения информации по видам кредита,CreditTypeIDs - массив идентификаторов,UsFields - массив номеров доп. полей. 
  FUNCTION GetCrdTypeInfo(CreditTypeIDs IN IKFLTANumber, UsFields IN IKFLTANumber) RETURN IKFLTACrdType;
  
  --получение лимита
  FUNCTION GetLimitValue(CreditTypeID IN NUMBER) RETURN NUMBER;

  -- Процедура вставки/обновления информации по заявке
  -- ClaimID - идент. созданой заявки, либо идент. завки для редактирования
  -- DebitorClaimID- заявка заемщика,если создаем заявку для поручителя иначе NULL 
  FUNCTION ModifyClaim  (ClaimParm IN OUT IKFLTACLAIMPARM, ClaimID IN OUT NUMBER, DebitorClaimID IN NUMBER,DebitorContractNumber IN NUMBER) RETURN NUMBER;  

  -- Функция получения статусов заявок
  PROCEDURE GetClaimState (Claims IN OUT IKFLTAClaimState);

  -- Функция получения графика погашения
  FUNCTION GetPlanpay (ClaimID IN NUMBER) RETURN IKFLTAPlanpay;

  -- Функция получения информации по заявке
  FUNCTION GetClaimInfo (ClaimID IN NUMBER) RETURN IKFLTAClaimParm;

  -- Функция возвращает последнее действующее значение процентной ставки по объекту
  FUNCTION lnGetRateVal (ObjectTypeID IN NUMBER, ObjectNumber IN NUMBER, RateType IN NUMBER) RETURN FLOAT;

  -- Функция возвращает последнее действующее значение пользовательского поля по объекту
  FUNCTION  GetUsFiledValue(ObjectTypeID IN NUMBER, ObjectNumber IN NUMBER, FieldType IN NUMBER) RETURN VARCHAR2;

  -- Функция формирует название параметра-допполя
  FUNCTION GetFieldParmID(UsFieldID IN NUMBER) RETURN VARCHAR2;

  -- Функция проверяет - взведен ли признак хренения истории на пользовательском поле или нет
  FUNCTION usfSaveHistory(ObjectTypeID IN NUMBER, FieldType IN NUMBER) RETURN BOOLEAN;

  -- Исключения, обрабатвыаемые ф-цией ModifyClaim
  eNO_CLAIM_FOUND             EXCEPTION;  -- Не найдена кредитная заявка по ID
  eINCORRECT_USERFIELD_NUMBER EXCEPTION;        -- Неверный номер пользовательского поля (некорректный формат ID параметра)
  eINCORRECT_PARMID           EXCEPTION;  -- Некорректный ID параметра (параметр не обрабатывается)
  eNO_LOANER_FOUND            EXCEPTION;  -- Не найден заемщик
  eDUP_ADRESSF                EXCEPTION;  -- Дублируется юридический адрес
  eDUP_ADRESS                 EXCEPTION;  -- Дублируется фактический адрес
  eDUP_PAPER                  EXCEPTION;  -- Дублируется удостоверение личности
  eNO_DATE                    EXCEPTION;  -- Не определена дата (опердня)
  eNO_DATA_PERSN              EXCEPTION;  -- Не определены данные по заемщику (ФИО, дата рождения)
  eNO_CREDIT_TYPE             EXCEPTION;  -- Не определен вид кредита
  eINCORRECT_TEMPLATE         EXCEPTION;  -- Некорректный шаблон нумерации заявок
  eNO_TEMPLATE                EXCEPTION;  -- Не задан шаблон нумерации заявок
  eNO_CLAIM_DATA              EXCEPTION;  -- Не заданы данные по заявке при вводе
  eINCORRECT_CLIENT_CODE      EXCEPTION;  -- Не удалось сформировать код клиента
  eINCORRECT_DATE             EXCEPTION;  -- Некорректная дата

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

  LO_CREDIT     CONSTANT PLS_INTEGER := 1;   -- Кредитный договор
  LO_CLAIM      CONSTANT PLS_INTEGER := 3;   -- Заявка
  LO_TYPECREDIT CONSTANT PLS_INTEGER := 7;   -- Вид кредита
  NULLDATE      CONSTANT DATE        := TO_DATE ('1.1.1', 'dd.mm.yyyy');

END IKFL;
/