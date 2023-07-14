package com.rssl.phizic.business.dictionaries.basketident;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

/**
 * Атрибуты для отображения идентификаторов корзины.
 *
 * @author bogdanov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */

public class AttributeForBasketIdentType extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private Long identId;
	private AttributeSystemId systemId;
	private String name;
	private AttributeDataType dataType;
	private String regexp;
	private boolean mandatory;

	public AttributeDataType getDataType()
	{
		return dataType;
	}

	public void setDataType(AttributeDataType dataType)
	{
		this.dataType = dataType;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getIdentId()
	{
		return identId;
	}

	public void setIdentId(Long identId)
	{
		this.identId = identId;
	}

	public boolean isMandatory()
	{
		return mandatory;
	}

	public void setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRegexp()
	{
		return regexp;
	}

	public void setRegexp(String regexp)
	{
		this.regexp = regexp;
	}

	public AttributeSystemId getSystemId()
	{
		return systemId;
	}

	public void setSystemId(AttributeSystemId systemId)
	{
		this.systemId = systemId;
	}
}
