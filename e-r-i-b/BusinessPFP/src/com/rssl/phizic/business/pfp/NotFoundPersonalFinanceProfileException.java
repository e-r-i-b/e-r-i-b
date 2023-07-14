package com.rssl.phizic.business.pfp;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * �� ������� ������������ ���������� ������������
 * @author lepihina
 * @ created 25.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class NotFoundPersonalFinanceProfileException extends BusinessLogicException
{
	public NotFoundPersonalFinanceProfileException(String message)
	{
		super(message);
	}

	public NotFoundPersonalFinanceProfileException(Throwable cause)
	{
		super(cause);
	}

	public NotFoundPersonalFinanceProfileException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
