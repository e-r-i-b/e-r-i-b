CREATE OR REPLACE PACKAGE BODY MOSSIB.IKFL AS


  -- Процедура для вывода в лог-файл
  -- для включения лога выставить trace :=true
  -- для создания таблицы лога выполнить скрипт
  -- CREATE TABLE LogIKFL (what VARCHAR(80),val VARCHAR2(250),start_time TIMESTAMP(2));
  procedure Log(what in VARCHAR2, val in VARCHAR2)
  is
    trace BOOLEAN :=false;
  begin
        if trace then
        insert into logikfl (what,val,start_time)
        values (what,val,SYSTIMESTAMP);
        end if;
  end Log;
  
  -- Функция проверяет - взведен ли признак хренения истории на пользовательском поле или нет
  FUNCTION usfSaveHistory(ObjectTypeID IN NUMBER, FieldType IN NUMBER) RETURN BOOLEAN
  IS
     cntFlag  NUMBER := 0;
  BEGIN
     SELECT count(*) INTO cntFlag from DLUSFFLAG_DBT
                   WHERE t_ObjectID_Ref  = ObjectTypeID
                               AND t_UsFieldID_Ref = FieldType
                               AND t_Flag          = 'X';
     IF cntFlag = 0 THEN
        RETURN FALSE;
     END IF;
     RETURN TRUE;
  END usfSaveHistory;
    -- определение номера филиала по номеру кредитного офиса 
    FUNCTION getCodeGroup(OrgCode IN NUMBER)RETURN NUMBER
    IS
    BEGIN
        IF((OrgCode = 500)OR(OrgCode = 1)OR(OrgCode = 2)OR(OrgCode = 3))THEN /*"Головной офис Москва"*/
            RETURN 1;
        ELSIF(OrgCode = 700)THEN /*"ККО Калужский"*/
            RETURN 2;
        ELSIF(OrgCode = 800)THEN /*"ККО Ярославский"*/
            RETURN 3;
        ELSIF(OrgCode = 4) THEN /*"ККО Воронежский"*/
            RETURN 4;
        END IF;
        /* по остальным, начиная с Воронежа, коды совпадают*/
        RETURN OrgCode;
    END getCodeGroup;  

  PROCEDURE InsertUsFieldValue(usFObjTypeID_Ref IN NUMBER, ObjectTypeID IN NUMBER, ObjectNumber IN NUMBER, FieldType IN NUMBER, FieldValue IN VARCHAR2, ValDate IN DATE, Field IN NUMBER)
  IS
  BEGIN
     Log(' procedure InsertUsFieldValue ','usFObjTypeID_Ref='||usFObjTypeID_Ref||' FieldValue='||TRIM(FieldValue));
     IF (FieldValue IS NULL) OR (TRIM(FieldValue) = '') OR (TRIM(FieldValue) IS NULL) THEN
        RETURN;
     END IF;

     IF usfSaveHistory(ObjectTypeID, FieldType) THEN
     -- Пользовательское поле поддерживает историю - добавим новое значение
         BEGIN
                 -- Попытаемся добавить новое значение в историю
         INSERT INTO DLUFVALUE_DBT (t_UsFObjTypeID_Ref, t_UsFieldValue, t_UsFieldValDate, t_IsOperating, t_Reserve)
                             VALUES(usFObjTypeID_Ref, TRIM(FieldValue), ValDate, 'X', CHR(1));
         Log('INSERT INTO DLUFVALUE_DBT ','usFObjTypeID_Ref='||usFObjTypeID_Ref||' FieldValue='||TRIM(FieldValue));
-- для проверки
/*IF ((Field = 15108) or (Field=15109) or (Field=15110) or (Field=15111)or (Field=15112)) THEN
 INSERT INTO FORTEST_DBT (t_UsFObjTypeID_Ref, t_UsFieldValue, t_UsFieldValDate, t_IsOperating, t_Reserve)
                       VALUES(ObjectNumber, FieldValue, SYSDATE, '','1');
 INSERT INTO FORTEST_DBT (t_UsFObjTypeID_Ref, t_UsFieldValue, t_UsFieldValDate, t_IsOperating, t_Reserve)
                       VALUES(ObjectNumber, trim(FieldValue), SYSDATE, '','1 trim');
END IF;  */
-- стереть

         EXCEPTION WHEN DUP_VAL_ON_INDEX THEN
                 -- Уже есть значение на заданную дату - обновим его
        UPDATE DLUFVALUE_DBT SET t_UsFieldValue = TRIM(FieldValue), t_IsOperating  = 'X'
                         WHERE t_UsFObjTypeID_Ref = usFObjTypeID_Ref AND t_UsFieldValDate = ValDate;
         Log('DLUFVALUE_DBT DLUFVALUE_DBT ','usFObjTypeID_Ref='||usFObjTypeID_Ref||' FieldValue='||TRIM(FieldValue));
-- для проверки
/*IF ((Field = 15108) or (Field=15109) or (Field=15110) or (Field=15111)or (Field=15112)) THEN
 INSERT INTO FORTEST_DBT (t_UsFObjTypeID_Ref, t_UsFieldValue, t_UsFieldValDate, t_IsOperating, t_Reserve)
                       VALUES(ObjectNumber, FieldValue, SYSDATE, '','2');
 INSERT INTO FORTEST_DBT (t_UsFObjTypeID_Ref, t_UsFieldValue, t_UsFieldValDate, t_IsOperating, t_Reserve)
                       VALUES(ObjectNumber, trim(FieldValue), SYSDATE, '','2 trim');
END IF; */
-- стереть
         END;
     ELSE -- Пользовательское поле историю не поддерживает
            -- Удалим старое значение, чтобы не морочить голову с датами
            DELETE FROM DLUFVALUE_DBT WHERE t_UsFObjTypeID_Ref = usfObjTypeID_Ref;
            Log('DELETE FROM DLUFVALUE_DBT','usFObjTypeID_Ref='||usFObjTypeID_Ref);
            -- Добавим новое значение
            INSERT INTO DLUFVALUE_DBT (t_UsFObjTypeID_Ref, t_UsFieldValue, t_UsFieldValDate, t_IsOperating, t_Reserve)
                       VALUES(usFObjTypeID_Ref, TRIM(FieldValue), ValDate, 'X', CHR(1));
            Log('INSERT INTO DLUFVALUE_DBT','usFObjTypeID_Ref='||usFObjTypeID_Ref||' FieldValue='||TRIM(FieldValue));
-- для проверки
/*IF ((Field = 15108) or (Field=15109) or (Field=15110) or (Field=15111)or (Field=15112)) THEN
 INSERT INTO FORTEST_DBT (t_UsFObjTypeID_Ref, t_UsFieldValue, t_UsFieldValDate, t_IsOperating, t_Reserve)
                        VALUES(ObjectNumber, FieldValue, SYSDATE, '','3');
 INSERT INTO FORTEST_DBT (t_UsFObjTypeID_Ref, t_UsFieldValue, t_UsFieldValDate, t_IsOperating, t_Reserve)
                        VALUES(ObjectNumber, trim(FieldValue), SYSDATE, '','3 trim');
END IF; */
-- стереть
     END IF;
  END InsertUsFieldValue;

   -- Функция возвращает последнее действующее значение процентной ставки по объекту
   FUNCTION lnGetRateVal (ObjectTypeID IN NUMBER, ObjectNumber IN NUMBER, RateType IN NUMBER) RETURN FLOAT
   IS
      RateVal  FLOAT(53) := 0.0;
   BEGIN
         SELECT t_RateVal INTO RateVal
                      FROM DLCHISTRY_DBT
                             WHERE t_RateID_Ref = (SELECT t_RateID_Ref
                                                      FROM dlcusrate_dbt
                                                     WHERE t_ObjectID_Ref = ObjectTypeID
                                                       AND t_CredObjID_Ref = ObjectNumber
                                                       AND t_TypeRateID_Ref = RateType)
                       AND t_RateState = 2
                       AND t_RateDate = (SELECT MAX(t_RateDate)
                                                   FROM dlchistry_dbt b
                                                  WHERE  b.t_RateID_Ref = (SELECT t_RateID_Ref
                                                                             FROM dlcusrate_dbt
                                                                            WHERE t_ObjectID_Ref = ObjectTypeID
                                                                              AND t_CredObjID_Ref = ObjectNumber
                                                                              AND t_TypeRateID_Ref = RateType)
                                                                              AND b.t_RateState = 2);
         RETURN RateVal;
   EXCEPTION WHEN OTHERS
             THEN RETURN 0.0;
   END lnGetRateVal;

   -- Функция возвращает последнее действующее значение пользовательского поля по объекту
   FUNCTION  GetUsFiledValue(ObjectTypeID IN NUMBER, ObjectNumber IN NUMBER, FieldType IN NUMBER) RETURN VARCHAR2
   IS
      FieldValue VARCHAR2(99);
   BEGIN
      IF FieldType IS NULL 
      THEN RETURN ' ';
       END IF;
      
      SELECT t_UsFieldValue INTO FieldValue FROM dlufvalue_dbt
                            WHERE   t_UsFObjTypeID_Ref = (SELECT t_UsFObjTypeID FROM DLUFOTYPE_DBT
                                                           WHERE t_ObjectID_Ref = ObjectTypeID
                                                            AND t_ObjID_Ref = ObjectNumber
                                                            AND t_UsFieldID_Ref = FieldType)
                                          AND  t_IsOperating = 'X'
                                          AND  t_UsFieldValDate = (SELECT MAX(t_UsFieldValDate) FROM dlufvalue_dbt b
                                                WHERE b.t_UsFObjTypeID_Ref = (SELECT t_UsFObjTypeID FROM DLUFOTYPE_DBT
                                                                         WHERE t_ObjectID_Ref = ObjectTypeID
                                                                           AND t_ObjID_Ref = ObjectNumber
                                                                           AND t_UsFieldID_Ref = FieldType)
                                                                           AND b.t_IsOperating = 'X');
      RETURN FieldValue;
   EXCEPTION WHEN OTHERS
             THEN RETURN ' ';
   END;

   -- Функция формирует название параметра-допполя
   FUNCTION GetFieldParmID(UsFieldID IN NUMBER) RETURN VARCHAR2
   IS
      FieldName   VARCHAR2(59);
   BEGIN
      SELECT t_UsFieldName INTO FieldName FROM DLUFIELD_DBT WHERE t_UsFieldID = UsFieldID;
          RETURN TRIM(TO_CHAR(UsFieldID));
   EXCEPTION WHEN OTHERS
      THEN RETURN TRIM(TO_CHAR(UsFieldID));
   END GetFieldParmID;

