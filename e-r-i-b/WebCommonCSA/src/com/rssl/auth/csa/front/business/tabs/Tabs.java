package com.rssl.auth.csa.front.business.tabs;

import java.io.Serializable;

/**
 * @author osminin
 * @ created 13.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * �������/������� ������������ �� �������� ����� � �������
 */
public class Tabs implements Serializable
{
	private long id;
	private String tabName;     // �������� �������
	private String tabText;     // ����� �������

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getTabName()
	{
		return tabName;
	}

	public void setTabName(String tabName)
	{
		this.tabName = tabName;
	}

	public String getTabText()
	{
		return tabText;
	}

	public void setTabText(String tabText)
	{
		this.tabText = tabText;
	}

	public String toJSON()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append("\"name\":\"").append(tabName).append("\"");
		builder.append(",");
		builder.append("\"text\":\"").append(tabText).append("\"");
		builder.append("}");
		return builder.toString();
	}
}
