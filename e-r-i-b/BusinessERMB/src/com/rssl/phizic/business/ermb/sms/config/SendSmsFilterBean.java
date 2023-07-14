package com.rssl.phizic.business.ermb.sms.config;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * Бин для XML-маппинга фильтра отправки смс-сообщений препроцессора
 * @author Rtischeva
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "SendSmsFilter")
@XmlAccessorType(XmlAccessType.NONE)
class SendSmsFilterBean
{
	@XmlElementWrapper(name = "search-strings", required = true)	
	@XmlElement(name= "string")
	private List<String> searchStrings;

	@XmlElement(name= "sms-text")
	private String smsText;

	////////////////////////////////////////////////////////////

	List<String> getSearchStrings()
	{
		return searchStrings;
	}

	void setSearchStrings(List<String> searchStrings)
	{
		this.searchStrings = searchStrings;
	}

	String getSmsText()
	{
		return smsText;
	}

	void setSmsText(String smsText)
	{
		this.smsText = smsText;
	}
}
