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
	 * ����������, ��������� ��� ���������� ����������� ������� � ������ ��������� ������� ������������ code
	 * @param message - ���������
	 * @param cause -
	 */
	public DublicateBillingException(String message, Throwable cause)
	{
			super(message, cause);
	}
}
