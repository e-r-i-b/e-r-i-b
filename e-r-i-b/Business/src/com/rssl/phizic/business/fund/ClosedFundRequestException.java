package com.rssl.phizic.business.fund;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author osminin
 * @ created 24.12.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ����������� � ���, ��� ������ �� ���� ������� ������.
 */
public class ClosedFundRequestException extends BusinessLogicException
{
	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public ClosedFundRequestException(String message)
	{
		super(message);
	}
}
