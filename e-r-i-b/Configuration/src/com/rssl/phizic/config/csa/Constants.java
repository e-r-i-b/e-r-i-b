package com.rssl.phizic.config.csa;

/**
 * @author gladishev
 * @ created 25.01.14
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	public static final String DENY_MULTIPLE_REGISTRATION_PROPERTY_NAME = "com.rssl.auth.csa.back.config.registration.user.deny-multiple";
	public static final String DENY_EXIST_ENTERED_REGISTRATION_PROPERTY_NAME = "com.rssl.auth.csa.back.config.registration.user.deny-exist-entered";
	public static final String CONFIRMATION_TIMEOUT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.common.confirmation.timeout";
	public static final String CONFIRMATION_CODE_ALLOWED_CHARS_PROPERTY_NAME = "com.rssl.auth.csa.back.config.common.confirmation.code.allowed-chars";
	public static final String CONFIRMATION_CODE_LENGTH_PROPERTY_NAME = "com.rssl.auth.csa.back.config.common.confirmation.code.length";
	public static final String CONFIRMATION_ATTEMPTS_COUNT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.common.confirmation.attempts.count";
	public static final String REGISTRATION_TIMEOUT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.registration.timeout";
	public static final String AUTHENTICATION_TIMEOUT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.authentication.timeout";
	public static final String AUTHENTICATION_FAILED_BLOCKING_TIME_OUT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.authentication.blocking.timeout";
	public static final String MAX_AUTHENTICATION_FAILED_PROPERTY_NAME = "com.rssl.auth.csa.back.config.authentication.failed.limit";
	public static final String RESTORE_PASSWORD_TIMEOUT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.password-restoration.timeout";
	public static final String MOBILE_REGISTRATION_TIMEOUT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.mobile.registration.timeout";
	public static final String MAX_MOBILE_CONNECTORS_COUNT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.mobile.registration.max.connectors";
	public static final String MAX_MOBILE_REGISTRATION_REQUEST_COUNT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.mobile.registration.request.limit";
	public static final String MOBILE_REGISTRATION_REQUEST_CHECK_INTERVAL_PROPERTY_NAME = "com.rssl.auth.csa.back.config.mobile.registration.request.limit.check.interval";
	public static final String MAX_USER_REGISTRATION_REQUEST_COUNT_PROPERTY_NAME = "com.rssl.auth.csa.back.config.registration.request.limit";
	public static final String USER_REGISTRATION_REQUEST_CHECK_INTERVAL_PROPERTY_NAME = "com.rssl.auth.csa.back.config.registration.request.limit.check.interval";
	public static final String IPAS_URL_PROPERTY_NAME = "com.rssl.auth.csa.back.config.integration.ipas.url";
	public static final String ERIB_URL_PROPERTY_NAME = "com.rssl.auth.csa.back.config.integration.erib.url";
	public static final String SESSION_TIMEOUT = "com.rssl.auth.csa.back.config.common.session.timeout";
	public static final String GENERATED_PASSWORD_ALLOWED_CHARS_PROPERTY_NAME = "com.rssl.auth.csa.back.config.password.generation.allowed-chars";
	public static final String GENERATED_PASSWORD_LENGTH_PROPERTY_NAME = "com.rssl.auth.csa.back.config.password.generation.length";
	public static final String RESTORE_PASSWORD_CB_CODE_DENY_PATTERN_PROPERTY_NAME = "com.rssl.auth.csa.back.config.password-restoration.deny.cbcodes";
	public static final String USER_REGISTRATION_CB_CODE_DENY_PATTERN_PROPERTY_NAME = "com.rssl.auth.csa.back.config.registration.deny.cbcodes";
	public static final String IPAS_CSA_BACK_TIMEOUT = "com.rssl.ipas.csa.back.config.timeout";
	public static final String ERIB_CSA_BACK_TIMEOUT = "com.rssl.erib.csa.back.config.timeout";
	public static final String ADDITIONAL_REQUEST_FOR_SYNC_ALLOWED_PROPERTY_NAME = "com.rssl.auth.csa.back.config.common.additional.request.for.synchronization.allowed";
	public static final String POST_AUTHENTICATION_SYNC_ALLOWED_PROPERTY_NAME = "com.rssl.auth.csa.back.config.common.post.authentication.synchronization.allowed";
	public static final String BUSINESS_ENVIRONMENT_URL_PROPERTY_NAME = "com.rssl.auth.csa.back.config.business.environment.url";
	public static final String BUSINESS_ENVIRONMENT_SOAPACTION_PROPERTY_NAME = "com.rssl.auth.csa.back.config.business.environment.SOAPAction";
	public static final String IPAS_PASSWORD_STORE_ALLOWED_PROPERTY_NAME = "com.rssl.auth.csa.back.config.authentication.ipas.password.store.allowed";
	public static final String IPAS_AUTHENTICATION_ALLOWED_PROPERTY_NAME = "com.rssl.auth.csa.back.config.integration.ipas.allowed";
	public static final String USER_INFO_PROVIDER_PROPERTY_NAME = "com.rssl.auth.csa.back.config.integration.user-info-provider";
	public static final String POST_REGISTRATION_LOGIN_PROPERTY_NAME = "com.rssl.auth.csa.back.config.post.registration.login";
	public static final String ACCESS_AUTO_REGISTRATION_PROPERTY_NAME = "com.rssl.auth.csa.back.config.access.auto.registration";
	public static final String STAND_IN_MODE_PROPERTY_NAME = "com.rssl.auth.csa.back.config.stand.in.mode";
	public static final String WAY4U_WORKAROUND_089795_PROPERTY_NAME ="com.rssl.auth.csa.back.config.integration.way4u.workaround.089795";

	//***************************

	public static final String ACCESS_RECOVER_PASSWORD_PROPERTY_NAME   = "com.rssl.auth.csa.front.config.access.recover.password";
	public static final String OLD_CSA_CBCODES_PROPERTY_NAME           = "com.rssl.auth.csa.front.config.old.csa.cbcodes";
	public static final String AUTH_GLOBAL_OLD_CSA_KEY_WORD            = "all";
	public static final String ACCESS_INTERNAL_REGISTRATION_PROPERTY_NAME = "com.rssl.auth.csa.front.config.access.registration.internal";
	public static final String ACCESS_EXTERNAL_REGISTRATION_PROPERTY_NAME = "com.rssl.auth.csa.front.config.access.registration.external";
	public static final String ACCESS_DENY_MULTIPLE_REGISTRATION_PROPERTY_NAME = "com.rssl.auth.csa.front.config.access.registration.user.deny-multiple";
	public static final String USE_CAPTCHA_RESTRICT_PROPERTY_NAME      = "com.rssl.auth.csa.front.config.use.captcha.restrict";
	public static final String CAPTCHA_CONTROL_ENABLED_PROPERTY_NAME   = "com.rssl.auth.csa.front.config.constant.captcha.control.enabled";
	public static final String CHOOSE_REGION_MODE_PROPERTY_NAME        = "com.rssl.auth.csa.front.config.choose.region.mode";
	public static final String RESTRICTION_MESSAGE_NAME                = "com.rssl.auth.csa.front.business.blockingrules.BlockingRules.RestrictionMessage";
	public static final String RESTRICTION_ACCESS_TYPE_PROPERTY_NAME   = "com.rssl.auth.csa.front.config.registration.access.type";
	public static final String AJAX_FIELDS_CHECK_ENABLED               = "com.rssl.auth.csa.front.config.auth.AsyncFieldsCheck";
	public static final String LOGIN_MINIMUM_LENGTH                    = "com.rssl.iccs.login.minimumLength";
	public static final String PROMO_BLOCK_ENABLED                     = "com.rssl.auth.csa.front.config.auth.showPromoBlock";
	public static final String SHOW_POPUP_AFTER_REGISTRATION           = "com.rssl.auth.csa.front.config.auth.showPopupAfterReg";

	public static final String COMMON_CAPTCHA_DELAY                    ="com.rssl.captcha.delay";
	public static final String CAPTCHA_RULE_STOPPING_MINIMAL_DELAY     ="com.rssl.captcha.delay.minimal";

	public static final String COMMON_GUEST_CAPTCHA_DELAY              ="com.rssl.guest.captcha.delay";
	public static final String UNTRUSTED_GUEST_CAPTCHA_DELAY           ="com.rssl.guest.captcha.delay.minimal";

	public static final String GUEST_ENTRY_MODE                        = "com.rssl.guest.entry.mode";
	public static final String GUEST_ENTRY_PHONES_LIMIT                = "com.rssl.guest.phones.limit";
	public static final String GUEST_ENTRY_PHONES_LIMIT_MAX            = "com.rssl.guest.phones.limit.max";
	public static final String GUEST_ENTRY_PHONES_LIMIT_COOLDOWN = "com.rssl.guest.phones.limit.cooldown";
	public static final String GUEST_ENTRY_GUEST_CAPTCHA = "com.rssl.guest.captcha";
	public static final String GUEST_ENTRY_SMS_COUNT                   = "com.rssl.guest.sms.count";
	public static final String GUEST_ENTRY_SMS_COUNT_GLOBAL            = "com.rssl.guest.sms.count.global";
	public static final String GUEST_ENTRY_SMS_COUNT_MAX           = "com.rssl.guest.sms.count.max";
	public static final String GUEST_ENTRY_SMS_COOLDOWN                = "com.rssl.guest.sms.cooldown";
	public static final String GUEST_ENTRY_CLAIMS_SHOW_PERIOD          ="com.rssl.guest.entry.claims.show.period";

	//‘–Œƒ ÃŒÕ»“Œ–»Õ√
	public static final String RSA_ACTIVITY_STATE                                = "com.rssl.rsa.activity.state";
	public static final String RSA_CONNECTION_URL                                = "com.rssl.rsa.url";
	public static final String RSA_CONNECTION_TIMEOUT                            = "com.rssl.rsa.connection.timeout";
	public static final String RSA_CONNECTION_LOGIN                              = "com.rssl.rsa.login";
	public static final String RSA_CONNECTION_PASSWORD                           = "com.rssl.rsa.password";
	public static final String RSA_JOB_UNLOADING_TIME                            = "com.rssl.rsa.job.unloading.time";
	public static final String RSA_VERDICT_COMMENT_ACTIVITY                      = "com.rssl.rsa.verdict.comment.activity";

	public static final String BUSINESS_ENVIRONMENT_ACCESS_PROPERTY_NAME         = "com.rssl.auth.csa.front.config.business.environment.access";
	public static final String BUSINESS_ENVIRONMENT_MAINURL_PROPERTY_NAME        = "com.rssl.auth.csa.front.config.business.environment.main.url";
	public static final String BUSINESS_ENVIRONMENT_USERURL_PROPERTY_NAME        = "com.rssl.auth.csa.front.config.business.environment.user.url";
	public static final String BUSINESS_ENVIRONMENT_URL_MAP_PROPERTY_NAME_PREFIX = "com.rssl.auth.csa.front.config.business.environment.after.verify.";

	public static final String UEC_WEBSITE_URL_PROPERTY_NAME = "com.rssl.auth.csa.front.uec.url";
	public static final String PROMOTER_DOMAIN_NAME = "com.rssl.auth.csa.front.promoter.domain";

	public static final String REGISTRATION_CAPTCHA_ATTEMPTS   = "com.rssl.auth.csa.front.config.registration.finish.captcha.attemps";
	public static final String REGISTRATION_CAPTCHA_TIME       = "com.rssl.auth.csa.front.config.registration.finish.captcha.time";

	public static final String SMS_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME              = "com.rssl.iccs.csa.front.log.message.sos.sms.available";
	public static final String CHECK_IMSI_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME       = "com.rssl.iccs.csa.front.log.message.sos.check.imsi.available";
	public static final String UPDATE_RESOURCE_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME  = "com.rssl.iccs.csa.front.log.message.sos.update.resource.available";
	public static final String UPDATE_CLIENT_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME    = "com.rssl.iccs.csa.front.log.message.sos.update.client.available";
	public static final String CONFIRM_PROFILE_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME  = "com.rssl.iccs.csa.front.log.message.sos.confirm.profile.available";
	public static final String FEE_RESULT_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME       = "com.rssl.iccs.csa.front.log.message.sos.fee.result.available";

	public static final String PIXEL_METRIC_ACTIVITY                                = "com.rssl.iccs.pixel.metric.activity";
	public static final String GLOBAL_URL_FOR_EXTERNAL_RESOURCES                    = "global.url.for.external.resources";
}
