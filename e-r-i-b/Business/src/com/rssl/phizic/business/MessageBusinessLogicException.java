package com.rssl.phizic.business;

/**
 * ���������� ��� ������ ��������� ������������ �
 * ��������� �������
 *
 * @author hudyakov
 * @ created 29.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class MessageBusinessLogicException extends BusinessLogicException
{
	/**
	 * ctor
	 * @param message - ���������
	 */
	public MessageBusinessLogicException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause - �������
	 */
	public MessageBusinessLogicException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message - ���������
	 * @param cause   - �������
	 */
	public MessageBusinessLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
