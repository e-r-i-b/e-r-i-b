package com.rssl.phizic.csaadmin.business.common;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author mihaylov
 * @ created 13.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ���������� ������ CSAAdmin
 */
public class AdminLogicException extends LogicException
{
	private final long errorCode = 100;

	/**
	 * ����������� �� ���������
	 * @param message ���������
	 */
	public AdminLogicException(String message)
	{
		super(message);
	}

	/**
	 * �����������
	 * @param message ���������
	 * @param cause ����������, ���������� ������
	 */
	public AdminLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @return ��� ������
	 */
	public long getErrorCode()
	{
		return errorCode;
	}
}
