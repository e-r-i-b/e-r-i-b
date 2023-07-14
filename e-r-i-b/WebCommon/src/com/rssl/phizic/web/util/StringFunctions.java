package com.rssl.phizic.web.util;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import sun.security.action.GetPropertyAction;

import java.net.URLEncoder;
import java.security.AccessController;

/**
 * @author Evgrafov
 * @ created 14.08.2006
 * @ $Author: basharin $
 * @ $Revision: 54970 $
 */

public class StringFunctions
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static String defaultEncoding;

	static
	{
		defaultEncoding = (String) AccessController.doPrivileged(
				new GetPropertyAction("file.encoding")
		);
	}

	public static String substring(String value, int beginindex, int endindex)
	{
		try
		{
			return value.substring(beginindex, endindex > value.length() ? value.length() : endindex);
		}
		catch (Exception e)
		{
			log.error("Ошибка получения подстроки из строки", e);
			return "";
		}
	}

	public static String substring(String value, int beginindex)
	{
		return value.substring(beginindex);
	}

	public static String substring(String value, String character, int position)
	{
		try
		{
			if(value != null && value.contains(character) && value.split(character).length >= position)
			{
				return value.split(character)[position];
			}
			return "";
		}
		catch (Exception e)
		{
			log.error("Ошибка получения подстроки из строки", e);
			return "";
		}
	}

	public static String stringEncode(String value)
	{
		if(value == null)
			return "";
		try
		{
			return URLEncoder.encode(value, defaultEncoding);
		}
		catch(Exception e)
		{
			log.error("Ошибка смены кодировки строки", e);
			return "";
		}
	}

	/**
	 * заменяем кавычки на &quot;
	 * @param value - строка для замены
	 * @return
	 */
	public static String replaceQuotes(String value)
	{
		if (value == null || value.length() == 0)
			return value;
		else
			return value.replace("\"", "&quot;").replace("'", "\\'");
	}
}