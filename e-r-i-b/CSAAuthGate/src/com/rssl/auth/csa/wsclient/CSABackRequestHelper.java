package com.rssl.auth.csa.wsclient;

import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.utils.UpdateProfileInfo;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.requests.*;
import com.rssl.auth.csa.wsclient.requests.clients.GetClientNodeStateRequestData;
import com.rssl.auth.csa.wsclient.requests.clients.GetClientProfileIdRequestData;
import com.rssl.auth.csa.wsclient.requests.clients.GetTemporaryNodeClientsCountRequestData;
import com.rssl.auth.csa.wsclient.requests.clients.UpdateClientMDMInfoRequestData;
import com.rssl.auth.csa.wsclient.requests.guest.FinishCreateGuestSessionRequestData;
import com.rssl.auth.csa.wsclient.requests.guest.GuestEntryConfirmationRequestData;
import com.rssl.auth.csa.wsclient.requests.guest.GuestEntryInitialRequestData;
import com.rssl.auth.csa.wsclient.requests.guest.GuestRegistrationRequestData;
import com.rssl.auth.csa.wsclient.requests.info.ValidateLoginInfo;
import com.rssl.auth.csa.wsclient.requests.nodes.ChangeNodesAvailabilityInfoRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.CreateProfileRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.FindProfileCardNumberListRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.FindProfileInformationRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.FindProfileInformationWithNodeInfoRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.incognito.ChangeIncognitoSettingData;
import com.rssl.auth.csa.wsclient.requests.profile.incognito.GetClientConnectorsData;
import com.rssl.auth.csa.wsclient.requests.profile.incognito.GetIncognitoSettingData;
import com.rssl.auth.csa.wsclient.requests.profile.lock.LockProfileCHG071536ByProfileIdRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.lock.LockProfileCHG071536RequestData;
import com.rssl.auth.csa.wsclient.requests.profile.lock.LockProfileForExecuteDocumentRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.lock.UnlockProfileForExecuteDocumentRequestData;
import com.rssl.auth.csa.wsclient.requests.verification.InitializeVerifyBusinessEnvironmentRequestData;
import com.rssl.auth.csa.wsclient.requests.verification.VerifyBusinessEnvironmentRequestData;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.auth.csa.wsclient.responses.ResponseData;
import com.rssl.phizic.gate.csa.*;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.gate.clients.IncognitoState;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import java.util.*;

