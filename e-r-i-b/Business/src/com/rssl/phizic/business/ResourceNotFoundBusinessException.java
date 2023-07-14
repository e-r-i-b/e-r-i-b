package com.rssl.phizic.business;

/** Исключение для обозначения ошибок доступа к каким либо несуществующим ресурсам: счетам, картам, новостям и т.д.
 * @author akrenev
 * @ created 15.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class ResourceNotFoundBusinessException extends BusinessException
{
	private Class resourceClass; //класс несуществующего объекта
	private String caseOfException; //класс несуществующего объекта
	/**
	 * @param message выводимое сообщение
	 * @param resourceClass класс несуществующего объекта
	 */
	public ResourceNotFoundBusinessException(String message, Class resourceClass)
	{
		this(message, resourceClass, null);
	}

	/**
	 * @param message выводимое сообщение
	 * @param resourceClass класс несуществующего объекта
	*/
	public ResourceNotFoundBusinessException(String message, Class resourceClass, String caseOfException)
	{
		super(message);
		this.resourceClass = resourceClass;
		this.caseOfException = caseOfException;
	}

	/**
	 * @param cause исключение
	 */
	public ResourceNotFoundBusinessException(ResourceNotFoundDocumentException cause)
	{
		this(cause.getMessage(), cause, cause.getResourceClass());
	}

	/**
	 * @param cause исключение
	 * @param resourceClass класс несуществующего объекта
	 */
	public ResourceNotFoundBusinessException(Throwable cause, Class resourceClass)
	{
		super(cause);
		this.resourceClass = resourceClass;
	}

	/**
	 * @param message выводимое сообщение
	 * @param cause исключение
	 * @param resourceClass класс несуществующего объекта
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
