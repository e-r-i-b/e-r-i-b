package com.rssl.phizic.business.descriptions;

/**
 * @author niculichev
 * @ created 08.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedDescriptionData
{
	private Long id; // �������������
	private String name; // ������� ���
	private String content; // ����������

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
}
