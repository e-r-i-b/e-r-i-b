package com.rssl.phizgate.ext.sbrf.technobreaks.locale;

import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author komarov
 * @ created 12.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class LocaledTechnoBreak extends TechnoBreak
{
	private Set<TechnoBreakResources> resources;

	@Override
	public String getMessage()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getMessage();
		return super.getMessage();
	}
}
