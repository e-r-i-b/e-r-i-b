package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.integration.erib.types.CardInformation;
import com.rssl.auth.csa.back.integration.erib.types.ConfirmationInformation;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.ResponseBuilder;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.client.ClientInformation;
import com.rssl.auth.csa.back.servises.client.ClientNodeInfo;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.auth.csa.back.servises.operations.AuthenticationOperation;
import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.auth.csa.back.servises.operations.GetConfirmationInfoOperation;
import com.rssl.auth.csa.back.servises.operations.confirmations.IPasConfirmationInfo;
import com.rssl.auth.csa.back.servises.operations.confirmations.SMSConfirmationInfo;
import com.rssl.auth.csa.back.servises.operations.guest.GuestConfirmableOperation;
import com.rssl.auth.csa.back.servises.operations.guest.GuestLogonOperation;
import com.rssl.auth.csa.back.servises.operations.guest.GuestPhoneAuthenticationOperation;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.security.RegistrationStatus;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.Map;

import static com.rssl.phizic.context.Constants.*;

/**
 * @author krenev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ResponseBuilderHelper extends ResponseBuilder
{
	public ResponseBuilderHelper(String bodyTagName) throws SAXException
	{
		super(bodyTagName);
	}

	public ResponseBuilderHelper(String bodyTagName, int errorCode, String errorDescription) throws SAXException
	{
		super(bodyTagName, errorCode, errorDescription);
	}

	/**
	 * Добавить в собщение идентифкатор операции
	 * @param operation операция.
	 * @return постоитель ответов.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addOUID(OperationBase operation) throws SAXException
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция не может быть null");
		}
		addParameter(Constants.OUID_TAG, operation.getOuid());
		return this;
	}

	/**
	 * Добавить в собщение Тип входящего клиента
	 * @param type значение типа
	 * @return постоитель ответов
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addUserLogonType(UserLogonType type) throws SAXException
	{
		if (type == null)
		{
			throw new IllegalArgumentException("Тип входящего клиента не может быть null");
		}
		addParameter(Constants.USER_LOGON_TYPE, type.name());
		return this;
	}


	/**
	 * Добавить в собщение идентифкатор профиля
	 * @param profile профиль
	 * @return постоитель ответов.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addProfileId(Profile profile) throws SAXException
	{
		addParameter(Constants.PROFILE_ID_TAG, profile.getId());
		return this;
	}

	/**
	 * Добавить в собщение идентифкатор профиля
	 * @param profile профиль
	 * @return постоитель ответов.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addProfileIdNullSafe(Profile profile) throws SAXException
	{
		addParameterIfNotEmpty(Constants.PROFILE_ID_TAG, profile == null ? null : profile.getId());
		return this;
	}

	/**
	 * Добавить в сообщение информацию о связи клиента с блоком
	 * @return построитель ответов
	 * @throws SAXException
	 * @param profileNode связь профиля с блоком
	 */
	public ResponseBuilderHelper addProfileNodeInfo(ProfileNode profileNode) throws Exception
	{
		if (profileNode == null)
		{
			throw new IllegalArgumentException("Информация о связи узла с профилем не может быть null");
		}
		openTag(Constants.PROFILE_NODE_INFO_TAG).
			addParameter(Constants.PROFILE_TYPE_TAG, profileNode.getProfileType()).
			addParameterIfNotEmpty(Constants.MIGRATION_STATE_TAG, ProfileNode.getMigrationState(profileNode)).
		closeTag();
		return this;
	}

	/**
	 * Добавить в ответ ошибки параметры взаимодействия с ВС ФМ (deviceTokenCookie, deviceTokenFSO)
	 * @return this
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addFaultRsaData() throws SAXException
	{
		if (StringHelper.isNotEmpty(RSAContext.getSystemDeviceTokenCookie()) || StringHelper.isNotEmpty(RSAContext.getSystemDeviceTokenFSO()))
		{
			openTag(Constants.RSA_DATA_TAG).
				addParameter(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, RSAContext.getSystemDeviceTokenCookie()).
				addParameter(DEVICE_TOKEN_FSO_PARAMETER_NAME, RSAContext.getSystemDeviceTokenFSO());
			closeTag();
		}
		return this;
	}

	public ResponseBuilderHelper addRsaData(Map<String, String> data) throws SAXException
	{
		if (data != null)
		{
			openTag(Constants.RSA_DATA_TAG).
				//RSAContext
				addParameter(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, data.get(DEVICE_TOKEN_COOKIE_PARAMETER_NAME)).
				addParameter(DEVICE_TOKEN_FSO_PARAMETER_NAME, data.get(DEVICE_TOKEN_FSO_PARAMETER_NAME)).
				addParameter(DOM_ELEMENTS_PARAMETER_NAME, data.get(DOM_ELEMENTS_PARAMETER_NAME)).
				addParameter(DEVICE_PRINT_PARAMETER_NAME, data.get(DEVICE_PRINT_PARAMETER_NAME)).
				addParameter(JS_EVENTS_PARAMETER_NAME, data.get(JS_EVENTS_PARAMETER_NAME)).
				//HeaderContext
				addParameter(HTTP_ACCEPT_HEADER_NAME, data.get(HTTP_ACCEPT_HEADER_NAME)).
				addParameter(HTTP_ACCEPT_CHARS_HEADER_NAME, data.get(HTTP_ACCEPT_CHARS_HEADER_NAME)).
				addParameter(HTTP_ACCEPT_ENCODING_HEADER_NAME, data.get(HTTP_ACCEPT_ENCODING_HEADER_NAME)).
				addParameter(HTTP_ACCEPT_LANGUAGE_HEADER_NAME, data.get(HTTP_ACCEPT_LANGUAGE_HEADER_NAME)).
				addParameter(HTTP_REFERRER_HEADER_NAME, data.get(HTTP_REFERRER_HEADER_NAME)).
				addParameter(HTTP_USER_AGENT_HEADER_NAME, data.get(HTTP_USER_AGENT_HEADER_NAME)).
				addParameter(PAGE_ID_PARAMETER_NAME, data.get(PAGE_ID_PARAMETER_NAME)).
			closeTag();
		}
		return this;
	}

	/**
	 * Добавить в сообщение информацию о mobileSDK
	 * @param data - собственно данные
	 * @return построитель ответов
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addMobileSDKData(String data) throws SAXException
	{
		if (data != null)
		{
			addParameter(Constants.MOBILE_SDK_DATA_TAG, data);
		}
		return this;
	}

	/**
	 * Добавить в собщение идентифкатор операции
	 * @param operation операция.
	 * @return постоитель ответов.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addAuthToken(AuthenticationOperation operation) throws SAXException
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция не может быть null");
		}
		addParameter(Constants.AUTH_TOKEN_TAG, operation.getAuthToken());
		return this;
	}

	/**
	 * Добавить в собщение идентифкатор операции
	 * @param operation токен.
	 * @return постоитель ответов.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addAuthToken(GuestPhoneAuthenticationOperation operation) throws SAXException
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция не может быть null");
		}
		addParameter(Constants.AUTH_TOKEN_TAG, operation.getOuid());
		return this;
	}

	/**
	 * Добавить в сообщение уровень безопасности
	 * @param connector коннектор
	 * @return построитель ответа.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addSecurityType(Connector connector) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("Коннектор не может быть null");
		}
		addParameter(Constants.SECURITY_TYPE_TAG, connector.calcSecurityType().name());
		return this;
	}

	private ResponseBuilder addConfirmParameters(IPasConfirmationInfo confirmationInfo) throws SAXException
	{
		return addParameter(Constants.ATTEMPTS_TAG, confirmationInfo.getLastAtempts())
				.addParameter(Constants.PASSWORDS_LEFT_TAG, confirmationInfo.getPasswordsLeft())
				.addParameter(Constants.PASSWORD_NUMBER_TAG, confirmationInfo.getPasswordNo())
				.addParameter(Constants.RECEIPT_NUMBER_TAG, confirmationInfo.getReceiptNo());
	}

	private ResponseBuilder addConfirmParameters(SMSConfirmationInfo confirmationInfo) throws SAXException
	{
		return addParameter(Constants.ATTEMPTS_TAG, confirmationInfo.getLastAtempts())
				.addParameter(Constants.TIMEOUT_TAG, confirmationInfo.getConfirmTimeOut());
	}

	/**
	 * Добавить в операцию информацию о параметрах подтвержения
	 * confirmParameters        параметры подтверждения                                             [1]
	 *      confirmationType    идентификатор коннектора                                            [1]
	 *      attempts            оставшееся количество попыток                                       [0..1]
	 *                          при подтверждении по карте есть только при ошибке валидации пароля
	 *              для подтверждения по смс
	 *      timeout             время жизни пароля                                                  [0..1]
	 *              для подтверждения по карте
	 *      passwordsLeft       паролей осталось на чеке                                            [0..1]
	 *      passwordNo          номер пароля на чеке                                                [0..1]
	 *      receiptNo           номер чека                                                          [0..1]
	 * @param operation операция
	 * @return постоитель ответов.
	 */
	public ResponseBuilderHelper addConfirmParameters(ConfirmableOperationBase operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция не может быть null");
		}
		ConfirmStrategyType confirmType = operation.getConfirmStrategyType();
		openTag(Constants.CONFIRM_PARAMETERS_TAG).addParameter(Constants.CONFIRMATION_PARAM_NAME, confirmType.name());
		switch (confirmType)
		{
			case sms: addConfirmParameters((SMSConfirmationInfo) operation.getConfirmationInfo()); break;
			case card: addConfirmParameters((IPasConfirmationInfo) operation.getConfirmationInfo()); break;
			case push: addConfirmParameters((SMSConfirmationInfo) operation.getConfirmationInfo()); break;
		}
		closeTag();
		return this;
	}

	/**
	 * Добавить в операцию информацию о параметрах подтвержения
	 * @param operation гостевая подтверждаемая операция
	 * @return постоитель ответов
	 * @throws Exception
	 */
	public ResponseBuilderHelper addConfirmParameters(GuestConfirmableOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция не может быть null");
		}

		openTag(Constants.CONFIRM_PARAMETERS_TAG);
		addParameter(Constants.CONFIRMATION_PARAM_NAME, ConfirmStrategyType.sms.name());
		addParameter(Constants.ATTEMPTS_TAG, operation.getLastAttempts());
		addParameter(Constants.TIMEOUT_TAG, operation.getConfirmTimeOut());
		closeTag();
		return this;
	}

	/**
	 * Добавить параметры гостевой аутентификации
	 * @param operation операция гостевой аутентификации
	 * @return постоитель ответов
	 * @throws Exception
	 */
	public ResponseBuilderHelper addGuestAuthParameters(GuestPhoneAuthenticationOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция не может быть null");
		}

		openTag(Constants.CONFIRM_PARAMETERS_TAG).addParameter(Constants.CONFIRMATION_PARAM_NAME, ConfirmStrategyType.sms.name());
		addParameter(Constants.ATTEMPTS_TAG, operation.getLastAtempts());
		addParameter(Constants.TIMEOUT_TAG, operation.getConfirmTimeOut());
		closeTag();

		return this;
	}

	/**
	 * Добавить информацию о номерах телефонов, которые не прошли проверку IMSI
	 * Используется только при построении сообщения с кодом 515, когда обнаружена замена sim-карты
	 * confirmParameters        параметры подтверждения                                         [1]
	 *      phones          номера телефонов, которые не прошли проверку IMSI                   [1]
	 * @param phones номера телефонов
	 * @return построитель ответов.
	 */
	public ResponseBuilderHelper addErrorConfirmParameters(String phones) throws SAXException
	{
		openTag(Constants.CONFIRM_PARAMETERS_TAG);
		addParameter(Constants.PHONES_TAG, phones);
		closeTag();
		return this;
	}

	/**
	 * Добавить информацию о коннекторe.
	 * ConnectorInfo                Информация о коннекторе                                     [1]
	 *      GUID                    идентификатор коннектора                                    [1]
	 *      deviceState             предыдущее состояние устройства                             [0..1]
	 *      deviceInfo              информация об устройстве                                    [0..1]
	 *      cbCode                  Подразделение пользователя                                  [1]
	 *      userId                  Цифровой логин пользователя                                 [1]
	 *      cardNumber              "карта входа"                                               [1]
	 *      login                   логин/алиас                                                 [0..1]
	 *      type                    тип коннектора(TERMINAL, CSA, MAPI)                         [1]
	 *      creationDate            дата создания коннектора                                    [1]
	 *      passwordCreationDate    дата создания пароля                                        [0..1]
	 *      registrationStatus      статус регистрации клиента в ЦСА                            [0..1]
	 *      devID                   идентификатор устройства                                    [0..1]
	 *      currentSessionDate      дата последнего входа                                       [1]
	 * @param connector коннетор
	 * @param registrationStatus если параметр не null, то будет добавлен registrationStatus
	 * @return постоитель ответов.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addConnectorInfo(Connector connector, RegistrationStatus registrationStatus) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("Коннектор не может быть null");
		}
		openTag(Constants.CONNECTOR_INFO_TAG)
			.addParameter(Constants.ID_TAG, connector.getId())
			.addParameter(Constants.GUID_TAG, connector.getGuid())
			.addParameterIfNotEmpty(Constants.DEVICE_STATE_TAG, connector.getDeviceState())
			.addParameterIfNotEmpty(Constants.DEVICE_INFO_TAG, connector.getDeviceInfo())
			.addParameter(Constants.CB_CODE_TAG, connector.getCbCode())
			.addParameter(Constants.USER_ID_TAG, connector.getUserId())
			.addParameter(Constants.PUSH_SUPPORTED_TAG, connector.getPushSupported())
			.addParameter(Constants.CARD_NUMBER_TAG, connector.getCardNumber());
			addParameterIfNotEmpty(Constants.LOGIN_TAG, getDisposableLogin(connector));
			addParameter(Constants.TYPE_TAG, connector.getType().name())
			.addParameter(Constants.CREATION_DATE_TAG, connector.getCreationDate())
			.addParameterIfNotEmpty(Constants.PASSWORD_CREATION_DATE_TAG, connector.getPasswordCreationDate())
			.addParameterIfNotEmpty(Constants.REGISTRATION_STATUS_TAG, registrationStatus != null ? registrationStatus.name() : null)
			.addParameter(Constants.REGISTRATION_TYPE_TAG, connector.getRegistrationType().name())
			.addParameterIfNotEmpty(Constants.DEVICE_ID_TAG, connector.getDeviceId())
			.addParameterIfNotEmpty(Constants.MOBILE_API_VERSION_TAG, connector.getVersion())
			.addParameterIfNotEmpty(Constants.LAST_SESSION_DATE, connector.getCurrentSessionDate());
		closeTag();
		return this;
	}

	private String getDisposableLogin(Connector connector) throws Exception
	{
		CSAConnector csaConnector = CSAConnector.findLastConnectorByUserId(connector.getUserId());

		if (ConnectorType.DISPOSABLE.equals(connector.getType()) && csaConnector != null)
		{
			String res = csaConnector.getLogin();
			if(res != null)
				return res;
		}

		return connector.getLogin();
	}

	/**
	 * Добавить информацию о входящем госте
	 * @param profile профиль гостя
	 * @param operation операция входа
	 * @return постоитель ответов
	 * @throws Exception
	 */
	public ResponseBuilderHelper addGuestInfo(GuestProfile profile, GuestLogonOperation operation) throws Exception
	{
		if(profile != null)
		{
			openTag(Constants.GUEST_INFO_TAG);
			addParameter(Constants.TYPE_TAG, "GUEST");
			addParameter(Constants.PHONE_NUMBER_TAG, profile.getPhone());
			addParameter(Constants.GUEST_CODE_TAG, profile.getCode());
			addParameter(Constants.FIRSTNAME_TAG, profile.getFirstname());
			addParameter(Constants.PATRNAME_TAG, profile.getPatrname());
			addParameter(Constants.SURNAME_TAG, profile.getSurname());
			addParameter(Constants.BIRTHDATE_TAG, profile.getBirthdate());
			addParameter(Constants.PASSPORT_TAG, profile.getPassport());
			addParameter(Constants.TB_TAG, profile.getTb());

			closeTag();
		}

		if(operation != null)
		{
			openTag(Constants.GUEST_PAPAMS_TAG);
			addParameter(Constants.GUEST_LOGON_TYPE_TAG, operation.getLogonType().name());
			addParameter(Constants.GUEST_LOGIN, operation.getGuestLogin());
			if(operation.getGuestProfileId() != null)
				addParameter(Constants.GUEST_PROFILE_ID_TAG, operation.getGuestProfileId());
			closeTag();
		}


		return this;
	}

	/**
	 * Добавить информацию по наличию у гостя не гостевого профился в ЦСА и подключенности его телефона к МБ
	 * guestParams            [1]
	 *     profile_id         [0..1]
	 *     phoneConnectMB     [1]
	 * @param hasPhoneInMB Есть ли номер телефона в МБ
	 * @param csaProfileId Индентификатор профиля в ЦСА
	 * @return Построитель ответов
	 * @throws Exception
	 */
	public ResponseBuilder addAdditionInformationForGuest(boolean hasPhoneInMB, Long csaProfileId) throws Exception
	{
		openTag(Constants.GUEST_PAPAMS_TAG);
		if (csaProfileId != null)
		{
			addParameter(Constants.PROFILE_ID_TAG, csaProfileId);
		}
		addParameter(Constants.PHONE_CONNECT_MB_TAG, hasPhoneInMB);
		closeTag();
		return this;
	}

	/**
	 * Добавить информацию о коннекторe.
	 * @param connector коннетор
	 */
	public ResponseBuilderHelper addConnectorInfo(Connector connector) throws Exception
	{
		return addConnectorInfo(connector, null);
	}

	/**
	 * Добавить информацию о коннекторах.
	 * ConnectorInfo                Информация о коннекторе                                     [0..n]
	 *      GUID                    идентификатор коннектора                                    [1]
	 *      deviceState             предыдущее состояние устройства                             [0..1]
	 *      deviceInfo              информация об устройстве                                    [0..1]
	 *      cbCode                  Подразделение пользователя                                  [1]
	 *      userId                  Цифровой логин пользователя                                 [1]
	 *      cardNumber              "карта входа"                                               [1]
	 *      login                   логин/алиас                                                 [0..1]
	 *      type                    тип коннектора(TERMINAL, CSA, MAPI)                         [1]
	 *      creationDate            дата создания коннектора                                    [1]
	 *      passwordCreationDate    дата создания пароля                                        [0..1]
	 * @param connectors список коннекторов
	 * @return постоитель ответов.
	 * @throws Exception
	 */
	public ResponseBuilderHelper addConnectorsInfo(List<? extends Connector> connectors) throws Exception
	{
		for (Connector connector : connectors)
		{
			addConnectorInfo(connector);
		}
		return this;
	}

	/**
	 * Добавить информацию о пользователе (только общие сведения) по профилю.
	 * UserInfo		                Информация о пользователе                                   [1]
	 *      firstname               Имя пользователя                                            [1]
	 *      patrname                Отчество пользователя                                       [0..1]
	 *      surname                 Фамилия пользователя                                        [1]
	 *      birthdate               Дата рождения пользователя                                  [1]
	 *      passport                ДУЛ пользователя                                            [1]
	 * @param profile коннетор
	 * @return постоитель ответов.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addUserInfo(Profile profile) throws SAXException
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		openTag(Constants.USER_INFO_TAG)
				.addParameter(Constants.FIRSTNAME_TAG, profile.getFirstname())
				.addParameterIfNotEmpty(Constants.PATRNAME_TAG, profile.getPatrname())
				.addParameter(Constants.SURNAME_TAG, profile.getSurname())
				.addParameter(Constants.BIRTHDATE_TAG, profile.getBirthdate())
				.addParameter(Constants.PASSPORT_TAG, profile.getPassport())
				.addParameter(Constants.TB_TAG, profile.getTb())
		.closeTag();
		return this;
	}

	/**
	 * Добавить полную информацию о пользователе по UserInfo
	 * UserInfo	                    Информация о пользователе                                   [1]
	 *      firstname               Имя пользователя                                            [1]
	 *      patrname                Отчество пользователя                                       [0..1]
	 *      surname                 Фамилия пользователя                                        [1]
	 *      birthdate               Дата рождения пользователя                                  [1]
	 *      passport                ДУЛ пользователя                                            [1]
	 *      cbCode                  cbCode пользователя                                         [1]
	 * @param userInfo информация о пользователе
	 * @return построитель ответов
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addUserInfo(UserInfo userInfo) throws SAXException
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null");
		}
		openTag(Constants.USER_INFO_TAG)
				.addParameter(Constants.FIRSTNAME_TAG, userInfo.getFirstname())
				.addParameterIfNotEmpty(Constants.PATRNAME_TAG, userInfo.getPatrname())
				.addParameter(Constants.SURNAME_TAG, userInfo.getSurname())
				.addParameter(Constants.BIRTHDATE_TAG, userInfo.getBirthdate())
				.addParameter(Constants.PASSPORT_TAG, userInfo.getPassport())
				.addParameter(Constants.TB_TAG, Utils.getTBByCbCode(userInfo.getCbCode()))
		.closeTag();
		return this;
	}

	/**
	 * Добавить информацию о сессии
	 * SessionInfo                  информация о сессии                                         [1]
	 *      SID		                идентификатор открытой сессии.                              [1]
	 *      creationDate            дата создания сессии                                        [1]
	 *      expireDate              дата инвалиации сессии (после истечения этой даты
	 *                              запросы по сессии не будут приниматься)                     [1]
	 *      prevSessionDate         дата открытия предыдущей сессии                             [0..1]
	 *      prevSID                 идентификатор предыдущей сессии                             [0..1]
	 * @param session сессия
	 * @return постоитель ответов.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addSessionInfo(Session session) throws SAXException
	{
		if (session == null)
		{
			throw new IllegalArgumentException("Сессия не может быть null");
		}
		openTag(Constants.SESSION_INFO_TAG)
				.addParameter(Constants.SID_TAG, session.getGuid())
				.addParameter(Constants.CREATION_DATE_TAG, session.getCreationDate())
				.addParameter(Constants.EXPIRE_DATE_TAG, session.getExpireDate())
				.addParameterIfNotEmpty(Constants.PREV_SESSION_DATE_TAG, session.getPrevSessionDate())
				.addParameterIfNotEmpty(Constants.PREV_SID_TAG, session.getPrevSessionGuid())
		.closeTag();
		return this;
	}

	/**
	 * Добавить информацию о способах подтверждения операций клиентом
	 * сonfirmationInfo             информация о способах подтверждения операций клиентом       [1]
	 *      preferredConfirmType    предпочтительный способ подтверждения                       [1]
	 *      cardConfirmationSource  информация о картах для подтверждения                       [0..1]
	 *          cardNumber          номер карты                                                 [1..n]
	 *
	 * @param operation операция
	 * @return постоитель ответов.
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addConfirmationInfo(GetConfirmationInfoOperation operation) throws SAXException
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция не может быть null");
		}
		ConfirmationInformation information = operation.getConfirmationInformation();
		openTag(Constants.CONFIRMATION_INFO_TAG)
				.addParameter(Constants.PREFERRED_CONFIRM_TYPE_TAG, information.getConfirmStrategyType().name())
		        .addParameter(Constants.PUSH_ALLOWED_TAG, information.isPushAllowed());

		List<CardInformation> cardInformations = information.getConfirmationSources();
		if (CollectionUtils.isNotEmpty(cardInformations))
		{
			openTag(Constants.CARD_CONFIRMATION_SOURCE_TAG);
			for (CardInformation cardInformation : cardInformations)
			{
				addParameter(Constants.CARD_NUMBER_TAG, cardInformation.getNumber());
			}
			closeTag();
		}
		closeTag();
		return this;
	}

	/**
	 * Добавить информацию о типе зоны входа пользователя в мобильное приложение
	 * @param authorizedZoneType тип зоны входа
	 * @return построитель ответов
	 * @throws Exception
	 */
	public ResponseBuilderHelper addAuthorizedZoneType(AuthorizedZoneType authorizedZoneType) throws Exception
	{
		if (authorizedZoneType == null)
		{
			throw new IllegalArgumentException("Тип зоны входа пользователя не может быть null");
		}
		addParameter(Constants.AUTHORIZED_ZONE_PARAM_NAME, authorizedZoneType.name());
		return this;
	}

	/**
	 * Добавить информацию о блоках
	 * @param nodes - список блоков
	 * @return построитель ответов
	 */
	public ResponseBuilderHelper addNodesInfo(List<Node> nodes) throws SAXException
	{
		if (CollectionUtils.isEmpty(nodes))
			throw new IllegalArgumentException("Список блоков не заполнен");

		openTag(Constants.NODES_TAG);
		for (Node node : nodes)
		{
			addNodeInfo(node);
		}
		closeTag();
		return this;
	}

	/**
	 * Добавить полную информацию о блоке
	 * @param profileNode связь клиента с блоком
	 * @return потсроитель ответов
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addNodeInfo(ProfileNode profileNode) throws SAXException
	{
		if (profileNode == null)
		{
			throw new IllegalArgumentException("Информация о связи узла с профилем не может быть null");
		}
		return addNodeInfo(profileNode.getNode(), profileNode.getProfileType());
	}

	/**
	 * Добавить полную информацию о блоке
	 * @param node блок
	 * @return потсроитель ответов
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addNodeInfo(Node node) throws SAXException
	{
		if (node == null)
		{
			throw new IllegalArgumentException("Блок не может быть null");
		}
		return addNodeInfo(node, null);
	}

	/**
	 * Добавить полную информацию о блоке
	 * @param node блок
	 * @param profileType тип профиля (null - вне контекста связки профиль-блок)
	 * @return построитель ответов
	 * @throws SAXException
	 */
	private ResponseBuilderHelper addNodeInfo(Node node, ProfileType profileType) throws SAXException
	{
		if (node == null)
		{
			throw new IllegalArgumentException("Блок не может быть null");
		}
		openTag(Constants.NODE_INFO_TAG)
				.addParameter(Constants.NODE_INFO_ID_NODE_NAME, node.getId())
				.addParameter(Constants.NODE_INFO_NAME_NODE_NAME, node.getName())
				.addParameter(Constants.HOST_TAG, node.getHostname())
				.addParameter(Constants.LISTENER_HOST_TAG, node.getListenerHostname())
				.addParameter(Constants.NODE_INFO_NEW_USERS_ALLOWED_NODE_NAME, node.isNewUsersAllowed())
				.addParameter(Constants.NODE_INFO_EXISTING_USERS_ALLOWED_NODE_NAME, node.isExistingUsersAllowed())
				.addParameter(Constants.NODE_INFO_TEMPORARY_USERS_ALLOWED_NODE_NAME, node.isTemporaryUsersAllowed())
				.addParameter(Constants.NODE_INFO_USERS_TRANSFER_ALLOWED_NODE_NAME, node.isUsersTransferAllowed())
				.addParameter(Constants.NODE_INFO_ADMIN_AVAILABLE_NODE_NAME, node.isAdminAvailable())
				.addParameter(Constants.NODE_INFO_GUEST_AVAILABLE_NODE_NAME, node.isGuestAvailable())
				.addParameter(Constants.SMS_QUEUE_NAME_TAG, node.getSmsQueueName())
				.addParameter(Constants.SMS_FACTORY_NAME_TAG, node.getSmsFactoryName())
				.addParameter(Constants.ERMB_QUEUE_NAME_TAG, node.getErmbQueueName())
				.addParameter(Constants.ERMB_FACTORY_NAME_TAG, node.getErmbFactoryName())
				.addParameter(Constants.DICTIONARY_QUEUE_NAME_TAG, node.getDictionaryQueueName())
				.addParameter(Constants.DICTIONARY_FACTORY_NAME_TAG, node.getDictionaryFactoryName())
				.addParameter(Constants.MULTI_NODE_DATA_QUEUE_NAME_TAG, node.getMultiNodeDataQueueName())
				.addParameter(Constants.MULTI_NODE_DATA_FACTORY_NAME_TAG, node.getMultiNodeDataFactoryName())
				.addParameter(Constants.MBK_REGISTRATION_QUEUE_NAME_TAG, node.getMbkRegistrationQueueName())
				.addParameter(Constants.MBK_REGISTRATION_FACTORY_NAME_TAG, node.getMbkRegistrationFactoryName())
				.addParameterIfNotEmpty(Constants.PROFILE_TYPE_TAG, profileType);
		closeTag();
		return  this;
	}

	/**
	 * Добавить информацию о клиентах
	 * @param clientInformationList - список клиентов
	 * @return построитель ответов
	 */
	public ResponseBuilderHelper addClientsInfo(List<ClientInformation> clientInformationList) throws SAXException
	{
		openTag(Constants.CLIENTS_TAG);
		for (ClientInformation client : clientInformationList)
		{
			openTag(Constants.CLIENT_INFO_TAG)
				.addParameter(Constants.EXTERNAL_ID_PARAM_NAME, client.getId())
				.addParameter(Constants.FIRSTNAME_TAG, client.getFirstname())
				.addParameter(Constants.SURNAME_TAG, client.getSurname())
				.addParameterIfNotEmpty(Constants.PATRNAME_TAG, client.getPatrname())
				.addParameter(Constants.BIRTHDATE_TAG, client.getBirthday())
				.addParameter(Constants.PASSPORT_TAG, client.getDocument())
				.addParameter(Constants.TB_TAG, client.getTb())
				.addParameterIfNotEmpty(Constants.LOGIN_TAG, client.getLogin())
				.addParameter(Constants.USER_ID_TAG, client.getUserId())
				.addParameterIfNotEmpty(Constants.LAST_LOGIN_DATE_TAG, client.getLastLoginDate())
				.addParameter(Constants.AGREEMENT_NUMBER_TAG, client.getAgreementNumber())
				.addParameter(Constants.CREATION_TYPE_TAG, StringHelper.getNullIfNull(client.getCreationType()))
				.addParameter(Constants.ERMB_STATUS, client.getErmbStatus())
				.addParameterIfNotEmpty(Constants.MAIN_PHONE_TAG, client.getErmbPhoneNumber())
				.openTag(Constants.LOCKS_TAG);
					for (ProfileLock lock : client.getLocks())
					{
						openTag(Constants.LOCK_INFO_TAG)
							.addParameter(Constants.LOCK_FROM_TAG, lock.getFrom())
							.addParameterIfNotEmpty(Constants.LOCK_TO_TAG, lock.getTo())
							.addParameter(Constants.REASON_TAG, lock.getReason())
							.addParameter(Constants.LOCKER_FIO_TAG, lock.getLockerFIO())
						.closeTag();
					}
					for (ProfileLockCHG071536 lock : client.getLocksCHG071536())
					{
						openTag(Constants.LOCK_INFO_TAG)
							.addParameter(Constants.LOCK_FROM_TAG, lock.getFrom())
							.addParameterIfNotEmpty(Constants.LOCK_TO_TAG, lock.getTo())
							.addParameter(Constants.REASON_TAG, lock.getReason())
							.addParameter(Constants.LOCKER_FIO_TAG, lock.getLockerFIO())
						.closeTag();
					}
				closeTag()
				.openTag(Constants.NODES_INFO_NODE_NAME);
					for (ClientNodeInfo node : client.getNodes())
					{
						openTag(Constants.NODE_INFO_NODE_NAME)
							.addParameter(Constants.NODE_INFO_ID_NODE_NAME, node.getId())
							.addParameter(Constants.PROFILE_NODE_STATE_TAG, node.getState())
							.addParameter(Constants.PROFILE_NODE_TYPE_TAG,  node.getType())
						.closeTag();
					}
				closeTag()
			.closeTag();
		}
		closeTag();
		return this;
	}

	/**
	 * Добавить информацию о клиентах
	 * @param date - дата снятия блокировки
	 * @return построитель ответов
	 */
	public ResponseBuilderHelper addBlockingRuleInfo(String date) throws SAXException
	{
		addParameter(Constants.BLOCKING_RULES_DATE, date);
		return this;
	}

	/**
	 * Добавление ИД гостевого аккаунта в ответ.
	 * @param id ИД гостя
	 * @return Построитель ответов
	 * @throws SAXException
	 */
	public ResponseBuilderHelper addGuestIdInfo(Long id)throws SAXException
	{
		addParameter(Constants.GUEST_PROFILE_ID_TAG, id);
		return this;
	}
}
