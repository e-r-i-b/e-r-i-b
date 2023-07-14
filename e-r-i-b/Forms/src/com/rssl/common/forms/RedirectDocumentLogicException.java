package com.rssl.common.forms;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ������ ��� ������ � ���������� (������ ������������),
 * ������� ������� �������� �� ������� ����������
 */
public class RedirectDocumentLogicException extends DocumentLogicException
{

	/** @param message ��������� */
	public RedirectDocumentLogicException(String message)
	{
		super(message);
	}

	/** @param cause ������� */
	public RedirectDocumentLogicException(Throwable cause)
	{
		super(cause == null ? null : cause.getMessage(), cause);
	}

	/**
	 * @param message ���������
	 * @param cause   �������
	 */
	public RedirectDocumentLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
