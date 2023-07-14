package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

import java.util.Collection;
import java.util.Collections;

/**
 * Исключение при отправке смс
 * @author niculichev
 * @ created 02.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankSendSmsMessageException extends LogicException
{
	private Collection<String> errorPhones = Collections.emptySet();

	public MobileBankSendSmsMessageException(String message)
	{
		super(message);
	}

	public MobileBankSendSmsMessageException(String message, Collection<String> errorPhones)
	{
		super(message);
		this.errorPhones = errorPhones;
	}

	/**
	 * @return список номеров телефонов по которым не удалось отправить смс-сообщение
	 */
	public Collection<String> getErrorPhones()
	{
		return errorPhones;
	}
}
