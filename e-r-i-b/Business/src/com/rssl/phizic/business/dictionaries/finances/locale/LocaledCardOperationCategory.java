package com.rssl.phizic.business.dictionaries.finances.locale;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author muhin
 * @ created 27.05.15
 * @ $Author$
 * @ $Revision$
 */
public class LocaledCardOperationCategory  extends CardOperationCategory
{
	private Set<CardOperationCategoryResources> resources;

	@Override
	public String getName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getName();
		return super.getName();
	}
}
