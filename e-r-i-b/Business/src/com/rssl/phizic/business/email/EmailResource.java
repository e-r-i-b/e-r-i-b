package com.rssl.phizic.business.email;

import java.util.Calendar;

/**
 * шаблон email-оповещения
 * @author lukina
 * @ created 16.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class EmailResource
{
	private Long     id;
	private String   key;
	private String   description;
	private String   theme;
	private String   plainText;
	private String   htmlText;
	private Calendar lastModified;
	private String   previousPlainText;
	private String   previousHtmlText;
	private String   variables;
	private Long     employeeLoginId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getTheme()
	{
		return theme;
	}

	public void setTheme(String theme)
	{
		this.theme = theme;
	}

	public String getPlainText()
	{
		return plainText;
	}

	public void setPlainText(String plainText)
	{
		this.plainText = plainText;
	}

	public String getHtmlText()
	{
		return htmlText;
	}

	public void setHtmlText(String htmlText)
	{
		this.htmlText = htmlText;
	}

	public Calendar getLastModified()
	{
		return lastModified;
	}

	public void setLastModified(Calendar lastModified)
	{
		this.lastModified = lastModified;
	}

	public String getPreviousPlainText()
	{
		return previousPlainText;
	}

	public void setPreviousPlainText(String previousPlainText)
	{
		this.previousPlainText = previousPlainText;
	}

	public String getPreviousHtmlText()
	{
		return previousHtmlText;
	}

	public void setPreviousHtmlText(String previousHtmlText)
	{
		this.previousHtmlText = previousHtmlText;
	}

	public Long getEmployeeLoginId()
	{
		return employeeLoginId;
	}

	public void setEmployeeLoginId(Long employeeLoginId)
	{
		this.employeeLoginId = employeeLoginId;
	}

	public String getVariables()
	{
		return variables;
	}

	public void setVariables(String variables)
	{
		this.variables = variables;
	}
}
