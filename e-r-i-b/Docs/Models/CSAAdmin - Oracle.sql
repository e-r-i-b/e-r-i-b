/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     28.07.2015 10:56:47                          */
/*==============================================================*/


DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_ADV_DEP_TO_ADVERTISINGS'; 
    EXECUTE IMMEDIATE('drop index DXFK_ADV_DEP_TO_ADVERTISINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISINGS_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('alter table ADVERTISINGS_DEPARTMENTS drop constraint FK_ADV_DEP_TO_ADVERTISINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_ADV_AREA_TO_ADVERTISINGS'; 
    EXECUTE IMMEDIATE('drop index DXFK_ADV_AREA_TO_ADVERTISINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_AREAS'; 
    EXECUTE IMMEDIATE('alter table ADVERTISING_AREAS drop constraint FK_ADV_AREA_TO_ADVERTISINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_ADV_BUTT_TO_ADVERTISINGS'; 
    EXECUTE IMMEDIATE('drop index DXFK_ADV_BUTT_TO_ADVERTISINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_BUTTONS'; 
    EXECUTE IMMEDIATE('alter table ADVERTISING_BUTTONS drop constraint FK_ADV_BUTT_TO_ADVERTISINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_ADV_REQ_TO_ADVERTISINGS'; 
    EXECUTE IMMEDIATE('drop index DXFK_ADV_REQ_TO_ADVERTISINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_REQUIREMENTS'; 
    EXECUTE IMMEDIATE('alter table ADVERTISING_REQUIREMENTS drop constraint FK_ADV_REQ_TO_ADVERTISINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_ADV_REQ_ACC_TYPES_TO_ADV'; 
    EXECUTE IMMEDIATE('drop index DXFK_ADV_REQ_ACC_TYPES_TO_ADV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_REQ_ACC_TYPES'; 
    EXECUTE IMMEDIATE('alter table ADVERTISING_REQ_ACC_TYPES drop constraint FK_ADV_REQ_ACC_TYPES_TO_ADV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_ADV_REQ_ACC_T_TO_DEPOSITS'; 
    EXECUTE IMMEDIATE('drop index DXFK_ADV_REQ_ACC_T_TO_DEPOSITS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_REQ_ACC_TYPES'; 
    EXECUTE IMMEDIATE('alter table ADVERTISING_REQ_ACC_TYPES drop constraint FK_ADVERTIS_FK_ADV_RE_DEPOSITD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ALLOWED_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('alter table ALLOWED_DEPARTMENTS drop constraint FK_A_DEPARTMENTS_TO_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ATTRIBUTES_FOR_IDENT_TYPES'; 
    EXECUTE IMMEDIATE('alter table ATTRIBUTES_FOR_IDENT_TYPES drop constraint "FK_ATTRIBUTE_TO_IDENT "');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_AUTH_TOKEN_TO_SESSIONS'; 
    EXECUTE IMMEDIATE('drop index DXFK_AUTH_TOKEN_TO_SESSIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'AUTHENTICATION_TOKEN'; 
    EXECUTE IMMEDIATE('alter table AUTHENTICATION_TOKEN drop constraint FK_AUTH_TOKEN_TO_SESSIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_AUTOPAY_SETTING_TO_PROV'; 
    EXECUTE IMMEDIATE('drop index DXFK_AUTOPAY_SETTING_TO_PROV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'AUTOPAY_SETTINGS'; 
    EXECUTE IMMEDIATE('alter table AUTOPAY_SETTINGS drop constraint FK_AUTOPAY_SETTING_TO_PROV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'C_CENTER_AREAS_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('alter table C_CENTER_AREAS_DEPARTMENTS drop constraint FK_C_C_AREA_DEP_TO_C_C_AREA');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DEPARTMENTS_TASKS_CONTENT'; 
    EXECUTE IMMEDIATE('alter table DEPARTMENTS_TASKS_CONTENT drop constraint FK_CONTENT_TO_DEP_TASKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_EMPLOYEE_TO_LOGINS'; 
    EXECUTE IMMEDIATE('drop index DXFK_EMPLOYEE_TO_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'EMPLOYEES'; 
    EXECUTE IMMEDIATE('alter table EMPLOYEES drop constraint FK_EMPLOYEE_TO_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXEXCEPTION_MAPPING_RESTRICTIO'; 
    EXECUTE IMMEDIATE('drop index DXEXCEPTION_MAPPING_RESTRICTIO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'EXCEPTION_MAPPING_RESTRICTIONS'; 
    EXECUTE IMMEDIATE('alter table EXCEPTION_MAPPING_RESTRICTIONS drop constraint FK_EXCEPTIO_EXCEPTION_EXCEPTIO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFIELD_DESC_TO_PROVIDERS'; 
    EXECUTE IMMEDIATE('drop index DXFIELD_DESC_TO_PROVIDERS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_DESCRIPTIONS'; 
    EXECUTE IMMEDIATE('alter table FIELD_DESCRIPTIONS drop constraint FIELD_DESC_TO_PROVIDERS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_F_REQUISITE_TYPE_TO_FIELD'; 
    EXECUTE IMMEDIATE('drop index DXFK_F_REQUISITE_TYPE_TO_FIELD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_REQUISITE_TYPES'; 
    EXECUTE IMMEDIATE('alter table FIELD_REQUISITE_TYPES drop constraint FK_F_REQUISITE_TYPE_TO_FIELD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_F_VALID_TO_FIELD'; 
    EXECUTE IMMEDIATE('drop index DXFK_F_VALID_TO_FIELD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_VALIDATORS_DESCR'; 
    EXECUTE IMMEDIATE('alter table FIELD_VALIDATORS_DESCR drop constraint FK_F_VALID_TO_FIELD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_VAL_PARAM_TO_VALID'; 
    EXECUTE IMMEDIATE('drop index DXFK_VAL_PARAM_TO_VALID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_VALIDATORS_PARAM'; 
    EXECUTE IMMEDIATE('alter table FIELD_VALIDATORS_PARAM drop constraint FK_VAL_PARAM_TO_VALID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_F_VALUE_TO_FIELD'; 
    EXECUTE IMMEDIATE('drop index DXFK_F_VALUE_TO_FIELD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_VALUES_DESCR'; 
    EXECUTE IMMEDIATE('alter table FIELD_VALUES_DESCR drop constraint FK_F_VALUE_TO_FIELD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_LOGINS_TO_ACCESSSC'; 
    EXECUTE IMMEDIATE('drop index DXFK_LOGINS_TO_ACCESSSC');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'LOGINS'; 
    EXECUTE IMMEDIATE('alter table LOGINS drop constraint FK_LOGINS_TO_ACCESSSC');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_B_LOGIN_E_TO_LOGINS'; 
    EXECUTE IMMEDIATE('drop index DXFK_B_LOGIN_E_TO_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'LOGIN_BLOCK'; 
    EXECUTE IMMEDIATE('alter table LOGIN_BLOCK drop constraint FK_B_LOGIN_E_TO_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'LOGIN_BLOCK'; 
    EXECUTE IMMEDIATE('alter table LOGIN_BLOCK drop constraint FK_B_LOGIN_TO_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_OPER_CONTEXT_TO_SESSIONS'; 
    EXECUTE IMMEDIATE('drop index DXFK_OPER_CONTEXT_TO_SESSIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'OPERATION_CONTEXT'; 
    EXECUTE IMMEDIATE('alter table OPERATION_CONTEXT drop constraint FK_OPERATIO_FK_OPER_C_SESSIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PANEL_BLOCKS_TO_PROVIDERS'; 
    EXECUTE IMMEDIATE('drop index DXFK_PANEL_BLOCKS_TO_PROVIDERS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PANEL_BLOCKS'; 
    EXECUTE IMMEDIATE('alter table PANEL_BLOCKS drop constraint FK_PANEL_BLOCKS_TO_PROVIDERS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PAN_BLCKS_TO_Q_P_PANELS'; 
    EXECUTE IMMEDIATE('drop index DXFK_PAN_BLCKS_TO_Q_P_PANELS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PANEL_BLOCKS'; 
    EXECUTE IMMEDIATE('alter table PANEL_BLOCKS drop constraint FK_PAN_BLCKS_TO_Q_P_PANELS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_TO_PAY_GR_RSK_GROUP_RISK'; 
    EXECUTE IMMEDIATE('drop index DXFK_TO_PAY_GR_RSK_GROUP_RISK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PAYMENTS_GROUP_RISK'; 
    EXECUTE IMMEDIATE('alter table PAYMENTS_GROUP_RISK drop constraint FK_TO_PAY_GR_RSK_GROUP_RISK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_P_SERVICE_TO_IMAGES'; 
    EXECUTE IMMEDIATE('drop index DXFK_P_SERVICE_TO_IMAGES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PAYMENT_SERVICES'; 
    EXECUTE IMMEDIATE('alter table PAYMENT_SERVICES drop constraint FK_P_SERVICE_TO_IMAGES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_P_SERVICE_TO_P_SERVICE'; 
    EXECUTE IMMEDIATE('drop index DXFK_P_SERVICE_TO_P_SERVICE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PAYMENT_SERVICES'; 
    EXECUTE IMMEDIATE('alter table PAYMENT_SERVICES drop constraint FK_P_SERVICE_TO_P_SERVICE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PAY_SER_TO_PARENT'; 
    EXECUTE IMMEDIATE('drop index DXFK_PAY_SER_TO_PARENT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PAYMENT_SERV_PARENTS'; 
    EXECUTE IMMEDIATE('alter table PAYMENT_SERV_PARENTS drop constraint FK_PAY_SER_TO_PARENT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PAY_SER_TO_SERV'; 
    EXECUTE IMMEDIATE('drop index DXFK_PAY_SER_TO_SERV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PAYMENT_SERV_PARENTS'; 
    EXECUTE IMMEDIATE('alter table PAYMENT_SERV_PARENTS drop constraint FK_PAY_SER_TO_SERV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_ANSWERS_TO_QUESTIONS'; 
    EXECUTE IMMEDIATE('drop index DXFK_ANSWERS_TO_QUESTIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_ANSWERS'; 
    EXECUTE IMMEDIATE('alter table PFP_ANSWERS drop constraint FK_ANSWERS_TO_QUESTIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_C_PROD_TO_ACCOUNT_PRO'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_C_PROD_TO_ACCOUNT_PRO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_COMPLEX_PRODUCTS'; 
    EXECUTE IMMEDIATE('alter table PFP_COMPLEX_PRODUCTS drop constraint FK_PFP_C_PROD_TO_ACCOUNT_PROD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_CP_T_FK_TABLE_PFP_TAB'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_CP_T_FK_TABLE_PFP_TAB');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CP_TABLE_VIEW_PARAMETERS'; 
    EXECUTE IMMEDIATE('alter table PFP_CP_TABLE_VIEW_PARAMETERS drop constraint FK_PFP_CP_T_FK_TABLE_PFP_TABL');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CR_CARDS'; 
    EXECUTE IMMEDIATE('alter table PFP_CR_CARDS drop constraint FK_PFP_CR_CARDS_TO_CR');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CR_CARDS'; 
    EXECUTE IMMEDIATE('alter table PFP_CR_CARDS drop constraint FK_PFP_CR_TO_CARDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CR_STEPS'; 
    EXECUTE IMMEDIATE('alter table PFP_CR_STEPS drop constraint FK_PFP_CR_STEPS_TO_CR');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_C_PROD_TO_FUND_PROD'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_C_PROD_TO_FUND_PROD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_C_FUND_PRODUCTS'; 
    EXECUTE IMMEDIATE('alter table PFP_C_FUND_PRODUCTS drop constraint FK_PFP_C_PROD_TO_FUND_PROD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_C_PROD_TO_IMA_PROD'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_C_PROD_TO_IMA_PROD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_C_IMA_PRODUCTS'; 
    EXECUTE IMMEDIATE('alter table PFP_C_IMA_PRODUCTS drop constraint FK_PFP_C_IM_FK_PFP_C__PFP_PROD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_C_I_PROD_TO_I_PROD'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_C_I_PROD_TO_I_PROD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_C_INSURANCE_PRODUCTS'; 
    EXECUTE IMMEDIATE('alter table PFP_C_INSURANCE_PRODUCTS drop constraint FK_PFP_C_I_PROD_TO_I_PROD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_AXIS_TO_PT'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_AXIS_TO_PT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_DIAGRAM_STEPS'; 
    EXECUTE IMMEDIATE('alter table PFP_DIAGRAM_STEPS drop constraint FK_PFP_AXIS_TO_PT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_INS_P_TYPE_TO_D_RERIO'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_INS_P_TYPE_TO_D_RERIO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INSURANCE_DATE_PERIODS'; 
    EXECUTE IMMEDIATE('alter table PFP_INSURANCE_DATE_PERIODS drop constraint FK_PFP_INS_P_TYPE_TO_D_RERIOD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_COMPANY_TO_INS'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_COMPANY_TO_INS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INSURANCE_PRODUCTS'; 
    EXECUTE IMMEDIATE('alter table PFP_INSURANCE_PRODUCTS drop constraint FK_PFP_COMPANY_TO_INS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_I_PRODUCTS_TO_TYPES'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_I_PRODUCTS_TO_TYPES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INSURANCE_PRODUCTS'; 
    EXECUTE IMMEDIATE('alter table PFP_INSURANCE_PRODUCTS drop constraint FK_PFP_I_PRODUCTS_TO_TYPES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_I_TYPES_TO_I_TYPES'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_I_TYPES_TO_I_TYPES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INSURANCE_TYPES'; 
    EXECUTE IMMEDIATE('alter table PFP_INSURANCE_TYPES drop constraint FK_PFP_I_TYPES_TO_I_TYPES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_PENSION_PRODUCT_TO_FU'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_PENSION_PRODUCT_TO_FU');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PENSION_PRODUCT'; 
    EXECUTE IMMEDIATE('alter table PFP_PENSION_PRODUCT drop constraint FK_PFP_PENSION_PRODUCT_TO_FUND');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_INV_PERIOD_TO_PRODUCT'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_INV_PERIOD_TO_PRODUCT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PRODUCTS'; 
    EXECUTE IMMEDIATE('alter table PFP_PRODUCTS drop constraint FK_PFP_INV_PERIOD_TO_PRODUCT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_RISK_TO_PRODUCT'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_RISK_TO_PRODUCT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PRODUCTS'; 
    EXECUTE IMMEDIATE('alter table PFP_PRODUCTS drop constraint FK_PFP_RISK_TO_PRODUCT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_SEGMENT_TO_PT'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_SEGMENT_TO_PT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PT_TARGET_GROUPS'; 
    EXECUTE IMMEDIATE('alter table PFP_PT_TARGET_GROUPS drop constraint FK_PFP_SEGMENT_TO_PT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_TARGET_GR_TO_P_PRODUC'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_TARGET_GR_TO_P_PRODUC');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_P_PRODUCT_TARGET_GROUPS'; 
    EXECUTE IMMEDIATE('alter table PFP_P_PRODUCT_TARGET_GROUPS drop constraint FK_PFP_TARGET_GR_TO_P_PRODUCT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_SP_T_FK_TABLE_PFP_TAB'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_SP_T_FK_TABLE_PFP_TAB');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_SP_TABLE_VIEW_PARAMETERS'; 
    EXECUTE IMMEDIATE('alter table PFP_SP_TABLE_VIEW_PARAMETERS drop constraint FK_PFP_SP_T_FK_TABLE_PFP_TABL');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PFP_TABLE_COLUMNS_TO_PT'; 
    EXECUTE IMMEDIATE('drop index DXFK_PFP_TABLE_COLUMNS_TO_PT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_TABLE_COLUMNS'; 
    EXECUTE IMMEDIATE('alter table PFP_TABLE_COLUMNS drop constraint FK_PFP_TABLE_COLUMNS_TO_PT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXPROVIDER_PROP_TO_PROVIDER'; 
    EXECUTE IMMEDIATE('drop index DXPROVIDER_PROP_TO_PROVIDER');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROVIDER_PROPERTIES'; 
    EXECUTE IMMEDIATE('alter table PROVIDER_PROPERTIES drop constraint PROVIDER_PROP_TO_PROVIDER');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_SMS_ALIAS_TO_PROVIDER'; 
    EXECUTE IMMEDIATE('drop index DXFK_SMS_ALIAS_TO_PROVIDER');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROVIDER_SMS_ALIAS'; 
    EXECUTE IMMEDIATE('alter table PROVIDER_SMS_ALIAS drop constraint FK_SMS_ALIAS_TO_PROVIDER');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PROVIDER_FK_ALIAS__FIELD_'; 
    EXECUTE IMMEDIATE('drop index DXFK_PROVIDER_FK_ALIAS__FIELD_');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROVIDER_SMS_ALIAS_FIELD'; 
    EXECUTE IMMEDIATE('alter table PROVIDER_SMS_ALIAS_FIELD drop constraint FK_PROVIDER_FK_ALIAS__FIELD_DE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PROVIDER_FK_ALIAS__PROVID'; 
    EXECUTE IMMEDIATE('drop index DXFK_PROVIDER_FK_ALIAS__PROVID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROVIDER_SMS_ALIAS_FIELD'; 
    EXECUTE IMMEDIATE('alter table PROVIDER_SMS_ALIAS_FIELD drop constraint FK_PROVIDER_FK_ALIAS__PROVIDER');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_Q_P_PAN_DEP_TO_Q_P_PAN'; 
    EXECUTE IMMEDIATE('drop index DXFK_Q_P_PAN_DEP_TO_Q_P_PAN');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'Q_P_PANELS_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('alter table Q_P_PANELS_DEPARTMENTS drop constraint FK_Q_P_PAN_DEP_TO_Q_P_PAN');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_CALENDAR_TO_RT'; 
    EXECUTE IMMEDIATE('drop index DXFK_CALENDAR_TO_RT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'RECEPTIONTIMES'; 
    EXECUTE IMMEDIATE('alter table RECEPTIONTIMES drop constraint FK_RECEPTIO_FK_CALEND_CALENDAR');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXDXFK_REGION_TO_REGION'; 
    EXECUTE IMMEDIATE('drop index DXDXFK_REGION_TO_REGION');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'REGIONS'; 
    EXECUTE IMMEDIATE('alter table REGIONS drop constraint FK_REGION_TO_REGION');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_SERVICE__FK_S_PROV_GROUPS'; 
    EXECUTE IMMEDIATE('drop index DXFK_SERVICE__FK_S_PROV_GROUPS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDERS'; 
    EXECUTE IMMEDIATE('alter table SERVICE_PROVIDERS drop constraint FK_SERVICE__FK_S_PROV_GROUPS_R');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_SERVICE__FK_S_PROV_IMAGES'; 
    EXECUTE IMMEDIATE('drop index DXFK_SERVICE__FK_S_PROV_IMAGES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDERS'; 
    EXECUTE IMMEDIATE('alter table SERVICE_PROVIDERS drop constraint FK_SERVICE__FK_S_PROV_IMAGES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_S_PROVIDERS_TO_BILLINGS'; 
    EXECUTE IMMEDIATE('drop index DXFK_S_PROVIDERS_TO_BILLINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDERS'; 
    EXECUTE IMMEDIATE('alter table SERVICE_PROVIDERS drop constraint FK_S_PROVIDERS_TO_BILLINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_S_PROVIDERS_TO_DEPARTMENT'; 
    EXECUTE IMMEDIATE('drop index DXFK_S_PROVIDERS_TO_DEPARTMENT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDERS'; 
    EXECUTE IMMEDIATE('alter table SERVICE_PROVIDERS drop constraint FK_S_PROVIDERS_TO_DEPARTMENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_S_PROVIDERS_TO_IMAGES'; 
    EXECUTE IMMEDIATE('drop index DXFK_S_PROVIDERS_TO_IMAGES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDERS'; 
    EXECUTE IMMEDIATE('alter table SERVICE_PROVIDERS drop constraint FK_S_PROVIDERS_TO_IMAGES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PROV_REG_TO_PROV'; 
    EXECUTE IMMEDIATE('drop index DXFK_PROV_REG_TO_PROV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDER_REGIONS'; 
    EXECUTE IMMEDIATE('alter table SERVICE_PROVIDER_REGIONS drop constraint FK_PROV_REG_TO_PROV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PROV_REG_TO_REG'; 
    EXECUTE IMMEDIATE('drop index DXFK_PROV_REG_TO_REG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDER_REGIONS'; 
    EXECUTE IMMEDIATE('alter table SERVICE_PROVIDER_REGIONS drop constraint FK_PROV_REG_TO_REG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PROV_PAY_SER_TO_PAY'; 
    EXECUTE IMMEDIATE('drop index DXFK_PROV_PAY_SER_TO_PAY');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERV_PROVIDER_PAYMENT_SERV'; 
    EXECUTE IMMEDIATE('alter table SERV_PROVIDER_PAYMENT_SERV drop constraint FK_PROV_PAY_SER_TO_PAY');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_PROV_PAY_SER_TO_PROV'; 
    EXECUTE IMMEDIATE('drop index DXFK_PROV_PAY_SER_TO_PROV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERV_PROVIDER_PAYMENT_SERV'; 
    EXECUTE IMMEDIATE('alter table SERV_PROVIDER_PAYMENT_SERV drop constraint FK_PROV_PAY_SER_TO_PROV');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_SESSIONS_TO_LOGINS'; 
    EXECUTE IMMEDIATE('drop index DXFK_SESSIONS_TO_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SESSIONS'; 
    EXECUTE IMMEDIATE('alter table SESSIONS drop constraint FK_SESSIONS_TO_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_ACCESSSCHEMES_MAIL'; 
    EXECUTE IMMEDIATE('drop index I_ACCESSSCHEMES_MAIL');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_TYPE'; 
    EXECUTE IMMEDIATE('drop index INDEX_TYPE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ACCESSSCHEMES'; 
    EXECUTE IMMEDIATE('drop table ACCESSSCHEMES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ACCESSSCHEMES';
    EXECUTE IMMEDIATE('drop sequence  S_ACCESSSCHEMES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_ADVERTISINGS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_ADVERTISINGS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISINGS'; 
    EXECUTE IMMEDIATE('drop table ADVERTISINGS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ADVERTISINGS';
    EXECUTE IMMEDIATE('drop sequence  S_ADVERTISINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISINGS_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('drop table ADVERTISINGS_DEPARTMENTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISINGS_RES'; 
    EXECUTE IMMEDIATE('drop table ADVERTISINGS_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_AREAS'; 
    EXECUTE IMMEDIATE('drop table ADVERTISING_AREAS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ADVERTISING_AREAS';
    EXECUTE IMMEDIATE('drop sequence  S_ADVERTISING_AREAS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_ADVERTISING_BUTTONS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_ADVERTISING_BUTTONS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_BUTTONS'; 
    EXECUTE IMMEDIATE('drop table ADVERTISING_BUTTONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ADVERTISING_BUTTONS';
    EXECUTE IMMEDIATE('drop sequence  S_ADVERTISING_BUTTONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_BUTTONS_RES'; 
    EXECUTE IMMEDIATE('drop table ADVERTISING_BUTTONS_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_REQUIREMENTS'; 
    EXECUTE IMMEDIATE('drop table ADVERTISING_REQUIREMENTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ADVERTISING_REQUIREMENTS';
    EXECUTE IMMEDIATE('drop sequence  S_ADVERTISING_REQUIREMENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISING_REQ_ACC_TYPES'; 
    EXECUTE IMMEDIATE('drop table ADVERTISING_REQ_ACC_TYPES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ADVERTISING_REQ_ACC_TYPES';
    EXECUTE IMMEDIATE('drop sequence  S_ADVERTISING_REQ_ACC_TYPES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_ALLOWED_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('drop index I_ALLOWED_DEPARTMENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ALLOWED_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('drop table ALLOWED_DEPARTMENTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ALLOWED_DEPARTMENTS';
    EXECUTE IMMEDIATE('drop sequence  S_ALLOWED_DEPARTMENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ATTRIBUTES_FOR_IDENT_TYPES'; 
    EXECUTE IMMEDIATE('drop table ATTRIBUTES_FOR_IDENT_TYPES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ATTRIBUTES_FOR_IDENT_TYPES';
    EXECUTE IMMEDIATE('drop sequence  S_ATTRIBUTES_FOR_IDENT_TYPES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'AUTHENTICATION_TOKEN'; 
    EXECUTE IMMEDIATE('drop table AUTHENTICATION_TOKEN cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'AUTOPAY_SETTINGS'; 
    EXECUTE IMMEDIATE('drop table AUTOPAY_SETTINGS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_AUTOPAY_SETTINGS';
    EXECUTE IMMEDIATE('drop sequence  S_AUTOPAY_SETTINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_AVTR_LAST_DATE'; 
    EXECUTE IMMEDIATE('drop index IDX_AVTR_LAST_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'AVATAR_CHANGE_JURNAL'; 
    EXECUTE IMMEDIATE('drop table AVATAR_CHANGE_JURNAL cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_ACCOUNT_NUMBER'; 
    EXECUTE IMMEDIATE('drop index INDEX_ACCOUNT_NUMBER');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'BANNED_ACCOUNTS'; 
    EXECUTE IMMEDIATE('drop table BANNED_ACCOUNTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_BANNED_ACCOUNTS';
    EXECUTE IMMEDIATE('drop sequence  S_BANNED_ACCOUNTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_BILLINGS_CODE'; 
    EXECUTE IMMEDIATE('drop index I_BILLINGS_CODE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'BILLINGS'; 
    EXECUTE IMMEDIATE('drop table BILLINGS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_BILLINGS';
    EXECUTE IMMEDIATE('drop sequence  S_BILLINGS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_UUID_CALENDARS'; 
    EXECUTE IMMEDIATE('drop index I_UUID_CALENDARS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CALENDARS'; 
    EXECUTE IMMEDIATE('drop table CALENDARS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CALENDARS';
    EXECUTE IMMEDIATE('drop sequence  S_CALENDARS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_CONTACT_CENTER_AREAS_NAME'; 
    EXECUTE IMMEDIATE('drop index I_CONTACT_CENTER_AREAS_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_CONTACT_CENTER_AREAS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_CONTACT_CENTER_AREAS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CONTACT_CENTER_AREAS'; 
    EXECUTE IMMEDIATE('drop table CONTACT_CENTER_AREAS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CONTACT_CENTER_AREAS';
    EXECUTE IMMEDIATE('drop sequence  S_CONTACT_CENTER_AREAS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_CREDIT_OFFER_TEMPLATE_UUID'; 
    EXECUTE IMMEDIATE('drop index IDX_CREDIT_OFFER_TEMPLATE_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CREDIT_OFFER_TEMPLATE'; 
    EXECUTE IMMEDIATE('drop table CREDIT_OFFER_TEMPLATE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CREDIT_OFFER_TEMPLATE';
    EXECUTE IMMEDIATE('drop sequence  S_CREDIT_OFFER_TEMPLATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_C_CENTER_AREAS_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('drop index I_C_CENTER_AREAS_DEPARTMENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'C_CENTER_AREAS_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('drop table C_CENTER_AREAS_DEPARTMENTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_C_CENTER_AREAS_DEPARTMENTS';
    EXECUTE IMMEDIATE('drop sequence  S_C_CENTER_AREAS_DEPARTMENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_DEPARTMENTS_TBS'; 
    EXECUTE IMMEDIATE('drop index I_DEPARTMENTS_TBS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DEPARTMENTS_INDEX_OFFICE'; 
    EXECUTE IMMEDIATE('drop index DEPARTMENTS_INDEX_OFFICE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_DEPARTMENTS_BANK_INFO'; 
    EXECUTE IMMEDIATE('drop index I_DEPARTMENTS_BANK_INFO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'LIST_VSP_INDEX_DEPS'; 
    EXECUTE IMMEDIATE('drop index LIST_VSP_INDEX_DEPS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DEPARTMENTS'; 
    EXECUTE IMMEDIATE('drop table DEPARTMENTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_DEPARTMENTS';
    EXECUTE IMMEDIATE('drop sequence  S_DEPARTMENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DEPARTMENTS_RECORDING'; 
    EXECUTE IMMEDIATE('drop table DEPARTMENTS_RECORDING cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_DEPARTMENTS_RECORDING';
    EXECUTE IMMEDIATE('drop sequence  S_DEPARTMENTS_RECORDING');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DEPARTMENTS_RECORDING_TMP'; 
    EXECUTE IMMEDIATE('drop table DEPARTMENTS_RECORDING_TMP cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_DEPARTMENTS_RECORDING_TMP';
    EXECUTE IMMEDIATE('drop sequence  S_DEPARTMENTS_RECORDING_TMP');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DEPARTMENTS_REPLICA_TASKS'; 
    EXECUTE IMMEDIATE('drop table DEPARTMENTS_REPLICA_TASKS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_DEPARTMENTS_REPLICA_TASKS';
    EXECUTE IMMEDIATE('drop sequence  S_DEPARTMENTS_REPLICA_TASKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DEPARTMENTS_TASKS_CONTENT'; 
    EXECUTE IMMEDIATE('drop table DEPARTMENTS_TASKS_CONTENT cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UK_P_ID'; 
    EXECUTE IMMEDIATE('drop index UK_P_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UK_KEY'; 
    EXECUTE IMMEDIATE('drop index UK_KEY');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DEPOSITDESCRIPTIONS'; 
    EXECUTE IMMEDIATE('drop table DEPOSITDESCRIPTIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_DEPOSITDESCRIPTIONS';
    EXECUTE IMMEDIATE('drop sequence  S_DEPOSITDESCRIPTIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DEPOSITGLOBALS'; 
    EXECUTE IMMEDIATE('drop table DEPOSITGLOBALS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DEPOSIT_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('drop table DEPOSIT_DEPARTMENTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_DICTIONARY_CHANGE_INFO'; 
    EXECUTE IMMEDIATE('drop index I_DICTIONARY_CHANGE_INFO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DICTIONARY_CHANGE_INFO'; 
    EXECUTE IMMEDIATE('drop table DICTIONARY_CHANGE_INFO cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_DICTIONARY_CHANGE_INFO';
    EXECUTE IMMEDIATE('drop sequence  S_DICTIONARY_CHANGE_INFO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'DICTIONARY_INFORMATIONS'; 
    EXECUTE IMMEDIATE('drop table DICTIONARY_INFORMATIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_EMPLOYEES_DEPARTMENT'; 
    EXECUTE IMMEDIATE('drop index I_EMPLOYEES_DEPARTMENT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_EMPLOYEES_FIO'; 
    EXECUTE IMMEDIATE('drop index I_EMPLOYEES_FIO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'EMPLOYEES'; 
    EXECUTE IMMEDIATE('drop table EMPLOYEES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_EMPLOYEES';
    EXECUTE IMMEDIATE('drop sequence  S_EMPLOYEES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'EXCEPTION_MAPPINGS'; 
    EXECUTE IMMEDIATE('drop table EXCEPTION_MAPPINGS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'EXCEPTION_MAPPINGS_RES'; 
    EXECUTE IMMEDIATE('drop table EXCEPTION_MAPPINGS_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'EXC_MAP_RESTRICTION_UK'; 
    EXECUTE IMMEDIATE('drop index EXC_MAP_RESTRICTION_UK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'EXCEPTION_MAPPING_RESTRICTIONS'; 
    EXECUTE IMMEDIATE('drop table EXCEPTION_MAPPING_RESTRICTIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_FIELD_DESCRIPTIONS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_FIELD_DESCRIPTIONS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_DESCRIPTIONS'; 
    EXECUTE IMMEDIATE('drop table FIELD_DESCRIPTIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_FIELD_DESCRIPTIONS';
    EXECUTE IMMEDIATE('drop sequence  S_FIELD_DESCRIPTIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_REQUISITE_TYPES'; 
    EXECUTE IMMEDIATE('drop table FIELD_REQUISITE_TYPES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_VALIDATORS_DESCR'; 
    EXECUTE IMMEDIATE('drop table FIELD_VALIDATORS_DESCR cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_FIELD_VALIDATORS_DESCR';
    EXECUTE IMMEDIATE('drop sequence  S_FIELD_VALIDATORS_DESCR');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_VALIDATORS_PARAM'; 
    EXECUTE IMMEDIATE('drop table FIELD_VALIDATORS_PARAM cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_FIELD_VALIDATORS_PARAM';
    EXECUTE IMMEDIATE('drop sequence  S_FIELD_VALIDATORS_PARAM');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FIELD_VALUES_DESCR'; 
    EXECUTE IMMEDIATE('drop table FIELD_VALUES_DESCR cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GROUPS_RISK'; 
    EXECUTE IMMEDIATE('drop table GROUPS_RISK cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_GROUPS_RISK';
    EXECUTE IMMEDIATE('drop sequence  S_GROUPS_RISK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'IDENT_TYPE_FOR_DEPS'; 
    EXECUTE IMMEDIATE('drop table IDENT_TYPE_FOR_DEPS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_IDENT_TYPE_FOR_DEPS';
    EXECUTE IMMEDIATE('drop sequence  S_IDENT_TYPE_FOR_DEPS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_IMAGES_MD5'; 
    EXECUTE IMMEDIATE('drop index IDX_IMAGES_MD5');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'IMAGES'; 
    EXECUTE IMMEDIATE('drop table IMAGES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_IMAGES';
    EXECUTE IMMEDIATE('drop sequence  S_IMAGES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_IMAPRODUCT_UUID'; 
    EXECUTE IMMEDIATE('drop index I_IMAPRODUCT_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'IMAPRODUCT'; 
    EXECUTE IMMEDIATE('drop table IMAPRODUCT cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_IMAPRODUCT';
    EXECUTE IMMEDIATE('drop sequence  S_IMAPRODUCT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_INCOG_LAST_DATE'; 
    EXECUTE IMMEDIATE('drop index IDX_INCOG_LAST_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'INCOGNITO_PHONES_JURNAL'; 
    EXECUTE IMMEDIATE('drop table INCOGNITO_PHONES_JURNAL cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_LIMITS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_LIMITS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_START_DATE_1'; 
    EXECUTE IMMEDIATE('drop index INDEX_START_DATE_1');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'LIMITS'; 
    EXECUTE IMMEDIATE('drop table LIMITS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_LIMITS';
    EXECUTE IMMEDIATE('drop sequence  S_LIMITS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_LOGINS_NAME'; 
    EXECUTE IMMEDIATE('drop index I_LOGINS_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'LOGINS'; 
    EXECUTE IMMEDIATE('drop table LOGINS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_LOGINS';
    EXECUTE IMMEDIATE('drop sequence  S_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_LOGIN_BLOCKS'; 
    EXECUTE IMMEDIATE('drop index I_LOGIN_BLOCKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'LOGIN_BLOCK'; 
    EXECUTE IMMEDIATE('drop table LOGIN_BLOCK cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_LOGIN_BLOCK';
    EXECUTE IMMEDIATE('drop sequence  S_LOGIN_BLOCK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_MAIL_SUBJECTS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_MAIL_SUBJECTS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MAIL_SUBJECTS'; 
    EXECUTE IMMEDIATE('drop table MAIL_SUBJECTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MAIL_SUBJECTS';
    EXECUTE IMMEDIATE('drop sequence  S_MAIL_SUBJECTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_PH_JUR_LAST_DATE'; 
    EXECUTE IMMEDIATE('drop index IDX_PH_JUR_LAST_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_PHONE_JURNAL'; 
    EXECUTE IMMEDIATE('drop table MBK_PHONE_JURNAL cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MBK_PHONE_JURNAL';
    EXECUTE IMMEDIATE('drop sequence  S_MBK_PHONE_JURNAL');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_NEWS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_NEWS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_NEWS_DATE'; 
    EXECUTE IMMEDIATE('drop index I_NEWS_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'NEWS'; 
    EXECUTE IMMEDIATE('drop table NEWS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_NEWS';
    EXECUTE IMMEDIATE('drop sequence  S_NEWS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'NEWS_DEPARTMENT'; 
    EXECUTE IMMEDIATE('drop table NEWS_DEPARTMENT cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'NEWS_RES'; 
    EXECUTE IMMEDIATE('drop table NEWS_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'OFFICES_NOT_ISSUING_CARDS'; 
    EXECUTE IMMEDIATE('drop table OFFICES_NOT_ISSUING_CARDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_OFFICES_NOT_ISSUING_CARDS';
    EXECUTE IMMEDIATE('drop sequence  S_OFFICES_NOT_ISSUING_CARDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'OPERATION_CONTEXT'; 
    EXECUTE IMMEDIATE('drop table OPERATION_CONTEXT cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_OPERATION_CONTEXT';
    EXECUTE IMMEDIATE('drop sequence  S_OPERATION_CONTEXT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PANEL_BLOCKS'; 
    EXECUTE IMMEDIATE('drop table PANEL_BLOCKS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PANEL_BLOCKS';
    EXECUTE IMMEDIATE('drop sequence  S_PANEL_BLOCKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PAYMENTS_GROUP_RISK'; 
    EXECUTE IMMEDIATE('drop table PAYMENTS_GROUP_RISK cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PAYMENTS_GROUP_RISK';
    EXECUTE IMMEDIATE('drop sequence  S_PAYMENTS_GROUP_RISK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PAYMENT_SERVICES_CODE'; 
    EXECUTE IMMEDIATE('drop index I_PAYMENT_SERVICES_CODE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PAYMENT_SERVICES'; 
    EXECUTE IMMEDIATE('drop table PAYMENT_SERVICES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PAYMENT_SERVICES';
    EXECUTE IMMEDIATE('drop sequence  S_PAYMENT_SERVICES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PAYMENT_SERVICES_RES'; 
    EXECUTE IMMEDIATE('drop table PAYMENT_SERVICES_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PAYMENT_SERV_PARENTS'; 
    EXECUTE IMMEDIATE('drop table PAYMENT_SERV_PARENTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PAYMENT_SERV_PARENTS';
    EXECUTE IMMEDIATE('drop sequence  S_PAYMENT_SERV_PARENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_AGE_CATEGORIES_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_AGE_CATEGORIES_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_AGE_CATEGORIES'; 
    EXECUTE IMMEDIATE('drop table PFP_AGE_CATEGORIES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_AGE_CATEGORIES';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_AGE_CATEGORIES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_ANSWERS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_ANSWERS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_ANSWERS'; 
    EXECUTE IMMEDIATE('drop table PFP_ANSWERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_ANSWERS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_ANSWERS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_CARDS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_CARDS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_CARDS_DEFAULT_CARD'; 
    EXECUTE IMMEDIATE('drop index I_PFP_CARDS_DEFAULT_CARD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_CARDS_NAME'; 
    EXECUTE IMMEDIATE('drop index I_PFP_CARDS_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CARDS'; 
    EXECUTE IMMEDIATE('drop table PFP_CARDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_CARDS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_CARDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_CARDRECOMMENDATIONS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_CARDRECOMMENDATIONS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CARD_RECOMMENDATIONS'; 
    EXECUTE IMMEDIATE('drop table PFP_CARD_RECOMMENDATIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_CARD_RECOMMENDATIONS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_CARD_RECOMMENDATIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_CHANNELS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_CHANNELS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_CHANNELS_NAME'; 
    EXECUTE IMMEDIATE('drop index I_PFP_CHANNELS_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CHANNELS'; 
    EXECUTE IMMEDIATE('drop table PFP_CHANNELS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_CHANNELS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_CHANNELS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_COMPLEX_PRODUCTS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_COMPLEX_PRODUCTS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_C_PROD_ACCOUNT'; 
    EXECUTE IMMEDIATE('drop index I_PFP_C_PROD_ACCOUNT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_COMPLEX_PRODUCTS'; 
    EXECUTE IMMEDIATE('drop table PFP_COMPLEX_PRODUCTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_COMPLEX_PRODUCTS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_COMPLEX_PRODUCTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CP_TABLE_VIEW_PARAMETERS'; 
    EXECUTE IMMEDIATE('drop table PFP_CP_TABLE_VIEW_PARAMETERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CP_TARGET_GROUPS_BUNDLE'; 
    EXECUTE IMMEDIATE('drop table PFP_CP_TARGET_GROUPS_BUNDLE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CR_CARDS'; 
    EXECUTE IMMEDIATE('drop table PFP_CR_CARDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_CR_STEPS'; 
    EXECUTE IMMEDIATE('drop table PFP_CR_STEPS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_C_FUND_PRODUCTS'; 
    EXECUTE IMMEDIATE('drop table PFP_C_FUND_PRODUCTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_C_IMA_PRODUCTS'; 
    EXECUTE IMMEDIATE('drop table PFP_C_IMA_PRODUCTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_C_INSURANCE_PRODUCTS'; 
    EXECUTE IMMEDIATE('drop table PFP_C_INSURANCE_PRODUCTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_C_PRODUCT_PARAMETERS'; 
    EXECUTE IMMEDIATE('drop table PFP_C_PRODUCT_PARAMETERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_DIAGRAM_STEPS'; 
    EXECUTE IMMEDIATE('drop table PFP_DIAGRAM_STEPS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_INSURANCE_COMPANIES_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_INSURANCE_COMPANIES_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_INSURANCE_COMPANIES_NAME'; 
    EXECUTE IMMEDIATE('drop index I_PFP_INSURANCE_COMPANIES_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INSURANCE_COMPANIES'; 
    EXECUTE IMMEDIATE('drop table PFP_INSURANCE_COMPANIES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_INSURANCE_COMPANIES';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_INSURANCE_COMPANIES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INSURANCE_DATE_PERIODS'; 
    EXECUTE IMMEDIATE('drop table PFP_INSURANCE_DATE_PERIODS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_INSURANCE_DATE_PERIODS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_INSURANCE_DATE_PERIODS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_INS_PERIOD_TYPES_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_INS_PERIOD_TYPES_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_I_P_PERIOD_TYPES_NAME'; 
    EXECUTE IMMEDIATE('drop index I_PFP_I_P_PERIOD_TYPES_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INSURANCE_PERIOD_TYPES'; 
    EXECUTE IMMEDIATE('drop table PFP_INSURANCE_PERIOD_TYPES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_INSURANCE_PERIOD_TYPES';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_INSURANCE_PERIOD_TYPES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_INSURANCE_PRODUCTS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_INSURANCE_PRODUCTS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_PRODUCTS_UNIVERSAL_IP'; 
    EXECUTE IMMEDIATE('drop index I_PFP_PRODUCTS_UNIVERSAL_IP');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_INSURANCE_PRODUCTS_NAME'; 
    EXECUTE IMMEDIATE('drop index I_PFP_INSURANCE_PRODUCTS_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INSURANCE_PRODUCTS'; 
    EXECUTE IMMEDIATE('drop table PFP_INSURANCE_PRODUCTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_INSURANCE_PRODUCTS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_INSURANCE_PRODUCTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_INSURANCE_TYPES_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_INSURANCE_TYPES_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_INSURANCE_TYPES_NAME'; 
    EXECUTE IMMEDIATE('drop index I_PFP_INSURANCE_TYPES_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INSURANCE_TYPES'; 
    EXECUTE IMMEDIATE('drop table PFP_INSURANCE_TYPES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_INSURANCE_TYPES';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_INSURANCE_TYPES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INS_PRODUCT_TO_PERIODS'; 
    EXECUTE IMMEDIATE('drop table PFP_INS_PRODUCT_TO_PERIODS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_INVESTMENT_PERIODS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_INVESTMENT_PERIODS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_INV_PERIODS_PERIOD'; 
    EXECUTE IMMEDIATE('drop index I_PFP_INV_PERIODS_PERIOD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_INVESTMENT_PERIODS'; 
    EXECUTE IMMEDIATE('drop table PFP_INVESTMENT_PERIODS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_INVESTMENT_PERIODS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_INVESTMENT_PERIODS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_IP_TARGET_GROUPS_BUNDLE'; 
    EXECUTE IMMEDIATE('drop table PFP_IP_TARGET_GROUPS_BUNDLE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_LOAN_KINDS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_LOAN_KINDS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_LOAN_KINDS'; 
    EXECUTE IMMEDIATE('drop table PFP_LOAN_KINDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_LOAN_KINDS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_LOAN_KINDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_PENSION_FUND_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_PENSION_FUND_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_PENSION_FUND_NAME'; 
    EXECUTE IMMEDIATE('drop index I_PFP_PENSION_FUND_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PENSION_FUND'; 
    EXECUTE IMMEDIATE('drop table PFP_PENSION_FUND cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_PENSION_FUND';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_PENSION_FUND');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_PENSION_PRODUCT_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_PENSION_PRODUCT_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PENSION_PRODUCT'; 
    EXECUTE IMMEDIATE('drop table PFP_PENSION_PRODUCT cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_PENSION_PRODUCT';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_PENSION_PRODUCT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_PRODUCTS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_PRODUCTS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_PRODUCTS_POINT_P'; 
    EXECUTE IMMEDIATE('drop index I_PFP_PRODUCTS_POINT_P');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_PRODUCTS_UNIVERSAL_P'; 
    EXECUTE IMMEDIATE('drop index I_PFP_PRODUCTS_UNIVERSAL_P');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_PROD_NAME'; 
    EXECUTE IMMEDIATE('drop index I_PFP_PROD_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PRODUCTS'; 
    EXECUTE IMMEDIATE('drop table PFP_PRODUCTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_PRODUCTS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_PRODUCTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PRODUCTS_WEIGHTS'; 
    EXECUTE IMMEDIATE('drop table PFP_PRODUCTS_WEIGHTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_PRODUCTS_WEIGHTS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_PRODUCTS_WEIGHTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PRODUCT_PARAMETERS'; 
    EXECUTE IMMEDIATE('drop table PFP_PRODUCT_PARAMETERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_PRODUCT_TYPES_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_PRODUCT_TYPES_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PRODUCT_TYPE_PARAMETERS'; 
    EXECUTE IMMEDIATE('drop table PFP_PRODUCT_TYPE_PARAMETERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_PRODUCT_TYPE_PARAMETERS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_PRODUCT_TYPE_PARAMETERS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_PT_TARGET_GROUPS'; 
    EXECUTE IMMEDIATE('drop table PFP_PT_TARGET_GROUPS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_P_PRODUCT_TARGET_GROUPS'; 
    EXECUTE IMMEDIATE('drop table PFP_P_PRODUCT_TARGET_GROUPS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_QUESTIONS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_QUESTIONS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_QUESTIONS'; 
    EXECUTE IMMEDIATE('drop table PFP_QUESTIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_QUESTIONS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_QUESTIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_RISKS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_RISKS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_RISK_NAME'; 
    EXECUTE IMMEDIATE('drop index I_PFP_RISK_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_RISKS'; 
    EXECUTE IMMEDIATE('drop table PFP_RISKS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_RISKS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_RISKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_RISK_PROFILES_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_RISK_PROFILES_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_RISK_PROFILES'; 
    EXECUTE IMMEDIATE('drop table PFP_RISK_PROFILES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_RISK_PROFILES';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_RISK_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_SP_TABLE_VIEW_PARAMETERS'; 
    EXECUTE IMMEDIATE('drop table PFP_SP_TABLE_VIEW_PARAMETERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_SP_TARGET_GROUPS_BUNDLE'; 
    EXECUTE IMMEDIATE('drop table PFP_SP_TARGET_GROUPS_BUNDLE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_TABLE_COLUMNS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_TABLE_COLUMNS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_TABLE_COLUMNS'; 
    EXECUTE IMMEDIATE('drop table PFP_TABLE_COLUMNS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_TABLE_COLUMNS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_TABLE_COLUMNS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PFP_TARGETS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PFP_TARGETS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PFP_TARGETS'; 
    EXECUTE IMMEDIATE('drop table PFP_TARGETS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PFP_TARGETS';
    EXECUTE IMMEDIATE('drop sequence  S_PFP_TARGETS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_PROMO_CODES_DEP_CODE'; 
    EXECUTE IMMEDIATE('drop index IDX_PROMO_CODES_DEP_CODE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_PROMO_CODES_DEP_UUID'; 
    EXECUTE IMMEDIATE('drop index IDX_PROMO_CODES_DEP_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROMO_CODES_DEPOSIT'; 
    EXECUTE IMMEDIATE('drop table PROMO_CODES_DEPOSIT cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PROMO_CODES_DEPOSIT';
    EXECUTE IMMEDIATE('drop sequence  S_PROMO_CODES_DEPOSIT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'PROPERTIES_UNIQ'; 
    EXECUTE IMMEDIATE('drop index PROPERTIES_UNIQ');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROPERTIES'; 
    EXECUTE IMMEDIATE('drop table PROPERTIES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PROPERTIES';
    EXECUTE IMMEDIATE('drop sequence  S_PROPERTIES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PROP_SYNC_INFO_GUID'; 
    EXECUTE IMMEDIATE('drop index I_PROP_SYNC_INFO_GUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROPERTY_SYNC_INFO'; 
    EXECUTE IMMEDIATE('drop table PROPERTY_SYNC_INFO cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PROPERTY_SYNC_INFO';
    EXECUTE IMMEDIATE('drop sequence  S_PROPERTY_SYNC_INFO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROVIDER_PROPERTIES'; 
    EXECUTE IMMEDIATE('drop table PROVIDER_PROPERTIES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PROVIDER_PROPERTIES';
    EXECUTE IMMEDIATE('drop sequence  S_PROVIDER_PROPERTIES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROVIDER_REPLICA_TASKS'; 
    EXECUTE IMMEDIATE('drop table PROVIDER_REPLICA_TASKS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PROVIDER_REPLICA_TASKS';
    EXECUTE IMMEDIATE('drop sequence  S_PROVIDER_REPLICA_TASKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_PROVIDER_SMS_ALIAS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_PROVIDER_SMS_ALIAS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'PROVIDER_SMS_ALIAS_NAME'; 
    EXECUTE IMMEDIATE('drop index PROVIDER_SMS_ALIAS_NAME');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROVIDER_SMS_ALIAS'; 
    EXECUTE IMMEDIATE('drop table PROVIDER_SMS_ALIAS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PROVIDER_SMS_ALIAS';
    EXECUTE IMMEDIATE('drop sequence  S_PROVIDER_SMS_ALIAS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROVIDER_SMS_ALIAS_FIELD'; 
    EXECUTE IMMEDIATE('drop table PROVIDER_SMS_ALIAS_FIELD cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PROVIDER_SMS_ALIAS_FIELD';
    EXECUTE IMMEDIATE('drop sequence  S_PROVIDER_SMS_ALIAS_FIELD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'QUICK_PAYMENT_PANELS'; 
    EXECUTE IMMEDIATE('drop table QUICK_PAYMENT_PANELS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_QUICK_PAYMENT_PANELS';
    EXECUTE IMMEDIATE('drop sequence  S_QUICK_PAYMENT_PANELS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'Q_P_PANELS_DEPARTMENTS'; 
    EXECUTE IMMEDIATE('drop table Q_P_PANELS_DEPARTMENTS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_Q_P_PANELS_DEPARTMENTS';
    EXECUTE IMMEDIATE('drop sequence  S_Q_P_PANELS_DEPARTMENTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_DEPFORM'; 
    EXECUTE IMMEDIATE('drop index INDEX_DEPFORM');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'RECEPTIONTIMES'; 
    EXECUTE IMMEDIATE('drop table RECEPTIONTIMES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_RECEPTIONTIMES';
    EXECUTE IMMEDIATE('drop sequence  S_RECEPTIONTIMES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_REGIONS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_REGIONS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_CODE_2'; 
    EXECUTE IMMEDIATE('drop index INDEX_CODE_2');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'REGIONS'; 
    EXECUTE IMMEDIATE('drop table REGIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_REGIONS';
    EXECUTE IMMEDIATE('drop sequence  S_REGIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'REGION_RES'; 
    EXECUTE IMMEDIATE('drop table REGION_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'RUSBANKS'; 
    EXECUTE IMMEDIATE('drop table RUSBANKS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_RUSBANKS';
    EXECUTE IMMEDIATE('drop sequence  S_RUSBANKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'RUSBANKS_RES'; 
    EXECUTE IMMEDIATE('drop table RUSBANKS_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_SP_IS_FASILITATOR'; 
    EXECUTE IMMEDIATE('drop index IDX_SP_IS_FASILITATOR');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_SERVICE_PROVIDERS_UUID'; 
    EXECUTE IMMEDIATE('drop index I_SERVICE_PROVIDERS_UUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IND_ACC_INN'; 
    EXECUTE IMMEDIATE('drop index IND_ACC_INN');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_ALLOWP'; 
    EXECUTE IMMEDIATE('drop index IDX_ALLOWP');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_MB_CODE'; 
    EXECUTE IMMEDIATE('drop index IDX_MB_CODE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_EXT_ID'; 
    EXECUTE IMMEDIATE('drop index INDEX_EXT_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SERV_PROV_PAYM_COUNT'; 
    EXECUTE IMMEDIATE('drop index SERV_PROV_PAYM_COUNT');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDERS'; 
    EXECUTE IMMEDIATE('drop table SERVICE_PROVIDERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_SERVICE_PROVIDERS';
    EXECUTE IMMEDIATE('drop sequence  S_SERVICE_PROVIDERS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDERS_RES'; 
    EXECUTE IMMEDIATE('drop table SERVICE_PROVIDERS_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERVICE_PROVIDER_REGIONS'; 
    EXECUTE IMMEDIATE('drop table SERVICE_PROVIDER_REGIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SERV_PROVIDER_PAYMENT_SERV'; 
    EXECUTE IMMEDIATE('drop table SERV_PROVIDER_PAYMENT_SERV cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SESSIONS'; 
    EXECUTE IMMEDIATE('drop table SESSIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_SESSIONS';
    EXECUTE IMMEDIATE('drop sequence  S_SESSIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UDBO_CL_RU_START_DATE_IDX'; 
    EXECUTE IMMEDIATE('drop index UDBO_CL_RU_START_DATE_IDX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'UDBO_CLAIM_RULES'; 
    EXECUTE IMMEDIATE('drop table UDBO_CLAIM_RULES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_UDBO_CLAIM_RULES';
    EXECUTE IMMEDIATE('drop sequence  S_UDBO_CLAIM_RULES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'WORK_DAYS_INDEX_DAY'; 
    EXECUTE IMMEDIATE('drop index WORK_DAYS_INDEX_DAY');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'WORK_DAYS'; 
    EXECUTE IMMEDIATE('drop table WORK_DAYS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_WORK_DAYS';
    EXECUTE IMMEDIATE('drop sequence  S_WORK_DAYS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MBK_PHONE_JURNAL'; 
    EXECUTE IMMEDIATE('drop sequence S_MBK_PHONE_JURNAL');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

create sequence S_MBK_PHONE_JURNAL
cache 1000
go

/*==============================================================*/
/* Table: ACCESSSCHEMES                                         */
/*==============================================================*/
create table ACCESSSCHEMES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   TYPE                 VARCHAR2(16)         not null,
   FOR_CA               CHAR(1)              not null,
   FOR_VSP              CHAR(1)              not null,
   MAIL_MANAGEMENT      CHAR(1)              not null,
   constraint PK_ACCESSSCHEMES primary key (ID)
)

go

create sequence S_ACCESSSCHEMES
go

comment on column ACCESSSCHEMES.TYPE is
'Тип схемы прав: клиента, сотрудника, администратора.'
go

/*==============================================================*/
/* Index: INDEX_TYPE                                            */
/*==============================================================*/
create index INDEX_TYPE on ACCESSSCHEMES (
   TYPE ASC
)
go

/*==============================================================*/
/* Index: I_ACCESSSCHEMES_MAIL                                  */
/*==============================================================*/
create index I_ACCESSSCHEMES_MAIL on ACCESSSCHEMES (
   decode(MAIL_MANAGEMENT, '1', 1, null) ASC
)
go

/*==============================================================*/
/* Table: ADVERTISINGS                                          */
/*==============================================================*/
create table ADVERTISINGS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   STATE                VARCHAR2(16)         not null,
   NAME                 VARCHAR2(100)        not null,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   TITLE                VARCHAR2(100),
   TEXT                 VARCHAR2(400),
   IMAGE_ID             INTEGER,
   SHOW_TIME            INTEGER              not null,
   ORDER_INDEX          INTEGER              not null,
   AVAILABLE            VARCHAR2(4)          not null,
   constraint PK_ADVERTISINGS primary key (ID)
)

go

create sequence S_ADVERTISINGS
go

/*==============================================================*/
/* Index: I_ADVERTISINGS_UUID                                   */
/*==============================================================*/
create unique index I_ADVERTISINGS_UUID on ADVERTISINGS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: ADVERTISINGS_DEPARTMENTS                              */
/*==============================================================*/
create table ADVERTISINGS_DEPARTMENTS 
(
   ADVERTISING_ID       INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_ADVERTISINGS_DEPARTMENTS primary key (ADVERTISING_ID, TB)
)
go

/*==============================================================*/
/* Table: ADVERTISINGS_RES                                      */
/*==============================================================*/
create table ADVERTISINGS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(10)         not null,
   TITLE                VARCHAR2(100)        not null,
   TEXT                 VARCHAR2(400)        not null,
   constraint PK_ADVERTISINGS_RES primary key (UUID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: ADVERTISING_AREAS                                     */
/*==============================================================*/
create table ADVERTISING_AREAS 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER,
   LIST_INDEX           INTEGER,
   AREA                 VARCHAR2(10)         not null,
   ORDER_INDEX          INTEGER              not null,
   constraint PK_ADVERTISING_AREAS primary key (ID)
)

go

create sequence S_ADVERTISING_AREAS
go

/*==============================================================*/
/* Table: ADVERTISING_BUTTONS                                   */
/*==============================================================*/
create table ADVERTISING_BUTTONS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   ADVERTISING_ID       INTEGER,
   TITLE                VARCHAR2(200),
   URL                  VARCHAR2(256),
   SHOW                 CHAR(1)              not null,
   ORDER_INDEX          INTEGER              not null,
   IMAGE_ID             INTEGER,
   constraint PK_ADVERTISING_BUTTONS primary key (ID)
)

go

create sequence S_ADVERTISING_BUTTONS
go

/*==============================================================*/
/* Index: I_ADVERTISING_BUTTONS_UUID                            */
/*==============================================================*/
create unique index I_ADVERTISING_BUTTONS_UUID on ADVERTISING_BUTTONS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: ADVERTISING_BUTTONS_RES                               */
/*==============================================================*/
create table ADVERTISING_BUTTONS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(10)         not null,
   TITLE                VARCHAR2(100)        not null,
   constraint PK_ADVERTISING_BUTTONS_RES primary key (UUID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: ADVERTISING_REQUIREMENTS                              */
/*==============================================================*/
create table ADVERTISING_REQUIREMENTS 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER,
   REQUIREMENT          VARCHAR2(20)         not null,
   REQUIREMENT_STATE    CHAR(1)              not null,
   constraint PK_ADVERTISING_REQUIREMENTS primary key (ID)
)

go

create sequence S_ADVERTISING_REQUIREMENTS
go

/*==============================================================*/
/* Table: ADVERTISING_REQ_ACC_TYPES                             */
/*==============================================================*/
create table ADVERTISING_REQ_ACC_TYPES 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER,
   REQUIREMENT_STATE    CHAR(1)              not null,
   PRODUCT_ID           INTEGER              not null,
   constraint PK_ADVERTISING_REQ_ACC_TYPES primary key (ID)
)

go

create sequence S_ADVERTISING_REQ_ACC_TYPES
go

/*==============================================================*/
/* Table: ALLOWED_DEPARTMENTS                                   */
/*==============================================================*/
create table ALLOWED_DEPARTMENTS 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   OSB                  VARCHAR2(4)          not null,
   VSP                  VARCHAR2(7)          not null,
   constraint PK_ALLOWED_DEPARTMENTS primary key (ID)
)

go

create sequence S_ALLOWED_DEPARTMENTS
go

/*==============================================================*/
/* Index: I_ALLOWED_DEPARTMENTS                                 */
/*==============================================================*/
create unique index I_ALLOWED_DEPARTMENTS on ALLOWED_DEPARTMENTS (
   LOGIN_ID ASC,
   TB || '|' || OSB || '|' || VSP ASC
)
go

/*==============================================================*/
/* Table: ATTRIBUTES_FOR_IDENT_TYPES                            */
/*==============================================================*/
create table ATTRIBUTES_FOR_IDENT_TYPES 
(
   ID                   INTEGER              not null,
   IDENT_ID             INTEGER              not null,
   SYSTEM_ID            VARCHAR2(20)         not null,
   NAME                 VARCHAR2(100)        not null,
   DATA_TYPE            VARCHAR2(100)        not null,
   REG_EXP              VARCHAR2(100),
   MANDATORY            CHAR(1)              not null,
   UUID                 VARCHAR2(100),
   constraint "PK_ATTRIBUTE_FOR_IDENT_TYPE " primary key (ID)
)

go

create sequence S_ATTRIBUTES_FOR_IDENT_TYPES
go

/*==============================================================*/
/* Table: AUTHENTICATION_TOKEN                                  */
/*==============================================================*/
create table AUTHENTICATION_TOKEN 
(
   ID                   VARCHAR2(32)         not null,
   SESSION_ID           VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            not null,
   STATE                VARCHAR2(6)          not null,
   ACTION               VARCHAR2(256),
   PARAMETERS           CLOB,
   constraint PK_AUTHENTICATION_TOKEN primary key (ID)
)
go

/*==============================================================*/
/* Table: AUTOPAY_SETTINGS                                      */
/*==============================================================*/
create table AUTOPAY_SETTINGS 
(
   ID                   INTEGER              not null,
   TYPE                 VARCHAR2(10)         not null,
   RECIPIENT_ID         INTEGER              not null,
   PARAMETERS           CLOB                 not null,
   constraint PK_AUTOPAY_SETTINGS primary key (ID)
)

go

create sequence S_AUTOPAY_SETTINGS
go

/*==============================================================*/
/* Table: AVATAR_CHANGE_JURNAL                                  */
/*==============================================================*/
create table AVATAR_CHANGE_JURNAL 
(
   PHONE                VARCHAR2(11)         not null,
   LAST_UPDATE_TIME     TIMESTAMP            not null,
   NODE_ID              INTEGER              not null,
   AVATAR_PATH          VARCHAR2(100)
)
partition by range
 (LAST_UPDATE_TIME)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('10-10-2014','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: IDX_AVTR_LAST_DATE                                    */
/*==============================================================*/
create index IDX_AVTR_LAST_DATE on AVATAR_CHANGE_JURNAL (
   LAST_UPDATE_TIME ASC,
   NODE_ID ASC
)
local
go

/*==============================================================*/
/* Table: BANNED_ACCOUNTS                                       */
/*==============================================================*/
create table BANNED_ACCOUNTS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   ACCOUNT_NUMBER       VARCHAR2(20)         not null,
   BICS                 VARCHAR2(500),
   BAN_TYPE             VARCHAR2(5)          not null,
   constraint PK_BANNED_ACCOUNTS primary key (ID),
   constraint AK_UK_BANNED_ACCOUNTS unique (UUID)
)

go

create sequence S_BANNED_ACCOUNTS
go

/*==============================================================*/
/* Index: INDEX_ACCOUNT_NUMBER                                  */
/*==============================================================*/
create index INDEX_ACCOUNT_NUMBER on BANNED_ACCOUNTS (
   ACCOUNT_NUMBER ASC
)
go

/*==============================================================*/
/* Table: BILLINGS                                              */
/*==============================================================*/
create table BILLINGS 
(
   ID                   INTEGER              not null,
   ADAPTER_UUID         VARCHAR2(64)         not null,
   EXTERNAL_ID          VARCHAR2(115)        not null,
   CODE                 VARCHAR(50)          not null,
   NAME                 VARCHAR(128)         not null,
   NEED_UPLOAD_JBT      CHAR(1)              not null,
   TEMPLATE_STATE       VARCHAR2(30),
   constraint PK_BILLINGS primary key (ID),
   constraint UK_BILLINGS_ADAPTER_UUID unique (ADAPTER_UUID)
)

go

create sequence S_BILLINGS
go

/*==============================================================*/
/* Index: I_BILLINGS_CODE                                       */
/*==============================================================*/
create unique index I_BILLINGS_CODE on BILLINGS (
   CODE ASC
)
go

/*==============================================================*/
/* Table: CALENDARS                                             */
/*==============================================================*/
create table CALENDARS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(256)        not null,
   TB                   VARCHAR2(4),
   UUID                 VARCHAR2(32)         not null,
   constraint PK_CALENDARS primary key (ID),
   constraint AK_NAME_CALENDAR unique (NAME)
)

go

create sequence S_CALENDARS
go

/*==============================================================*/
/* Index: I_UUID_CALENDARS                                      */
/*==============================================================*/
create unique index I_UUID_CALENDARS on CALENDARS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: CONTACT_CENTER_AREAS                                  */
/*==============================================================*/
create table CONTACT_CENTER_AREAS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   AREA_NAME            VARCHAR2(50)         not null,
   constraint PK_CONTACT_CENTER_AREAS primary key (ID)
)

go

create sequence S_CONTACT_CENTER_AREAS
go

/*==============================================================*/
/* Index: I_CONTACT_CENTER_AREAS_UUID                           */
/*==============================================================*/
create unique index I_CONTACT_CENTER_AREAS_UUID on CONTACT_CENTER_AREAS (
   UUID ASC
)
go

/*==============================================================*/
/* Index: I_CONTACT_CENTER_AREAS_NAME                           */
/*==============================================================*/
create unique index I_CONTACT_CENTER_AREAS_NAME on CONTACT_CENTER_AREAS (
   AREA_NAME ASC
)
go

/*==============================================================*/
/* Table: CREDIT_OFFER_TEMPLATE                                 */
/*==============================================================*/
create table CREDIT_OFFER_TEMPLATE 
(
   ID                   INTEGER              not null,
   DATE_FROM            TIMESTAMP            not null,
   DATE_TO              TIMESTAMP,
   OFFER                CLOB                 not null,
   UUID                 VARCHAR2(32)         not null,
   TYPE                 VARCHAR2(4)          not null,
   constraint PK_CREDIT_OFFER_TEMPLATE primary key (ID)
)

go

create sequence S_CREDIT_OFFER_TEMPLATE
go

/*==============================================================*/
/* Index: IDX_CREDIT_OFFER_TEMPLATE_UUID                        */
/*==============================================================*/
create unique index IDX_CREDIT_OFFER_TEMPLATE_UUID on CREDIT_OFFER_TEMPLATE (
   UUID ASC
)
go

/*==============================================================*/
/* Table: C_CENTER_AREAS_DEPARTMENTS                            */
/*==============================================================*/
create table C_CENTER_AREAS_DEPARTMENTS 
(
   C_C_AREA_ID          INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_C_CENTER_AREAS_DEPARTMENTS primary key (C_C_AREA_ID, TB)
)

go

create sequence S_C_CENTER_AREAS_DEPARTMENTS
go

/*==============================================================*/
/* Index: I_C_CENTER_AREAS_DEPARTMENTS                          */
/*==============================================================*/
create unique index I_C_CENTER_AREAS_DEPARTMENTS on C_CENTER_AREAS_DEPARTMENTS (
   TB ASC
)
go

/*==============================================================*/
/* Table: DEPARTMENTS                                           */
/*==============================================================*/
create table DEPARTMENTS 
(
   ACTIVE               CHAR(1)              default '1' not null,
   IS_CREDIT_CARD_OFFICE CHAR(1)              default '0' not null,
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(256),
   BRANCH               VARCHAR2(4),
   DEPARTMENT           VARCHAR2(7),
   CITY                 VARCHAR2(256),
   POST_ADDRESS         VARCHAR2(256),
   LOCATION             VARCHAR2(256),
   PHONE                VARCHAR2(50),
   WEEK_OPER_TIME_BEGIN VARCHAR2(10),
   WEEK_OPER_TIME_END   VARCHAR2(10),
   WEEKEND_OPER_TIME_BEGIN VARCHAR2(10),
   WEEKEND_OPER_TIME_END VARCHAR2(10),
   FRIDAY_OPER_TIME_BEGIN VARCHAR2(10),
   FRIDAY_OPER_TIME_END VARCHAR2(10),
   TIME_SCALE           VARCHAR2(30),
   NOTIFY_CONRACT_CANCELATION INTEGER,
   CONNECTION_CHARGE    NUMBER(15,4),
   MONTHLY_CHARGE       NUMBER(15,4),
   RECONNECTION_CHARGE  NUMBER(15,4),
   KIND                 CHAR(1)              not null,
   MAIN                 CHAR(1)              default '0' not null,
   BIC                  VARCHAR2(26),
   TB                   VARCHAR2(4),
   OSB                  VARCHAR2(4),
   OFFICE               VARCHAR2(7),
   OFFICE_ID            VARCHAR2(64),
   SBIDNT               VARCHAR2(4),
   TIME_ZONE            INTEGER,
   SERVICE              CHAR(1),
   ESB_SUPPORTED        CHAR(1)              default '0' not null,
   BILLING_ID           INTEGER,
   ADAPTER_UUID         varchar2(64),
   IS_OPEN_IMA_OFFICE   CHAR(1)              default '0' not null,
   SEND_SMS_METHOD      VARCHAR2(16),
   AUTOMATION_TYPE      VARCHAR2(15),
   POSSIBLE_LOANS_OPERATION CHAR(1)              default '0' not null,
   constraint PK_DEPARTMENTS primary key (ID),
   constraint OFFICE_UNIQUE unique (OFFICE_ID)
)

go

create sequence S_DEPARTMENTS
go

comment on column DEPARTMENTS.TIME_SCALE is
'Шкала времени'
go

comment on column DEPARTMENTS.NOTIFY_CONRACT_CANCELATION is
'Уведомлять о расторжении договора за Y дней'
go

/*==============================================================*/
/* Index: LIST_VSP_INDEX_DEPS                                   */
/*==============================================================*/
create index LIST_VSP_INDEX_DEPS on DEPARTMENTS (
   NAME ASC,
   POST_ADDRESS ASC,
   IS_CREDIT_CARD_OFFICE ASC
)
go

/*==============================================================*/
/* Index: I_DEPARTMENTS_BANK_INFO                               */
/*==============================================================*/
create unique index I_DEPARTMENTS_BANK_INFO on DEPARTMENTS (
   TB ASC,
   nvl(OSB, 'NULL') ASC,
   nvl(OFFICE, 'NULL') ASC,
   ID ASC
)
go

/*==============================================================*/
/* Index: DEPARTMENTS_INDEX_OFFICE                              */
/*==============================================================*/
create index DEPARTMENTS_INDEX_OFFICE on DEPARTMENTS (
   (TB || '|' || OSB || '|' || OFFICE) ASC
)
go

/*==============================================================*/
/* Index: I_DEPARTMENTS_TBS                                     */
/*==============================================================*/
create index I_DEPARTMENTS_TBS on DEPARTMENTS (
   DECODE(OFFICE||OSB,NULL,TB,NULL) ASC
)
go

/*==============================================================*/
/* Table: DEPARTMENTS_RECORDING                                 */
/*==============================================================*/
create table DEPARTMENTS_RECORDING 
(
   ID                   INTEGER              not null,
   TB_ERIB              VARCHAR2(3)          not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)          not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   DESPATCH             VARCHAR2(11),
   DATE_SUC             TIMESTAMP,
   constraint PK_DEPARTMENTS_RECORDING primary key (ID)
)

go

create sequence S_DEPARTMENTS_RECORDING
go

/*==============================================================*/
/* Table: DEPARTMENTS_RECORDING_TMP                             */
/*==============================================================*/
create table DEPARTMENTS_RECORDING_TMP 
(
   ID                   INTEGER              not null,
   TB_ERIB              VARCHAR2(3)          not null,
   OSB_ERIB             VARCHAR2(4),
   OFFICE_ERIB          VARCHAR2(7),
   TB_SPOOBK2           VARCHAR2(3)          not null,
   OSB_SPOOBK2          VARCHAR2(4),
   OFFICE_SPOOBK2       VARCHAR2(7),
   DESPATCH             VARCHAR2(11),
   DATE_SUC             TIMESTAMP,
   constraint PK_DEPARTMENTS_RECORDING_TMP primary key (ID)
)

go

create sequence S_DEPARTMENTS_RECORDING_TMP
go

/*==============================================================*/
/* Table: DEPARTMENTS_REPLICA_TASKS                             */
/*==============================================================*/
create table DEPARTMENTS_REPLICA_TASKS 
(
   ID                   INTEGER              not null,
   OWNER_ID             INTEGER              not null,
   OWNER_FIO            VARCHAR2(256)        not null,
   CREATION_DATE        TIMESTAMP            not null,
   STATE_CODE           VARCHAR2(20)         not null,
   REPLICATION_MODE     VARCHAR2(10)         not null,
   REPORT_START_DATE    TIMESTAMP,
   REPORT_END_DATE      TIMESTAMP,
   DEPARTMENTS          VARCHAR2(3000),
   DETAILED_REPORT      CLOB,
   SOURCE_ERRORS        NUMBER(22,0),
   SOURCE_SUCCESS       NUMBER(22,0),
   DEST_INSERED         NUMBER(22,0),
   DEST_UPDATED         NUMBER(22,0),
   TOTAL_RECORDS        NUMBER(22,0),
   DEST_INSERED_REPORT  CLOB,
   DEST_INSERED_DECENTR_REPORT CLOB,
   DEST_UPDATED_REPORT  CLOB,
   ERROR_FORMAT_REPORT  CLOB,
   ERROR_PARENT_REPORT  CLOB,
   constraint PK_DEPARTMENTS_REPLICA_TASKS primary key (ID)
)

go

create sequence S_DEPARTMENTS_REPLICA_TASKS
go

/*==============================================================*/
/* Table: DEPARTMENTS_TASKS_CONTENT                             */
/*==============================================================*/
create table DEPARTMENTS_TASKS_CONTENT 
(
   REPLICA_TASKS_ID     INTEGER              not null,
   CONTENT              BLOB,
   constraint PK_DEPARTMENTS_TASKS_CONTENT primary key (REPLICA_TASKS_ID)
)
go

/*==============================================================*/
/* Table: DEPOSITDESCRIPTIONS                                   */
/*==============================================================*/
create table DEPOSITDESCRIPTIONS 
(
   MINIMUM_BALANCE      CHAR(1 BYTE)         default '0' not null,
   CAPITALIZATION       CHAR(1 BYTE)         default '0' not null,
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(256)        not null,
   DESCRIPTION          CLOB                 not null,
   DETAILS              CLOB,
   DEPARTMENT_ID        INTEGER,
   KIND                 CHAR(1)              not null,
   PRODUCT_ID           INTEGER              not null,
   AVAILABLE_ONLINE     CHAR(1)              default '0' not null,
   LAST_UPDATE_DATE     TIMESTAMP            not null,
   constraint PK_DEPOSITDESCRIPTIONS primary key (ID)
)

go

create sequence S_DEPOSITDESCRIPTIONS
go

/*==============================================================*/
/* Index: UK_KEY                                                */
/*==============================================================*/
create unique index UK_KEY on DEPOSITDESCRIPTIONS (
   NAME ASC,
   DEPARTMENT_ID ASC
)
go

/*==============================================================*/
/* Index: UK_P_ID                                               */
/*==============================================================*/
create unique index UK_P_ID on DEPOSITDESCRIPTIONS (
   PRODUCT_ID ASC
)
go

/*==============================================================*/
/* Table: DEPOSITGLOBALS                                        */
/*==============================================================*/
create table DEPOSITGLOBALS 
(
   ROW_KEY              VARCHAR2(20)         not null,
   LIST_TRANSFORMATION  CLOB                 not null,
   CALCULATOR_TRANSFORMATION CLOB,
   ADMIN_LIST_TRANSFORMATION CLOB,
   ADMIN_EDIT_TRANSFORMATION CLOB,
   DEFAULT_DETAILS_TRANSFORMATION CLOB                 not null,
   MOBILE_DETAILS_TRANSFORMATION CLOB,
   MOBILE_LIST_TRANSFORMATION CLOB,
   VISIBILITY_TRANSFORMATION CLOB,
   PERCENT_RATES_TRANSFORMATION CLOB,
   constraint PK_DEPOSITGLOBALS primary key (ROW_KEY)
)
go

/*==============================================================*/
/* Table: DEPOSIT_DEPARTMENTS                                   */
/*==============================================================*/
create table DEPOSIT_DEPARTMENTS 
(
   DEPOSIT_ID           INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_DEPOSIT_DEPARTMENTS primary key (DEPOSIT_ID, TB)
)
go

/*==============================================================*/
/* Table: DICTIONARY_CHANGE_INFO                                */
/*==============================================================*/
create table DICTIONARY_CHANGE_INFO 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(128)        not null,
   DICTIONARY_TYPE      VARCHAR2(150)        not null,
   UPDATE_DATE          TIMESTAMP            default SYSDATE not null,
   CHANGE_TYPE          VARCHAR2(6)          not null,
   ENTITY_DATA          CLOB
)
partition by range
 (UPDATE_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_FIRST
        values less than (to_date('1-1-2014','DD-MM-YYYY'))
         compress)
go

create sequence S_DICTIONARY_CHANGE_INFO
go

/*==============================================================*/
/* Index: I_DICTIONARY_CHANGE_INFO                              */
/*==============================================================*/
create index I_DICTIONARY_CHANGE_INFO on DICTIONARY_CHANGE_INFO (
   id ASC
)
local
go

/*==============================================================*/
/* Table: DICTIONARY_INFORMATIONS                               */
/*==============================================================*/
create table DICTIONARY_INFORMATIONS 
(
   NODE_ID              INTEGER              not null,
   LAST_UPDATE_DATE     TIMESTAMP,
   STATE                VARCHAR2(9)          not null,
   ERROR_DETAIL         CLOB,
   constraint PK_DICTIONARY_INFORMATIONS primary key (NODE_ID)
)
go

/*==============================================================*/
/* Table: EMPLOYEES                                             */
/*==============================================================*/
create table EMPLOYEES 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   FIRST_NAME           VARCHAR2(30)         not null,
   SUR_NAME             VARCHAR2(30)         not null,
   PATR_NAME            VARCHAR2(30),
   INFO                 VARCHAR2(512),
   E_MAIL               VARCHAR2(40),
   MOBILE_PHONE         VARCHAR2(20),
   CA_ADMIN             CHAR(1)              not null,
   VSP_EMPLOYEE         CHAR(1)              not null,
   MANAGER_ID           VARCHAR2(14),
   MANAGER_PHONE        VARCHAR2(20),
   MANAGER_EMAIL        VARCHAR2(40),
   MANAGER_LEAD_EMAIL   VARCHAR2(40),
   MANAGER_CHANNEL      VARCHAR2(100),
   TB                   VARCHAR2(4)          not null,
   OSB                  VARCHAR2(4),
   VSP                  VARCHAR2(7),
   SUDIR_LOGIN          VARCHAR2(100),
   constraint PK_EMPLOYEES primary key (ID),
   constraint AK_MANAGER_ID_UNIQUE unique (MANAGER_ID)
)

go

create sequence S_EMPLOYEES
go

/*==============================================================*/
/* Index: I_EMPLOYEES_FIO                                       */
/*==============================================================*/
create index I_EMPLOYEES_FIO on EMPLOYEES (
   upper(replace(replace(concat(concat(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC
)
go

/*==============================================================*/
/* Index: I_EMPLOYEES_DEPARTMENT                                */
/*==============================================================*/
create index I_EMPLOYEES_DEPARTMENT on EMPLOYEES (
   TB ASC,
   nvl(OSB, 'NULL') ASC,
   nvl(VSP, 'NULL') ASC
)
go

/*==============================================================*/
/* Table: EXCEPTION_MAPPINGS                                    */
/*==============================================================*/
create table EXCEPTION_MAPPINGS 
(
   HASH                 VARCHAR2(277)        not null,
   GROUP_ID             INTEGER              not null,
   MESSAGE              VARCHAR2(2000)       not null,
   constraint PK_EXCEPTION_MAPPINGS primary key (HASH, GROUP_ID)
)
go

/*==============================================================*/
/* Table: EXCEPTION_MAPPINGS_RES                                */
/*==============================================================*/
create table EXCEPTION_MAPPINGS_RES 
(
   HASH                 varchar2(277)        not null,
   GROUP_ID             INTEGER              not null,
   LOCALE_ID            varchar2(30)         not null,
   MESSAGE              VARCHAR2(2000)       not null,
   constraint PK_EXCEPTION_MAPPINGS_RES primary key (HASH, GROUP_ID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: EXCEPTION_MAPPING_RESTRICTIONS                        */
/*==============================================================*/
create table EXCEPTION_MAPPING_RESTRICTIONS 
(
   HASH                 VARCHAR2(277)        not null,
   GROUP_ID             INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   TB                   VARCHAR2(4)          not null
)
go

/*==============================================================*/
/* Index: EXC_MAP_RESTRICTION_UK                                */
/*==============================================================*/
create unique index EXC_MAP_RESTRICTION_UK on EXCEPTION_MAPPING_RESTRICTIONS (
   HASH ASC,
   APPLICATION ASC,
   TB ASC
)
go

/*==============================================================*/
/* Table: FIELD_DESCRIPTIONS                                    */
/*==============================================================*/
create table FIELD_DESCRIPTIONS 
(
   ID                   INTEGER              not null,
   EXTERNAL_ID          VARCHAR2(40)         not null,
   NAME                 NVARCHAR2(60)        not null,
   DESCRIPTION          NVARCHAR2(200),
   HINT                 NVARCHAR2(200),
   TYPE                 VARCHAR2(20),
   MAX_LENGTH           INTEGER,
   MIN_LENGTH           INTEGER,
   NUMBER_PRECISION     INTEGER,
   IS_REQUIRED          CHAR(1)              not null,
   IS_EDITABLE          CHAR(1)              not null,
   IS_VISIBLE           CHAR(1)              not null,
   IS_SUM               CHAR(1)              not null,
   IS_KEY               CHAR(1)              not null,
   IS_INCLUDE_IN_SMS    CHAR(1)              not null,
   IS_SAVE_IN_TEMPLATE  CHAR(1)              not null,
   IS_FOR_BILL          CHAR(1)              not null,
   IS_HIDE_IN_CONFIRMATION CHAR(1)              not null,
   INITIAL_VALUE        NVARCHAR2(1024),
   RECIPIENT_ID         INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   MASK                 VARCHAR2(1024),
   BUSINESS_SUB_TYPE    VARCHAR2(10),
   EXTENDED_DESC_DATA_ID VARCHAR2(50),
   UUID                 VARCHAR2(32)         not null,
   constraint PK_FIELD_DESCRIPTIONS primary key (ID)
)

go

create sequence S_FIELD_DESCRIPTIONS
go

/*==============================================================*/
/* Index: I_FIELD_DESCRIPTIONS_UUID                             */
/*==============================================================*/
create unique index I_FIELD_DESCRIPTIONS_UUID on FIELD_DESCRIPTIONS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: FIELD_REQUISITE_TYPES                                 */
/*==============================================================*/
create table FIELD_REQUISITE_TYPES 
(
   FIELD_ID             INTEGER              not null,
   REQUISITE_TYPE       VARCHAR2(32)         not null,
   LIST_INDEX           INTEGER              not null,
   constraint PK_FIELD_REQUISITE_TYPES primary key (FIELD_ID, LIST_INDEX)
)
go

/*==============================================================*/
/* Table: FIELD_VALIDATORS_DESCR                                */
/*==============================================================*/
create table FIELD_VALIDATORS_DESCR 
(
   ID                   INTEGER              not null,
   FIELD_ID             INTEGER              not null,
   TYPE                 VARCHAR2(50)         not null,
   MESSAGE              NVARCHAR2(500)       not null,
   LIST_INDEX           INTEGER              not null,
   constraint PK_FIELD_VALIDATORS_DESCR primary key (ID)
)

go

create sequence S_FIELD_VALIDATORS_DESCR
go

/*==============================================================*/
/* Table: FIELD_VALIDATORS_PARAM                                */
/*==============================================================*/
create table FIELD_VALIDATORS_PARAM 
(
   ID                   INTEGER              not null,
   FIELD_ID             INTEGER              not null,
   NAME                 VARCHAR2(64)         not null,
   TYPE                 VARCHAR2(64)         not null,
   VALUE                VARCHAR2(1024)       not null,
   constraint PK_FIELD_VALIDATORS_PARAM primary key (ID)
)

go

create sequence S_FIELD_VALIDATORS_PARAM
go

/*==============================================================*/
/* Table: FIELD_VALUES_DESCR                                    */
/*==============================================================*/
create table FIELD_VALUES_DESCR 
(
   FIELD_ID             INTEGER              not null,
   VALUE                NVARCHAR2(128)       not null,
   LIST_INDEX           INTEGER              not null,
   constraint PK_FIELD_VALUES_DESCR primary key (FIELD_ID, LIST_INDEX)
)
go

/*==============================================================*/
/* Table: GROUPS_RISK                                           */
/*==============================================================*/
create table GROUPS_RISK 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   IS_DEFAULT           CHAR(1)              not null,
   RANK                 VARCHAR2(5)          default 'HIGH' not null,
   EXTERNAL_ID          VARCHAR2(35)         not null,
   constraint PK_GROUPS_RISK primary key (ID),
   constraint UNIQUE_NAME unique (NAME)
)

go

create sequence S_GROUPS_RISK
go

/*==============================================================*/
/* Table: IDENT_TYPE_FOR_DEPS                                   */
/*==============================================================*/
create table IDENT_TYPE_FOR_DEPS 
(
   ID                   INTEGER              not null,
   SYSTEM_ID            VARCHAR2(20)         not null,
   NAME                 VARCHAR2(100)        not null,
   UUID                 VARCHAR2(100),
   constraint "PK_IDENT_TYPE_FOR_DEPS " primary key (ID)
)

go

create sequence S_IDENT_TYPE_FOR_DEPS
go

/*==============================================================*/
/* Table: IMAGES                                                */
/*==============================================================*/
create table IMAGES 
(
   ID                   INTEGER              not null,
   IMAGE                BLOB,
   IMAGE_HELP           VARCHAR2(100),
   EXTEND_IMAGE         VARCHAR2(250),
   UPDATE_TIME          TIMESTAMP            not null,
   LINK_URL             VARCHAR2(512),
   NAME                 VARCHAR2(256),
   INNER_IMAGE          VARCHAR2(250),
   MD5                  VARCHAR(32),
   constraint PK_IMAGES primary key (ID)
)

go

create sequence S_IMAGES
go

/*==============================================================*/
/* Index: IDX_IMAGES_MD5                                        */
/*==============================================================*/
create index IDX_IMAGES_MD5 on IMAGES (
   MD5 ASC
)
go

/*==============================================================*/
/* Table: IMAPRODUCT                                            */
/*==============================================================*/
create table IMAPRODUCT 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   NAME                 VARCHAR2(255)        not null,
   TYPE                 INTEGER              not null,
   SUBTYPE              INTEGER              not null,
   CURRENCY             CHAR(3)              not null,
   CONTRACT_TEMPLATE    CLOB                 not null,
   constraint PK_IMAPRODUCT primary key (ID)
)

go

create sequence S_IMAPRODUCT
go

comment on table IMAPRODUCT is
'ОМС, загруженные из справочника ЦАС НСИ'
go

/*==============================================================*/
/* Index: I_IMAPRODUCT_UUID                                     */
/*==============================================================*/
create unique index I_IMAPRODUCT_UUID on IMAPRODUCT (
   UUID ASC
)
go

/*==============================================================*/
/* Table: INCOGNITO_PHONES_JURNAL                               */
/*==============================================================*/
create table INCOGNITO_PHONES_JURNAL 
(
   PHONE                VARCHAR2(11)         not null,
   LAST_UPDATE_TIME     INTEGER,
   ACTIVE               CHAR(1)              not null,
   NODE_ID              INTEGER              not null
)
partition by range
 (LAST_UPDATE_TIME)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('10-10-2014','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: IDX_INCOG_LAST_DATE                                   */
/*==============================================================*/
create index IDX_INCOG_LAST_DATE on INCOGNITO_PHONES_JURNAL (
   LAST_UPDATE_TIME ASC,
   NODE_ID ASC
)
local
go

/*==============================================================*/
/* Table: LIMITS                                                */
/*==============================================================*/
create table LIMITS 
(
   ID                   INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   CREATION_DATE        TIMESTAMP            not null,
   START_DATE           TIMESTAMP            not null,
   AMOUNT               NUMBER(15,4),
   CURRENCY             CHAR(3),
   GROUP_RISK_ID        INTEGER,
   LIMIT_TYPE           VARCHAR2(33)         not null,
   OPERATION_TYPE       VARCHAR2(30)         not null,
   RESTRICTION_TYPE     VARCHAR2(30)         not null,
   OPERATION_COUNT      INTEGER,
   CHANNEL_TYPE         VARCHAR2(20)         not null,
   STATE                VARCHAR2(10)         not null,
   SECURITY_TYPE        VARCHAR2(8),
   UUID                 VARCHAR2(32)         not null,
   END_DATE             TIMESTAMP,
   constraint PK_LIMITS primary key (ID)
)

go

create sequence S_LIMITS
go

/*==============================================================*/
/* Index: INDEX_START_DATE_1                                    */
/*==============================================================*/
create index INDEX_START_DATE_1 on LIMITS (
   START_DATE ASC
)
go

/*==============================================================*/
/* Index: I_LIMITS_UUID                                         */
/*==============================================================*/
create unique index I_LIMITS_UUID on LIMITS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: LOGINS                                                */
/*==============================================================*/
create table LOGINS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   PASSWORD             VARCHAR2(128)        not null,
   ACCESSSCHEME_ID      INTEGER,
   NODE_EXT_ID          INTEGER,
   LAST_UPDATE_DATE     TIMESTAMP            default SYSDATE not null,
   DELETED              CHAR(1)              not null,
   WRONG_LOGONS         INTEGER              default 0 not null,
   PASSWORD_EXPIRE_DATE DATE                 not null,
   LAST_LOGON_DATE      DATE,
   constraint PK_LOGINS primary key (ID)
)

go

create sequence S_LOGINS
go

/*==============================================================*/
/* Index: I_LOGINS_NAME                                         */
/*==============================================================*/
create unique index I_LOGINS_NAME on LOGINS (
   NAME ASC
)
go

/*==============================================================*/
/* Table: LOGIN_BLOCK                                           */
/*==============================================================*/
create table LOGIN_BLOCK 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   TYPE                 VARCHAR2(16)         not null,
   REASON               VARCHAR2(1024),
   DATE_FROM            TIMESTAMP            not null,
   DATE_UNTIL           TIMESTAMP,
   BLOCKER_ID           INTEGER,
   constraint PK_LOGIN_BLOCK primary key (ID)
)

go

create sequence S_LOGIN_BLOCK
go

comment on column LOGIN_BLOCK.TYPE is
'Тип блокировка: системная или сотрудником.
Системная - сотрудник несколько раз некорректно ввел пароль.
Сотрудником - сотрудник заблокировани другим сотрудником.'
go

/*==============================================================*/
/* Index: I_LOGIN_BLOCKS                                        */
/*==============================================================*/
create index I_LOGIN_BLOCKS on LOGIN_BLOCK (
   LOGIN_ID ASC,
   DATE_FROM ASC,
   DATE_UNTIL ASC
)
go

/*==============================================================*/
/* Table: MAIL_SUBJECTS                                         */
/*==============================================================*/
create table MAIL_SUBJECTS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   DESCRIPTION          VARCHAR2(50)         not null,
   constraint PK_MAIL_SUBJECTS primary key (ID)
)

go

create sequence S_MAIL_SUBJECTS
go

/*==============================================================*/
/* Index: I_MAIL_SUBJECTS_UUID                                  */
/*==============================================================*/
create unique index I_MAIL_SUBJECTS_UUID on MAIL_SUBJECTS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: MBK_PHONE_JURNAL                                      */
/*==============================================================*/
create table MBK_PHONE_JURNAL 
(
   ID                   INTEGER              not null,
   PHONE                VARCHAR2(11)         not null,
   ADDED                CHAR(1)              not null,
   LAST_UPDATE_TIME     TIMESTAMP            not null
)
partition by range
 (LAST_UPDATE_TIME)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-02-2015','DD-MM-YYYY')))
go

create sequence S_MBK_PHONE_JURNAL
go

/*==============================================================*/
/* Index: IDX_PH_JUR_LAST_DATE                                  */
/*==============================================================*/
create index IDX_PH_JUR_LAST_DATE on MBK_PHONE_JURNAL (
   ID ASC
)
local
go

/*==============================================================*/
/* Table: NEWS                                                  */
/*==============================================================*/
create table NEWS 
(
   ID                   INTEGER              not null,
   NEWS_DATE            TIMESTAMP            not null,
   TITLE                VARCHAR2(100)        not null,
   TEXT                 CLOB                 not null,
   IMPORTANT            VARCHAR2(10)         not null,
   SHORT_TEXT           VARCHAR2(150)        not null,
   TYPE                 VARCHAR2(25),
   START_PUBLISH_DATE   TIMESTAMP,
   END_PUBLISH_DATE     TIMESTAMP,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_NEWS primary key (ID)
)

go

create sequence S_NEWS
go

/*==============================================================*/
/* Index: I_NEWS_DATE                                           */
/*==============================================================*/
create index I_NEWS_DATE on NEWS (
   START_PUBLISH_DATE ASC,
   END_PUBLISH_DATE ASC
)
go

/*==============================================================*/
/* Index: I_NEWS_UUID                                           */
/*==============================================================*/
create unique index I_NEWS_UUID on NEWS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: NEWS_DEPARTMENT                                       */
/*==============================================================*/
create table NEWS_DEPARTMENT 
(
   NEWS_ID              INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_NEWS_DEPARTMENT primary key (NEWS_ID, TB)
)
go

/*==============================================================*/
/* Table: NEWS_RES                                              */
/*==============================================================*/
create table NEWS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   TITLE                VARCHAR2(100)        not null,
   SHORT_TEXT           VARCHAR2(150)        not null,
   TEXT                 CLOB                 not null,
   constraint PK_NEWS_RES primary key (UUID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: OFFICES_NOT_ISSUING_CARDS                             */
/*==============================================================*/
create table OFFICES_NOT_ISSUING_CARDS 
(
   ID                   INTEGER              not null,
   TB                   VARCHAR2(3)          not null,
   OSB                  VARCHAR2(4),
   OFFICE               VARCHAR2(7),
   constraint PK_OFFICES_NOT_ISSUING_CARDS primary key (ID)
)

go

create sequence S_OFFICES_NOT_ISSUING_CARDS
go

/*==============================================================*/
/* Table: OPERATION_CONTEXT                                     */
/*==============================================================*/
create table OPERATION_CONTEXT 
(
   ID                   INTEGER              not null,
   SESSION_ID           VARCHAR2(32)         not null,
   STATE                VARCHAR2(6)          not null,
   CONTEXT              CLOB                 not null,
   constraint PK_OPERATION_CONTEXT primary key (ID)
)

go

create sequence S_OPERATION_CONTEXT
go

/*==============================================================*/
/* Table: PANEL_BLOCKS                                          */
/*==============================================================*/
create table PANEL_BLOCKS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   Q_P_PANEL_ID         INTEGER,
   SHOW                 CHAR(1)              not null,
   ORDER_INDEX          INTEGER              not null,
   PROVIDER_NAME        varchar2(14),
   FIELD_SUMM_NAME      varchar2(40),
   SUMM                 NUMBER(15,4),
   FIELD_NAME           varchar2(40),
   IMAGE_ID             INTEGER,
   SHOW_PROVIDER_NAME   CHAR(1)              not null,
   PROVIDER_ID          INTEGER              not null,
   constraint PK_PANEL_BLOCKS primary key (ID),
   constraint AK_UNIQUE_PANEL_BLOCKS unique (UUID)
)

go

create sequence S_PANEL_BLOCKS
go

/*==============================================================*/
/* Table: PAYMENTS_GROUP_RISK                                   */
/*==============================================================*/
create table PAYMENTS_GROUP_RISK 
(
   ID                   INTEGER              not null,
   KIND                 VARCHAR2(3)          not null,
   GROUP_RISK_ID        INTEGER,
   TB                   VARCHAR2(4)          not null,
   constraint PK_PAYMENTS_GROUP_RISK primary key (ID)
)

go

create sequence S_PAYMENTS_GROUP_RISK
go

/*==============================================================*/
/* Table: PAYMENT_SERVICES                                      */
/*==============================================================*/
create table PAYMENT_SERVICES 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(50)         not null,
   NAME                 NVARCHAR2(128)       not null,
   PARENT_ID            INTEGER,
   IMAGE_ID             INTEGER,
   POPULAR              CHAR(1)              not null,
   DESCRIPTION          VARCHAR2(512),
   SYSTEM               CHAR(1)              not null,
   PRIORITY             INTEGER,
   VISIBLE_IN_SYSTEM    CHAR(1)              not null,
   IMAGE_NAME           VARCHAR2(128)        not null,
   IS_CATEGORY          CHAR(1)              not null,
   SHOW_IN_SYSTEM       CHAR(1)              not null,
   SHOW_IN_API          CHAR(1)              not null,
   SHOW_IN_ATM          CHAR(1)              not null,
   SHOW_IN_SOCIAL       CHAR(1)              not null,
   constraint PK_PAYMENT_SERVICES primary key (ID)
)

go

create sequence S_PAYMENT_SERVICES
go

/*==============================================================*/
/* Index: I_PAYMENT_SERVICES_CODE                               */
/*==============================================================*/
create unique index I_PAYMENT_SERVICES_CODE on PAYMENT_SERVICES (
   CODE ASC
)
go

/*==============================================================*/
/* Table: PAYMENT_SERVICES_RES                                  */
/*==============================================================*/
create table PAYMENT_SERVICES_RES 
(
   UUID                 VARCHAR2(50)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(128)        not null,
   DESCRIPTION          VARCHAR2(512),
   constraint PK_PAYMENT_SERVICES_RES primary key (UUID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: PAYMENT_SERV_PARENTS                                  */
/*==============================================================*/
create table PAYMENT_SERV_PARENTS 
(
   SERVICE_ID           INTEGER              not null,
   PARENT_ID            INTEGER              not null,
   constraint PK_PAYMENT_SERV_PARENTS primary key (SERVICE_ID, PARENT_ID)
)

go

create sequence S_PAYMENT_SERV_PARENTS
go

/*==============================================================*/
/* Table: PFP_AGE_CATEGORIES                                    */
/*==============================================================*/
create table PFP_AGE_CATEGORIES 
(
   ID                   INTEGER              not null,
   MIN_AGE              INTEGER              not null,
   MAX_AGE              INTEGER,
   WEIGHT               INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_AGE_CATEGORIES primary key (ID)
)

go

create sequence S_PFP_AGE_CATEGORIES
go

/*==============================================================*/
/* Index: I_PFP_AGE_CATEGORIES_UUID                             */
/*==============================================================*/
create unique index I_PFP_AGE_CATEGORIES_UUID on PFP_AGE_CATEGORIES (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_ANSWERS                                           */
/*==============================================================*/
create table PFP_ANSWERS 
(
   ID                   INTEGER              not null,
   QUESTION_ID          INTEGER,
   TEXT                 VARCHAR2(250),
   WEIGHT               NUMBER(2)            not null,
   LIST_INDEX           INTEGER,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_ANSWERS primary key (ID)
)

go

create sequence S_PFP_ANSWERS
go

/*==============================================================*/
/* Index: I_PFP_ANSWERS_UUID                                    */
/*==============================================================*/
create unique index I_PFP_ANSWERS_UUID on PFP_ANSWERS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_CARDS                                             */
/*==============================================================*/
create table PFP_CARDS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(80)         not null,
   PROGRAMM_TYPE        VARCHAR2(50)         not null,
   INPUTS               NUMBER,
   BONUS                NUMBER,
   CLAUSE               VARCHAR2(250),
   CARD_ICON_ID         INTEGER              not null,
   PROGRAMM_ICON_ID     INTEGER,
   DESCRIPTION          VARCHAR2(170)        not null,
   RECOMMENDATION       VARCHAR2(500),
   DIAGRAM_USE_IMAGE    CHAR(1)              not null,
   DIAGRAM_COLOR        VARCHAR2(16),
   DIAGRAM_ICON_ID      INTEGER,
   DIAGRAM_USE_NET      CHAR(1)              not null,
   DEFAULT_CARD         CHAR(1)              default '0',
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_CARDS primary key (ID)
)

go

create sequence S_PFP_CARDS
go

/*==============================================================*/
/* Index: I_PFP_CARDS_NAME                                      */
/*==============================================================*/
create unique index I_PFP_CARDS_NAME on PFP_CARDS (
   NAME ASC
)
go

/*==============================================================*/
/* Index: I_PFP_CARDS_DEFAULT_CARD                              */
/*==============================================================*/
create unique index I_PFP_CARDS_DEFAULT_CARD on PFP_CARDS (
    decode(DEFAULT_CARD, '0', null, DEFAULT_CARD) ASC
)
go

/*==============================================================*/
/* Index: I_PFP_CARDS_UUID                                      */
/*==============================================================*/
create unique index I_PFP_CARDS_UUID on PFP_CARDS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_CARD_RECOMMENDATIONS                              */
/*==============================================================*/
create table PFP_CARD_RECOMMENDATIONS 
(
   ID                   INTEGER              not null,
   RECOMMENDATION       VARCHAR2(150),
   ACCOUNT_FROM_INCOME  NUMBER(7,2),
   ACCOUNT_TO_INCOME    NUMBER(7,2),
   ACCOUNT_DEFAULT_INCOME NUMBER(7,2)          not null,
   ACCOUNT_DESCRIPTION  VARCHAR2(150)        not null,
   THANKS_FROM_INCOME   NUMBER(7,2),
   THANKS_TO_INCOME     NUMBER(7,2),
   THANKS_DEFAULT_INCOME NUMBER(7,2)          not null,
   THANKS_DESCRIPTION   VARCHAR2(150)        not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_CARD_RECOMMENDATIONS primary key (ID)
)

go

create sequence S_PFP_CARD_RECOMMENDATIONS
go

/*==============================================================*/
/* Index: I_PFP_CARDRECOMMENDATIONS_UUID                        */
/*==============================================================*/
create unique index I_PFP_CARDRECOMMENDATIONS_UUID on PFP_CARD_RECOMMENDATIONS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_CHANNELS                                          */
/*==============================================================*/
create table PFP_CHANNELS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   DELETED              CHAR(1)              default '0' not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_CHANNELS primary key (ID)
)

go

create sequence S_PFP_CHANNELS
go

/*==============================================================*/
/* Index: I_PFP_CHANNELS_NAME                                   */
/*==============================================================*/
create unique index I_PFP_CHANNELS_NAME on PFP_CHANNELS (
   decode(DELETED, '1', null, NAME) ASC
)
go

/*==============================================================*/
/* Index: I_PFP_CHANNELS_UUID                                   */
/*==============================================================*/
create unique index I_PFP_CHANNELS_UUID on PFP_CHANNELS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_COMPLEX_PRODUCTS                                  */
/*==============================================================*/
create table PFP_COMPLEX_PRODUCTS 
(
   ID                   INTEGER              not null,
   TYPE                 CHAR(1)              not null,
   DESCRIPTION          VARCHAR2(500)        not null,
   MIN_SUM              NUMBER,
   ACCOUNT_ID           INTEGER              not null,
   IMAGE_ID             INTEGER,
   MIN_SUM_INSURANCE    NUMBER,
   USE_ICON             CHAR(1)              not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_COMPLEX_PRODUCTS primary key (ID)
)

go

create sequence S_PFP_COMPLEX_PRODUCTS
go

/*==============================================================*/
/* Index: I_PFP_C_PROD_ACCOUNT                                  */
/*==============================================================*/
create unique index I_PFP_C_PROD_ACCOUNT on PFP_COMPLEX_PRODUCTS (
   TYPE ASC,
   ACCOUNT_ID ASC
)
go

/*==============================================================*/
/* Index: I_PFP_COMPLEX_PRODUCTS_UUID                           */
/*==============================================================*/
create unique index I_PFP_COMPLEX_PRODUCTS_UUID on PFP_COMPLEX_PRODUCTS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_CP_TABLE_VIEW_PARAMETERS                          */
/*==============================================================*/
create table PFP_CP_TABLE_VIEW_PARAMETERS 
(
   PRODUCT_ID           INTEGER              not null,
   TABLE_COLUMN_ID      INTEGER              not null,
   VALUE                varchar2(100)        not null,
   constraint PK_PFP_CP_TBL_VW_PARAMETERS primary key (PRODUCT_ID, TABLE_COLUMN_ID)
)
go

/*==============================================================*/
/* Table: PFP_CP_TARGET_GROUPS_BUNDLE                           */
/*==============================================================*/
create table PFP_CP_TARGET_GROUPS_BUNDLE 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(20)         not null,
   constraint PK_PFP_CP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

/*==============================================================*/
/* Table: PFP_CR_CARDS                                          */
/*==============================================================*/
create table PFP_CR_CARDS 
(
   RECOMMENDATION_ID    INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   CARD_ID              INTEGER              not null,
   constraint PK_PFP_CR_CARDS primary key (RECOMMENDATION_ID, LIST_INDEX)
)
go

/*==============================================================*/
/* Table: PFP_CR_STEPS                                          */
/*==============================================================*/
create table PFP_CR_STEPS 
(
   RECOMMENDATION_ID    INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   DESCRIPTION          VARCHAR2(500)        not null,
   constraint PK_PFP_CR_STEPS primary key (RECOMMENDATION_ID, LIST_INDEX)
)
go

/*==============================================================*/
/* Table: PFP_C_FUND_PRODUCTS                                   */
/*==============================================================*/
create table PFP_C_FUND_PRODUCTS 
(
   C_PRODUCT_ID         INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   PRODUCT_ID           INTEGER              not null,
   constraint PK_PFP_C_FUND_PRODUCTS primary key (C_PRODUCT_ID, LIST_INDEX, PRODUCT_ID)
)
go

/*==============================================================*/
/* Table: PFP_C_IMA_PRODUCTS                                    */
/*==============================================================*/
create table PFP_C_IMA_PRODUCTS 
(
   C_PRODUCT_ID         INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   PRODUCT_ID           INTEGER              not null,
   constraint PK_PFP_C_IMA_PRODUCTS primary key (C_PRODUCT_ID, LIST_INDEX, PRODUCT_ID)
)
go

/*==============================================================*/
/* Table: PFP_C_INSURANCE_PRODUCTS                              */
/*==============================================================*/
create table PFP_C_INSURANCE_PRODUCTS 
(
   C_PRODUCT_ID         INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   INSURANCE_ID         INTEGER              not null,
   constraint PK_PFP_C_INSURANCE_PRODUCTS primary key (C_PRODUCT_ID, LIST_INDEX)
)
go

/*==============================================================*/
/* Table: PFP_C_PRODUCT_PARAMETERS                              */
/*==============================================================*/
create table PFP_C_PRODUCT_PARAMETERS 
(
   PRODUCT_ID           INTEGER              not null,
   KEY_PARAMETER        VARCHAR2(16)         not null,
   MIN_SUM              NUMBER               not null,
   constraint PK_PFP_C_PRODUCT_PARAMETERS primary key (PRODUCT_ID, KEY_PARAMETER)
)
go

/*==============================================================*/
/* Table: PFP_DIAGRAM_STEPS                                     */
/*==============================================================*/
create table PFP_DIAGRAM_STEPS 
(
   PFP_TYPE_PARAMETERS_ID INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   STEP_FROM            INTEGER              not null,
   STEP_TO              INTEGER              not null,
   STEP_NAME            VARCHAR2(50)         not null,
   constraint PK_PFP_DIAGRAM_STEPS primary key (PFP_TYPE_PARAMETERS_ID, LIST_INDEX)
)
go

/*==============================================================*/
/* Table: PFP_INSURANCE_COMPANIES                               */
/*==============================================================*/
create table PFP_INSURANCE_COMPANIES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   IMAGE_ID             INTEGER,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_INSURANCE_COMPANIES primary key (ID)
)

go

create sequence S_PFP_INSURANCE_COMPANIES
go

/*==============================================================*/
/* Index: I_PFP_INSURANCE_COMPANIES_NAME                        */
/*==============================================================*/
create unique index I_PFP_INSURANCE_COMPANIES_NAME on PFP_INSURANCE_COMPANIES (
   NAME ASC
)
go

/*==============================================================*/
/* Index: I_PFP_INSURANCE_COMPANIES_UUID                        */
/*==============================================================*/
create unique index I_PFP_INSURANCE_COMPANIES_UUID on PFP_INSURANCE_COMPANIES (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_INSURANCE_DATE_PERIODS                            */
/*==============================================================*/
create table PFP_INSURANCE_DATE_PERIODS 
(
   ID                   INTEGER              not null,
   TYPE_ID              INTEGER              not null,
   PERIOD               VARCHAR2(15)         not null,
   IS_DEFAULT_PERIOD    CHAR(1)              not null,
   PERIOD_MIN_SUM       NUMBER,
   PERIOD_MAX_SUM       NUMBER,
   constraint PK_PFP_I_DATE_PERIODS primary key (ID)
)

go

create sequence S_PFP_INSURANCE_DATE_PERIODS
go

/*==============================================================*/
/* Table: PFP_INSURANCE_PERIOD_TYPES                            */
/*==============================================================*/
create table PFP_INSURANCE_PERIOD_TYPES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   MONTHS               INTEGER,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_I_PERIOD_TYPES primary key (ID)
)

go

create sequence S_PFP_INSURANCE_PERIOD_TYPES
go

/*==============================================================*/
/* Index: I_PFP_I_P_PERIOD_TYPES_NAME                           */
/*==============================================================*/
create unique index I_PFP_I_P_PERIOD_TYPES_NAME on PFP_INSURANCE_PERIOD_TYPES (
   NAME ASC
)
go

/*==============================================================*/
/* Index: I_PFP_INS_PERIOD_TYPES_UUID                           */
/*==============================================================*/
create unique index I_PFP_INS_PERIOD_TYPES_UUID on PFP_INSURANCE_PERIOD_TYPES (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_INSURANCE_PRODUCTS                                */
/*==============================================================*/
create table PFP_INSURANCE_PRODUCTS 
(
   ID                   INTEGER              not null,
   TYPE_ID              INTEGER,
   NAME                 VARCHAR2(250)        not null,
   DESCRIPTION          VARCHAR2(500),
   IMAGE_ID             INTEGER,
   MIN_AGE              INTEGER,
   MAX_AGE              INTEGER,
   FOR_COMPLEX          char(1)              not null,
   COMPANY_ID           INTEGER              not null,
   MIN_INCOME           NUMBER(7,2),
   MAX_INCOME           NUMBER(7,2),
   DEFAULT_INCOME       NUMBER(7,2),
   UNIVERSAL            CHAR(1)              not null,
   ENABLED              CHAR(1)              not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_I_PRODUCTS primary key (ID)
)

go

create sequence S_PFP_INSURANCE_PRODUCTS
go

/*==============================================================*/
/* Index: I_PFP_INSURANCE_PRODUCTS_NAME                         */
/*==============================================================*/
create unique index I_PFP_INSURANCE_PRODUCTS_NAME on PFP_INSURANCE_PRODUCTS (
   NAME ASC,
   FOR_COMPLEX ASC
)
go

/*==============================================================*/
/* Index: I_PFP_PRODUCTS_UNIVERSAL_IP                           */
/*==============================================================*/
create unique index I_PFP_PRODUCTS_UNIVERSAL_IP on PFP_INSURANCE_PRODUCTS (
   decode(UNIVERSAL,'0', null, '1') ASC
)
go

/*==============================================================*/
/* Index: I_PFP_INSURANCE_PRODUCTS_UUID                         */
/*==============================================================*/
create unique index I_PFP_INSURANCE_PRODUCTS_UUID on PFP_INSURANCE_PRODUCTS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_INSURANCE_TYPES                                   */
/*==============================================================*/
create table PFP_INSURANCE_TYPES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   IMAGE_ID             INTEGER,
   PARENT_ID            INTEGER,
   DESCRIPTION          VARCHAR(250)         not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_INSURANCE_TYPES primary key (ID)
)

go

create sequence S_PFP_INSURANCE_TYPES
go

/*==============================================================*/
/* Index: I_PFP_INSURANCE_TYPES_NAME                            */
/*==============================================================*/
create unique index I_PFP_INSURANCE_TYPES_NAME on PFP_INSURANCE_TYPES (
   NAME ASC,
   PARENT_ID ASC
)
go

/*==============================================================*/
/* Index: I_PFP_INSURANCE_TYPES_UUID                            */
/*==============================================================*/
create unique index I_PFP_INSURANCE_TYPES_UUID on PFP_INSURANCE_TYPES (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_INS_PRODUCT_TO_PERIODS                            */
/*==============================================================*/
create table PFP_INS_PRODUCT_TO_PERIODS 
(
   PRODUCT_ID           INTEGER              not null,
   LIST_INDEX           INTEGER              not null,
   DATE_PERIOD_ID       INTEGER              not null,
   constraint PK_PFP_INS_PRODUCT_TO_PERIODS primary key (PRODUCT_ID, LIST_INDEX)
)
go

/*==============================================================*/
/* Table: PFP_INVESTMENT_PERIODS                                */
/*==============================================================*/
create table PFP_INVESTMENT_PERIODS 
(
   ID                   INTEGER              not null,
   PERIOD               VARCHAR2(50)         not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_INVESTMENT_PERIODS primary key (ID)
)

go

create sequence S_PFP_INVESTMENT_PERIODS
go

/*==============================================================*/
/* Index: I_PFP_INV_PERIODS_PERIOD                              */
/*==============================================================*/
create unique index I_PFP_INV_PERIODS_PERIOD on PFP_INVESTMENT_PERIODS (
   PERIOD ASC
)
go

/*==============================================================*/
/* Index: I_PFP_INVESTMENT_PERIODS_UUID                         */
/*==============================================================*/
create unique index I_PFP_INVESTMENT_PERIODS_UUID on PFP_INVESTMENT_PERIODS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_IP_TARGET_GROUPS_BUNDLE                           */
/*==============================================================*/
create table PFP_IP_TARGET_GROUPS_BUNDLE 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(20)         not null,
   constraint PK_PFP_IP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

/*==============================================================*/
/* Table: PFP_LOAN_KINDS                                        */
/*==============================================================*/
create table PFP_LOAN_KINDS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(256)        not null,
   FROM_AMOUNT          NUMBER               not null,
   TO_AMOUNT            NUMBER               not null,
   FROM_PERIOD          INTEGER              not null,
   TO_PERIOD            INTEGER              not null,
   DEFAULT_PERIOD       INTEGER              not null,
   FROM_RATE            NUMBER               not null,
   TO_RATE              NUMBER               not null,
   DEFAULT_RATE         NUMBER               not null,
   IMAGE_ID             INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_LOAN_KINDS primary key (ID),
   constraint PFP_LOAN_KINDS_NAME_UNIQUE unique (NAME)
)

go

create sequence S_PFP_LOAN_KINDS
go

/*==============================================================*/
/* Index: I_PFP_LOAN_KINDS_UUID                                 */
/*==============================================================*/
create unique index I_PFP_LOAN_KINDS_UUID on PFP_LOAN_KINDS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_PENSION_FUND                                      */
/*==============================================================*/
create table PFP_PENSION_FUND 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   ICON_ID              INTEGER,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_PENSION_FUND primary key (ID)
)

go

create sequence S_PFP_PENSION_FUND
go

/*==============================================================*/
/* Index: I_PFP_PENSION_FUND_NAME                               */
/*==============================================================*/
create unique index I_PFP_PENSION_FUND_NAME on PFP_PENSION_FUND (
   NAME ASC
)
go

/*==============================================================*/
/* Index: I_PFP_PENSION_FUND_UUID                               */
/*==============================================================*/
create unique index I_PFP_PENSION_FUND_UUID on PFP_PENSION_FUND (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_PENSION_PRODUCT                                   */
/*==============================================================*/
create table PFP_PENSION_PRODUCT 
(
   ID                   INTEGER              not null,
   PENSION_FUND_ID      INTEGER              not null,
   ENTRY_FEE            NUMBER               not null,
   QUARTERLY_FEE        NUMBER               not null,
   MIN_PERIOD           INTEGER              not null,
   MAX_PERIOD           INTEGER              not null,
   DEFAULT_PERIOD       INTEGER              not null,
   NAME                 VARCHAR2(250)        not null,
   DESCRIPTION          VARCHAR2(500),
   IMAGE_ID             INTEGER,
   MIN_INCOME           NUMBER(7,2),
   MAX_INCOME           NUMBER(7,2),
   DEFAULT_INCOME       NUMBER(7,2),
   UNIVERSAL            char(1)              not null,
   ENABLED              char(1)              not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_PENSION_PRODUCT primary key (ID)
)

go

create sequence S_PFP_PENSION_PRODUCT
go

/*==============================================================*/
/* Index: I_PFP_PENSION_PRODUCT_UUID                            */
/*==============================================================*/
create unique index I_PFP_PENSION_PRODUCT_UUID on PFP_PENSION_PRODUCT (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_PRODUCTS                                          */
/*==============================================================*/
create table PFP_PRODUCTS 
(
   ID                   INTEGER              not null,
   TYPE                 CHAR(1)              not null,
   NAME                 VARCHAR2(250)        not null,
   DESCRIPTION          VARCHAR2(500),
   IMAGE_ID             INTEGER,
   FOR_COMPLEX          VARCHAR2(10),
   ADVISABLE_SUM        varchar2(32),
   MAX_SUM_FACTOR       NUMBER,
   MIN_INCOME           NUMBER(7,2),
   MAX_INCOME           NUMBER(7,2),
   DEFAULT_INCOME       NUMBER(7,2),
   RISK_ID              INTEGER,
   INVESTMENT_PERIOD_ID INTEGER,
   USE_ICON             CHAR(1)              not null,
   AXIS_X               INTEGER,
   AXIS_Y               INTEGER,
   UNIVERSAL            CHAR(1)              not null,
   ENABLED              CHAR(1)              not null,
   ERIB_PRODUCT_ID      INTEGER,
   ERIB_PRODUCT_ADDITIONAL_ID INTEGER,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_PRODUCTS primary key (ID)
)

go

create sequence S_PFP_PRODUCTS
go

/*==============================================================*/
/* Index: I_PFP_PROD_NAME                                       */
/*==============================================================*/
create unique index I_PFP_PROD_NAME on PFP_PRODUCTS (
   TYPE ASC,
   NAME ASC,
   FOR_COMPLEX ASC
)
go

/*==============================================================*/
/* Index: I_PFP_PRODUCTS_UNIVERSAL_P                            */
/*==============================================================*/
create unique index I_PFP_PRODUCTS_UNIVERSAL_P on PFP_PRODUCTS (
   decode(UNIVERSAL, '0', null, '1') ASC,
   decode(UNIVERSAL,'1', TYPE, null) ASC
)
go

/*==============================================================*/
/* Index: I_PFP_PRODUCTS_POINT_P                                */
/*==============================================================*/
create unique index I_PFP_PRODUCTS_POINT_P on PFP_PRODUCTS (
   decode(AXIS_X,null,null,TYPE) ASC,
   AXIS_X ASC,
   AXIS_Y ASC
)
go

/*==============================================================*/
/* Index: I_PFP_PRODUCTS_UUID                                   */
/*==============================================================*/
create unique index I_PFP_PRODUCTS_UUID on PFP_PRODUCTS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_PRODUCTS_WEIGHTS                                  */
/*==============================================================*/
create table PFP_PRODUCTS_WEIGHTS 
(
   ID                   INTEGER              not null,
   PRODUCT              VARCHAR2(15)         not null,
   WEIGHT               INTEGER,
   constraint PK_PFP_PRODUCTS_WEIGHTS primary key (ID, PRODUCT)
)

go

create sequence S_PFP_PRODUCTS_WEIGHTS
go

/*==============================================================*/
/* Table: PFP_PRODUCT_PARAMETERS                                */
/*==============================================================*/
create table PFP_PRODUCT_PARAMETERS 
(
   PRODUCT_ID           INTEGER              not null,
   KEY_PARAMETER        VARCHAR2(16)         not null,
   MIN_SUM              NUMBER               not null,
   constraint PK_PFP_PRODUCT_PARAMETERS primary key (PRODUCT_ID, KEY_PARAMETER)
)
go

/*==============================================================*/
/* Table: PFP_PRODUCT_TYPE_PARAMETERS                           */
/*==============================================================*/
create table PFP_PRODUCT_TYPE_PARAMETERS 
(
   ID                   INTEGER              not null,
   DICTIONARY_TYPE      VARCHAR2(100)        not null,
   NAME                 VARCHAR2(100)        not null,
   USE                  CHAR(1)              not null,
   IMAGE_ID             INTEGER              not null,
   DESCRIPTION          VARCHAR2(500)        not null,
   USE_ON_DIAGRAM       CHAR(1)              not null,
   USE_ON_TABLE         CHAR(1)              not null,
   AXIS_USE_ZERO        CHAR(1),
   X_AXIS_NAME          VARCHAR2(50),
   X_AXIS_USE_STEPS     CHAR(1),
   Y_AXIS_NAME          VARCHAR2(50),
   LINK_NAME            VARCHAR2(100)        not null,
   LINK_HINT            VARCHAR2(500)        not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_PRODUCT_TYPE_PARAMETERS primary key (ID)
)

go

create sequence S_PFP_PRODUCT_TYPE_PARAMETERS
go

/*==============================================================*/
/* Index: I_PFP_PRODUCT_TYPES_UUID                              */
/*==============================================================*/
create unique index I_PFP_PRODUCT_TYPES_UUID on PFP_PRODUCT_TYPE_PARAMETERS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_PT_TARGET_GROUPS                                  */
/*==============================================================*/
create table PFP_PT_TARGET_GROUPS 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(16)         not null,
   constraint PK_PFP_PT_TARGET_GROUPS primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

/*==============================================================*/
/* Table: PFP_P_PRODUCT_TARGET_GROUPS                           */
/*==============================================================*/
create table PFP_P_PRODUCT_TARGET_GROUPS 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(20)         not null,
   constraint PK_PFP_P_PRODUCT_TARGET_GROUPS primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

/*==============================================================*/
/* Table: PFP_QUESTIONS                                         */
/*==============================================================*/
create table PFP_QUESTIONS 
(
   ID                   INTEGER              not null,
   TEXT                 VARCHAR2(250),
   IS_DELETED           CHAR(1)              not null,
   SEGMENT              VARCHAR2(16)         not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_QUESTIONS primary key (ID)
)

go

create sequence S_PFP_QUESTIONS
go

/*==============================================================*/
/* Index: I_PFP_QUESTIONS_UUID                                  */
/*==============================================================*/
create unique index I_PFP_QUESTIONS_UUID on PFP_QUESTIONS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_RISKS                                             */
/*==============================================================*/
create table PFP_RISKS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)         not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_RISKS primary key (ID)
)

go

create sequence S_PFP_RISKS
go

/*==============================================================*/
/* Index: I_PFP_RISK_NAME                                       */
/*==============================================================*/
create unique index I_PFP_RISK_NAME on PFP_RISKS (
   NAME ASC
)
go

/*==============================================================*/
/* Index: I_PFP_RISKS_UUID                                      */
/*==============================================================*/
create unique index I_PFP_RISKS_UUID on PFP_RISKS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_RISK_PROFILES                                     */
/*==============================================================*/
create table PFP_RISK_PROFILES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(250)        not null,
   DESCRIPTION          VARCHAR2(500)        not null,
   MIN_WEIGHT           INTEGER              not null,
   MAX_WEIGHT           INTEGER,
   IS_DELETED           CHAR(1)              not null,
   IS_DEFAULT           CHAR(1)              not null,
   SEGMENT              VARCHAR2(16)         not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_RISK_PROFILES primary key (ID)
)

go

create sequence S_PFP_RISK_PROFILES
go

/*==============================================================*/
/* Index: I_PFP_RISK_PROFILES_UUID                              */
/*==============================================================*/
create unique index I_PFP_RISK_PROFILES_UUID on PFP_RISK_PROFILES (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_SP_TABLE_VIEW_PARAMETERS                          */
/*==============================================================*/
create table PFP_SP_TABLE_VIEW_PARAMETERS 
(
   PRODUCT_ID           INTEGER              not null,
   TABLE_COLUMN_ID      INTEGER              not null,
   VALUE                VARCHAR2(100)        not null,
   constraint PK_PFP_SP_TABLE_VIEW_PARAMETER primary key (PRODUCT_ID, TABLE_COLUMN_ID)
)
go

/*==============================================================*/
/* Table: PFP_SP_TARGET_GROUPS_BUNDLE                           */
/*==============================================================*/
create table PFP_SP_TARGET_GROUPS_BUNDLE 
(
   PRODUCT_ID           INTEGER              not null,
   SEGMENT_CODE         VARCHAR2(20)         not null,
   constraint PK_PFP_SP_TARGET_GROUPS_BUNDLE primary key (PRODUCT_ID, SEGMENT_CODE)
)
go

/*==============================================================*/
/* Table: PFP_TABLE_COLUMNS                                     */
/*==============================================================*/
create table PFP_TABLE_COLUMNS 
(
   ID                   INTEGER              not null,
   PFP_TYPE_PARAMETERS_ID INTEGER,
   ORDER_INDEX          INTEGER              not null,
   COLUMN_NAME          VARCHAR2(50)         not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_TABLE_COLUMNS primary key (ID)
)

go

create sequence S_PFP_TABLE_COLUMNS
go

/*==============================================================*/
/* Index: I_PFP_TABLE_COLUMNS_UUID                              */
/*==============================================================*/
create unique index I_PFP_TABLE_COLUMNS_UUID on PFP_TABLE_COLUMNS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PFP_TARGETS                                           */
/*==============================================================*/
create table PFP_TARGETS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(250)        not null,
   IMAGE_ID             INTEGER,
   ONLY_ONE             CHAR(1)              default '0' not null,
   LATER_ALL            CHAR(1)              default '0' not null,
   LATER_LOANS          CHAR(1)              default '0' not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PFP_TARGETS primary key (ID),
   constraint PFP_TARGETS_NAME_UNIQUE unique (NAME)
)

go

create sequence S_PFP_TARGETS
go

/*==============================================================*/
/* Index: I_PFP_TARGETS_UUID                                    */
/*==============================================================*/
create unique index I_PFP_TARGETS_UUID on PFP_TARGETS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PROMO_CODES_DEPOSIT                                   */
/*==============================================================*/
create table PROMO_CODES_DEPOSIT 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(9)          not null,
   CODE_G               VARCHAR2(9)          not null,
   MASK                 VARCHAR2(15)         not null,
   CODE_S               VARCHAR2(9)          not null,
   DATE_BEGIN           TIMESTAMP            not null,
   DATE_END             TIMESTAMP            not null,
   SROK_BEGIN           VARCHAR2(7)          not null,
   SROK_END             VARCHAR2(7)          not null,
   NUM_USE              INTEGER              default 0 not null,
   PRIORITY             CHAR(1)              not null,
   AB_REMOVE            CHAR(1)              not null,
   ACTIVE_COUNT         INTEGER              not null,
   HIST_COUNT           INTEGER              not null,
   NAME_ACT             VARCHAR2(250)        not null,
   NAME_S               VARCHAR2(150)        not null,
   NAME_F               VARCHAR2(500),
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PROMO_CODES_DEPOSIT primary key (ID)
)

go

create sequence S_PROMO_CODES_DEPOSIT
go

comment on table PROMO_CODES_DEPOSIT is
'Справочник промо-кодов для открытия вкладов'
go

comment on column PROMO_CODES_DEPOSIT.CODE is
'Уникальный код «промо»'
go

comment on column PROMO_CODES_DEPOSIT.CODE_G is
'Код промо-акции'
go

comment on column PROMO_CODES_DEPOSIT.MASK is
'Маска промо- кода'
go

comment on column PROMO_CODES_DEPOSIT.CODE_S is
'Код сегмента клиента'
go

comment on column PROMO_CODES_DEPOSIT.DATE_BEGIN is
'Дата начала периода активации промо- кода'
go

comment on column PROMO_CODES_DEPOSIT.DATE_END is
'Дата окончания периода активации промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.SROK_BEGIN is
'Срок действия промо-кода, начиная со дня ввода клиентом'
go

comment on column PROMO_CODES_DEPOSIT.SROK_END is
'Срок действия промо-кода, начиная со дня окончания периода активации промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.NUM_USE is
'Возможное количество раз использования клиентом'
go

comment on column PROMO_CODES_DEPOSIT.PRIORITY is
'Приоритет условий с промо-кодом «0» - используются ставки для промо-кода; «1» - отображается как отдельный вклад'
go

comment on column PROMO_CODES_DEPOSIT.AB_REMOVE is
'Возможность удаления введенного промо-кода:«0» - нельзя удалять;«1» - можно удалять'
go

comment on column PROMO_CODES_DEPOSIT.ACTIVE_COUNT is
'Количество действующих промо- кодов у клиента'
go

comment on column PROMO_CODES_DEPOSIT.HIST_COUNT is
'Количество введенных промо - кодов клиентом'
go

comment on column PROMO_CODES_DEPOSIT.NAME_ACT is
'Описание промо акции'
go

comment on column PROMO_CODES_DEPOSIT.NAME_S is
'Краткое описание промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.NAME_F is
'Подробное описание промо-кода'
go

comment on column PROMO_CODES_DEPOSIT.UUID is
'Кросблочный ID'
go

/*==============================================================*/
/* Index: IDX_PROMO_CODES_DEP_UUID                              */
/*==============================================================*/
create unique index IDX_PROMO_CODES_DEP_UUID on PROMO_CODES_DEPOSIT (
   UUID ASC
)
go

/*==============================================================*/
/* Index: IDX_PROMO_CODES_DEP_CODE                              */
/*==============================================================*/
create unique index IDX_PROMO_CODES_DEP_CODE on PROMO_CODES_DEPOSIT (
   CODE ASC
)
go

/*==============================================================*/
/* Table: PROPERTIES                                            */
/*==============================================================*/
create table PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500)        not null,
   CATEGORY             VARCHAR2(80),
   constraint PK_PROPERTIES primary key (ID)
)

go

create sequence S_PROPERTIES
go

/*==============================================================*/
/* Index: PROPERTIES_UNIQ                                       */
/*==============================================================*/
create unique index PROPERTIES_UNIQ on PROPERTIES (
   PROPERTY_KEY ASC,
   CATEGORY ASC
)
go

/*==============================================================*/
/* Table: PROPERTY_SYNC_INFO                                    */
/*==============================================================*/
create table PROPERTY_SYNC_INFO 
(
   ID                   INTEGER              not null,
   NODE_ID              INTEGER              not null,
   OPERATION_DATE       DATE                 not null,
   STATE                VARCHAR2(5)          not null,
   GUID                 VARCHAR2(32)         not null
)
partition by range
 (OPERATION_DATE)
    interval (NUMTOYMINTERVAL(1,'YEAR'))
 (partition
         P_START
        values less than (to_date('10-10-2014','DD-MM-YYYY'))
         compress)
go

create sequence S_PROPERTY_SYNC_INFO
go

/*==============================================================*/
/* Index: I_PROP_SYNC_INFO_GUID                                 */
/*==============================================================*/
create index I_PROP_SYNC_INFO_GUID on PROPERTY_SYNC_INFO (
   GUID ASC
)
local
go

/*==============================================================*/
/* Table: PROVIDER_PROPERTIES                                   */
/*==============================================================*/
create table PROVIDER_PROPERTIES 
(
   PROVIDER_ID          INTEGER              not null,
   MCHECKOUT_DEFAULT_ENABLED CHAR(1)              not null,
   MB_CHECK_ENABLED     CHAR(1)              not null,
   EINVOICE_DEFAULT_ENABLED CHAR(1)              not null,
   MB_CHECK_DEFAULT_ENABLED CHAR(1)              not null,
   UPDATE_DATE          TIMESTAMP,
   USE_ESB              CHAR(1)              default '0' not null,
   constraint PK_PROVIDER_PROPERTIES primary key (PROVIDER_ID)
)

go

create sequence S_PROVIDER_PROPERTIES
go

/*==============================================================*/
/* Table: PROVIDER_REPLICA_TASKS                                */
/*==============================================================*/
create table PROVIDER_REPLICA_TASKS 
(
   ID                   INTEGER              not null,
   OWNER_ID             INTEGER              not null,
   OWNER_FIO            VARCHAR2(256)        not null,
   CREATION_DATE        TIMESTAMP            not null,
   STATE_CODE           VARCHAR2(20)         not null,
   REPORT_START_DATE    TIMESTAMP,
   REPORT_END_DATE      TIMESTAMP,
   CONTENT              BLOB,
   BILLINGS             VARCHAR2(200),
   PROPERTIES           BLOB,
   DETAILED_REPORT      CLOB,
   SOURCE_ERRORS        NUMBER(22,0),
   SOURCE_SUCCESS       NUMBER(22,0),
   DEST_INSERED         NUMBER(22,0),
   DEST_UPDATED         NUMBER(22,0),
   DEST_DELETED         NUMBER(22,0),
   constraint PK_PROVIDER_REPLICA_TASKS primary key (ID)
)

go

create sequence S_PROVIDER_REPLICA_TASKS
go

/*==============================================================*/
/* Table: PROVIDER_SMS_ALIAS                                    */
/*==============================================================*/
create table PROVIDER_SMS_ALIAS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(20)         not null,
   SERVICE_PROVIDER_ID  INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_PROVIDER_SMS_ALIAS primary key (ID)
)

go

create sequence S_PROVIDER_SMS_ALIAS
go

/*==============================================================*/
/* Index: PROVIDER_SMS_ALIAS_NAME                               */
/*==============================================================*/
create unique index PROVIDER_SMS_ALIAS_NAME on PROVIDER_SMS_ALIAS (
   NAME ASC
)
go

/*==============================================================*/
/* Index: I_PROVIDER_SMS_ALIAS_UUID                             */
/*==============================================================*/
create unique index I_PROVIDER_SMS_ALIAS_UUID on PROVIDER_SMS_ALIAS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: PROVIDER_SMS_ALIAS_FIELD                              */
/*==============================================================*/
create table PROVIDER_SMS_ALIAS_FIELD 
(
   ID                   INTEGER              not null,
   EDITABLE             CHAR(1)              not null,
   VALUE                NVARCHAR2(2000),
   PROVIDER_SMS_ALIAS_ID INTEGER              not null,
   FIELD_DESCRIPTION_ID INTEGER              not null,
   constraint PK_PROVIDER_SMS_ALIAS_FIELD primary key (ID)
)

go

create sequence S_PROVIDER_SMS_ALIAS_FIELD
go

/*==============================================================*/
/* Table: QUICK_PAYMENT_PANELS                                  */
/*==============================================================*/
create table QUICK_PAYMENT_PANELS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   STATE                VARCHAR(16)          not null,
   NAME                 VARCHAR(100)         not null,
   START_DATE           TIMESTAMP,
   END_DATE             TIMESTAMP,
   constraint PK_QUICK_PAYMENT_PANELS primary key (ID),
   constraint AK_NAME_UNIQUE unique (NAME),
   constraint AK_UUID_UNIQUE unique (UUID)
)

go

create sequence S_QUICK_PAYMENT_PANELS
go

/*==============================================================*/
/* Table: Q_P_PANELS_DEPARTMENTS                                */
/*==============================================================*/
create table Q_P_PANELS_DEPARTMENTS 
(
   Q_P_PANEL_ID         INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   constraint PK_Q_P_PANELS_DEPARTMENTS primary key (Q_P_PANEL_ID, TB),
   constraint AK_DEPARTMENT_ID unique (TB)
)

go

create sequence S_Q_P_PANELS_DEPARTMENTS
go

/*==============================================================*/
/* Table: RECEPTIONTIMES                                        */
/*==============================================================*/
create table RECEPTIONTIMES 
(
   ID                   INTEGER              not null,
   CALENDAR_ID          INTEGER,
   DEPARTMENT_ID        INTEGER              not null,
   TIME_START           VARCHAR2(10),
   TIME_END             VARCHAR2(10),
   USE_PARENT_SETTINGS  CHAR(1)              not null,
   PAYMENTTYPE          VARCHAR2(256)        not null,
   PAYMENTTYPEDESCRIPTION VARCHAR2(256)        not null,
   constraint PK_RECEPTIONTIMES primary key (ID)
)

go

create sequence S_RECEPTIONTIMES
go

/*==============================================================*/
/* Index: INDEX_DEPFORM                                         */
/*==============================================================*/
create index INDEX_DEPFORM on RECEPTIONTIMES (
   DEPARTMENT_ID ASC,
   PAYMENTTYPE ASC
)
go

/*==============================================================*/
/* Table: REGIONS                                               */
/*==============================================================*/
create table REGIONS 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(20)         not null,
   NAME                 NVARCHAR2(128)       not null,
   CODE_TB              VARCHAR2(2),
   PARENT_ID            INTEGER,
   PROVIDER_CODE_MAPI   VARCHAR2(200),
   PROVIDER_CODE_ATM    VARCHAR2(200),
   UUID                 VARCHAR2(32)         not null,
   constraint PK_REGIONS primary key (ID)
)

go

create sequence S_REGIONS
go

/*==============================================================*/
/* Index: INDEX_CODE_2                                          */
/*==============================================================*/
create unique index INDEX_CODE_2 on REGIONS (
   CODE ASC
)
go

/*==============================================================*/
/* Index: I_REGIONS_UUID                                        */
/*==============================================================*/
create unique index I_REGIONS_UUID on REGIONS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: REGION_RES                                            */
/*==============================================================*/
create table REGION_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(128)        not null,
   constraint PK_REGION_RES primary key (UUID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: RUSBANKS                                              */
/*==============================================================*/
create table RUSBANKS 
(
   ID                   VARCHAR2(64)         not null,
   NAME                 VARCHAR2(256)        not null,
   BIC                  VARCHAR2(26),
   PLACE                VARCHAR2(50),
   CORR_ACCOUNT         VARCHAR2(26),
   SHORT_NAME           VARCHAR2(256),
   COUNTRY              VARCHAR2(4),
   ADDRESS              VARCHAR2(256),
   OUR                  CHAR(1),
   PARTICIPANT_CODE     VARCHAR2(100),
   INN                  VARCHAR2(12),
   KPP                  VARCHAR2(12),
   DATE_CH              TIMESTAMP,
   constraint PK_RUSBANKS primary key (ID),
   constraint AK_UK_BANKS_BIC unique (BIC)
)

go

create sequence S_RUSBANKS
go

/*==============================================================*/
/* Table: RUSBANKS_RES                                          */
/*==============================================================*/
create table RUSBANKS_RES 
(
   ID                   VARCHAR2(64)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(256)        not null,
   PLACE                VARCHAR2(50),
   SHORT_NAME           VARCHAR2(256),
   ADDRESS              VARCHAR2(256),
   constraint PK_RUSBANKS_RES primary key (ID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: SERVICE_PROVIDERS                                     */
/*==============================================================*/
create table SERVICE_PROVIDERS 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   EXTERNAL_ID          VARCHAR2(128)        not null,
   CODE                 VARCHAR2(20)         not null,
   CODE_RECIPIENT_SBOL  VARCHAR2(32),
   NAME                 VARCHAR2(160)        not null,
   DESCRIPTION          VARCHAR2(512),
   ALIAS                VARCHAR2(250),
   LEGAL_NAME           VARCHAR2(250),
   NAME_ON_BILL         VARCHAR2(250),
   INN                  VARCHAR2(12),
   KPP                  VARCHAR2(9),
   ACCOUNT              VARCHAR2(25),
   BANK_CODE            VARCHAR2(9),
   BANK_NAME            VARCHAR2(128),
   CORR_ACCOUNT         VARCHAR2(25),
   BILLING_ID           INTEGER,
   CODE_SERVICE         VARCHAR2(50),
   NAME_SERVICE         VARCHAR2(150),
   IS_DEPT_AVAILABLE    CHAR(1)              default '0' not null,
   IS_FEDERAL           CHAR(1)              default '0' not null,
   MAX_COMISSION_AMOUNT number(15,4),
   PLANING_FOR_DEACTIVATE CHAR(1),
   MIN_COMISSION_AMOUNT number(15,4),
   COMISSION_RATE       number(15,4),
   DEPARTMENT_ID        INTEGER              not null,
   TRANSIT_ACCOUNT      varchar2(25),
   ATTR_DELIMITER       CHAR(1),
   ATTR_VALUES_DELIMITER CHAR(1),
   NSI_CODE             varchar2(128),
   STATE                varchar2(16)         not null,
   IS_GROUND            CHAR(1)              default '0' not null,
   IMAGE_ID             INTEGER,
   IS_POPULAR           CHAR(1)              default '0' not null,
   IS_PROPS_ONLINE      CHAR(1)              default '0' not null,
   IS_BANK_DETAILS      CHAR(1)              default '0' not null,
   ACCOUNT_TYPE         VARCHAR2(16),
   IS_MOBILEBANK        CHAR(1)              default '0' not null,
   MOBILEBANK_CODE      VARCHAR2(32),
   IS_FULL_PAYMENT      CHAR(1)              default '0' not null,
   PAYMENT_TYPE         VARCHAR2(20),
   KIND                 CHAR(1)              not null,
   IS_ALLOW_PAYMENTS    CHAR(1)              default '1' not null,
   PHONE_NUMBER         VARCHAR2(15),
   CREATION_DATE        TIMESTAMP            not null,
   IS_AUTOPAYMENT_SUPPORTED CHAR(1)              default '0' not null,
   URL                  VARCHAR2(256),
   TIP_OF_PROVIDER      VARCHAR2(255),
   BACK_URL             VARCHAR2(255),
   AFTER_ACTION         CHAR(1)              default '0' not null,
   CHECK_ORDER          CHAR(1)              default '0' not null,
   STANDART_SMS         CHAR(1)              default '1' not null,
   SMS_FORMAT           VARCHAR2(255),
   SMS_EXAMPLE          VARCHAR2(255),
   IS_BANKOMAT_SUPPORTED CHAR(1)              default '0' not null,
   VERSION_API          INTEGER,
   MIN_SUM_RESTRICTION  NUMBER(15,4),
   MAX_SUM_RESTRICTION  NUMBER(15,4),
   COMMISSION_MESSAGE   VARCHAR(250),
   FORM_NAME            VARCHAR2(35),
   SEND_CHARGE_OFF_INFO CHAR(1)              default '0' not null,
   PAYMENTCOUNT         INTEGER,
   IS_TEMPLATE_SUPPORTED CHAR(1)              default '1' not null,
   SORT_PRIORITY        INTEGER              not null,
   IS_BAR_SUPPORTED     CHAR(1)              default '0' not null,
   GROUP_RISK_ID        INTEGER,
   IMAGE_HELP_ID        INTEGER,
   IS_OFFLINE_AVAILABLE CHAR(1)              default '0' not null,
   VISIBLE_PAYMENTS_FOR_IB CHAR(1)              default '0' not null,
   VISIBLE_PAYMENTS_FOR_M_API CHAR(1)              default '0' not null,
   VISIBLE_PAYMENTS_FOR_ATM_API CHAR(1)              default '0' not null,
   VISIBLE_PAYMENTS_FOR_S_API CHAR(1)              default '0' not null,
   AVAILABLE_PAYMENTS_FOR_IB CHAR(1)              default '1' not null,
   AVAILABLE_PAYMENTS_FOR_M_API CHAR(1)              default '1' not null,
   AVAILABLE_PAYMENTS_FOR_ATM_API CHAR(1)              default '1' not null,
   AVAILABLE_PAYMENTS_FOR_ERMB CHAR(1)              default '1' not null,
   AVAILABLE_PAYMENTS_FOR_S_API CHAR(1)              default '1' not null,
   IS_AUTOPAYMENT_SUPPORTED_API CHAR(1)              default '0' not null,
   IS_AUTOPAYMENT_SUPPORTED_ATM CHAR(1)              default '0' not null,
   IS_AUTOPAYMENT_SUPPORTED_ERMB CHAR(1)              default '0' not null,
   IS_AUTOPAYMENT_SUPPORTED_S_API CHAR(1)              default '0' not null,
   IS_EDIT_PAYMENT_SUPPORTED CHAR(1)              default '1' not null,
   IS_CREDIT_CARD_SUPPORTED CHAR(1)              default '1' not null,
   VISIBLE_AUTOPAYMENTS_FOR_IB CHAR(1)              default '0' not null,
   VISIBLE_AUTOPAYMENTS_FOR_API CHAR(1)              default '0' not null,
   VISIBLE_AUTOPAYMENTS_FOR_ATM CHAR(1)              default '0' not null,
   VISIBLE_AUTOPAYMENTS_FOR_ERMB CHAR(1)              default '0' not null,
   VISIBLE_AUTOPAYMENTS_FOR_S_API CHAR(1)              default '0' not null,
   MOBILE_CHECKOUT_AVAILABLE CHAR(1)              default '0' not null,
   SUB_TYPE             VARCHAR2(10),
   IS_FASILITATOR       CHAR(1)              default '0' not null,
   constraint PK_SERVICE_PROVIDERS primary key (ID),
   constraint UN_CODE_S_CODE_R unique (CODE, CODE_SERVICE, BILLING_ID),
   constraint UN_CODE_S_CODE_R_SBOL unique (CODE_RECIPIENT_SBOL, CODE_SERVICE, BILLING_ID)
)

go

create sequence S_SERVICE_PROVIDERS
go

/*==============================================================*/
/* Index: SERV_PROV_PAYM_COUNT                                  */
/*==============================================================*/
create index SERV_PROV_PAYM_COUNT on SERVICE_PROVIDERS (
   PAYMENTCOUNT DESC
)
go

/*==============================================================*/
/* Index: INDEX_EXT_ID                                          */
/*==============================================================*/
create unique index INDEX_EXT_ID on SERVICE_PROVIDERS (
   EXTERNAL_ID ASC
)
go

/*==============================================================*/
/* Index: IDX_MB_CODE                                           */
/*==============================================================*/
create unique index IDX_MB_CODE on SERVICE_PROVIDERS (
   MOBILEBANK_CODE ASC
)
go

/*==============================================================*/
/* Index: IDX_ALLOWP                                            */
/*==============================================================*/
create index IDX_ALLOWP on SERVICE_PROVIDERS (
   IS_ALLOW_PAYMENTS ASC
)
go

/*==============================================================*/
/* Index: IND_ACC_INN                                           */
/*==============================================================*/
create index IND_ACC_INN on SERVICE_PROVIDERS (
   INN ASC,
   ACCOUNT ASC
)
go

/*==============================================================*/
/* Index: I_SERVICE_PROVIDERS_UUID                              */
/*==============================================================*/
create unique index I_SERVICE_PROVIDERS_UUID on SERVICE_PROVIDERS (
   UUID ASC
)
go

/*==============================================================*/
/* Index: IDX_SP_IS_FASILITATOR                                 */
/*==============================================================*/
create index IDX_SP_IS_FASILITATOR on SERVICE_PROVIDERS (
   IS_FASILITATOR ASC
)
go

/*==============================================================*/
/* Table: SERVICE_PROVIDERS_RES                                 */
/*==============================================================*/
create table SERVICE_PROVIDERS_RES 
(
   UUID                 VARCHAR2(32)         not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   NAME                 VARCHAR2(160)        not null,
   LEGAL_NAME           VARCHAR2(250)        not null,
   ALLIAS               VARCHAR2(250)        not null,
   BANK_NAME            VARCHAR2(128)        not null,
   DESCRIPTION          VARCHAR2(512)        not null,
   TIP_OF_PROVIDER      VARCHAR2(255)        not null,
   COMMISSION_MESSAGE   VARCHAR2(250)        not null,
   NAME_ON_BILL         VARCHAR2(250)        not null,
   NAME_SERVICE         VARCHAR2(150),
   constraint PK_SERVICE_PROVIDERS_RES primary key (UUID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: SERVICE_PROVIDER_REGIONS                              */
/*==============================================================*/
create table SERVICE_PROVIDER_REGIONS 
(
   REGION_ID            INTEGER              not null,
   SERVICE_PROVIDER_ID  INTEGER              not null,
   SHOW_IN_PROMO_BLOCK  CHAR(1)              default '0' not null,
   constraint PK_SERVICE_PROVIDER_REGIONS primary key (REGION_ID, SERVICE_PROVIDER_ID)
)
go

/*==============================================================*/
/* Table: SERV_PROVIDER_PAYMENT_SERV                            */
/*==============================================================*/
create table SERV_PROVIDER_PAYMENT_SERV 
(
   PAYMENT_SERVICE_ID   INTEGER              not null,
   SERVICE_PROVIDER_ID  INTEGER              not null,
   LIST_INDEX           INTEGER,
   constraint PK_SERV_PROVIDER_PAYMENT_SERV primary key (PAYMENT_SERVICE_ID, SERVICE_PROVIDER_ID)
)
go

/*==============================================================*/
/* Table: SESSIONS                                              */
/*==============================================================*/
create table SESSIONS 
(
   SID                  VARCHAR2(32)         not null,
   LOGIN_ID             INTEGER              not null,
   CREATION_DATE        TIMESTAMP            not null,
   CLOSE_DATE           TIMESTAMP,
   STATE                VARCHAR2(12)         not null,
   constraint PK_SESSIONS primary key (SID)
)

go

create sequence S_SESSIONS
go

/*==============================================================*/
/* Table: UDBO_CLAIM_RULES                                      */
/*==============================================================*/
create table UDBO_CLAIM_RULES 
(
   ID                   INTEGER              not null,
   UUID                 VARCHAR2(32)         not null,
   START_DATE           TIMESTAMP            not null,
   RULES_TEXT           CLOB                 not null,
   constraint PK_UDBO_CLAIM_RULES primary key (ID)
)

go

create sequence S_UDBO_CLAIM_RULES
go

/*==============================================================*/
/* Index: UDBO_CL_RU_START_DATE_IDX                             */
/*==============================================================*/
create index UDBO_CL_RU_START_DATE_IDX on UDBO_CLAIM_RULES (
   START_DATE ASC
)
go

/*==============================================================*/
/* Table: WORK_DAYS                                             */
/*==============================================================*/
create table WORK_DAYS 
(
   ID                   INTEGER              not null,
   CALENDAR_ID          INTEGER              not null,
   WORK_DATE            TIMESTAMP            not null,
   WORK_DAY             CHAR(1)              not null,
   constraint PK_WORK_DAYS primary key (ID)
)

go

create sequence S_WORK_DAYS
go

/*==============================================================*/
/* Index: WORK_DAYS_INDEX_DAY                                   */
/*==============================================================*/
create index WORK_DAYS_INDEX_DAY on WORK_DAYS (
   (TRUNC(WORK_DATE, 'DDD')) ASC
)
go


create index "DXFK_ADV_DEP_TO_ADVERTISINGS" on ADVERTISINGS_DEPARTMENTS
(
   ADVERTISING_ID
)
go

alter table ADVERTISINGS_DEPARTMENTS
   add constraint FK_ADV_DEP_TO_ADVERTISINGS foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_AREA_TO_ADVERTISINGS" on ADVERTISING_AREAS
(
   ADVERTISING_ID
)
go

alter table ADVERTISING_AREAS
   add constraint FK_ADV_AREA_TO_ADVERTISINGS foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_BUTT_TO_ADVERTISINGS" on ADVERTISING_BUTTONS
(
   ADVERTISING_ID
)
go

alter table ADVERTISING_BUTTONS
   add constraint FK_ADV_BUTT_TO_ADVERTISINGS foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_REQ_TO_ADVERTISINGS" on ADVERTISING_REQUIREMENTS
(
   ADVERTISING_ID
)
go

alter table ADVERTISING_REQUIREMENTS
   add constraint FK_ADV_REQ_TO_ADVERTISINGS foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_REQ_ACC_TYPES_TO_ADV" on ADVERTISING_REQ_ACC_TYPES
(
   ADVERTISING_ID
)
go

alter table ADVERTISING_REQ_ACC_TYPES
   add constraint FK_ADV_REQ_ACC_TYPES_TO_ADV foreign key (ADVERTISING_ID)
      references ADVERTISINGS (ID)
      on delete cascade
go


create index "DXFK_ADV_REQ_ACC_T_TO_DEPOSITS" on ADVERTISING_REQ_ACC_TYPES
(
   PRODUCT_ID
)
go

alter table ADVERTISING_REQ_ACC_TYPES
   add constraint FK_ADVERTIS_FK_ADV_RE_DEPOSITD foreign key (PRODUCT_ID)
      references DEPOSITDESCRIPTIONS (ID)
      on delete cascade
go

alter table ALLOWED_DEPARTMENTS
   add constraint FK_A_DEPARTMENTS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
go

alter table ATTRIBUTES_FOR_IDENT_TYPES
   add constraint "FK_ATTRIBUTE_TO_IDENT " foreign key (IDENT_ID)
      references IDENT_TYPE_FOR_DEPS (ID)
go


create index "DXFK_AUTH_TOKEN_TO_SESSIONS" on AUTHENTICATION_TOKEN
(
   SESSION_ID
)
go

alter table AUTHENTICATION_TOKEN
   add constraint FK_AUTH_TOKEN_TO_SESSIONS foreign key (SESSION_ID)
      references SESSIONS (SID)
      on delete cascade
go


create index "DXFK_AUTOPAY_SETTING_TO_PROV" on AUTOPAY_SETTINGS
(
   RECIPIENT_ID
)
go

alter table AUTOPAY_SETTINGS
   add constraint FK_AUTOPAY_SETTING_TO_PROV foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go

alter table C_CENTER_AREAS_DEPARTMENTS
   add constraint FK_C_C_AREA_DEP_TO_C_C_AREA foreign key (C_C_AREA_ID)
      references CONTACT_CENTER_AREAS (ID)
      on delete cascade
go

alter table DEPARTMENTS_TASKS_CONTENT
   add constraint FK_CONTENT_TO_DEP_TASKS foreign key (REPLICA_TASKS_ID)
      references DEPARTMENTS_REPLICA_TASKS (ID)
      on delete cascade
go


create index "DXFK_EMPLOYEE_TO_LOGINS" on EMPLOYEES
(
   LOGIN_ID
)
go

alter table EMPLOYEES
   add constraint FK_EMPLOYEE_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
go


create index "DXEXCEPTION_MAPPING_RESTRICTIO" on EXCEPTION_MAPPING_RESTRICTIONS
(
   HASH, GROUP_ID
)
go

alter table EXCEPTION_MAPPING_RESTRICTIONS
   add constraint FK_EXCEPTIO_EXCEPTION_EXCEPTIO foreign key (HASH, GROUP_ID)
      references EXCEPTION_MAPPINGS (HASH, GROUP_ID)
      on delete cascade
go


create index "DXFIELD_DESC_TO_PROVIDERS" on FIELD_DESCRIPTIONS
(
   RECIPIENT_ID
)
go

alter table FIELD_DESCRIPTIONS
   add constraint FIELD_DESC_TO_PROVIDERS foreign key (RECIPIENT_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go


create index "DXFK_F_REQUISITE_TYPE_TO_FIELD" on FIELD_REQUISITE_TYPES
(
   FIELD_ID
)
go

alter table FIELD_REQUISITE_TYPES
   add constraint FK_F_REQUISITE_TYPE_TO_FIELD foreign key (FIELD_ID)
      references FIELD_DESCRIPTIONS (ID)
      on delete cascade
go


create index "DXFK_F_VALID_TO_FIELD" on FIELD_VALIDATORS_DESCR
(
   FIELD_ID
)
go

alter table FIELD_VALIDATORS_DESCR
   add constraint FK_F_VALID_TO_FIELD foreign key (FIELD_ID)
      references FIELD_DESCRIPTIONS (ID)
      on delete cascade
go


create index "DXFK_VAL_PARAM_TO_VALID" on FIELD_VALIDATORS_PARAM
(
   FIELD_ID
)
go

alter table FIELD_VALIDATORS_PARAM
   add constraint FK_VAL_PARAM_TO_VALID foreign key (FIELD_ID)
      references FIELD_VALIDATORS_DESCR (ID)
      on delete cascade
go


create index "DXFK_F_VALUE_TO_FIELD" on FIELD_VALUES_DESCR
(
   FIELD_ID
)
go

alter table FIELD_VALUES_DESCR
   add constraint FK_F_VALUE_TO_FIELD foreign key (FIELD_ID)
      references FIELD_DESCRIPTIONS (ID)
      on delete cascade
go


create index "DXFK_LOGINS_TO_ACCESSSC" on LOGINS
(
   ACCESSSCHEME_ID
)
go

alter table LOGINS
   add constraint FK_LOGINS_TO_ACCESSSC foreign key (ACCESSSCHEME_ID)
      references ACCESSSCHEMES (ID)
go


create index "DXFK_B_LOGIN_E_TO_LOGINS" on LOGIN_BLOCK
(
   BLOCKER_ID
)
go

alter table LOGIN_BLOCK
   add constraint FK_B_LOGIN_E_TO_LOGINS foreign key (BLOCKER_ID)
      references LOGINS (ID)
      on delete set null
go

alter table LOGIN_BLOCK
   add constraint FK_B_LOGIN_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
go


create index "DXFK_OPER_CONTEXT_TO_SESSIONS" on OPERATION_CONTEXT
(
   SESSION_ID
)
go

alter table OPERATION_CONTEXT
   add constraint FK_OPERATIO_FK_OPER_C_SESSIONS foreign key (SESSION_ID)
      references SESSIONS (SID)
      on delete cascade
go


create index "DXFK_PANEL_BLOCKS_TO_PROVIDERS" on PANEL_BLOCKS
(
   PROVIDER_ID
)
go

alter table PANEL_BLOCKS
   add constraint FK_PANEL_BLOCKS_TO_PROVIDERS foreign key (PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go


create index "DXFK_PAN_BLCKS_TO_Q_P_PANELS" on PANEL_BLOCKS
(
   Q_P_PANEL_ID
)
go

alter table PANEL_BLOCKS
   add constraint FK_PAN_BLCKS_TO_Q_P_PANELS foreign key (Q_P_PANEL_ID)
      references QUICK_PAYMENT_PANELS (ID)
      on delete cascade
go


create index "DXFK_TO_PAY_GR_RSK_GROUP_RISK" on PAYMENTS_GROUP_RISK
(
   GROUP_RISK_ID
)
go

alter table PAYMENTS_GROUP_RISK
   add constraint FK_TO_PAY_GR_RSK_GROUP_RISK foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID)
go


create index "DXFK_P_SERVICE_TO_IMAGES" on PAYMENT_SERVICES
(
   IMAGE_ID
)
go

alter table PAYMENT_SERVICES
   add constraint FK_P_SERVICE_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go


create index "DXFK_P_SERVICE_TO_P_SERVICE" on PAYMENT_SERVICES
(
   PARENT_ID
)
go

alter table PAYMENT_SERVICES
   add constraint FK_P_SERVICE_TO_P_SERVICE foreign key (PARENT_ID)
      references PAYMENT_SERVICES (ID)
go


create index "DXFK_PAY_SER_TO_PARENT" on PAYMENT_SERV_PARENTS
(
   PARENT_ID
)
go

alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_PARENT foreign key (PARENT_ID)
      references PAYMENT_SERVICES (ID)
go


create index "DXFK_PAY_SER_TO_SERV" on PAYMENT_SERV_PARENTS
(
   SERVICE_ID
)
go

alter table PAYMENT_SERV_PARENTS
   add constraint FK_PAY_SER_TO_SERV foreign key (SERVICE_ID)
      references PAYMENT_SERVICES (ID)
go


create index "DXFK_ANSWERS_TO_QUESTIONS" on PFP_ANSWERS
(
   QUESTION_ID
)
go

alter table PFP_ANSWERS
   add constraint FK_ANSWERS_TO_QUESTIONS foreign key (QUESTION_ID)
      references PFP_QUESTIONS (ID)
      on delete cascade
go


create index "DXFK_PFP_C_PROD_TO_ACCOUNT_PRO" on PFP_COMPLEX_PRODUCTS
(
   ACCOUNT_ID
)
go

alter table PFP_COMPLEX_PRODUCTS
   add constraint FK_PFP_C_PROD_TO_ACCOUNT_PROD foreign key (ACCOUNT_ID)
      references PFP_PRODUCTS (ID)
go


create index "DXFK_PFP_CP_T_FK_TABLE_PFP_TAB" on PFP_CP_TABLE_VIEW_PARAMETERS
(
   TABLE_COLUMN_ID
)
go

alter table PFP_CP_TABLE_VIEW_PARAMETERS
   add constraint FK_PFP_CP_T_FK_TABLE_PFP_TABL foreign key (TABLE_COLUMN_ID)
      references PFP_TABLE_COLUMNS (ID)
      on delete cascade
go

alter table PFP_CR_CARDS
   add constraint FK_PFP_CR_CARDS_TO_CR foreign key (RECOMMENDATION_ID)
      references PFP_CARD_RECOMMENDATIONS (ID)
      on delete cascade
go

alter table PFP_CR_CARDS
   add constraint FK_PFP_CR_TO_CARDS foreign key (CARD_ID)
      references PFP_CARDS (ID)
go

alter table PFP_CR_STEPS
   add constraint FK_PFP_CR_STEPS_TO_CR foreign key (RECOMMENDATION_ID)
      references PFP_CARD_RECOMMENDATIONS (ID)
      on delete cascade
go


create index "DXFK_PFP_C_PROD_TO_FUND_PROD" on PFP_C_FUND_PRODUCTS
(
   PRODUCT_ID
)
go

alter table PFP_C_FUND_PRODUCTS
   add constraint FK_PFP_C_PROD_TO_FUND_PROD foreign key (PRODUCT_ID)
      references PFP_PRODUCTS (ID)
go


create index "DXFK_PFP_C_PROD_TO_IMA_PROD" on PFP_C_IMA_PRODUCTS
(
   PRODUCT_ID
)
go

alter table PFP_C_IMA_PRODUCTS
   add constraint FK_PFP_C_IM_FK_PFP_C__PFP_PROD foreign key (PRODUCT_ID)
      references PFP_PRODUCTS (ID)
go


create index "DXFK_PFP_C_I_PROD_TO_I_PROD" on PFP_C_INSURANCE_PRODUCTS
(
   INSURANCE_ID
)
go

alter table PFP_C_INSURANCE_PRODUCTS
   add constraint FK_PFP_C_I_PROD_TO_I_PROD foreign key (INSURANCE_ID)
      references PFP_INSURANCE_PRODUCTS (ID)
go


create index "DXFK_PFP_AXIS_TO_PT" on PFP_DIAGRAM_STEPS
(
   PFP_TYPE_PARAMETERS_ID
)
go

alter table PFP_DIAGRAM_STEPS
   add constraint FK_PFP_AXIS_TO_PT foreign key (PFP_TYPE_PARAMETERS_ID)
      references PFP_PRODUCT_TYPE_PARAMETERS (ID)
      on delete cascade
go


create index "DXFK_PFP_INS_P_TYPE_TO_D_RERIO" on PFP_INSURANCE_DATE_PERIODS
(
   TYPE_ID
)
go

alter table PFP_INSURANCE_DATE_PERIODS
   add constraint FK_PFP_INS_P_TYPE_TO_D_RERIOD foreign key (TYPE_ID)
      references PFP_INSURANCE_PERIOD_TYPES (ID)
go


create index "DXFK_PFP_COMPANY_TO_INS" on PFP_INSURANCE_PRODUCTS
(
   COMPANY_ID
)
go

alter table PFP_INSURANCE_PRODUCTS
   add constraint FK_PFP_COMPANY_TO_INS foreign key (COMPANY_ID)
      references PFP_INSURANCE_COMPANIES (ID)
go


create index "DXFK_PFP_I_PRODUCTS_TO_TYPES" on PFP_INSURANCE_PRODUCTS
(
   TYPE_ID
)
go

alter table PFP_INSURANCE_PRODUCTS
   add constraint FK_PFP_I_PRODUCTS_TO_TYPES foreign key (TYPE_ID)
      references PFP_INSURANCE_TYPES (ID)
go


create index "DXFK_PFP_I_TYPES_TO_I_TYPES" on PFP_INSURANCE_TYPES
(
   PARENT_ID
)
go

alter table PFP_INSURANCE_TYPES
   add constraint FK_PFP_I_TYPES_TO_I_TYPES foreign key (PARENT_ID)
      references PFP_INSURANCE_TYPES (ID)
go


create index "DXFK_PFP_PENSION_PRODUCT_TO_FU" on PFP_PENSION_PRODUCT
(
   PENSION_FUND_ID
)
go

alter table PFP_PENSION_PRODUCT
   add constraint FK_PFP_PENSION_PRODUCT_TO_FUND foreign key (PENSION_FUND_ID)
      references PFP_PENSION_FUND (ID)
go


create index "DXFK_PFP_INV_PERIOD_TO_PRODUCT" on PFP_PRODUCTS
(
   INVESTMENT_PERIOD_ID
)
go

alter table PFP_PRODUCTS
   add constraint FK_PFP_INV_PERIOD_TO_PRODUCT foreign key (INVESTMENT_PERIOD_ID)
      references PFP_INVESTMENT_PERIODS (ID)
go


create index "DXFK_PFP_RISK_TO_PRODUCT" on PFP_PRODUCTS
(
   RISK_ID
)
go

alter table PFP_PRODUCTS
   add constraint FK_PFP_RISK_TO_PRODUCT foreign key (RISK_ID)
      references PFP_RISKS (ID)
go


create index "DXFK_PFP_SEGMENT_TO_PT" on PFP_PT_TARGET_GROUPS
(
   PRODUCT_ID
)
go

alter table PFP_PT_TARGET_GROUPS
   add constraint FK_PFP_SEGMENT_TO_PT foreign key (PRODUCT_ID)
      references PFP_PRODUCT_TYPE_PARAMETERS (ID)
      on delete cascade
go


create index "DXFK_PFP_TARGET_GR_TO_P_PRODUC" on PFP_P_PRODUCT_TARGET_GROUPS
(
   PRODUCT_ID
)
go

alter table PFP_P_PRODUCT_TARGET_GROUPS
   add constraint FK_PFP_TARGET_GR_TO_P_PRODUCT foreign key (PRODUCT_ID)
      references PFP_PENSION_PRODUCT (ID)
      on delete cascade
go


create index "DXFK_PFP_SP_T_FK_TABLE_PFP_TAB" on PFP_SP_TABLE_VIEW_PARAMETERS
(
   TABLE_COLUMN_ID
)
go

alter table PFP_SP_TABLE_VIEW_PARAMETERS
   add constraint FK_PFP_SP_T_FK_TABLE_PFP_TABL foreign key (TABLE_COLUMN_ID)
      references PFP_TABLE_COLUMNS (ID)
      on delete cascade
go


create index "DXFK_PFP_TABLE_COLUMNS_TO_PT" on PFP_TABLE_COLUMNS
(
   PFP_TYPE_PARAMETERS_ID
)
go

alter table PFP_TABLE_COLUMNS
   add constraint FK_PFP_TABLE_COLUMNS_TO_PT foreign key (PFP_TYPE_PARAMETERS_ID)
      references PFP_PRODUCT_TYPE_PARAMETERS (ID)
go


create index "DXPROVIDER_PROP_TO_PROVIDER" on PROVIDER_PROPERTIES
(
   PROVIDER_ID
)
go

alter table PROVIDER_PROPERTIES
   add constraint PROVIDER_PROP_TO_PROVIDER foreign key (PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go


create index "DXFK_SMS_ALIAS_TO_PROVIDER" on PROVIDER_SMS_ALIAS
(
   SERVICE_PROVIDER_ID
)
go

alter table PROVIDER_SMS_ALIAS
   add constraint FK_SMS_ALIAS_TO_PROVIDER foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go


create index "DXFK_PROVIDER_FK_ALIAS__FIELD_" on PROVIDER_SMS_ALIAS_FIELD
(
   FIELD_DESCRIPTION_ID
)
go

alter table PROVIDER_SMS_ALIAS_FIELD
   add constraint FK_PROVIDER_FK_ALIAS__FIELD_DE foreign key (FIELD_DESCRIPTION_ID)
      references FIELD_DESCRIPTIONS (ID)
      on delete cascade
go


create index "DXFK_PROVIDER_FK_ALIAS__PROVID" on PROVIDER_SMS_ALIAS_FIELD
(
   PROVIDER_SMS_ALIAS_ID
)
go

alter table PROVIDER_SMS_ALIAS_FIELD
   add constraint FK_PROVIDER_FK_ALIAS__PROVIDER foreign key (PROVIDER_SMS_ALIAS_ID)
      references PROVIDER_SMS_ALIAS (ID)
      on delete cascade
go


create index "DXFK_Q_P_PAN_DEP_TO_Q_P_PAN" on Q_P_PANELS_DEPARTMENTS
(
   Q_P_PANEL_ID
)
go

alter table Q_P_PANELS_DEPARTMENTS
   add constraint FK_Q_P_PAN_DEP_TO_Q_P_PAN foreign key (Q_P_PANEL_ID)
      references QUICK_PAYMENT_PANELS (ID)
      on delete cascade
go


create index "DXFK_CALENDAR_TO_RT" on RECEPTIONTIMES
(
   CALENDAR_ID
)
go

alter table RECEPTIONTIMES
   add constraint FK_RECEPTIO_FK_CALEND_CALENDAR foreign key (CALENDAR_ID)
      references CALENDARS (ID)
go


create index "DXDXFK_REGION_TO_REGION" on REGIONS
(
   PARENT_ID
)
go

alter table REGIONS
   add constraint FK_REGION_TO_REGION foreign key (PARENT_ID)
      references REGIONS (ID)
go


create index "DXFK_SERVICE__FK_S_PROV_GROUPS" on SERVICE_PROVIDERS
(
   GROUP_RISK_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_SERVICE__FK_S_PROV_GROUPS_R foreign key (GROUP_RISK_ID)
      references GROUPS_RISK (ID)
go


create index "DXFK_SERVICE__FK_S_PROV_IMAGES" on SERVICE_PROVIDERS
(
   IMAGE_HELP_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_SERVICE__FK_S_PROV_IMAGES foreign key (IMAGE_HELP_ID)
      references IMAGES (ID)
go


create index "DXFK_S_PROVIDERS_TO_BILLINGS" on SERVICE_PROVIDERS
(
   BILLING_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_BILLINGS foreign key (BILLING_ID)
      references BILLINGS (ID)
go


create index "DXFK_S_PROVIDERS_TO_DEPARTMENT" on SERVICE_PROVIDERS
(
   DEPARTMENT_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_DEPARTMENTS foreign key (DEPARTMENT_ID)
      references DEPARTMENTS (ID)
go


create index "DXFK_S_PROVIDERS_TO_IMAGES" on SERVICE_PROVIDERS
(
   IMAGE_ID
)
go

alter table SERVICE_PROVIDERS
   add constraint FK_S_PROVIDERS_TO_IMAGES foreign key (IMAGE_ID)
      references IMAGES (ID)
go


create index "DXFK_PROV_REG_TO_PROV" on SERVICE_PROVIDER_REGIONS
(
   SERVICE_PROVIDER_ID
)
go

alter table SERVICE_PROVIDER_REGIONS
   add constraint FK_PROV_REG_TO_PROV foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go


create index "DXFK_PROV_REG_TO_REG" on SERVICE_PROVIDER_REGIONS
(
   REGION_ID
)
go

alter table SERVICE_PROVIDER_REGIONS
   add constraint FK_PROV_REG_TO_REG foreign key (REGION_ID)
      references REGIONS (ID)
go


create index "DXFK_PROV_PAY_SER_TO_PAY" on SERV_PROVIDER_PAYMENT_SERV
(
   PAYMENT_SERVICE_ID
)
go

alter table SERV_PROVIDER_PAYMENT_SERV
   add constraint FK_PROV_PAY_SER_TO_PAY foreign key (PAYMENT_SERVICE_ID)
      references PAYMENT_SERVICES (ID)
go


create index "DXFK_PROV_PAY_SER_TO_PROV" on SERV_PROVIDER_PAYMENT_SERV
(
   SERVICE_PROVIDER_ID
)
go

alter table SERV_PROVIDER_PAYMENT_SERV
   add constraint FK_PROV_PAY_SER_TO_PROV foreign key (SERVICE_PROVIDER_ID)
      references SERVICE_PROVIDERS (ID)
      on delete cascade
go


create index "DXFK_SESSIONS_TO_LOGINS" on SESSIONS
(
   LOGIN_ID
)
go

alter table SESSIONS
   add constraint FK_SESSIONS_TO_LOGINS foreign key (LOGIN_ID)
      references LOGINS (ID)
      on delete cascade
go

