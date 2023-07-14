package com.rssl.phizic.business.ermb.provider;

import com.rssl.phizic.business.fields.FieldDescription;

/**
 * User: Moshenko
 * Date: 20.05.2013
 * Time: 12:15:29
 * поле поставщика услуг в разрезе алиаса
 */
public class ServiceProviderSmsAliasField
{
	private long id;
	private String value;           //новое значение
	private boolean editable;       //признак редактируемости
	private FieldDescription field; //исходное поле

	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
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

	public FieldDescription getField()
	{
		return field;
	}

	public void setField(FieldDescription field)
	{
		this.field = field;
	}
}
