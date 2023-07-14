package com.rssl.phizic.auth.modes;

import java.util.List;
import java.util.ArrayList;

/**
 * Контейнер для нескольких исключений
 * @author niculichev
 * @ created 21.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class CompositeException extends Exception
{
	private static final String DELIMITER = "\r\n";
	private List<Exception> exceptions = new ArrayList<Exception>();

	public CompositeException()
	{
	}

	public CompositeException(Exception... exceptions)
	{
		for(Exception exception : exceptions)
			addException(exception);
	}

	public void addException(Exception e)
	{
		exceptions.add(e);
	}

	public String getMessage()
	{
		StringBuilder builder = new StringBuilder();
		for(Exception exception : exceptions)
			builder.append(exception.getMessage()).append(DELIMITER);

		return builder.toString();
	}

	public List<Exception> getExceptions()
	{
		return exceptions;
	}
	
	public void clear()
	{
		exceptions.clear();
	}

	public boolean isEmpty()
	{
		return exceptions.isEmpty();
	}

}