-- Функция получения информации по видам кредита,CreditTypeIDs - массив идентификаторов,UsFields - массив номеров доп. полей. 
  FUNCTION GetCrdTypeInfo(CreditTypeIDs IN IKFLTANumber, UsFields IN IKFLTANumber) RETURN IKFLTACrdType
  IS
    crdKind NUMBER;
    crdIndex NUMBER;
    userIndex NUMBER;
    creditTypeID NUMBER;
    crdtypeList IKFLTACrdType := IKFLTACrdType();
    i NUMBER;
    k NUMBER;
    crdType IKFLTCrdType := IKFLTCrdType(0,'','',0,0,0,0,0,'',0,0,null);
    TYPE CRDIINFOCURSORTYPE IS REF CURSOR;
    crdInfoCursor CRDIINFOCURSORTYPE;
  BEGIN
    DBMS_OUTPUT.PUT_LINE('start');
    crdIndex := CreditTypeIDs.FIRST;
    i := 1;      
    WHILE crdIndex <= CreditTypeIDs.LAST LOOP
        creditTypeID := CreditTypeIDs(crdIndex);
        DBMS_OUTPUT.PUT_LINE('while crediTYPE');
        OPEN crdInfoCursor FOR 
        SELECT dtype_crd_dbt.t_CreditTypeNumber Code,
              dtype_crd_dbt.t_CreditTypeName CreditTypeName,
              dfininstr_dbt.t_CCY CurCodeISO,
              dfininstr_dbt.t_ISO_NUMBER CurCode,
              dtype_crd_dbt.t_Duration Duration,
              dtype_crd_dbt.t_TypeDuration TypeDuration,
              dtype_crd_dbt.t_Credit_Overdraft LimitOver,
              dtype_crd_dbt.t_CreditTypeID CreditTypeID,
              dtype_crd_dbt.t_crd_kind crdKind 
        FROM dtype_crd_dbt, dfininstr_dbt
        WHERE dtype_crd_dbt.t_CreditTypeID = creditTypeID
          AND dtype_crd_dbt.t_curcode = dfininstr_dbt.T_FIID;
         
        FETCH crdInfoCursor into
        crdType.Code,crdType.CreditTypeName,crdType.CurCodeISO,
        crdType.CurCode,crdType.Duration,crdType.TypeDuration,
        crdType.LimitOver,crdType.CreditTypeID, crdKind;
        
        IF crdInfoCursor%FOUND THEN
        
        DBMS_OUTPUT.PUT_LINE('IF');
                               
        crdtypeList.EXTEND;
                                                     
        crdtypeList(i) := IKFLTCrdType (0,'','',0,0,0,0,0,'',0,0,null);
        crdtypeList(i).Code           := crdType.Code;
        crdtypeList(i).CreditTypeName := crdType.CreditTypeName;
        crdtypeList(i).CurCodeISO     := crdType.CurCodeISO;
        crdtypeList(i).CurCode        := crdType.CurCode;
        crdtypeList(i).Duration       := crdType.Duration;
        crdtypeList(i).TypeDuration   := crdType.TypeDuration;
        crdtypeList(i).LimitOver      := crdType.LimitOver;
        crdtypeList(i).CreditTypeID   := crdType.CreditTypeID;                                                                    
        IF crdKind = 0 THEN
            crdtypeList(i).MainRate       := lnGetRateVal(LO_TYPECREDIT, CreditTypeIDs(crdIndex) , 1);                 
        ELSE -- кредитная карта
            crdtypeList(i).MainRate       := lnGetRateVal(LO_TYPECREDIT, CreditTypeIDs(crdIndex) , 1000);
        END IF;
        crdtypeList(i).LimitVal       := GetLimitValue(CreditTypeIDs(crdIndex));        
        crdtypeList(i).PenaltyPerDay  := lnGetRateVal(LO_TYPECREDIT, CreditTypeIDs(crdIndex) , 3);
      
        crdtypeList(i).AdditionFields := IKFLTAUSERFIELD();
        userIndex := UsFields.FIRST;
        k := 1; 
        WHILE userIndex <= UsFields.LAST LOOP
            DBMS_OUTPUT.PUT_LINE('WHILE USEFIELDS');
            crdtypeList(i).AdditionFields.EXTEND;
                                    
            crdtypeList(i).AdditionFields(k) := IKFLTUserField(0, '');
            
            crdtypeList(i).AdditionFields(k).UserFieldID := UsFields(userIndex); 
            crdtypeList(i).AdditionFields(k).UserFieldValue := GetUsFiledValue(LO_TYPECREDIT, CreditTypeIDs(crdIndex),  UsFields(userIndex));            
                        
            k := k+1;
            userIndex := UsFields.NEXT(userIndex);  
        END LOOP;                
        
        END IF;        
        
        CLOSE crdInfoCursor;
        
        i := i + 1;                                 
        crdIndex := CreditTypeIDs.NEXT(crdIndex);
                     
    END LOOP;
    RETURN crdtypeList;     
  END GetCrdTypeInfo;  
   FUNCTION GetListTypeCrd(TypeCrdCode IN NUMBER, CrdKind IN NUMBER, UsField1 IN NUMBER, UsField2 IN NUMBER, UsField3 IN NUMBER, UsField4 IN NUMBER, UsField5 IN NUMBER) RETURN IKFLTATypeCrdLst
   IS
      curdate DATE;
      type_crdLST  IKFLTATypeCrdLst := IKFLTATypeCrdLst();
      i   NUMBER;
   BEGIN
      i := 1;
      SELECT MAX(t_CurDate) INTO curdate from dcurdate_dbt;
      FOR type_crd IN (SELECT dtype_crd_dbt.t_CreditTypeNumber Code,
                              dtype_crd_dbt.t_CreditTypeName CreditTypeName,
                              dfininstr_dbt.t_CCY CurCodeISO,
                              dfininstr_dbt.t_ISO_NUMBER CurCode,
                                        dtype_crd_dbt.t_Duration Duration,
                                        dtype_crd_dbt.t_TypeDuration TypeDuration,
                                        dtype_crd_dbt.t_Credit_Overdraft LimitOver,
                                        dtype_crd_dbt.t_CreditTypeID CreditTypeID
                       FROM dtype_crd_dbt, dfininstr_dbt
                       WHERE ( (TypeCrdCode is null) or (dtype_crd_dbt.t_CreditTypeNumber = TypeCrdCode))
                        AND (dtype_crd_dbt.t_Crd_Kind = CrdKind OR CrdKind = 2)
                        AND dfininstr_dbt.t_FIID = dtype_crd_dbt.t_CurCode)
     LOOP
         type_crdLST.Extend;
         type_crdLST(i) := IKFLTTypeCrdLst (0, '', '', 0,0,0,0,0,'',0,0,0,0,0,0);
         type_crdLST(i).Code           := type_crd.Code;
         type_crdLST(i).CreditTypeName := type_crd.CreditTypeName;
         type_crdLST(i).CurCodeISO     := type_crd.CurCodeISO;
         type_crdLST(i).CurCode        := type_crd.CurCode;
         type_crdLST(i).Duration       := type_crd.Duration;
         type_crdLST(i).TypeDuration   := type_crd.TypeDuration;
         type_crdLST(i).LimitOver      := type_crd.LimitOver;
         type_crdLST(i).CreditTypeID   := type_crd.CreditTypeID;
         type_crdLST(i).UserField1     := GetUsFiledValue(LO_TYPECREDIT, type_crd.CreditTypeID, UsField1);
         type_crdLST(i).UserField2     := GetUsFiledValue(LO_TYPECREDIT, type_crd.CreditTypeID, UsField2);
         type_crdLST(i).UserField3     := GetUsFiledValue(LO_TYPECREDIT, type_crd.CreditTypeID, UsField3);
         type_crdLST(i).UserField4     := GetUsFiledValue(LO_TYPECREDIT, type_crd.CreditTypeID, UsField4);
                 type_crdLST(i).UserField5     := GetUsFiledValue(LO_TYPECREDIT, type_crd.CreditTypeID, UsField5);
         i := i + 1;
     END LOOP;

      i := type_crdLST.FIRST;
          LOOP
          EXIT WHEN i IS NULL;
         type_crdLST(i).MainRate := lnGetRateVal(7, type_crdLST(i).CREDITTYPEID, 1);
         BEGIN
            SELECT t_MaxValue INTO type_crdLST(i).LimitVal
                              FROM dhist_lim_dbt
                             WHERE t_CreditTypeID_Ref = type_crdLST(i).CREDITTYPEID
                      AND t_Date = (SELECT MAX(t_DATE)
                                      FROM dhist_lim_dbt b
                                     WHERE b.t_CreditTypeID_Ref = type_crdLST(i).CREDITTYPEID
                                       AND b.t_Date <= curdate);
                 EXCEPTION WHEN NO_DATA_FOUND
                           THEN type_crdLST(i).LimitVal := 0;
             END;

                 i := type_crdLST.Next(i);
          END LOOP;

        RETURN type_crdLST;
   END GetListTypeCrd;
   
    --получение лимита
    FUNCTION GetLimitValue(CreditTypeID IN NUMBER) RETURN NUMBER
    IS
        curdate Date;
        LimitVal NUMBER;
    BEGIN
        SELECT MAX(t_CurDate) INTO curdate from dcurdate_dbt;
        
        SELECT t_MaxValue INTO LimitVal
        FROM dhist_lim_dbt
        WHERE t_CreditTypeID_Ref = CreditTypeID
            AND t_Date = 
                (SELECT MAX(t_DATE)
                    FROM dhist_lim_dbt b
                    WHERE b.t_CreditTypeID_Ref = CreditTypeID
                       AND b.t_Date <= curdate);
        RETURN LimitVal;
                               
        EXCEPTION WHEN NO_DATA_FOUND
        THEN RETURN 0;              
    END GetLimitValue;
    --
    FUNCTION GetClaimNumber(ClaimID IN NUMBER) RETURN VARCHAR2
    IS
         claimNum   VARCHAR2(15) := '';
    BEGIN
       SELECT t_ClaimNumber INTO claimNum FROM DCLAIM_DBT WHERE t_ClaimID = ClaimID;
       RETURN claimNum;  
    END GetClaimNumber;
   
  -- Процедура вставки/обновления информации по заявке
  -- ClaimID - идент. созданой заявки, либо идент. завки для редактирования
  -- DebitorClaimID- заявка заемщика,если создаем заявку для поручителя иначе NULL 
   FUNCTION ModifyClaim  (ClaimParm IN OUT IKFLTAClaimParm, ClaimID IN OUT NUMBER, DebitorClaimID IN NUMBER,DebitorContractNumber IN NUMBER) RETURN NUMBER
   IS
         i             NUMBER;
         FieldTypeID   NUMBER(10);
         usfObjTypeID  NUMBER(10);
         ClmFld        NUMBER(10);
         PartyID       NUMBER(10);
         ErrCodeID     NUMBER(10);
         ErrDescID     NUMBER(10);
         ClaimNumID    NUMBER(10);
         curdate       DATE  := TO_DATE ('1.1.1', 'dd.mm.yyyy');

         claim_rec     dclaim_dbt%ROWTYPE    := NULL;
         persn_rec     dpersn_dbt%ROWTYPE    := NULL;
         persnidc_rec  dpersnidc_dbt%ROWTYPE := NULL;
         adress_rec    dadress_dbt%ROWTYPE   := NULL;
         adressf_rec   dadress_dbt%ROWTYPE   := NULL;
         adr_rec       dadress_dbt%ROWTYPE   := NULL;

         ParmName      VARCHAR2(70);
         fullAddr      VARCHAR2(511) := NULL;
         OrgCode       VARCHAR2(1)   := NULL;

         claimupd      BOOLEAN               := FALSE;
         persnupd      BOOLEAN               := FALSE;
         persnidcupd   BOOLEAN               := FALSE;
         adrupd        BOOLEAN               := FALSE;
         adrfupd       BOOLEAN               := FALSE;
         PercRate      BOOLEAN               := FALSE;

         persnidc      BOOLEAN               := TRUE;
         adress        BOOLEAN               := TRUE;
         adressf       BOOLEAN               := TRUE;

         oldDebitorClaimNumbers VARCHAR(128);
    -- Процедура добавляет в массив параметров два параметра с описанием ошибки (или обновляет)
    PROCEDURE CreateErrMessage(ErrMes IN VARCHAR2, ErrCode IN NUMBER)
    IS
    BEGIN
       IF ErrCodeID = 0 THEN
          IF claimparm.LAST IS NOT NULL THEN
             ErrCodeID := claimparm.LAST + 1;
          ELSE
             ErrCodeID := 1;
          END IF;
          claimparm.Extend;
          claimparm(ErrCodeID) :=IKFLTClaimParm('','');
          claimparm(ErrCodeID).ParmID := 'errCode';
       END IF;
       claimparm(ErrCodeID).ParmValue := ErrCode;

       IF ErrDescID = 0 THEN
          ErrDescID := claimparm.LAST + 1;
          claimparm.Extend;
          claimparm(ErrDescID) :=IKFLTClaimParm('','');
          claimparm(ErrDescID).ParmID := 'errDesc';
       END IF;
       claimparm(ErrDescID).ParmValue := ErrMes;

    END CreateErrMessage;

    -- Процедура иницилизирует записи корректными NULL-значениями
    PROCEDURE InitRecords
    IS
    BEGIN

       persn_rec.t_Name1            := ' ';
       persn_rec.t_Name2            := ' ';
       persn_rec.t_Name3            := ' ';
       persn_rec.t_BIRSPLASE        := ' ';
       persn_rec.t_Ethnos           := CHR(1);
       persn_rec.t_LicenceNUMBER    := CHR(1);
       persn_rec.t_REGIONBORN       := CHR(1);
       persn_rec.T_RAIONBORN        := CHR(1);
       persn_rec.t_PLACEBORN        := CHR(1);
       persn_rec.t_PlaceWORK        := CHR(1);
       persn_rec.t_PensCARDNUMBER   := CHR(1);
       persn_rec.T_RESERVE          := CHR(1);
       persn_rec.T_BORN             := NULLDATE;
       persn_rec.t_LICENCEDATE      := NULLDATE;
       persn_rec.t_DEATH            := NULLDATE;
       persn_rec.t_PENSCARDDATE     := NULLDATE;
       persn_rec.t_IsEmployer       := CHR(0);
       persn_rec.t_GROUPAUTHOR      := CHR(0);
       persn_rec.t_GROUPWIL         := CHR(0);
       persn_rec.t_IsLiterate       := CHR(0);
       persn_rec.t_SpecialAccess    := CHR(0);
       persn_rec.t_VZAIMOSVYAZANNIY := CHR(0);
       persn_rec.T_OFFSHORERESIDENT := CHR(0);
       persn_rec.t_KATEGOR    := 0;
       persn_rec.t_Branch     := 0;
       persn_rec.t_NumSession := 0;

       claim_rec.t_CreditNumber_Ref := 0;
       claim_rec.t_LCREDITSUM       := 0;
       claim_rec.t_LCreditCurCode   := 0;
       claim_rec.t_lSelfSum         := 0;
       claim_rec.t_LSelfCurCode     := 0;
       claim_rec.t_LCreditDuration  := 0;
       claim_rec.t_LCTypeDuration   := 0;
       claim_rec.t_LPTypeDuration   := 0;
       claim_rec.t_LPayDuration     := 1;
       claim_rec.t_LPercRate        := 0;
       claim_rec.t_BCreditSum       := 0;
       claim_rec.t_BCreditCurCode   := 0;
       claim_rec.t_BPaySum          := 0;
       claim_rec.t_BPayCurCode      := 0;
       claim_rec.t_BCreditDuration  := 0;
       claim_rec.t_BCTypeDuration   := 0;
       claim_rec.t_BPayDuration     := 1;
       claim_rec.t_BPTypeDuration   := 0;
       claim_rec.t_BPercrate        := 0;
       claim_rec.t_CreditTypeID_Ref := 0;
       claim_rec.t_GroupNum         := 999;
       claim_rec.t_SumObject        := 0;
       claim_rec.t_SumObjectCurCode := 0;
       claim_rec.t_CredComDecision  := 0;
       claim_rec.t_CredComID_Ref    := 0;
       claim_rec.t_FnCash           := 0;
       claim_rec.t_MRK              := 0;
       claim_rec.t_TuneType         := 1;
       claim_rec.t_KIND_OPERATION   := 600;
       claim_rec.t_Status           := 1;
       claim_rec.t_BlockOper        := 0;
       claim_rec.t_ClaimKind        := 0;
       claim_rec.t_GivingDate       := curdate;
       claim_rec.t_ApprovalDate     := NULLDATE;
       claim_rec.t_Rejectiondate    := NULLDATE;
       claim_rec.t_ClaimState       := 20;
       claim_rec.t_LCreditPurpose   := CHR(1);
       claim_rec.t_RejectMotive     := CHR(1);
       claim_rec.t_Comment          := CHR(1);
       claim_rec.t_Note             := CHR(1);
       claim_rec.t_ClaimNumber      := '00/0';

       persnidc_rec.T_PERSONID         := 0;
       persnidc_rec.T_PAPERKIND        := 0;
       persnidc_rec.T_PAPERSERIES      := CHR(1);
       persnidc_rec.T_PAPERNUMBER      := CHR(1);
       persnidc_rec.T_PAPERISSUEDDATE  := NULLDATE;
       persnidc_rec.T_PAPERISSUER      := CHR(1);
       persnidc_rec.T_PAPERISSUERCODE  := CHR(1);
       persnidc_rec.T_ISMAIN           := 'X';
       persnidc_rec.T_BRANCH           := 0;
       persnidc_rec.T_NUMSESSION       := 0;

       adress_rec.T_PARTYID         := 0;
       adress_rec.T_TYPE            := 1;
       adress_rec.T_ADRESS          := CHR(1);
       adress_rec.T_COUNTRY         := CHR(1);
       adress_rec.T_POSTINDEX       := CHR(1);
       adress_rec.T_REGION          := CHR(1);
       adress_rec.T_CODEPROVINCE    := 4;
       adress_rec.T_PROVINCE        := CHR(1);
       adress_rec.T_CODEDISTRICT    := 0;
       adress_rec.T_DISTRICT        := CHR(1);
       adress_rec.T_CODEPLACE       := 6;
       adress_rec.T_PLACE           := CHR(1);
       adress_rec.T_CODESTREET      := 76;
       adress_rec.T_STREET          := CHR(1);
       adress_rec.T_HOUSE           := CHR(1);
       adress_rec.T_NUMCORPS        := CHR(1);
       adress_rec.T_FLAT            := CHR(1);
       adress_rec.T_PHONENUMBER     := CHR(1);
       adress_rec.T_PHONENUMBER2    := CHR(1);
       adress_rec.T_FAX             := CHR(1);
       adress_rec.T_TELEGRAPH       := CHR(1);
       adress_rec.T_TELEXNUMBER     := CHR(1);
       adress_rec.T_E_MAIL          := CHR(1);
       adress_rec.T_RS_MAIL_COUNTRY := 0;
       adress_rec.T_RS_MAIL_REGION  := 0;
       adress_rec.T_RS_MAIL_NODE    := 0;
       adress_rec.T_TERRITORY       := CHR(1);
       adress_rec.T_BRANCH          := 0;
       adress_rec.T_NUMSESSION      := 0;
       adress_rec.T_KLADR           := CHR(1);

       adressf_rec.T_PARTYID         := 0;
       adressf_rec.T_TYPE            := 2;
       adressf_rec.T_ADRESS          := CHR(1);
       adressf_rec.T_COUNTRY         := CHR(1);
       adressf_rec.T_POSTINDEX       := CHR(1);
       adressf_rec.T_REGION          := CHR(1);
       adressf_rec.T_CODEPROVINCE    := 4;
       adressf_rec.T_PROVINCE        := CHR(1);
       adressf_rec.T_CODEDISTRICT    := 0;
       adressf_rec.T_DISTRICT        := CHR(1);
       adressf_rec.T_CODEPLACE       := 6;
       adressf_rec.T_PLACE           := CHR(1);
       adressf_rec.T_CODESTREET      := 76;
       adressf_rec.T_STREET          := CHR(1);
       adressf_rec.T_HOUSE           := CHR(1);
       adressf_rec.T_NUMCORPS        := CHR(1);
       adressf_rec.T_FLAT            := CHR(1);
       adressf_rec.T_PHONENUMBER     := CHR(1);
       adressf_rec.T_PHONENUMBER2    := CHR(1);
       adressf_rec.T_FAX             := CHR(1);
       adressf_rec.T_TELEGRAPH       := CHR(1);
       adressf_rec.T_TELEXNUMBER     := CHR(1);
       adressf_rec.T_E_MAIL          := CHR(1);
       adressf_rec.T_RS_MAIL_COUNTRY := 0;
       adressf_rec.T_RS_MAIL_REGION  := 0;
       adressf_rec.T_RS_MAIL_NODE    := 0;
       adressf_rec.T_TERRITORY       := CHR(1);
       adressf_rec.T_BRANCH          := 0;
       adressf_rec.T_NUMSESSION      := 0;
       adressf_rec.T_KLADR           := CHR(1);

    END InitRecords;

    FUNCTION TemplConstPart(Template IN VARCHAR2, TemplSym IN CHAR) RETURN VARCHAR2
    IS
       i  INTEGER := INSTR(Template, TemplSym, 1, 1);
    BEGIN
       IF I <= 0  THEN
          RAISE eINCORRECT_TEMPLATE;
       END IF;

       RETURN SUBSTR(TEMPLATE, 1, i - 1);
    END TemplConstPart;


    FUNCTION NumCorrespToTempl(Template IN VARCHAR2, ClaimNumStr IN VARCHAR2, TemplSym IN CHAR) RETURN BOOLEAN
    IS
        i  INTEGER := INSTR(Template, TemplSym, 1, 1);
    BEGIN

      IF i <= 0 THEN
         RETURN FALSE;
      END IF;

      IF SUBSTR(Template,1, i-1) = SUBSTR(ClaimNumStr,1, i-1) THEN
         RETURN TRUE;
      END IF;

      RETURN FALSE;

    END NumCorrespToTempl;

    FUNCTION NextObjNumClaim(Templ IN VARCHAR2, ClaimNumStr IN VARCHAR2, ClaimID IN NUMBER) RETURN VARCHAR2
    IS
       ObjNum    VARCHAR2(15) := ClaimNumStr;
       cnt       NUMBER := 0;
       MaxCode   NUMBER := 0;

    BEGIN
       IF ClaimID != 0 AND NumCorrespToTempl(Templ, ClaimNumStr, 'C') THEN
          RETURN ClaimNumStr;
       end IF;

       ObjNum   := TemplConstPart(Templ, 'C');
       cnt      := LENGTH(ObjNum);
       select max(to_number(substr(T_CLAIMNUMBER, cnt+1))) INTO maxCode FROM DCLAIM_DBT WHERE T_CLAIMNUMBER LIKE '' || ObjNum || '%' || '';
       IF MaxCode IS NULL THEN
          MaxCode := 0;
       END IF;
       cnt := MaxCode + 1;
       ObjNum := ObjNum || TRIM(TO_CHAR(cnt));

       RETURN ObjNum;
    END;

         FUNCTION GetTemplate (Kind IN NUMBER,Reference IN NUMBER,ObjectID_Ref IN NUMBER,AttrID_Ref IN NUMBER) RETURN VARCHAR2
         IS
            Templ VARCHAR2(25);
         BEGIN
            SELECT t_Template INTO Templ FROM DLTEMPL_DBT
                   WHERE t_Kind = Kind
                     AND t_Reference = Reference
                     AND t_ObjectID_Ref = ObjectID_Ref
                     AND t_AttrID_Ref = AttrID_Ref;

            RETURN Templ;
         EXCEPTION WHEN NO_DATA_FOUND THEN RAISE eNO_TEMPLATE;
         END;

         FUNCTION StringOnTemplate(Mask IN VARCHAR2, ObjectTypeID IN NUMBER, CreditTypeID IN NUMBER) RETURN VARCHAR2
         IS
            ObjNum    VARCHAR2(15);
         BEGIN
         IF MASK IS NOT NULL THEN
          ObjNum := Mask;
       ELSIF ObjectTypeID = LO_CLAIM THEN
          ObjNum := GetTemplate(1, CreditTypeID,LO_CLAIM,4);
       ELSIF ObjNum = LO_CREDIT THEN
          ObjNum := GetTemplate(1, CreditTypeID,LO_CREDIT,1);
       END IF;

       IF ObjectTypeID = LO_CLAIM THEN
          ObjNum := NextObjNumClaim(ObjNum, claim_rec.t_ClaimNumber, ClaimID);
