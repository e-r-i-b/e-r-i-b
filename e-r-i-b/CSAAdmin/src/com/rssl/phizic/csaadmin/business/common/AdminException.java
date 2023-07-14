package com.rssl.phizic.csaadmin.business.common;

import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * @author akrenev
 * @ created 30.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������ CSAAdmin
 */

public class AdminException extends IKFLException
{
	/**
	 * ��������� �����������
	 */
	public AdminException()
	{}

	/**
	 * ����������� �� ���������
	 * @param message ���������
	 */
	public AdminException(String message)
	{
		super(message);
	}

	/**
	 * ����������� �� ����������
	 * @param cause ����������
	 */
	public AdminException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ����������� �� ��������� � ����������
	 * @param message ���������
	 * @param cause ����������
	 */
	public AdminException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