/**
 * @author niculichev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class CSABackRequestHelper
{
	private static final CSABackRequestSender sender = new CSABackRequestSender();

	/**
	 * Послать запрос аутентификацию
	 * @param login логин
	 * @param password пароль
	 * @return ответ
	 */
	public static Document sendAuthenticationRq(String login, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new AuthenticationRequestData(login, password));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на начало регистрации
	 * @param cardNumber номер карты
	 * @param confirmStrategyType способ подтверждения
	 * @return ответ
	 */
	public static Document sendStartUserRegistrationRq(String cardNumber, ConfirmStrategyType confirmStrategyType) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartUserRegistrationRequestData(cardNumber, confirmStrategyType));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на начало самостоятельной регистрации
	 * @param cardNumber номер карты
	 * @param confirmStrategyType способ подтверждения
	 * @return ответ
	 */
	public static Document sendStartUserSelfRegistrationRq(String cardNumber, ConfirmStrategyType confirmStrategyType) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartUserSelfRegistrationRequestData(cardNumber, confirmStrategyType));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на завершение регистрации
	 * @param ouid идентификатор операции
	 * @param login логин
	 * @param password пароль
	 * @param notification необходимо ли уведомление по смс.
	 * @return ответ
	 */
	public static Document sendFinishUserRegistrationRq(String ouid, String login, String password, boolean notification) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishUserRegistrationRequestData(ouid, login, password, notification));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на завершение самостоятельной регистрации
	 * @param ouid идентификатор операции
	 * @param login логин
	 * @param password пароль
	 * @param notification необходимо ли уведомление по смс.
	 * @return ответ
	 */
	public static Document sendFinishUserSelfRegistrationRq(String ouid, String login, String password, boolean notification) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishUserSelfRegistrationRequestData(ouid, login, password, notification));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на начало восстановление пароля
	 * @param login логин
	 * @param confirmStrategyType способ подтверждения
	 * @return ответ
	 */
	public static Document sendStartRecoverPasswordRq(String login, ConfirmStrategyType confirmStrategyType) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartRestorePasswordRequestData(login, confirmStrategyType));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на завершение восстановление пароля
	 * @param ouid идентификатор операции
	 * @param password новый пароль
	 * @return ответ
	 */
	public static Document sendFinishRecoverPasswordRq(String ouid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishRestorePasswordRequestData(ouid, password));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на завершение восстановление гостевого пароля
	 * @param ouid идентификатор операции
	 * @param password новый пароль
	 * @return ответ
	 */
	public static Document sendFinishGuestRecoverPasswordRq(String ouid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishGuestRestorePasswordRequestData(ouid, password));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на подтверждение регистрации
	 * @param ouid идентификатор операции
	 * @param confirmationCode код подтверждения
	 * @return ответ
	 */
	public static Document sendConfirmOperationRq(String ouid, String confirmationCode) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ConfirmOperationRequestData(ouid, confirmationCode));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на подтверждение гостеовой операции
	 * @param ouid идентификатор операции
	 * @param confirmationCode код подтверждения
	 * @return ответ
	 */
	public static Document sendGuestConfirmOperationRq(String ouid, String confirmationCode) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ConfirmGuestOperationRequestData(ouid, confirmationCode));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на получение информации о только что залогиневшемся клиенте.
	 * @param ouid идентификатор операции
	 * @return ответ
	 */
	public static Document sendFinishCreateSessionRq(String ouid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateSessionRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на создание сессии
	 * @param login логин
	 * @param password пароль
	 * @return ответ
	 */
	public static Document sendStartCreateSessionRq(String login, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartCreateSessionRequestData(login, password));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на создание сессии для мобильного приложения
	 * @param data - данные для формирования запроса
	 * @return ответ
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendStartCreateMobileSessionRq(StartCreateMobileSessionRequestData data) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(data);
		return responseData.getBody();
	}

	/**
     * Послать запрос на создание сессии для социального приложения
     * @param guid идентификатор приложения
     * @param deviceState статус
     * @param deviceId идентификатор устройства
     * @param authorizedZoneType Тип зоны входа пользователя
     * @param pin пароль для доступа к полному функционалу (может быть null)
     * @return ответ
     * @throws BackException
     * @throws BackLogicException
     */
	public static Document sendStartCreateSocialSessionRq(String guid, String deviceState, String deviceId, AuthorizedZoneType authorizedZoneType, String pin) throws BackException, BackLogicException
    {
        ResponseData responseData = sender.sendRequest(new StartCreateSocialSessionRequestData(guid, deviceState, deviceId, authorizedZoneType, pin));
        return responseData.getBody();
    }

	/**
	 * Послать запрос на получение информации о только что залогинившемся через мАпи клиенте
	 * @param ouid токен аутентификации
	 * @return ответ
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendFinishCreateMobileSessionRq(String ouid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateMobileSessionRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на получение информации о только что залогинившемся через мАпи клиенте
	 * @param ouid токен аутентификации
	 * @return ответ
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendFinishCreateSocialSessionRq(String ouid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateSocialSessionRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на проверку сессии.
	 *
	 * @param sid идентификатор сессии.
	 * @return ответ.
	 */
	public static Document sendCheckSessionRq(String sid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new CheckSessionRequestData(sid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на закрытие сессии.
	 *
	 * @param sid идентификатор сессии.
	 * @return овтет.
	 */
	public static Document sendCloseSessionRq(String sid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new CloseSessionRequestData(sid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на смену пароля.
	 * @param sid идентификатор сессии.
	 * @param password пароль.
	 * @return ответ.
	 */
	public static Document sendChangePasswordRq(String sid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ChangePasswordRequestData(sid, password));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на смену логина.
	 * @param sid идентификатор сессии.
	 * @param login логин.
	 * @return ответ.
	 */
	public static Document sendChangeLoginRq(String sid, String login) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ChangeLoginRequestData(sid, login));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на проверку текущего пароля.
	 * @param sid идентификатор сессии.
	 * @param password пароль.
	 * @return ответ.
	 */
	public static Document sendCheckPasswordRq(String sid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new CheckPasswordRequestData(sid, password));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на проверку корректности данного логина в контексте операции
	 * @param ouid - идентификатор проводимой операции
	 * @param login - логин
	 * @return - ответ
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendPrepareCheckLoginByOperationRq(String ouid, String login) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new PrepareCheckLoginByOperationRequestData(ouid, login));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на проверку корректности данного пароля в контексте операции
	 * @param ouid - идентификатор проводимой операции
	 * @param password - логин
	 * @return - ответ
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendPrepareCheckPasswordByOperationRq(String ouid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new PrepareCheckPasswordByOperationRequestData(ouid, password));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на генерацию нового пароля.
	 * @param login логин.
	 * @param employeeInfo - информация о сотруднике, выполняющем запрос. Если восстановление из клиентской части, этот параметр null.
	 * @return ответ.
	 */
	public static Document sendGeneratePasswordRq(String login, EmployeeInfo employeeInfo, boolean ignoreImsiCheck) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GeneratePasswordRequestData(login, employeeInfo, ignoreImsiCheck));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на генерацию нового пароля по данным из профиля клиента.
	 * @param userInfo информация о клиент.
	 * @param employeeInfo - информация о сотруднике, выполняющем запрос
	 * @return ответ.
	 */
	public static Document sendGeneratePassword2Rq(Map<String, String> userInfo, EmployeeInfo employeeInfo, boolean ignoreIMSICheck) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GeneratePassword2RequestData(userInfo, employeeInfo, ignoreIMSICheck));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на начало регистрации мобильного приложения
	 * @param login параметры запроса
	 * @param deviceInfo информация об устройстве
	 * @param confirmStrategyType способ подтверждения
	 * @param registrationIPasAvailable доступность регистрации по логину iPas
	 * @param deviceId уникальный идентификатор устройства
	 * @return ответ ЦСА
	 */
	public static Document sendStartMobileRegistrationRq(String login, String deviceInfo, ConfirmStrategyType confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartMobileRegistrationRequestData(login, deviceInfo, confirmStrategyType, registrationIPasAvailable, deviceId, appName));
		return responseData.getBody();
	}

	/**
     * Послать запрос на начало регистрации мобильного приложения
     * @param login параметры запроса
     * @param deviceInfo информация об устройстве
     * @param confirmStrategyType способ подтверждения
     * @param registrationIPasAvailable доступность регистрации по логину iPas
     * @param deviceId уникальный идентификатор устройства
     * @return ответ ЦСА
	 */
	public static Document sendStartMobileRegistrationRq(String login, String deviceInfo, ConfirmStrategyType confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String card, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartMobileRegistrationRequestData(login, deviceInfo, confirmStrategyType, registrationIPasAvailable, deviceId, card, appName));
		return responseData.getBody();
	}

	/**
     * Послать запрос на начало регистрации социального приложения
     * @param login параметры запроса
     * @param deviceInfo информация об устройстве
     * @param confirmStrategyType способ подтверждения
     * @param registrationIPasAvailable доступность регистрации по логину iPas
     * @param deviceId уникальный идентификатор клиента
     * @return ответ ЦСА
	 */
	public static Document sendStartSocialRegistrationRq(String login, String deviceInfo, ConfirmStrategyType confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String card, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartSocialRegistrationRequestData(login, deviceInfo, confirmStrategyType, registrationIPasAvailable, deviceId, card, appName));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на окончание регистрации мобильного приложения
	 * @param ouid идентификатор операции
	 * @param password пароль
	 * @param deviceState состояние устройства
	 * @param deviceId уникальный идентификатор устройства
	 * @return ответ ЦСА
	 */
	public static Document sendFinishMobileRegistrationRq(String ouid, String password, String deviceState, String deviceId, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishMobileRegistrationRequestData(ouid, password, deviceState, deviceId, appName));
		return responseData.getBody();
	}

	/**
     * Послать запрос на окончание регистрации социального приложения
     * @param ouid идентификатор операции
     * @param password пароль
     * @param deviceState состояние устройства
     * @param deviceId уникальный идентификатор клиента
     * @return ответ ЦСА
     */
	public static Document sendFinishSocialRegistrationRq(String ouid, String password, String deviceState, String deviceId, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishSocialRegistrationRequestData(ouid, password, deviceState, deviceId, appName));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на отмену регистрации мобильного приложения
	 * @param guid гуид
	 * @return ответ ЦСА
	 */
	public static Document sendCancelMobileRegistrationRq(String guid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new CancelMobileRegistrationRequestData(guid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на отмену регистрации социального приложения
	 * @param guid гуид
	 * @return ответ ЦСА
	 */
	public static Document sendCancelSocialRegistrationRq(String guid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new CancelSocialRegistrationRequestData(guid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на валидащию нового пароля
	 * @param sid идентификатор сессии
	 * @param password пароль
	 * @return ответ ЦСА
	 */
	public static Document sendValidatePasswordRq(String sid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ValidatePasswordRequestData(sid, password));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на валидащию нового логина с проверкой, что у пользователя уже может быть такой логин.
	 * @param validateLoginInfo информация для валидации логина
	 * @return ответ ЦСА
	 */
	public static Document sendValidateLoginRq(ValidateLoginInfo validateLoginInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ValidateLoginRequestData(validateLoginInfo));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на актуализацию информации о входе
	 * @param ouid идентификатор операции
	 * @param guid идентификатор коннектора
	 * @return ответ ЦСА
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendActualizeLogonInfoRq(String ouid, String guid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ActualizeLogonInfoRequestData(ouid, guid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на инициацию подтверждения операции
	 * @param authToken токен аутентификации
	 * @param clientExternalId идентификатор клиента во внешней системе
	 * @param confirmType тип подтверждения
	 * @param cardNumber номер карты подтверждения
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendInitializeVerifyBusinessEnvironmentRq(String authToken, String clientExternalId, ConfirmStrategyType confirmType, String cardNumber) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new InitializeVerifyBusinessEnvironmentRequestData(authToken, clientExternalId, confirmType, cardNumber));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на верификацию данных в деловой среде
	 * @param ouid идентификатор операции
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendVerifyBusinessEnvironmentRq(String ouid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new VerifyBusinessEnvironmentRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на получение данных о способах подтверждения операций клиентом
	 * @param authToken токен аутентификации
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetConfirmationInfoRq(String authToken) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetConfirmationInfoRequestData(authToken));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на изменение признака подключения push-уведомлений
	 * @param guid - идентификатор коннектора
	 * @param deviceId - идентификатор устройства
	 * @param pushSupported - признак подключения push-уведомлений
	 * @param securityToken - Строка токена безопасности, сформированная мобильным приложением
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendChangePushSupportedRq(String guid, String deviceId, boolean pushSupported, String securityToken) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new SetPushSupportedRequestData(guid, deviceId, pushSupported, securityToken));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на создание сессии для АТМ
	 * @param cardNumber номер карты
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendStartCreateATMSessionRq(String cardNumber) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new StartCreateATMSessionRequestData(cardNumber));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на получение информации о только что залогинившемся пользователе
	 * @param ouid токен аутентификации
	 * @return ответ ЦСА
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendFinishCreateATMSessionRq(String ouid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateATMSessionRequestData(ouid));
		return  responseData.getBody();
	}

	/**
	 * Послать запрос обновление регистраций телефонов (добавление, удаление, смена активного телефона)
	 * @param phoneNumber номер активного телефона
	 * @param userInfo информация о пользователе
	 * @param addPhones добавляемые телефоны
     * @param removePhones удаляемые телефоны
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendUpdatePhoneRegistrationsRq(String phoneNumber, UserInfo userInfo, List<String> addPhones, List<String> removePhones) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UpdatePhoneRegistrationsRequestData(phoneNumber, userInfo, addPhones, removePhones));
		return responseData.getBody();
	}

	/**
	 * !!! Использовать метод только в местах, где клиент подтвердил, что добавляемые телефоны,
	 * для которых найдены дубликаты у других пользователей, принадлежат ему !!!
	 *
	 * Послать запрос обновление регистраций телефонов (добавление, удаление, смена активного телефона), удаляющий дубликаты
	 * @param phoneNumber номер активного телефона
	 * @param userInfo информация о пользователе
	 * @param addPhones добавляемые телефоны
     * @param removePhones удаляемые телефоны
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendUpdatePhoneRegRemoveDuplicateRq(String phoneNumber, UserInfo userInfo, List<String> addPhones, List<String> removePhones) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UpdatePhoneRegRemoveDuplicateRequestData(phoneNumber, userInfo, addPhones, removePhones));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на получение информации о блоке пользователя по телефону
	 * @param phoneNumber номер телефона
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendFindProfileNodeByPhoneRq(String phoneNumber) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileNodeByPhoneRequestData(phoneNumber));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на получение информации о блоке пользователя по ФИО ДУЛ ДР ТБ
	 * @param userInfo информация о пользователе
	 * @param needCreateProfile необходимо ли создавать профиль клиента.
	 * @param createProfileNodeMode признак привязки профиля к блоку.
	 * @return ответ ЦСА
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendFindProfileNodeByUserInfoRq(UserInfo userInfo, boolean needCreateProfile, CreateProfileNodeMode createProfileNodeMode) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileNodeByUserInfoRequestData(userInfo, needCreateProfile, createProfileNodeMode));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на обновление профиля ЦСА
	 * @param newUserInfo новая информация о пользователе
	 * @param oldUserInfo старая информация о пользователе
	 * @return ответ ЦСА
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendUpdateProfileRq(UserInfo newUserInfo, UserInfo oldUserInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new UpdateProfileRequestData(newUserInfo, oldUserInfo));
		return responseData.getBody();
	}

	/**
	 * Получить информацию о блоках
	 * @return - документ с информацией о блоках системы
	 */
	public static Document sendGetNodesInfoRq() throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetNodesInfoRequestData());
		return responseData.getBody();
	}

	/**
	 * Получить информацию о пользователе по номеру телефона
	 * В случае, если ЕРМБ коннекторы по телефону не будут найдены и выставлен признак "использовать карту",
	 * будет найдена в МБ карта по телефону и информация по клиенту построится по первому найденному по данной карте коннектору
	 * Важно! Если ЦСА не найдёт пользователя, метод бросит "ProfileNotFoundException: Операция временно недоступна, повторите попытку позже."
	 * @param phoneNumber номер телефона
	 * @param usingCardByPhone использовать ли карту, найденную по телефону в МБ для получения информации
	 * @return ответ ЦСА
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGetUserInfoByPhoneRq(String phoneNumber, boolean usingCardByPhone) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetUserInfoByPhoneRequestData(phoneNumber, usingCardByPhone));
		return responseData.getBody();
	}

	/**
	 * Получить информацию о пользователе по deviceId и deviceInfo.
	 * @param deviceId - идентификатор клиента во внешнем приложении
	 * @param deviceInfo - идентификатор платформы, в рамках которой поступил запрос на перевод
	 * @return ответ ЦСА
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGetUserInfoByDeviceIdAndInfoRq(String deviceId, String deviceInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetUserInfoByDeviceIdAndInfoRequestData(deviceId, deviceInfo));
		return responseData.getBody();
	}

	/**
	 * Получить информацию о пользователе по номеру телефона с обращением в МБК
	 * @param phoneNumber номер телефона
	 * @return ответ ЦСА
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGetUserInfoByPhoneWithMBRq(String phoneNumber) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetUserInfoByPhoneWithMBRequestData(phoneNumber));
		return responseData.getBody();
	}

	/**
     * Послать запрос на изменение настройки приватности клиента
     * @param sid - идентификатор сессии
     * @param incognito - значение параметра приватности
     * @return ответ
     * @throws BackLogicException
     * @throws BackException
     */
    public static Document sendChangeIncognitoSettingRq(String sid, boolean incognito) throws BackLogicException, BackException
    {
        ResponseData responseData = sender.sendRequest(new ChangeIncognitoSettingData(sid, incognito));
        return responseData.getBody();
	}

	/**
     * Послать запрос на изменение настройки приватности клиента
     * @param userInfo - клиент
     * @param incognito - значение параметра приватности
     * @return ответ
     * @throws BackLogicException
     * @throws BackException
     */
    public static Document sendChangeIncognitoSettingRq(UserInfo userInfo, IncognitoState incognito) throws BackLogicException, BackException
    {
        ResponseData responseData = sender.sendRequest(new ChangeIncognitoSettingData(userInfo, incognito));
        return responseData.getBody();
	}

	/**
	 * поиск инфы по клиентам
	 * @param fio фио клиента
	 * @param document ДУЛ клиента
	 * @param birthday ДР клиента
	 * @param login логин клиента
	 * @param creationType тип договора
	 * @param agreementNumber номер договора
	 * @param phoneNumber номер телефона
	 * @param tbList список ТБ в которых нужно искать
	 * @param maxResults максимальное количество клиентов
	 * @param firstResult смещение выборки
	 * @return ответ
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document getClientsInformationRq(String fio, String document, Calendar birthday, String login,
	                                               CreationType creationType, String agreementNumber, String phoneNumber,
	                                               List<String> tbList, int maxResults, int firstResult) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientsInformationRequestData(fio, document, birthday, login, creationType, agreementNumber, phoneNumber, tbList, maxResults, firstResult));
		return responseData.getBody();
	}

	/**
	 * поиск инфы по клиентам, ожидающим миграции
	 * @param fio фио клиента
	 * @param document ДУЛ клиента
	 * @param birthday ДР клиента
	 * @param creationType тип договора
	 * @param agreementNumber номер договора
	 * @param tbList список ТБ в которых нужно искать
	 * @param nodeId идентификатор основного блока
	 * @param maxResults максимальное количество клиентов
	 * @param firstResult смещение выборки
	 * @return ответ
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document getTemporaryNodeClientsInformationRq(String fio, String document, Calendar birthday,
	                                               CreationType creationType, String agreementNumber,
	                                               List<String> tbList, Long nodeId, int maxResults, int firstResult) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetTemporaryNodeClientsInformationRequestData(fio, document, birthday, creationType, agreementNumber, tbList, nodeId, maxResults, firstResult));
		return responseData.getBody();
	}

	/**
	 * количество клиентов в резервном блоке
	 * @param nodeId идентификатор основного блока
	 * @return ответ
	 */
	public static Document getTemporaryNodeClientsCountRq(Long nodeId) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetTemporaryNodeClientsCountRequestData(nodeId));
		return responseData.getBody();
	}

	/**
	 * Состояние клиента
	 * @param userInfo информация о клиенте
	 * @return ответ
	 */
	public static Document getClientNodeStateRq(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientNodeStateRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * Обновление профиля данными из мдм
	 * @param profileId идентификатор профиля
	 * @param mdmId идентификатор в мдм
	 * @return ответ
	 */
	public static Document updateClientMDMInfoRq(Long profileId, String mdmId) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UpdateClientMDMInfoRequestData(profileId, mdmId));
		return responseData.getBody();
	}

	/**
	 * Получениe идентификатора профиля по ФИО+ДУЛ+ДР+ТБ
	 * @param info информация о клиенте
	 * @return ответ
	 */
	public static Document getClientProfileIdRequestDataRq(UserInfo info) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientProfileIdRequestData(info));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на получение истории изменения данных пользователя.
	 * @param userInfo данные пользователя.
	 * @return история изменений.
	 */
	public static Document sendGetProfileHistoryInfo(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responceData = sender.sendRequest(new GetProfileHistoryInfo(userInfo));
		return responceData.getBody();
	}
	/**
	 * обновление доп. параметров профиля
	 * @param userInfo идентификационные данные клиента
	 * @param creationType тип договора
	 * @param agreementNumber номер договора
	 * @param phone телефон
	 * @return ответ
	 */
	public static Document sendUpdateProfileAdditionalDataRq(UserInfo userInfo, CreationType creationType, String agreementNumber, String phone) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UpdateProfileAdditionalDataRequestData(userInfo, creationType, agreementNumber, phone));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на получение списка блоков по списку обновленных профилей
	 * @param updateProfileInfoList список обновленных профилей
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendFindNodesByUpdatedProfilesRq(List<UpdateProfileInfo> updateProfileInfoList) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindNodesByUpdatedProfilesRequestData(updateProfileInfoList));
		return responseData.getBody();
	}

	/**
	 * Поиск информации о пользователе
	 * @param firstName - имя
	 * @param surName - фамилия
	 * @param patrName - отчество
	 * @param passport - данные паспорта
	 * @param birthdate - дата рождения
	 * @param tb - тербанк
	 * @return ответ
	 * @throws BackLogicException
	 * @throws BackException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document findProfileInformationRq(String surName, String firstName, String patrName, String passport,
	                                                Calendar birthdate, String tb) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileInformationRequestData(surName, firstName, patrName, passport, birthdate, tb));
		return responseData.getBody();
	}

	/**
	 * Поиск информации о пользователе с полной информацией об узлах в которых работал клиент
	 * @param firstName - имя
	 * @param surName - фамилия
	 * @param patrName - отчество
	 * @param passport - данные паспорта
	 * @param birthdate - дата рождения
	 * @param tb - тербанк
	 * @return профиль с историей и информацией об узлах в которых работал клиент
	 * @throws BackLogicException
	 * @throws BackException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document findProfileInformationWithNodeInfoRq(String surName, String firstName, String patrName, String passport,
	                                                            Calendar birthdate, String tb) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileInformationWithNodeInfoRequestData(surName, firstName, patrName, passport, birthdate, tb));
		return responseData.getBody();
	}

	/**
	 * Создать профиль клиента в ЦСА.
	 * @param firstName - имя
	 * @param surName - фамилия
	 * @param patrName - отчество
	 * @param passport - данные паспорта
	 * @param birthdate - дата рождения
	 * @param tb - тербанк
	 * @return Созданный профиль клиента с привязанным блоком.
	 *         Если профиль с такими данными был в ЦСа, то новый профиль не создаем - возвращаем существующий.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document createProfileRq(String surName, String firstName, String patrName, String passport,
	                                                Calendar birthdate, String tb) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new CreateProfileRequestData(surName, firstName, patrName, passport, birthdate, tb));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на блокировку клиента
	 * @param userInfo информация о блокируемом клиенте
	 * @param lockFrom начало блокировки
	 * @param lockTo окончание блокировки
	 * @param reason причина блокировки
	 * @param blockerFIO фио блокирующего сотрудника
	 * @return ответ
	 */
	public static Document sendLockProfileRq(UserInfo userInfo, Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LockProfileRequestData(userInfo, lockFrom, lockTo, reason, blockerFIO));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на снятие блокировки клиента
	 * @param userInfo информация о разблокируемом клиенте
	 * @return ответ
	 */
	public static Document sendUnlockProfileRq(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UnlockProfileRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на "блокировку" клиента реализованную в рамках запроса CHG071536.
	 * По факту всего лишь добавим информацию в профиль.
	 * @param userInfo информация о блокируемом клиенте
	 * @param lockFrom дата начала блокировки
	 * @param lockTo дата окончания блокировки
	 * @param reason причина блокировки
	 * @param blockerFIO фио блокирующего сотрудника
	 * @return ответ
	 */
	public static Document sendLockProfileCHG071536Rq(UserInfo userInfo, Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LockProfileCHG071536RequestData(userInfo, lockFrom, lockTo, reason, blockerFIO));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на "блокировку" клиента реализованную в рамках запроса CHG071536.
	 * По факту всего лишь добавим информацию в профиль.
	 * @param profileId идентификатор профиля клиента
	 * @param lockFrom дата начала блокировки
	 * @param lockTo дата окончания блокировки
	 * @param reason причина блокировки
	 * @param blockerFIO фио блокирующего сотрудника
	 * @return ответ
	 */
	public static Document sendLockProfileCHG071536Rq(Long profileId, Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LockProfileCHG071536ByProfileIdRequestData(profileId, lockFrom, lockTo, reason, blockerFIO));
		return responseData.getBody();
	}

	/**
	 * Послать запрос
	 * @param cardNum номер карты
	 * @param sendSMS признак отправки СМС
	 * @return ответ
	 */
	public static Document sendUserRegistrationDisposableRq(String cardNum, String sendSMS) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UserRegistrationDisposableRequestData(cardNum, sendSMS));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на присвоение профилю низкого уровня безопасности
	 * @param history история изменений данных клиента
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendLowerProfileSecurityTypeRq(List<UserInfo> history) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LowerProfileSecurityTypeRequestData(history));
		return responseData.getBody();
	}

	/**
	 * Получить из ЦСА признак инкогнито.
	 * @param sid идентифкатор сессии
	 * @return ответ ЦСА.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetIncognitoSettingRq(String sid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetIncognitoSettingData(sid));
		return responseData.getBody();
	}

	/**
	 * Получить из ЦСА признак инкогнито.
	 * @param userInfo информация о клиенте
	 * @return ответ ЦСА.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetIncognitoSettingRq(UserInfo userInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetIncognitoSettingData(userInfo));
		return responseData.getBody();
	}

	/**
	 * Получить информацию о коннекторах ЕРМБ
	 * @param userInfo информация о клиенте
	 * @return ответ ЦСА.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document getErmbConnectorInfoRq(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetErmbConnectorInfoRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * Найти ЕРМБ-телефонов
	 *
	 * @param surName - фамилия
	 * @param firstName - имя
	 * @param patrName - отчество
	 * @param passports список дул
	 * @param birthdate - дата рождения
	 * @return список уникальных номеров телефонов или пустой список
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Collection<String> findErmbPhones(String surName, String firstName, String patrName, Collection<String> passports, Calendar birthdate) throws BackLogicException, BackException
	{
		Cache cache = CacheProvider.getCache("csa-ermb-phones-cache");
		String[] strings = new String[]{surName, firstName, patrName, passports.toString(), birthdate.toString()};
		String cacheKey = StringUtils.join(strings);
		Element cacheValue = null;
		if (cache != null)
			cacheValue = cache.get(cacheKey);
		if (cacheValue != null)
			//noinspection unchecked
			return new ArrayList<String>((Collection<String>) cacheValue.getObjectValue());

		ResponseData responseData = sender.sendRequest(new GetErmbPhoneListRequestData(surName, firstName, patrName, birthdate, passports));
		Document response = responseData.getBody();
		String[] phoneNumbers = StringUtils.split(XmlHelper.getSimpleElementValue(response.getDocumentElement(), CSAResponseConstants.PHONES_TAG), ',');
		if (phoneNumbers == null)
			return Collections.emptyList();
		else
		{
			Collection<String> result = Arrays.asList(phoneNumbers);
			cache.put(new Element(cacheKey, result));
			return result;
		}
	}

	/**
	 * Получить информацию о коннекторах клиента
	 * @param sid идентифкатор сессии
	 * @return ответ ЦСА.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetClientConnectorsRq(String sid, ConnectorInfo.Type type) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientConnectorsData(sid, type));
		return responseData.getBody();
	}

	/**
	 * Получить информацию о коннекторах клиента
	 *
	 * @param userInfo информация о клиенте
	 * @param type
	 * @return ответ ЦСА.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetClientConnectorsRq(UserInfo userInfo, ConnectorInfo.Type type) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientConnectorsData(userInfo, type));
		return responseData.getBody();
	}

	/**
	 * Получить список карт по данным профиля
	 * @param surName - фамилия профиля
	 * @param firstName - имя профиля
	 * @param patrName - отчество профиля
	 * @param passport - ДУЛ
	 * @param birthdate - ДР
	 * @param tb - ТБ
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document findProfileCardNumberListRq(String surName, String firstName, String patrName, String passport,
		                                                Calendar birthdate, String tb) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileCardNumberListRequestData(surName, firstName, patrName, passport, birthdate, tb));
		return responseData.getBody();
	}

	/**
	 * Изменение информации о состоянии блоков
	 * @param changedNodesAvailabilityInfo новая информация
	 * @return информация о состоянии блоков
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document changeNodesAvailabilityInfo(Collection<com.rssl.phizic.gate.csa.NodeInfo> changedNodesAvailabilityInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new ChangeNodesAvailabilityInfoRequestData(changedNodesAvailabilityInfo));
		return responseData.getBody();
	}
/**
	 * Список номеро ЕРМБ по номерам телефенов.
	 *
	 * @param srcPhones исходный список.
	 * @return список номеров ЕРМБ.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static List<String> getRegisteredPhones(Collection<String> srcPhones) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new HasUserByPhoneRequestData(srcPhones));
		Document document = responseData.getBody();
		org.w3c.dom.Element element = document.getDocumentElement();
        String phoneNumbers[] = XmlHelper.getSimpleElementValue(element, RequestConstants.PHONES_TAG).split("\\|");
		if (phoneNumbers.length == 0 || StringHelper.isEmpty(phoneNumbers[0]))
			return Collections.EMPTY_LIST;
		return Arrays.asList(phoneNumbers);
	}

	/**
	 * поставить лок на профиль для исполнения документа сотрудником
	 * @param userInfo информация о клиенте
	 * @return поставился ли лок
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendLockProfileForExecuteDocument(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LockProfileForExecuteDocumentRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * снять лок с профиля после исполнения документа сотрудником
	 * @param userInfo информация о клиенте
	 * @return снялся ли лок
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendUnlockProfileForExecuteDocument(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UnlockProfileForExecuteDocumentRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * Поставить технологический перерыв для МБК в ЦСА (новый или обновить)
	 * Нужен только для МБК!
	 * @param technoBreak технологический перерыв
	 * @return ответ ЦСА
	 */
	public static Document saveOrUpdateMbkTechnoBreak(TechnoBreak technoBreak) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new MbkTechnoBreakSaveOrUpdateRequestData(technoBreak));
		return responseData.getBody();
	}

	/**
	 * Получить информацию о получателе запроса на сбор средств
	 * @param userInfo информация о получателе
	 * @return ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendFindFundSenderInfo(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindFundSenderInfoRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на инициализацию гостевого входа по телефонному номеру
	 * @param phoneNumber - номер телефона
	 * @return - ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGuestEntryInitialRq(String phoneNumber) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GuestEntryInitialRequestData(phoneNumber));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на подтверждение смс-пароля для аутентификации в гостевом входе
	 * @param password - пароль
	 * @return - ответ ЦСА
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGuestEntryConfirmationRq(String ouid, String password) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GuestEntryConfirmationRequestData(ouid, password));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на завершение аутентификации
	 * @param ouid идентификатор операции входа
	 * @return данные аутентификации
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendFinishCreateGuestSessionRq(String ouid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateGuestSessionRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на проверку IMSI.
	 * @param login логин.
	 * @param employeeInfo - информация о сотруднике, выполняющем запрос. Если восстановление из клиентской части, этот параметр null.
	 * @return ответ.
	 */
	public static Document sendCheckIMSIRq(String login, EmployeeInfo employeeInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new CheckIMSIRequestData(login, employeeInfo));
		return responseData.getBody();
	}

	/**
	 * Послать запрос на регистрацию гостя
	 * @param phone номер телефона входа
	 * @param code код гостя
	 * @param login логин
	 * @param password пароль
	 * @param nodeNumber номер блока
	 * @param userInfo информация о госте
	 * @return тело ответа
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGuestRegistrationRq(String phone, Long code, String login, String password, Long nodeNumber, UserInfo userInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GuestRegistrationRequestData(phone, code, login, password, nodeNumber, userInfo));
		return responseData.getBody();
	}

	/**
	 * получение информации о наличии мАпи ПРо-версии у клиента
	 * @param userInfo информация о клиенте
	 * @return ответ ЦСА
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGetContainsProMAPIInfoRq(UserInfo userInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetContainsProMAPIInfoRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * Получение дополнительной информации по гостю:
	 *  <ol>
	 *     <li>Подключён ли телефон к МБ</li>
	 *     <li>Есть ли в ЦСА основная учётная запись</li>
	 * </ol>
	 * @param phone Номер телефона гостя
	 * @return Ответ от ЦСА
	 */
	public static Document getAdditionInformationForGuest(String phone) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetAdditionInformationForGuestRequestData(phone));
		return responseData.getBody();
	}
}
