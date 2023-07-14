package com.rssl.phizic.business.fields;

/**
 * @author hudyakov
 * @ created 29.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class FieldValues
{
	private Long id;
	private String value;
	private FieldDescription description;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public FieldDescription getDescription()
	{
		return description;
	}

	public void setDescription(FieldDescription description)
	{
		this.description = description;
	}
}
