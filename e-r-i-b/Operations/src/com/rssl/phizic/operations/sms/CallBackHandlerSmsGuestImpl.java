package com.rssl.phizic.operations.sms;

import com.rssl.phizic.auth.GuestLoginImpl;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * @author shapin
 * @ created 10.02.15
 * @ $Author$
 * @ $Revision$
 */
public class CallBackHandlerSmsGuestImpl implements CallBackHandler
{
	private static final String NOT_SEND_PHONE = "Не удалось отправить СМС сообщение на номер: %s";
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final MessageComposer messageComposer = new MessageComposer();
	private final MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();

	private ConfirmableObject confirmableObject;
	private boolean needAdditionalCheck = false;
	private String password;


	public String proceed() throws Exception
	{
		GuestLoginImpl login = (GuestLoginImpl) PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		try
		{
			IKFLMessage message = messageComposer.buildConfirmationSmsPasswordMessage(login, confirmableObject, password, needAdditionalCheck);

			message.setOperationType(OperationType.PAYMENT_OPERATION);
			message.setRecipientMobilePhone(login.getAuthPhone());

			messagingService.sendOTPSms(message);
		}
		catch (IKFLMessagingException e)
		{
			log.error(e);
			return String.format(NOT_SEND_PHONE, login.getAuthPhone());
		}
		return null;

	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmableObject getConfimableObject()
	{
		return confirmableObject;
	}

	public void setLogin(Login login){}

	public Login getLogin()
	{
		return null;
	}

	public void setAdditionalCheck(){}

	public void setOperationType(OperationType operationType){}

	public void setUseRecipientMobilePhoneOnly(boolean useRecipientMobilePhoneOnly){}

	public void setNeedWarningMessage(boolean needWarningMessage){}

	public void setUseAlternativeRegistrations(boolean useAlternativeRegistrations){}

}
