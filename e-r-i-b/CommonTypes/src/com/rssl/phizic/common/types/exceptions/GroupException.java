package com.rssl.phizic.common.types.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Barinov
 * @ created 20.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class GroupException extends SystemException
{
	private final Throwable[] exceptions;

	public GroupException(Throwable... exceptions)
	{
		this.exceptions = exceptions;
	}

	public GroupException(List<? extends Throwable> exceptions)
	{
		this.exceptions = exceptions.toArray(new Throwable[exceptions.size()]);
	}

	public void printStackTrace()
	{
		for (Throwable e : exceptions)
			if(e!=null)
				e.printStackTrace();
	}

	public String getMessage()
	{
		String res = "Group exception:\r\n";
		for (Throwable e : exceptions)
			res += "\t" + e.getMessage();
		return res;
	}

	public void printStackTrace(PrintStream s)
	{
		for (Throwable e : exceptions)
			e.printStackTrace(s);
	}

	public void printStackTrace(PrintWriter s)
	{
		for (Throwable e : exceptions)
			e.printStackTrace(s);
	}
}
