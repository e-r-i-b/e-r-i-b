/*==============================================================*/
/* Database name:  CSA                                          */
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     14.08.2015 14:23:20                          */
/*==============================================================*/


drop package CSA_CONNECTORS_PKG
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CLIENT_IDS'; 
    EXECUTE IMMEDIATE('alter table CLIENT_IDS drop constraint FK_CLIENT_IDS_TO_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_CSA_CONN_FK_CSA_CO_CSA_PR'; 
    EXECUTE IMMEDIATE('drop index DXFK_CSA_CONN_FK_CSA_CO_CSA_PR');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_CONNECTORS'; 
    EXECUTE IMMEDIATE('alter table CSA_CONNECTORS drop constraint FK_CSA_CONN_FK_CSA_CO_CSA_PROF');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_LOGINS'; 
    EXECUTE IMMEDIATE('alter table CSA_LOGINS drop constraint FK_CSA_LOGINS_TO_CONNECTORS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_LOGINS'; 
    EXECUTE IMMEDIATE('alter table CSA_LOGINS drop constraint FK_CSA_LOGINS_TO_GUEST');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_OPERATIONS'; 
    EXECUTE IMMEDIATE('alter table CSA_OPERATIONS drop constraint FK_CSA_OPER_FK_CSA_OP_CSA_PROF');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_CSA_PASS_FK_CSA_PA_CSA_CO'; 
    EXECUTE IMMEDIATE('drop index DXFK_CSA_PASS_FK_CSA_PA_CSA_CO');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PASSWORDS'; 
    EXECUTE IMMEDIATE('alter table CSA_PASSWORDS drop constraint FK_CSA_PASS_FK_CSA_PA_CSA_CONN');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_CSA_PROF_REFERENCE_CSA_PR'; 
    EXECUTE IMMEDIATE('drop index DXFK_CSA_PROF_REFERENCE_CSA_PR');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROFILES_HISTORY'; 
    EXECUTE IMMEDIATE('alter table CSA_PROFILES_HISTORY drop constraint FK_CSA_PROF_FK_CSA_PR_CSA_PROF');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXREFERENCE_5'; 
    EXECUTE IMMEDIATE('drop index DXREFERENCE_5');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROFILES_LOCK'; 
    EXECUTE IMMEDIATE('alter table CSA_PROFILES_LOCK drop constraint FK_CSA_PROF_REFERENCE_CSA_PROF');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_CSA_PROFILENODES_TO_NODE'; 
    EXECUTE IMMEDIATE('drop index DXFK_CSA_PROFILENODES_TO_NODE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROFILE_NODES'; 
    EXECUTE IMMEDIATE('alter table CSA_PROFILE_NODES drop constraint FK_CSA_PROFILENODES_TO_NODE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_CSA_PROFILENODES_TO_PROFI'; 
    EXECUTE IMMEDIATE('drop index DXFK_CSA_PROFILENODES_TO_PROFI');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROFILE_NODES'; 
    EXECUTE IMMEDIATE('alter table CSA_PROFILE_NODES drop constraint FK_CSA_PROFILENODES_TO_PROFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GUEST_PASSWORDS'; 
    EXECUTE IMMEDIATE('alter table GUEST_PASSWORDS drop constraint FK_GUEST_PASS_TO_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_LIST_PROPERTIES'; 
    EXECUTE IMMEDIATE('drop index DXFK_LIST_PROPERTIES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'LIST_PROPERTIES'; 
    EXECUTE IMMEDIATE('alter table LIST_PROPERTIES drop constraint FK_LIST_PROPERTIES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROFILE_MDM_IDS'; 
    EXECUTE IMMEDIATE('alter table PROFILE_MDM_IDS drop constraint FK_P_MDM_TO_PROFILE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IND_BLOCKRULE_T_PUBLISH_DATE'; 
    EXECUTE IMMEDIATE('drop index IND_BLOCKRULE_T_PUBLISH_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IND_BLOCKRULE_F_PUBLISH_DATE'; 
    EXECUTE IMMEDIATE('drop index IND_BLOCKRULE_F_PUBLISH_DATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'BLOCKINGRULES'; 
    EXECUTE IMMEDIATE('drop table BLOCKINGRULES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_BLOCKINGRULES';
    EXECUTE IMMEDIATE('drop sequence  S_BLOCKINGRULES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'BLOCKINGRULES_RES'; 
    EXECUTE IMMEDIATE('drop table BLOCKINGRULES_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CLIENT_IDS_CLIENT_ID_INDEX'; 
    EXECUTE IMMEDIATE('drop index CLIENT_IDS_CLIENT_ID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CLIENT_IDS_PROFILE_ID_INDEX'; 
    EXECUTE IMMEDIATE('drop index CLIENT_IDS_PROFILE_ID_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CLIENT_IDS'; 
    EXECUTE IMMEDIATE('drop table CLIENT_IDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CLIENT_IDS';
    EXECUTE IMMEDIATE('drop sequence  S_CLIENT_IDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_CSA_CONNECTORS_DEVICE_ID'; 
    EXECUTE IMMEDIATE('drop index IDX_CSA_CONNECTORS_DEVICE_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CONNECTORS_CTS'; 
    EXECUTE IMMEDIATE('drop index CSA_CONNECTORS_CTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CONNECTORS_PTS'; 
    EXECUTE IMMEDIATE('drop index CSA_CONNECTORS_PTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CONNECTORS_UTS'; 
    EXECUTE IMMEDIATE('drop index CSA_CONNECTORS_UTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CONNECTORS_LTS'; 
    EXECUTE IMMEDIATE('drop index CSA_CONNECTORS_LTS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CONNECTORS_U_LOGIN'; 
    EXECUTE IMMEDIATE('drop index CSA_CONNECTORS_U_LOGIN');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CONNECTORS_U_GUID'; 
    EXECUTE IMMEDIATE('drop index CSA_CONNECTORS_U_GUID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CONNECTORS_PHONE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CONNECTORS_PHONE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_CONNECTORS'; 
    EXECUTE IMMEDIATE('drop table CSA_CONNECTORS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_CONNECTORS';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_CONNECTORS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_CSA_LOGINS'; 
    EXECUTE IMMEDIATE('drop index IDX_CSA_LOGINS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_CSA_LOGINS_TO_GUEST_PROF'; 
    EXECUTE IMMEDIATE('drop index DXFK_CSA_LOGINS_TO_GUEST_PROF');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'DXFK_CSA_LOGINS_TO_CONNECTORS'; 
    EXECUTE IMMEDIATE('drop index DXFK_CSA_LOGINS_TO_CONNECTORS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_LOGINS'; 
    EXECUTE IMMEDIATE('drop table CSA_LOGINS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_NODES'; 
    EXECUTE IMMEDIATE('drop table CSA_NODES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_NODES';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_NODES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_OPERATIONS_PTSC'; 
    EXECUTE IMMEDIATE('drop index CSA_OPERATIONS_PTSC');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_OPERATIONS'; 
    EXECUTE IMMEDIATE('drop table CSA_OPERATIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PASSWORDS_CC'; 
    EXECUTE IMMEDIATE('drop index CSA_PASSWORDS_CC');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PASSWORDS'; 
    EXECUTE IMMEDIATE('drop table CSA_PASSWORDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_PASSWORDS';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_PASSWORDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROFILES_INCOGNITO_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_PROFILES_INCOGNITO_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROFILES_PHONE_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_PROFILES_PHONE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROFILES_AGREEMENT_NUM_I'; 
    EXECUTE IMMEDIATE('drop index CSA_PROFILES_AGREEMENT_NUM_I');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROFILES_FIO_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_PROFILES_FIO_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROFILES_DUL_BIRTH_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_PROFILES_DUL_BIRTH_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROFILES_UNIVERSAL_ID'; 
    EXECUTE IMMEDIATE('drop index CSA_PROFILES_UNIVERSAL_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROFILES'; 
    EXECUTE IMMEDIATE('drop table CSA_PROFILES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_PROFILES';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROFILES_HISTORY_UID'; 
    EXECUTE IMMEDIATE('drop index CSA_PROFILES_HISTORY_UID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROFILES_HISTORY'; 
    EXECUTE IMMEDIATE('drop table CSA_PROFILES_HISTORY cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_PROFILES_HISTORY';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_PROFILES_HISTORY');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROFILES_LOCK'; 
    EXECUTE IMMEDIATE('drop table CSA_PROFILES_LOCK cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_PROFILES_LOCK';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_PROFILES_LOCK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_CHG071536_PROFILE_ID'; 
    EXECUTE IMMEDIATE('drop index CSA_CHG071536_PROFILE_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROFILES_LOCK_CHG071536'; 
    EXECUTE IMMEDIATE('drop table CSA_PROFILES_LOCK_CHG071536 cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_PROFILES_LOCK_CHG071536';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_PROFILES_LOCK_CHG071536');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_CSA_PROFILE_NODES_STATE'; 
    EXECUTE IMMEDIATE('drop index I_CSA_PROFILE_NODES_STATE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROFILE_NODES'; 
    EXECUTE IMMEDIATE('drop table CSA_PROFILE_NODES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_PROFILE_NODES';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_PROFILE_NODES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROMCLNT_PROM_SESID_INDX'; 
    EXECUTE IMMEDIATE('drop index CSA_PROMCLNT_PROM_SESID_INDX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROMOCLIENT_LOG'; 
    EXECUTE IMMEDIATE('drop table CSA_PROMOCLIENT_LOG cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_PROMOCLIENT_LOG';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_PROMOCLIENT_LOG');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROMO_SES_CREATE_DATE_INDX'; 
    EXECUTE IMMEDIATE('drop index CSA_PROMO_SES_CREATE_DATE_INDX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_PROMOTER_SESSIONS_INDEX'; 
    EXECUTE IMMEDIATE('drop index CSA_PROMOTER_SESSIONS_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_PROMOTER_SESSIONS'; 
    EXECUTE IMMEDIATE('drop table CSA_PROMOTER_SESSIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_PROMOTER_SESSIONS';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_PROMOTER_SESSIONS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_SESSIONS_CSD'; 
    EXECUTE IMMEDIATE('drop index CSA_SESSIONS_CSD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_SESSIONS'; 
    EXECUTE IMMEDIATE('drop table CSA_SESSIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'CSA_STORED_PASSWORD_U_LOGIN'; 
    EXECUTE IMMEDIATE('drop index CSA_STORED_PASSWORD_U_LOGIN');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_STORED_PASSWORDS'; 
    EXECUTE IMMEDIATE('drop table CSA_STORED_PASSWORDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_STORED_PASSWORDS';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_STORED_PASSWORDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'CSA_TABS'; 
    EXECUTE IMMEDIATE('drop table CSA_TABS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_CSA_TABS';
    EXECUTE IMMEDIATE('drop sequence  S_CSA_TABS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ERIB_LOCALES'; 
    EXECUTE IMMEDIATE('drop table ERIB_LOCALES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_ERIB_STATIC_MESSAGE_LBK'; 
    EXECUTE IMMEDIATE('drop index I_ERIB_STATIC_MESSAGE_LBK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'ERIB_STATIC_MESSAGE'; 
    EXECUTE IMMEDIATE('drop table ERIB_STATIC_MESSAGE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_ERIB_STATIC_MESSAGE';
    EXECUTE IMMEDIATE('drop sequence  S_ERIB_STATIC_MESSAGE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_GUEST_OPERATIONS_OOUD'; 
    EXECUTE IMMEDIATE('drop index IDX_GUEST_OPERATIONS_OOUD');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GUEST_OPERATIONS'; 
    EXECUTE IMMEDIATE('drop table GUEST_OPERATIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_GUEST_DATE_PASSWORDS'; 
    EXECUTE IMMEDIATE('drop index IDX_GUEST_DATE_PASSWORDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GUEST_PASSWORDS'; 
    EXECUTE IMMEDIATE('drop table GUEST_PASSWORDS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_GUEST_PASSWORDS';
    EXECUTE IMMEDIATE('drop sequence  S_GUEST_PASSWORDS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'IDX_GUEST_PROFILES_PHONE'; 
    EXECUTE IMMEDIATE('drop index IDX_GUEST_PROFILES_PHONE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'GUEST_PROFILES'; 
    EXECUTE IMMEDIATE('drop table GUEST_PROFILES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'LIST_PROPERTIES'; 
    EXECUTE IMMEDIATE('drop table LIST_PROPERTIES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_LIST_PROPERTIES';
    EXECUTE IMMEDIATE('drop sequence  S_LIST_PROPERTIES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CACHE_CARDS_BY_PHONE'; 
    EXECUTE IMMEDIATE('drop table MBK_CACHE_CARDS_BY_PHONE cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CACHE_CLIENT_BY_CARD'; 
    EXECUTE IMMEDIATE('drop table MBK_CACHE_CLIENT_BY_CARD cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CACHE_CLIENT_BY_LOGIN'; 
    EXECUTE IMMEDIATE('drop table MBK_CACHE_CLIENT_BY_LOGIN cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CACHE_IMSI_CHECK_RESULT'; 
    EXECUTE IMMEDIATE('drop table MBK_CACHE_IMSI_CHECK_RESULT cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CACHE_REGISTRATIONS'; 
    EXECUTE IMMEDIATE('drop table MBK_CACHE_REGISTRATIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CACHE_REGISTRATIONS2'; 
    EXECUTE IMMEDIATE('drop table MBK_CACHE_REGISTRATIONS2 cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CACHE_REGISTRATIONS3'; 
    EXECUTE IMMEDIATE('drop table MBK_CACHE_REGISTRATIONS3 cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CACHE_REGISTRATIONS_PACK'; 
    EXECUTE IMMEDIATE('drop table MBK_CACHE_REGISTRATIONS_PACK cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MBK_CACHE_REGISTRATIONS_PACK';
    EXECUTE IMMEDIATE('drop sequence  S_MBK_CACHE_REGISTRATIONS_PACK');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'MBK_CACHE_REGISTRATIONS_PACK3'; 
    EXECUTE IMMEDIATE('drop table MBK_CACHE_REGISTRATIONS_PACK3 cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_MBK_CACHE_REGISTRATIONS_PACK3';
    EXECUTE IMMEDIATE('drop sequence  S_MBK_CACHE_REGISTRATIONS_PACK3');
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

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_DATE_6'; 
    EXECUTE IMMEDIATE('drop index INDEX_DATE_6');
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

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'U_CSA_PROFILE_ID'; 
    EXECUTE IMMEDIATE('drop index U_CSA_PROFILE_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PROFILE_MDM_IDS'; 
    EXECUTE IMMEDIATE('drop table PROFILE_MDM_IDS cascade constraints');
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
    WHERE UPPER (index_name) = 'I_PUSH_PARAMS_KEY'; 
    EXECUTE IMMEDIATE('drop index I_PUSH_PARAMS_KEY');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'PUSH_PARAMS'; 
    EXECUTE IMMEDIATE('drop table PUSH_PARAMS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_PUSH_PARAMS';
    EXECUTE IMMEDIATE('drop sequence  S_PUSH_PARAMS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_CODE'; 
    EXECUTE IMMEDIATE('drop index INDEX_CODE');
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

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_SMS_KEY'; 
    EXECUTE IMMEDIATE('drop index INDEX_SMS_KEY');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SMS_RESOURCES'; 
    EXECUTE IMMEDIATE('drop table SMS_RESOURCES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_SMS_RESOURCES';
    EXECUTE IMMEDIATE('drop sequence  S_SMS_RESOURCES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'SMS_RESOURCES_RES'; 
    EXECUTE IMMEDIATE('drop table SMS_RESOURCES_RES cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'UI_TECHNOBREAKS'; 
    EXECUTE IMMEDIATE('drop index UI_TECHNOBREAKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'I_TECHNOBREAKS'; 
    EXECUTE IMMEDIATE('drop index I_TECHNOBREAKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'TECHNOBREAKS'; 
    EXECUTE IMMEDIATE('drop table TECHNOBREAKS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_TECHNOBREAKS';
    EXECUTE IMMEDIATE('drop sequence  S_TECHNOBREAKS');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_PROFILE_ID'; 
    EXECUTE IMMEDIATE('drop index INDEX_PROFILE_ID');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'INDEX_COOKIE'; 
    EXECUTE IMMEDIATE('drop index INDEX_COOKIE');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'USERS_REGIONS'; 
    EXECUTE IMMEDIATE('drop table USERS_REGIONS cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE i_name   VARCHAR2 (100);
BEGIN 
   SELECT index_name
     INTO i_name
     FROM user_indexes
    WHERE UPPER (index_name) = 'WAY4_NOTIF_CLIENTID_DATE_INDEX'; 
    EXECUTE IMMEDIATE('drop index WAY4_NOTIF_CLIENTID_DATE_INDEX');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE t_name   VARCHAR2 (100);
BEGIN 
   SELECT table_name
     INTO t_name
     FROM user_all_tables
    WHERE UPPER (table_name) = 'WAY4_NOTIFICATION_JOURNAL'; 
    EXECUTE IMMEDIATE('drop table WAY4_NOTIFICATION_JOURNAL cascade constraints');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_GUEST_CODE_PROFILES'; 
    EXECUTE IMMEDIATE('drop sequence S_GUEST_CODE_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

DECLARE s_name   VARCHAR2 (100);
BEGIN 
   SELECT sequence_name
     INTO s_name
     FROM user_sequences
    WHERE UPPER (sequence_name) = 'S_GUEST_PROFILES'; 
    EXECUTE IMMEDIATE('drop sequence S_GUEST_PROFILES');
EXCEPTION WHEN NO_DATA_FOUND THEN NULL;
END;
go

create sequence S_GUEST_CODE_PROFILES
cache 10000
go

create sequence S_GUEST_PROFILES
cache 1000
go

/*==============================================================*/
/* Table: BLOCKINGRULES                                         */
/*==============================================================*/
create table BLOCKINGRULES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   DEPARTMENTS          VARCHAR2(100)        not null,
   STATE                VARCHAR2(9)          not null,
   RESUMING_TIME        TIMESTAMP            not null,
   FROM_PUBLISH_DATE    TIMESTAMP,
   TO_PUBLISH_DATE      TIMESTAMP,
   FROM_RESTRICTION_DATE TIMESTAMP,
   TO_RESTRICTION_DATE  TIMESTAMP,
   ERIB                 CHAR                 default '0' not null,
   ERIB_MESSAGE         VARCHAR2(1024),
   MAPI                 CHAR                 default '0' not null,
   MAPI_MESSAGE         VARCHAR2(1024),
   ATM                  CHAR                 default '0' not null,
   ATM_MESSAGE          VARCHAR2(1024),
   ERMB                 CHAR                 default '0' not null,
   ERMB_MESSAGE         VARCHAR2(1024),
   constraint PK_BLOCKINGRULES primary key (ID)
)

go

create sequence S_BLOCKINGRULES
go

/*==============================================================*/
/* Index: IND_BLOCKRULE_F_PUBLISH_DATE                          */
/*==============================================================*/
create index IND_BLOCKRULE_F_PUBLISH_DATE on BLOCKINGRULES (
   FROM_PUBLISH_DATE ASC
)
go

/*==============================================================*/
/* Index: IND_BLOCKRULE_T_PUBLISH_DATE                          */
/*==============================================================*/
create index IND_BLOCKRULE_T_PUBLISH_DATE on BLOCKINGRULES (
   TO_PUBLISH_DATE ASC
)
go

/*==============================================================*/
/* Table: BLOCKINGRULES_RES                                     */
/*==============================================================*/
create table BLOCKINGRULES_RES 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   ERIB_MESSAGE         VARCHAR2(1024),
   MAPI_MESSAGE         VARCHAR2(1024),
   ATM_MESSAGE          VARCHAR2(1024),
   ERMB_MESSAGE         VARCHAR2(1024),
   constraint PK_BLOCKINGRULES_RES primary key (ID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: CLIENT_IDS                                            */
/*==============================================================*/
create table CLIENT_IDS 
(
   PROFILE_ID           INTEGER              not null,
   CLIENTID             VARCHAR2(100)        not null,
   AMND_DATE            TIMESTAMP            not null
)

go

create sequence S_CLIENT_IDS
go

/*==============================================================*/
/* Index: CLIENT_IDS_PROFILE_ID_INDEX                           */
/*==============================================================*/
create index CLIENT_IDS_PROFILE_ID_INDEX on CLIENT_IDS (
   PROFILE_ID ASC
)
go

/*==============================================================*/
/* Index: CLIENT_IDS_CLIENT_ID_INDEX                            */
/*==============================================================*/
create index CLIENT_IDS_CLIENT_ID_INDEX on CLIENT_IDS (
   CLIENTID ASC
)
go

/*==============================================================*/
/* Table: CSA_CONNECTORS                                        */
/*==============================================================*/
create table CSA_CONNECTORS 
(
   PUSH_SUPPORTED       CHAR                 default '0' not null,
   DEVICE_ID            VARCHAR2(40),
   ID                   INTEGER              not null,
   GUID                 VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            not null,
   STATE                VARCHAR2(40)         not null,
   TYPE                 VARCHAR2(40)         not null,
   USER_ID              VARCHAR2(10),
   LOGIN                VARCHAR2(32),
   CB_CODE              VARCHAR2(20)         not null,
   CARD_NUMBER          VARCHAR2(20),
   BLOCK_REASON         VARCHAR2(256),
   BLOCKED_UNTIL        TIMESTAMP,
   AUTH_ERRORS          INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   DEVICE_INFO          VARCHAR2(100),
   DEVICE_STATE         VARCHAR2(100),
   LAST_SESSION_ID      VARCHAR2(32),
   LAST_SESSION_DATE    TIMESTAMP,
   REGISTRATION_TYPE    VARCHAR2(8)          not null,
   PHONE_NUMBER         VARCHAR2(20),
   API_VERSION          VARCHAR2(4),
   DEVICE_SECURITY_TOKEN VARCHAR2(4000),
   SECURITY_TYPE        VARCHAR2(28),
   REGISTRATION_LOGIN_TYPE VARCHAR2(20),
   constraint PK_CSA_CONNECTORS primary key (ID)
)

go

create sequence S_CSA_CONNECTORS
go

/*==============================================================*/
/* Index: CONNECTORS_PHONE_INDEX                                */
/*==============================================================*/
create unique index CONNECTORS_PHONE_INDEX on CSA_CONNECTORS (
   PHONE_NUMBER ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_U_GUID                                 */
/*==============================================================*/
create unique index CSA_CONNECTORS_U_GUID on CSA_CONNECTORS (
   GUID ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_U_LOGIN                                */
/*==============================================================*/
create unique index CSA_CONNECTORS_U_LOGIN on CSA_CONNECTORS (
   UPPER(LOGIN) ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_LTS                                    */
/*==============================================================*/
create index CSA_CONNECTORS_LTS on CSA_CONNECTORS (
   LOGIN ASC,
   TYPE ASC,
   STATE ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_UTS                                    */
/*==============================================================*/
create index CSA_CONNECTORS_UTS on CSA_CONNECTORS (
   USER_ID ASC,
   TYPE ASC,
   STATE ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_PTS                                    */
/*==============================================================*/
create index CSA_CONNECTORS_PTS on CSA_CONNECTORS (
   PROFILE_ID ASC,
   TYPE ASC,
   STATE ASC
)
go

/*==============================================================*/
/* Index: CSA_CONNECTORS_CTS                                    */
/*==============================================================*/
create index CSA_CONNECTORS_CTS on CSA_CONNECTORS (
   CARD_NUMBER ASC,
   TYPE ASC,
   STATE ASC
)
go

/*==============================================================*/
/* Index: IDX_CSA_CONNECTORS_DEVICE_ID                          */
/*==============================================================*/
create index IDX_CSA_CONNECTORS_DEVICE_ID on CSA_CONNECTORS (
   DEVICE_ID ASC
)
go

/*==============================================================*/
/* Table: CSA_LOGINS                                            */
/*==============================================================*/
create table CSA_LOGINS 
(
   LOGIN                VARCHAR2(32)         not null,
   CONNECTOR_ID         INTEGER,
   GUEST_ID             INTEGER
)
go

/*==============================================================*/
/* Index: DXFK_CSA_LOGINS_TO_CONNECTORS                         */
/*==============================================================*/
create index DXFK_CSA_LOGINS_TO_CONNECTORS on CSA_LOGINS (
   CONNECTOR_ID ASC
)
go

/*==============================================================*/
/* Index: DXFK_CSA_LOGINS_TO_GUEST_PROF                         */
/*==============================================================*/
create index DXFK_CSA_LOGINS_TO_GUEST_PROF on CSA_LOGINS (
   GUEST_ID ASC
)
go

/*==============================================================*/
/* Index: IDX_CSA_LOGINS                                        */
/*==============================================================*/
create unique index IDX_CSA_LOGINS on CSA_LOGINS (
   UPPER(LOGIN) ASC
)
go

/*==============================================================*/
/* Table: CSA_NODES                                             */
/*==============================================================*/
create table CSA_NODES 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(100)        not null,
   HOSTNAME             VARCHAR2(100)        not null,
   LISTENER_HOSTNAME    VARCHAR2(100)        not null,
   EXISTING_USERS_ALLOWED CHAR(1)              not null,
   NEW_USERS_ALLOWED    CHAR(1)              not null,
   TEMPORARY_USERS_ALLOWED CHAR(1)              not null,
   USERS_TRANSFER_ALLOWED CHAR(1)              not null,
   ADMIN_AVAILABLE      CHAR(1)              not null,
   SMS_QUEUE_NAME       VARCHAR2(64)         not null,
   SMS_FACTORY_NAME     VARCHAR2(64)         not null,
   DICTIONARY_QUEUE_NAME VARCHAR2(64)         not null,
   DICTIONARY_FACTORY_NAME VARCHAR2(64)         not null,
   MULTI_NODE_DATA_QUEUE_NAME VARCHAR2(64)         not null,
   MULTI_NODE_DATA_FACTORY_NAME VARCHAR2(64)         not null,
   ERMB_QUEUE_NAME      varchar2(64)         not null,
   ERMB_FACTORY_NAME    varchar2(64)         not null,
   MBK_REGISTRATION_QUEUE_NAME varchar2(64)         not null,
   MBK_REGISTRATION_FACTORY_NAME varchar2(64)         not null,
   GUEST_AVAILABLE      CHAR(1)              not null,
   constraint PK_CSA_NODES primary key (ID)
)

go

create sequence S_CSA_NODES
go

/*==============================================================*/
/* Table: CSA_OPERATIONS                                        */
/*==============================================================*/
create table CSA_OPERATIONS 
(
   OUID                 VARCHAR2(32)         not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   TYPE                 VARCHAR2(40)         not null,
   STATE                VARCHAR2(40)         not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   PROFILE_ID           INTEGER              not null,
   PARAMS               CLOB,
   CONFIRM_TYPE         VARCHAR2(32),
   CONFIRM_CODE_HASH    VARCHAR2(40),
   CONFIRM_CODE_SALT    VARCHAR2(32),
   CONFIRM_CODE_CREATION_DATE TIMESTAMP,
   CONFIRM_ERRORS       INTEGER,
   CONFIRM_SID          VARCHAR2(64),
   CONFIRM_PASSWORD_NUMBER VARCHAR2(32),
   CONFIRM_RECEIPT_NUMBER VARCHAR2(32),
   CONFIRM_PASSWORD_LEFT INTEGER,
   CONFIRM_LAST_ATEMPTS INTEGER,
   INFO                 CLOB,
   IP_ADDRESS           VARCHAR2(15),
   constraint PK_CSA_OPERATIONS primary key (OUID)
)
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) 
subpartition by hash (PROFILE_ID) subpartitions 16
(
       partition P_2013_07 values less than (timestamp '2013-08-01 00:00:00')
)
enable row movement
go

/*==============================================================*/
/* Index: CSA_OPERATIONS_PTSC                                   */
/*==============================================================*/
create index CSA_OPERATIONS_PTSC on CSA_OPERATIONS (
   PROFILE_ID ASC,
   TYPE ASC,
   STATE ASC,
   CREATION_DATE ASC
)
local
go

/*==============================================================*/
/* Table: CSA_PASSWORDS                                         */
/*==============================================================*/
create table CSA_PASSWORDS 
(
   ID                   INTEGER              not null,
   VALUE                VARCHAR2(40)         not null,
   SALT                 VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            not null,
   ACTIVE               CHAR                 not null,
   CONNECTOR_ID         INTEGER              not null,
   constraint PK_CSA_PASSWORDS primary key (ID)
)
partition by range
 (CREATION_DATE)
 (partition
         P_MAXVALUE
        values less than (maxvalue ))
go

create sequence S_CSA_PASSWORDS
go

/*==============================================================*/
/* Index: CSA_PASSWORDS_CC                                      */
/*==============================================================*/
create index CSA_PASSWORDS_CC on CSA_PASSWORDS (
   CONNECTOR_ID ASC,
   CREATION_DATE ASC
)
local
go

/*==============================================================*/
/* Table: CSA_PROFILES                                          */
/*==============================================================*/
create table CSA_PROFILES 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   MAPI_PASSWORD_VALUE  VARCHAR2(40),
   MAPI_PASSWORD_SALT   VARCHAR2(32),
   MAPI_PASSWORD_CREATION_DATE TIMESTAMP,
   INCOGNITO            CHAR                 default '0' not null,
   AGREEMENT_NUMBER     VARCHAR2(16),
   CREATION_TYPE        VARCHAR2(25),
   PHONE                VARCHAR2(16),
   SECURITY_TYPE        VARCHAR2(28),
   constraint PK_CSA_PROFILES primary key (ID)
)

go

create sequence S_CSA_PROFILES
go

/*==============================================================*/
/* Index: CSA_PROFILES_UNIVERSAL_ID                             */
/*==============================================================*/
create unique index CSA_PROFILES_UNIVERSAL_ID on CSA_PROFILES (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   REPLACE(PASSPORT,' ','') ASC,
   TB ASC
)
go

/*==============================================================*/
/* Index: CSA_PROFILES_DUL_BIRTH_INDEX                          */
/*==============================================================*/
create index CSA_PROFILES_DUL_BIRTH_INDEX on CSA_PROFILES (
   REPLACE(REPLACE(PASSPORT,' ',''),'-','') ASC,
   BIRTHDATE DESC
)
go

/*==============================================================*/
/* Index: CSA_PROFILES_FIO_INDEX                                */
/*==============================================================*/
create index CSA_PROFILES_FIO_INDEX on CSA_PROFILES (
   UPPER(REPLACE(REPLACE(CONCAT(CONCAT(SUR_NAME, FIRST_NAME), PATR_NAME), ' ', ''), '-', '')) ASC
)
go

/*==============================================================*/
/* Index: CSA_PROFILES_AGREEMENT_NUM_I                          */
/*==============================================================*/
create index CSA_PROFILES_AGREEMENT_NUM_I on CSA_PROFILES (
   AGREEMENT_NUMBER ASC
)
go

/*==============================================================*/
/* Index: CSA_PROFILES_PHONE_INDEX                              */
/*==============================================================*/
create index CSA_PROFILES_PHONE_INDEX on CSA_PROFILES (
   PHONE ASC
)
go

/*==============================================================*/
/* Index: CSA_PROFILES_INCOGNITO_INDEX                          */
/*==============================================================*/
create index CSA_PROFILES_INCOGNITO_INDEX on CSA_PROFILES (
   decode(INCOGNITO, '1', ID, null) ASC
)
go

/*==============================================================*/
/* Table: CSA_PROFILES_HISTORY                                  */
/*==============================================================*/
create table CSA_PROFILES_HISTORY 
(
   ID                   INTEGER              not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   BIRTHDATE            TIMESTAMP            not null,
   TB                   VARCHAR2(4)          not null,
   EXPIRE_DATE          TIMESTAMP            default SYSDATE not null,
   PROFILE_ID           INTEGER              not null,
   constraint PK_CSA_PROFILES_HISTORY primary key (ID)
)

go

create sequence S_CSA_PROFILES_HISTORY
go

/*==============================================================*/
/* Index: CSA_PROFILES_HISTORY_UID                              */
/*==============================================================*/
create index CSA_PROFILES_HISTORY_UID on CSA_PROFILES_HISTORY (
   UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' '))) ASC,
   BIRTHDATE ASC,
   TB ASC,
   REPLACE(PASSPORT,' ','') ASC
)
go

/*==============================================================*/
/* Table: CSA_PROFILES_LOCK                                     */
/*==============================================================*/
create table CSA_PROFILES_LOCK 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   REASON               VARCHAR2(1024)       not null,
   DATE_FROM            TIMESTAMP            not null,
   DATE_TO              TIMESTAMP,
   LOCKER_FIO           VARCHAR2(100)        not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   constraint PK_CSA_PROFILES_LOCK primary key (ID)
)

go

create sequence S_CSA_PROFILES_LOCK
go

/*==============================================================*/
/* Table: CSA_PROFILES_LOCK_CHG071536                           */
/*==============================================================*/
create table CSA_PROFILES_LOCK_CHG071536 
(
   ID                   INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   REASON               VARCHAR2(1024)       not null,
   DATE_FROM            TIMESTAMP            not null,
   DATE_TO              TIMESTAMP,
   LOCKER_FIO           VARCHAR2(100)        not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   constraint PK_CSA_PROFILES_LOCK_CHG071536 primary key (ID)
)

go

create sequence S_CSA_PROFILES_LOCK_CHG071536
go

/*==============================================================*/
/* Index: CSA_CHG071536_PROFILE_ID                              */
/*==============================================================*/
create index CSA_CHG071536_PROFILE_ID on CSA_PROFILES_LOCK_CHG071536 (
   PROFILE_ID ASC
)
go

/*==============================================================*/
/* Table: CSA_PROFILE_NODES                                     */
/*==============================================================*/
create table CSA_PROFILE_NODES 
(
   ID                   INTEGER              not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   STATE                VARCHAR2(20)         not null,
   PROFILE_TYPE         VARCHAR2(20)         not null,
   PROFILE_ID           INTEGER              not null,
   NODE_ID              INTEGER              not null,
   constraint PK_CSA_PROFILE_NODES primary key (ID)
)

go

create sequence S_CSA_PROFILE_NODES
go

/*==============================================================*/
/* Index: I_CSA_PROFILE_NODES_STATE                             */
/*==============================================================*/
create index I_CSA_PROFILE_NODES_STATE on CSA_PROFILE_NODES (
   STATE ASC
)
go

/*==============================================================*/
/* Table: CSA_PROMOCLIENT_LOG                                   */
/*==============================================================*/
create table CSA_PROMOCLIENT_LOG 
(
   ID                   NUMBER(6)            not null,
   PROMO_SESSION_ID     INTEGER              not null,
   PROFILE_ID           INTEGER              not null,
   CREATION_DATE        TIMESTAMP            not null,
   BEFORE_CREATION_DATE TIMESTAMP,
   constraint PK_CSA_PROMOCLIENT_LOG primary key (ID)
)

go

create sequence S_CSA_PROMOCLIENT_LOG
go

comment on table CSA_PROMOCLIENT_LOG is
'Таблица регистрации входов клиентов, привлеченных промоутерами'
go

comment on column CSA_PROMOCLIENT_LOG.ID is
'ID записи'
go

comment on column CSA_PROMOCLIENT_LOG.PROMO_SESSION_ID is
'ID смены промоутера'
go

comment on column CSA_PROMOCLIENT_LOG.PROFILE_ID is
'ID профиля клиента'
go

comment on column CSA_PROMOCLIENT_LOG.CREATION_DATE is
'Дата входа клиента'
go

comment on column CSA_PROMOCLIENT_LOG.BEFORE_CREATION_DATE is
'Дата последнего входа перед данным'
go

/*==============================================================*/
/* Index: CSA_PROMCLNT_PROM_SESID_INDX                          */
/*==============================================================*/
create index CSA_PROMCLNT_PROM_SESID_INDX on CSA_PROMOCLIENT_LOG (
   PROMO_SESSION_ID ASC
)
go

/*==============================================================*/
/* Table: CSA_PROMOTER_SESSIONS                                 */
/*==============================================================*/
create table CSA_PROMOTER_SESSIONS 
(
   SESSION_ID           NUMBER(6)            not null,
   CREATION_DATE        TIMESTAMP            not null,
   CLOSE_DATE           TIMESTAMP,
   CHANNEL              VARCHAR2(10)         not null,
   TB                   VARCHAR2(4)          not null,
   OSB                  VARCHAR2(4)          not null,
   OFFICE               VARCHAR(7),
   PROMOTER             VARCHAR2(100)        not null,
   constraint PK_CSA_PROMOTER_SESSIONS primary key (SESSION_ID)
)

go

create sequence S_CSA_PROMOTER_SESSIONS
go

comment on table CSA_PROMOTER_SESSIONS is
'Таблица хранит историю открытия/закрытия смен промоутеров'
go

comment on column CSA_PROMOTER_SESSIONS.SESSION_ID is
'Идентификатор смены промотера'
go

comment on column CSA_PROMOTER_SESSIONS.CREATION_DATE is
'Время начала смены'
go

comment on column CSA_PROMOTER_SESSIONS.CLOSE_DATE is
'Время окончания смены'
go

comment on column CSA_PROMOTER_SESSIONS.CHANNEL is
'Канал'
go

comment on column CSA_PROMOTER_SESSIONS.TB is
'Террбанк'
go

comment on column CSA_PROMOTER_SESSIONS.OSB is
'ОСБ'
go

comment on column CSA_PROMOTER_SESSIONS.OFFICE is
'ВСП'
go

comment on column CSA_PROMOTER_SESSIONS.PROMOTER is
'Идентификатор промоутера'
go

/*==============================================================*/
/* Index: CSA_PROMOTER_SESSIONS_INDEX                           */
/*==============================================================*/
create index CSA_PROMOTER_SESSIONS_INDEX on CSA_PROMOTER_SESSIONS (
   TB ASC,
   OSB ASC,
   OFFICE ASC
)
go

/*==============================================================*/
/* Index: CSA_PROMO_SES_CREATE_DATE_INDX                        */
/*==============================================================*/
create index CSA_PROMO_SES_CREATE_DATE_INDX on CSA_PROMOTER_SESSIONS (
   CREATION_DATE ASC
)
go

/*==============================================================*/
/* Table: CSA_SESSIONS                                          */
/*==============================================================*/
create table CSA_SESSIONS 
(
   SID                  VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            not null,
   CLOSE_DATE           TIMESTAMP,
   STATE                VARCHAR2(40)         not null,
   CONNECTOR            VARCHAR2(32)         not null,
   PREV_SESSION_ID      VARCHAR2(32),
   PREV_SESSION_DATE    TIMESTAMP,
   constraint PK_CSA_SESSIONS primary key (SID)
)
partition by range
 (CREATION_DATE)
 (partition
         P_MAXVALUE
        values less than (maxvalue ))
go

/*==============================================================*/
/* Index: CSA_SESSIONS_CSD                                      */
/*==============================================================*/
create index CSA_SESSIONS_CSD on CSA_SESSIONS (
   CONNECTOR ASC,
   STATE ASC,
   CREATION_DATE ASC
)
local
go

/*==============================================================*/
/* Table: CSA_STORED_PASSWORDS                                  */
/*==============================================================*/
create table CSA_STORED_PASSWORDS 
(
   ID                   INTEGER              not null,
   LOGIN                VARCHAR2(10)         not null,
   HASH                 VARCHAR2(64)         not null,
   SALT                 VARCHAR2(32)         not null,
   CHANGED              TIMESTAMP            not null,
   constraint PK_CSA_STORED_PASSWORDS primary key (ID)
)

go

create sequence S_CSA_STORED_PASSWORDS
go

/*==============================================================*/
/* Index: CSA_STORED_PASSWORD_U_LOGIN                           */
/*==============================================================*/
create unique index CSA_STORED_PASSWORD_U_LOGIN on CSA_STORED_PASSWORDS (
   LOGIN ASC
)
go

/*==============================================================*/
/* Table: CSA_TABS                                              */
/*==============================================================*/
create table CSA_TABS 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(32),
   TEXT                 CLOB,
   constraint PK_CSA_TABS primary key (ID)
)

go

create sequence S_CSA_TABS
go

/*==============================================================*/
/* Table: ERIB_LOCALES                                          */
/*==============================================================*/
create table ERIB_LOCALES 
(
   ID                   varchar2(30)         not null,
   NAME                 VARCHAR2(100)        not null,
   IMAGE_ID             INTEGER              not null,
   ERIB_AVAILABLE       CHAR(1)              not null,
   MAPI_AVAILABLE       CHAR(1)              not null,
   ATMAPI_AVAILABLE     CHAR(1)              not null,
   WEBAPI_AVAILABLE     CHAR(1)              not null,
   ERMB_AVAILABLE       CHAR(1)              not null,
   STATE                VARCHAR2(10)         not null,
   ACTUAL_DATE          TIMESTAMP            not null,
   constraint PK_ERIB_LOCALES primary key (ID)
)
go

/*==============================================================*/
/* Table: ERIB_STATIC_MESSAGE                                   */
/*==============================================================*/
create table ERIB_STATIC_MESSAGE 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   BUNDLE               VARCHAR2(40)         not null,
   KEY                  VARCHAR2(200)        not null,
   MESSAGE              VARCHAR2(2048)       not null,
   constraint PK_ERIB_STATIC_MESSAGE primary key (ID)
)

go

create sequence S_ERIB_STATIC_MESSAGE
go

/*==============================================================*/
/* Index: I_ERIB_STATIC_MESSAGE_LBK                             */
/*==============================================================*/
create index I_ERIB_STATIC_MESSAGE_LBK on ERIB_STATIC_MESSAGE (
   LOCALE_ID ASC,
   BUNDLE ASC,
   KEY ASC
)
go

/*==============================================================*/
/* Table: GUEST_OPERATIONS                                      */
/*==============================================================*/
create table GUEST_OPERATIONS 
(
   OUID                 VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            default SYSDATE not null,
   PHONE                VARCHAR2(16)         not null,
   TYPE                 VARCHAR2(40)         not null,
   STATE                VARCHAR2(40)         not null,
   AUTH_CODE            VARCHAR2(40),
   AUTH_ERRORS          INTEGER,
   IP_ADDRESS           VARCHAR2(15),
   INFO                 VARCHAR2(4000),
   PARAMS               VARCHAR2(4000)
)
partition by range (CREATION_DATE) interval (NUMTOYMINTERVAL(1,'MONTH')) 
subpartition by hash (PHONE) subpartitions 64
(
       partition P_2015_02 values less than (timestamp '2015-02-01 00:00:00')
)
go

/*==============================================================*/
/* Index: IDX_GUEST_OPERATIONS_OOUD                             */
/*==============================================================*/
create unique index IDX_GUEST_OPERATIONS_OOUD on GUEST_OPERATIONS (
   OUID ASC
)
global partition by hash
 (OUID)
 partitions 64
go

/*==============================================================*/
/* Table: GUEST_PASSWORDS                                       */
/*==============================================================*/
create table GUEST_PASSWORDS 
(
   ID                   INTEGER              not null,
   VALUE                VARCHAR2(40)         not null,
   SALT                 VARCHAR2(32)         not null,
   CREATION_DATE        TIMESTAMP            not null,
   ACTIVE               CHAR(1)              not null,
   GUEST_ID             INTEGER              not null,
   constraint PK_GUEST_PASSWORDS primary key (ID)
)
partition by range
 (CREATION_DATE)
    interval (NUMTOYMINTERVAL(1,'MONTH'))
 (partition
         P_START
        values less than (to_date('01-02-2015','DD-MM-YYYY')))
go

create sequence S_GUEST_PASSWORDS
go

/*==============================================================*/
/* Index: IDX_GUEST_DATE_PASSWORDS                              */
/*==============================================================*/
create index IDX_GUEST_DATE_PASSWORDS on GUEST_PASSWORDS (
   GUEST_ID ASC,
   CREATION_DATE ASC
)
go

/*==============================================================*/
/* Table: GUEST_PROFILES                                        */
/*==============================================================*/
create table GUEST_PROFILES 
(
   ID                   INTEGER              not null,
   PHONE                VARCHAR2(16)         not null,
   CODE                 INTEGER,
   BLOCKED_UNTIL        TIMESTAMP,
   AUTH_ERRORS          INTEGER,
   FIRST_NAME           VARCHAR2(100),
   SUR_NAME             VARCHAR2(100),
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100),
   BIRTHDATE            TIMESTAMP,
   TB                   VARCHAR2(4),
   constraint PK_GUEST_PROFILES primary key (ID)
)
partition by hash
 (PHONE)
 partitions 64
go

/*==============================================================*/
/* Index: IDX_GUEST_PROFILES_PHONE                              */
/*==============================================================*/
create unique index IDX_GUEST_PROFILES_PHONE on GUEST_PROFILES (
   PHONE ASC
)
local
go

/*==============================================================*/
/* Table: LIST_PROPERTIES                                       */
/*==============================================================*/
create table LIST_PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_ID          INTEGER              not null,
   VALUE                VARCHAR2(200),
   constraint PK_LIST_PROPERTIES primary key (ID)
)

go

create sequence S_LIST_PROPERTIES
go

/*==============================================================*/
/* Table: MBK_CACHE_CARDS_BY_PHONE                              */
/*==============================================================*/
create table MBK_CACHE_CARDS_BY_PHONE 
(
   PHONE_NUMBER         VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   RESULT_SET           CLOB,
   constraint PK_MBK_CACHE_CARDS_BY_PHONE primary key (PHONE_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_CLIENT_BY_CARD                              */
/*==============================================================*/
create table MBK_CACHE_CLIENT_BY_CARD 
(
   CARD_NUMBER          VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   FATHERS_NAME         VARCHAR2(100),
   LAST_NAME            VARCHAR2(100)        not null,
   REG_NUMBER           VARCHAR2(20)         not null,
   BIRTH_DATE           TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   AUTH_IDT             VARCHAR2(10)         not null,
   CONTR_STATUS         INTEGER              not null,
   ADD_INFO_CN          INTEGER              not null,
   constraint PK_MBK_CACHE_CLIENT_BY_CARD primary key (CARD_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_CLIENT_BY_LOGIN                             */
/*==============================================================*/
create table MBK_CACHE_CLIENT_BY_LOGIN 
(
   AUTH_IDT             VARCHAR2(10)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   FATHERS_NAME         VARCHAR2(100),
   LAST_NAME            VARCHAR2(100)        not null,
   REG_NUMBER           VARCHAR2(20)         not null,
   BIRTH_DATE           TIMESTAMP            not null,
   CB_CODE              VARCHAR2(20)         not null,
   CARD_NUMBER          VARCHAR2(20)         not null,
   CONTR_STATUS         INTEGER              not null,
   ADD_INFO_CN          INTEGER              not null,
   constraint PK_MBK_CACHE_CLIENT_BY_LOGIN primary key (AUTH_IDT)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_IMSI_CHECK_RESULT                           */
/*==============================================================*/
create table MBK_CACHE_IMSI_CHECK_RESULT 
(
   PHONE_NUMBER         VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   MESSAGE_ID           INTEGER              not null,
   VALIDATION_RESULT    INTEGER,
   constraint PK_MBK_CACHE_IMSI_CHECK_RESULT primary key (PHONE_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_REGISTRATIONS                               */
/*==============================================================*/
create table MBK_CACHE_REGISTRATIONS 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS primary key (STR_CARD_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_REGISTRATIONS2                              */
/*==============================================================*/
create table MBK_CACHE_REGISTRATIONS2 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS2 primary key (STR_CARD_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_REGISTRATIONS3                              */
/*==============================================================*/
create table MBK_CACHE_REGISTRATIONS3 
(
   STR_CARD_NUMBER      VARCHAR2(20)         not null,
   REQUEST_TIME         TIMESTAMP            not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REGISTRATIONS3 primary key (STR_CARD_NUMBER)
)
go

/*==============================================================*/
/* Table: MBK_CACHE_REGISTRATIONS_PACK                          */
/*==============================================================*/
create table MBK_CACHE_REGISTRATIONS_PACK 
(
   STR_CARDS            VARCHAR2(2000)       not null,
   REQUEST_TIME         TIMESTAMP(6)         not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REG_PACK primary key (STR_CARDS)
)

go

create sequence S_MBK_CACHE_REGISTRATIONS_PACK
go

/*==============================================================*/
/* Table: MBK_CACHE_REGISTRATIONS_PACK3                         */
/*==============================================================*/
create table MBK_CACHE_REGISTRATIONS_PACK3 
(
   STR_CARDS            VARCHAR2(2000)       not null,
   REQUEST_TIME         TIMESTAMP(6)         not null,
   STR_RET_STR          CLOB,
   constraint PK_MBK_CACHE_REG_PACK3 primary key (STR_CARDS)
)

go

create sequence S_MBK_CACHE_REGISTRATIONS_PACK3
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
   constraint PK_NEWS primary key (ID)
)

go

create sequence S_NEWS
go

/*==============================================================*/
/* Index: INDEX_DATE_6                                          */
/*==============================================================*/
create index INDEX_DATE_6 on NEWS (
   NEWS_DATE DESC
)
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
/* Table: PROFILE_MDM_IDS                                       */
/*==============================================================*/
create table PROFILE_MDM_IDS 
(
   CSA_PROFILE_ID       INTEGER,
   MDM_ID               VARCHAR2(64)
)
go

/*==============================================================*/
/* Index: U_CSA_PROFILE_ID                                      */
/*==============================================================*/
create unique index U_CSA_PROFILE_ID on PROFILE_MDM_IDS (
   CSA_PROFILE_ID ASC
)
go

/*==============================================================*/
/* Table: PROPERTIES                                            */
/*==============================================================*/
create table PROPERTIES 
(
   ID                   INTEGER              not null,
   PROPERTY_KEY         VARCHAR2(256)        not null,
   PROPERTY_VALUE       VARCHAR2(500),
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
/* Table: PUSH_PARAMS                                           */
/*==============================================================*/
create table PUSH_PARAMS 
(
   ID                   INTEGER              not null,
   TYPE                 VARCHAR(20)          not null,
   KEY                  VARCHAR(255)         not null,
   TEXT                 CLOB                 not null,
   SHORT_TEXT           VARCHAR(255)         not null,
   DESCRIPTION          VARCHAR(255),
   PRIORITY             INTEGER,
   CODE                 NUMBER(2)            not null,
   PRIVACY_TYPE         VARCHAR(20)          not null,
   PUBLICITY_TYPE       VARCHAR(20)          not null,
   SMS_BACKUP           CHAR(1)              not null,
   VARIABLES            VARCHAR(4000),
   LAST_MODIFIED        TIMESTAMP,
   PREVIOUS_TEXT        CLOB,
   EMPLOYEE_LOGIN_ID    INTEGER,
   ATTRIBUTES           CLOB,
   constraint PK_PUSH_PARAMS primary key (ID)
)

go

create sequence S_PUSH_PARAMS
go

/*==============================================================*/
/* Index: I_PUSH_PARAMS_KEY                                     */
/*==============================================================*/
create unique index I_PUSH_PARAMS_KEY on PUSH_PARAMS (
   KEY ASC
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
   EN_NAME              VARCHAR2(128),
   constraint PK_REGIONS primary key (ID)
)

go

create sequence S_REGIONS
go

/*==============================================================*/
/* Index: INDEX_CODE                                            */
/*==============================================================*/
create unique index INDEX_CODE on REGIONS (
   CODE ASC
)
go

/*==============================================================*/
/* Table: SMS_RESOURCES                                         */
/*==============================================================*/
create table SMS_RESOURCES 
(
   ID                   INTEGER              not null,
   KEY                  VARCHAR2(255)        not null,
   TEXT                 CLOB                 not null,
   DESCRIPTION          VARCHAR2(255),
   SMS_TYPE             VARCHAR2(13)         not null,
   LAST_MODIFIED        TIMESTAMP,
   PREVIOUS_TEXT        CLOB,
   EMPLOYEE_LOGIN_ID    INTEGER,
   VARIABLES            VARCHAR2(4000),
   constraint PK_SMS_RESOURCES primary key (ID)
)

go

create sequence S_SMS_RESOURCES
go

/*==============================================================*/
/* Index: INDEX_SMS_KEY                                         */
/*==============================================================*/
create unique index INDEX_SMS_KEY on SMS_RESOURCES (
   KEY ASC
)
go

/*==============================================================*/
/* Table: SMS_RESOURCES_RES                                     */
/*==============================================================*/
create table SMS_RESOURCES_RES 
(
   ID                   INTEGER              not null,
   LOCALE_ID            VARCHAR2(30)         not null,
   TEXT                 CLOB                 not null,
   constraint PK_SMS_RESOURCES_RES primary key (ID, LOCALE_ID)
)
go

/*==============================================================*/
/* Table: TECHNOBREAKS                                          */
/*==============================================================*/
create table TECHNOBREAKS 
(
   ID                   INTEGER              not null,
   ADAPTER_UUID         VARCHAR2(64)         not null,
   FROM_DATE            TIMESTAMP            not null,
   TO_DATE              TIMESTAMP            not null,
   PERIODIC             VARCHAR2(15)         not null,
   IS_DEFAULT_MESSAGE   CHAR(1)              default '1' not null,
   MESSAGE              VARCHAR2(200)        not null,
   STATUS               VARCHAR2(10)         not null,
   IS_AUTO_ENABLED      char(1)              default '0' not null,
   IS_ALLOWED_OFFLINE_PAY CHAR(1)              default '0' not null,
   UUID                 VARCHAR2(32)         not null,
   constraint PK_TECHNOBREAKS primary key (ID)
)

go

create sequence S_TECHNOBREAKS
go

/*==============================================================*/
/* Index: I_TECHNOBREAKS                                        */
/*==============================================================*/
create index I_TECHNOBREAKS on TECHNOBREAKS (
   ADAPTER_UUID ASC,
   STATUS ASC,
   TO_DATE ASC
)
go

/*==============================================================*/
/* Index: UI_TECHNOBREAKS                                       */
/*==============================================================*/
create unique index UI_TECHNOBREAKS on TECHNOBREAKS (
   UUID ASC
)
go

/*==============================================================*/
/* Table: USERS_REGIONS                                         */
/*==============================================================*/
create table USERS_REGIONS 
(
   CODE                 VARCHAR2(20),
   COOKIE               VARCHAR(32)          not null,
   PROFILE_ID           NUMBER               not null,
   constraint PK_USERS_REGIONS primary key (PROFILE_ID)
)
go

/*==============================================================*/
/* Index: INDEX_COOKIE                                          */
/*==============================================================*/
create unique index INDEX_COOKIE on USERS_REGIONS (
   COOKIE ASC
)
go

/*==============================================================*/
/* Index: INDEX_PROFILE_ID                                      */
/*==============================================================*/
create index INDEX_PROFILE_ID on USERS_REGIONS (
   PROFILE_ID ASC
)
go

/*==============================================================*/
/* Table: WAY4_NOTIFICATION_JOURNAL                             */
/*==============================================================*/
create table WAY4_NOTIFICATION_JOURNAL 
(
   ID                   INTEGER              not null,
   CLIENTID             VARCHAR2(100)        not null,
   FIRST_NAME           VARCHAR2(100)        not null,
   SUR_NAME             VARCHAR2(100)        not null,
   PATR_NAME            VARCHAR2(100),
   PASSPORT             VARCHAR2(100)        not null,
   CB_CODE              VARCHAR2(20)         not null,
   BIRTHDATE            TIMESTAMP            not null,
   AMND_DATE            TIMESTAMP            not null
)
partition by range (AMND_DATE) interval (NUMTOYMINTERVAL(1,'MONTH'))
subpartition by hash (CLIENTID) subpartitions 64
(
    partition P_FIRST values less than (to_date('01-01-2014','DD-MM-YYYY'))
)
go

create sequence S_WAY4_NOTIFICATION_JOURNAL cache 10000
go

/*==============================================================*/
/* Index: WAY4_NOTIF_CLIENTID_DATE_INDEX                        */
/*==============================================================*/
create index WAY4_NOTIF_CLIENTID_DATE_INDEX on WAY4_NOTIFICATION_JOURNAL (
   CLIENTID ASC,
   AMND_DATE DESC
)
local
go

alter table CLIENT_IDS
   add constraint FK_CLIENT_IDS_TO_PROFILES foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go


create index "DXFK_CSA_CONN_FK_CSA_CO_CSA_PR" on CSA_CONNECTORS
(
   PROFILE_ID
)
go

alter table CSA_CONNECTORS
   add constraint FK_CSA_CONN_FK_CSA_CO_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go

alter table CSA_LOGINS
   add constraint FK_CSA_LOGINS_TO_CONNECTORS foreign key (CONNECTOR_ID)
      references CSA_CONNECTORS (ID)
go

alter table CSA_LOGINS
   add constraint FK_CSA_LOGINS_TO_GUEST foreign key (GUEST_ID)
      references GUEST_PROFILES (ID)
go

alter table CSA_OPERATIONS
   add constraint FK_CSA_OPER_FK_CSA_OP_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go


create index "DXFK_CSA_PASS_FK_CSA_PA_CSA_CO" on CSA_PASSWORDS
(
   CONNECTOR_ID
)
go

alter table CSA_PASSWORDS
   add constraint FK_CSA_PASS_FK_CSA_PA_CSA_CONN foreign key (CONNECTOR_ID)
      references CSA_CONNECTORS (ID)
go


create index "DXFK_CSA_PROF_REFERENCE_CSA_PR" on CSA_PROFILES_HISTORY
(
   PROFILE_ID
)
go

alter table CSA_PROFILES_HISTORY
   add constraint FK_CSA_PROF_FK_CSA_PR_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go


create index "DXREFERENCE_5" on CSA_PROFILES_LOCK
(
   PROFILE_ID
)
go

alter table CSA_PROFILES_LOCK
   add constraint FK_CSA_PROF_REFERENCE_CSA_PROF foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go


create index "DXFK_CSA_PROFILENODES_TO_NODE" on CSA_PROFILE_NODES
(
   NODE_ID
)
go

alter table CSA_PROFILE_NODES
   add constraint FK_CSA_PROFILENODES_TO_NODE foreign key (NODE_ID)
      references CSA_NODES (ID)
go


create index "DXFK_CSA_PROFILENODES_TO_PROFI" on CSA_PROFILE_NODES
(
   PROFILE_ID
)
go

alter table CSA_PROFILE_NODES
   add constraint FK_CSA_PROFILENODES_TO_PROFILE foreign key (PROFILE_ID)
      references CSA_PROFILES (ID)
go

alter table GUEST_PASSWORDS
   add constraint FK_GUEST_PASS_TO_PROFILES foreign key (GUEST_ID)
      references GUEST_PROFILES (ID)
go


create index "DXFK_LIST_PROPERTIES" on LIST_PROPERTIES
(
   PROPERTY_ID
)
go

alter table LIST_PROPERTIES
   add constraint FK_LIST_PROPERTIES foreign key (PROPERTY_ID)
      references PROPERTIES (ID)
      on delete cascade
go

alter table PROFILE_MDM_IDS
   add constraint FK_P_MDM_TO_PROFILE foreign key (CSA_PROFILE_ID)
      references CSA_PROFILES (ID)
      on delete cascade
go

/*==============================================================*/
/* Database package: CSA_CONNECTORS_PKG                         */
/*==============================================================*/
create or replace package CSA_CONNECTORS_PKG as
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2);
end CSA_CONNECTORS_PKG;
go

create or replace package body CSA_CONNECTORS_PKG as
   procedure SetPromoClientSessionLog (pConnectorGUID in varchar2,pOperationUID in varchar2,pPromoterSessionId in varchar2,pPromoClientLogId out varchar2) as
   l_profile_id number;
   l_before_creation_date timestamp;
   begin
     if nvl(pConnectorGUID, pOperationUID) is null or pPromoterSessionId is null then
       RAISE_APPLICATION_ERROR(-20900,'Ошибка входных дaнных при вызове SetPromoClientSessionLog');
     end if;
   
     if (pConnectorGUID is not null) then
       select max(con.profile_id), max(con.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_connectors con
        where con.guid = pConnectorGUID;
     else
       select max(op.profile_id), max(c.last_session_date)
         into l_Profile_id, l_before_creation_date
         from csa_operations op
            , csa_connectors c
        where op.ouid = pOperationUID
          and c.profile_id = op.profile_id
          and c.state = 'ACTIVE';
     end if;
     
     if l_Profile_id is null then
       RAISE_APPLICATION_ERROR(-20900,'Не обнаружен коннектор или идентификатор профиля (profile_id) клиента');
     end if;
     
     insert into csa_promoclient_log (id, promo_session_id, profile_id, creation_date, before_creation_date)
          values (s_csa_promoclient_log.nextval, pPromoterSessionId, l_Profile_id, SYSTIMESTAMP, l_before_creation_date)
     returning id into pPromoClientLogId;
   end;
end CSA_CONNECTORS_PKG;
go
