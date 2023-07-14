package com.rssl.phizic.operations.person.list;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author mihaylov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �������������, ����� ������� ������ �������� � �����, ��� ���������
 */
public class TooManyActivePersonsException extends BusinessLogicException
{
	/**
	 * �����������
	 * @param message ���������
	 */
	public TooManyActivePersonsException(String message)
	{
		super(message);
	}
}
