package com.rssl.common.forms;

/**
 * @author mihaylov
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � ������������� �������� ��������� � ������ �����
 */
public class DocumentOfflineException extends DocumentLogicException
{
	public DocumentOfflineException(String message)
	{
		super(message);
	}

	public DocumentOfflineException(Throwable cause)
	{
		super(cause);
	}

	public DocumentOfflineException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
