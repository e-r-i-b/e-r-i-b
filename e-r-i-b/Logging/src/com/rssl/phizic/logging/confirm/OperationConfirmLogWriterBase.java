package com.rssl.phizic.logging.confirm;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class OperationConfirmLogWriterBase implements OperationConfirmLogWriter
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void initializeSMSSuccess(String recipient, String textToLog, boolean additionalCheck)
	{
		save(ConfirmType.SMS, recipient, textToLog, null, null, additionalCheck, null, ConfirmState.INIT_SUCCESS);
	}

	public void initializeSMSFailed(String recipient, String textToLog, boolean additionalCheck)
	{
		save(ConfirmType.SMS, recipient, textToLog, null, null, additionalCheck, null, ConfirmState.INIT_FAILED);
	}

	public void initializePUSHSuccess(String recipient, String textToLog)
	{
		save(ConfirmType.PUSH, recipient, textToLog, null, null, false, null, ConfirmState.INIT_SUCCESS);
	}

	public void initializePUSHFailed(String recipient, String textToLog)
	{
		save(ConfirmType.PUSH, recipient, textToLog, null, null, false, null, ConfirmState.INIT_FAILED);
	}

	public void initializeCardSuccess(String cardNumber, String passwordNumber)
	{
		save(ConfirmType.CARD, null, null, cardNumber, passwordNumber, false, null, ConfirmState.INIT_SUCCESS);
	}

	public void initializeCardFailed(String userId)
	{
		save(ConfirmType.CARD, null, null, userId, null, false, null, ConfirmState.INIT_FAILED);
	}

	public void initializeCAPSuccess(String capCardNumber)
	{
		save(ConfirmType.CAP, null, null, capCardNumber, null, false, null, ConfirmState.INIT_SUCCESS);
	}

	public void initializeCAPFailed()
	{
		save(ConfirmType.CAP, null, null, null, null, false, null, ConfirmState.INIT_FAILED);
	}

	public void confirmFailed(ConfirmType confirmType, String confirmCode)
	{
		save(confirmType, null, null, null, null, false, confirmCode, ConfirmState.CONF_FAILED);
	}

	public void confirmSuccess(ConfirmType confirmType, String confirmCode)
	{
		save(confirmType, null, null, null, null, false, confirmCode, ConfirmState.CONF_SUCCESS);
	}

	public void confirmTimeout(ConfirmType confirmType, String confirmCode)
	{
		save(confirmType, null, null, null, null, false, confirmCode, ConfirmState.CONF_TIMEOUT);
	}

	private void save(ConfirmType type, String recipient, String message, String cardNumber, String passwordNumber, boolean additionalCheck, String confirmCode, ConfirmState state)
	{
		try
		{
			OperationConfirmLogEntry entry = createEntry();
			entry.setDate(Calendar.getInstance());
			entry.setOperationUID(OperationContext.getCurrentOperUID());
			entry.setSessionId(LogThreadContext.getSessionId());
			entry.setType(type);
			entry.setRecipient(recipient);
			entry.setMessage(message);
			entry.setCardNumber(cardNumber);
			entry.setPasswordNumber(passwordNumber);
			entry.setCheckIMSI(additionalCheck);
			entry.setConfirmCode(confirmCode);
			entry.setState(state);
			doSave(entry);
		}
		catch (Exception e)
		{
			log.error("Не удалось добавить в лог запрос на ввод пароля", e);
		}
	}

	private OperationConfirmLogEntry createEntry()
	{
		if (LogThreadContext.isGuest())
		{
			return new GuestOperationConfirmLogEntry();
		}
		return new OperationConfirmLogEntry();
	}

	protected abstract void doSave(OperationConfirmLogEntry entry) throws Exception;
}
