package com.rssl.phizic.utils.xml;

import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * @author hudyakov
 * @ created 14.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class XmlEntityBuilder
{
	private StringBuffer buf = new StringBuffer();

	public XmlEntityBuilder openEntityTag(String key)
	{
		buf.append("<").append(key).append(">");
		return this;
	}

	public XmlEntityBuilder openEntityTag(String key, Map<String, String> attrs)
	{
		buf.append("<").append(key);
		if(MapUtils.isNotEmpty(attrs))
		{
			for(String keyAttr : attrs.keySet())
				buf.append(" ").append(XmlHelper.escape(keyAttr)).append('=').append("\"").append(XmlHelper.escape(attrs.get(keyAttr))).append("\"");
		}

		buf.append(">");
		return this;
	}

	public XmlEntityBuilder closeEntityTag(String key)
	{
		buf.append("</").append(key).append(">");
		return this;
	}

	public XmlEntityBuilder createEntityTag(String key, String value)
	{
		openEntityTag(key);
		buf.append(XmlHelper.escape(value));
		closeEntityTag(key);
		return this;
	}

	public XmlEntityBuilder createEntityTag(String key, String value, Map<String, String> attrs)
	{
		openEntityTag(key, attrs);
		buf.append(XmlHelper.escape(value));
		closeEntityTag(key);
		return this;
	}

	public XmlEntityBuilder append(String content)
	{
		buf.append(content);
		return this;
	}

	public String toString()
	{
		return buf.toString();
	}
}