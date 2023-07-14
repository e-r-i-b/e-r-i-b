package com.rssl.phizic.business;

import com.rssl.common.forms.DocumentException;

/**
 * @author akrenev
 * @ created 07.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ResourceNotFoundDocumentException extends DocumentException
{
	private Class resourceClass; 	 //����� ��������������� �������

	/**
	 * @param message ��������� ���������
	 * @param resourceClass ����� ��������������� �������
	 */
	public ResourceNotFoundDocumentException(String message, Class resourceClass)
	{
		super(message);
		this.resourceClass = resourceClass;
	}

	/**
	 * @param cause ����������
	 * @param resourceClass ����� ��������������� �������
	 */
	public ResourceNotFoundDocumentException(Throwable cause, Class resourceClass)
	{
		super(cause);
		this.resourceClass = resourceClass;
	}

	/**
	 * @param message ��������� ���������
	 * @param cause ����������
	 * @param resourceClass ����� ��������������� �������
	 */
	public ResourceNotFoundDocumentException(String message, Throwable cause, Class resourceClass)
	{
		super(message, cause);
		this.resourceClass = resourceClass;
	}

	public Class getResourceClass()
	{
		return resourceClass;
	}
}
