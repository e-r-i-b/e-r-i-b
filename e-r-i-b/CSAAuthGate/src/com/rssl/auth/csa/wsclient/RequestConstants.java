package com.rssl.auth.csa.wsclient;

/**
 * @author niculichev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class RequestConstants
{
	public static final String MESSAGE_TAG              = "message";
	public static final String UID_TAG                  = "UID";
	public static final String OUID_TAG                 = "OUID";
	public static final String AUTH_TOKEN_TAG           = "authToken";
	public static final String DATE_TAG                 = "date";
	public static final String SOURCE_TAG               = "source";
	public static final String VERSION_TAG              = "version";
	public static final String IP_TAG                   = "IP";
	public static final String STATUS_TAG               = "status";
	public static final String STATUS_CODE_TAG          = "code";
	public static final String BLOCKING_RULES_DATE      = "RestrictionDate";
	public static final String STATUS_DESCRIPTION_TAG   = "description";
	public static final String MGUID_TAG                = "mGUID";
	public static final String TIMEOUT_TAG              = "timeout";
	public static final String ATTEMPTS_TAG             = "attempts";
	public static final String IS_NEW_PASSWORD_TAG      = "newPassword";
	public static final String BLOCKING_TIMEOUT_TAG     = "blockingTimeout";
	public static final String PHONES_TAG               = "phones";

	// поля для запросов в CSABack
	public static final String LOGIN_PARAM_NAME         = "login";
	public static final String CARD_NUMBER_PARAM_NAME   = "cardNumber";
	public static final String PASSWORD_PARAM_NAME      = "password";
	public static final String OUID_PARAM_NAME          = "OUID";
	public static final String GUID_PARAM_NAME          = "GUID";
	public static final String CONFIRM_CODE_PARAM_NAME  = "confirmationCode";
	public static final String SID_PARAM_NAME           = "SID";
	public static final String DEVICE_INFO_PARAM_NAME   = "deviceInfo";
	public static final String DEVICE_STATE_PARAM_NAME  = "deviceState";
	public static final String TIMEOUT                  = "timeout";
	public static final String ATTEMPTS                 = "attempts";
	public static final String CONFIRMATION_PARAM_NAME  = "confirmationType";
	public static final String EXTERNAL_ID_PARAM_NAME   = "externalId";
	public static final String REGISTRATION_IPAS_PARAM_NAME= "registrationIPasAvailable";
	public static final String DEVICE_ID_PARAM_NAME     = "devId";
	public static final String APP_NAME_PARAM_NAME      = "appName";
	public static final String VERSION_PARAM_NAME       = "version";
	public static final String AUTHORIZED_ZONE_PARAM_NAME = "authorizedZone";
	public static final String PUSH_SUPPORTED_PARAM_NAME = "pushSupported";
	public static final String SURNAME_PARAM_NAME       = "surname";
	public static final String FIRSTNAME_PARAM_NAME     = "firstname";
	public static final String PATRNAME_PARAM_NAME      = "patrname";
	public static final String BIRTHDATE_PARAM_NAME     = "birthdate";
	public static final String CB_CODE_PARAM_NAME       = "cbCode";
	public static final String PASSPORT_PARAM_NAME      = "passport";
	public static final String PHONE_NUMBER_PARAM_NAME  = "phoneNumber";
	public static final String USING_CARD_BY_PHONE_PARAM_NAME = "usingCardByPhone";
	public static final String REMOVE_PHONE_PARAM_NAME  = "removePhone";
	public static final String ADD_PHONE_PARAM_NAME     = "addPhone";
	public static final String SAME_LOGIN_PARAM_NAME    = "sameLogin";
	public static final String CHECK_PASSWORD_PARAM_NAME= "checkPassword";
	public static final String CARD_PARAM_NAME          = "card";
	public static final String CLAIM_TYPE_NAME          = "claimType";
	public static final String MOBILE_SDK_DATA_PARAM_NAME="mobileSdkData";
	public static final String IS_SOCIAL                = "isSocial";

	public static final String PROFILE_INFO_TAG         = "profileInfo";
	public static final String MAIN_PHONE_TAG           = "mainPhone";
	public static final String REMOVE_PHONES_TAG        = "removePhones";
	public static final String ADD_PHONES_TAG           = "addPhones";
	public static final String EMPLOYEE_NAME            = "employeeName";
	public static final String EMPLOYEE_GUID            = "employeeGUID";
	public static final String INCOGNITO_SETTING_PARAM_NAME = "incognito";

	public static final String UPDATE_PROFILE_INFO_TAG  = "updateProfileInfo";
	public static final String USER_INFO_TAG            = "userInfo";
	public static final String NEW_USER_INFO_TAG        = "newUserInfo";
	public static final String OLD_USER_INFO_TAG        = "oldUserInfo";
	public static final String OLD_USER_INFO_LIST_TAG   = "oldUserInfoList";
	public static final String USER_INFO_LIST_TAG       = "userInfoList";

	public static final String SECURITY_TOKEN_PARAM_NAME = "securityToken";

	public static final String FIO_TAG = "fio";
	public static final String TB_LIST_TAG = "tbList";
	public static final String TB_TAG = "tb";
	public static final String CLIENT_ADDITIONAL_DATA_TAG = "clientAdditionalData";
	public static final String CREATION_TYPE_TAG = "creationType";
	public static final String AGREEMENT_NUMBER_TAG = "agreementNumber";
	public static final String NODE_ID_TAG = "nodeId";
	public static final String PROFILE_ID               = "profile_id";
	public static final String MDM_ID                   = "mdm_id";

	public static final String LOCK_FROM_PARAM_NAME = "lockFrom";
	public static final String LOCK_TO_PARAM_NAME = "lockTo";
	public static final String REASON_PARAM_NAME = "reason";
	public static final String LOCKER_FIO_PARAM_NAME = "lockerFIO";

	public static final String SEND_SMS_PARAM_NAME = "sendSMS";

	public static final String NEED_CREATE_PROFILE = "needCreateProfile";
	public static final String CREATE_PROFILE_NODE_MODE = "createProfileNodeMode";

	public static final String NODES_INFO_NODE_NAME = "nodes";
	public static final String NODE_INFO_NODE_NAME = "node";
	public static final String NODE_INFO_ID_NODE_NAME = "nodeId";
	public static final String NODE_INFO_NAME_NODE_NAME = "nodeName";
	public static final String NODE_INFO_NEW_USERS_ALLOWED_NODE_NAME = "newUsersAllowed";
	public static final String NODE_INFO_EXISTING_USERS_ALLOWED_NODE_NAME = "existingUsersAllowed";
	public static final String NODE_INFO_TEMPORARY_USERS_ALLOWED_NODE_NAME = "temporaryUsersAllowed";
	public static final String NODE_INFO_USERS_TRANSFER_ALLOWED_NODE_NAME = "usersTransferAllowed";
	public static final String NODE_INFO_ADMIN_AVAILABLE_NODE_NAME = "adminAvailable";
	public static final String NODE_INFO_GUEST_AVAILABLE_NODE_NAME = "guestAvailable";

	public static final String TECHNO_BREAK_UUID = "technoBreakUUID";
	public static final String TECHNO_BREAK_FROM_DATE = "technoBreakFromDate";
	public static final String TECHNO_BREAK_TO_DATE = "technoBreakToDate";
	public static final String TECHNO_BREAK_MESSAGE = "technoBreakMessage";
	public static final String TECHNO_BREAK_STATUS = "technoBreakStatus";
	public static final String TECHNO_BREAK_PERIODIC = "technoBreakPeriodic";
	public static final String TECHNO_BREAK_IS_AUTO = "technoBreakIsAuto";

	public static final String IGNORE_IMSI_CHEK = "ignoreImsiCheck";

	public static final String GUEST_CODE = "guestCode";

	public static final String CONTAINS_PRO_MAPI_TAG = "containsProMAPI";
}
