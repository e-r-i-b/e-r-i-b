package com.rssl.phizic.business.fields;

/**
 * Краткое описания полей для мобильной версии
 @author Pankin
 @ created 29.11.2010
 @ $Author$
 @ $Revision$
 */
public class FieldDescriptionShortcut
{
	private Long id;
	private String externalId;
	private String name;
	private String defaultValue;
	private Long recipientId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public Long getRecipientId()
	{
		return recipientId;
	}

	public void setRecipientId(Long recipientId)
	{
		this.recipientId = recipientId;
	}
}
