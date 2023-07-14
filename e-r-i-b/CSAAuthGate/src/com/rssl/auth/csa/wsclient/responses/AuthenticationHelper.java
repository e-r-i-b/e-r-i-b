package com.rssl.auth.csa.wsclient.responses;

import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.IPasResponseConstants;
import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.TBRenameDictionary;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthParams;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.CameFromType;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.client.CSAType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.csa.MigrationState;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.csa.CSAConfig;
import com.rssl.phizic.context.Constants;
import com.rssl.phizic.gate.einvoicing.ShopProfile;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.security.RegistrationStatus;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author khudyakov
 * @ created 10.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class AuthenticationHelper
{
	/**
	 * Заполнить authData данными из iPas
	 * @param authData authData
	 * @param container данные из iPas
	 */
	public static void fillFromIPasData(AuthData authData, AuthParamsContainer container)
	{
		authData.setASPKey(container.getParameter(IPasResponseConstants.ASP_KEY_TAG));
		authData.setSID(container.getParameter(IPasResponseConstants.SID_TAG));
		authData.setUserId(container.getParameter(IPasResponseConstants.USER_ID_TAG));
		authData.setMB(Boolean.parseBoolean(container.getParameter(IPasResponseConstants.MB_TAG)));

		String cbCode = container.getParameter(IPasResponseConstants.CB_CODE_TAG);
		for(String code: ConfigFactory.getConfig(TBRenameDictionary.class).getAllSynonyms().keySet())
		{
			if (cbCode.startsWith(code))
				cbCode = cbCode.replaceFirst(code, ConfigFactory.getConfig(TBRenameDictionary.class).getNewTbBySynonym(code));
		}
		authData.setCB_CODE(cbCode);
		authData.setPAN(StringHelper.isNotEmpty(container.getParameter(IPasResponseConstants.PAN_TAG)) ?
				container.getParameter(IPasResponseConstants.PAN_TAG) : container.getParameter(IPasResponseConstants.CARD_NUMBER_TAG));
		authData.setMoveSession(Boolean.parseBoolean(container.getParameter(IPasResponseConstants.IS_MOVE_SESSION_TAG)));
		authData.setLastName(container.getParameter(IPasResponseConstants.LAST_NAME_TAG));
		authData.setFirstName(container.getParameter(IPasResponseConstants.FIRST_NAME_TAG));
		authData.setMiddleName(container.getParameter(IPasResponseConstants.MIDDLE_NAME_TAG));
		authData.setDocument(StringHelper.isNotEmpty(container.getParameter(IPasResponseConstants.PASSPORT_NO_TAG)) ?
				container.getParameter(IPasResponseConstants.PASSPORT_NO_TAG) : container.getParameter(IPasResponseConstants.CLIENT_TAG));
		authData.setDocumentType("PASSPORT_WAY");                       ////из ЦСА приходит паспотр way
		authData.setBirthDate(container.getParameter(IPasResponseConstants.BIRTH_DATE_TAG));

		String cameFrom = container.getParameter(AuthParams.CameFrom.name());
		authData.setCameFromYoungWebsite(StringHelper.isNotEmpty(cameFrom) && CameFromType.YOUNG_PEOPLE_WEBSITE.name().equals(cameFrom));

		authData.setLastLoginDate(null);
		authData.setExpiredPassword(false);
		authData.setUserAlias(container.getParameter(IPasResponseConstants.USER_ID_TAG));
		authData.setCsaType(CSAType.CBOL_CA);
		authData.setLoginType(LoginType.TERMINAL);
		authData.setRegistrationStatus(RegistrationStatus.OFF);
	}

	/**
	 * Заполнить authData для МАПИ данными из ЦСА
	 * @param authData контейнер
	 * @param document ответ ЦСА
	 * @throws BackLogicException
	 */
	public static void fillFromMAPICSAData(AuthData authData, Document document) throws BackLogicException
	{
		Element element = document.getDocumentElement();

		String authorizedZoneValue = XmlHelper.getSimpleElementValue(element, CSAResponseConstants.AUTHORIZED_ZONE_PARAM_NAME);
		AuthorizedZoneType authorizedZone = StringHelper.isEmpty(authorizedZoneValue) ? AuthorizedZoneType.PRE_AUTHORIZATION : AuthorizedZoneType.valueOf(authorizedZoneValue);
		authData.setAuthorizedZoneType(authorizedZone);

		String mobileSDKData = XmlHelper.getSimpleElementValue(element, CSAResponseConstants.MOBILE_SDK_DATA_PARAM_NAME);
		setMobileSDKData(authData, mobileSDKData);

		boolean isLightScheme = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.IS_LIGHT_SCHEME_TAG));
		fillFromERIBCSADataBase(authData, element, isLightScheme, null);
	}

	private static void setMobileSDKData(AuthData authData, String mobileSDKData)
	{
		authData.getRsaData().put(Constants.MOBILE_SDK_DATA_PARAMETER_NAME, mobileSDKData);
	}

	/**
	 * Заполнить AuthData данными из ЕРИБ ЦСА
	 * @param authData контейнер данных актентификации
	 * @param document ответ от ЕРИБ ЦСА
	 * @param isLightScheme признак схемы прав
	 * @throws com.rssl.auth.csa.wsclient.exceptions.BackLogicException
	 */
	public static void fillFromERIBCSAData(AuthData authData, Document document, boolean isLightScheme, String browserInfo) throws BackLogicException
	{
		fillFromERIBCSADataBase(authData, document.getDocumentElement(), isLightScheme, browserInfo);
	}

	/**
	 * Заполнить AuthData данными из шоп-профиля
	 * @param profile профиль
	 * @param mobileCheckout был ли mobileCheckout при покупке
	 * @return контейнер данных актентификации
	 */
	public static AuthData fillAuthData(ShopProfile profile, boolean mobileCheckout) throws BackLogicException
	{
		AuthData authData = new AuthData();

		authData.setLastName(profile.getSurName());
		authData.setFirstName(profile.getFirstName());
		authData.setMiddleName(profile.getPatrName());
		authData.setDocument(profile.getPassport());
		authData.setSeries("");
		authData.setDocumentType(ClientDocumentType.PASSPORT_WAY.name());
		authData.setBirthDate(DateHelper.toXMLDateFormat(profile.getBirthdate().getTime()));
		authData.setCB_CODE(profile.getTb() + "000000");
		authData.setSecurityType(SecurityType.LOW);
		authData.setMobileCheckout(mobileCheckout);

		return authData;
	}

	/**
	 * Заполенение authData данными при входе гостя
	 * @param authData объект данных аутентификации
	 * @param document ответ от бэка
	 * @param browserInfo информация о браузере
	 * @throws BackLogicException
	 */
	public static void fillGuestAuthData(AuthData authData, Document document, String browserInfo) throws BackLogicException
	{
		Element root = document.getDocumentElement();

		authData.setLastName(XmlHelper.getSimpleElementValue(root, CSAResponseConstants.SURNAME_TAG));
		authData.setFirstName(XmlHelper.getSimpleElementValue(root, CSAResponseConstants.FIRSTNAME_TAG));
		authData.setMiddleName(XmlHelper.getSimpleElementValue(root, CSAResponseConstants.PATRNAME_TAG));
		authData.setBirthDate(XmlHelper.getSimpleElementValue(root, CSAResponseConstants.BIRTHDATE_TAG));
		authData.setDocument(XmlHelper.getSimpleElementValue(root, CSAResponseConstants.PASSPORT_TAG));
		authData.setLoginType(Enum.valueOf(LoginType.class, XmlHelper.getSimpleElementValue(root, CSAResponseConstants.TYPE_TAG)));
		authData.setGuestPhone(XmlHelper.getSimpleElementValue(root, CSAResponseConstants.PHONE_NUMBER_TAG));
		authData.setCB_CODE(XmlHelper.getSimpleElementValue(root, CSAResponseConstants.TB_TAG));
		authData.setUserAlias(XmlHelper.getSimpleElementValue(root, CSAResponseConstants.GUEST_LOGIN));

		String guestCode = XmlHelper.getSimpleElementValue(root, CSAResponseConstants.GUEST_CODE);
		if(StringHelper.isNotEmpty(guestCode))
			authData.setGuestCode(Long.valueOf(guestCode));

		String guestProfileId = XmlHelper.getSimpleElementValue(root, CSAResponseConstants.GUEST_PROFILE_ID_TAG);
		if(StringHelper.isNotEmpty(guestProfileId))
		{
			authData.setGuestProfileId(Long.valueOf(guestProfileId));
		}
		authData.setBrowserInfo(browserInfo);
	}

	private static void fillFromERIBCSADataBase(AuthData authData, Element element, boolean isLightScheme, String browserInfo) throws BackLogicException
	{
		authData.setSID(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.SID_TAG));
		authData.setUserId(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.USER_ID_TAG));
		authData.setUserAlias(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.LOGIN_TAG));
		authData.setMB(true);

		String cbCode = XmlHelper.getSimpleElementValue(element, CSAResponseConstants.CB_CODE_TAG);
		for (String code: ConfigFactory.getConfig(TBRenameDictionary.class).getAllSynonyms().keySet())
		{
			if (cbCode.startsWith(code))
				cbCode = cbCode.replaceFirst(code, ConfigFactory.getConfig(TBRenameDictionary.class).getNewTbBySynonym(code));
		}
		authData.setCB_CODE(cbCode);
		authData.setPAN(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.CARD_NUMBER_TAG));
		authData.setMoveSession(false);
		authData.setLastName(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.SURNAME_TAG));
		authData.setFirstName(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.FIRSTNAME_TAG));
		authData.setMiddleName(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PATRNAME_TAG));
		authData.setLoginType(Enum.valueOf(LoginType.class, XmlHelper.getSimpleElementValue(element, CSAResponseConstants.TYPE_TAG)));

		authData.setDocument(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PASSPORT_TAG));
		authData.setBirthDate(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.BIRTHDATE_TAG));
		authData.setCameFromYoungWebsite(false);
		//из ЦСА приходит паспотр way
		authData.setDocumentType("PASSPORT_WAY");

		String prevSessionDate = XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PREV_SESSION_DATE_TAG);
		authData.setLastLoginDate(StringHelper.isNotEmpty(prevSessionDate) ? XMLDatatypeHelper.parseDateTime(prevSessionDate) : null);
		authData.setExpiredPassword(isExpiredPassword(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PASSWORD_CREATION_DATE_TAG)));
		authData.setCsaType(CSAType.ERIB_CSA);
		authData.setCsaGuid(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.GUID_TAG));
		if (StringHelper.isNotEmpty(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PROFILE_ID_TAG)))
			authData.setCsaProfileId(Long.valueOf(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PROFILE_ID_TAG)));

		String deviceState = XmlHelper.getSimpleElementValue(element, CSAResponseConstants.DEVICE_STATE_TAG);
		if (isLightScheme)
		{
			authData.setMobileAppScheme(MobileAppScheme.LIGHT);
		}
		else
		{
			if (StringHelper.isNotEmpty(deviceState))
			{
				authData.setMobileAppScheme(MobileAppScheme.valueOf(deviceState));
			}
		}

		String registrationStatus = XmlHelper.getSimpleElementValue(element, CSAResponseConstants.REGISTRATION_STATUS_TAG);
		if (StringHelper.isNotEmpty(registrationStatus))
			authData.setRegistrationStatus(RegistrationStatus.valueOf(registrationStatus));
		else
			authData.setRegistrationStatus(RegistrationStatus.OFF);

		authData.setRegistrationType(RegistrationType.valueOf(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.REGISTRATION_TYPE_TAG)));
		authData.setDeviceId(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.DEVICE_ID_TAG));
		authData.setDeviceInfo(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.DEVICE_INFO_TAG));
		authData.setMobileAppVersion(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.MOBILE_API_VERSION_TAG));
		authData.setBrowserInfo(browserInfo);

		updateSecurityType(authData, element);
		updateProfileNodeInfo(authData, element);
	}

	private static void updateSecurityType(AuthData authData, Element root)
	{
		String securityTypeValue = XmlHelper.getSimpleElementValue(root, CSAResponseConstants.SECURITY_TYPE_TAG);
		if (StringHelper.isNotEmpty(securityTypeValue))
		{
			authData.setSecurityType(SecurityType.valueOf(securityTypeValue));
		}
	}

	private static void updateProfileNodeInfo(AuthData authData, Element root)
	{
		authData.setProfileType(ProfileType.valueOf(XmlHelper.getSimpleElementValue(root, CSAResponseConstants.PROFILE_TYPE_TAG)));
		String migrationStateValue = XmlHelper.getSimpleElementValue(root, CSAResponseConstants.MIGRATION_STATE_TAG);
		if (StringHelper.isNotEmpty(migrationStateValue))
		{
			authData.setMigrationState(MigrationState.valueOf(migrationStateValue));
		}
	}

	/**
	 * Заполнить AuthData данными из мобильного банка
	 * @param authData контейнер данных актентификации
	 * @param userInfo данные из мобильного банка
	 */
	public static void fillFromFromMobileBankData(AuthData authData, UserInfo userInfo)
	{
		authData.setLastName(userInfo.getSurname());
		authData.setFirstName(userInfo.getFirstname());
		authData.setMiddleName(userInfo.getPatrname());
		authData.setBirthDate(DateHelper.toXMLDateFormat(userInfo.getBirthdate().getTime()));

		authData.setDocument(userInfo.getPassport());
		authData.setDocumentType("PASSPORT_WAY");

		authData.setPAN(userInfo.getCardNumber());
		authData.setUserId(userInfo.getUserId());
		authData.setCB_CODE(userInfo.getCbCode());
		authData.setLoginType(LoginType.TERMINAL);
		authData.setMB(true);
	}

	/**
	 * Заполнить authData для АТМ данным из ЦСА
	 * @param authData контейнер данных
	 * @param document данные из ЦСА
	 */
	public static void fillFromATMCSAData(AuthData authData, Document document)
	{
		Element element = document.getDocumentElement();

		authData.setLastName(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.SURNAME_TAG));
		authData.setFirstName(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.FIRSTNAME_TAG));
		authData.setMiddleName(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PATRNAME_TAG));
		authData.setBirthDate(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.BIRTHDATE_TAG));

		authData.setDocument(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PASSPORT_TAG));
		authData.setDocumentType("PASSPORT_WAY");

		authData.setPAN(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.CARD_NUMBER_TAG));
		authData.setUserId(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.USER_ID_TAG));
		authData.setCB_CODE(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.CB_CODE_TAG));
		authData.setLoginType(LoginType.valueOf(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.TYPE_TAG)));
		authData.setMB(true);

		updateSecurityType(authData, element);
		updateProfileNodeInfo(authData, element);
	}

	/**
	 * Запонить контейнер данными при начале регистрации моб. приложения
	 * @param container контейнер
	 * @param document ответ
	 */
	public static void fillStartMobileRegistration(AuthParamsContainer container, Document document)
	{
		Element element = document.getDocumentElement();

		container.addParameter(RequestConstants.OUID_TAG, XmlHelper.getSimpleElementValue(element, RequestConstants.OUID_TAG));
		container.addParameter(RequestConstants.TIMEOUT_TAG, XmlHelper.getSimpleElementValue(element, RequestConstants.TIMEOUT_TAG));
		container.addParameter(RequestConstants.ATTEMPTS_TAG, XmlHelper.getSimpleElementValue(element, RequestConstants.ATTEMPTS_TAG));
	}

	private static boolean isExpiredPassword(String creationDate)
	{
		//Если данные о времени создания пароля не пришли, то пароль не может устареть.
		if (StringHelper.isEmpty(creationDate))
			return false;

		//Пароль является устаревшим, если текущее время больше либо равно времени истечения срока жизни пароля.
		Calendar expireDate = XMLDatatypeHelper.parseDateTime(creationDate);
		int lifeTime = ConfigFactory.getConfig(CSAConfig.class).getPasswordLifeTime();

		return DateHelper.diff(DateHelper.addDays(expireDate, lifeTime), Calendar.getInstance()) <= 0;
	}

}
