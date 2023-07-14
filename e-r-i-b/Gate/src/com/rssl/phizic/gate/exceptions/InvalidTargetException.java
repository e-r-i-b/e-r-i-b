package com.rssl.phizic.gate.exceptions;

/**
 * @author krenev
 * @ created 01.10.2010
 * @ $Author$
 * @ $Revision$
 * ���������� ��������������� � ���, ��� �������� �� ��������� � �������� ������� �
 * �� ����� ���� ���������� ������� �������.
 */
public class InvalidTargetException extends GateException
{
	public InvalidTargetException(String message)
	{
		super(message);
	}

	public InvalidTargetException(Throwable couse)
	{
		super(couse);
	}
}
