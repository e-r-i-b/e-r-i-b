package com.rssl.phizicgate.enisey.messaging;

import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.errors.ErrorMessagesMatcher;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.errors.ErrorType;
import com.rssl.phizic.ConfigurationCheckedException;

import java.util.List;

/**
 * @author gladishev
 * @ created 01.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class ErrorProcessor
{
	public static final String DEFAULT_ERROR_MESSAGE = "Операция временно недоступна, повторите попытку позже";

	/**
	 * обработать ошибку (выкинуть ошибку нужного типа) по значекнию из справочника ошибок
	 * @param errorCode код ошибки
	 * @param errorText описание ошибки
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public static void processError(String errorCode, String errorText) throws GateMessagingClientException, GateMessagingValidationException, GateMessagingException
	{
		try
		{
			//ошибки с отрицательным кодом - системные, с положительным - клиентские (BUG024009)
			if (Integer.parseInt(errorCode) > 0)
			{
				ErrorMessagesMatcher matcher = ErrorMessagesMatcher.getInstance();
				List<ErrorMessage> errorMessages = matcher.matchMessage(errorCode, ErrorSystem.Enisey);
				if (!errorMessages.isEmpty())
				{
					ErrorMessage errorMessage = errorMessages.get(0);
					if (errorMessage.getErrorType().equals(ErrorType.Client))
						throw new GateMessagingClientException(errorMessage.getMessage());
					if (errorMessage.getErrorType().equals(ErrorType.Validation))
						throw new GateMessagingValidationException(errorMessage);
				}
				else
					throw new GateMessagingClientException(errorText);
			}
			throw new GateMessagingException(DEFAULT_ERROR_MESSAGE, errorCode, errorText);
		}
		catch (ConfigurationCheckedException e)
		{
			throw new RuntimeException(e);
		}
		catch (NumberFormatException ne)
		{
			throw new RuntimeException(ne);
		}
	}
}
