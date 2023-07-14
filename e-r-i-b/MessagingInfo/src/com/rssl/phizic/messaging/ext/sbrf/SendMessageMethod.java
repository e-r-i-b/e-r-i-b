package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.gate.mobilebank.SendMessageError;

import java.util.Map;

/**
 * @author Erkin
 * @ created 09.11.2010
 * @ $Author$
 * @ $Revision$
 */
public interface SendMessageMethod
{
	void send(String text,String textToLog, Long priority) throws IKFLMessagingException;

	String getRecipient();

	Map<String, SendMessageError> getErrorInfo();

	/**
	 * @return результат проверки сим-карты (если проверка не выполняется, то результат true).
	 */
	boolean imsiResult();
}
