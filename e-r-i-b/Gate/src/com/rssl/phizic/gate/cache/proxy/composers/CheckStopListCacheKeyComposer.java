package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Erkin
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 *  омпозер дл€ check-методов StopListService
 */
public class CheckStopListCacheKeyComposer extends AbstractCacheKeyComposer
{

	///////////////////////////////////////////////////////////////////////////

	public Serializable getKey(Object[] args, String params)
	{
		// ¬се аргументы ключевые
		List<String> chunks = new ArrayList<String>(args.length);
		for (Object arg : args)
		{
			if (arg == null)
				chunks.add("null");
			else chunks.add(String.format("%s'%s'", getArgumentClassCode(arg), getArgumentValue(arg)));
		}
		return StringUtils.join(chunks, ",");
	}

	private String getArgumentClassCode(Object arg)
	{
		// строки не кодируем
		if (arg instanceof String)
			return "";
		// даты не кодируем
		if (arg instanceof Calendar)
			return "";

		Class clazz = arg.getClass();

		// перечислени€ не кодируем
		if (clazz.isEnum())
			return "";
		
		// остальное по короткому имени класса
		return clazz.getSimpleName();
	}

	private String getArgumentValue(Object arg)
	{
		if (arg instanceof Calendar)
			return String.format("%1$tF %1$tT %1$tS", ((Calendar)arg).getTime());

		return arg.toString();
	}
}
