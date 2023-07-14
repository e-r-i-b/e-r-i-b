package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Фильтр препроцессора смс-сообщений, для отправки смс-сообщений (выбрасывается UserErrorException)
 * @author Rtischeva
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class SendSmsPreprocessorFilter implements PreprocessorFilter
{
	private List<String> searchStrings;
	private String smsText;

	public String filter(String text)
	{
		String [] strings = new String[searchStrings.size()];
		
		if(StringUtils.indexOfAny(text.toUpperCase(), searchStrings.toArray(strings)) != -1)
			throw new UserErrorException(new TextMessage(smsText));

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

	public String getSmsText()
	{
		return smsText;
	}

	public void setSmsText(String smsText)
	{
		this.smsText = smsText;
	}
}
