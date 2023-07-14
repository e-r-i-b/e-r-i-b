package com.rssl.phizicgate.iqwave;

import com.rssl.phizic.ConfigurationCheckedException;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.errors.ErrorMessagesMatcher;
import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.errors.ErrorType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;

import java.util.List;

/**
 * @author krenev
 * @ created 04.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class ErrorProcessor
{
	public static final String DEFAULT_ERROR_MESSAGE = "Операция временно недоступна, повторите попытку позже";
	public static final String DEFAULT_TIMEOUT_MESSAGE = "Проведение операции невозможно, внешняя система не доступна";
	public static final String DEFAULT_REFUSE_MESSAGE = "Вам отказано в исполнении операции. Обратитесь, пожалуйста, в банк.";
	public static final String TIMEOUT_ERROR_CODE = "402";

	/**
	 * обработать ошибку (выкинуть ошибку нужного типа) по значекнию из справочника ошибок
	 * @param errorCode код ошибки
	 * @param errorText описание ошибки
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static void processError(String errorCode, String errorText) throws GateMessagingClientException, GateMessagingValidationException, GateMessagingException, GateTimeOutException
	{
		try
		{
			// ошибка 402: Истекло время ожидания ответа
			if(TIMEOUT_ERROR_CODE.equals(errorCode))
				throw new GateTimeOutException(DEFAULT_TIMEOUT_MESSAGE);

			ErrorMessagesMatcher matcher = ErrorMessagesMatcher.getInstance();
			List<ErrorMessage> errorMessages = matcher.matchMessage(errorCode, ErrorSystem.IQWave);
			if (!errorMessages.isEmpty())
			{
				ErrorMessage errorMessage = errorMessages.get(0);
				if (errorMessage.getErrorType().equals(ErrorType.Client))
					throw new GateMessagingClientException(errorMessage.getMessage(), errorCode);
				if (errorMessage.getErrorType().equals(ErrorType.Validation))
					throw new GateMessagingValidationException(errorMessage);
			}
			throw new GateMessagingException(DEFAULT_ERROR_MESSAGE, errorCode, errorText);
		}
		catch (ConfigurationCheckedException e)
		{
			throw new RuntimeException(e);
		}
	}
}
