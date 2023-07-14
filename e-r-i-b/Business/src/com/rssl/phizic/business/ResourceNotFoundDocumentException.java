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
	private Class resourceClass; 	 //класс несуществующего объекта

	/**
	 * @param message выводимое сообщение
	 * @param resourceClass класс несуществующего объекта
	 */
	public ResourceNotFoundDocumentException(String message, Class resourceClass)
	{
		super(message);
		this.resourceClass = resourceClass;
	}

	/**
	 * @param cause исключение
	 * @param resourceClass класс несуществующего объекта
	 */
	public ResourceNotFoundDocumentException(Throwable cause, Class resourceClass)
	{
		super(cause);
		this.resourceClass = resourceClass;
	}

	/**
	 * @param message выводимое сообщение
	 * @param cause исключение
	 * @param resourceClass класс несуществующего объекта
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
