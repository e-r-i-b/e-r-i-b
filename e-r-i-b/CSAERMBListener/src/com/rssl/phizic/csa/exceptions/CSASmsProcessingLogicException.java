package com.rssl.phizic.csa.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author osminin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ���������� ��� ��������� ���-���������
 */
public class CSASmsProcessingLogicException extends LogicException
{
	private final String phoneNumber;

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param phoneNumber ����� ��������
	 */
	public CSASmsProcessingLogicException(String message, String phoneNumber)
	{
		super(message);
		this.phoneNumber = phoneNumber;
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param phoneNumber ����� ��������
	 * @param cause ����������
	 */
	public CSASmsProcessingLogicException(String message, String phoneNumber, Throwable cause)
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
