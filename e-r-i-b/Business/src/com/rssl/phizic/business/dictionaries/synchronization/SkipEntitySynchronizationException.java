package com.rssl.phizic.business.dictionaries.synchronization;

import com.rssl.phizic.business.BusinessException;

/**
 * @author mihaylov
 * @ created 14.03.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ��� ������������� ������������, ���������������� � ������������� ���������� ������� ��������
 * �����!!! �� ����������� ��� ����������, ������������� ������ ���� �� ������� ������� � ����.
 * ����� �������� ��������� �������� ��������������� ���������� ������������ � �����.
 *
 */
public class SkipEntitySynchronizationException extends BusinessException
{

	/**
	 * @param message ��������� ��� ������ � ���
	 */
	public SkipEntitySynchronizationException(String message)
	{
		super(message);
	}
}
