package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;

/**
 * @author akrenev
 * @ created 04.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ��� ������������� �������� ����������� ��� �� ��������������� ��������
 */
public class UnsupportedStateException extends BusinessException
{
	private final String realStatePath;

	/**
	 * @param message ��������� ����������
	 * @param realStatePath ���� ������� ������������� �������� ������� ������������
	 */
	public UnsupportedStateException(String message, String realStatePath)
	{
		super(message);
		this.realStatePath = realStatePath;
	}

	/**
	 * @param cause ����������-��������
	 * @param realStatePath ���� ������� ������������� �������� ������� ������������
	 */
	public UnsupportedStateException(Throwable cause, String realStatePath)
	{
		super(cause);
		this.realStatePath = realStatePath;
	}

	/**
	 * @param message ��������� ����������
	 * @param cause ����������-��������
	 * @param realStatePath ���� ������� ������������� �������� ������� ������������
	 */
	public UnsupportedStateException(String message, Throwable cause, String realStatePath)
	{
		super(message, cause);
		this.realStatePath = realStatePath;
	}

	/**
	 * @return ���� ������� ������������� �������� ������� ������������
	 */
	public String getRealStatePath()
	{
		return realStatePath;
	}
}
