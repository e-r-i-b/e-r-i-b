package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;

/**
 * Исключение, сигнализирующее о том, что нет подключения к Мобильному банку
 * @author Pankin
 * @ created 18.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankNotAvailabeException extends IKFLMessagingException
{
	public MobileBankNotAvailabeException(String message)
	{
		super(message);
	}

	public MobileBankNotAvailabeException(Throwable cause)
	{
		super(cause);
	}

	public MobileBankNotAvailabeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
