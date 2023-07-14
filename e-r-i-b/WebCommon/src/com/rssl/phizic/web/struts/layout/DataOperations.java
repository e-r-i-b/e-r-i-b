package com.rssl.phizic.web.struts.layout;

import java.util.List;

/**
 * @author Krenev
 * @ created 15.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class DataOperations
{
	public static Object sum(List<String> args)
	{
		if (args == null)
		{
			return null;
		}
		double result = 0;
		for (String o : args)
		{
			result += new Double(o);
		}
		return result;
	}
}
