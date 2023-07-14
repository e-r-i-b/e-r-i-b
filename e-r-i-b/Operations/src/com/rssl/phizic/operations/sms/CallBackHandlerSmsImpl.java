package com.rssl.phizic.operations.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;

/**
 * @author eMakarov
 * @ created 01.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class CallBackHandlerSmsImpl implements CallBackHandler
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	protected final MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();

	protected String password;
	protected Login login;
	protected ConfirmableObject confirmableObject;
	protected boolean needAdditionalCheck = false;
	protected boolean useRecipientMobilePhoneOnly = false;
	protected final MessageComposer messageComposer = new MessageComposer();
	protected boolean needWarningMessage = false;
	protected boolean useAlternativeRegistrations = false;
	protected OperationType operationType;
	protected String phoneNumber;

	private void proceedWarningMessage()
	{
		try
		{
			IKFLMessage message = messageComposer.buildWarningMessage(login);
			message.setUseRecipientMobilePhoneOnly(useRecipientMobilePhoneOnly);
			messagingService.sendSms(message);
		}
		catch (Exception e)
		{
			//глушим все исключения при отправке дополнительного смс
			log.error("Ошибка при отправке СМС с предупреждением.", e);
		}
	}

	public String proceed() throws IKFLMessagingException, IKFLMessagingLogicException, BusinessException
	{
		IKFLMessage message = messageComposer.buildConfirmationSmsPasswordMessage(
				login, confirmableObject, password, needAdditionalCheck);
		message.setUseRecipientMobilePhoneOnly(useRecipientMobilePhoneOnly);
		message.setUseAlternativeRegistrations(useAlternativeRegistrations);
		message.setOperationType(operationType);
		message.setRecipientMobilePhone(phoneNumber);
		String result = messagingService.sendOTPSms(message);
		if (needWarningMessage)
			proceedWarningMessage();
		return result;
	}

	public void setAdditionalCheck()
	{
		needAdditionalCheck = true;
	}

	public void setOperationType(OperationType operationType)
	{
		this.operationType = operationType;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public void setLogin(Login login)
	{
		this.login = login;
	}

	public Login getLogin()
	{
		return login;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmableObject getConfimableObject()
	{
		return confirmableObject;
	}

	public void setUseRecipientMobilePhoneOnly(boolean useRecipientMobilePhoneOnly)
	{
		this.useRecipientMobilePhoneOnly = useRecipientMobilePhoneOnly;
	}

	public void setNeedWarningMessage(boolean needWarningMessage)
	{
		this.needWarningMessage = needWarningMessage;
	}

	public void setUseAlternativeRegistrations(boolean useAlternativeRegistrations)
	{
		this.useAlternativeRegistrations = useAlternativeRegistrations;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
