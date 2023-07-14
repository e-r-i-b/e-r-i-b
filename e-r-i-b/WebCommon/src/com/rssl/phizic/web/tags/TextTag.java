package com.rssl.phizic.web.tags;

/**
 * Расширение класса TextTag
 * @author basharin
 * @ created 08.10.13
 * @ $Author$
 * @ $Revision$
 */

public class TextTag extends org.apache.struts.taglib.html.TextTag
{
	protected String autocomplete; //атрибут autocomplete

	public String getAutocomplete()
	{
		return autocomplete;
	}

	public void setAutocomplete(String autocomplete)
	{
		this.autocomplete = autocomplete;
	}

	protected void prepareOtherAttributes(StringBuffer sb)
	{
		if (autocomplete != null)
		{
			sb.append(" autocomplete=\"" + autocomplete + "\"");
		}
	}
}
