package com.rssl.phizic.security;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.common.types.TextMessage;

/**
 * @author Erkin
 * @ created 23.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ���������, ���������� ��� �������������
 */
@Immutable
@PlainOldJavaObject
public class ConfirmCodeMessage extends TextMessage
{
	private final String confirmCode;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param confirmCode - ��� ������������� (never null, never empty)
	 * @param text - ����� ��������� ��� �������� �������, ���������� ��� ������������� <confirmCode>
	 * @param textToLog ����� ��������� ��� �����������, �� ���������� ��� ������������� <confirmCode>
	 */
	public ConfirmCodeMessage(String confirmCode, String text, String textToLog)
	{
		super(text, textToLog);
		this.confirmCode = confirmCode;
	}

	/**
	 * @return ��� ������������� (never null, never empty)
	 */
	public String getConfirmCode()
	{
		return confirmCode;
	}
}
