package com.rssl.phizic.business.dictionaries.payment.services;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author akrenev
 * @ created 04.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class DublicatePaymentServiceException extends BusinessLogicException
{
	public DublicatePaymentServiceException()
	{
		super("Услуга с таким идентификатором уже существует.");
	}
}
