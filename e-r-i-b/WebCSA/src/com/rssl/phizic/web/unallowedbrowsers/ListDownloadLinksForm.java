package com.rssl.phizic.web.unallowedbrowsers;

import org.apache.struts.action.ActionForm;

/**
 * ����� ������ �� ���������� ������� ��� ��������� ���������, �������� ����� �������.
 * @ author: Vagin
 * @ created: 28.11.2012
 * @ $Author
 * @ $Revision
 */
public class ListDownloadLinksForm extends ActionForm
{
	private String link;

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}
}
