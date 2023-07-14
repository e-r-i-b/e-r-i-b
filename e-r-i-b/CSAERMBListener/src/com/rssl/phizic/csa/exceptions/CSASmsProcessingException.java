package com.rssl.phizic.csa.exceptions;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author osminin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ��� ��������� ���-���������
 */
public class CSASmsProcessingException extends SystemException
{
	private final String phoneNumber;

	/**
	 * ctor
	 * @param cause ����������
	 * @param phoneNumber ����� ��������
	 */
	public CSASmsProcessingException(Throwable cause, String phoneNumber)
	{
		super(cause);
		this.phoneNumber = phoneNumber;
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param phoneNumber ����� �������
	 * @param cause ����������
	 */
	public CSASmsProcessingException(String message, String phoneNumber, Throwable cause)
	{
		super(message, cause);
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return ����� ��������
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
}
