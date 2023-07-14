package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author osminin
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ �������������� ��� ��� ����������� ������������ - ����� ������ ���� �������� � ��������
 */
public class CardNotActiveAndMainException extends LogicException
{
	/**
	 * �����������
	 * @param message ���������
	 */
	public CardNotActiveAndMainException(String message)
	{
		super(message);
	}
}
