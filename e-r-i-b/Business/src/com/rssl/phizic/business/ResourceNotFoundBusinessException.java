package com.rssl.phizic.business;

/** ���������� ��� ����������� ������ ������� � ����� ���� �������������� ��������: ������, ������, �������� � �.�.
 * @author akrenev
 * @ created 15.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class ResourceNotFoundBusinessException extends BusinessException
{
	private Class resourceClass; //����� ��������������� �������
	private String caseOfException; //����� ��������������� �������
	/**
	 * @param message ��������� ���������
	 * @param resourceClass ����� ��������������� �������
	 */
	public ResourceNotFoundBusinessException(String message, Class resourceClass)
	{
		this(message, resourceClass, null);
	}

	/**
	 * @param message ��������� ���������
	 * @param resourceClass ����� ��������������� �������
	*/
	public ResourceNotFoundBusinessException(String message, Class resourceClass, String caseOfException)
	{
		super(message);
		this.resourceClass = resourceClass;
		this.caseOfException = caseOfException;
	}

	/**
	 * @param cause ����������
	 */
	public ResourceNotFoundBusinessException(ResourceNotFoundDocumentException cause)
	{
		this(cause.getMessage(), cause, cause.getResourceClass());
	}

	/**
	 * @param cause ����������
	 * @param resourceClass ����� ��������������� �������
	 */
	public ResourceNotFoundBusinessException(Throwable cause, Class resourceClass)
	{
		super(cause);
		this.resourceClass = resourceClass;
	}

	/**
	 * @param message ��������� ���������
	 * @param cause ����������
	 * @param resourceClass ����� ��������������� �������
	 */
	public ResourceNotFoundBusinessException(String message, Throwable cause, Class resourceClass)
	{
		super(message, cause);
		this.resourceClass = resourceClass;
	}

	public Class getResourceClass()
	{
		return resourceClass;
	}

	public String getCaseOfException()
	{
		return caseOfException;
	}
}
