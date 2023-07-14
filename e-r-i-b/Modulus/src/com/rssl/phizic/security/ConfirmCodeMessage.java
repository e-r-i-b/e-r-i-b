package com.rssl.phizic.security;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.common.types.TextMessage;

/**
 * @author Erkin
 * @ created 23.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Текстовое сообщение, содержащее код подтверждения
 */
@Immutable
@PlainOldJavaObject
public class ConfirmCodeMessage extends TextMessage
{
	private final String confirmCode;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param confirmCode - код подтверждения (never null, never empty)
	 * @param text - текст сообщения для передачи клиенту, содержащий код подтверждения <confirmCode>
	 * @param textToLog текст сообщения для логирования, не содержащий код подтверждения <confirmCode>
	 */
	public ConfirmCodeMessage(String confirmCode, String text, String textToLog)
	{
		super(text, textToLog);
		this.confirmCode = confirmCode;
	}

	/**
	 * @return код подтверждения (never null, never empty)
	 */
	public String getConfirmCode()
	{
		return confirmCode;
	}
}
