package com.rssl.auth.csasocial.exceptions;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;

/**
 * ����������, ����������� � ������, ���� ��� �� ���� ���������������� �������
 * @author Dorzhinov
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 */
public class FailureIdentificationFrontException extends FrontLogicException
{
	public FailureIdentificationFrontException()
	{
	}

	public FailureIdentificationFrontException(String message)
	{
		super(message);
	}

	public FailureIdentificationFrontException(Throwable cause)
	{
		super(cause);
	}

	public FailureIdentificationFrontException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
