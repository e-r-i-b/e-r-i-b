package com.rssl.common.forms;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������ ��� ������ � ����������
 */
public class TemporalDocumentException extends DocumentException
{
	/**
	 * @param message ���������
	 */
	public TemporalDocumentException(String message)
	{
		super(message);
	}

	/**
	 * @param cause �������
	 */
	public TemporalDocumentException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message ���������
	 * @param cause �������
	 */
	public TemporalDocumentException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
