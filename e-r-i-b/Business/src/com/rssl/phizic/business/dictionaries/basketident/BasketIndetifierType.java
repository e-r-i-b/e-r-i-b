package com.rssl.phizic.business.dictionaries.basketident;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.business.userDocuments.DocumentType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Тип для отображения инентификатора корзины.
 *
 * @author bogdanov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */

public class BasketIndetifierType extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private DocumentType systemId;
	private String name;
	private Map<String, AttributeForBasketIdentType> attributes = new HashMap<String, AttributeForBasketIdentType>();

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

	public DocumentType getSystemId()
	{
		return systemId;
	}

	public void setSystemId(DocumentType systemId)
	{
		this.systemId = systemId;
	}

	public Map<String, AttributeForBasketIdentType> getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Map<String, AttributeForBasketIdentType> attributes)
	{
		this.attributes = attributes;
	}
}
