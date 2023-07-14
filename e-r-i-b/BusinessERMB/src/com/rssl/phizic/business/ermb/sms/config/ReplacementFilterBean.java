package com.rssl.phizic.business.ermb.sms.config;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * Бин для XML-маппинга фильтра замены 
 * @author Rtischeva
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "ReplacementFilter")
@XmlAccessorType(XmlAccessType.NONE)
class ReplacementFilterBean
{
	@XmlElementWrapper(name = "search-strings", required = true)
    @XmlElement(name = "string")
	private List<String> searchStrings;

	@XmlElement(name= "replacement")
	private String replacement;

	/////////////////////////////////////////////////////////////

	List<String> getSearchStrings()
	{
		return searchStrings;
	}

	void setSearchStrings(List<String> searchStrings)
	{
		this.searchStrings = searchStrings;
	}

	String getReplacement()
	{
		return replacement;
	}

	void setReplacement(String replacement)
	{
		this.replacement = replacement;
	}
}