--       ELSIF ObjectTypeID = LO_CREDIT THEN
--          ObjNum := NextObjNumCredit(ObjNum); Не реализовано
--       ELSIF ObjectTypeID = LO_CREDIT THEN
--          ObjNum := NextObjNumENS(ObjNum); Не реализовано
       END IF;

       RETURN ObjNum;
   END StringOnTemplate;

  /*  Формируем номервида ABCС/NNNNN, где
      А - Регион
      B - код типа кредита
      CС - порядковый номер вида кредита в своей подгруппе   */
   FUNCTION NextClaimNumber(OrgCode IN VARCHAR2, CreditTypeID IN NUMBER) RETURN VARCHAR2
   IS
      ObjNum  VARCHAR2(15)  := NULL ;
      cnt     NUMBER        := 0;
      MaxCode NUMBER        := 0;
      cod_15021 NUMBER      := 0;
      cod_15133 VARCHAR2(2) := NULL;

   BEGIN
      IF ClaimID != 0 THEN
         RETURN claim_rec.t_ClaimNumber;
      end IF;
       -- код типа кредита
       cod_15021 := to_number(GetUsFiledValue(LO_TYPECREDIT, CreditTypeID, 15021));
       if (cod_15021=1) THEN
          cod_15021:=1;
       ELSIF ((cod_15021=2) or (cod_15021=5)) THEN
          cod_15021:=2;
       ELSIF ((cod_15021=3) or (cod_15021=6) or (cod_15021=7)) THEN
          cod_15021:=3;
       ELSIF ((cod_15021=4) or (cod_15021=8)) THEN
          cod_15021:=0;
       ELSE
        RAISE eINCORRECT_USERFIELD_NUMBER;      
       End if;

      -- номер кредита в группе
      cod_15133 := Trim(GetUsFiledValue(LO_TYPECREDIT, CreditTypeID, 15133));
      if ((cod_15133 = NULL) or (cod_15133 = '')) Then
         RAISE eINCORRECT_USERFIELD_NUMBER;      
      end if;
      -- номер заявки
          IF OrgCode IS NULL 
      THEN
        ObjNum := TO_CHAR(cod_15021) || cod_15133 || '/';
      ELSE
        ObjNum := OrgCode || TO_CHAR(cod_15021) || cod_15133 || '/';
      END IF;
      cnt := LENGTH(ObjNum);
      select max(to_number(substr(T_CLAIMNUMBER, cnt+1))) INTO maxCode FROM DCLAIM_DBT
          WHERE T_CLAIMNUMBER LIKE '' || ObjNum || '%' || '';
      IF MaxCode IS NULL THEN
         MaxCode := 0;
      END IF;
      cnt    := MaxCode + 1;

      ObjNum := ObjNum || TRIM(TO_CHAR(cnt));
      RETURN ObjNum;
   END NextClaimNumber;

   FUNCTION GetClientCode RETURN VARCHAR2
   IS
      NewCode   VARCHAR2(35);
   BEGIN
      SELECT 'КЛИЕНТ_' || TRIM(TO_CHAR(MAX(TO_NUMBER(SUBSTR(t_Code, 8))) + 1, '000000')) INTO NewCode FROM dpartcode_dbt WHERE t_Code LIKE 'КЛИЕНТ_%';
      IF NewCode LIKE 'КЛИЕНТ_' THEN
         NewCode := 'КЛИЕНТ_000001';
      END IF;
      RETURN NewCode;
      EXCEPTION WHEN OTHERS
                THEN RAISE eINCORRECT_CLIENT_CODE;
   END;

   --процедура добавления поля в адрес
   PROCEDURE ADD_ADDRESS_FIELD(fullAddress IN OUT VARCHAR2, fieldValue IN VARCHAR2, fieldDefinition IN VARCHAR2)
   IS
   BEGIN
      IF fieldValue IS NOT NULL AND fieldValue != CHR(1) THEN
         IF fullAddress IS NULL THEN
            fullAddress := fieldDefinition || fieldValue;
         ELSE
            fullAddress := fullAddress || ', ' ||fieldDefinition || fieldValue;
         END IF;
      END IF;   
   END ADD_ADDRESS_FIELD;
   
   -- Функция формирования полного адреса
   FUNCTION GetAddress (adr IN DADRESS_DBT%ROWTYPE) RETURN VARCHAR2
   IS
      fullAddr  VARCHAR2(511) := NULL;
   BEGIN
      IF adr.t_PostIndex IS NOT NULL AND adr.t_PostIndex != CHR(1) THEN
         fullAddr := fullAddr || adr.t_PostIndex;
      END IF;
        
      ADD_ADDRESS_FIELD(fullAddr, adr.t_PROVINCE, 'обл. ' );
      ADD_ADDRESS_FIELD(fullAddr, adr.t_DISTRICT, 'р-н. ' );
      ADD_ADDRESS_FIELD(fullAddr, adr.t_Place, 'г. ' );
      ADD_ADDRESS_FIELD(fullAddr, adr.t_Street, 'ул. ' );      
      ADD_ADDRESS_FIELD(fullAddr, adr.t_House, 'д. ' );      
      ADD_ADDRESS_FIELD(fullAddr, adr.t_numCorps, 'корп. ' );      
      ADD_ADDRESS_FIELD(fullAddr, adr.t_Flat, 'кв. ' );

      RETURN FullAddr;
   END GetAddress;
   
   --создание договора обеспечения(ClaimID - заяка заемщика, PersonID - идентификатор поручителя, EnsuringSumm - сумма поручительства)
   PROCEDURE CreateEnsuringContract(ClaimID IN NUMBER, PersonID IN NUMBER,EnsuringSumm IN NUMBER,EnsuringSummCurrency IN NUMBER,DebitorContractNumber IN NUMBER)
   IS
    ContractId NUMBER := 0;
    --ContractNumber NUMBER := 0;
    curdate       DATE;
    OperationId NUMBER := 0;
    
    ENSURING_TYPE NUMBER :=1;--вид обеспечения:поручительство    
    ENSURING_STATUS_NEW NUMBER :=1;--статус поручительства; 1-новый
    NULL_DATE DATE := TO_DATE ('1.1.1', 'dd.mm.yyyy');--нулевая дата
    OPER_NUMBER NUMBER :=9999;--Пока нет выделенного операциониста пишем 9999
    DOC_STATE_NEW NUMBER := 1;--текущая стадия - Ввод
    DOC_STATE_TRANSACTION NUMBER := 99;--текущая стадия - проводка  
   BEGIN
         Log('CreateEnsuringContract','TRUE');
        --считаем, что транзакция открыта где-то выше.        
        curdate := GetCurrentDate();
                
        --получение следующего номера договора
        --BEGIN
        -- SELECT MAX(to_number(t_enscontractnumber))+1 INTO ContractNumber FROM DENSCONTR_DBT; 
        -- EXCEPTION
        -- WHEN NO_DATA_FOUND THEN ContractNumber := 0;
        --END;
        
         Log('CreateEnsuringContract','INSERT INTO DENSCONTR_DBT');
        INSERT INTO DENSCONTR_DBT 
            (t_enscontractid, t_enscontractnumber, t_enssystype,t_enscontractpersonid_ref,
             t_enscontractfirstdate,t_enscontractlastdate, t_enscontractsum, t_enscontractcur,
             t_enscontractstatus, t_claimid_ref, t_pantrynum, t_idinpantry,
             t_enscontractstatuspantry,t_discount,t_deposit,t_qualityid_ref,
             t_flag_rvps)
        VALUES 
            (NULL, DebitorContractNumber, ENSURING_TYPE, PersonID,
            curdate, NULL_DATE, EnsuringSumm, EnsuringSummCurrency,
            1, ClaimID, 1, 0,
            0, 0, CHR(0), 0, 
            CHR(0))
            RETURNING t_enscontractid INTO ContractId; 
                                   
         Log('CreateEnsuringContract','INSERT INTO DCRD_OP_DBT');
        INSERT INTO DCRD_OP_DBT 
            (t_credoperid, t_objecttypeid_ref, t_opertypenumber_ref, t_isdeleted,
            t_objectid_ref, t_credoperdate, t_creditnumber_ref, t_batchoperid_ref,
            t_systemoperationid, t_stageid_ref,t_oper)
        VALUES 
            (NULL, 4, 61, 0, 
            ContractId ,curdate ,0 ,0 ,
            39 ,DOC_STATE_TRANSACTION ,OPER_NUMBER)
            RETURNING t_credoperid INTO OperationId;
                                                       
         Log('CreateEnsuringContract','INSERT INTO DCOPSTAGE_DBT');
        INSERT INTO DCOPSTAGE_DBT 
            (t_credoperid_ref, t_stageid_ref, t_oper, 
            t_date, t_autoinc, t_syscarrytime)
        VALUES 
            (OperationId, DOC_STATE_NEW, OPER_NUMBER,
            curdate, 0 , NULL_DATE);
            
         Log('CreateEnsuringContract','INSERT INTO DCOPSTAGE_DBT 2');
        INSERT INTO DCOPSTAGE_DBT 
            (t_credoperid_ref, t_stageid_ref, t_oper, 
            t_date, t_autoinc, t_syscarrytime)
        VALUES 
            (OperationId, DOC_STATE_TRANSACTION, OPER_NUMBER,
            curdate, 0 , NULL_DATE);
            
         Log('CreateEnsuringContract','END');
   --     RETURN ContractNumber;                                                                
   END;
   
   BEGIN --MODIFY_CLAIM
    ErrCodeID  := 0;
    ErrDescID  := 0;
    ClaimNumID := 0;
        
    SAVEPOINT MODIFY_CLAIM;
    --получаем текущую дату
    curdate := GetCurrentDate();
    
    Log('IN PARAMERES','ClaimID='||to_char(ClaimID)||' DebitorClaimID='||to_char(DebitorClaimID)||' DebitorContractNumber='||to_char(DebitorContractNumber));
    --если создаем заявку
    IF ClaimID = 0 THEN -- вставить заявку
        Log('procedure MODIFY_CLAIM','ClaimID='||to_char(ClaimID));
        i := ClaimParm.FIRST;
        InitRecords;
        LOOP
           EXIT WHEN i IS NULL;
           IF INSTR(ClaimParm(i).ParmID, ':', 1, 1) = 0 THEN --если есть :, то это пользовательское поле.
              IF (TRIM(UPPER(ClaimParm(i).ParmID)) = 'IKFL_FNCASH') THEN
                  OrgCode :=getCodeGroup(ClaimParm(i).ParmValue);
              ELSE 
               -- Обработка системного поля
                  ParseSystemClaimsField(ClaimParm(i), claim_rec, ClaimNumID, i,Claimupd);  
                  ParseSystemPersonsField(ClaimParm(i),persn_rec ,persnupd);
                  ParseSystemDocumentField(ClaimParm(i),persnidc_rec, persnidcupd);
                  ParseSystemAddressField(ClaimParm(i), adress_rec ,adrupd);
                  ParseSystemAddressField(ClaimParm(i), adressf_rec ,adrfupd);
                  ParseSystemErrorField(ClaimParm(i),i ,ErrCodeID ,ErrDescID);
               END IF;
           END IF;
           i := ClaimParm.Next(i);
        END LOOP;

        Log('procedure MODIFY_CLAIM','parse complited');
        IF NOT persnupd THEN
           RAISE eNO_DATA_PERSN;
        END IF;
        
        Log('procedure MODIFY_CLAIM','find person');
        -- Поищем субъекта по паспортным данным
        PartyID := 0;
        BEGIN
         SELECT t_PersonID INTO PartyID FROM DPERSNIDC_DBT WHERE t_PaperKind = 0
                                                             AND t_paperSeries = persnidc_rec.t_PAPERSERIES
                                                             AND t_PAPERNUMBER = persnidc_rec.t_PAPERNUMBER
                                                             AND ROWNUM=1;
         EXCEPTION
         WHEN NO_DATA_FOUND THEN PartyID := 0;
        END;
        

        IF PartyID = 0 THEN -- Если клиента не нашли - добавляем его
         Log('procedure MODIFY_CLAIM','select max person');
         -- Вставим запись о субъекте
         SELECT MAX(t_PartyID) + 1  INTO PartyID FROM DPARTY_DBT;

         Log('INSERT INTO DPARTY_DBT ','t_PARTYID='||to_char(PartyID));
         INSERT INTO DPARTY_DBT (t_PARTYID, t_IOWNERKIND, t_LEGALFORM, t_SHORTNAME,
                                T_NAME,T_ADDNAME, T_OKPO,
                                T_NOTRESIDENT, T_SUPERIOR,T_CHANGE_DATE, T_CHANGE_DATEPREV, T_LOCKED, T_TAXINSTITUTION, 
                                T_TAXREGDATE, T_SYSTYPE, T_USERTYPE, T_USERFIELD1, T_USERFIELD2, 
                                T_USERFIELD3, T_USERFIELD4, T_NRCOUNTRY, T_CONSOLIDATERECORD, 
                                T_SETACCSEARCHALG, T_BRANCH, T_NUMSESSION, T_OFSHOREZONE, T_RESERVE)
                         VALUES (PartyID, 0, 2, persn_rec.t_NAME1, 
                                TRIM(persn_rec.t_NAME1 || ' ' || persn_rec.t_NAME2 || ' ' || persn_rec.t_NAME3),CHR(1),CHR(1),
                                CHR(0), -1, NULLDATE,NULLDATE,CHR(0),-1, 
                                NULLDATE,CHR(1),CHR(1),CHR(1),CHR(1),
                                CHR(1), CHR(1),'RUS',CHR(0),
                                1,0,0,0,CHR(1));

        
         persn_rec.t_PersonID := PartyID;

         INSERT INTO DPERSN_DBT VALUES persn_rec;
         
        
         -- Добавляем код клиента
         DECLARE
            ClCode    VARCHAR2(35) := GetClientCode();
         BEGIN

            INSERT INTO DPARTCODE_DBT (t_PARTYID, T_CODEKIND, t_CODE,t_STATE, t_BANKDATE, t_SYSDATE, t_SYSTIME, 
                                       T_USERID, T_BRANCH, T_NUMSESSION, T_RESERVE)
                               VALUES (PartyID, 1, ClCode, 0, NULLDATE, NULLDATE, NULLDATE,
                                        0, 0, 0,CHR(1));
         EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN RAISE eINCORRECT_CLIENT_CODE;
         END;

        Log('procedure MODIFY_CLAIM','find person document');

        -- Добавляем удостоверение личности
         IF persnidcupd THEN
            BEGIN
               INSERT INTO DPERSNIDC_DBT (t_PERSONID, t_PAPERKIND, t_PAPERSERIES, t_PAPERNUMBER, 
                                          t_PAPERISSUEDDATE, t_PAPERISSUER, 
                                          t_PAPERISSUERCODE, t_ISMAIN, t_BRANCH, t_NUMSESSION)
                                     VALUES (PartyID, 0, persnidc_rec.t_PAPERSERIES,persnidc_rec.t_PAPERNUMBER, 
                                              persnidc_rec.t_PAPERISSUEDDATE, persnidc_rec.t_PAPERISSUER,
                                              persnidc_rec.t_PAPERISSUERCODE,'X',0, 0);
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX THEN RAISE eDUP_PAPER;
            END;
         END IF;
         -- добавляем юридический адрес
        Log('procedure MODIFY_CLAIM','884 string');
         IF adrupd THEN
            fullAddr := GetAddress(adress_rec);
            BEGIN
               INSERT INTO DADRESS_DBT (t_PARTYID, t_TYPE, t_REGION, t_PROVINCE, t_DISTRICT, 
                                        t_CodePlace, t_Place, t_CodeStreet, t_Street, t_House, 
                                        t_NumCorps, t_Flat, t_ADRESS, t_PHONENUMBER,
                                        t_PHONENUMBER2, t_FAX, t_POSTINDEX)
                                VALUES (PartyID, 1, adress_rec.t_REGION, adress_rec.t_PROVINCE, adress_rec.t_DISTRICT, 
                                        6, adress_rec.t_Place, 76, adress_rec.t_Street, adress_rec.t_House, 
                                        adress_rec.t_NumCorps, adress_rec.t_Flat, fullAddr, adress_rec.t_PHONENUMBER, 
                                        adress_rec.t_PHONENUMBER2, adress_rec.t_FAX, adress_rec.t_POSTINDEX);
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX THEN RAISE eDUP_ADRESS;
            END;
         END IF;
         -- добавляем фактический адрес - 
         IF adrfupd THEN
            fullAddr := GetAddress(adressf_rec);
            BEGIN
               INSERT INTO DADRESS_DBT (t_PARTYID, t_TYPE, t_PROVINCE, t_DISTRICT,t_REGION, 
                                        t_CodePlace, t_Place, t_CodeStreet, t_Street, t_House,
                                        t_NumCorps, t_Flat, t_ADRESS, t_POSTINDEX)
                                VALUES (PartyID,   2,adressf_rec.t_PROVINCE, adressf_rec.t_DISTRICT, adressf_rec.t_REGION,
                                       6, adressf_rec.t_Place,76, adressf_rec.t_Street, adressf_rec.t_House, 
                                       adressf_rec.t_NumCorps, adressf_rec.t_Flat, fullAddr, adressf_rec.t_POSTINDEX);
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX THEN RAISE eDUP_ADRESSF;
            END;
         END IF;         
      END IF;  -- IF PartyID = 0, если клиент уже существует в БД LOANS,то записываем данные клиента только в польз. поля заявки
        Log('procedure MODIFY_CLAIM','PArtyId='||to_char(PartyID));
      
      IF Claimupd THEN --если обновились поля заявки
        Log('Claimupd','Claimupd=true');
         claim_rec.t_ClaimID := 0;
         claim_rec.t_LoanerID_Ref := PartyID;
         
         IF claim_rec.t_CreditTypeID_Ref = 0 THEN
            Log('RAISE Error','eNO_CREDIT_TYPE 926');
            RAISE eNO_CREDIT_TYPE;
         END IF;
        Log('Claimupd','eNO_CREDIT_TYPE=false');
         -- Генерация номера заявки по шаблону
         -- claim_rec.t_ClaimNumber := StringOnTemplate(NULL, LO_CLAIM, claim_rec.t_CreditTypeID_Ref);
         -- Переходим к групам пользователей и регионам
         -- OrgCode - кредитный офис
         claim_rec.t_ClaimNumber := NextClaimNumber(OrgCode, claim_rec.t_CreditTypeID_Ref);
         -- Записываем номер заявки в массив
         IF ClaimNumID = 0 THEN
            ClaimNumID := ClaimParm.LAST + 1;
            ClaimParm.Extend;
            ClaimParm(ClaimNumID) := IKFLTClaimParm('', '');
            
         END IF;
        Log('Claimupd','940');
         ClaimParm(ClaimNumID).ParmID    := 'number';
         ClaimParm(ClaimNumID).ParmValue := claim_rec.t_ClaimNumber;
         -- Определяем % ставку по заявке
         IF Percrate THEN
           -- Ставка задана индивидуально
           claim_rec.t_TuneType := 2;
         ELSE
           -- Ставка не задана - типовая, берем из вида кредита
           claim_rec.t_TuneType := 1;
           claim_rec.t_BPercRate := lnGetRateVal(LO_TYPECREDIT, claim_rec.t_CreditTypeID_Ref, 1);

         END IF;
        Log('Claimupd','953');
         claim_rec.t_BCreditDuration := claim_rec.t_LCreditDuration;
         claim_rec.t_BCTypeDuration  := claim_rec.t_LCTypeDuration;
         -- Вставляем заявку в таблицу
         INSERT INTO DCLAIM_DBT VALUES claim_rec RETURNING t_ClaimID INTO ClaimID;
         
        Log('procedure MODIFY_CLAIM','959 str');
        --если создавали заявку для поручителя, связываем поручителя и заявку заемщика.                  
        IF DebitorClaimID IS NOT NULL THEN           
            CreateEnsuringContract(DebitorClaimID ,PartyID ,claim_rec.t_LCreditSum,claim_rec.t_LCreditCurCode,DebitorContractNumber);
        else
           Log('CreateEnsuringContract','FALSE');
        END IF;         
                 
      ELSE
         Log('RAISE','eNO_CLAIM_DATA');
         RAISE eNO_CLAIM_DATA;
      END IF; --IF Claimupd
      Log('INSERT INTO DLUFOTYPE_DBT','ClaimID='||to_char(ClaimID));
      -- Привязка пользовательских полей к заявке
      INSERT INTO DLUFOTYPE_DBT (t_UsFObjTypeID, t_ObjectID_Ref, t_ObjID_Ref, t_UsFieldID_Ref, t_UsFieldGroupID_Ref, t_parentID)
          SELECT 0, 3, ClaimID, b.t_UsFieldID_Ref, b.t_UsFieldGroupID_Ref, b.t_parentID
            FROM DLUFOTYPE_DBT b
           WHERE b.t_ObjectID_Ref = 3
             AND b.t_ObjID_Ref    = 0
             AND EXISTS (SELECT * FROM DLUSFFLAG_DBT
                                 WHERE t_ObjectID_Ref = 3
                                   AND t_UsFieldID_Ref = b.T_USFIELDID_REF
                                   AND t_UsFlagID_Ref = 2
                                   AND t_FLAG = 'X');
      -- Вставка значений пользовательских полей
      i := ClaimParm.FIRST;
      LOOP
      EXIT WHEN i IS NULL;
          IF INSTR(ClaimParm(i).ParmID, ':', 1, 1) >= 1 THEN
          -- Обработка пользовательского поля
            ProceedUserField(ClaimID, ClaimParm(i));            
          END IF;
          i := ClaimParm.Next(i);
      END LOOP;
      
      IF DebitorClaimID IS NOT NULL THEN
         -- связываем заявку поручителя и заемщика
         -- 15351 Номер заявки для поручительства        В заявке поручителя сода сохраняется номер заявки заемщика
         ProceedUserField(ClaimID, IKFLTClaimParm('15351',GetClaimNumber(DebitorClaimID)));

         -- 15350 Номер заявки поручителя       В заявке заемщика сюда должен сохраняться номер заявки поручителя
         oldDebitorClaimNumbers := GetUsFiledValue(LO_CLAIM, DebitorClaimID, '15350');
         IF oldDebitorClaimNumbers != ' ' THEN
            oldDebitorClaimNumbers := oldDebitorClaimNumbers || ';';
         END IF;
         ProceedUserField(DebitorClaimID, IKFLTClaimParm('15350',oldDebitorClaimNumbers || GetClaimNumber(ClaimID)));
      END IF;
   ELSE --Поискать и обновить заявку, если нашли ее (IF ClaimID != 0)
      BEGIN
          Log('procedure MODIFY_CLAIM','ClaimID='||to_char(ClaimID));
          SELECT * INTO claim_rec FROM DCLAIM_DBT WHERE t_ClaimID = ClaimID;
      EXCEPTION WHEN NO_DATA_FOUND THEN RAISE eNO_CLAIM_FOUND;
      END;

      IF claim_rec.t_LoanerID_Ref > 0 THEN --если заявка найдена
          BEGIN
             SELECT * INTO persn_rec FROM DPERSN_DBT    WHERE t_PersonID = claim_rec.t_LoanerID_Ref;
          EXCEPTION
               WHEN NO_DATA_FOUND THEN RAISE eNO_LOANER_FOUND;
          END;
          IF persn_rec.t_name1 = CHR(1) THEN
             persn_rec.t_name1 := ' ';
          END IF;

          IF persn_rec.t_name2 = CHR(1) THEN
             persn_rec.t_name2 := ' ';
          END IF;

          IF persn_rec.t_name3 = CHR(1) THEN
             persn_rec.t_name3 := ' ';
          END IF;

          BEGIN
              persnidc := true;
              SELECT * INTO persnidc_rec  FROM DPERSNIDC_DBT WHERE t_PersonID = claim_rec.t_LoanerID_Ref AND t_IsMain = 'X';
          EXCEPTION
               WHEN NO_DATA_FOUND THEN persnidc := false;
          END;

          BEGIN
              adress := true;
              SELECT * INTO adress_rec    FROM dadress_dbt   WHERE t_PartyID  = claim_rec.t_LoanerID_Ref AND t_Type   = 1;
          EXCEPTION
               WHEN NO_DATA_FOUND THEN adress := false;
          END;

          BEGIN
              adressf := true;
              SELECT * INTO adressf_rec   FROM dadress_dbt   WHERE t_PartyID  = claim_rec.t_LoanerID_Ref AND t_Type   = 2;
          EXCEPTION
               WHEN NO_DATA_FOUND THEN adressf := false;
          END;
      ELSE
          -- в БД заявка без ссылки на заещика
          RAISE eNO_LOANER_FOUND;
      END IF;

      i := ClaimParm.FIRST;
          Log('procedure MODIFY_CLAIM','1027 строка');

      LOOP
      EXIT WHEN i IS NULL;
         IF INSTR(ClaimParm(i).ParmID, ':', 1, 1) > 1 THEN
            ProceedUserField(ClaimID, ClaimParm(i));            
         ELSE
          -- Обработка системного поля
              ParseSystemClaimsField(ClaimParm(i), claim_rec, ClaimNumID, i, Claimupd);  
              ParseSystemPersonsField(ClaimParm(i),persn_rec ,persnupd);
              ParseSystemDocumentField(ClaimParm(i),persnidc_rec, persnidcupd);
              ParseSystemAddressField(ClaimParm(i), adress_rec ,adrupd);
              ParseSystemAddressField(ClaimParm(i), adressf_rec ,adrfupd);
              ParseSystemErrorField(ClaimParm(i),i ,ErrCodeID ,ErrDescID);
         END IF;
         i := ClaimParm.Next(i);
      END LOOP;
      
      -- Если изменились поля заявки - обновим ее
      IF Claimupd THEN
         UPDATE DCLAIM_DBT SET  t_ClaimNumber      = claim_rec.t_ClaimNumber      ,
                                t_GivingDate       = claim_rec.t_GivingDate       ,
                                t_LCreditSum       = claim_rec.t_LCreditSum       ,
                                t_LCreditCurCode   = claim_rec.t_LCreditCurCode   ,
                                t_LSelfSum          = claim_rec.t_LSelfSum        ,
                                t_LSelfCurCode     = claim_rec.t_LSelfCurCode     ,
                                t_LCreditDuration  = claim_rec.t_LCreditDuration  ,
                                t_LCTypeDuration   = claim_rec.t_LCTypeDuration   ,
                                t_LPayDuration     = claim_rec.t_LPayDuration     ,
                                t_LPTypeDuration   = claim_rec.t_LPTypeDuration   ,
                                t_LPercRate        = claim_rec.t_LPercRate        ,
                                t_LCreditPurpose   = claim_rec.t_LCreditPurpose   ,
                                t_CreditTypeID_Ref = claim_rec.t_CreditTypeID_Ref ,
                                t_SumObject        = claim_rec.t_SumObject        ,
                                t_SumObjectCurCode = claim_rec.t_SumObjectCurCode ,
                                t_Note             = claim_rec.t_Note
                WHERE t_ClaimID = ClaimID;
      END IF;

     
      -- Если изменилось удостоверение личности и в бд его нет, то добавляем из заявки
      IF (persnidcupd) AND (not persnidc) THEN         
        BEGIN
            INSERT INTO DPERSNIDC_DBT (t_PERSONID, t_PAPERKIND, t_PAPERSERIES, t_PAPERNUMBER, t_PAPERISSUEDDATE,
                                          t_PAPERISSUER, t_PAPERISSUERCODE, t_ISMAIN, t_BRANCH, t_NUMSESSION)
            VALUES (claim_rec.t_LoanerID_REF, 0, persnidc_rec.t_PAPERSERIES,persnidc_rec.t_PAPERNUMBER, 
                    persnidc_rec.t_PAPERISSUEDDATE, persnidc_rec.t_PAPERISSUER,persnidc_rec.t_PAPERISSUERCODE,
                    'X', 0, 0);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN RAISE eDUP_PAPER;
        END;
      END IF;

      -- Изменился юридический адрес - адрес не был задан - добавляем
      IF (adrupd) AND (not adress) THEN
        fullAddr := GetAddress(adress_rec);         
        BEGIN
            INSERT INTO DADRESS_DBT (t_PARTYID, t_TYPE, t_PROVINCE, t_DISTRICT, t_CodePlace, t_Place, t_CodeStreet,
                                    t_Street, t_House, t_NumCorps, t_Flat, t_ADRESS, t_PostIndex, t_PhoneNumber, 
                                    t_PhoneNumber2, t_Fax)
                              VALUES (PartyID, 1, adress_rec.t_PROVINCE, adress_rec.t_DISTRICT, 6, adress_rec.t_Place, 76,
                                    adress_rec.t_Street, adress_rec.t_House, adress_rec.t_NumCorps, adress_rec.t_Flat, 
                                    fullAddr, adress_rec.t_PostIndex, adress_rec.t_PhoneNumber, adress_rec.t_PhoneNumber2, 
                                    adress_rec.t_Fax);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN RAISE eDUP_ADRESS;
        END;         
      END IF;

      -- Изменился фактический адрес - адрес не был задан - добавляем
      IF (adrfupd) AND (NOT adressf) THEN
         fullAddr := GetAddress(adressf_rec);
         BEGIN
            INSERT INTO DADRESS_DBT (t_PARTYID, t_TYPE, t_PROVINCE, t_DISTRICT, t_CodePlace, t_Place, t_CodeStreet,
                                     t_Street, t_House, t_NumCorps, t_Flat, t_ADRESS, t_PostIndex)
                                VALUES (PartyID, 2, adressf_rec.t_PROVINCE, adressf_rec.t_DISTRICT, 6, adressf_rec.t_Place,
                                        76, adressf_rec.t_Street, adressf_rec.t_House, adressf_rec.t_NumCorps, 
                                        adressf_rec.t_Flat, fullAddr, adressf_rec.t_PostIndex);
         EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN RAISE eDUP_ADRESSF;
         END;
      END IF;
   END IF;
   COMMIT;

   RETURN 1; -- Успешное завершение

   EXCEPTION
       WHEN eNO_CLAIM_FOUND             THEN
            CreateERRMessage('Не найдена кредитная заявка', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
       WHEN eINCORRECT_USERFIELD_NUMBER THEN
            CreateERRMessage('Пользовательское поле не найдено', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
            WHEN eINCORRECT_PARMID           THEN
            CreateERRMessage('Некорректый ID параметра', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
            WHEN eNO_LOANER_FOUND            THEN
            CreateERRMessage('Не найден заемщик', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
            WHEN eDUP_ADRESSF                THEN
            CreateERRMessage('Фактический адрес заемщика уже задан', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
            WHEN eDUP_ADRESS                 THEN
            CreateERRMessage('Фактический адрес заемщика уже определен', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
            WHEN eDUP_PAPER                  THEN
            CreateERRMessage('Дублирование паспортных данных заемщика', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
            WHEN eNO_DATE                    THEN
            CreateERRMessage('Не задана дата операционного дня', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
            WHEN eNO_DATA_PERSN              THEN
            CreateERRMessage('Не определены данные по заещику (ФИО, дата рождения)', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
            WHEN eNO_CREDIT_TYPE             THEN
            CreateERRMessage('Не задан вид кредита', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
       WHEN eINCORRECT_TEMPLATE         THEN
            CreateERRMessage('Некорректный шаблон нумерации заявок', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
       WHEN eNO_TEMPLATE                THEN
            CreateERRMessage('Не задан шаблон нумерации заявок', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
       WHEN eNO_CLAIM_DATA              THEN
            CreateERRMessage('Не заданы данные по заявке', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
       WHEN eINCORRECT_CLIENT_CODE      THEN
            CreateERRMessage('Не удалось автоматически сформировать код клиента', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
       WHEN eINCORRECT_DATE             THEN
            CreateERRMessage('Некорректная дата', SQLCODE);
            ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
            RETURN 0;
       WHEN OTHERS THEN
             CreateERRMessage('Не удалось добавить/обновить заявку ',SQLCODE);
--             CreateERRMessage('id ' || trim(ClaimParm(i).ParmID) || ' val  ' || trim(ClaimParm(i).ParmValue), SQLCODE);
             ROLLBACK TO SAVEPOINT MODIFY_CLAIM;
             RETURN 0;
   END ModifyClaim;

   -- Функция получения статусов заявок
   PROCEDURE GetClaimState (Claims IN OUT IKFLTAClaimState)
   IS
      i        NUMBER;
          recCount NUMBER;
          claim_rec dclaim_dbt%ROWTYPE    := NULL;
   BEGIN
      i := Claims.FIRST;
          LOOP
          EXIT WHEN i IS NULL;
             BEGIN

                        SELECT * INTO claim_rec FROM dclaim_dbt where t_ClaimID = TO_NUMBER(Claims(i).ClaimID);
                        SELECT t_Code INTO Claims(i).State
                        FROM DLLVALUES_DBT
             WHERE t_List = 1400
               AND t_Element = claim_rec.t_ClaimState;
                    Claims(i).CreditSum      := claim_rec.T_BCREDITSUM;
                        Claims(i).CreditSumCurrency := claim_rec.T_BCREDITCURCODE;
                        Claims(i).CreditDuration := claim_rec.T_BCREDITDURATION;
                        Claims(i).CreditTypeDuration := claim_rec.T_BCTYPEDURATION;
                        SELECT count(*) INTO recCount FROM dcrd_op_dbt WHERE T_stageID_REF = 99
                          AND T_SYSTEMOPERATIONID = 2 AND T_ISDELETED = 0 AND T_CREDITNUMBER_REF = claim_rec.T_CREDITNUMBER_REF;
                        IF recCount > 0
                           THEN Claims(i).State := 'Выдано';
                        END IF;

                             EXCEPTION WHEN NO_DATA_FOUND
                           THEN Claims(i).State := '';
                 WHEN OTHERS
                           THEN Claims(i).State := '';
         END;
                 i := Claims.Next(i);
          END LOOP;
   END;

   -- Функция получения графика погашения
   FUNCTION GetPlanpay (ClaimID IN NUMBER) RETURN IKFLTAPlanpay
   IS
     planpay          IKFLTAPlanpay := IKFLTAPlanpay(); -- График погашения
     CreditNumber     NUMBER(10); --ID кредитного договора, оформленного по заявке
     KomissDutyID     NUMBER(10); --ID комиссионного СО
     MainDutyID       NUMBER(10); --ID основного СО
     LastPayOpOperID  NUMBER(10); --ID последней операции формирования графика погашения
     i                NUMBER;
     Komission        NUMBER(19,4) := 0;
     MainRest         NUMBER(19,4) := 0;
     PeriodCount      NUMBER(5)    := 0;
     PrevDate_        DATE := NULL;
     FirstPayDate     DATE := NULL;

     calcgpay_rec     dlgpayop_dbt%ROWTYPE;
     credit_rec       DCREDIT_C_DBT%ROWTYPE;
     duty_crd_rec     DDUTY_CRD_DBT%ROWTYPE;
     DD               NUMBER(5) := 0;
     MM               NUMBER(5) := 0;
     YY               NUMBER(5) := 0;
     CommRate         FLOAT(53) := 0;
     CommSum          NUMBER(19, 4) := 0;
     CommBase         VARCHAR2(99) := '';
     PayRound         VARCHAR2(99) := '';
     AdvancedComission NUMBER(19, 4) := 0;
     REST              NUMBER(19, 4) := 0;
     PREVSUM           NUMBER(19, 4) := 0;
     TOTSUM            NUMBER(19, 4) := 0;
     P                 FLOAT(53)     := 0;
     Anuitet           NUMBER(19, 4) := 0;

     FUNCTION isHoliday(dt IN DATE) RETURN BOOLEAN
     IS
     BEGIN
        RETURN (NEXT_DAY (dt - 1, 'SATURDAY') = DT) OR (NEXT_DAY (DT - 1, 'SUNDAY') = DT);
     EXCEPTION WHEN OTHERS THEN RETURN FALSE;
     END isHoliday;

     FUNCTION GetDay(EXPDAY IN NUMBER, DT IN DATE) RETURN DATE
     IS
        ODATE  DATE := DT;
     BEGIN

        IF EXPDAY = 1 THEN
          IF ISHOLIDAY (ODATE) THEN
             ODATE := NEXT_DAY(ODATE, 'MONDAY');
          END IF;
        ELSIF EXPDAY = 2 THEN

          IF ISHOLIDAY (ODATE) THEN
             ODATE := NEXT_DAY(ODATE, 'FRIDAY') - 7;
          END IF;
          IF ODATE < duty_crd_rec.t_DUTYFIRSTDATE THEN
            ODATE := DT;
            IF ISHOLIDAY (ODATE) THEN
              ODATE := NEXT_DAY(ODATE, 'MONDAY');
            END IF;
            IF ODATE > duty_crd_rec.t_DUTYLASTDATE THEN
              ODATE := DT;
            END IF;
          END IF;
        END IF;
        RETURN ODATE;
     END GetDay;

     FUNCTION GetDaysInMonth(dt IN DATE) RETURN NUMBER
     IS
        mn   NUMBER(5) := EXTRACT(MONTH FROM dt);
        yr   NUMBER(5) := EXTRACT(YEAR  FROM dt);
     BEGIN
        RETURN (LAST_DAY(dt) - TO_DATE('1.' || mn || '.' || yr, 'dd.mm.yyyy')) + 1;
     END;


     FUNCTION  GetExpiredDate (ArrearDate IN INTEGER, CurrentDate IN DATE) RETURN NUMBER
     IS
        REALEXPDATE   NUMBER (5) := ArrearDate;
        NDAY          NUMBER (5) := GETDAYSINMONTH( CurrentDate );
     BEGIN
        IF ArrearDate = 31 THEN
           REALEXPDATE := EXTRACT(DAY FROM LAST_DAY(CurrentDate));
        ELSE
           IF(ArrearDate >  NDAY) THEN
              REALEXPDATE := NDAY;
           END IF;
        END IF;
        RETURN REALEXPDATE;
     END GetExpiredDate;

     FUNCTION LastWorkDay (mnth IN NUMBER, YR IN NUMBER) RETURN DATE
     IS
        FIRSTDATE  DATE := TO_DATE('1.' || mnth || '.' || YR, 'dd.mm.yyyy');
     BEGIN
        IF IsHoliday(LAST_DAY(FIRSTDATE)) THEN
           RETURN NEXT_DAY(LAST_DAY(FIRSTDATE) - 1, 'FRIDAY') - 7;
        END IF;
        RETURN LAST_DAY(FIRSTDATE);
     END LastWorkDay;

     FUNCTION CalcPayDate(i IN INTEGER, FirstDate IN DATE, LastDate IN DATE, CountPay IN INTEGER, TypePeriod IN INTEGER, Period IN INTEGER, ArrearDate IN INTEGER, ExpDay IN INTEGER) RETURN DATE
     IS
        D   INTEGER := 0;
        M   INTEGER := 0;
        Y   INTEGER := 0;

        Paydate         DATE := NULLDATE;
        REALARREARDATE1 DATE := NULLDATE;
        REALARREARDATE  NUMBER(5) := 0;
     BEGIN
          IF I = 1 THEN
            PAYDATE    := FIRSTDATE;
            PREVDATE_  := NULLDATE;

--          IF RATE = 0 THEN
              SELECT MAX(t_CredOperDate) INTO PREVDATE_
                                       FROM DCRD_OP_DBT
                                       WHERE T_CreditNumber_Ref = credit_rec.t_CreditNumber
                                         AND T_SYSTEMOPERATIONID IN (3, 4)
                                         AND T_STAGEID_REF = 99
                                         AND t_ObjectID_Ref IN (1, 6);
              IF PREVDATE_ IS NULL THEN
                 PREVDATE_ := NULLDATE;
              END IF;
--          END IF;

            IF PREVDATE_   = NULLDATE THEN
               PREVDATE_ := duty_crd_rec.T_DUTYFIRSTDATE;
            END IF;
          ELSE
            IF TYPEPERIOD = 0 THEN
              MM := MM + PERIOD;
              IF  MM > 12  THEN
                MM := MM - 12;
                YY := YY + 1;
              END IF;

              REALARREARDATE := GetExpiredDate(ARREARDATE, TO_DATE('1.' || MM || '.' || YY, 'dd.mm.yyyy'));
              PAYDATE        := TO_DATE(REALARREARDATE || '.' ||  MM || '.' || YY, 'dd.mm.yyyy');
            ELSE
              PAYDATE := PAYDATE + PERIOD;
              MM := EXTRACT(MONTH FROM PAYDATE);
              YY := EXTRACT(YEAR  FROM PAYDATE);
            END IF;

            D := EXTRACT(DAY FROM LastWorkDay(MM, YY));


            IF DD > D THEN
              PAYDATE := TO_DATE(D || '.' || MM || '.' ||  YY,'dd.mm.yyyy');
            ELSE
              PAYDATE := TO_DATE(DD || '.' || MM || '.' ||  YY, 'dd.mm.yyyy');
            END IF;
          END IF;

          IF (EXPDAY != 0) AND ISHOLIDAY (PAYDATE) THEN
             PAYDATE := GETDAY (EXPDAY, PAYDATE);
          END IF;

          M := EXTRACT(MONTH FROM PAYDATE);
          Y := EXTRACT(YEAR  FROM PAYDATE);

          REALARREARDATE1 := LastWorkDay(M,Y);
          IF PAYDATE = REALARREARDATE1 THEN
             PAYDATE  := GETDAY (EXPDAY, PAYDATE+1);
          END IF;

          RETURN PAYDATE;
     EXCEPTION WHEN OTHERS
              THEN RETURN NULLDATE;
     END CalcPayDate;

     FUNCTION CalcAnuitet RETURN NUMBER
     IS
       retSum  NUMBER(19,4) := 0;
     BEGIN
       IF SUBSTR(COMMBASE,1,1) = '1' THEN
         retSum := REST*P/12.0/100.0/(1-POWER(1+P/12.0,-calcgpay_rec.t_PAY_PLANCOUNTPAY))+REST*COMMRATE/100.0 + COMMSUM/100.0;
       ELSE
         retSum := REST*(P/12.0 + COMMRATE/100.0 ) / 100.0 / (1-POWER(1+P/12.0+COMMRATE/100.0,-calcgpay_rec.t_PAY_PLANCOUNTPAY)) + COMMSUM/100.0;
       END IF;

       IF SUBSTR(PAYROUND,1,1) = '1' THEN
         retSum := ROUND(retSum*100.0*1.00115+0.5,0);
       ELSIF SUBSTR(PAYROUND,1,1) = '2' THEN
         retSum := ROUND(retSum*100.0+5,-1);
       ELSE
         retSum := ROUND(retSum*100.0,2);
       END IF;

       RETURN retSum;
       EXCEPTION WHEN OTHERS THEN RETURN 0;
     END CalcAnuitet;

     FUNCTION CalcPlanPay_LEFCO(Rest IN NUMBER,FirstDate IN DATE, LastDate IN DATE, Rate IN NUMBER) RETURN NUMBER
     IS
       retSum  NUMBER(19,4) := 0;
       y1      NUMBER(5)    := 0;
       y2      NUMBER(5)    := 0;
       dt         DATE         := NULLDATE;
       FIRSTDATE1 DATE         := FirstDate;

       FUNCTION DayInYear (y IN NUMBER) RETURN NUMBER
       IS
       BEGIN
          IF MOD(Y, 4) = 0 THEN
            RETURN 366;
          ELSE
            RETURN 365;
          END IF;
       END DayInYear;
     BEGIN

       Y1 := EXTRACT(YEAR FROM FirstDATE);
       Y2 := EXTRACT(YEAR FROM LastDATE);
       IF FIRSTDATE >= LASTDATE THEN
          RETURN 0;
       END IF;

       IF Y1 != Y2 THEN
         DT := TO_DATE(31 || '.' || 12 || '.' ||  Y1, 'dd.mm.yyyy');
       ELSE
         DT := LASTDATE;
       END IF;

       LOOP
          EXIT WHEN Y1 > Y2;
          retSum := retSum + (DT - FIRSTDATE1) * REST * RATE / DAYINYEAR(Y1);
          Y1  := Y1 + 1;
          FIRSTDATE1 := DT;

          IF Y1 != Y2 THEN
            DT := TO_DATE(31 || '.' || 12 || '.' ||  Y1, 'dd.mm.yyyy');
          ELSE
            DT := LASTDATE;
          END IF;

        END LOOP;

        RETURN retSUM;
     EXCEPTION WHEN OTHERS THEN RETURN 0;
     END CalcPlanPay_LEFCO;

   BEGIN
     -- Получим ID кредитного договора
     BEGIN
        SELECT * INTO credit_rec FROM DCREDIT_C_DBT WHERE t_CreditNumber = (SELECT t_CreditNumber_Ref FROM DCLAIM_DBT WHERE t_ClaimID = ClaimID);
     EXCEPTION WHEN NO_DATA_FOUND THEN -- Не найден кредитный договор
        RETURN planpay;
     END;

     PeriodCount := credit_rec.t_Duration;
-- ЗАГЛУШКА!!! Перенесена из макроса
     IF credit_rec.t_TypeDuration != 0 THEN
        PeriodCount := 12;
     END IF;

     PrevDate_               := credit_rec.t_RegDate;
     credit_rec.t_RegDate    := credit_rec.t_RegDate + 1;
     credit_rec.t_ReturnDate := credit_rec.t_ReturnDate + 100;
     duty_crd_rec.t_DutyFirstDate := credit_rec.t_RegDate;
     duty_crd_rec.t_DutyLastDate := credit_rec.t_ReturnDate;

     FirstPayDate :=  CalcPayDate(1, credit_rec.t_RegDate, credit_rec.t_ReturnDate, PeriodCount, 0, 1, 31, 1);

     DD := EXTRACT(DAY   FROM FirstPayDate);
     MM := EXTRACT(MONTH FROM FirstPayDate);
     YY := EXTRACT(YEAR  FROM FirstPayDate);

     FirstPayDate :=  CalcPayDate(2, credit_rec.t_RegDate, credit_rec.t_ReturnDate, PeriodCount, 0, 1, 31, 1);

     calcgpay_rec.t_PAY_PlanCountPay  := PeriodCount;
     calcgpay_rec.t_PAY_PlanFirstDate := FirstPayDate;
     calcgpay_rec.t_PAY_PlanLastDate  := credit_rec.t_ReturnDate;
     calcgpay_rec.t_PAY_TypePeriod    := 0;
     calcgpay_rec.t_PAY_Period        := 1;
     calcgpay_rec.t_PAY_ArrearDate    := 31;
     calcgpay_rec.t_PAY_ExpDay        := 1;

     MainRest := credit_rec.t_CreditSum;
     P := lnGetRateVal(LO_TYPECREDIT, credit_rec.t_CreditTypeID_Ref, 1)/100.0;

     IF P = 0 THEN
        RETURN planpay;
     END IF;

      CommRate := TO_NUMBER(TRIM(GetUsFiledValue(LO_TYPECREDIT, credit_rec.t_CreditTypeID_Ref, 15001)), '99999999999999D99999999', ' NLS_NUMERIC_CHARACTERS = ''.,'' ');
      CommSum  := TO_NUMBER(TRIM(GetUsFiledValue(LO_TYPECREDIT, credit_rec.t_CreditTypeID_Ref, 15002)), '99999999999999D99', ' NLS_NUMERIC_CHARACTERS = ''.,'' ');
      CommBase := GetUsFiledValue(LO_TYPECREDIT, credit_rec.t_CreditTypeID_Ref, 15003);
      PayRound := GetUsFiledValue(LO_TYPECREDIT, credit_rec.t_CreditTypeID_Ref, 15004);
      AdvancedComission := TO_NUMBER(TRIM(GETUSFIledvalue(LO_TYPECREDIT, credit_rec.t_CreditTypeID_Ref, 15022)), '99999999999999D99', ' NLS_NUMERIC_CHARACTERS = ''.,'' ');


      IF CommRate IS NULL THEN
         CommRate := 0;
      END IF;

      IF AdvancedComission IS NULL THEN
         AdvancedComission := 0;
      END IF;

      IF CommSum IS NULL THEN
        CommSum := 0;
      END IF;

      IF TRIM(CommBase) = '' THEN
         RETURN planpay;
      END IF;

      IF TRIM(PayRound) = '' THEN
         RETURN planpay;
      END IF;

      IF SUBSTR(TRIM(CommBase),1,1) = '1' THEN
        CommSum  := MainRest * CommRate / 100.0 + CommSum;
        CommRate := 0;
      END IF;

      Rest     := MainRest;
      Anuitet  := CalcAnuitet();

      I        := 1;
      LOOP
      EXIT WHEN  I > PERIODCOUNT;

          planpay.Extend;
          planpay(i) := IKFLTPlanpay ('', '', '0', '0', '0' , '0', '0', '0');
          IF I = 1 THEN
            planpay(i).PlannedPAYDATE := TO_CHAR(calcgpay_rec.t_PAY_PLANFIRSTDATE, 'dd.mm.yyyy');
          ELSE
            planpay(i).PlannedPAYDATE := TO_CHAR(CALCPAYDATE(I,
                                                   calcgpay_rec.t_PAY_PLANFIRSTDATE,
                                                   calcgpay_rec.t_PAY_PLANLASTDATE,
                                                   calcgpay_rec.t_PAY_PLANCOUNTPAY,
                                                   calcgpay_rec.t_PAY_TYPEPERIOD,
                                                   calcgpay_rec.t_PAY_PERIOD,
                                                   calcgpay_rec.t_PAY_ARREARDATE,
                                                   calcgpay_rec.t_PAY_EXPDAY), 'dd.mm.yyyy');
          END IF;
          planpay(i).PlannedExpDate    := planpay(i).PlannedPAYDATE;  -- ???? не нашел, чем заполнить
          planpay(i).PlannedPercentSum := ROUND(CalcPlanPay_LEFCO((Rest - PREVSUM), PREVDATE_, TO_DATE(planpay(i).PlannedPAYDATE, 'dd.mm.yyyy'), P), 2);
          planpay(i).PlannedKomission  := ROUND((Rest - PREVSUM) * COMMRATE / 100.0 + COMMSUM, 2);
          IF  I = calcgpay_rec.t_PAY_PLANCOUNTPAY THEN
            planpay(i).PlannedPaySum   := ROUND(Rest - TOTSUM + planpay(i).PlannedPercentSum + planpay(i).PlannedKomission, 2);
          ELSE
            planpay(i).PlannedPaySum   := Anuitet;
          END IF;

          planpay(i).PlannedPaySum     := planpay(i).PlannedPaySum - planpay(i).PlannedPercentSum - planpay(i).PlannedKomission;
          planpay(i).PlannedTotalSum   := planpay(i).PlannedPaySum + planpay(i).PlannedPercentSum + planpay(i).PlannedKomission;

          MainRest := MainRest - planpay(i).PlannedPaySum; -- Уменьшим остаток погашения на планируемую сумму выдачи

          planpay(i).plannedMainRest   := MainRest;
          planpay(i).AdvancedRepayment := MainRest + planpay(i).PlannedTotalSum + AdvancedComission;

          PREVDATE_ := TO_DATE(planpay(i).PlannedPAYDATE, 'dd.mm.yyyy');

          TOTSUM  := TOTSUM  + planpay(i).PlannedPaySum;
          PREVSUM := PREVSUM + planpay(i).PlannedPaySum;

          I       := I + 1;
      END LOOP;

      RETURN planpay;
   EXCEPTION WHEN OTHERS
             THEN RETURN planpay;
   END;

   -- Функция получения информации по заявке
   FUNCTION GetClaimInfo (ClaimID IN NUMBER) RETURN IKFLTAClaimParm
   IS
    claimparm     IKFLTAClaimParm := IKFLTAClaimParm();
         i             NUMBER;
         claim_rec     dclaim_dbt%ROWTYPE;
         credit_rec    dcredit_c_dbt%ROWTYPE;
         persn_rec     dpersn_dbt%ROWTYPE;
         persnidc_rec  dpersnidc_dbt%ROWTYPE;
         adress_rec    dadress_dbt%ROWTYPE;
         adressf_rec   dadress_dbt%ROWTYPE;
         MainRate      NUMBER := 0;
         KomisPersRate FLOAT (53);

         PROCEDURE SetClaimParm (ParmID IN NUMBER, ParmName IN VARCHAR2, ParmValue IN VARCHAR2)
         IS
         BEGIN
       claimparm.Extend;
       claimparm(ParmID) := IKFLTClaimParm('', '');
       claimparm(ParmID).ParmID    := ParmName;
                 claimparm(ParmID).ParmValue := ParmValue;
         END;

   BEGIN
     SELECT * INTO claim_rec     FROM DCLAIM_DBT    WHERE t_ClaimID      = ClaimID;

         BEGIN
        SELECT * INTO credit_rec    FROM DCREDIT_C_DBT WHERE t_CreditNumber = claim_rec.t_CreditNumber_Ref;
         EXCEPTION
            WHEN NO_DATA_FOUND THEN NULL;
         END;

         BEGIN
        SELECT * INTO persn_rec     FROM DPERSN_DBT    WHERE t_PersonID     = credit_rec.t_ClientID_Ref;
         EXCEPTION
            WHEN NO_DATA_FOUND THEN NULL;
         END;

         BEGIN
        SELECT * INTO persnidc_rec  FROM DPERSNIDC_DBT WHERE t_PersonID     = credit_rec.t_ClientID_Ref AND t_IsMain = 'X';
         EXCEPTION
            WHEN NO_DATA_FOUND THEN NULL;
         END;

         BEGIN
        SELECT * INTO adress_rec    FROM dadress_dbt   WHERE t_PartyID      = credit_rec.t_ClientID_Ref AND t_Type   = 1;
         EXCEPTION
            WHEN NO_DATA_FOUND THEN NULL;
         END;

         BEGIN
        SELECT * INTO adressf_rec   FROM dadress_dbt   WHERE t_PartyID      = credit_rec.t_ClientID_Ref AND t_Type   = 2;
         EXCEPTION
            WHEN NO_DATA_FOUND THEN NULL;
         END;

     SetClaimParm(1,  'ClaimNumber',     claim_rec.t_ClaimNumber);
     SetClaimParm(2,  'UsDocNumber',     credit_rec.t_UsCreditNumber);
     SetClaimParm(3,  'RegDate',         TO_CHAR(credit_rec.t_RegDate, 'dd.mm.yyyy'));
     SetClaimParm(4,  'Returndate',      TO_CHAR(credit_rec.t_ReturnDate, 'dd.mm.yyyy'));
     SetClaimParm(5,  'CreditSum',       credit_rec.t_CreditSum);
     SetClaimParm(6,  'Duration',        credit_rec.t_Duration);
     SetClaimParm(7,  'DurationType',    credit_rec.t_TypeDuration);
     SetClaimParm(8,  'Name1',           persn_rec.t_Name1);
     SetClaimParm(9,  'Name2',           persn_rec.t_Name2);
     SetClaimParm(10, 'Name3',           persn_rec.t_Name3);
     SetClaimParm(11, 'Born',            TO_CHAR(persn_rec.t_Born,'dd.mm.yyyy'));
     SetClaimParm(12, 'PaperNumber',     persnidc_rec.t_paperNumber);
     SetClaimParm(13, 'PaperSeries',     persnidc_rec.t_PaperSeries);
     SetClaimParm(14, 'PaperIssuedDate', TO_CHAR(persnidc_rec.t_PaperIssuedDate, 'dd.mm.yyyy'));
     SetClaimParm(15, 'PaperIssuer',     persnidc_rec.t_PaperIssuer);
     SetClaimParm(16, 'Address',         adress_rec.t_Adress);
     SetClaimParm(17, 'AddressF',        adressf_rec.t_Adress);
     SetClaimParm(18, 'Rate',            lnGetRateVal(1, claim_rec.t_CreditNumber_Ref, 1));
     SetClaimParm(19, 'PercentSum',      GetUsFiledValue(LO_TYPECREDIT, credit_rec.t_CreditTypeID_Ref, 15001));
     SetClaimParm(20, 'LSelfSum',        claim_rec.t_LSelfSum);
     SetClaimParm(21, 'base',            GetUsFiledValue(LO_TYPECREDIT, credit_rec.t_CreditTypeID_Ref, 15003));
     SetClaimParm(22, 'newCommiss',      GetUsFiledValue(LO_TYPECREDIT, credit_rec.t_CreditTypeID_Ref, 15132));

     -- Загоняем пользовательские поля по заявкам в массив
     i := 23;
     FOR lufotype_rec IN (SELECT * FROM DLUFOTYPE_DBT WHERE t_ObjectID_Ref = LO_CLAIM
                                                        AND t_ObjID_Ref    = ClaimID
                                                        AND t_USFIELDGroupID_Ref = 0)
     LOOP
        SetClaimParm(i, GetFieldParmID(lufotype_rec.t_UsFieldID_Ref), GetUsFiledValue(LO_CLAIM, ClaimID, lufotype_rec.t_UsFieldID_Ref));
        i := i + 1;
     END LOOP;

     RETURN claimparm;
   EXCEPTION WHEN OTHERS
             THEN RETURN claimparm;
   END;
   
   PROCEDURE ProceedUserField(ClaimID IN NUMBER, ClaimParm IN IKFLTCLAIMPARM)
   IS
        usfObjTypeID  NUMBER(10);
        FieldTypeID   NUMBER(10);
        curdate       DATE  := TO_DATE ('1.1.1', 'dd.mm.yyyy');
   BEGIN   
        FieldTypeID := TO_NUMBER(SUBSTR(ClaimParm.ParmID, INSTR(ClaimParm.ParmID,':')+1));
        --Log(' procedure ProceedUserField ','ClaimID='||to_char(ClaimID)||';FieldTypeID='||to_char(FieldTypeID));
        IF FieldTypeID > 0 THEN
           BEGIN
                SELECT t_usfObjTypeID INTO usfObjTypeID FROM DLUFOTYPE_DBT
                                                    WHERE t_ObjectID_Ref  = 3
                                                    AND t_ObjID_Ref     = ClaimID
                                                    AND t_UsFieldID_Ref = FieldTypeID;
            EXCEPTION WHEN NO_DATA_FOUND
            THEN usfObjTypeID := 0;
           END;
           
           curdate := GetCurrentDate();

            IF usfObjTypeID > 0 THEN -- Поле уже есть обновим его значение
                InsertUsFieldValue(usfObjTypeID, 3, ClaimID, FieldTypeID, ClaimParm.ParmValue, curdate, FieldTypeID);
            ELSE -- Поля еще нет - добавим
                usfObjTypeID := 0;
                INSERT INTO DLUFOTYPE_DBT (t_UsFObjTypeID, t_ObjectID_Ref, t_ObjID_Ref, 
                                          t_UsFieldID_Ref, t_UsFieldGroupID_Ref, t_ParentID)
                VALUES (0,3,ClaimID,FieldTypeID,0, 0)
                RETURNING t_UsFObjTypeID INTO usfObjTypeID;
                InsertUsFieldValue(usfObjTypeID, 3, ClaimID, FieldTypeID, ClaimParm.ParmValue, curdate, FieldTypeID);
            END IF;
        ELSE
            RAISE eINCORRECT_USERFIELD_NUMBER;
        END IF;
    END ProceedUserField;   
    
    --получаем текущую дату
    FUNCTION GetCurrentDate RETURN DATE
    IS
        curdate       DATE;
    BEGIN
        SELECT MAX(t_Curdate) INTO curdate FROM DCURDATE_DBT;
        
        IF curdate IS NULL THEN
            RAISE eNO_DATE;
        END IF;
        
        RETURN curdate;
    END GetCurrentDate;
    -- Обновляемые поля заявки
    PROCEDURE ParseSystemClaimsField(ClaimParm IN OUT IKFLTCLAIMPARM,claim_rec IN OUT dclaim_dbt%ROWTYPE,ClaimNumID In OUT NUMBER,ParamNumber IN NUMBER,IsUpdate IN OUT BOOLEAN)
    IS
        ParmName      VARCHAR2(70);
    BEGIN
       ParmName := TRIM(UPPER(ClaimParm.ParmID));
       CASE ParmName
              WHEN 'NUMBER'           THEN ClaimNumID := ParamNumber; ClaimParm.ParmValue := '';
              WHEN 'GIVINGDATE'       THEN claim_rec.t_GivingDate       := lnGetDate(ClaimParm.ParmValue); IsUpdate := true;
              WHEN 'LCREDITSUM'       THEN claim_rec.t_LCreditSum       := NVL(to_number(trim(ClaimParm.ParmValue),'99999999999999.99'), '0'); IsUpdate := true;
              WHEN 'LCREDITCURCODE'   THEN claim_rec.t_LCreditCurCode   := NVL(ClaimParm.ParmValue, '0'); IsUpdate := true;
              WHEN 'LSELFSUM'         THEN claim_rec.t_LSelfSum         := NVL(to_number(trim(ClaimParm.ParmValue),'99999999999999.99'), '0'); IsUpdate := true;
              WHEN 'LSELFCURCODE'     THEN claim_rec.t_LSelfCurCode     := NVL(ClaimParm.ParmValue, '0'); IsUpdate := true;
              WHEN 'LCREDITDURATION'  THEN claim_rec.t_LCreditDuration  := NVL(ClaimParm.ParmValue, '0'); IsUpdate := true;
              WHEN 'LCTYPEDURATION'   THEN claim_rec.t_LCTypeDuration   := NVL(ClaimParm.ParmValue, '0'); IsUpdate := true;
              WHEN 'LPAYDURATION'     THEN claim_rec.t_LPayDuration     := NVL(ClaimParm.ParmValue, '0'); IsUpdate := true;
              WHEN 'LTYPEDURATION'    THEN claim_rec.t_LPTypeDuration   := NVL(ClaimParm.ParmValue, '0'); IsUpdate := true;
              WHEN 'LPERCRATE'        THEN claim_rec.t_LPercRate        := NVL(ClaimParm.ParmValue, '0'); IsUpdate := true;
              WHEN 'LCREDITPURPOSE'   THEN claim_rec.t_LCreditPurpose   := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 50); IsUpdate := true;
              WHEN 'CREDITTYPEID_REF' THEN claim_rec.t_CreditTypeID_Ref := NVL(ClaimParm.ParmValue, '0'); IsUpdate := true;
              WHEN 'SUMOBJECT'        THEN claim_rec.t_SumObject        := NVL(to_number(trim(ClaimParm.ParmValue),'99999999999999.99'), '0'); IsUpdate := true;
              WHEN 'SUMOBJECTCURCODE' THEN claim_rec.t_SumObjectCurCode := NVL(ClaimParm.ParmValue, '0'); IsUpdate := true;
              WHEN 'NOTE'             THEN claim_rec.t_NOTE             := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 80); IsUpdate := true;
              ELSE NULL;
        END CASE;                
        Log('ParseSystemClaimsField',ParmName||'='||to_char(ClaimParm.ParmValue));
    END ParseSystemClaimsField;
     
    --обновляем данные клиента
    PROCEDURE ParseSystemPersonsField(ClaimParm IN IKFLTCLAIMPARM,persn_rec IN OUT dpersn_dbt%ROWTYPE,IsUpdate IN OUT BOOLEAN)
    IS
        ParmName      VARCHAR2(70);
    BEGIN
        ParmName := TRIM(UPPER(ClaimParm.ParmID));
        CASE ParmName
              WHEN 'NAME1'     THEN persn_rec.t_NAME1     := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 24); IsUpdate := true;
              WHEN 'NAME2'     THEN persn_rec.t_NAME2     := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 24); IsUpdate := true;
              WHEN 'NAME3'     THEN persn_rec.t_NAME3     := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 24); IsUpdate := true;
              WHEN 'BIRSPLASE' THEN persn_rec.t_BIRSPLASE := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 80); IsUpdate := true;
              WHEN 'BORN'   THEN persn_rec.t_BORN    := lnGetDate(ClaimParm.ParmValue); IsUpdate := true;
              WHEN 'PLACEWORK' THEN persn_rec.t_PlaceWORK := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 50); IsUpdate := true;
              WHEN 'ISMALE' THEN persn_rec.t_IsMale  := TO_CHAR(SUBSTR(TRIM(ClaimParm.ParmValue), 1, 1)); IsUpdate := true;
              ELSE NULL;
         END CASE;
    END ParseSystemPersonsField;
    
    -- Обновляемые поля удостоверения личности
    PROCEDURE ParseSystemDocumentField(ClaimParm IN IKFLTCLAIMPARM,persnidc_rec IN OUT dpersnidc_dbt%ROWTYPE,IsUpdate IN OUT BOOLEAN)
    IS
        ParmName      VARCHAR2(70);
    BEGIN
        ParmName := TRIM(UPPER(ClaimParm.ParmID));
        CASE ParmName
              WHEN 'PAPERSERIES'     THEN persnidc_rec.t_PAPERSERIES     := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 12); IsUpdate := true;
              WHEN 'PAPERNUMBER'     THEN persnidc_rec.t_PAPERNUMBER     := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 25); IsUpdate := true;
              WHEN 'PAPERISSUEDDATE' THEN persnidc_rec.t_PAPERISSUEDDATE := lnGetDate(ClaimParm.ParmValue); IsUpdate := true;
              WHEN 'PAPERISSUER'     THEN persnidc_rec.t_PAPERISSUER     := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 164); IsUpdate := true;
              WHEN 'PAPERISSUERCODE' THEN persnidc_rec.t_PAPERISSUERCODE := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 10); IsUpdate := true;
              ELSE NULL;
         END CASE;       
    END ParseSystemDocumentField;
        
    -- Обновляемые поля адреса
    PROCEDURE ParseSystemAddressField(ClaimParm IN IKFLTCLAIMPARM,adress_rec IN OUT dadress_dbt%ROWTYPE,IsUpdate IN OUT BOOLEAN) 
    IS
        ParmName      VARCHAR2(70);
    BEGIN
        ParmName := TRIM(UPPER(ClaimParm.ParmID));
        CASE ParmName
              WHEN 'POSTINDEX' THEN adress_rec.t_POSTINDEX:= SUBSTR(TRIM(ClaimParm.ParmValue), 1, 15); IsUpdate  := true;
              WHEN 'CITY'     THEN adress_rec.t_PLACE    := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 49); IsUpdate  := true;
              WHEN 'PROVINCE' THEN adress_rec.t_PROVINCE := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 49); IsUpdate  := true;
              WHEN 'DISTRICT' THEN adress_rec.t_DISTRICT := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 49); IsUpdate  := true;
              WHEN 'STREET'   THEN adress_rec.t_STREET   := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 49); IsUpdate  := true;
              WHEN 'HOUSE'    THEN adress_rec.t_HOUSE    := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 10); IsUpdate  := true;
              WHEN 'CORP'     THEN adress_rec.t_NUMCORPS := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 5); IsUpdate  := true;
              WHEN 'FLAT'     THEN adress_rec.t_FLAT     := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 5); IsUpdate  := true;
              WHEN 'PHONENUMBER' THEN adress_rec.t_PHONENUMBER := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 24); IsUpdate  := true;
              WHEN 'PHONENUMBER2' THEN adress_rec.t_PHONENUMBER2 := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 24); IsUpdate  := true;
              WHEN 'FAX' THEN adress_rec.t_FAX := SUBSTR(TRIM(ClaimParm.ParmValue), 1, 24); IsUpdate  := true;
              ELSE NULL;
         END CASE;         
    END ParseSystemAddressField;
    
    -- Служебные параметры (возвращаемые)                 
    PROCEDURE ParseSystemErrorField(ClaimParm IN OUT IKFLTCLAIMPARM,ParamNumber IN NUMBER, ErrCodeID IN OUT NUMBER ,ErrDescID IN OUT NUMBER) 
    IS
        ParmName      VARCHAR2(70);
    BEGIN
        ParmName := TRIM(UPPER(ClaimParm.ParmID));
        ErrCodeID := 0;
        ErrDescID := 0;
        CASE ParmName
            WHEN 'ERRCODE' THEN ErrCodeID := ParamNumber; ClaimParm.ParmValue := ' ';
            WHEN 'ERRDESC' THEN ErrDescID := ParamNumber; ClaimParm.ParmValue := ' ';
            ELSE NULL;
         END CASE;         
    END ParseSystemErrorField;
    
   FUNCTION lnGetDate (StrDate IN VARCHAR2) RETURN Date
   IS
      formatStr VARCHAR2(10);
   BEGIN
      Log('format date',StrDate);
      IF INSTR(StrDate, '-') > 1 THEN
         formatStr := 'yyyy-mm-dd';
      ELSIF INSTR(StrDate, '.') > 1 THEN
         formatStr := 'yyyy.mm.dd';
      ELSE
         RAISE eINCORRECT_DATE;
      END IF;
      RETURN TO_DATE(TRIM(StrDate), formatStr);
      EXCEPTION WHEN OTHERS THEN RAISE eINCORRECT_DATE;
   END lnGetDate;        

END IKFL;
/
