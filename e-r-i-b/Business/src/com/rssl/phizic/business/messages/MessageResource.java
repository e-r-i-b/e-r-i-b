package com.rssl.phizic.business.messages;

import java.util.Calendar;

/**
 * @author  Balovtsev
 * @version 05.04.13 8:30
 */
public abstract class MessageResource
{
	private Long     id;
	private String   key;
	private String   text;
	private String   description;
	private String   variables;
	private Long     employeeLoginId;
	private String   previousText;
	private Calendar lastModified;

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

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getVariables()
	{
		return variables;
	}

	public void setVariables(String variables)
	{
		this.variables = variables;
	}

	public Long getEmployeeLoginId()
	{
		return employeeLoginId;
	}

	public void setEmployeeLoginId(Long employeeLoginId)
	{
		this.employeeLoginId = employeeLoginId;
	}

	public String getPreviousText()
	{
		return previousText;
	}

	public void setPreviousText(String previousText)
	{
		this.previousText = previousText;
	}

	public Calendar getLastModified()
	{
		return lastModified;
	}

	public void setLastModified(Calendar lastModified)
	{
		this.lastModified = lastModified;
	}
}
