package com.rssl.phizic.business.ermb.sms.parser;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Фильтр препроцессора смс-сообщений для замены текста на другой текст
 * @author Rtischeva
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ReplacementPreprocessorFilter implements PreprocessorFilter
{

	private List<String> searchStrings;
	private String replacement;

	public String filter(String text)
	{
		String [] strings = new String[searchStrings.size()];
		if(StringUtils.indexOfAny(text.toUpperCase(), searchStrings.toArray(strings)) != -1)
			return replacement;
		
		else return text;
	}

	public List<String> getSearchStrings()
	{
		return searchStrings;
	}

	public void setSearchStrings(List<String> searchStrings)
	{
		this.searchStrings = searchStrings;
	}

	public String getReplacement()
	{
		return replacement;
	}

	public void setReplacement(String replacement)
	{
		this.replacement = replacement;
	}
}
