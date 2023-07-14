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

	private static final int ERROR_CODE_IDENTIFICATION_FAILED = 500;            //������ �������������
	private static final int ERROR_CODE_BAD_LOGIN = 502;                        //������������ �����
	private static final int ERROR_CODE_BAD_PASSWORD = 503;                     //������������ ������
	private static final int ERROR_CODE_RESTRICTON_VIOLATED = 504;              //�������� ������ ����������e �� ���������� ��������
	private static final int ERROR_CODE_LOGIN_ALREADY_REGISTERED = 505;         //����� ��� �����
	private static final int ERROR_CODE_BAD_OUID = 506;                         //���������������� ������������� ��������.
	private static final int ERROR_CODE_WRONG_CONFIRM_ATEMPT = 507;             //������������ ������� �������������.
	private static final int ERROR_CODE_LOGIN_NOT_FOUND = 508;                  //����� �� ��������������� � �������
	private static final int ERROR_CODE_CONNECTOR_BLOCKED = 509;                //��������� ������������
	private static final int ERROR_CODE_AUTENTIFICATION_FAILED = 510;           //������ ��������������
	private static final int ERROR_CODE_HACKING_RESTRICTON_VIOLATED = 511;      //��������� �����������-������ �� ���� (������� �������� � �.�.).
	private static final int ERROR_CODE_BAD_MGUID = 512;                        //���������������� ������������� ����������.
	private static final int ERROR_CODE_BAD_SID = 513;                          //���������������� ������������� ������
	private static final int ERROR_CODE_NOT_FOUND_MOBILE_REGISTRATION = 514;    //�� ������� �� ������ ������ ������������������ � ��������� �����
	private static final int ERROR_CODE_ERROR_SEND_SMS_MESSAGE = 515;           //�� ������� ��������� ��� ��������� �� �� ���� �� ������������������ �������
	private static final int ERROR_CODE_GATE_TIMEOUT = 516;                     //������ ������� �������� ������

	private static final int ERROR_CODE_ACTUALIZATION_LOGON_INFO_REQUIRED = 517;//������ "��������� ������������ ���������� � �����" (����� ������)
	private static final int ERROR_CODE_AUTHENTIFICATION_REGUIRED = 518;         //������. ��������� ��������������
	private static final int ERROR_CODE_SERVICE_UNAVAILABLE = 519;               //������. ������ �������� ����������
	private static final int ERROR_CODE_IPAS_UNAVAILABLE = 520;                  //���������� ������ iPas
	private static final int ERROR_CODE_RETRY_IPAS_UNAVAILABLE = 530;            //���������� ������ iPas, ������ ������� ����������� ��� ���.

	private static final int ERROR_CODE_BUSINESS_ENVIRONMENT_REFUSE = 521;       //����� ������� ����� � �����������
	private static final int ERROR_CODE_BUSINESS_ENVIRONMENT_ERROR = 522;        //������ �������������� � ������� ������

	private static final int ERROR_CODE_ACTIVE_PHONE_ERMB_REQUIRED = 523;        //������� � ���� ��������� ������ �� ��������� (���������) ��������
	private static final int ERROR_CODE_BLOCKING_RULE_ACTIVE = 524;              //��������� ��������� �� ���� � �����������

	private static final int ERROR_CODE_CARD_BY_PHONE_NOT_FOUND = 525;           //�� ������� ����� �� ������ ��������
	private static final int ERROR_CODE_USER_INFO_BY_CARD_NOT_FOUND = 526;       //�� ������� ���������� � ������������ �� �����

	private static final int ERROR_CODE_USER_NOT_FOUND = 527;                    //�� ������� ���������� � ������������
	private static final int ERROR_CODE_USER_ALREADY_REGISTERED = 528;           //�� ������� ���������� � ������������
	private static final int ERROR_CODE_DUPLICATE_PHONE_REGISTRATIONS = 529;     //������� ��������� ����������� ���������
	private static final int ERROR_CODE_ACTIVATE_PHONE_REGISTRATION_ERROR = 531; //������ ��������� ����������� ��������

	private static final int ERROR_CODE_PROFILE_NOT_FOUND = 532;                 //�� ������ ������� ������������

	private static final int ERROR_CODE_PROFILE_LOCKED                      = 533; // ������� ������������
	private static final int ERROR_CODE_NOT_MAIN_CARD_REGISTRATION          = 534; // ����� ����������� �� �������� ��������
	private static final int ERROR_CODE_INACTIVE_CARD_REGISTRATION          = 535; // ����� ����������� �� �������� ��������
	private static final int ERROR_CODE_DENY_CB_CODE_OPERATION              = 536; // ����������� ���������� ������ �������� � ������� ��
	private static final int ERROR_CODE_USER_REGISTERED_ALREADY_ENTERED     = 537; // ����������� �����������, ���� ������������ ��� ������ � �������

	private static final int ERROR_CODE_ACTIVE_CSA_CONNECTORS_NOT_FOUND     = 538; // �� ������� �������� ��� ����������
	private static final int ERROR_CODE_USER_INFO_BY_PHONE_NOT_FOUND        = 539; // �� ������� ���������� � ������������ �� ��������
	private static final int ERROR_TECHNO_BREAK                             = 540; // ���. �������
	private static final int ERROR_EXTERNAL_SYSTEM_STAND_IN                 = 541; // Stand-in ������� �������
	private static final int ERROR_CHECK_IMSI                               = 542; // ������������ IMSI
	private static final int ERROR_CODE_STAND_IN_MODE                       = 543; // ������������� �������� � ������ StandIn
	private static final int ERROR_CODE_FRAUD_BLOCK_PROFILE                 = 544; // ���� ����������: ���������� �������
	private static final int ERROR_CODE_FRAUD_PROHIBITION_OPERATION         = 545; // ���� ����������: ������ ���������� ��������

	private static final String ERROR_LOGIN_NOT_FOUND = "�� ������� ������������ ��� ��� ������������ ������.";
	private static final String ERROR_NOT_FOUND_MOBILE_REGISTRATION = "� ��� �� ���������� ������ ���������� ����. ���������� ������ �� ������ � ����� ��������� ���������. ��������� ����� ��������������� ���������.";
	private static final String ERROR_SEND_SMS_MESSAGE = "� ����� ������������ �������� ������������ �������� SMS-������� �� ����� %s � ����� � ������� SIM-����� �� ������� ������ ��������� � ���������� ����� ��������� �� �������� 8-800-555-5550.";
	private static final String ERROR_TOO_MANY_MOBILE_CONNECTORS = "�� ��� ���������������� ������������ ���������� ��������� ���������. ����������, ������� � ������ ��������� ���������� � �������� ������ � ��������� ������ ���������� ��� ���������� �����������.";
	private static final String ERROR_HACKING_RESTRICTON_VIOLATED = "�� ��������� ���������� ������ �� ����������� ����������. ����������, ��������� ������� �����.";
	private static final String ERROR_BAD_OUID = "�� ��������� ���������� ������� ����� ������ ��� ������������� ��������. ����������, �������� ����� ������ �� ����������� ����������.";
	private static final String ERROR_WRONG_CONFIRM_ATEMPT = "�� ������� ������� SMS-������ ��� ������������� ����������� ����������. ����������, ������� ������ �����.";
	private static final String ERROR_GATE_TIMEOUT = "��������� ����� �������� ������ �� ������� �������.";
	private static final String ERROR_ACTIVE_PHONE_ERMB_REQUIRED = "��� ������ �� ��������, ��� ��� ������ ����� �������� �� ������ �������� ��� ����������� ���������� �����. ��� ���������� �������� ��������� ��������� � ������� ������ ��������.";

	private final String responceType;

	/**
	 * �����������
	 * @param responceType ��� �������
	 */
	public FailureResponseHelper(String responceType)
	{
		this.responceType = responceType;
	}

	/**
	 * ������������ �����-������: ����� �� ������ �������� �� �������
	 * @param phoneNumber ����� ��������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildCardByPhoneNotFoundResponse(String phoneNumber) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_CARD_BY_PHONE_NOT_FOUND, "����� �� �������� � ������� " + phoneNumber + " �� �������.");
	}

	/**
	 * ������������ �����-������: ���������� � ������������ �������� �� �������
	 * @param phoneNumber ����� ��������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildUserInfoByPhoneNotFoundResponse(String phoneNumber) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_USER_INFO_BY_PHONE_NOT_FOUND, "���������� � ������������ �� �������� � ������� " + phoneNumber + " �� �������.");
	}

	/**
	 * ������������ �����-������: ���������� � ������������ �� ����� �� �������
	 * @param cardNumber ����� �����
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildUserInfoByCardNotFoundResponse(String cardNumber) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_USER_INFO_BY_CARD_NOT_FOUND, "���������� � ������������ �� ����� " + cardNumber + " �� �������.");
	}

	/**
	 * �������� �����-������ "�� �������� �������������"
	 * @param e ���� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildIdentificationFailedResponse(IdentificationFailedException e) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_IDENTIFICATION_FAILED, e.getMessage());
	}

	/**
	 * �������� �����-������ � ������������ ������
	 * @param errorMessage ����� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildBadLoginResponse(String errorMessage) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_LOGIN, errorMessage);
	}

	/**
	 * �������� �����-������ � ������������ ������
	 * @param errorMessage ����� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildBadPasswordResponse(String errorMessage) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_PASSWORD, errorMessage);
	}

	/**
	 * �������� �����-������ � ���������������� �������������� ��������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildBadOUIDResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_OUID, ERROR_BAD_OUID);
	}

	/**
	 * �������� �����-������ � ���������������� �������������� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildBadSIDResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_SID, "���������������� ������������� ������.");
	}

	/**
	 * �������� �����-������ "������������ ��� ���������������"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildUserAlreadyRegisteredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_USER_ALREADY_REGISTERED, "�� ��� ���������������� � ������� ��������� ������. ���� �� ������ ���� ������ � �������������� ���������� ��������������.");
	}

	/**
	 * ��������� �����-������ "������������ ��� ������ � �������"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildUserAlreadyEnteredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_USER_REGISTERED_ALREADY_ENTERED, "����������� ���������� - �� ��� ��������� ������������� �������� ������. ���� �� �� ������ ����� � ������� ������� ��������������� �������� �������������� ������ �� ������ ������ ������?. ����� ������������� ������������ � ������ ����� �������� ����� ���������� ���������������� ��������� ������ � ������� ���������� ����� � � ���������� ������ ��������� ������ +7 (495) 500 5550, 8 (800) 555 5550.");
	}

	/**
	 * �������� �����-������ "����� ��� �����"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildLoginAlreadyRegisteredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_LOGIN_ALREADY_REGISTERED, "��������� ������������� ��� ������������ � �������. ������� ������ ��������.");
	}

	/**
	 * �������� �����-������ "����� �� ������"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildLoginNotFoundResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_LOGIN_NOT_FOUND, ERROR_LOGIN_NOT_FOUND);
	}

	/**
	 * �������� �����-������ "������ ��������������"
	 * @param connector ���������. �� ����� ���� null.
	 * @return ���������� �� ������
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
			return getFailureResponseBuilder(ERROR_CODE_CONNECTOR_BLOCKED, "��� ����� ������������ �� " + String.format(DATE_TIME_FORMAT, calendar) + ". ��������� ������� �����.")
					.addFaultRsaData()
					.addParameter(Constants.BLOCKING_TIMEOUT, timeout > 0 ? timeout : 0)
					.end().getResponceInfo();
		}
		return buildFailureResponse(ERROR_CODE_CONNECTOR_BLOCKED, "��� ����� ������������. ��������� ������� �����.");
	}

	/**
	 * �������� �����-������ "������ ��������������"
	 * @param message ��������� �� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildAuthenticationFailedResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_AUTENTIFICATION_FAILED, message);
	}

	/**
	 * �������� �����-������ "���������������� ������������� ����������"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildBadMGUIDResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BAD_MGUID, "���������������� ������������� ����������. �������� ��������� ����������� ����������.");
	}

	/**
	 * �������� �����-������ "������ �������������� �����"
	 * @param profile �������, ��� �������� �� ������ ��������������
	 * @return ���������� �� ������
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
				return getFailureResponseBuilder(ERROR_CODE_CONNECTOR_BLOCKED, "��� ����� ������������ �� " + String.format(DATE_TIME_FORMAT, calendar) + ". ��������� ������� �����.")
						.addFaultRsaData()
						.addParameter(Constants.BLOCKING_TIMEOUT, timeout > 0 ? timeout : 0)
						.end().getResponceInfo();
			}
		}

		return buildFailureResponse(ERROR_CODE_AUTENTIFICATION_FAILED, ERROR_LOGIN_NOT_FOUND);
	}

	/**
	 * �������� �����-������ "�������� ��� �������������"
	 * @param operation �������������� ��������
	 * @return ���������� �� ������
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
	 * �������� �����-������ "�������� ��� �������������"
	 * @param operation �������������� ��������
	 * @return ���������� �� ������
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
	 * �������� �����-������ "�������� ������ �������� ��������������"
	 * @param operation �������������� ��������
	 * @return ���������� �� ������
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
	 * �������� �����-������ "��������� ���������� ���������������� �������� �� ����������� ��������� ����������"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildTooManyMobileRegistrationRequestResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_HACKING_RESTRICTON_VIOLATED, ERROR_HACKING_RESTRICTON_VIOLATED);
	}

	/**
	 * �������� �����-������ "��������� ���������� ���������������� �������� �� ����������� �������������"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildTooManyUserRegistrationRequestResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_HACKING_RESTRICTON_VIOLATED, "�� ��������� ���������� ������� ����������� � �������. ����������, ��������� ������� �����");
	}

	/**
	 * �������� �����-������ "�� ������� �� ������ ������ ������������������ � ��������� �����"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildMobileBankRegistrationNotFoundRequestResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_NOT_FOUND_MOBILE_REGISTRATION, ERROR_NOT_FOUND_MOBILE_REGISTRATION);
	}

	/**
	 * �������� �����-������ "��������� ����� �������� ������"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildGateTimeoutResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_GATE_TIMEOUT, ERROR_GATE_TIMEOUT);
	}

	/**
	 * �������� �����-������ "�� ������� ��������� ��� ��������� �� �� ���� �� ������������������ �������"
	 * @param errorPhones - ������ ������� ��������� �� ������� �� ������� ��������� ���-���������
	 * @return ���������� �� ������
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
	 * �������� �����-������ "��������� ���������� ������������������ ���������"
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildTooManyMobileConnectorsResponse() throws SAXException
	{
		return buildRestrictionViolatedResponse(ERROR_TOO_MANY_MOBILE_CONNECTORS);
	}

	/**
	 * ��������� ����� ������ � ��������� �����������
	 * @param message ����� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildRestrictionViolatedResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_RESTRICTON_VIOLATED, message);
	}

	/**
	 * ��������� ����� ������ � ������������ �������
	 * @param message ����� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildProfileLockedResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_PROFILE_LOCKED, message);
	}

	/**
	 * ��������� ����� ������ � ������������� ����������� �� ������� ����, ��� ����� ����������� �� ��������
	 * @param message ����� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildNotMainCardRegistrationResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_NOT_MAIN_CARD_REGISTRATION, message);
	}

	/**
	 * ��������� ����� ������ � ������������� ����������� �� ������� ������������ ����� �����������
	 * @param message ����� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildInactiveCardRegistrationResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_INACTIVE_CARD_REGISTRATION, message);
	}

	/**
	 * ��������� ����� ������ �� ����������� �������� � ������� ��
	 * @param message ����� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildDenyCbCodeOperationResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_DENY_CB_CODE_OPERATION, message);
	}

	/**
	 * ��������� ����� ������ �� ����������� �� ���� � �����������
	 * @param message ����� ������
	 * @param date ���� ��������� ����������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildBlockingRuleActiveResponse(String message, String date) throws SAXException
	{
		if(StringHelper.isNotEmpty(date))
			return getFailureResponseBuilder(ERROR_CODE_BLOCKING_RULE_ACTIVE, message).addBlockingRuleInfo(date).addFaultRsaData().end().getResponceInfo();
		return buildFailureResponse(ERROR_CODE_BLOCKING_RULE_ACTIVE, message);
	}

	/**
	 * ������������ ����� ����� � ������������� ������������ ���������� � ����� (����� ������)
	 * @param operation �������� ������������ ���������� � �����.
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildActualizationLogonInfoRequiredResponse(ActualizationLogonInfoOperation operation) throws Exception
	{
		ResponseBuilderHelper builder = getFailureResponseBuilder(ERROR_CODE_ACTUALIZATION_LOGON_INFO_REQUIRED, "��������� ������������ ���������� � �����").addOUID(operation);
		for (Connector connector : operation.getConnectors())
		{
			builder.addConnectorInfo(connector);
		}
		return builder.end().getResponceInfo();
	}

	/**
	 * ������������ ����� ����� � ������������� ��������������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildAuthentificationReguiredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_AUTHENTIFICATION_REGUIRED, "��������� ��������������");
	}

	/**
	 * ������������ ����� ����� ������ �������� ����������
	 * @param message ��������� ��� ������������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildServiceUnavailableResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_SERVICE_UNAVAILABLE, message);
	}

	/**
	 * ������������ ����� ����� ������ �������� ���������� ��-�� ������������� iPas
	 * @param connector ���������, � ��������� �������� ����������� �������������� � iPas
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	//TODO
	public ResponseInfo buildIpasUnavailableResponse(Connector connector) throws Exception
	{
		ResponseBuilderHelper builder = getFailureResponseBuilder(ERROR_CODE_IPAS_UNAVAILABLE, "���������� ������ iPas");
		if (connector != null)
		{
			builder
					.addUserInfo(connector.getProfile())
					.addConnectorInfo(connector);
		}
		return builder.end().getResponceInfo();
	}

	/**
	 * ������������ �����-�����. Ipas �������� ����������. ������������ ������ ���������� ���.
	 * @return ���������� �� ������
	 * @throws Exception
	 * @param message ��������� ��� ������������.
	 */
	public ResponseInfo buildRetryIpasUnavailableResponse(String message) throws Exception
	{
		ResponseBuilderHelper builder = getFailureResponseBuilder(ERROR_CODE_RETRY_IPAS_UNAVAILABLE, message);
		return builder.end().getResponceInfo();
	}

	/**
	 * ������� ����� � ������� ����������� ������ � ������� �����
	 * @param message ������
	 * @return ����� � �������
	 * @throws SAXException
	 */
	public ResponseInfo buildBusinessEnvironmentRefuseResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BUSINESS_ENVIRONMENT_REFUSE, message);
	}

	/**
	 * ������� ����� � ������� �������������� � ������� ������
	 * @param message ������
	 * @return ����� � �������
	 * @throws SAXException
	 */
	public ResponseInfo buildBusinessEnvironmentErrorResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_BUSINESS_ENVIRONMENT_ERROR, message);
	}

	/**
	 * ������� ����� � ������� ������� ��������� �������� �� ����������� ��������.
	 * @return ����� � �������
	 * @throws SAXException
	 */
	public ResponseInfo buildActivePhoneERMBRequiredResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_ACTIVE_PHONE_ERMB_REQUIRED, ERROR_ACTIVE_PHONE_ERMB_REQUIRED);
	}

	/**
	 * ������� ����� � ������� ������ �������.
	 * @param userInfo ���������� �� ������� ������
	 * @return ����� � �������
	 * @throws SAXException
	 */
	public ResponseInfo buildUserNotFoundResponse(CSAUserInfo userInfo) throws SAXException
	{
		String errorDescription = "�� ������ ������ " + userInfo.getFirstname() + " " + userInfo.getSurname() + " " + userInfo.getPatrname();
		return buildFailureResponse(ERROR_CODE_USER_NOT_FOUND, errorDescription);
	}

	/**
	 * ������� ����� � ������� ��������� ����������� ��������
	 * @param message ��������� �� ������
	 * @return ����� � �������
	 * @throws SAXException
	 */
	public ResponseInfo buildActivatePhoneRegistrationErrorResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_ACTIVATE_PHONE_REGISTRATION_ERROR, message);
	}

	/**
	 * ������� ����� � ������������ ����������� ���������
	 * @param message ��������� �� ������
	 * @return ����� � �������
	 * @throws SAXException
	 */
	public ResponseInfo buildDuplicatePhoneRegistrations(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_DUPLICATE_PHONE_REGISTRATIONS, message);
	}

	/**
	 * ������� ����� � ������� ������ �������.
	 * @param template - ������ ������� �� �������� ������������ �����
	 * @return ����� � �������
	 * @throws SAXException
	 */
	public ResponseInfo buildProfileNotFoundResponse(Profile template) throws SAXException
	{
		StringBuilder descriptionBuilder = new StringBuilder("�� ������ ������ ");
		descriptionBuilder.append(template.getSurname()).append(" ");
		descriptionBuilder.append(template.getFirstname()).append(" ");
		descriptionBuilder.append(template.getPatrname()).append(" ");
		descriptionBuilder.append(" � ���������� ").append(template.getPassport()).append(" ");
		descriptionBuilder.append(" � �������� ").append(template.getTb());
		return buildFailureResponse(ERROR_CODE_PROFILE_NOT_FOUND, descriptionBuilder.toString());
	}

	/**
	 * ������������ �����-�����
	 * @param errorCode ��� ������
	 * @param errorDescription �������� ������
	 * @return ���������� �� ������
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
	 * ������� ����� � ������� ������ �������� ��� �����������
	 * @param message ���������
	 * @return ����� � �������
	 * @throws SAXException
	 */
	public ResponseInfo buildActiveCSAConnectorsNotFoundResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_ACTIVE_CSA_CONNECTORS_NOT_FOUND, message);
	}

	/**
	 * ������������ ����� � ���. �������� ������� �������
	 * @param message ��������� ���. ��������
	 * @return ����� � �������
	 */
	public ResponseInfo buildTechnoBreakResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_TECHNO_BREAK, message);
	}

	/**
	 * ������������ ����� � ������ Stand-In ������� �������
	 * @param message ��������� ���. ��������
	 * @return ����� � �������
	 */
	public ResponseInfo buildStandInResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_EXTERNAL_SYSTEM_STAND_IN, message);
	}

	/**
	 * ��������� ����� ������ � ������������ IMSI
	 * @param message ����� ������
	 * @param errorPhones ��������
	 * @return ���������� �� ������
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
	 * ����� � ������������� �������� � ������ StandIn ���
	 * @param message ����� � ��������� ������
	 * @return ���������� �� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildCSAStandInResponse(String message) throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_STAND_IN_MODE, message);
	}

	/**
	 * ����� � ���������� ������� �� ���������� �������� �� ��
	 * @return message ����� � ��������� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildFraudBlockProfileResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_FRAUD_BLOCK_PROFILE, PROFILE_BLOCKED_ERROR_MESSAGE);
	}

	/**
	 * ����� � ������� ���������� �������� �� ���������� �������� �� ��
	 * @return message ����� � ��������� ������
	 * @throws SAXException
	 */
	public ResponseInfo buildFraudProhibitionOperationResponse() throws SAXException
	{
		return buildFailureResponse(ERROR_CODE_FRAUD_PROHIBITION_OPERATION, PROHIBITION_OPERATION_DEFAULT_ERROR_MESSAGE);
	}
}
