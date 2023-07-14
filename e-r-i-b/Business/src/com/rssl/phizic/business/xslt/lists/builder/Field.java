package com.rssl.phizic.business.xslt.lists.builder;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author akrenev
 * @ created 12.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class Field
{
	private static final String OPEN_FIELD_TAG = "<field name=\"";
	private static final String CLOSE_FIELD_TAG = "</field>";
	private static final String CLOSE_ATTRIBUTES = "\">";
	private static final String EMPTY_PREFIX = "";
	private String name;
	private String value;
	private String prefix;

	/**
	 * ����������� ��� �������� ����
	 * @param name ��� ����
	 * @param value �������� ����
	 */
	public Field(String name, String value)
	{
		this.name = name;
		this.value = XmlHelper.escape(StringHelper.getEmptyIfNull(value));
		this.prefix = EMPTY_PREFIX;
	}

	/**
	 * ����������� ��� �������� ����
	 * @param name ��� ����
	 * @param value �������� ����
	 * @param prefix ������� ����� ����
	 */
	public Field(String name, String value, String prefix)
	{
		this.name = name;
		this.value = XmlHelper.escape(StringHelper.getEmptyIfNull(value));
		this.prefix = StringHelper.getEmptyIfNull(prefix);
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append(OPEN_FIELD_TAG);
		buf.append(prefix);
		buf.append(name);
		buf.append(CLOSE_ATTRIBUTES);
		buf.append(value);
		buf.append(CLOSE_FIELD_TAG);
		return buf.toString();
	}
}
