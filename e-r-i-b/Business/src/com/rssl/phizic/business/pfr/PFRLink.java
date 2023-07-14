package com.rssl.phizic.business.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;

/**
 * Карточка ПФР
 * @author Dorzhinov
 * @ created 18.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class PFRLink extends EditableExternalResourceLink
{
	public static final String CODE_PREFIX = "pfr";
	
	private Long id;

	{
		setName("Карточка ПФР");
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public void reset()
	{
	}

	public Object getValue() throws BusinessException, BusinessLogicException
	{
		return null;
	}

	public ResourceType getResourceType()
	{
		return ResourceType.NULL;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$pfrName:" + this.getId();
	}
}
