package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.*;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.RequestProcessor;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.UserLogonType;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.operations.ActualizationLogonInfoOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserLogonOperation;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.StandInExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.logging.Log;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.util.Calendar;
import java.util.Map;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class RequestProcessorBase implements RequestProcessor
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	/**
	 * @return тип ответа
	 */
	protected abstract String getResponceType();

	/**
	 * @return тип запроса
	 */
	protected abstract String getRequestType();

	public final ResponseInfo process(RequestInfo requestInfo) throws Exception
	{
		Config config = ConfigFactory.getConfig(Config.class);
		if(config.isStandInMode() && !isAccessStandIn())
		{
			error("Недоступность обрабоки запроса " + getRequestType() + " в режиме StandIn ЦСА");
			return getFailureResponseBuilder().buildCSAStandInResponse("Операция временно недоступна.");
		}

		try
		{
			return processRequest(requestInfo);
		}
		//Глобальная обработка исключений, от которых не зависит логика бека и требуется безусловный отказ
		//Например, ошибки мобильного банка, или технологичесике перерывы.
		catch (MobileBankRegistrationNotFoundException e)
		{
			error("ошибка обработки запроса ", e);
			return getFailureResponseBuilder().buildMobileBankRegistrationNotFoundRequestResponse();
		}
		catch (GateTimeOutException e)
		{
			error("ошибка обработки запроса ", e);
			return getFailureResponseBuilder().buildGateTimeoutResponse();
		}
		catch (MobileBankSendSmsMessageException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildMobileBankSendSmsMessageRequestResponse(e.getErrorPhones());
		}
		catch (IllegalOperationStateException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildBadOUIDResponse();
		}
		catch (AuthenticationFailedException e)
		{
			error("ошибка аутентификации", e);
			return getFailureResponseBuilder().buildAuthenticationFailedResponse(e.getConnector());
		}
		catch (GuestAuthenticationFailedException e)
		{
			error("ошибка аутентификации гостя", e);
			return getFailureResponseBuilder().buildAuthenticationFailedResponse(e.getProfile());
		}
		catch (LoginAlreadyRegisteredException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildLoginAlreadyRegisteredResponse();
		}
		catch (LoginRestrictionException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildBadLoginResponse(e.getMessage());
		}
		catch (PasswordRestrictionException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildBadPasswordResponse(e.getMessage());
		}
		catch (UserAlreadyEnteredRegisterException e)
		{
			warn("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildUserAlreadyEnteredResponse();
		}
		catch (UserAlreadyRegisteredException e)
		{
			warn("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildUserAlreadyRegisteredResponse();
		}
		catch (OperationNotFoundException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildBadOUIDResponse();
		}
		catch (AuthentificationReguiredException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildAuthentificationReguiredResponse();
		}
		catch (BlockingRuleActiveException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildBlockingRuleActiveResponse(e.getMessage(), e.getDate());
		}
		catch (ProfileLockedException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildProfileLockedResponse(e.getMessage());
		}
		catch (NotMainCardRegistrationException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildNotMainCardRegistrationResponse(e.getMessage());
		}
		catch (InactiveCardRegistrationException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildInactiveCardRegistrationResponse(e.getMessage());
		}
		catch (DenyCbCodeOperationException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildDenyCbCodeOperationResponse(e.getMessage());
		}
		catch (RestrictionException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildRestrictionViolatedResponse(e.getMessage());
		}
		catch (IdentificationFailedException e)
		{
			warn("ошибка идентификации пользователя", e);
			return getFailureResponseBuilder().buildIdentificationFailedResponse(e);
		}
		catch (PhoneRegistrationNotActiveException e)
		{
			warn("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildActivePhoneERMBRequiredResponse();
		}
		catch (DuplicatePhoneRegistrationsException e)
		{
			warn("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildDuplicatePhoneRegistrations(e.getMessage());
		}
		catch (ServiceUnavailableException e)
		{
			warn("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildServiceUnavailableResponse(e.getMessage());
		}
		catch (ActivatePhoneRegistrationException e)
		{
			warn("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildActivatePhoneRegistrationErrorResponse(e.getMessage());
		}
		catch (InvalidSessionException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildBadSIDResponse();
		}
		catch (StandInExternalSystemException e)
		{
			error("Stand-In внешней системы", e);
			return getFailureResponseBuilder().buildStandInResponse(e.getMessage());
		}
		catch (InactiveExternalSystemException e)
		{
			error("технологический перерыв", e);
			return getFailureResponseBuilder().buildTechnoBreakResponse(e.getMessage());
		}
		catch (IMSICheckException e)
		{
			error("IMSI не совпал", e);
			return getFailureResponseBuilder().buildIMSICheckResponse(e.getMessage(), e.getErrorPhones());
		}
		catch (UnsupportedStandInCSAProcessException e)
		{
			error("Недоступность операции в режиме StandIn ЦСА", e);
			return getFailureResponseBuilder().buildCSAStandInResponse(e.getMessage());
		}
		catch (BlockClientOperationFraudException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildFraudBlockProfileResponse();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			error("ошибка обработки запроса", e);
			return getFailureResponseBuilder().buildFraudProhibitionOperationResponse();
		}
	}

	public boolean isAccessStandIn()
	{
		return false;
	}

	/**
	 * Обработать запрос и вернуть результат
	 * @param requestInfo информация о запросе
	 * @return информация об ответе
	 */
	protected abstract ResponseInfo processRequest(RequestInfo requestInfo) throws Exception;

	/**
	 * Получить постоитель ответа-отказа
	 * @return постоитель ответа-отказа
	 * @throws SAXException
	 */
	protected FailureResponseHelper getFailureResponseBuilder() throws SAXException
	{
		return new FailureResponseHelper(getResponceType());
	}

	/**
	 * Получить постоитель успешного ответа
	 * @return постоитель успешного ответа
	 * @throws SAXException
	 */
	protected ResponseBuilderHelper getSuccessResponseBuilder() throws SAXException
	{
		return new ResponseBuilderHelper(getResponceType());
	}

	/**
	 * Получить пустой успешный ответ
	 * @return пустой успешный ответ
	 * @throws SAXException
	 */
	protected ResponseInfo buildSuccessResponse() throws SAXException
	{
		return getSuccessResponseBuilder().end().getResponceInfo();
	}

	/**
	 * Создать и сохранить операцию входа для указанного коннектора.
	 * При этом происходит проверка наличия уже активной сессии по коннетору.
	 * @param identificationContext контекст идентифкации клиента
	 * @param connector коннектор
	 * @return сохраненная заявка на вход.
	 */
	protected UserLogonOperation createLogonOperation(IdentificationContext identificationContext, Connector connector, AuthorizedZoneType authorizedZoneType, Map<String, String> parameters, String mobileSDKData) throws Exception
	{
		UserLogonOperation operation = new UserLogonOperation(identificationContext);
		operation.initialize(connector.getGuid(), connector.getType(), authorizedZoneType, parameters, mobileSDKData);
		return operation;
	}

	/**
	 * Выполнить подготовительные действия для входа(многошаговый вход) в Апи.
	 * @param identificationContext контекст идентифкации
	 * @param connector коннектор аутентификации
	 * @return информация об ответе
	 * @throws Exception
	 */
	protected ResponseInfo prepareApiLogon(IdentificationContext identificationContext, Connector connector) throws Exception
	{
		return prepareLogonBase(identificationContext, connector, null, null);
	}

	/**
	 * Выполнить подготовительные действия для входа(многошаговый вход) в мАпи.
	 * @param identificationContext контекст идентифкации
	 * @param connector коннектор аутентификации
	 * @return информация об ответе
	 * @throws Exception
	 */
	protected ResponseInfo prepareMAPILogon(IdentificationContext identificationContext, Connector connector, AuthorizedZoneType authorizedZoneType, String mobileSDKData) throws Exception
	{
		return prepareLogonBase(identificationContext, connector, authorizedZoneType, mobileSDKData);
	}

	/**
	 * Выполнить подготовительные действия для входа(многошаговый вход).
	 * @param identificationContext контекст идентифкации
	 * @param connector коннектор аутентификации
	 * @return информация об ответе
	 * @throws Exception
	 */
	protected ResponseInfo prepareLogon(IdentificationContext identificationContext, Connector connector) throws Exception
	{
		return prepareLogon(identificationContext, connector, null);
	}

	protected ResponseInfo prepareLogon(IdentificationContext identificationContext, Connector connector, Map<String, String> parameters) throws Exception
	{
		try
		{
			return prepareLogonBase(identificationContext, connector, null, parameters, null);
		}
		catch (TooManyCSAConnectorsException e)
		{
			error("ошибка создания операции входа клиента.", e);
			//начинаем извращаться: создавать заявки на актуализацию инфы о входе(выбор логина).
			trace("создаем заявку на актуализацию информации входе");
			ActualizationLogonInfoOperation operation = new ActualizationLogonInfoOperation(identificationContext);
			operation.initialize(e.getConnectors(), connector.getGuid());
			trace("возвращаем ответ ошибку требуется актуализация информации о входе");
			return getFailureResponseBuilder().buildActualizationLogonInfoRequiredResponse(operation);
		}
	}

	private ResponseInfo prepareLogonBase(IdentificationContext identificationContext, Connector connector, AuthorizedZoneType authorizedZoneType, String mobileSDKData) throws Exception
	{
		return prepareLogonBase(identificationContext, connector, authorizedZoneType, null, mobileSDKData);
	}

	private ResponseInfo prepareLogonBase(IdentificationContext identificationContext, Connector connector, AuthorizedZoneType authorizedZoneType, Map<String, String> parameters, String mobileSDKData) throws Exception
	{
		trace("создаем заявку на вход");
		UserLogonOperation logonOperation = createLogonOperation(identificationContext, connector, authorizedZoneType, parameters, mobileSDKData);
		trace("возвращаем удачный ответ");
		return getSuccessResponseBuilder()
				.addOUID(logonOperation)
				.addNodeInfo(Utils.getActiveProfileNode(logonOperation.getProfileId(), CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES))
				.addUserInfo(connector.getProfile())
				.addProfileId(connector.getProfile())
				.addConnectorInfo(connector)
				.addUserLogonType(UserLogonType.CLIENT)
				.end().getResponceInfo();
	}

	protected void trace(String message)
	{
		log.trace(getResponceType() + ": " + message);
	}

	protected void trace(String message, Throwable e)
	{
		log.trace(getResponceType() + ": " + message, e);
	}

	protected void error(String message)
	{
		log.error(getResponceType() + ": " + message);
	}

	protected void error(String message, Throwable e)
	{
		log.error(getResponceType() + ": " + message, e);
	}

	protected void warn(String message, Throwable e)
	{
		log.warn(getResponceType() + ": " + message, e);
	}

	protected void warn(String message)
	{
		log.warn(getResponceType() + ": " + message);
	}

	protected void info(String message)
	{
		log.info(getResponceType() + ": " + message);
	}

	/**
	 * Постоить из запроса шаблон профиля
	 *      firstname               Имя пользователя                                            [1]
	 *      patrname                Отчество пользователя                                       [0..1]
	 *      surname                 Фамилия пользователя                                        [1]
	 *      birthdate               Дата рождения пользователя                                  [1]
	 *      passport                ДУЛ пользователя                                            [1]
	 *      tb                      ТБ пользователя                                             [1]
	 * @param element - элемент запроса.
	 * @return заполненный шаблон профиля
	 */
	protected Profile fillProfileTemplate(Element element)
	{
		Profile template = new Profile();
		template.setFirstname(XmlHelper.getSimpleElementValue(element, Constants.FIRSTNAME_TAG));
		template.setPatrname(XmlHelper.getSimpleElementValue(element, Constants.PATRNAME_TAG));
		template.setSurname(XmlHelper.getSimpleElementValue(element, Constants.SURNAME_TAG));
		template.setBirthdate(XMLDatatypeHelper.parseDateTime(XmlHelper.getSimpleElementValue(element, Constants.BIRTHDATE_TAG)));
		template.setPassport(XmlHelper.getSimpleElementValue(element, Constants.PASSPORT_TAG));
		template.setTb(XmlHelper.getSimpleElementValue(element, Constants.TB_TAG));
		return template;
	}

	/**
	 * Постоить из запроса шаблон профиля
	 *      firstname               Имя пользователя                                            [1]
	 *      patrname                Отчество пользователя                                       [0..1]
	 *      surname                 Фамилия пользователя                                        [1]
	 *      birthdate               Дата рождения пользователя                                  [1]
	 *      passport                ДУЛ пользователя                                            [1]
	 *      tb                      ТБ пользователя                                             [1]
	 * @param userInfo - Информация о пользователе.
	 * @return заполненный шаблон профиля
	 */
	protected Profile fillProfileTemplate(CSAUserInfo userInfo)
	{
		Profile template = new Profile();
		template.setFirstname(userInfo.getFirstname());
		template.setPatrname(userInfo.getPatrname());
		template.setSurname(userInfo.getSurname());
		template.setBirthdate(userInfo.getBirthdate());
		template.setPassport(userInfo.getPassport());
		template.setTb(Utils.getTBByCbCode(userInfo.getCbCode()));
		return template;
	}

	/**
	 * Постоить из запроса информацию о пользователе
	 *      firstname               Имя пользователя                                            [1]
	 *      patrname                Отчество пользователя                                       [0..1]
	 *      surname                 Фамилия пользователя                                        [1]
	 *      birthdate               Дата рождения пользователя                                  [1]
	 *      passport                ДУЛ пользователя                                            [1]
	 *      cbCode                  Подразделение пользователя                                  [1]
	 * @param element - элемент запроса.
	 * @return заполненная информация о пользователе
	 */
	protected CSAUserInfo fillUserInfo(Element element)
	{
		String firstName = XmlHelper.getSimpleElementValue(element, Constants.FIRSTNAME_TAG);
		String surName = XmlHelper.getSimpleElementValue(element, Constants.SURNAME_TAG);
		String patrName = XmlHelper.getSimpleElementValue(element, Constants.PATRNAME_TAG);
		String passport = XmlHelper.getSimpleElementValue(element, Constants.PASSPORT_TAG);
		String cbCode = XmlHelper.getSimpleElementValue(element, Constants.CB_CODE_TAG);
		Calendar birthDate = XMLDatatypeHelper.parseDateTime(XmlHelper.getSimpleElementValue(element, Constants.BIRTHDATE_TAG));

		return new CSAUserInfo(cbCode, firstName, surName, patrName, passport, birthDate);
	}

	/**
	 * Получить контекст идентифкации по идентифкатору сессии или универсальному идентифкатору.
	 * Параметры в запросе:
	 * SID		                идентификатор сессии      [1]
	 * либо
	 * UserInfo                     Информация о пользователе                                   [1]
	 *      firstname               Имя пользователя                                            [1]
	 *      patrname                Отчество пользователя                                       [0..1]
	 *      surname                 Фамилия пользователя                                        [1]
	 *      birthdate               Дата рождения пользователя                                  [1]
	 *      passport                ДУЛ пользователя                                            [1]
	 *      tb                      ТБ пользователя                                             [1]
	 * @param requestInfo запрос
	 * @return контекст идентифкации
	 * @throws Exception
	 */
	protected IdentificationContext getIdentificationContextByUserInfoOrSID(RequestInfo requestInfo) throws Exception
	{
		Element root = requestInfo.getBody().getDocumentElement();
		String sid = XmlHelper.getSimpleElementValue(root, Constants.SID_TAG);
		if (StringHelper.isNotEmpty(sid))
		{
			return IdentificationContext.createBySessionId(sid);
		}
		Element userInfoElement = XmlHelper.selectSingleNode(root, Constants.USER_INFO_TAG);
		if (userInfoElement != null)
		{
			return IdentificationContext.createByTemplateProfile(fillProfileTemplate(userInfoElement));
		}
		throw new IllegalStateException("Должен быть задан или SID или USER_INFO");
	}
}
