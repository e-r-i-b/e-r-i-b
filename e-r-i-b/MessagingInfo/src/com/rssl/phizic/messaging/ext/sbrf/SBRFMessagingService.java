package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.GuestLoginImpl;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.imsi.LoginIMSIError;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.SendSMSPreferredMethod;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.confirm.NullOperationConfirmLogWriter;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.confirm.OperationConfirmLogWriter;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.messaging.ext.sbrf.push.SendPushFactory;
import com.rssl.phizic.messaging.ext.sbrf.sms.SendSmsFactory;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 11.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� MessagingService ��� �����
 */
public class SBRFMessagingService extends GeneralMessagingService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final String MB_ERROR_MESSAGE = "��� ���� ����� �������� ����������� ������ �� SMS, ��� ���������� ���������� ������ \"��������� ����\"";
	private static final String MB_ERROR_SUFFIX = " ��� ���������� � ����.";
	///////////////////////////////////////////////////////////////////////////

	/**
	 * �������� ������������ SMS � ����������.
	 * � ����������� �� "��������", ����� ����� ���������
	 * ���� �� ������ ��������, ���������� � ������ �������,
	 * ���� �� ������ �������� ����� �������
	 * @param message - ���������
	 */
	public String sendSms(IKFLMessage message) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		return sendMessage(message, new SendSmsFactory(), NullOperationConfirmLogWriter.INSTANCE);
	}

	/**
	 * �������� ������������ push-���������.
	 * @param message - ���������
	 */
	public String sendPush(PushMessage message) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		return sendMessage(message, new SendPushFactory(), NullOperationConfirmLogWriter.INSTANCE);
	}

	public String sendOTPSms(IKFLMessage message) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		return sendMessage(message, new SendSmsFactory(), ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter());
	}

	public String sendOTPPush(PushMessage message) throws IKFLMessagingException, IKFLMessagingLogicException
	{
		return sendMessage(message, new SendPushFactory(), ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter());
	}

	protected String sendMessage(IKFLMessage message, SendMessageFactory sendMessageFactory, OperationConfirmLogWriter confirmLogWriter)  throws IKFLMessagingException, IKFLMessagingLogicException
	{
		// ���� �������� ������ �� ������ ����������, ���������� ������� ����������
		if (message.isUseRecipientMobilePhoneOnly())
			return super.sendMessage(message, sendMessageFactory, confirmLogWriter);
		String text = message.getText();
		String textToLog = message.getTextToLog();
		if (StringHelper.isEmpty(textToLog))
			textToLog = text;
		IKFLMessageContext messageContext = null;
		try{
			messageContext = buildMessageContext(message);
		}
		catch (IKFLMessagingException e){

			logFailed(confirmLogWriter, sendMessageFactory.getConfirmType(), textToLog, null, message.isAdditionalCheck());
			throw e;
		}

		ActivePerson person = messageContext.getRecipient();
		Login login = person == null ? message.getRecipientLogin() : person.getLogin();

		if (PersonHelper.isGuest())
			log.info("�������� ��������� ������� � guestCode=" + ((GuestLoginImpl) login).getGuestCode() + " � ������� '" + textToLog + "' ");
		else
			log.info("�������� ��������� ������� LOGIN_ID=" + login.getId() + " � ������� '" + textToLog + "' ");

		// 1. ���������� ������� �������
		List<SendMessageMethod> sendMessageMethods = null;
		SendSMSPreferredMethod sendMessagePreferredMethod = null;
		try{
			// 1.1 ���������������� ������ �������� ��������� ��� ������������� �������
			sendMessagePreferredMethod = getSendMessagePreferredMethod(person);
			// 2. ���������� ������������������ �������� �������� ���
			sendMessageMethods = getMessageMethods(sendMessageFactory, messageContext, sendMessagePreferredMethod);
		}
		catch (IKFLMessagingException e){

			logFailed(confirmLogWriter, sendMessageFactory.getConfirmType(), textToLog, null, message.isAdditionalCheck());
			throw e;
		}


		List<SendMessageResult> sendMessageResults = new ArrayList<SendMessageResult>(2);
		Map<String, SendMessageError> errorInfo = new HashMap<String, SendMessageError>();

		try
		{
			List<String> recipientList = new ArrayList<String>();
			for (SendMessageMethod sendMessageMethod : sendMessageMethods)
			{
				Pair<SendMessageResult, Map<String, SendMessageError>> sendMessageResult = send(sendMessageMethod, text, login, textToLog, message.getPriority());
				errorInfo.putAll(sendMessageResult.getSecond());
				if (sendMessageResult.getFirst().isSended())
				{
					logSuccess(confirmLogWriter, sendMessageFactory.getConfirmType(),textToLog, sendMessageMethod.getRecipient(),  message.isAdditionalCheck());
					return sendMessageFactory.getErrorInfoMessage(errorInfo, message.getOperationType());
				}
				if (!StringHelper.isEmpty(sendMessageMethod.getRecipient()))
					recipientList.add(sendMessageMethod.getRecipient());
				sendMessageResults.add(sendMessageResult.getFirst());
			}
			//���� ��������� ��������� �� ������� ���������� � ���
			logFailed(confirmLogWriter, sendMessageFactory.getConfirmType(), textToLog, StringHelper.stringListToString(recipientList, ';'), message.isAdditionalCheck());
		}
		finally
		{
			if (message.isAdditionalCheck() && sendMessageFactory.methodsCanDoAdditionalCheck() && !PersonHelper.isGuest())
				writeIMSICheckInfo(sendMessageMethods, login);
		}

		// �������� �� �������, ���� ���� ������ ��������� ��������� - ��������� ������������ ���, ����� ���������� ������ ����������
		if (!CollectionUtils.isEmpty(sendMessageMethods))
		{
			if (!errorInfo.isEmpty())
				throw new IKFLMessagingLogicException(sendMessageFactory.getErrorInfoMessage(errorInfo, message.getOperationType()));

			// ���� ������� ���������� MobileBankNotAvailabeException, ������ ��������� ���� �� ���������.
			for (SendMessageResult sendMessageResult : sendMessageResults)
			{
				IKFLMessagingException cause = sendMessageResult.getException();
				if (cause instanceof MobileBankNotAvailabeException)
				{
					String errorMessage = MB_ERROR_MESSAGE +
							(SendSMSPreferredMethod.MOBILE_BANK_ONLY == sendMessagePreferredMethod ? "." : MB_ERROR_SUFFIX);
					throw new IKFLMessagingLogicException(errorMessage, cause);
				}
			}

			throw sendMessageResults.get(0).getException();
		}

		return null;
	}

	private List<SendMessageMethod> getMessageMethods(SendMessageFactory sendMessageFactory, IKFLMessageContext messageContext, SendSMSPreferredMethod sendMessagePreferredMethod)
	{
		IKFLMessage message = messageContext.getMessage();
		String phone = messageContext.getMobilePhone();
		TranslitMode translit = messageContext.getTranslitMode();
		ActivePerson person = messageContext.getRecipient();
		Login login = person == null ? message.getRecipientLogin() : person.getLogin();
		List<SendMessageMethod> sendMessageMethods = new ArrayList<SendMessageMethod>(2);

		OperationType opType = message.getOperationType();
		if (OperationType.LOGIN == opType || OperationType.UNUSUAL_IP == opType)
		{
			sendMessageMethods.add(sendMessageFactory.createSendByClientAllInfoCardMethod(person, phone, translit, sendMessagePreferredMethod == SendSMSPreferredMethod.MOBILE_BANK_ONLY, message));
			return sendMessageMethods;
		}
		ErmbProfile ermbProfile = null;
		switch (sendMessagePreferredMethod)
		{
			case PROFILE:
				sendMessageMethods.add(sendMessageFactory.createSendByPhoneMethod(person, phone, translit, message));
				sendMessageMethods.add(sendMessageFactory.createSendByClientInfoCardsMethod(person, translit, message, message.isUseAlternativeRegistrations()));
				break;
			case MOBILE_BANK:

				ermbProfile = getErmbProfile(login);

				if (ermbProfile != null && ermbProfile.isServiceStatus() && StringHelper.isNotEmpty(ermbProfile.getMainPhoneNumber()))
				{
					sendMessageMethods.add(sendMessageFactory.createSendByPhoneMethod(person, ermbProfile.getMainPhoneNumber(), translit, message));
					sendMessageMethods.add(sendMessageFactory.createSendByClientInfoCardsMethod(person, translit, message, message.isUseAlternativeRegistrations()));
				}
				else
				{
					sendMessageMethods.add(sendMessageFactory.createSendByClientInfoCardsMethod(person, translit, message, message.isUseAlternativeRegistrations()));
					sendMessageMethods.add(sendMessageFactory.createSendByPhoneMethod(person, phone, translit, message));
				}
				break;
			case MOBILE_BANK_ONLY:
				if (OperationType.REGISTRATION_OPERATION == opType)
					sendMessageMethods.add(sendMessageFactory.createSendByClientInfoCardsMethod(person, translit, message, message.isUseAlternativeRegistrations()));

				else if (OperationType.CALLBACK_BCI_CONFIRM_OPERATION == opType)
					sendMessageMethods.add(sendMessageFactory.createSendByPhoneMethod(person, message.getRecipientMobilePhone(), translit, message));

				else
				{
					ermbProfile = getErmbProfile(login);

					if (ermbProfile != null && ermbProfile.isServiceStatus() && StringHelper.isNotEmpty(ermbProfile.getMainPhoneNumber()))
						sendMessageMethods.add(sendMessageFactory.createSendByPhoneMethod(person, ermbProfile.getMainPhoneNumber(), translit, message));
					else
						sendMessageMethods.add(sendMessageFactory.createSendByClientInfoCardsMethod(person, translit, message, message.isUseAlternativeRegistrations()));						}
				}

		return sendMessageMethods;
	}

	/**
	 * ���������� ���������� � �������� IMSI, ���� ��� ���� ����������.
	 * @param sendMessageMethods ������ ������� �������� ���.
	 */
	private void writeIMSICheckInfo(List<SendMessageMethod> sendMessageMethods, Login login) throws IKFLMessagingException
	{
		boolean imsiResult = true;
		for (SendMessageMethod sendMessageMethod : sendMessageMethods)
		{
			imsiResult = imsiResult && sendMessageMethod.imsiResult();
		}
		
		try
		{
			//������� ������ � �������� ����� sim-�����.
			LoginIMSIError loginIMSIError = new LoginIMSIError();
			loginIMSIError.setGoodIMSI(imsiResult);
			loginIMSIError.setLogin(login);
			simpleService.addOrUpdate(loginIMSIError, null);
		}
		catch (BusinessException e)
		{
			throw new IKFLMessagingException(e);
		}
	}

	private SendSMSPreferredMethod getSendMessagePreferredMethod(ActivePerson person) throws IKFLMessagingException
	{
		try
		{
			Long departmentId = null;
			if (PersonHelper.isGuest())
			{
				LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
				ExtendedDepartment department = departmentService.getDepartment(loanClaimConfig.getGuestLoanDepartmentTb(),
						loanClaimConfig.getGuestLoanDepartmentOsb(), loanClaimConfig.getGuestLoanDepartmentVsp());
				departmentId = department.getId();
			}
			else
				departmentId = person.getDepartmentId();
			ExtendedDepartment terBank = (ExtendedDepartment) departmentService.getTB(departmentId);
			return terBank.getSendSMSPreferredMethod();
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� �� �������.", e);
			throw new IKFLMessagingException(e);
		}
	}

	private Pair<SendMessageResult, Map<String, SendMessageError>> send(SendMessageMethod sendMessageMethod, String text, Login login, String textToLog, Long priority)
	{
		try
		{
			sendMessageMethod.send(text,textToLog, priority);
			if (PersonHelper.isGuest())
				log.debug("��������� ������� ����������, " +
						"��������� " + sendMessageMethod.toString() + ". " +
						"guestCode=" + ((GuestLoginImpl) login).getGuestCode());
			else
				log.debug("��������� ������� ����������, " +
						"��������� " + sendMessageMethod.toString() + ". " +
						"LOGIN_ID=" + login.getId());
			return new Pair<SendMessageResult, Map<String, SendMessageError>>(new SendMessageResult(true, null), sendMessageMethod.getErrorInfo());
		}
		catch (IKFLMessagingException ex)
		{
			if (PersonHelper.isGuest())
				log.error("������� ��������� ��������� " +
						"guestCode=" + ((GuestLoginImpl) login).getGuestCode() +
						"��������� " + sendMessageMethod.toString() + ", " +
						"��������� ���������", ex);
			else
				log.error("������� ��������� ��������� " +
						"������������ LOGIN_ID=" + login.getId() + ", " +
						"��������� " + sendMessageMethod.toString() + ", " +
						"��������� ���������", ex);
			return new Pair<SendMessageResult, Map<String, SendMessageError>>(new SendMessageResult(false, ex), sendMessageMethod.getErrorInfo());
		}
	}

	/**
	 * ��������� ���� ������� ������������.
	 * @param login ����� ������������
	 * @return ���� ������� ������������. ���� ������������ - �����, ������� null.
	 */
	private ErmbProfile getErmbProfile(Login login)
	{
		return  login instanceof GuestLogin ? null : ErmbHelper.getErmbProfileByLogin(login);
	}
}
