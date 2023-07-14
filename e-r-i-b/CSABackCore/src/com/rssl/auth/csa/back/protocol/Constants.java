package com.rssl.auth.csa.back.protocol;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public interface Constants
{
	String MESSAGE_TAG = "message";
	String MESSAGE_UID_TAG = "UID";
	String MESSAGE_DATE_TAG = "date";
	String MESSAGE_SOURCE_TAG = "source";
	String MESSAGE_VERSION_TAG = "version";
	String MESSAGE_IP_TAG = "IP";
	String MESSAGE_STATUS_TAG = "status";
	String MESSAGE_STATUS_CODE_TAG = "code";
	String MESSAGE_STATUS_DESCRIPTION_TAG = "description";

	String OUID_TAG = "OUID";
	String USER_LOGON_TYPE = "userLogonType";
	String AUTH_TOKEN_TAG = "authToken";
	String CONFIRM_PARAMETERS_TAG = "confirmParameters";
	String PHONES_TAG = "phones"; 
	String TIMEOUT_TAG = "timeout";
	String ATTEMPTS_TAG = "attempts";
	String PASSWORD_NUMBER_TAG = "passwordNo";
	String RECEIPT_NUMBER_TAG  = "receiptNo";
	String PASSWORDS_LEFT_TAG  = "passwordsLeft";
	String CARD_NUMBER_TAG = "cardNumber";
	String LOGIN_TAG = "login";
	String PROFILE_ID_TAG = "profile_id";
	String PASSWORD_TAG = "password";
	String CONFIRMATION_CODE_TAG = "confirmationCode";
	String USER_INFO_TAG = "userInfo";
	String DEVICE_INFO_TAG = "deviceInfo";
	String HOST_TAG = "host";
	String LISTENER_HOST_TAG = "listenerHost";

	String NEED_NOTIFICATION_TAG = "notification";
	String SAME_LOGIN_TAG = "sameLogin";
	String CHECK_PASSWORD_PARAM_NAME = "checkPassword";

	String SURNAME_TAG = "surname";
	String FIRSTNAME_TAG = "firstname";
	String PATRNAME_TAG = "patrname";
	String BIRTHDATE_TAG = "birthdate";
	String CB_CODE_TAG = "cbCode";
	String PASSPORT_TAG = "passport";
	String USER_ID_TAG = "userId";
	String GUID_TAG = "GUID";
	String IS_NEW_PASSWORD_TAG = "newPassword";
	String CONNECTORS_TAG = "connectors";
	String CONNECTOR_INFO_TAG = "connectorInfo";
	String DEVICE_STATE_TAG = "deviceState";
	String SESSION_INFO_TAG = "sessionInfo";
	String LAST_SESSION_DATE = "currentSessionDate";
	String SID_TAG = "SID";
	String CREATION_DATE_TAG = "creationDate";
	String EXPIRE_DATE_TAG = "expireDate";
	String PREV_SESSION_DATE_TAG = "prevSessionDate";
	String PREV_SID_TAG = "prevSID";
	String TYPE_TAG = "type";
	String PASSWORD_CREATION_DATE_TAG = "passwordCreationDate";
	String BLOCKING_TIMEOUT = "blockingTimeout";
	String CONFIRMATION_PARAM_NAME  = "confirmationType";
	String EXTERNAL_ID_PARAM_NAME   = "externalId";
	String REGISTRATION_STATUS_TAG = "registrationStatus";
	String REGISTRATION_IPAS_TAG = "registrationIPasAvailable";
	String REGISTRATION_TYPE_TAG = "registrationType";
	String PHONE_NUMBER_TAG = "phoneNumber";
	String USING_CARD_BY_PHONE_TAG = "usingCardByPhone";
	String PROFILE_INFO_TAG = "profileInfo";
	String MAIN_PHONE_TAG = "mainPhone";
	String REMOVE_PHONES_TAG = "removePhones";
	String REMOVE_PHONE_TAG = "removePhone";
	String ADD_PHONES_TAG = "addPhones";
	String ADD_PHONE_TAG = "addPhone";
	String MOBILE_SDK_DATA_TAG = "mobileSdkData";

	String CONFIRMATION_INFO_TAG   = "confirmationInfo";
	String PREFERRED_CONFIRM_TYPE_TAG = "preferredConfirmType";
	String PUSH_ALLOWED_TAG = "pushAllowed";
	String CARD_CONFIRMATION_SOURCE_TAG = "cardConfirmationSource";

	String ID_TAG = "id";
	String DEVICE_ID_TAG = "devId";
    String CARD_TAG = "card";
	String APP_NAME_TAG = "appName";
	String MOBILE_API_VERSION_TAG = "version";
	String AUTHORIZED_ZONE_PARAM_NAME = "authorizedZone";
	String PUSH_SUPPORTED_TAG = "pushSupported";
	String LOCKS_TAG = "locks";
	String LOCK_INFO_TAG = "lockInfo";
	String LOCK_FROM_TAG = "lockFrom";
	String LOCK_TO_TAG = "lockTo";
	String REASON_TAG = "reason";
	String LOCKER_FIO_TAG = "lockerFIO";
	String TB_TAG = "tb";
	String IS_SOCIAL = "isSocial";
	String MDM_ID_TAG = "mdm_id";

	String NODES_TAG = "nodes";
	String NODE_INFO_TAG = "nodeInfo";
	String WAIT_MIGRATION_NODE_ID_TAG = "waitMigrationNodeId";
	String NODE_ID_TAG = "nodeId";
	String SMS_QUEUE_NAME_TAG = "smsQueueName";
	String SMS_FACTORY_NAME_TAG  = "smsFactoryName";
	String ERMB_QUEUE_NAME_TAG = "ermbQueueName";
	String ERMB_FACTORY_NAME_TAG  = "ermbFactoryName";
	String DICTIONARY_QUEUE_NAME_TAG = "dictionaryQueueName";
	String DICTIONARY_FACTORY_NAME_TAG = "dictionaryFactoryName";
	String MULTI_NODE_DATA_QUEUE_NAME_TAG = "multiNodeDataQueueName";
	String MULTI_NODE_DATA_FACTORY_NAME_TAG = "multiNodeDataFactoryName";
	String MBK_REGISTRATION_QUEUE_NAME_TAG      = "MbkRegistrationQueueName";
	String MBK_REGISTRATION_FACTORY_NAME_TAG    = "MbkRegistrationFactoryName";

	String PROFILE_NODE_INFO_TAG = "profileNodeInfo";
	String MIGRATION_STATE_TAG = "migrationState";
	String PROFILE_TYPE_TAG = "profileType";

	String RSA_DATA_TAG = "RSAData";

	String UPDATE_PROFILE_INFO_TAG  = "updateProfileInfo";
	String NEW_USER_INFO_TAG = "newUserInfo";
	String OLD_USER_INFO_TAG = "oldUserInfo";
	String OLD_USER_INFO_LIST_TAG = "oldUserInfoList";
	String USER_INFO_LIST_TAG = "userInfoList";

	String HISTORY_TAG = "history";
	String HISTORY_ITEM_TAG = "historyItem";

	String EMPLOYEE_NAME = "employeeName";
	String EMPLOYEE_GUID = "employeeGUID";
	String SECURITY_TOKEN_TAG = "securityToken";

	String INCOGNITO_SETTING_PARAM_NAME = "incognito";

	String CLIENTS_TAG = "clients";
	String CLIENT_INFO_TAG = "clientInfo";
	String LAST_LOGIN_DATE_TAG = "lastLoginDate";
	String FIO_TAG = "fio";
	String TB_LIST_TAG = "tbList";
	String CLIENT_ADDITIONAL_DATA_TAG = "clientAdditionalData";
	String CREATION_TYPE_TAG = "creationType";
	String AGREEMENT_NUMBER_TAG = "agreementNumber";

	String PROFILE_HISTORY_TAG = "profileHistory";
	String PROFILE_HISTORY_INFO_TAG = "profileHistoryInfo";

	String SEND_SMS_TAG = "sendSMS";

	String SECURITY_TYPE_TAG = "securityType";

	String ERMB_CONNECTED = "ermbConnected";
	String ERMB_STATUS = "ermbStatus";
	String NEED_CREATE_PROFILE = "needCreateProfile";
	String CREATE_PROFILE_NODE_MODE = "createProfileNodeMode";
	String CARD_NUMBER_LIST_TAG = "cardNumberListTag";

	String NODES_INFO_NODE_NAME                         = "nodes";
	String NODE_INFO_NODE_NAME                          = "node";
	String NODE_INFO_ID_NODE_NAME                       = "nodeId";
	String NODE_INFO_NAME_NODE_NAME                     = "nodeName";
	String NODE_INFO_NEW_USERS_ALLOWED_NODE_NAME        = "newUsersAllowed";
	String NODE_INFO_EXISTING_USERS_ALLOWED_NODE_NAME   = "existingUsersAllowed";
	String NODE_INFO_TEMPORARY_USERS_ALLOWED_NODE_NAME  = "temporaryUsersAllowed";
	String NODE_INFO_USERS_TRANSFER_ALLOWED_NODE_NAME   = "usersTransferAllowed";
	String NODE_INFO_ADMIN_AVAILABLE_NODE_NAME          = "adminAvailable";
	String NODE_INFO_GUEST_AVAILABLE_NODE_NAME          = "guestAvailable";

	String COUNT_NODE_NAME = "count";
	String PROFILE_NODE_STATE_TAG = "profileNodeState";
	String PROFILE_NODE_TYPE_TAG = "profileNodeType";

	String TECHNO_BREAK_UUID = "technoBreakUUID";
	String TECHNO_BREAK_FROM_DATE = "technoBreakFromDate";
	String TECHNO_BREAK_TO_DATE = "technoBreakToDate";
	String TECHNO_BREAK_MESSAGE = "technoBreakMessage";
	String TECHNO_BREAK_STATUS = "technoBreakStatus";
	String TECHNO_BREAK_PERIODIC = "technoBreakPeriodic";
	String TECHNO_BREAK_IS_AUTO = "technoBreakIsAuto";

	String GUEST_INFO_TAG       = "guestInfo";
	String GUEST_PAPAMS_TAG     = "guestParams";
	String GUEST_PROFILE_ID_TAG = "guestProfileId";
	String PHONE_CONNECT_MB_TAG = "phoneConnectMB";
	String GUEST_CODE_TAG       = "guestCode";
	String GUEST_LOGON_TYPE_TAG = "guestLogonType";
	String GUEST_LOGIN          = "guestLogin";

	String IGNORE_IMSI_CHEK = "ignoreImsiCheck";

	String CONTAINS_PRO_MAPI_TAG = "containsProMAPI";
	String BLOCKING_RULES_DATE = "RestrictionDate";
}
