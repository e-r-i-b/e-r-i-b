package com.rssl.auth.csa.wsclient;

/**
 * @author osminin
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 *
 * константы для ответов ЦСА
 */
public interface CSAResponseConstants
{
	String CONNECTOR_INFO_TAG                   = "connectorInfo";
	String DEVICE_STATE_TAG                     = "deviceState";
	String DEVICE_INFO_TAG                      = "deviceInfo";
	String DEVICE_ID_TAG                        = "devId";
	String CB_CODE_TAG                          = "cbCode";
	String USER_ID_TAG                          = "userId";
	String CARD_NUMBER_TAG                      = "cardNumber";
	String LOGIN_TAG                            = "login";
	String PROFILE_ID_TAG                       = "profile_id";
	String TYPE_TAG                             = "type";
	String CREATION_DATE_TAG                    = "creationDate";
	String PASSWORD_CREATION_DATE_TAG           = "passwordCreationDate";
	String GUID_TAG                             = "GUID";
	String REGISTRATION_STATUS_TAG              = "registrationStatus";
	String REGISTRATION_TYPE_TAG                = "registrationType";
	String IS_LIGHT_SCHEME_TAG                  = "isLightScheme";
	String LAST_SESSION_DATE                    = "currentSessionDate";

	String USER_INFO_TAG                        = "userInfo";
	String PROFILE_INFO_TAG                     = "profileInfo";
	String FIRSTNAME_TAG                        = "firstname";
	String PATRNAME_TAG                         = "patrname";
	String SURNAME_TAG                          = "surname";
	String BIRTHDATE_TAG                        = "birthdate";
	String PASSPORT_TAG                         = "passport";
	String TER_BANK_TAG                         = "tb";

	String OUID_TAG                             = "OUID";
	String AUTH_TOKEN_TAG                       = "authToken";
	String USER_LOGON_TYPE                      = "userLogonType";

	String SESSION_INFO_TAG                     = "SessionInfo";
	String SID_TAG                              = "SID";
	String EXPIRE_DATE_TAG                      = "expireDate";
	String PREV_SESSION_DATE_TAG                = "prevSessionDate";
	String PREV_SID_TAG                         = "prevSID";
	String TIMEOUT_TAG                          = "timeout";
	String ATTEMPTS_TAG                         = "attempts";
	String PASSWORD_NUMBER_TAG                  = "passwordNo";
	String RECEIPT_NUMBER_TAG                   = "receiptNo";
	String PASSWORDS_LEFT_TAG                   = "passwordsLeft";

	String CONFIRMATION_INFO_TAG                = "confirmationInfo";
	String PREFERRED_CONFIRM_TYPE_TAG           = "preferredConfirmType";
	String CARD_CONFIRMATION_SOURCE_TAG         = "cardConfirmationSource";
	String PUSH_ALLOWED_TAG                     = "pushAllowed";

	String TIMEOUT_CONFIRM_PARAM_NAME           = "Timeout";
	String ATTEMPTS_CONFIRM_PARAM_NAME          = "Attempts";
	String PASSWORD_NUMBER_CONFIRM_PARAM_NAME   = "PasswordNo";
	String RECEIPT_NUMBER_CONFIRM_PARAM_NAME    = "ReceiptNo";
	String PASSWORDS_LEFT_CONFIRM_PARAM_NAME    = "PasswordsLeft";

	String NODES_TAG                            = "nodes";
	String NODE_INFO_TAG                        = "nodeInfo";
	String NODE_ID_TAG                          = "nodeId";
	String WAIT_MIGRATION_NODE_ID_TAG           = "waitMigrationNodeId";
	String HOST_TAG                             = "host";
	String LISTENER_HOST_TAG                    = "listenerHost";
	String NODE_NAME_TAG                        = "nodeName";
	String SMS_QUEUE_NAME_TAG                   = "smsQueueName";
	String SMS_FACTORY_NAME_TAG                 = "smsFactoryName";
	String ERMB_QUEUE_NAME_TAG                  = "ermbQueueName";
	String ERMB_FACTORY_NAME_TAG                = "ermbFactoryName";
	String DICTIONARY_QUEUE_NAME_TAG            = "dictionaryQueueName";
	String DICTIONARY_FACTORY_NAME_TAG          = "dictionaryFactoryName";
	String MULTI_NODE_DATA_QUEUE_NAME_TAG       = "multiNodeDataQueueName";
	String MULTI_NODE_DATA_FACTORY_NAME_TAG     = "multiNodeDataFactoryName";
	String MBK_REGISTRATION_QUEUE_NAME_TAG      = "MbkRegistrationQueueName";
	String MBK_REGISTRATION_FACTORY_NAME_TAG    = "MbkRegistrationFactoryName";

	String MOBILE_API_VERSION_TAG   =             "version";

	String HISTORY_TAG                          = "history";
	String HISTORY_ITEM_TAG                     = "historyItem";

	String TB_TAG                               = "tb";
	String MESSAGE_STATUS                       = "status";
	String MESSAGE_ERROR_CODE                   = "code";
	String MESSAGE_ERROR_DESCRIPTION            = "description";

	String UPDATE_PROFILE_INFO_TAG              = "updateProfileInfo";

	String PROFILE_HISTORY_TAG                  = "profileHistory";
	String PROFILE_HISTORY_INFO_TAG             = "profileHistoryInfo";

	String AUTHORIZED_ZONE_PARAM_NAME           = "authorizedZone";

	String MOBILE_SDK_DATA_PARAM_NAME           = "mobileSdkData";

	String SECURITY_TYPE_TAG                    = "securityType";

	String PROFILE_NODE_INFO_TAG                = "profileNodeInfo";
	String MIGRATION_STATE_TAG                  = "migrationState";
	String PROFILE_TYPE_TAG                     = "profileType";

	String ERMB_CONNECTED = "ermbConnected";
	String ID_TAG = "id";
	String PUSH_SUPPORTED_TAG = "pushSupported";
	String CARD_NUMBER_LIST_TAG                 = "cardNumberListTag";

	String PHONES_TAG                           = "phones";

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

	String COUNT_TAG = "count";
	String PROFILE_NODE_STATE_TAG = "profileNodeState";

	String PHONE_NUMBER_TAG = "phoneNumber";
	String GUEST_CODE = "guestCode";
	String GUEST_PROFILE_ID_TAG = "guestProfileId";
	String GUEST_LOGIN = "guestLogin";
	String PHONE_CONNECT_MB_TAG = "phoneConnectMB";
}
