package com.rssl.phizic.business.web;

/**
 * @author Erkin
 * @ created 07.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� ������� ��� ��
 */
class WidgetBean
{
	/**
	 * ����������� �������
	 * ��������, Facebook1353317549083
	 */
	private String codename;

	/**
	 * ����������� ������-���������
	 * ��������, Facebook
	 */
	private String definitionCodename;

	/**
	 * ������������� ������-����������, ����������� ������
	 */
	private Long webPageId;

	/**
	 * ���������� ������� � json-�������
	 */
	private String body;

	///////////////////////////////////////////////////////////////////////////

	String getCodename()
	{
		return codename;
	}

	void setCodename(String codename)
	{
		this.codename = codename;
	}

	String getDefinitionCodename()
	{
		return definitionCodename;
	}

	void setDefinitionCodename(String definitionCodename)
	{
		this.definitionCodename = definitionCodename;
	}

	Long getWebPageId()
	{
		return webPageId;
	}

	void setWebPageId(Long webPageId)
	{
		this.webPageId = webPageId;
	}

	String getBody()
	{
		return body;
	}

	void setBody(String body)
	{
		this.body = body;
	}
}
