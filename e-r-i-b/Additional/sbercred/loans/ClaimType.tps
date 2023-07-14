CREATE OR REPLACE
TYPE "IKFLTPLANPAY" AS OBJECT (
    PlannedPayDate    VARCHAR2(10),
    PlannedExpDate    VARCHAR2(10),
    PlannedPaySum     VARCHAR2(20),
    PlannedPercentSum VARCHAR2(20),
    PlannedKomission  VARCHAR2(20),
    PlannedTotalSum   VARCHAR2(20),
    PlannedMainRest   VARCHAR2(20),
    AdvancedRepayment VARCHAR2(20)
  )
/
CREATE OR REPLACE TYPE "IKFLTAPLANPAY" IS TABLE OF IKFLTPlanpay
/

CREATE OR REPLACE
TYPE "IKFLTUSERFIELD" AS OBJECT (      
      UserFieldID       NUMBER,
      UserFieldValue    VARCHAR2(99)
  )
/
CREATE OR REPLACE TYPE "IKFLTAUSERFIELD" IS TABLE OF IKFLTUserField;
/

CREATE OR REPLACE
TYPE "IKFLTCRDTYPE" AS OBJECT (      
      Code              NUMBER(5),   
      CreditTypeName    CHAR(79),     
      CurCodeISO        CHAR(3),      
      CurCode           NUMBER(10),   
      Duration          NUMBER(5),    
      TypeDuration      NUMBER(5),   
      MainRate          FLOAT(53),    
      LimitVal          NUMBER(19,4), 
      LimitOver         CHAR,         
      CreditTypeID      NUMBER(10),
      PenaltyPerDay     FLOAT(53),  
      AdditionFields    IKFLTAUSERFIELD  
  )
/
CREATE OR REPLACE TYPE "IKFLTACRDTYPE" IS TABLE OF IKFLTCrdType;
/

CREATE OR REPLACE
TYPE "IKFLTCLAIMPARM" AS OBJECT (
      ParmID            VARCHAR2(70),
      ParmValue         VARCHAR2(99)
  )
/
CREATE OR REPLACE TYPE IKFLTACLAIMPARM IS TABLE OF IKFLTClaimParm;
/

CREATE OR REPLACE
TYPE "IKFLTCLAIMSTATE" AS OBJECT (
      ClaimID        VARCHAR2(10),
      State          VARCHAR2(99),
      CreditSum      VARCHAR2(20),
      CreditDuration VARCHAR2(20),
      CreditTypeDuration VARCHAR2(20),
      CreditSumCurrency VARCHAR2(20)
  )
/
CREATE OR REPLACE
TYPE "IKFLTACLAIMSTATE" IS TABLE OF IKFLTClaimState
/

CREATE OR REPLACE
TYPE "IKFLTTYPECRDLST" AS OBJECT (
    Code              NUMBER(5),
    CreditTypeName    CHAR(79),
    CurCodeISO        CHAR(3),
    CurCode           NUMBER(10),
    Duration          NUMBER(5),
    TypeDuration      NUMBER(5),
    MainRate          FLOAT(53),
    LimitVal          NUMBER(19,4),
    LimitOver         CHAR,
    CreditTypeID      NUMBER(10),
    UserField1        VARCHAR2(99),
    UserField2        VARCHAR2(99),
    UserField3        VARCHAR2(99),
    UserField4        VARCHAR2(99),
    UserField5        VARCHAR2(99)
  )
/
CREATE OR REPLACE TYPE "IKFLTATYPECRDLST"  IS TABLE OF IKFLTTypeCrdLst
/

CREATE OR REPLACE
TYPE "IKFLTURNSALD_T" IS OBJECT (
    USCRDNUM VARCHAR2(15),
    CLIENTNAME VARCHAR2(320),
    ACCOUNTNUMBER VARCHAR2(25),
    SHORTNAME VARCHAR2(30),
    CURCODE NUMBER,
    INREST NUMBER(19,4),
    DEB NUMBER(19,4),
    CRED NUMBER(19,4),
    OUTREST NUMBER(19,4)
);
/
CREATE OR REPLACE TYPE "IKFLTURNSALD_TABLE" IS TABLE OF IKFLturnsald_t;
/

CREATE OR REPLACE TYPE "IKFLTANUMBER" IS TABLE OF NUMBER;
/
