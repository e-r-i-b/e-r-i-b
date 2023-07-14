package com.rssl.common.forms;

/**
 * ��������� ������ ��� ������ � ����������
 * @author Evgrafov
 * @ created 21.11.2006
 * @ $Author: erkin $
 * @ $Revision: 48660 $
 */
public class DocumentException extends Exception
{

	/**
	 * @param message ���������
	 */
	public DocumentException(String message)
	{
		super(message);
	}

	/**
	 * @param cause �������
	 */
	public DocumentException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message ���������
	 * @param cause �������
	 */
	public DocumentException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
