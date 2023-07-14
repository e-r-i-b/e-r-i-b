package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;

/**
 * User: Moshenko
 * Date: 14.11.2011
 * Time: 14:51:35
 * Ошибка выбрасываемая при формировании 
 */
public class ClaimUnloadExceptoin  extends BusinessException
{

	public ClaimUnloadExceptoin(String message)
	{
		super(message);
	}

	public ClaimUnloadExceptoin(Throwable cause)
	{
		super(cause);
	}

	public ClaimUnloadExceptoin(String message, Throwable cause)
	{
		super(message, cause);
	}
}

