package com.rssl.phizic.business.sms;

import org.apache.commons.lang.StringUtils;

/**
 * @author Balovtsev
 * @version 29.05.13 12:37
 */
final class MessageResourceVariablesReaderUtil
{
	public static boolean hasUnclosed(String string, String first, String second)
	{
		return StringUtils.countMatches(string, first) != StringUtils.countMatches(string, second);
	}

	public static boolean hasUnclosed(String string, String character)
	{
		return StringUtils.countMatches(string, character) % 2 != 0;
	}
}
