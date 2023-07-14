package com.rssl.common.forms;

/**
 * ���������� ��� �������� ��������� ������������ �
 * ��������� ������� ����������
 *
 * @author hudyakov
 * @ created 29.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class MessageDocumentLogicException extends DocumentLogicException
{
	/**
	 * ctor
	 * @param message - ���������
	 */
	public MessageDocumentLogicException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause - �������
	 */
	public MessageDocumentLogicException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message - ���������
	 * @param cause   - �������
	 */
	public MessageDocumentLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
