package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author akrenev
 * @ created 14.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class DublicateServiceProviderException extends BusinessLogicException
{
	/**
	 * �����������
	 * @param cause ����������
	 */
	public DublicateServiceProviderException(Throwable cause)
	{
		super("���������� � ����� ��������������� ��� ����������.", cause);
	}
}
