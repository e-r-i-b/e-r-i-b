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
	 * @return ��� ������
	 */
	protected abstract String getResponceType();

	/**
	 * @return ��� �������
	 */
	protected abstract String getRequestType();

	public final ResponseInfo process(RequestInfo requestInfo) throws Exception
	{
		Config config = ConfigFactory.getConfig(Config.class);
		if(config.isStandInMode() && !isAccessStandIn())
		{
			error("������������� �������� ������� " + getRequestType() + " � ������ StandIn ���");
			return getFailureResponseBuilder().buildCSAStandInResponse("�������� �������� ����������.");
		}

		try
		{
			return processRequest(requestInfo);
		}
		//���������� ��������� ����������, �� ������� �� ������� ������ ���� � ��������� ����������� �����
		//��������, ������ ���������� �����, ��� ��������������� ��������.
		catch (MobileBankRegistrationNotFoundException e)
		{
			error("������ ��������� ������� ", e);
			return getFailureResponseBuilder().buildMobileBankRegistrationNotFoundRequestResponse();
		}
		catch (GateTimeOutException e)
		{
			error("������ ��������� ������� ", e);
			return getFailureResponseBuilder().buildGateTimeoutResponse();
		}
		catch (MobileBankSendSmsMessageException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildMobileBankSendSmsMessageRequestResponse(e.getErrorPhones());
		}
		catch (IllegalOperationStateException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildBadOUIDResponse();
		}
		catch (AuthenticationFailedException e)
		{
			error("������ ��������������", e);
			return getFailureResponseBuilder().buildAuthenticationFailedResponse(e.getConnector());
		}
		catch (GuestAuthenticationFailedException e)
		{
			error("������ �������������� �����", e);
			return getFailureResponseBuilder().buildAuthenticationFailedResponse(e.getProfile());
		}
		catch (LoginAlreadyRegisteredException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildLoginAlreadyRegisteredResponse();
		}
		catch (LoginRestrictionException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildBadLoginResponse(e.getMessage());
		}
		catch (PasswordRestrictionException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildBadPasswordResponse(e.getMessage());
		}
		catch (UserAlreadyEnteredRegisterException e)
		{
			warn("������ ��������� �������", e);
			return getFailureResponseBuilder().buildUserAlreadyEnteredResponse();
		}
		catch (UserAlreadyRegisteredException e)
		{
			warn("������ ��������� �������", e);
			return getFailureResponseBuilder().buildUserAlreadyRegisteredResponse();
		}
		catch (OperationNotFoundException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildBadOUIDResponse();
		}
		catch (AuthentificationReguiredException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildAuthentificationReguiredResponse();
		}
		catch (BlockingRuleActiveException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildBlockingRuleActiveResponse(e.getMessage(), e.getDate());
		}
		catch (ProfileLockedException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildProfileLockedResponse(e.getMessage());
		}
		catch (NotMainCardRegistrationException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildNotMainCardRegistrationResponse(e.getMessage());
		}
		catch (InactiveCardRegistrationException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildInactiveCardRegistrationResponse(e.getMessage());
		}
		catch (DenyCbCodeOperationException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildDenyCbCodeOperationResponse(e.getMessage());
		}
		catch (RestrictionException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildRestrictionViolatedResponse(e.getMessage());
		}
		catch (IdentificationFailedException e)
		{
			warn("������ ������������� ������������", e);
			return getFailureResponseBuilder().buildIdentificationFailedResponse(e);
		}
		catch (PhoneRegistrationNotActiveException e)
		{
			warn("������ ��������� �������", e);
			return getFailureResponseBuilder().buildActivePhoneERMBRequiredResponse();
		}
		catch (DuplicatePhoneRegistrationsException e)
		{
			warn("������ ��������� �������", e);
			return getFailureResponseBuilder().buildDuplicatePhoneRegistrations(e.getMessage());
		}
		catch (ServiceUnavailableException e)
		{
			warn("������ ��������� �������", e);
			return getFailureResponseBuilder().buildServiceUnavailableResponse(e.getMessage());
		}
		catch (ActivatePhoneRegistrationException e)
		{
			warn("������ ��������� �������", e);
			return getFailureResponseBuilder().buildActivatePhoneRegistrationErrorResponse(e.getMessage());
		}
		catch (InvalidSessionException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildBadSIDResponse();
		}
		catch (StandInExternalSystemException e)
		{
			error("Stand-In ������� �������", e);
			return getFailureResponseBuilder().buildStandInResponse(e.getMessage());
		}
		catch (InactiveExternalSystemException e)
		{
			error("��������������� �������", e);
			return getFailureResponseBuilder().buildTechnoBreakResponse(e.getMessage());
		}
		catch (IMSICheckException e)
		{
			error("IMSI �� ������", e);
			return getFailureResponseBuilder().buildIMSICheckResponse(e.getMessage(), e.getErrorPhones());
		}
		catch (UnsupportedStandInCSAProcessException e)
		{
			error("������������� �������� � ������ StandIn ���", e);
			return getFailureResponseBuilder().buildCSAStandInResponse(e.getMessage());
		}
		catch (BlockClientOperationFraudException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildFraudBlockProfileResponse();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			error("������ ��������� �������", e);
			return getFailureResponseBuilder().buildFraudProhibitionOperationResponse();
		}
	}

	public boolean isAccessStandIn()
	{
		return false;
	}

	/**
	 * ���������� ������ � ������� ���������
	 * @param requestInfo ���������� � �������
	 * @return ���������� �� ������
	 */
	protected abstract ResponseInfo processRequest(RequestInfo requestInfo) throws Exception;

	/**
	 * �������� ���������� ������-������
	 * @return ���������� ������-������
	 * @throws SAXException
	 */
	protected FailureResponseHelper getFailureResponseBuilder() throws SAXException
	{
		return new FailureResponseHelper(getResponceType());
	}

	/**
	 * �������� ���������� ��������� ������
	 * @return ���������� ��������� ������
	 * @throws SAXException
	 */
	protected ResponseBuilderHelper getSuccessResponseBuilder() throws SAXException
	{
		return new ResponseBuilderHelper(getResponceType());
	}

	/**
	 * �������� ������ �������� �����
	 * @return ������ �������� �����
	 * @throws SAXException
	 */
	protected ResponseInfo buildSuccessResponse() throws SAXException
	{
		return getSuccessResponseBuilder().end().getResponceInfo();
	}

	/**
	 * ������� � ��������� �������� ����� ��� ���������� ����������.
	 * ��� ���� ���������� �������� ������� ��� �������� ������ �� ���������.
	 * @param identificationContext �������� ������������ �������
	 * @param connector ���������
	 * @return ����������� ������ �� ����.
	 */
	protected UserLogonOperation createLogonOperation(IdentificationContext identificationContext, Connector connector, AuthorizedZoneType authorizedZoneType, Map<String, String> parameters, String mobileSDKData) throws Exception
	{
		UserLogonOperation operation = new UserLogonOperation(identificationContext);
		operation.initialize(connector.getGuid(), connector.getType(), authorizedZoneType, parameters, mobileSDKData);
		return operation;
	}

	/**
	 * ��������� ���������������� �������� ��� �����(������������ ����) � ���.
	 * @param identificationContext �������� ������������
	 * @param connector ��������� ��������������
	 * @return ���������� �� ������
	 * @throws Exception
	 */
	protected ResponseInfo prepareApiLogon(IdentificationContext identificationContext, Connector connector) throws Exception
	{
		return prepareLogonBase(identificationContext, connector, null, null);
	}

	/**
	 * ��������� ���������������� �������� ��� �����(������������ ����) � ����.
	 * @param identificationContext �������� ������������
	 * @param connector ��������� ��������������
	 * @return ���������� �� ������
	 * @throws Exception
	 */
	protected ResponseInfo prepareMAPILogon(IdentificationContext identificationContext, Connector connector, AuthorizedZoneType authorizedZoneType, String mobileSDKData) throws Exception
	{
		return prepareLogonBase(identificationContext, connector, authorizedZoneType, mobileSDKData);
	}

	/**
	 * ��������� ���������������� �������� ��� �����(������������ ����).
	 * @param identificationContext �������� ������������
	 * @param connector ��������� ��������������
	 * @return ���������� �� ������
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
			error("������ �������� �������� ����� �������.", e);
			//�������� �����������: ��������� ������ �� ������������ ���� � �����(����� ������).
			trace("������� ������ �� ������������ ���������� �����");
			ActualizationLogonInfoOperation operation = new ActualizationLogonInfoOperation(identificationContext);
			operation.initialize(e.getConnectors(), connector.getGuid());
			trace("���������� ����� ������ ��������� ������������ ���������� � �����");
			return getFailureResponseBuilder().buildActualizationLogonInfoRequiredResponse(operation);
		}
	}

	private ResponseInfo prepareLogonBase(IdentificationContext identificationContext, Connector connector, AuthorizedZoneType authorizedZoneType, String mobileSDKData) throws Exception
	{
		return prepareLogonBase(identificationContext, connector, authorizedZoneType, null, mobileSDKData);
	}

	private ResponseInfo prepareLogonBase(IdentificationContext identificationContext, Connector connector, AuthorizedZoneType authorizedZoneType, Map<String, String> parameters, String mobileSDKData) throws Exception
	{
		trace("������� ������ �� ����");
		UserLogonOperation logonOperation = createLogonOperation(identificationContext, connector, authorizedZoneType, parameters, mobileSDKData);
		trace("���������� ������� �����");
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
	 * �������� �� ������� ������ �������
	 *      firstname               ��� ������������                                            [1]
	 *      patrname                �������� ������������                                       [0..1]
	 *      surname                 ������� ������������                                        [1]
	 *      birthdate               ���� �������� ������������                                  [1]
	 *      passport                ��� ������������                                            [1]
	 *      tb                      �� ������������                                             [1]
	 * @param element - ������� �������.
	 * @return ����������� ������ �������
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
	 * �������� �� ������� ������ �������
	 *      firstname               ��� ������������                                            [1]
	 *      patrname                �������� ������������                                       [0..1]
	 *      surname                 ������� ������������                                        [1]
	 *      birthdate               ���� �������� ������������                                  [1]
	 *      passport                ��� ������������                                            [1]
	 *      tb                      �� ������������                                             [1]
	 * @param userInfo - ���������� � ������������.
	 * @return ����������� ������ �������
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
	 * �������� �� ������� ���������� � ������������
	 *      firstname               ��� ������������                                            [1]
	 *      patrname                �������� ������������                                       [0..1]
	 *      surname                 ������� ������������                                        [1]
	 *      birthdate               ���� �������� ������������                                  [1]
	 *      passport                ��� ������������                                            [1]
	 *      cbCode                  ������������� ������������                                  [1]
	 * @param element - ������� �������.
	 * @return ����������� ���������� � ������������
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
	 * �������� �������� ������������ �� ������������� ������ ��� �������������� �������������.
	 * ��������� � �������:
	 * SID		                ������������� ������      [1]
	 * ����
	 * UserInfo                     ���������� � ������������                                   [1]
	 *      firstname               ��� ������������                                            [1]
	 *      patrname                �������� ������������                                       [0..1]
	 *      surname                 ������� ������������                                        [1]
	 *      birthdate               ���� �������� ������������                                  [1]
	 *      passport                ��� ������������                                            [1]
	 *      tb                      �� ������������                                             [1]
	 * @param requestInfo ������
	 * @return �������� ������������
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
		throw new IllegalStateException("������ ���� ����� ��� SID ��� USER_INFO");
	}
}
