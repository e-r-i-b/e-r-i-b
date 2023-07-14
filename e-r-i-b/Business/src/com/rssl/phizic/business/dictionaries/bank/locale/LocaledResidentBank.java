package com.rssl.phizic.business.dictionaries.bank.locale;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author koptyaev
 * @ created 21.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class LocaledResidentBank extends ResidentBank
{
	@SuppressWarnings("UnusedDeclaration")
	private Set<ResidentBankResources> resources;

	@Override
	public String getShortName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getShortName();
		return super.getShortName();
	}

	@Override
	public String getAddress()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getAddress();
		return super.getAddress();
	}

	@Override
	public String getName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getName();
		return super.getName();
	}

	@Override
	public String getPlace()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getPlace();
		return super.getPlace();
	}

}
