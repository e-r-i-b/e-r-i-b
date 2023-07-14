package com.rssl.phizic.common.types.exceptions;

/**
 * @author Erkin
 * @ created 07.04.2013
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.common.types.TextMessage;

/**
 * ����������, ��������� ������������ ��������� ������������.
 * ������ ������������� ��� ��������, ����������� ���������� ���������� ������� ������.
 * �� ������������ �������-������ �������������� ��������� (�.�. ����������������).
 * ������ ���� ����������� �� "������� ������" � �������� � ������ ������������.
 * ��� ������������ ���������� �������� ��� ��������� � ����������� ������� ������
 * � (��������) ��������� �� � �����������.
 * �������:
 * - ������ ����� (������ ���������)
 * - ������, ������� ��� ������ ������������ (�� ������� �������)
 */
public class UserErrorException extends RuntimeException
{
	private TextMessage textMessage;

	public UserErrorException(TextMessage textMessage)
	{
		super(textMessage.getText());
		this.textMessage = textMessage;
	}

	/**
	 * ctor
	 * @param textMessage
	 * @param cause
	 */
	public UserErrorException(TextMessage textMessage, Throwable cause)
	{
		super(textMessage.getText(), cause);
		this.textMessage = textMessage;
	}

	public UserErrorException(Throwable cause)
	{
		super(cause == null ? null : cause.getMessage(), cause);
		this.textMessage = new TextMessage(cause == null ? null : cause.getMessage());
	}

	public TextMessage getTextMessage()
	{
		return textMessage;
	}
}
