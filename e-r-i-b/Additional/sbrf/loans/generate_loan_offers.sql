-- 
--  Генерирует для пользователя с login id указанное количество кредитных предложений  
--
DECLARE
-- login id клиента   
  LOGIN         CONSTANT INTEGER := login id пользователя;

-- количество предложений для клиента  
  OFFER_COUNT   CONSTANT INTEGER := количество генерируемых предложений;

-- валюта предложения
  CURRENCY_CODE VARCHAR2 (3);

-- максимальная сумма кредитного лимита
  LIMIT_AMOUNT  INTEGER;

  MONTH_6  INTEGER;
  MONTH_12 INTEGER;
  MONTH_18 INTEGER;
  MONTH_24 INTEGER;
  MONTH_36 INTEGER;
  MONTH_48 INTEGER;
  MONTH_60 INTEGER;

  RATE     NUMBER(19, 4);
BEGIN

  FOR i IN 1..OFFER_COUNT LOOP
    BEGIN

      CURRENCY_CODE := (CASE TRUNC(DBMS_RANDOM.VALUE(1, 3.4))
                        WHEN 1 THEN 'RUB'
                        WHEN 2 THEN 'EUR'
                        WHEN 3 THEN 'USD'
                        END);

      LIMIT_AMOUNT  := TRUNC(DBMS_RANDOM.VALUE(100, 2000));
      RATE          := ROUND(DBMS_RANDOM.VALUE(5, 20), 2);

      MONTH_6  := LIMIT_AMOUNT * (6  / 100);
      MONTH_12 := LIMIT_AMOUNT * (12 / 100);
      MONTH_18 := LIMIT_AMOUNT * (18 / 100);
      MONTH_24 := LIMIT_AMOUNT * (24 / 100);
      MONTH_36 := LIMIT_AMOUNT * (36 / 100);
      MONTH_48 := LIMIT_AMOUNT * (48 / 100);
      MONTH_60 := LIMIT_AMOUNT * (60 / 100);

      INSERT INTO LOAN_OFFER (ID, DEPARTMENT_ID, PASPORT_NUMBER, PASPORT_SERIES, TB, LOGIN_ID, CURRENCY, MAX_LIMIT_AMOUNT, PRODUCT_NAME, MONTH6, MONTH12, MONTH18, MONTH24, MONTH36, MONTH48, MONTH60, PROCENT_RATE)
        SELECT S_LOAN_OFFER.NEXTVAL, USERS.DEPARTMENT_ID, DOCUMENTS.DOC_NUMBER, DOCUMENTS.DOC_SERIES, DEPARTMENTS.TB, LOGIN, CURRENCY_CODE, LIMIT_AMOUNT, 'PRODUCT_NAME', MONTH_6, MONTH_12, MONTH_18, MONTH_24, MONTH_36, MONTH_48, MONTH_60, RATE
        FROM LOGINS

          JOIN USERS       ON (LOGINS.ID = USERS.LOGIN_ID AND
                               LOGINS.ID = LOGIN)

          JOIN DOCUMENTS   ON (DOCUMENTS.PERSON_ID = USERS.ID  AND
                               DOCUMENTS.DOC_MAIN  = '1')

          JOIN DEPARTMENTS ON (DEPARTMENTS.ID = USERS.DEPARTMENT_ID);
    END;
  END LOOP;
END;