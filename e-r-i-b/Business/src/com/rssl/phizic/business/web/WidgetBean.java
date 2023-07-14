package com.rssl.phizic.business.web;

/**
 * @author Erkin
 * @ created 07.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Бин виджета для бд
 */
class WidgetBean
{
	/**
	 * Кодификатор виджета
	 * Например, Facebook1353317549083
	 */
	private String codename;

	/**
	 * Кодификатор виджет-дефиниции
	 * Например, Facebook
	 */
	private String definitionCodename;

	/**
	 * Идентификатор виджет-контейнера, содержащего виджет
	 */
	private Long webPageId;

	/**
	 * Содержимое виджета в json-формате
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
