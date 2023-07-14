package com.rssl.auth.csa.back.servises.operations.confirmations;

import com.rssl.auth.csa.back.servises.Password;

import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

/**
 * @author akrenev
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ����������� ������
 */

public class DisposablePassword extends Password
{
	private Long confirmErrors = 0L;

	/**
	 * ����������� (��� ���������)
	 */
	public DisposablePassword()
	{}

	/**
	 * ����������� ��������� ���������� � ������
	 * @param code ������
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public DisposablePassword(String code) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		super(code);
	}

	/**
	 * @return ���������� ������ �������������
	 */
	public Long getConfirmErrors()
	{
		return confirmErrors;
	}

	/**
	 * ������ ���������� ������ �������������
	 * @param confirmErrors ���������� ������ �������������
	 */
	public void setConfirmErrors(Long confirmErrors)
	{
		this.confirmErrors = confirmErrors;
	}

	/**
	 * ��������� ���������� ������ ������������� 
	 */
	public void incErrorCount()
	{
		confirmErrors++;
	}
}
