package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 23.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class AddFavouriteLinkForm extends ActionFormBase
{
	private String name; // ��� ������� ������
	private String typeFormatLink; // ��� ������� ������
	private String pattern; // ������ �����
	private String url; // ��� ������� ������
	private String reference; //��� ��������� ������ � �����������

	private String message; //���������, ������������ ������������ ����� ������� �������� �������� � ���������.

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPattern()
	{
		return pattern;
	}

	public void setPattern(String pattern)
	{
		this.pattern = pattern;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getReference()
	{
		return reference;
	}

	public void setReference(String reference)
	{
		this.reference = reference;
	}

	public String getTypeFormatLink()
	{
		return typeFormatLink;
	}

	public void setTypeFormatLink(String typeFormatLink)
	{
		this.typeFormatLink = typeFormatLink;
	}
}
