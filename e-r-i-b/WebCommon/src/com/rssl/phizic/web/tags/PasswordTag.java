package com.rssl.phizic.web.tags;

/**
 * ���������� ������ PasswordTag
 * @author basharin
 * @ created 08.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PasswordTag extends org.apache.struts.taglib.html.PasswordTag
{
	protected String autocomplete; //������� autocomplete

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

