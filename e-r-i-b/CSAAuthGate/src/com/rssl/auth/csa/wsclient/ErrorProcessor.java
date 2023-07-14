package com.rssl.auth.csa.wsclient;

import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.StandInExternalSystemException;
import com.rssl.phizic.context.Constants;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author niculichev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ErrorProcessor
{
	private static final int ERROR_CODE_FAILURE_IDENTIFICATION          = 500; //������ �������������
	private static final int ERROR_CODE_RESTRICTON_VIOLATED             = 504; //�������� ������ ����������e �� ���������� ��������
	private static final int ERROR_CODE_LOGIN_ALREADY_REGISTERED        = 505; //����� ��� �����
	private static final int ERROR_CODE_ILLEGAL_OPERATION_STATE         = 506; //�������� ��������� ��������
	private static final int ERROR_CODE_WRONG_CONFIRM_ATEMPT            = 507; //������������ ������� �������������.
	private static final int ERROR_CODE_LOGIN_NOT_FOUND                 = 508; //����� �� ������
	private static final int ERROR_CODE_CONNECTOR_BLOCKED               = 509; //��������� ������������
	private static final int ERROR_CODE_AUTENTIFICATION_FAILED          = 510; //������ ��������������
	private static final int ERROR_CODE_NOT_FOUND_MOBILE_REGISTRATION   = 514; //�� ������� ����������� � ��������� �����
	private static final int ERROR_CODE_GATE_TIMEOUT                    = 516; //������ ������� �������� ������
	private static final int ERROR_CODE_FOUND_MANY_CONNECTORS           = 517; //������� ����� ������ ���������� ��� �������������
	private static final int ERROR_CODE_AUTHENTIFICATION_REQUIRED       = 518; // ��������� ��������������
	private static final int ERROR_CODE_IPAS_UNAVAILABLE                = 520; //���������� ������ IPas
	private static final int ERROR_CODE_RETRY_IPAS_UNAVAILABLE          = 530; //���������� ������ iPas, ������ ������� ����������� ��� ���.
	private static final int ERROR_CODE_BUSINESS_ENVIRONMENT_REFUSE     = 521; //����� ������� ����� � �����������
	private static final int ERROR_CODE_BUSINESS_ENVIRONMENT_ERROR      = 522; //������ �������������� � ������� ������
	private static final int ERROR_CODE_BAD_MGUID                       = 512; //���������������� ������������� ����������.
	private static final int ERROR_CODE_HACKING_RESTRICTON_VIOLATED     = 511; //��������� �����������-������ �� ���� (������� �������� � �.�.).
	private static final int ERROR_CODE_ERROR_SEND_SMS_MESSAGE          = 515; //�� ������� ��������� ��� ��������� �� �� ���� �� ������������������ �������
	private static final int ERROR_CODE_ACTIVE_PHONE_ERMB_REQUIRED      = 523; //������� � ���� ��������� ������ �� ��������� (���������) ��������
	private static final int ERROR_CODE_BLOCKING_RULE_ACTIVE            = 524; //��������� ��������� �� ���� � �����������

	private static final int ERROR_CODE_CARD_BY_PHONE_NOT_FOUND         = 525; //�� ������� ����� �� ������ ��������
	private static final int ERROR_CODE_USER_INFO_BY_CARD_NOT_FOUND     = 526; //�� ������� ���������� � ������������ �� �����

	private static final int ERROR_CODE_USER_NOT_FOUND                  = 527; //�� ������� ���������� � ������������
	private static final int ERROR_CODE_USER_ALREADY_REGISTERED         = 528;        //  ������������ ��� ����� �����������

	private static final int ERROR_CODE_DUPLICATE_PHONE_REGISTRATIONS   = 529; //������� ��������� ����������� ���������
	private static final int ERROR_CODE_ACTIVATE_PHONE_REGISTRATION_ERROR = 531; //������ ��������� ����������� ��������

	private static final int ERROR_CODE_PROFILE_NOT_FOUND               = 532; //�� ������ ������� ������������

	private static final int ERROR_CODE_PROFILE_LOCKED                  = 533; // ������� ������������
	private static final int ERROR_CODE_CARD_IS_NOT_MAIN                = 534;  //����� �� �������� ��������
	private static final int ERROR_CODE_CARD_IS_NOT_ACTIVE              = 535;  //��������� ����� ����� �����������
	private static final int ERROR_CODE_DENY_CB_CODE_OPERATION          = 536; // ����������� ���������� ������ �������� � ������� ��
	private static final int ERROR_CODE_USER_REGISTERED_ALREADY_ENTERED = 537; // ����������� �����������, ���� ������������ ��� ������ � �������

	private static final int ERROR_CODE_ACTIVE_CSA_CONNECTORS_NOT_FOUND     = 538; // �� ������� �������� ��� ����������
	private static final int ERROR_CODE_USER_INFO_BY_PHONE_NOT_FOUND        = 539; // �� ������� ���������� � ������������ �� ��������
	private static final int ERROR_TECHNO_BREAK                             = 540; // ���. �������
	private static final int ERROR_EXTERNAL_SYSTEM_STAND_IN                 = 541; // Stand-in ������� �������
	private static final int ERROR_CHECK_IMSI                               = 542; // ������������ IMSI
	private static final int ERROR_CODE_FRAUD_BLOCK_PROFILE                 = 544; // ���� ����������: ���������� �������
	private static final int ERROR_CODE_FRAUD_PROHIBITION_OPERATION         = 545; // ���� ����������: ������ ���������� ��������


	public static void processError(String errorCode, String errorText, Document body) throws BackLogicException, BackException
	{
		RSAContext.append(body);

		int code = Integer.parseInt(errorCode);
		switch(code)
		{
			case ERROR_CODE_CARD_IS_NOT_ACTIVE:
			{
				throw new CardIsInvalidException();
			}
			case ERROR_CODE_CARD_IS_NOT_MAIN:
			{
				throw new CardIsNotMainException();
			}
			case ERROR_CODE_FAILURE_IDENTIFICATION:
			{
				throw new FailureIdentificationException();
			}
			case ERROR_CODE_BLOCKING_RULE_ACTIVE:
			{
				String restrictionDate = XmlHelper.getSimpleElementValue(body.getDocumentElement(), RequestConstants.BLOCKING_RULES_DATE);
				throw new BlockingRuleActiveException(errorText, restrictionDate);
			}
			case ERROR_CODE_LOGIN_NOT_FOUND:
			{
				throw new LoginNotFoundException();
			}
			case ERROR_CODE_LOGIN_ALREADY_REGISTERED:
			{
				throw new LoginAlreadyRegisteredException();
			}
			case ERROR_CODE_ILLEGAL_OPERATION_STATE:
			{
				throw new IllegalOperationStateException();
			}
			case ERROR_CODE_NOT_FOUND_MOBILE_REGISTRATION:
			{
				throw new MobileBankRegistrationNotFoundException();
			}
			case ERROR_CODE_GATE_TIMEOUT:
			{
				throw new BackException(errorText);
			}
			case ERROR_CODE_WRONG_CONFIRM_ATEMPT:
			{
				String time = XmlHelper.getSimpleElementValue(body.getDocumentElement(), RequestConstants.TIMEOUT);
				Long attempts = Long.parseLong(XmlHelper.getSimpleElementValue(body.getDocumentElement(), RequestConstants.ATTEMPTS));
				ConfirmStrategyType confirmType = ConfirmStrategyType.valueOf(XmlHelper.getSimpleElementValue(body.getDocumentElement(), RequestConstants.CONFIRMATION_PARAM_NAME));
				throw new InvalidCodeConfirmException(confirmType, attempts, StringHelper.isEmpty(time) ? null : Long.parseLong(time));
			}
			case ERROR_CODE_CONNECTOR_BLOCKED:
			{
				String badAuthDelay = XmlHelper.getSimpleElementValue(body.getDocumentElement(), RequestConstants.BLOCKING_TIMEOUT_TAG);
				throw new ConnectorBlockedException(errorText, StringHelper.isEmpty(badAuthDelay) ? null : Long.parseLong(badAuthDelay));
			}
			case ERROR_CODE_AUTENTIFICATION_FAILED:
			{
				throw new AuthenticationFailedException(errorText);
			}
			case ERROR_CODE_RETRY_IPAS_UNAVAILABLE:
			{
				throw new RestrictionViolatedException(errorText);
			}
			case ERROR_CODE_IPAS_UNAVAILABLE:
			{
				try
				{
					List<ConnectorInfo> list = CSABackResponseSerializer.getConnectorInfos(body);
					throw new IPasUnavailableException(list.isEmpty() ? null : list.get(0));
				}
				catch (TransformerException e)
				{
					throw new RuntimeException("������ ��� ������� ������ �� ��� � ������������� ������� iPas",e);
				}
			}
			case ERROR_CODE_FOUND_MANY_CONNECTORS:
			{
				try
				{
					throw new TooManyConnectorsException(
							CSABackResponseSerializer.getOUID(body), CSABackResponseSerializer.getConnectorInfos(body));
				}
				catch (TransformerException e)
				{
					throw new RuntimeException(e);
				}
			}
			case ERROR_CODE_AUTHENTIFICATION_REQUIRED:
			{
				throw new AuthentificationReguiredException();
			}
			case ERROR_CODE_BUSINESS_ENVIRONMENT_REFUSE:
			{
				throw new FailVerifyBusinessEnvironmentException(errorText);
			}
			case ERROR_CODE_BUSINESS_ENVIRONMENT_ERROR:
			{
				throw new BusinessEnvironmentException(errorText);
			}
			case ERROR_CODE_HACKING_RESTRICTON_VIOLATED:
			{
				throw new NoMoreAttemptsRegistrationException();
			}
			case ERROR_CODE_ERROR_SEND_SMS_MESSAGE:
			{
				String phones = XmlHelper.getSimpleElementValue(body.getDocumentElement(), RequestConstants.PHONES_TAG);
				throw new SendSmsMessageException(errorText, phones);
			}
			case ERROR_CODE_ACTIVE_PHONE_ERMB_REQUIRED:
			{
				throw new PhoneRegistrationNotActiveException(errorText);
			}
			case ERROR_CODE_CARD_BY_PHONE_NOT_FOUND:
			{
				throw new CardByPhoneNotFoundException(errorText);
			}
			case ERROR_CODE_USER_INFO_BY_CARD_NOT_FOUND:
			{
				throw new UserInfoByCardNotFoundException(errorText);
			}
			case ERROR_CODE_USER_NOT_FOUND:
			{
				throw new UserNotFoundException(errorText);
			}
			case ERROR_CODE_USER_ALREADY_REGISTERED:
			{
				throw new UserAlreadyRegisteredException(errorText);
			}
			case ERROR_CODE_DUPLICATE_PHONE_REGISTRATIONS:
			{
				throw new DuplicatePhoneRegistrationsException(errorText);
			}
			case ERROR_CODE_ACTIVATE_PHONE_REGISTRATION_ERROR:
			{
				throw new ActivatePhoneRegistrationException(errorText);
			}
			case ERROR_CODE_PROFILE_NOT_FOUND:
			case ERROR_CODE_USER_INFO_BY_PHONE_NOT_FOUND:
			{
				throw new ProfileNotFoundException();
			}
			case ERROR_CODE_DENY_CB_CODE_OPERATION:
			{
				throw new WrongRegionException();
			}
			case ERROR_CODE_PROFILE_LOCKED:
			{
				throw new UserProfileBlockedException();
			}
			case ERROR_CODE_USER_REGISTERED_ALREADY_ENTERED:
			case ERROR_CODE_RESTRICTON_VIOLATED:
			{
				throw new RestrictionViolatedException(errorText);
			}
			case ERROR_CODE_BAD_MGUID:
			{
				throw new WrongMGUIDException();
			}
			case ERROR_EXTERNAL_SYSTEM_STAND_IN:
			{
				throw new StandInExternalSystemException(errorText);
			}
			case ERROR_TECHNO_BREAK:
			{
				throw new InactiveExternalSystemException(errorText);
			}
			case ERROR_CHECK_IMSI:
			{
				String phones = XmlHelper.getSimpleElementValue(body.getDocumentElement(), RequestConstants.PHONES_TAG);
				throw new CheckIMSIException(errorText, phones);
			}
			default:
				throw new BackLogicException(errorText);

		}
	}
}
