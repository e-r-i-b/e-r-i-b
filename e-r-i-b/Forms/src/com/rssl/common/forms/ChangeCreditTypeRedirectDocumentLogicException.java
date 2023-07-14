package com.rssl.common.forms;

/**
 * @author Mescheryakova
 * @ created 27.05.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������, ���������� � ��������,
 * ���� ��������� ����� � �������������� ������� ������ ������������� �� ��������� �����
 * � ���� ������ ����� ������� �������� �� ����������� ����� �������������� ����������������� �������
 */

public class ChangeCreditTypeRedirectDocumentLogicException extends RedirectDocumentLogicException
{

	/** @param message ��������� */
	public ChangeCreditTypeRedirectDocumentLogicException(String message)
	{
		super(message);
	}

	/** @param cause ������� */
	public ChangeCreditTypeRedirectDocumentLogicException(Throwable cause)
	{
		super(cause == null ? null : cause.getMessage(), cause);
	}

	/**
	 * @param message ���������
	 * @param cause   �������
	 */
	public ChangeCreditTypeRedirectDocumentLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
