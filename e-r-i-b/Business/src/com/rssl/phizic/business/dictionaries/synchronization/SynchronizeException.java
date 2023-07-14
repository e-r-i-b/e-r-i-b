package com.rssl.phizic.business.dictionaries.synchronization;

import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������������� ������������
 */

public class SynchronizeException extends IKFLException
{
	/**
	 * �����������
	 * @param message ���������
	 */
	public SynchronizeException(String message)
	{
		super(message);
	}

	/**
	 * �����������
	 * @param message ���������
	 * @param cause   ������
	 */
	public SynchronizeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
