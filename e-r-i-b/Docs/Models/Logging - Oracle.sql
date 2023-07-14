/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     28.07.2015 13:36:36                          */
/*==============================================================*/


drop package LOG_SERVICES_PKG
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_ADVERTISINGS_LOG'; 
    EXECUTE IMMEDIATE('drop index DXFK_ADVERTISINGS_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_ADVERTISING_LOG'; 
    EXECUTE IMMEDIATE('drop index I_ADVERTISING_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ADVERTISINGS_LOG'; 
    EXECUTE IMMEDIATE('drop table ADVERTISINGS_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ADVERTISINGS_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_ADVERTISINGS_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_AGREGATE_BUS_DOC_MONITORING'; 
    EXECUTE IMMEDIATE('drop index I_AGREGATE_BUS_DOC_MONITORING');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'AGREGATE_BUS_DOC_MONITORING'; 
    EXECUTE IMMEDIATE('drop table AGREGATE_BUS_DOC_MONITORING cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_ALF_RECATEGORIZATION_LOG'; 
    EXECUTE IMMEDIATE('drop index I_ALF_RECATEGORIZATION_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ALF_RECATEGORIZATION_LOG'; 
    EXECUTE IMMEDIATE('drop table ALF_RECATEGORIZATION_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ALF_RECATEGORIZATION_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_ALF_RECATEGORIZATION_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'BUS_DOCUMENTS_MONITORING_STATE'; 
    EXECUTE IMMEDIATE('drop table BUS_DOCUMENTS_MONITORING_STATE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_CHANGE_CARD_OP_LOG'; 
    EXECUTE IMMEDIATE('drop index IDX_CHANGE_CARD_OP_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CHANGE_CARD_OP_CATEGORY_LOG'; 
    EXECUTE IMMEDIATE('drop table CHANGE_CARD_OP_CATEGORY_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CHANGE_CARD_OP_CATEGORY_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_CHANGE_CARD_OP_CATEGORY_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_FIO_BIRTHDAY_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CL_FIO_BIRTHDAY_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_PK'; 
    EXECUTE IMMEDIATE('drop index CL_PK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_FIO_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CL_FIO_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_LOGIN_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CL_LOGIN_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_DI_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CL_DI_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_MESSAGE_DEMAND_ID_INDEX'; 
    EXECUTE IMMEDIATE('drop index CL_MESSAGE_DEMAND_ID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_SESSION_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CL_SESSION_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_OUID_INDEX'; 
    EXECUTE IMMEDIATE('drop index CL_OUID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_APP_SYST_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CL_APP_SYST_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CL_APP_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CL_APP_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CODLOG'; 
    EXECUTE IMMEDIATE('drop table CODLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CODLOG';
    EXECUTE IMMEDIATE('drop sequence  S_CODLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_CONTACT_SYNC_EXCEED_DATE'; 
    EXECUTE IMMEDIATE('drop index I_CONTACT_SYNC_EXCEED_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CONTACT_SYNC_COUNT_EXCEED_LOG'; 
    EXECUTE IMMEDIATE('drop table CONTACT_SYNC_COUNT_EXCEED_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_ACTIONLOG_UNIVERSAL_ID'; 
    EXECUTE IMMEDIATE('drop index CSA_ACTIONLOG_UNIVERSAL_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_ACTION_LOG'; 
    EXECUTE IMMEDIATE('drop table CSA_ACTION_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_ACTION_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_ACTION_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_LOG_UID_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_LOG_UID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_PROMO_ID_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_PROMO_ID_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_OUID_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_OUID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_MGUID_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_MGUID_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_MESSAGE_DEMAND_ID_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_MESSAGE_DEMAND_ID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_LOGIN_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_LOGIN_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_IP_DATA_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_IP_DATA_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_FIO_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_FIO_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_DI_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_DI_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_APP_SYST_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_APP_SYST_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_APP_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_APP_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CL_PK'; 
    EXECUTE IMMEDIATE('drop index CSA_CL_PK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_CODLOG'; 
    EXECUTE IMMEDIATE('drop table CSA_CODLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_CODLOG';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_CODLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_MESSAGE_TRANSLATE'; 
    EXECUTE IMMEDIATE('drop table CSA_MESSAGE_TRANSLATE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_MESSAGE_TRANSLATE';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_MESSAGE_TRANSLATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_SL_LOG_UID_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_SL_LOG_UID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_SL_FIO_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_SL_FIO_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_SL_LOGIN_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_SL_LOGIN_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_SL_IP_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_SL_IP_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_SL_DI_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_SL_DI_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_SL_APP_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_SL_APP_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_SL_PK'; 
    EXECUTE IMMEDIATE('drop index CSA_SL_PK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_SYSTEMLOG'; 
    EXECUTE IMMEDIATE('drop table CSA_SYSTEMLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_SYSTEMLOG';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_SYSTEMLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'EXCEPTIONS_LOG'; 
    EXECUTE IMMEDIATE('drop table EXCEPTIONS_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'EXCEPTION_COUNTERS'; 
    EXECUTE IMMEDIATE('drop table EXCEPTION_COUNTERS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IND_EXCEPTION_ENTRY_HASH'; 
    EXECUTE IMMEDIATE('drop index IND_EXCEPTION_ENTRY_HASH');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'EXCEPTION_ENTRY'; 
    EXECUTE IMMEDIATE('drop table EXCEPTION_ENTRY cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_EXCEPTION_ENTRY';
    EXECUTE IMMEDIATE('drop sequence  S_EXCEPTION_ENTRY');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FILTER_OUTCOME_PERIOD_INDEX'; 
    EXECUTE IMMEDIATE('drop index FILTER_OUTCOME_PERIOD_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FILTER_OUTCOME_PERIOD_LOG'; 
    EXECUTE IMMEDIATE('drop table FILTER_OUTCOME_PERIOD_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_FILTER_OUTCOME_PERIOD_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_FILTER_OUTCOME_PERIOD_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_FIO_BIRTHDAY_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index FCL_FIO_BIRTHDAY_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_PK'; 
    EXECUTE IMMEDIATE('drop index FCL_PK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_FIO_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index FCL_FIO_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_LOGIN_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index FCL_LOGIN_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_DI_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index FCL_DI_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_MESSAGE_DEMAND_ID_INDEX'; 
    EXECUTE IMMEDIATE('drop index FCL_MESSAGE_DEMAND_ID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_SESSION_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index FCL_SESSION_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_OUID_INDEX'; 
    EXECUTE IMMEDIATE('drop index FCL_OUID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_APP_SYST_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index FCL_APP_SYST_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'FCL_APP_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index FCL_APP_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'FINANCIAL_CODLOG'; 
    EXECUTE IMMEDIATE('drop table FINANCIAL_CODLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_FINANCIAL_CODLOG';
    EXECUTE IMMEDIATE('drop sequence  S_FINANCIAL_CODLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_CL_PK'; 
    EXECUTE IMMEDIATE('drop index I_G_CL_PK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_CL_FIO_DATE'; 
    EXECUTE IMMEDIATE('drop index I_G_CL_FIO_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_CL_PHONE_DATE'; 
    EXECUTE IMMEDIATE('drop index I_G_CL_PHONE_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GUEST_CODLOG'; 
    EXECUTE IMMEDIATE('drop table GUEST_CODLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_CSA_CL_PHONE_DATE'; 
    EXECUTE IMMEDIATE('drop index I_G_CSA_CL_PHONE_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_CSA_CL_FIO'; 
    EXECUTE IMMEDIATE('drop index I_G_CSA_CL_FIO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'G_CSA_CL_PK'; 
    EXECUTE IMMEDIATE('drop index G_CSA_CL_PK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GUEST_CSA_CODLOG'; 
    EXECUTE IMMEDIATE('drop table GUEST_CSA_CODLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_CSA_SL_FIO'; 
    EXECUTE IMMEDIATE('drop index I_G_CSA_SL_FIO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_CSA_SL_PHONE_DATE'; 
    EXECUTE IMMEDIATE('drop index I_G_CSA_SL_PHONE_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GUEST_CSA_SYSTEMLOG'; 
    EXECUTE IMMEDIATE('drop table GUEST_CSA_SYSTEMLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_SL_FIO_DATE'; 
    EXECUTE IMMEDIATE('drop index I_G_SL_FIO_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_SL_PHONE_DATE'; 
    EXECUTE IMMEDIATE('drop index I_G_SL_PHONE_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GUEST_SYSTEMLOG'; 
    EXECUTE IMMEDIATE('drop table GUEST_SYSTEMLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_UL_FIO_DATE'; 
    EXECUTE IMMEDIATE('drop index I_G_UL_FIO_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_UL_PK'; 
    EXECUTE IMMEDIATE('drop index I_G_UL_PK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_G_UL_PHONE_DATE'; 
    EXECUTE IMMEDIATE('drop index I_G_UL_PHONE_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GUEST_USERLOG'; 
    EXECUTE IMMEDIATE('drop table GUEST_USERLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MESSAGE_TRANSLATE'; 
    EXECUTE IMMEDIATE('drop table MESSAGE_TRANSLATE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MESSAGE_TRANSLATE';
    EXECUTE IMMEDIATE('drop sequence  S_MESSAGE_TRANSLATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MONITORING_BUSINESS_OPERATION'; 
    EXECUTE IMMEDIATE('drop table MONITORING_BUSINESS_OPERATION cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MONITORING_USER_LOGIN'; 
    EXECUTE IMMEDIATE('drop table MONITORING_USER_LOGIN cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_OFFER_NOTIF_LOG'; 
    EXECUTE IMMEDIATE('drop index I_OFFER_NOTIF_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'OFFER_NOTIFICATIONS_LOG'; 
    EXECUTE IMMEDIATE('drop table OFFER_NOTIFICATIONS_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_OFFER_NOTIFICATIONS_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_OFFER_NOTIFICATIONS_LOG');
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
    WHERE UPPER (index_name) = 'I_PUSH_DEVICES_STATES_LOG_DATE'; 
    EXECUTE IMMEDIATE('drop index I_PUSH_DEVICES_STATES_LOG_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PUSH_DEVICES_STATES_LOG'; 
    EXECUTE IMMEDIATE('drop table PUSH_DEVICES_STATES_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PUSH_DEVICES_STATES_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_PUSH_DEVICES_STATES_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'QUICK_PAYMENT_STATISTICS'; 
    EXECUTE IMMEDIATE('drop index QUICK_PAYMENT_STATISTICS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'QUICK_PAYMENT_PANELS_LOG'; 
    EXECUTE IMMEDIATE('drop table QUICK_PAYMENT_PANELS_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_QUICK_PAYMENT_PANELS_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_QUICK_PAYMENT_PANELS_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'REGISTRATION_FAILURE_REASONS'; 
    EXECUTE IMMEDIATE('drop table REGISTRATION_FAILURE_REASONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_REGISTRATION_FAILURE_REASONS';
    EXECUTE IMMEDIATE('drop sequence  S_REGISTRATION_FAILURE_REASONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_REQ_CARD_BY_PHONE_LOG_LOGIN'; 
    EXECUTE IMMEDIATE('drop index I_REQ_CARD_BY_PHONE_LOG_LOGIN');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_REQUEST_CARD_BY_PHONE_LOG'; 
    EXECUTE IMMEDIATE('drop index I_REQUEST_CARD_BY_PHONE_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'REQUEST_CARD_BY_PHONE_LOG'; 
    EXECUTE IMMEDIATE('drop table REQUEST_CARD_BY_PHONE_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_REQUEST_CARD_BY_PHONE_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_REQUEST_CARD_BY_PHONE_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SERV_PAY_STATISTICS_DATE_IDX'; 
    EXECUTE IMMEDIATE('drop index SERV_PAY_STATISTICS_DATE_IDX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SMS_SERVICE_PAYMENT_STATISTICS'; 
    EXECUTE IMMEDIATE('drop table SMS_SERVICE_PAYMENT_STATISTICS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SL_FIO_BIRTHDAY_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index SL_FIO_BIRTHDAY_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SL_PK'; 
    EXECUTE IMMEDIATE('drop index SL_PK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SL_FIO_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index SL_FIO_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SL_IP_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index SL_IP_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SL_SESSION_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index SL_SESSION_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SL_LOGIN_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index SL_LOGIN_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SL_DI_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index SL_DI_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'SL_APP_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index SL_APP_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SYSTEMLOG'; 
    EXECUTE IMMEDIATE('drop table SYSTEMLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_SYSTEMLOG';
    EXECUTE IMMEDIATE('drop sequence  S_SYSTEMLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UL_FIO_BIRTHDAY_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index UL_FIO_BIRTHDAY_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UL_PK'; 
    EXECUTE IMMEDIATE('drop index UL_PK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UL_FIO_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index UL_FIO_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UL_LOGIN_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index UL_LOGIN_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UL_SESSION_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index UL_SESSION_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UL_DI_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index UL_DI_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UL_APP_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index UL_APP_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UL_SUCCESS_DESCR_INDEX'; 
    EXECUTE IMMEDIATE('drop index UL_SUCCESS_DESCR_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UL_FOR_REPORTS_INDEX'; 
    EXECUTE IMMEDIATE('drop index UL_FOR_REPORTS_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'USERLOG'; 
    EXECUTE IMMEDIATE('drop table USERLOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_USERLOG';
    EXECUTE IMMEDIATE('drop sequence  S_USERLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_USER_MESSAGES_LOG_DATE_TYPE'; 
    EXECUTE IMMEDIATE('drop index I_USER_MESSAGES_LOG_DATE_TYPE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'USER_MESSAGES_LOG'; 
    EXECUTE IMMEDIATE('drop table USER_MESSAGES_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_USER_MESSAGES_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_USER_MESSAGES_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_USER_NOTIFICATION_LOG_DATE'; 
    EXECUTE IMMEDIATE('drop index I_USER_NOTIFICATION_LOG_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'USER_NOTIFICATION_LOG'; 
    EXECUTE IMMEDIATE('drop table USER_NOTIFICATION_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_USER_NOTIFICATION_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_USER_NOTIFICATION_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_GUEST_CODLOG'; 
    EXECUTE IMMEDIATE('drop sequence S_GUEST_CODLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_GUEST_CSA_CODLOG'; 
    EXECUTE IMMEDIATE('drop sequence S_GUEST_CSA_CODLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_GUEST_CSA_SYSTEMLOG'; 
    EXECUTE IMMEDIATE('drop sequence S_GUEST_CSA_SYSTEMLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_GUEST_SYSTEMLOG'; 
    EXECUTE IMMEDIATE('drop sequence S_GUEST_SYSTEMLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_GUEST_USERLOG'; 
    EXECUTE IMMEDIATE('drop sequence S_GUEST_USERLOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

create sequence S_GUEST_CODLOG
cache 15000
go

create sequence S_GUEST_CSA_CODLOG
cache 15000
go

create sequence S_GUEST_CSA_SYSTEMLOG
cache 15000
go

create sequence S_GUEST_SYSTEMLOG
cache 15000
go

create sequence S_GUEST_USERLOG
cache 15000
go

/*==============================================================*/
/* Table: ADVERTISINGS_LOG                                      */
/*==============================================================*/
create table ADVERTISINGS_LOG 
(
   ID                   INTEGER              not null,
   ADVERTISING_ID       INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   TYPE                 VARCHAR2(20)         not null
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-06-2014','DD-MM-YYYY')))
go

create sequence S_ADVERTISINGS_LOG
go

/*==============================================================*/
/* Index: I_ADVERTISING_LOG                                     */
/*==============================================================*/
create index I_ADVERTISING_LOG on ADVERTISINGS_LOG (
   START_DATE ASC
)
local
go

/*==============================================================*/
/* Index: DXFK_ADVERTISINGS_LOG                                 */
/*==============================================================*/
create index DXFK_ADVERTISINGS_LOG on ADVERTISINGS_LOG (
   ADVERTISING_ID ASC
)
local
go

/*==============================================================*/
/* Table: AGREGATE_BUS_DOC_MONITORING                           */
/*==============================================================*/
create table AGREGATE_BUS_DOC_MONITORING 
(
   REPORT_DATE          TIMESTAMP            not null,
   PROVIDER_UUID        VARCHAR2(32),
   DOCUMENT_TYPE        VARCHAR2(30)         not null,
   ACCOUNT_TYPE         VARCHAR2(20),
   AMOUNT               NUMBER(15,4),
   AMOUNT_CUR           CHAR(3),
   APPLICATION          VARCHAR2(20)         not null,
   TB                   VARCHAR2(4),
   PLATFORM             VARCHAR2(20),
   STATE_CODE           VARCHAR2(20)         not null,
   NODE_ID              INTEGER              not null,
   COUNT                INTEGER              not null
)
partition by range
 (REPORT_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-04-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_AGREGATE_BUS_DOC_MONITORING                         */
/*==============================================================*/
create index I_AGREGATE_BUS_DOC_MONITORING on AGREGATE_BUS_DOC_MONITORING (
   DOCUMENT_TYPE ASC,
   REPORT_DATE ASC
)
local
go

/*==============================================================*/
/* Table: ALF_RECATEGORIZATION_LOG                              */
/*==============================================================*/
create table ALF_RECATEGORIZATION_LOG 
(
   ID                   INTEGER              not null,
   LOG_DATE             TIMESTAMP            not null,
   DESCRIPTION          VARCHAR2(100)        not null,
   MCC_CODE             INTEGER              not null,
   ORIGINAL_CATEGORY    VARCHAR2(100)        not null,
   NEW_CATEGORY         VARCHAR2(100)        not null,
   OPERATION_TYPE       VARCHAR2(6)          not null,
   OPERATIONS_COUNT     INTEGER,
   constraint PK_ALF_RECATEGORIZATION_LOG primary key (ID)
)
partition by range
 (LOG_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-01-2014','DD-MM-YYYY')))
go

create sequence S_ALF_RECATEGORIZATION_LOG
go

/*==============================================================*/
/* Index: I_ALF_RECATEGORIZATION_LOG                            */
/*==============================================================*/
create index I_ALF_RECATEGORIZATION_LOG on ALF_RECATEGORIZATION_LOG (
   LOG_DATE ASC
)
local
go

/*==============================================================*/
/* Table: BUS_DOCUMENTS_MONITORING_STATE                        */
/*==============================================================*/
create table BUS_DOCUMENTS_MONITORING_STATE 
(
   REPORT_DATE          TIMESTAMP            not null,
   STATE                VARCHAR2(10)         not null,
   STATE_DESCR          VARCHAR2(200)
)
go

/*==============================================================*/
/* Table: CHANGE_CARD_OP_CATEGORY_LOG                           */
/*==============================================================*/
create table CHANGE_CARD_OP_CATEGORY_LOG 
(
   ID                   INTEGER              not null,
   CHANGE_DATE          TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   VSP                  VARCHAR2(7),
   FIO                  VARCHAR2(100)        not null,
   OPERATION_NAME       VARCHAR2(100),
   MCC_CODE             INTEGER,
   AMOUNT               NUMBER(15,4)         not null,
   PARENT_CATEGORY      VARCHAR2(100)        not null,
   NEW_CATEGORIES       VARCHAR2(100),
   NEW_CATEGORIES_COUNT INTEGER              not null,
   constraint PK_CHANGE_CARD_OP_CATEGORY_LOG primary key (ID)
)
partition by range (CHANGE_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
    --Создаем 1 начальную партицию до 1 января
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY'))
)


go

create sequence S_CHANGE_CARD_OP_CATEGORY_LOG
go

/*==============================================================*/
/* Index: IDX_CHANGE_CARD_OP_LOG                                */
/*==============================================================*/
create index IDX_CHANGE_CARD_OP_LOG on CHANGE_CARD_OP_CATEGORY_LOG (
   CHANGE_DATE DESC,
   TB ASC,
   VSP ASC
)
local
go

/*==============================================================*/
/* Table: CODLOG                                                */
/*==============================================================*/
create table CODLOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   LOGIN_ID             INTEGER,
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   OPERATION_UID        VARCHAR2(32),
   NODE_ID              INTEGER,
   TB                   varchar2(4),
   OSB                  varchar2(4),
   VSP                  varchar2(7),
   THREAD_INFO          INTEGER,
   ADD_INFO             VARCHAR2(128)
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition P_START values less than (to_date('01-12-2013','DD-MM-YYYY')))
go

create sequence S_CODLOG
go

/*==============================================================*/
/* Index: CL_APP_DATE_INDEX                                     */
/*==============================================================*/
create index CL_APP_DATE_INDEX on CODLOG (
   APPLICATION ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CL_APP_SYST_DATE_INDEX                                */
/*==============================================================*/
create index CL_APP_SYST_DATE_INDEX on CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CL_OUID_INDEX                                         */
/*==============================================================*/
create index CL_OUID_INDEX on CODLOG (
   OPERATION_UID ASC
)
go

/*==============================================================*/
/* Index: CL_SESSION_DATE_INDEX                                 */
/*==============================================================*/
create index CL_SESSION_DATE_INDEX on CODLOG (
   SESSION_ID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CL_MESSAGE_DEMAND_ID_INDEX                            */
/*==============================================================*/
create index CL_MESSAGE_DEMAND_ID_INDEX on CODLOG (
   MESSAGE_DEMAND_ID ASC
)
go

/*==============================================================*/
/* Index: CL_DI_DATE_INDEX                                      */
/*==============================================================*/
create index CL_DI_DATE_INDEX on CODLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CL_LOGIN_DATE_INDEX                                   */
/*==============================================================*/
create index CL_LOGIN_DATE_INDEX on CODLOG (
   LOGIN_ID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CL_FIO_DATE_INDEX                                     */
/*==============================================================*/
create index CL_FIO_DATE_INDEX on CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CL_PK                                                 */
/*==============================================================*/
create index CL_PK on CODLOG (
   ID ASC
)
reverse
go

/*==============================================================*/
/* Index: CL_FIO_BIRTHDAY_DATE_INDEX                            */
/*==============================================================*/
create index CL_FIO_BIRTHDAY_DATE_INDEX on CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE ASC
)
local
go

/*==============================================================*/
/* Table: CONTACT_SYNC_COUNT_EXCEED_LOG                         */
/*==============================================================*/
create table CONTACT_SYNC_COUNT_EXCEED_LOG 
(
   LOGIN_ID             INTEGER              not null,
   NAME                 VARCHAR2(256)        not null,
   DOCUMENT             VARCHAR2(64)         not null,
   BIRTHDAY             TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   SYNC_DATE            TIMESTAMP            not null,
   MESSAGE              CLOB                 not null
)
partition by range
 (SYNC_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-06-2014','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_CONTACT_SYNC_EXCEED_DATE                            */
/*==============================================================*/
create index I_CONTACT_SYNC_EXCEED_DATE on CONTACT_SYNC_COUNT_EXCEED_LOG (
   SYNC_DATE ASC
)
local
go

/*==============================================================*/
/* Table: CSA_ACTION_LOG                                        */
/*==============================================================*/
create table CSA_ACTION_LOG 
(
   START_DATE           TIMESTAMP            not null,
   OPERATION_TYPE       VARCHAR2(40)         not null,
   IDENTIFICATION_TYPE  VARCHAR2(15)         not null,
   IDENTIFICATION_PARAM VARCHAR2(350),
   CARD_NUMBER          VARCHAR2(20),
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   IP_ADDRESS           VARCHAR2(15),
   BIRTHDATE            TIMESTAMP            not null,
   ERROR_MESSAGE        CLOB,
   CONFIRMATION_TYPE    VARCHAR2(35),
   EMPLOYEE_FIO         VARCHAR2(300),
   EMPLOYEE_LOGIN       VARCHAR2(50),
   TB                   VARCHAR2(4)          not null,
   LOG_UID              VARCHAR2(32)         not null
)
partition by range(START_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
     partition P_START values less than (to_date('1-12-2013', 'DD-MM-YYYY'))
)

go

create sequence S_CSA_ACTION_LOG
go

/*==============================================================*/
/* Index: CSA_ACTIONLOG_UNIVERSAL_ID                            */
/*==============================================================*/
create index CSA_ACTIONLOG_UNIVERSAL_ID on CSA_ACTION_LOG (
   PASSPORT ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Table: CSA_CODLOG                                            */
/*==============================================================*/
create table CSA_CODLOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   OPERATION_UID        VARCHAR2(32),
   LOGIN                VARCHAR2(32),
   DEPARTMENT_CODE      VARCHAR2(4),
   MGUID                VARCHAR2(32),
   PROMOTER_ID          VARCHAR2(100),
   IP_ADDRESS           VARCHAR2(15),
   ERROR_CODE           VARCHAR2(10),
   LOG_UID              VARCHAR2(32),
   THREAD_INFO          INTEGER,
   ADD_INFO             VARCHAR2(128)
)
partition by range(START_DATE) interval ( numtoyminterval(1,'MONTH'))
(
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY'))
)
go

create sequence S_CSA_CODLOG
go

/*==============================================================*/
/* Index: CSA_CL_PK                                             */
/*==============================================================*/
create index CSA_CL_PK on CSA_CODLOG (
   ID ASC
)
reverse
go

/*==============================================================*/
/* Index: CSA_CL_APP_DATE_INDEX                                 */
/*==============================================================*/
create index CSA_CL_APP_DATE_INDEX on CSA_CODLOG (
   APPLICATION ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_CL_APP_SYST_DATE_INDEX                            */
/*==============================================================*/
create index CSA_CL_APP_SYST_DATE_INDEX on CSA_CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_CL_DI_DATE_INDEX                                  */
/*==============================================================*/
create index CSA_CL_DI_DATE_INDEX on CSA_CODLOG (
   replace(DOC_NUMBER,' ','') ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_CL_FIO_INDEX                                      */
/*==============================================================*/
create index CSA_CL_FIO_INDEX on CSA_CODLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_CL_IP_DATA_INDEX                                  */
/*==============================================================*/
create index CSA_CL_IP_DATA_INDEX on CSA_CODLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_CL_LOGIN_DATE_INDEX                               */
/*==============================================================*/
create index CSA_CL_LOGIN_DATE_INDEX on CSA_CODLOG (
   LOGIN ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_CL_MESSAGE_DEMAND_ID_INDEX                        */
/*==============================================================*/
create index CSA_CL_MESSAGE_DEMAND_ID_INDEX on CSA_CODLOG (
   MESSAGE_DEMAND_ID ASC
)
go

/*==============================================================*/
/* Index: CSA_CL_MGUID_DATE_INDEX                               */
/*==============================================================*/
create index CSA_CL_MGUID_DATE_INDEX on CSA_CODLOG (
   MGUID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_CL_OUID_INDEX                                     */
/*==============================================================*/
create index CSA_CL_OUID_INDEX on CSA_CODLOG (
   OPERATION_UID ASC
)
go

/*==============================================================*/
/* Index: CSA_CL_PROMO_ID_DATE_INDEX                            */
/*==============================================================*/
create index CSA_CL_PROMO_ID_DATE_INDEX on CSA_CODLOG (
   PROMOTER_ID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_CL_LOG_UID_INDEX                                  */
/*==============================================================*/
create index CSA_CL_LOG_UID_INDEX on CSA_CODLOG (
   LOG_UID ASC
)
go

/*==============================================================*/
/* Table: CSA_GUEST_ACTION_LOG                                  */
/*==============================================================*/
create table CSA_GUEST_ACTION_LOG 
(
   START_DATE           TIMESTAMP            not null,
   OPERATION_TYPE       VARCHAR2(40)         not null,
   IDENTIFICATION_TYPE  VARCHAR2(15)         not null,
   IDENTIFICATION_PARAM VARCHAR2(350),
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   BIRTHDATE            TIMESTAMP,
   ERROR_MESSAGE        CLOB,
   CONFIRMATION_TYPE    VARCHAR2(35),
   TB                   VARCHAR2(4),
   LOG_UID              VARCHAR2(32),
   PASSPORT             VARCHAR2(100),
   IP_ADDRESS           VARCHAR2(15),
   PHONE_NUMBER         VARCHAR(20)
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1, 'MONTH'))
 (partition
         P_START
        values less than (to_date('1-07-2015', 'DD-MM-YYYY')))
/

/*==============================================================*/
/* Index: CSA_GAL_FIO_DATE_IDX                                  */
/*==============================================================*/
create index CSA_GAL_FIO_DATE_IDX on CSA_GUEST_ACTION_LOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
/

/*==============================================================*/
/* Index: CSA_GAL_PHONE_IDX                                     */
/*==============================================================*/
create index CSA_GAL_PHONE_IDX on CSA_GUEST_ACTION_LOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
/

/*==============================================================*/
/* Table: CSA_MESSAGE_TRANSLATE                                 */
/*==============================================================*/
create table CSA_MESSAGE_TRANSLATE 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(255)        not null,
   TRANSLATE            VARCHAR2(255)        not null,
   TYPE                 CHAR(1)              not null,
   constraint PK_CSA_MESSAGE_TRANSLATE primary key (ID),
   constraint AK_CODE_CSA_MESS unique (CODE)
)

go

create sequence S_CSA_MESSAGE_TRANSLATE
go

/*==============================================================*/
/* Table: CSA_SYSTEMLOG                                         */
/*==============================================================*/
create table CSA_SYSTEMLOG 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   LOGIN_ID             INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB,
   SESSION_ID           VARCHAR2(64),
   IP_ADDRESS           VARCHAR2(15),
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   THREAD_INFO          INTEGER,
   LOGIN                VARCHAR2(32),
   DEPARTMENT_CODE      VARCHAR2(4),
   LOG_UID              VARCHAR2(32),
   ADD_INFO             VARCHAR2(128)
)
partition by range ( START_DATE ) interval ( numtoyminterval(1,'MONTH') )
(
   partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY'))
)

go

create sequence S_CSA_SYSTEMLOG
go

/*==============================================================*/
/* Index: CSA_SL_PK                                             */
/*==============================================================*/
create index CSA_SL_PK on CSA_SYSTEMLOG (
   ID ASC
)
reverse
go

/*==============================================================*/
/* Index: CSA_SL_APP_DATE_INDEX                                 */
/*==============================================================*/
create index CSA_SL_APP_DATE_INDEX on CSA_SYSTEMLOG (
   APPLICATION ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_SL_DI_DATE_INDEX                                  */
/*==============================================================*/
create index CSA_SL_DI_DATE_INDEX on CSA_SYSTEMLOG (
   replace(DOC_NUMBER,' ','') ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_SL_IP_DATE_INDEX                                  */
/*==============================================================*/
create index CSA_SL_IP_DATE_INDEX on CSA_SYSTEMLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_SL_LOGIN_DATE_INDEX                               */
/*==============================================================*/
create index CSA_SL_LOGIN_DATE_INDEX on CSA_SYSTEMLOG (
   LOGIN ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_SL_FIO_INDEX                                      */
/*==============================================================*/
create index CSA_SL_FIO_INDEX on CSA_SYSTEMLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: CSA_SL_LOG_UID_INDEX                                  */
/*==============================================================*/
create index CSA_SL_LOG_UID_INDEX on CSA_SYSTEMLOG (
   LOG_UID ASC
)
go

/*==============================================================*/
/* Table: EXCEPTIONS_LOG                                        */
/*==============================================================*/
create table EXCEPTIONS_LOG 
(
   CREATION_DATE        TIMESTAMP            not null,
   HASH                 VARCHAR2(277)        not null
)
partition by range
 (CREATION_DATE)
    interval (NUMTODSINTERVAL(1,'day'))
 (partition
         P_FIRST
        values less than (to_date('1-11-2013','DD-MM-YYYY')))
go

/*==============================================================*/
/* Table: EXCEPTION_COUNTERS                                    */
/*==============================================================*/
create table EXCEPTION_COUNTERS 
(
   EXCEPTION_HASH       VARCHAR2(277)        not null,
   EXCEPTION_DATE       TIMESTAMP            not null,
   EXCEPTION_COUNT      INTEGER              not null,
   constraint PK_EXCEPTION_COUNTERS primary key (EXCEPTION_HASH, EXCEPTION_DATE)
)
go

/*==============================================================*/
/* Table: EXCEPTION_ENTRY                                       */
/*==============================================================*/
create table EXCEPTION_ENTRY 
(
   ID                   INTEGER              not null,
   KIND                 CHAR(1)              not null,
   HASH                 VARCHAR2(277)        not null,
   OPERATION            VARCHAR2(160),
   APPLICATION          VARCHAR2(20),
   DETAIL               CLOB                 not null,
   SYSTEM               VARCHAR2(64),
   ERROR_CODE           VARCHAR2(20),
   constraint PK_EXCEPTION_ENTRY primary key (ID)
)

go

create sequence S_EXCEPTION_ENTRY
go

/*==============================================================*/
/* Index: IND_EXCEPTION_ENTRY_HASH                              */
/*==============================================================*/
create unique index IND_EXCEPTION_ENTRY_HASH on EXCEPTION_ENTRY (
   HASH ASC
)
go

/*==============================================================*/
/* Table: FILTER_OUTCOME_PERIOD_LOG                             */
/*==============================================================*/
create table FILTER_OUTCOME_PERIOD_LOG 
(
   ID                   INTEGER              not null,
   FILTER_DATE          TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   PERIOD_TYPE          VARCHAR2(30)         not null,
   IS_DEFAULT           CHAR(1)              not null,
   constraint PK_FILTER_OUTCOME_PERIOD_LOG primary key (ID)
)
partition by range(FILTER_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
	partition P_START values less than (to_date('1-12-2013', 'DD-MM-YYYY'))
)
go

create sequence S_FILTER_OUTCOME_PERIOD_LOG
go

/*==============================================================*/
/* Index: FILTER_OUTCOME_PERIOD_INDEX                           */
/*==============================================================*/
create index FILTER_OUTCOME_PERIOD_INDEX on FILTER_OUTCOME_PERIOD_LOG (
   FILTER_DATE ASC,
   PERIOD_TYPE ASC,
   IS_DEFAULT ASC
)
local
go

/*==============================================================*/
/* Table: FINANCIAL_CODLOG                                      */
/*==============================================================*/
create table FINANCIAL_CODLOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(40)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(80)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   LOGIN_ID             INTEGER,
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   OPERATION_UID        VARCHAR2(32),
   NODE_ID              INTEGER,
   TB                   VARCHAR2(4),
   OSB                  VARCHAR2(4),
   VSP                  VARCHAR2(7),
   THREAD_INFO          INTEGER,
   ADD_INFO             VARCHAR2(128)
)
partition by range("START_DATE") interval ( numtoyminterval(1,'MONTH') ) subpartition by hash("ID") subpartitions 16
(
      partition "P_START" values less than (timestamp' 2014-01-01 00:00:00')
)
go

create sequence S_FINANCIAL_CODLOG
go

/*==============================================================*/
/* Index: FCL_APP_DATE_INDEX                                    */
/*==============================================================*/
create index FCL_APP_DATE_INDEX on FINANCIAL_CODLOG (
   APPLICATION ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: FCL_APP_SYST_DATE_INDEX                               */
/*==============================================================*/
create index FCL_APP_SYST_DATE_INDEX on FINANCIAL_CODLOG (
   APPLICATION ASC,
   SYST ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: FCL_OUID_INDEX                                        */
/*==============================================================*/
create index FCL_OUID_INDEX on FINANCIAL_CODLOG (
   OPERATION_UID ASC
)
local
go

/*==============================================================*/
/* Index: FCL_SESSION_DATE_INDEX                                */
/*==============================================================*/
create index FCL_SESSION_DATE_INDEX on FINANCIAL_CODLOG (
   SESSION_ID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: FCL_MESSAGE_DEMAND_ID_INDEX                           */
/*==============================================================*/
create index FCL_MESSAGE_DEMAND_ID_INDEX on FINANCIAL_CODLOG (
   MESSAGE_DEMAND_ID ASC
)
local
go

/*==============================================================*/
/* Index: FCL_DI_DATE_INDEX                                     */
/*==============================================================*/
create index FCL_DI_DATE_INDEX on FINANCIAL_CODLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: FCL_LOGIN_DATE_INDEX                                  */
/*==============================================================*/
create index FCL_LOGIN_DATE_INDEX on FINANCIAL_CODLOG (
   LOGIN_ID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: FCL_FIO_DATE_INDEX                                    */
/*==============================================================*/
create index FCL_FIO_DATE_INDEX on FINANCIAL_CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: FCL_PK                                                */
/*==============================================================*/
create index FCL_PK on FINANCIAL_CODLOG (
   ID ASC
)
local
go

/*==============================================================*/
/* Index: FCL_FIO_BIRTHDAY_DATE_INDEX                           */
/*==============================================================*/
create index FCL_FIO_BIRTHDAY_DATE_INDEX on FINANCIAL_CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE ASC
)
local
go

/*==============================================================*/
/* Table: GUEST_CODLOG                                          */
/*==============================================================*/
create table GUEST_CODLOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   GUEST_CODE           INTEGER              not null,
   SESSION_ID           VARCHAR2(64)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(25),
   DOC_SERIES           VARCHAR2(25),
   TB                   VARCHAR2(4),
   BIRTHDATE            TIMESTAMP,
   NODE_ID              INTEGER              not null,
   THREAD_INFO          INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_LOGIN          VARCHAR2(50)
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-01-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_G_CL_PHONE_DATE                                     */
/*==============================================================*/
create index I_G_CL_PHONE_DATE on GUEST_CODLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CL_FIO_DATE                                       */
/*==============================================================*/
create index I_G_CL_FIO_DATE on GUEST_CODLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CL_PK                                             */
/*==============================================================*/
create index I_G_CL_PK on GUEST_CODLOG (
   ID ASC
)
reverse
go

/*==============================================================*/
/* Table: GUEST_CSA_CODLOG                                      */
/*==============================================================*/
create table GUEST_CSA_CODLOG 
(
   ID                   INTEGER              not null,
   START_DATE           TIMESTAMP            not null,
   EXECUTION_TIME       INTEGER,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE_TYPE         VARCHAR2(80)         not null,
   MESSAGE_DEMAND_ID    VARCHAR2(40)         not null,
   MESSAGE_DEMAND       CLOB                 not null,
   MESSAGE_ANSWER_ID    VARCHAR2(40),
   MESSAGE_ANSWER       CLOB,
   SYST                 VARCHAR2(10)         not null,
   SESSION_ID           VARCHAR2(64),
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(25),
   DOC_SERIES           VARCHAR2(25),
   BIRTHDATE            TIMESTAMP,
   DEPARTMENT_CODE      VARCHAR2(4),
   OPERATION_UID        VARCHAR2(32)         not null,
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_CODE           INTEGER              not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   ERROR_CODE           VARCHAR2(10),
   THREAD_INFO          INTEGER              not null,
   LOG_UID              VARCHAR2(32),
   ADD_INFO             VARCHAR2(128)
)
partition by range
 (START_DATE)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('1-01-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: G_CSA_CL_PK                                           */
/*==============================================================*/
create unique index G_CSA_CL_PK on GUEST_CSA_CODLOG (
   ID ASC
)
reverse
go

/*==============================================================*/
/* Index: I_G_CSA_CL_FIO                                        */
/*==============================================================*/
create index I_G_CSA_CL_FIO on GUEST_CSA_CODLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_CL_PHONE_DATE                                 */
/*==============================================================*/
create index I_G_CSA_CL_PHONE_DATE on GUEST_CSA_CODLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Table: GUEST_CSA_SYSTEMLOG                                   */
/*==============================================================*/
create table GUEST_CSA_SYSTEMLOG 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB                 not null,
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(25),
   DOC_SERIES           VARCHAR2(25),
   BIRTHDATE            TIMESTAMP,
   DEPARTMENT_CODE      VARCHAR2(4),
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   THREAD_INFO          INTEGER              not null,
   LOG_UID              VARCHAR2(32),
   ADD_INFO             VARCHAR2(128)
)
partition by range
 (START_DATE)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('1-01-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_G_CSA_SL_PHONE_DATE                                 */
/*==============================================================*/
create index I_G_CSA_SL_PHONE_DATE on GUEST_CSA_SYSTEMLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_CSA_SL_FIO                                        */
/*==============================================================*/
create index I_G_CSA_SL_FIO on GUEST_CSA_SYSTEMLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Table: GUEST_SYSTEMLOG                                       */
/*==============================================================*/
create table GUEST_SYSTEMLOG 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB                 not null,
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DOC_NUMBER           VARCHAR2(25),
   DOC_SERIES           VARCHAR2(25),
   BIRTHDATE            TIMESTAMP,
   TB                   VARCHAR2(4),
   LOGIN                VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(16)         not null,
   THREAD_INFO          INTEGER              not null,
   NODE_ID              INTEGER              not null,
   DEPARTMENT_NAME      VARCHAR2(256)
)
partition by range
 (START_DATE)
    interval (numtoyminterval(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('1-01-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_G_SL_PHONE_DATE                                     */
/*==============================================================*/
create index I_G_SL_PHONE_DATE on GUEST_SYSTEMLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_SL_FIO_DATE                                       */
/*==============================================================*/
create index I_G_SL_FIO_DATE on GUEST_SYSTEMLOG (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Table: GUEST_USERLOG                                         */
/*==============================================================*/
create table GUEST_USERLOG 
(
   ID                   INTEGER              not null,
   DESCRIPTION          VARCHAR2(160),
   DESCRIPTION_KEY      VARCHAR2(160),
   SUCCESS              CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   GUEST_CODE           INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   OPERATION_KEY        VARCHAR2(300),
   PARAMETERS           VARCHAR2(4000),
   SESSION_ID           VARCHAR2(64)         not null,
   IP_ADDRESS           VARCHAR2(15)         not null,
   EXECUTION_TIME       INTEGER,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(25),
   DOC_SERIES           VARCHAR2(25),
   BIRTHDATE            TIMESTAMP,
   NODE_ID              INTEGER              not null,
   TB                   VARCHAR2(4),
   THREAD_INFO          INTEGER              not null,
   PHONE_NUMBER         VARCHAR2(16)         not null,
   GUEST_LOGIN          VARCHAR2(50)
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
         subpartition by hash ("ID") subpartitions 16
 (partition
         P_START
        values less than (to_date('01-01-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: I_G_UL_PHONE_DATE                                     */
/*==============================================================*/
create index I_G_UL_PHONE_DATE on GUEST_USERLOG (
   PHONE_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: I_G_UL_PK                                             */
/*==============================================================*/
create index I_G_UL_PK on GUEST_USERLOG (
   ID ASC
)
local
go

/*==============================================================*/
/* Index: I_G_UL_FIO_DATE                                       */
/*==============================================================*/
create index I_G_UL_FIO_DATE on GUEST_USERLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Table: MESSAGE_TRANSLATE                                     */
/*==============================================================*/
create table MESSAGE_TRANSLATE 
(
   ID                   INTEGER              not null,
   CODE                 VARCHAR2(255)        not null,
   TRANSLATE            VARCHAR2(255)        not null,
   TYPE                 CHAR(1)              not null,
   IS_NEW               CHAR(1)              default '0' not null,
   LOG_TYPE             VARCHAR2(20),
   constraint PK_MESSAGE_TRANSLATE primary key (ID),
   constraint AK_CODE_MESSAGE_ unique (CODE)
)

go

create sequence S_MESSAGE_TRANSLATE
go

/*==============================================================*/
/* Table: MONITORING_BUSINESS_OPERATION                         */
/*==============================================================*/
create table MONITORING_BUSINESS_OPERATION 
(
   START_DATE           TIMESTAMP            not null,
   CREATION_DATE        TIMESTAMP            not null,
   PROVIDER_UUID        VARCHAR2(32),
   OPERATION_TYPE       VARCHAR2(30)         not null,
   ACCOUNT_TYPE         VARCHAR2(20),
   AMOUNT               NUMBER(15,4),
   AMOUNT_CUR           CHAR(3),
   APPLICATION          VARCHAR2(20)         not null,
   TB                   VARCHAR2(4),
   PLATFORM             VARCHAR2(20),
   STATE_CODE           VARCHAR2(20)         not null,
   NODE_ID              INTEGER              not null
)
partition by range
 (START_DATE)
    interval (NUMTODSINTERVAL(1,'DAY'))
 (partition
         P_START
        values less than (to_date('01-03-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Table: MONITORING_USER_LOGIN                                 */
/*==============================================================*/
create table MONITORING_USER_LOGIN 
(
   START_DATE           TIMESTAMP            not null,
   APPLICATION          VARCHAR2(20)         not null,
   TB                   VARCHAR2(4),
   PLATFORM             VARCHAR2(20),
   NODE_ID              INTEGER              not null
)
partition by range
 (START_DATE)
    interval (NUMTODSINTERVAL(1,'DAY'))
 (partition
         P_START
        values less than (to_date('01-03-2015','DD-MM-YYYY')))
go

/*==============================================================*/
/* Table: OFFER_NOTIFICATIONS_LOG                               */
/*==============================================================*/
create table OFFER_NOTIFICATIONS_LOG 
(
   ID                   INTEGER              not null,
   NOTIFICATION_ID      INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   DISPLAY_DATE         TIMESTAMP            not null,
   TYPE                 VARCHAR2(12)         not null,
   NAME                 VARCHAR2(40)         not null
)
partition by range
 (DISPLAY_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-01-2014','DD-MM-YYYY')))
go

create sequence S_OFFER_NOTIFICATIONS_LOG
go

/*==============================================================*/
/* Index: I_OFFER_NOTIF_LOG                                     */
/*==============================================================*/
create index I_OFFER_NOTIF_LOG on OFFER_NOTIFICATIONS_LOG (
   TYPE ASC,
   DISPLAY_DATE ASC,
   ID ASC
)
local
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
/* Table: PUSH_DEVICES_STATES_LOG                               */
/*==============================================================*/
create table PUSH_DEVICES_STATES_LOG 
(
   ID                   number(20)           not null,
   CREATION_DATE        DATE                 not null,
   CLIENT_ID            varchar2(64)         not null,
   MGUID                varchar2(64)         not null,
   TYPE                 varchar2(1)          not null,
   constraint PK_PUSH_DEVICES_STATES_LOG primary key (ID)
)
partition by range (CREATION_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
    --Создаем 1 начальную партицию до 1 января
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY'))
)

go

create sequence S_PUSH_DEVICES_STATES_LOG
go

/*==============================================================*/
/* Index: I_PUSH_DEVICES_STATES_LOG_DATE                        */
/*==============================================================*/
create index I_PUSH_DEVICES_STATES_LOG_DATE on PUSH_DEVICES_STATES_LOG (
   CREATION_DATE ASC,
   TYPE ASC
)
local
go

/*==============================================================*/
/* Table: QUICK_PAYMENT_PANELS_LOG                              */
/*==============================================================*/
create table QUICK_PAYMENT_PANELS_LOG 
(
   ID                   INTEGER              not null,
   PANEL_ID             INTEGER              not null,
   TB                   VARCHAR2(4)          not null,
   TYPE                 VARCHAR2(16)         not null,
   START_DATE           TIMESTAMP            not null,
   AMOUNT               NUMBER(15,4),
   constraint PK_QUICK_PAYMENT_PANELS_LOG primary key (ID)
)

go

create sequence S_QUICK_PAYMENT_PANELS_LOG
go

/*==============================================================*/
/* Index: QUICK_PAYMENT_STATISTICS                              */
/*==============================================================*/
create index QUICK_PAYMENT_STATISTICS on QUICK_PAYMENT_PANELS_LOG (
   TB ASC,
   START_DATE ASC
)
go

/*==============================================================*/
/* Table: REGISTRATION_FAILURE_REASONS                          */
/*==============================================================*/
create table REGISTRATION_FAILURE_REASONS 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   ADDITION_DATE        TIMESTAMP            not null,
   REASON               VARCHAR(250)         not null,
   constraint PK_REGISTRATION_FAILURE_REASON primary key (ID)
)

go

create sequence S_REGISTRATION_FAILURE_REASONS
go

/*==============================================================*/
/* Table: REQUEST_CARD_BY_PHONE_LOG                             */
/*==============================================================*/
create table REQUEST_CARD_BY_PHONE_LOG 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   EVENT_DATE           TIMESTAMP            not null,
   BLOCK_ID             INTEGER              not null,
   FIO                  VARCHAR2(360)        not null,
   DOC_TYPE             VARCHAR2(32)         not null,
   DOC_NUMBER           VARCHAR2(32)         not null,
   BIRTHDAY             TIMESTAMP
)
partition by range
 (EVENT_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-01-2014','DD-MM-YYYY')))
go

create sequence S_REQUEST_CARD_BY_PHONE_LOG
go

/*==============================================================*/
/* Index: I_REQUEST_CARD_BY_PHONE_LOG                           */
/*==============================================================*/
create index I_REQUEST_CARD_BY_PHONE_LOG on REQUEST_CARD_BY_PHONE_LOG (
   BLOCK_ID ASC,
   EVENT_DATE ASC
)
local
go

/*==============================================================*/
/* Index: I_REQ_CARD_BY_PHONE_LOG_LOGIN                         */
/*==============================================================*/
create index I_REQ_CARD_BY_PHONE_LOG_LOGIN on REQUEST_CARD_BY_PHONE_LOG (
   BLOCK_ID ASC,
   LOGIN_ID ASC,
   EVENT_DATE ASC
)
local
go

/*==============================================================*/
/* Table: SMS_SERVICE_PAYMENT_STATISTICS                        */
/*==============================================================*/
create table SMS_SERVICE_PAYMENT_STATISTICS 
(
   PAYMENT_ID           NUMBER               not null,
   SERVICE_PROVIDER_ID  NUMBER               not null,
   SERVICE_PROVIDER_NAME VARCHAR2(160),
   PAYMENT_STATE        VARCHAR2(10 BYTE)    not null,
   AMOUNT               NUMBER(19,4)         not null,
   CURRENCY             CHAR(3)              not null,
   TB                   VARCHAR2(4 BYTE)     not null,
   FINAL_STATUS_DATE    TIMESTAMP            not null
)
partition by range
 (FINAL_STATUS_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-01-2014','DD-MM-YYYY')))
go

/*==============================================================*/
/* Index: SERV_PAY_STATISTICS_DATE_IDX                          */
/*==============================================================*/
create index SERV_PAY_STATISTICS_DATE_IDX on SMS_SERVICE_PAYMENT_STATISTICS (
   FINAL_STATUS_DATE ASC
)
local
go

/*==============================================================*/
/* Table: SYSTEMLOG                                             */
/*==============================================================*/
create table SYSTEMLOG 
(
   ID                   INTEGER              not null,
   MSG_LEVEL            CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   LOGIN_ID             INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   MESSAGE              CLOB,
   SESSION_ID           VARCHAR2(64),
   IP_ADDRESS           VARCHAR2(15),
   MESSAGE_SOURCE       VARCHAR2(16)         not null,
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   THREAD_INFO          INTEGER,
   USER_ID              VARCHAR2(50),
   NODE_ID              INTEGER,
   TB                   varchar2(4),
   OSB                  varchar2(4),
   VSP                  varchar2(7),
   ADD_INFO             VARCHAR2(128)
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition P_START values less than (to_date('01-12-2013','DD-MM-YYYY')))
go

create sequence S_SYSTEMLOG
go

/*==============================================================*/
/* Index: SL_APP_DATE_INDEX                                     */
/*==============================================================*/
create index SL_APP_DATE_INDEX on SYSTEMLOG (
   APPLICATION ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: SL_DI_DATE_INDEX                                      */
/*==============================================================*/
create index SL_DI_DATE_INDEX on SYSTEMLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: SL_LOGIN_DATE_INDEX                                   */
/*==============================================================*/
create index SL_LOGIN_DATE_INDEX on SYSTEMLOG (
   LOGIN_ID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: SL_SESSION_DATE_INDEX                                 */
/*==============================================================*/
create index SL_SESSION_DATE_INDEX on SYSTEMLOG (
   SESSION_ID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: SL_IP_DATE_INDEX                                      */
/*==============================================================*/
create index SL_IP_DATE_INDEX on SYSTEMLOG (
   IP_ADDRESS ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: SL_FIO_DATE_INDEX                                     */
/*==============================================================*/
create index SL_FIO_DATE_INDEX on SYSTEMLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: SL_PK                                                 */
/*==============================================================*/
create index SL_PK on SYSTEMLOG (
   ID ASC
)
reverse
go

/*==============================================================*/
/* Index: SL_FIO_BIRTHDAY_DATE_INDEX                            */
/*==============================================================*/
create index SL_FIO_BIRTHDAY_DATE_INDEX on SYSTEMLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE ASC
)
local
go

/*==============================================================*/
/* Table: USERLOG                                               */
/*==============================================================*/
create table USERLOG 
(
   ID                   INTEGER              not null,
   DESCRIPTION          VARCHAR2(160),
   DESCRIPTION_KEY      VARCHAR2(160),
   SUCCESS              CHAR(1)              not null,
   START_DATE           TIMESTAMP            not null,
   LOGIN_ID             INTEGER              not null,
   APPLICATION          VARCHAR2(20)         not null,
   OPERATION_KEY        CLOB,
   PARAMETERS           CLOB,
   SESSION_ID           VARCHAR2(64),
   IP_ADDRESS           VARCHAR2(15),
   EXECUTION_TIME       INTEGER,
   FIRST_NAME           VARCHAR2(42),
   SUR_NAME             VARCHAR2(42),
   PATR_NAME            VARCHAR2(42),
   DEPARTMENT_NAME      VARCHAR2(256),
   DOC_NUMBER           VARCHAR2(512),
   DOC_SERIES           VARCHAR2(512),
   BIRTHDAY             TIMESTAMP,
   USER_ID              VARCHAR2(50),
   NODE_ID              INTEGER,
   TB                   varchar2(4),
   OSB                  varchar2(4),
   VSP                  varchar2(7),
   THREAD_INFO          INTEGER,
   ADD_INFO             VARCHAR2(128)
)
partition by range
 (START_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition P_START values less than (to_date('01-12-2013','DD-MM-YYYY')))
go

create sequence S_USERLOG
go

/*==============================================================*/
/* Index: UL_FOR_REPORTS_INDEX                                  */
/*==============================================================*/
create index UL_FOR_REPORTS_INDEX on USERLOG (
   LOGIN_ID ASC,
   DESCRIPTION_KEY ASC,
   START_DATE ASC,
   APPLICATION ASC
)
local
go

/*==============================================================*/
/* Index: UL_SUCCESS_DESCR_INDEX                                */
/*==============================================================*/
create index UL_SUCCESS_DESCR_INDEX on USERLOG (
   SUCCESS ASC,
   DESCRIPTION ASC
)
go

/*==============================================================*/
/* Index: UL_APP_DATE_INDEX                                     */
/*==============================================================*/
create index UL_APP_DATE_INDEX on USERLOG (
   APPLICATION ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: UL_DI_DATE_INDEX                                      */
/*==============================================================*/
create index UL_DI_DATE_INDEX on USERLOG (
   DOC_NUMBER ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: UL_SESSION_DATE_INDEX                                 */
/*==============================================================*/
create index UL_SESSION_DATE_INDEX on USERLOG (
   SESSION_ID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: UL_LOGIN_DATE_INDEX                                   */
/*==============================================================*/
create index UL_LOGIN_DATE_INDEX on USERLOG (
   LOGIN_ID ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: UL_FIO_DATE_INDEX                                     */
/*==============================================================*/
create index UL_FIO_DATE_INDEX on USERLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   START_DATE DESC
)
local
go

/*==============================================================*/
/* Index: UL_PK                                                 */
/*==============================================================*/
create index UL_PK on USERLOG (
   ID ASC
)
reverse
go

/*==============================================================*/
/* Index: UL_FIO_BIRTHDAY_DATE_INDEX                            */
/*==============================================================*/
create index UL_FIO_BIRTHDAY_DATE_INDEX on USERLOG (
   UPPER(REPLACE(REPLACE("SUR_NAME"||"FIRST_NAME"||"PATR_NAME",' ',''),'-','')) ASC,
   BIRTHDAY ASC,
   START_DATE ASC
)
local
go

/*==============================================================*/
/* Table: USER_MESSAGES_LOG                                     */
/*==============================================================*/
create table USER_MESSAGES_LOG 
(
   ID                   INTEGER              not null,
   ADDITION_DATE        TIMESTAMP            not null,
   TYPE                 VARCHAR2(25)         not null,
   MESSAGE_ID           VARCHAR2(64),
   LOGIN_ID             INTEGER              not null,
   TYPE_CODE            VARCHAR2(32),
   constraint PK_USER_MESSAGES_LOG primary key (ID)
)
partition by range (ADDITION_DATE) interval(NUMTOYMINTERVAL(1, 'MONTH'))
(
    --Создаем 1 начальную партицию до 1 января
    partition P_START values less than (to_date('1-12-2013','DD-MM-YYYY'))
)

go

create sequence S_USER_MESSAGES_LOG
go

/*==============================================================*/
/* Index: I_USER_MESSAGES_LOG_DATE_TYPE                         */
/*==============================================================*/
create index I_USER_MESSAGES_LOG_DATE_TYPE on USER_MESSAGES_LOG (
   ADDITION_DATE ASC,
   TYPE ASC
)
local
go

/*==============================================================*/
/* Table: USER_NOTIFICATION_LOG                                 */
/*==============================================================*/
create table USER_NOTIFICATION_LOG 
(
   ID                   INTEGER              not null,
   LOGIN_ID             INTEGER              not null,
   ADDITION_DATE        TIMESTAMP            not null,
   TYPE                 VARCHAR2(25)         not null,
   VALUE                VARCHAR2(20),
   constraint PK_USER_NOTIFICATION_LOG primary key (ID)
)

go

create sequence S_USER_NOTIFICATION_LOG
go

/*==============================================================*/
/* Index: I_USER_NOTIFICATION_LOG_DATE                          */
/*==============================================================*/
create index I_USER_NOTIFICATION_LOG_DATE on USER_NOTIFICATION_LOG (
   ADDITION_DATE ASC,
   VALUE ASC
)
go

/*==============================================================*/
/* Database package: LOG_SERVICES_PKG                           */
/*==============================================================*/
create or replace package LOG_SERVICES_PKG as
   procedure AGREGATEOPERATIONMONITORING;
   procedure aggregateExceptionInformation(aggregationDate TIMESTAMP);
   procedure updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in clob, exceptionSystem in varchar, exceptionErrorCode in varchar);
end LOG_SERVICES_PKG;
go

create or replace package body LOG_SERVICES_PKG as
   procedure AGREGATEOPERATIONMONITORING as
      err_num NUMBER;
      err_msg VARCHAR2(100);
      needCreate VARCHAR2(10);
   begin
      select PROPERTY_VALUE into needCreate from PROPERTIES where PROPERTY_KEY='reports.business.aggregate.on';
      IF needCreate = 'true' THEN
         INSERT INTO AGREGATE_BUS_DOC_MONITORING (
            REPORT_DATE, 
            PROVIDER_UUID, 
            DOCUMENT_TYPE, 
            ACCOUNT_TYPE, 
            AMOUNT, 
            AMOUNT_CUR, 
            APPLICATION, 
            TB, 
            PLATFORM, 
            STATE_CODE, 
            NODE_ID, 
            "COUNT"
         ) SELECT
            MAX(START_DATE) AS REPORT_DATE,
            MIN(PROVIDER_UUID) AS PROVIDER_UUID,
            MIN(OPERATION_TYPE) AS DOCUMENT_TYPE,
            MIN(ACCOUNT_TYPE) AS ACCOUNT_TYPE,
            SUM(AMOUNT) AS AMOUNT,
            MIN(AMOUNT_CUR) AS AMOUNT_CUR,
            MIN(APPLICATION) AS APPLICATION,
            MIN(TB) AS TB,
            MIN(PLATFORM) AS PLATFORM,
            MIN(STATE_CODE) AS STATE_CODE,
            MIN(NODE_ID) AS NODE_ID,
            COUNT(1) AS "COUNT"
         FROM
            MONITORING_BUSINESS_OPERATION 
         WHERE
            (OPERATION_TYPE not in ('AOC', 'AOC_ALF') or STATE_CODE ='EXECUTED') AND
            (AMOUNT_CUR IS NULL OR AMOUNT_CUR = 'RUB' OR AMOUNT_CUR = 'RUR') AND
            START_DATE BETWEEN (TRUNC(sysdate - 1)) AND (TRUNC(sysdate)) 
         GROUP BY
            OPERATION_TYPE,
            PROVIDER_UUID,
            ACCOUNT_TYPE,
            TB,
            PLATFORM,
            STATE_CODE,
            NODE_ID,
            AMOUNT_CUR;
   				
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE) values (sysdate, 'SUCCESS');
      END IF;
   EXCEPTION
      WHEN OTHERS THEN 
         err_num := SQLCODE;
         err_msg := SUBSTR(SQLERRM, 1, 100);
         INSERT INTO BUS_DOCUMENTS_MONITORING_STATE (REPORT_DATE, STATE, STATE_DESCR) values (sysdate, 'ERROR', TO_CHAR(err_num) || ':' || err_msg);
   end;
   procedure aggregateExceptionInformation(aggregationDate TIMESTAMP) as
   BEGIN
   execute immediate 'INSERT INTO EXCEPTION_COUNTERS (EXCEPTION_HASH, EXCEPTION_DATE, EXCEPTION_COUNT) '||
      'SELECT HASH, to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' ), count(1) FROM EXCEPTIONS_LOG '||
          'PARTITION FOR (to_date('''||to_char(aggregationDate, 'YYYYMMDD')||''',''YYYYMMDD'' )) GROUP BY HASH';
   END;
   procedure updateExceptionInformation(exceptionKind in varchar, exceptionHash in varchar, exceptionOperation in varchar, exceptionApplication in varchar, exceptionDetail in clob, exceptionSystem in varchar, exceptionErrorCode in varchar) as
   begin
     insert into EXCEPTIONS_LOG(CREATION_DATE, HASH) values (SYSDATE, exceptionHash);
   
     insert into EXCEPTION_ENTRY (ID, KIND, HASH, OPERATION, APPLICATION, DETAIL, SYSTEM, ERROR_CODE) 
         select S_EXCEPTION_ENTRY.nextval, exceptionKind, exceptionHash, exceptionOperation, exceptionApplication, exceptionDetail, exceptionSystem, exceptionErrorCode from DUAL
         where not exists (select 1 from EXCEPTION_ENTRY where EXCEPTION_ENTRY.HASH = exceptionHash);
   
     commit;
   end;
end LOG_SERVICES_PKG;
go

begin
DBMS_SCHEDULER.CREATE_JOB (
   job_name                 =>  'AGGREGATE_EXC_INFO', 
   job_type                 =>  'PLSQL_BLOCK',
   job_action               =>  'BEGIN LOG_SERVICES_PKG.aggregateExceptionInformation(sysdate-1); END;',
   start_date               =>  to_date('2015-03-01 02:25','YYYY-MM-DD HH24:MI'),
   repeat_interval          =>  'FREQ=DAILY',
   enabled => TRUE
   );
end;
/
go


begin
DBMS_SCHEDULER.CREATE_JOB (
   job_name                 =>  'AGREGATE_OPERATION_JOB', 
   job_type                 =>  'PLSQL_BLOCK',
   job_action               =>  'BEGIN LOG_SERVICES_PKG.AGREGATEOPERATIONMONITORING; END;',
   start_date               =>  to_date('2015-03-23 01:00','YYYY-MM-DD HH24:MI'),
   repeat_interval          =>  'FREQ=DAILY',
   enabled => TRUE
   );
end;
go
/
