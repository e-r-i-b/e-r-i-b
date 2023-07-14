package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2789 $
 */
class FilteredEntityListBuilder
{
	private StringBuffer buf = new StringBuffer();

	public void openListDataTag()
	{
		buf.append("<list-data>");
	}

	public void closeListDataTag()
	{
		buf.append("</list-data>");
	}

	public void openFilterDataTag()
	{
		buf.append("<filter-data>");
	}

	public void closeFilterDataTag()
	{
		buf.append("</filter-data>");
	}

	public void closeEntityListTag()
	{
		buf.append("</entity-list>");
	}

	public void openEntityListTag()
	{
		buf.append("<entity-list>");
	}

	public void closeEntityTag()
	{
		buf.append("</entity>");
	}

	public void openEntityTag(String key, boolean selected)
	{

		buf.append("<entity key=\"");
		buf.append(XmlHelper.escape(key));
		buf.append("\"");
		if(selected) buf.append(" selected=\"true\"");
		buf.append(">");
	}

	public void appentField(String name, String value)
	{
		buf.append("<");
		buf.append(name);
		buf.append(">");
		buf.append(XmlHelper.escape(value));
		buf.append("</");
		buf.append(name);
		buf.append(">");
	}

	public String toString()
	{
		return buf.toString();
	}
}