package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorState;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.operations.guest.GuestConfirmableOperation;
import com.rssl.auth.csa.back.servises.operations.guest.GuestPhoneAuthenticationOperation;
import com.rssl.auth.csa.back.servises.operations.ActualizationLogonInfoOperation;
import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.SAXException;

import static com.rssl.phizic.rsa.Constants.PROHIBITION_OPERATION_DEFAULT_ERROR_MESSAGE;
import static com.rssl.phizic.rsa.Constants.PROFILE_BLOCKED_ERROR_MESSAGE;

import java.util.Calendar;
import java.util.Collection;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class FailureResponseHelper
{
	private static final String DATE_TIME_FORMAT = "%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS";

	private static final int ERROR_CODE_IDENTIFICATION_FAILED = 500;            //Ошибка идентификации
	private static final int ERROR_CODE_BAD_LOGIN = 502;                        //Недопустимый логин
	private static final int ERROR_CODE_BAD_PASSWORD = 503;                     //Недопустимый пароль
	private static final int ERROR_CODE_RESTRICTON_VIOLATED = 504;              //Нарушено бизнес ограничениe на совершение операции
	private static final int ERROR_CODE_LOGIN_ALREADY_REGISTERED = 505;         //Логин уже занят
	private static final int ERROR_CODE_BAD_OUID = 506;                         //Недействительный идентификатор операции.
	private static final int ERROR_CODE_WRONG_CONFIRM_ATEMPT = 507;             //Некорректная попытка подтверждения.
	private static final int ERROR_CODE_LOGIN_NOT_FOUND = 508;                  //Логин не зарегистрирован в системе
	private static final int ERROR_CODE_CONNECTOR_BLOCKED = 509;                //Коннектор заблокирован
	private static final int ERROR_CODE_AUTENTIFICATION_FAILED = 510;           //Ошибка аутентификации
	private static final int ERROR_CODE_HACKING_RESTRICTON_VIOLATED = 511;      //Сработало ограничение-защита от атак (частота запросов и т.п.).
	private static final int ERROR_CODE_BAD_MGUID = 512;                        //Недействительный идентификатор приложения.
	private static final int ERROR_CODE_BAD_SID = 513;                          //Недействительный идентификатор сессии
	private static final int ERROR_CODE_NOT_FOUND_MOBILE_REGISTRATION = 514;    //Не найдено ни одного номера зарегистрированого в мобильном банке
	private static final int ERROR_CODE_ERROR_SEND_SMS_MESSAGE = 515;           //Не удалось отправить смс сообщение ни на один из зарегистрированных номеров
	private static final int ERROR_CODE_GATE_TIMEOUT = 516;                     //Ошибка времени ожидания ответа

	private static final int ERROR_CODE_ACTUALIZATION_LOGON_INFO_REQUIRED = 517;//Ошибка "требуется актуализация информации о входе" (выбор логина)
	private static final int ERROR_CODE_AUTHENTIFICATION_REGUIRED = 518;         //Ошибка. требуется аутентификация
	private static final int ERROR_CODE_SERVICE_UNAVAILABLE = 519;               //Ошибка. сервис временно недоступен
	private static final int ERROR_CODE_IPAS_UNAVAILABLE = 520;                  //Недоступен сервис iPas
	private static final int ERROR_CODE_RETRY_IPAS_UNAVAILABLE = 530;            //Недоступен сервис iPas, просим клиента попробовать еще раз.

	private static final int ERROR_CODE_BUSINESS_ENVIRONMENT_REFUSE = 521;       //Отказ деловой среды в верификации
	private static final int ERROR_CODE_BUSINESS_ENVIRONMENT_ERROR = 522;        //Ошибка взаимодействия с деловой средой

	private static final int ERROR_CODE_ACTIVE_PHONE_ERMB_REQUIRED = 523;        //Оперции с ЕРМБ возоможны только по активному (основному) телефону
	private static final int ERROR_CODE_BLOCKING_RULE_ACTIVE = 524;              //Действует огранение на вход и регистрацию

	private static final int ERROR_CODE_CARD_BY_PHONE_NOT_FOUND = 525;           //Не найдена карта по номеру телефона
	private static final int ERROR_CODE_USER_INFO_BY_CARD_NOT_FOUND = 526;       //Не найдена информация о пользователе по карте

	private static final int ERROR_CODE_USER_NOT_FOUND = 527;                    //Не найдена информация о пользователе
	private static final int ERROR_CODE_USER_ALREADY_REGISTERED = 528;           //Не найдена информация о пользователе
	private static final int ERROR_CODE_DUPLICATE_PHONE_REGISTRATIONS = 529;     //Найдены дубликаты регистраций телефонов
	private static final int ERROR_CODE_ACTIVATE_PHONE_REGISTRATION_ERROR = 531; //Ошибка активации регистрации телефона

	private static final int ERROR_CODE_PROFILE_NOT_FOUND = 532;                 //Не найден профиль пользователя

	private static final int ERROR_CODE_PROFILE_LOCKED                      = 533; // Профиль заблокирован
	private static final int ERROR_CODE_NOT_MAIN_CARD_REGISTRATION          = 534; // карта регистрации не является основной
	private static final int ERROR_CODE_INACTIVE_CARD_REGISTRATION          = 535; // карта регистрации не является активной
	private static final int ERROR_CODE_DENY_CB_CODE_OPERATION              = 536; // ограничение выполнения данной операции в разрезе ТБ
	private static final int ERROR_CODE_USER_REGISTERED_ALREADY_ENTERED     = 537; // огриничение регистрации, если пользователь уже входил в систему

	private static final int ERROR_CODE_ACTIVE_CSA_CONNECTORS_NOT_FOUND     = 538; // не найдены активные ЦСА коннекторы
	private static final int ERROR_CODE_USER_INFO_BY_PHONE_NOT_FOUND        = 539; // не найдена информация о пользователе по телефону
	private static final int ERROR_TECHNO_BREAK                             = 540; // тех. перерыв
	private static final int ERROR_EXTERNAL_SYSTEM_STAND_IN                 = 541; // Stand-in внешней системы
	private static final int ERROR_CHECK_IMSI                               = 542; // несовпажение IMSI
	private static final int ERROR_CODE_STAND_IN_MODE                       = 543; // Недоступность операции в режиме StandIn
	private static final int ERROR_CODE_FRAUD_BLOCK_PROFILE                 = 544; // ФРОД МОНИТОРИНГ: блокировка профиля
	private static final int ERROR_CODE_FRAUD_PROHIBITION_OPERATION         = 545; // ФРОД МОНИТОРИНГ: запрет выполнения операции

	private static final String ERROR_LOGIN_NOT_FOUND = "Вы указали неправильное имя или неправильный пароль.";
	private static final String ERROR_NOT_FOUND_MOBILE_REGISTRATION = "У Вас не подключена услуга «Мобильный банк». Подключить услугу Вы можете в любом банкомате Сбербанка. Экономный пакет предоставляется бесплатно.";
	private static final String ERROR_SEND_SMS_MESSAGE = "В целях безопасности Сбербанк приостановил отправку SMS-паролей на номер %s в связи с заменой SIM-карты до момента Вашего обращения в Контактный Центр Сбербанка по телефону 8-800-555-5550.";
	private static final String ERROR_TOO_MANY_MOBILE_CONNECTORS = "Вы уже зарегистрировали максимальное количество мобильных устройств. Пожалуйста, войдите в раздел Мобильных приложений в Сбербанк Онлайн и отключите лишние устройства для завершения регистрации.";
	private static final String ERROR_HACKING_RESTRICTON_VIOLATED = "Вы превысили количество заявок на регистрацию приложений. Пожалуйста, повторите попытку позже.";
	private static final String ERROR_BAD_OUID = "Вы превысили количество попыток ввода пароля для подтверждения операции. Пожалуйста, создайте новую заявку на регистрацию приложения.";
	private static final String ERROR_WRONG_CONFIRM_ATEMPT = "Вы неверно указали SMS-пароль для подтверждения регистрации приложения. Пожалуйста, введите пароль снова.";
	private static final String ERROR_GATE_TIMEOUT = "Превышено время ожидания ответа от внешней системы.";
	private static final String ERROR_ACTIVE_PHONE_ERMB_REQUIRED = "Ваш запрос не выполнен, так как данный номер телефона не выбран основным при подключении Мобильного банка. Для выполнения операции отправьте сообщение с другого номера телефона.";

	private final String responceType;

	/**
	 * Конструктор
	 * @param responceType тип запроса
	 */
	public FailureResponseHelper(String responceType)
	{
		this.responceType = responceType;
	}

	/**
	 * Сформировать ответ-ошибку: карта по номеру телефону не найдена
	 * @param phoneNumber номер телефона
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildCardByPhoneNotFoundResponse(String phoneNumber) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_CARD_BY_PHONE_NOT_FOUND, "Карта по телефону с номером " + phoneNumber + " не найдена.");
	}

	/**
	 * Сформировать ответ-ошибку: информация о пользователе телефону не найдена
	 * @param phoneNumber номер телефона
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildUserInfoByPhoneNotFoundResponse(String phoneNumber) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_USER_INFO_BY_PHONE_NOT_FOUND, "Информация о пользователе по телефону с номером " + phoneNumber + " не найдена.");
	}

	/**
	 * Сформировать ответ-ошибку: информация о пользователе по карте не найдена
	 * @param cardNumber номер карты
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildUserInfoByCardNotFoundResponse(String cardNumber) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_USER_INFO_BY_CARD_NOT_FOUND, "Информация о пользователе по карте " + cardNumber + " не найдена.");
	}

	/**
	 * Постоить ответ-ошибку "не профдена идентификация"
	 * @param e суть ошибки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildIdentificationFailedResponse(IdentificationFailedException e) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_IDENTIFICATION_FAILED, e.getMessage());
	}

	/**
	 * Постоить ответ-ошибку о недопустимом логине
	 * @param errorMessage текст ошибки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildBadLoginResponse(String errorMessage) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_LOGIN, errorMessage);
	}

	/**
	 * Постоить ответ-ошибку о недопустимом пароле
	 * @param errorMessage текст ошибки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildBadPasswordResponse(String errorMessage) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_PASSWORD, errorMessage);
	}

	/**
	 * Постоить ответ-ошибку о недействительном идентификаторе операции
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildBadOUIDResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_OUID, ERROR_BAD_OUID);
	}

	/**
	 * Постоить ответ-ошибку о недействительном идентификаторе сессии
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildBadSIDResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_SID, "Недействительный идентификатор сессии.");
	}

	/**
	 * Постоить ответ-ошибку "Пользователь уже зарегистрирован"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildUserAlreadyRegisteredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_USER_ALREADY_REGISTERED, "Вы уже зарегистрированы в системе «Сбербанк Онлайн». Если Вы забыли свой пароль – воспользуйтесь процедурой восстановления.");
	}

	/**
	 * Построить ответ-ошибку "Пользователь уже входил в систему"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildUserAlreadyEnteredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_USER_REGISTERED_ALREADY_ENTERED, "Регистрация невозможна - Вы уже являетесь пользователем Сбербанк Онлайн. Если Вы не можете войти в систему просьба воспользоваться функцией восстановления пароля по ссылке забыли пароль?. Также идентификатор пользователя и пароль можно получить через устройства самообслуживания Сбербанка России с помощью банковской карты и в контактном центре Сбербанка России +7 (495) 500 5550, 8 (800) 555 5550.");
	}

	/**
	 * Постоить ответ-ошибку "Логин уже занят"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildLoginAlreadyRegisteredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_LOGIN_ALREADY_REGISTERED, "Введенный идентификатор уже используется в системе. Введите другое значение.");
	}

	/**
	 * Постоить ответ-ошибку "Логин не найден"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildLoginNotFoundResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_LOGIN_NOT_FOUND, ERROR_LOGIN_NOT_FOUND);
	}

	/**
	 * Постоить ответ-ошибку "ошибка аутентификации"
	 * @param connector коннектор. не может быть null.
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildAuthenticationFailedResponse(Connector connector) throws SAXException
	{
		if (connector.getState() == ConnectorState.ACTIVE)
		{
			return buildAuthenticationFailedResponse(ERROR_LOGIN_NOT_FOUND);
		}
		Calendar calendar = connector.getBlockedUntil();
		if (calendar != null)
		{
			long timeout = DateHelper.diff(calendar, Calendar.getInstance()) / 1000;
			return getFailureResponseBuilder(ERROR_CODE_CONNECTOR_BLOCKED, "Ваш логин заблокирован до " + String.format(DATE_TIME_FORMAT, calendar) + ". Повторите попытку позже.")
					.addFaultRsaData()
					.addParameter(Constants.BLOCKING_TIMEOUT, timeout > 0 ? timeout : 0)
					.end().getResponceInfo();
		}
		return buildFailureResponse(ERROR_CODE_CONNECTOR_BLOCKED, "Ваш логин заблокирован. Повторите попытку позже.");
	}

	/**
	 * Постоить ответ-ошибку "ошибка аутентификации"
	 * @param message сообщение об ошибке
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildAuthenticationFailedResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_AUTENTIFICATION_FAILED, message);
	}

	/**
	 * Постоить ответ-ошибку "недействительный идентификатор приложения"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildBadMGUIDResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_MGUID, "Недействительный идентификатор приложения. Пройдите процедуру регистрации приложения.");
	}

	/**
	 * Постоить ответ-ошибку "ошибка аутентификации гостя"
	 * @param profile профиль, для которого не прошла аутентификация
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildAuthenticationFailedResponse(GuestProfile profile) throws SAXException
	{
		if(profile != null && profile.isBlock())
		{
			Calendar calendar = profile.getBlockedUntil();
			if (calendar != null)
			{
				long timeout = DateHelper.diff(calendar, Calendar.getInstance()) / 1000;
				return getFailureResponseBuilder(ERROR_CODE_CONNECTOR_BLOCKED, "Ваш логин заблокирован до " + String.format(DATE_TIME_FORMAT, calendar) + ". Повторите попытку позже.")
						.addFaultRsaData()
						.addParameter(Constants.BLOCKING_TIMEOUT, timeout > 0 ? timeout : 0)
						.end().getResponceInfo();
			}
		}

		return buildFailureResponse(ERROR_CODE_AUTENTIFICATION_FAILED, ERROR_LOGIN_NOT_FOUND);
	}

	/**
	 * Постоить ответ-ошибку "неверный код подтверждения"
	 * @param operation подтверждаемая операция
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildFailureConfirmResponse(ConfirmableOperationBase operation) throws Exception
	{
		if (operation == null)
		{
			return buildBadOUIDResponse();
		}
		return getFailureResponseBuilder(ERROR_CODE_WRONG_CONFIRM_ATEMPT, ERROR_WRONG_CONFIRM_ATEMPT)
				.addConfirmParameters(operation)
				.end().getResponceInfo();
	}

	/**
	 * Постоить ответ-ошибку "неверный код подтверждения"
	 * @param operation подтверждаемая операция
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildFailureConfirmResponse(GuestConfirmableOperation operation) throws Exception
	{
		if (operation == null)
		{
			return buildBadOUIDResponse();
		}
		return getFailureResponseBuilder(ERROR_CODE_WRONG_CONFIRM_ATEMPT, ERROR_WRONG_CONFIRM_ATEMPT)
				.addConfirmParameters(operation)
				.end().getResponceInfo();
	}

	/**
	 * Постоить ответ-ошибку "неверный пароль гостевой аутентификации"
	 * @param operation подтверждаемая операция
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildGuestFailurePasswordResponse(GuestPhoneAuthenticationOperation operation) throws Exception
	{
		if (operation == null)
		{
			return buildBadOUIDResponse();
		}
		return getFailureResponseBuilder(ERROR_CODE_WRONG_CONFIRM_ATEMPT, ERROR_WRONG_CONFIRM_ATEMPT)
				.addGuestAuthParameters(operation)
				.end().getResponceInfo();
	}

	/**
	 * Постоить ответ-ошибку "превышено количество неподтвержденных запросов на регистрацию мобильный приложений"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildTooManyMobileRegistrationRequestResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_HACKING_RESTRICTON_VIOLATED, ERROR_HACKING_RESTRICTON_VIOLATED);
	}

	/**
	 * Постоить ответ-ошибку "превышено количество неподтвержденных запросов на регистрацию пользователей"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildTooManyUserRegistrationRequestResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_HACKING_RESTRICTON_VIOLATED, "Вы превысили количество попыток регистрации в системе. Пожалуйста, повторите попытку позже");
	}

	/**
	 * Постоить ответ-ошибку "Не найдено ни одного номера зарегистрированого в мобильном банке"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildMobileBankRegistrationNotFoundRequestResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_NOT_FOUND_MOBILE_REGISTRATION, ERROR_NOT_FOUND_MOBILE_REGISTRATION);
	}

	/**
	 * Постоить ответ-ошибку "Превышено время ожидания ответа"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildGateTimeoutResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_GATE_TIMEOUT, ERROR_GATE_TIMEOUT);
	}

	/**
	 * Постоить ответ-ошибку "Не удалось отправить смс сообщение ни на один из зарегистрированных номеров"
	 * @param errorPhones - список номеров телефонов по которым не удалось отправить смс-сообщение
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildMobileBankSendSmsMessageRequestResponse(Collection<String> errorPhones) throws SAXException
	{
		String hiddenPhones = PhoneNumberUtil.translateAndHidePhoneNumbers(errorPhones, PhoneNumberFormat.MOBILE_INTERANTIONAL);
		return getFailureResponseBuilder(ERROR_CODE_ERROR_SEND_SMS_MESSAGE, String.format(ERROR_SEND_SMS_MESSAGE, hiddenPhones))
				.addErrorConfirmParameters(hiddenPhones)
				.addFaultRsaData()
				.end().getResponceInfo();
	}

	/**
	 * Постоить ответ-ошибку "превышено количество зарегистрированных устройств"
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildTooManyMobileConnectorsResponse() throws SAXException
	{
		return buildRestrictionViolatedResponse(ERROR_TOO_MANY_MOBILE_CONNECTORS);
	}

	/**
	 * Построить ответ ошибку о нарушении ограничения
	 * @param message текст ошибки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildRestrictionViolatedResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_RESTRICTON_VIOLATED, message);
	}

	/**
	 * Построить ответ ошибку о блокированно профиле
	 * @param message текст ошибки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildProfileLockedResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_PROFILE_LOCKED, message);
	}

	/**
	 * Построить ответ ошибку о недоступности регистрации по причине того, что карта регистрации не основная
	 * @param message текст ошибки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildNotMainCardRegistrationResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_NOT_MAIN_CARD_REGISTRATION, message);
	}

	/**
	 * Построить ответ ошибку о недоступности регистрации по причине неактивности карты регистрации
	 * @param message текст ошибки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildInactiveCardRegistrationResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_INACTIVE_CARD_REGISTRATION, message);
	}

	/**
	 * Построить ответ ошибку об ограничении операции в разрезе ТБ
	 * @param message текст ошибки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildDenyCbCodeOperationResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_DENY_CB_CODE_OPERATION, message);
	}

	/**
	 * Построить ответ ошибку об ограничении на вход и регистрацию
	 * @param message текст ошибки
	 * @param date дата окончания блокировки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildBlockingRuleActiveResponse(String message, String date) throws SAXException
	{
		if(StringHelper.isNotEmpty(date))
			return getFailureResponseBuilder(ERROR_CODE_BLOCKING_RULE_ACTIVE, message).addBlockingRuleInfo(date).addFaultRsaData().end().getResponceInfo();
		return buildFailureResponse(ERROR_CODE_BLOCKING_RULE_ACTIVE, message);
	}

	/**
	 * сформировать ответ отказ о необходимости актуализации информации о входе (выбор логина)
	 * @param operation операция актуализации информации о входе.
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildActualizationLogonInfoRequiredResponse(ActualizationLogonInfoOperation operation) throws Exception
	{
		ResponseBuilderHelper builder = getFailureResponseBuilder(ERROR_CODE_ACTUALIZATION_LOGON_INFO_REQUIRED, "Требуется актуализация информации о входе").addOUID(operation);
		for (Connector connector : operation.getConnectors())
		{
			builder.addConnectorInfo(connector);
		}
		return builder.end().getResponceInfo();
	}

	/**
	 * сформировать ответ отказ о необходимости аутентификации
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildAuthentificationReguiredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_AUTHENTIFICATION_REGUIRED, "Требуется аутентификация");
	}

	/**
	 * сформировать ответ отказ сервис временно недоступен
	 * @param message сообщение для пользователя
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildServiceUnavailableResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_SERVICE_UNAVAILABLE, message);
	}

	/**
	 * сформировать ответ отказ сервис временно недоступен из-зи недоступности iPas
	 * @param connector коннектор, в контектсе которого происходило взаимодействие с iPas
	 * @return информация об ответе
	 * @throws SAXException
	 */
	//TODO
	public ResponseInfo buildIpasUnavailableResponse(Connector connector) throws Exception
	{
		ResponseBuilderHelper builder = getFailureResponseBuilder(ERROR_CODE_IPAS_UNAVAILABLE, "Недоступен сервис iPas");
		if (connector != null)
		{
			builder
					.addUserInfo(connector.getProfile())
					.addConnectorInfo(connector);
		}
		return builder.end().getResponceInfo();
	}

	/**
	 * сформировать ответ-отказ. Ipas временно недоступен. Пользователь должен попытаться еще.
	 * @return информация об ответе
	 * @throws Exception
	 * @param message сообщение для пользователя.
	 */
	public ResponseInfo buildRetryIpasUnavailableResponse(String message) throws Exception
	{
		ResponseBuilderHelper builder = getFailureResponseBuilder(ERROR_CODE_RETRY_IPAS_UNAVAILABLE, message);
		return builder.end().getResponceInfo();
	}

	/**
	 * Генерит ответ с ошибкой верификации данных в деловой среде
	 * @param message ошибка
	 * @return ответ с ошибкой
	 * @throws SAXException
	 */
	public ResponseInfo buildBusinessEnvironmentRefuseResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BUSINESS_ENVIRONMENT_REFUSE, message);
	}

	/**
	 * Генерит ответ с ошибкой взаимодействия с деловой средой
	 * @param message ошибка
	 * @return ответ с ошибкой
	 * @throws SAXException
	 */
	public ResponseInfo buildBusinessEnvironmentErrorResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BUSINESS_ENVIRONMENT_ERROR, message);
	}

	/**
	 * Генерит ответ с ошибкой попытки выполнить действие по неактивному телефону.
	 * @return ответ с ошибкой
	 * @throws SAXException
	 */
	public ResponseInfo buildActivePhoneERMBRequiredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_ACTIVE_PHONE_ERMB_REQUIRED, ERROR_ACTIVE_PHONE_ERMB_REQUIRED);
	}

	/**
	 * Генерит ответ с ошибкой поиска клиента.
	 * @param userInfo информация по которой оскали
	 * @return ответ с ошибкой
	 * @throws SAXException
	 */
	public ResponseInfo buildUserNotFoundResponse(CSAUserInfo userInfo) throws SAXException
	{
		String errorDescription = "Не найден клиент " + userInfo.getFirstname() + " " + userInfo.getSurname() + " " + userInfo.getPatrname();
		return buildFailureResponse(ERROR_CODE_USER_NOT_FOUND, errorDescription);
	}

	/**
	 * Генерит ответ с ошибкой активации регистрации телефона
	 * @param message сообщение об ошибке
	 * @return ответ с ошибкой
	 * @throws SAXException
	 */
	public ResponseInfo buildActivatePhoneRegistrationErrorResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_ACTIVATE_PHONE_REGISTRATION_ERROR, message);
	}

	/**
	 * Генерит ответ о дублировании регистраций телефонов
	 * @param message сообщение об ошибке
	 * @return ответ с ошибкой
	 * @throws SAXException
	 */
	public ResponseInfo buildDuplicatePhoneRegistrations(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_DUPLICATE_PHONE_REGISTRATIONS, message);
	}

	/**
	 * Генерит ответ с ошибкой поиска клиента.
	 * @param template - шаблон профиля по которому производился поиск
	 * @return ответ с ошибкой
	 * @throws SAXException
	 */
	public ResponseInfo buildProfileNotFoundResponse(Profile template) throws SAXException
	{
		StringBuilder descriptionBuilder = new StringBuilder("Не найден клиент ");
		descriptionBuilder.append(template.getSurname()).append(" ");
		descriptionBuilder.append(template.getFirstname()).append(" ");
		descriptionBuilder.append(template.getPatrname()).append(" ");
		descriptionBuilder.append(" с документом ").append(template.getPassport()).append(" ");
		descriptionBuilder.append(" в тербанке ").append(template.getTb());
		return buildFailureResponse(ERROR_CODE_PROFILE_NOT_FOUND, descriptionBuilder.toString());
	}

	/**
	 * Сформировать ответ-отказ
	 * @param errorCode код отказа
	 * @param errorDescription описание отказа
	 * @return информация об ответе
	 * @throws SAXException
	 */
	private ResponseInfo buildFailureResponse(int errorCode, String errorDescription) throws SAXException
	{
		return getFailureResponseBuilder(errorCode, errorDescription).addFaultRsaData().end().getResponceInfo();
	}

	private ResponseBuilderHelper getFailureResponseBuilder(int errorCode, String errorDescription) throws SAXException
	{
		return new ResponseBuilderHelper(responceType, errorCode, errorDescription);
	}

	/**
	 * Генерит ответ с ошибкой поиска активных ЦСА коннекторов
	 * @param message сообщение
	 * @return ответ с ошибкой
	 * @throws SAXException
	 */
	public ResponseInfo buildActiveCSAConnectorsNotFoundResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_ACTIVE_CSA_CONNECTORS_NOT_FOUND, message);
	}

	/**
	 * Сформировать ответ о тех. перерыве внешней системы
	 * @param message сообщение тех. перерыва
	 * @return ответ с ошибкой
	 */
	public ResponseInfo buildTechnoBreakResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_TECHNO_BREAK, message);
	}

	/**
	 * Сформировать ответ о режиме Stand-In внешней системы
	 * @param message сообщение тех. перерыва
	 * @return ответ с ошибкой
	 */
	public ResponseInfo buildStandInResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_EXTERNAL_SYSTEM_STAND_IN, message);
	}

	/**
	 * Построить ответ ошибку о несовпадении IMSI
	 * @param message текст ошибки
	 * @param errorPhones телефоны
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildIMSICheckResponse(String message, Collection<String> errorPhones) throws SAXException
	{
		String hiddenPhones = PhoneNumberUtil.translateAndHidePhoneNumbers(errorPhones, PhoneNumberFormat.MOBILE_INTERANTIONAL);
		return getFailureResponseBuilder(ERROR_CHECK_IMSI, message)
				.addErrorConfirmParameters(hiddenPhones)
				.addFaultRsaData()
				.end().getResponceInfo();
	}

	/**
	 * Ответ о недоступности операции в режиме StandIn ЦСА
	 * @param message текст с описанием ошибки
	 * @return информация об ответе
	 * @throws SAXException
	 */
	public ResponseInfo buildCSAStandInResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_STAND_IN_MODE, message);
	}

	/**
	 * Ответ о блокировке профиля по результату проверки во ФМ
	 * @return message текст с описанием ошибки
	 * @throws SAXException
	 */
	public ResponseInfo buildFraudBlockProfileResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_FRAUD_BLOCK_PROFILE, PROFILE_BLOCKED_ERROR_MESSAGE);
	}

	/**
	 * Ответ о запрете выполнения операции по результату проверки во ФМ
	 * @return message текст с описанием ошибки
	 * @throws SAXException
	 */
	public ResponseInfo buildFraudProhibitionOperationResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_FRAUD_PROHIBITION_OPERATION, PROHIBITION_OPERATION_DEFAULT_ERROR_MESSAGE);
	}
}
