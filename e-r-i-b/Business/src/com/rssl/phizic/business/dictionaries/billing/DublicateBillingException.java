package com.rssl.phizic.business.dictionaries.billing;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author akrenev
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class DublicateBillingException extends BusinessLogicException
{
	/**
	 * Исключение, бросаемое при сохранении биллинговой системы в случае нарушения условия уникальности code
	 * @param message - сообщение
	 * @param cause -
	 */
	public DublicateBillingException(String message, Throwable cause)
	{
			super(message, cause);
	}
}
