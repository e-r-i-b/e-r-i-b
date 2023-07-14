package com.rssl.phizgate.common.messaging.retail.handlers;

import com.rssl.phizic.ConfigurationCheckedException;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.errors.ErrorMessagesMatcher;
import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.errors.ErrorType;
import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.NonUniqueMessageIdException;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import org.xml.sax.SAXException;

import java.util.List;

/**
 * @author Krenev
 * @ created 30.10.2009
 * @ $Author$
 * @ $Revision$
 */
//TODO ��-�� ��������� �������������� ������ �� ������������ ����� ��� � 5.1 � 5.5
public class DictionaryErrorMessageHandler extends DefaultErrorMessageHandler
{
	public void endDocument() throws SAXException
	{
		if (ERROR_UNIQUE_CODE.equals(errorCode))
		{
			exception = new NonUniqueMessageIdException(errorMessage);
		}
/*TODO �������������� ����� ���������� ������ ERROR_VALIDATE
		else if (ERROR_VALIDATE_CODE.equals(errorCode))
		{
			exception = new GateMessagingValidationException(errorMessage);
		}
*/
		else if (ERROR_CLIENT_CODE.equals(errorCode))
		{
			exception = new GateMessagingClientException(errorMessage);
		}
		else
		{
			exception = processError();
		}
	}

	private Exception processError()
	{
		List<ErrorMessage> errorMessages;
		try
		{
			ErrorMessagesMatcher matcher = ErrorMessagesMatcher.getInstance();
//TODO ���� ������ �� ������ ������, �� �������� ���������� � ����  ERROR_VALIDATE
			errorMessages = matcher.matchMessage(errorMessage, ErrorSystem.Retail);
		}
		catch (ConfigurationCheckedException e)
		{
			throw new RuntimeException(e);
		}

		if (errorMessages.size() != 0)
		{
			ErrorMessage dictionaryErrorMessage = errorMessages.get(0);
			if (dictionaryErrorMessage.getErrorType().equals(ErrorType.Client))
				return new GateMessagingClientException(dictionaryErrorMessage.getMessage());
			if (dictionaryErrorMessage.getErrorType().equals(ErrorType.Validation))
				return new GateMessagingValidationException(dictionaryErrorMessage);
		}
		return new GateMessagingException(errorMessage, errorCode, errorMessage);
	}
}
