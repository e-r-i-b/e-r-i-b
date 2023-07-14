package com.rssl.phizgate.common.routable;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author akrenev
 * @ created 21.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class BillingImpl implements com.rssl.phizic.gate.dictionaries.billing.Billing, Routable
{
	private String name;
	private Comparable synchKey;

	public String getName()
	{
		return name;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void updateFrom(DictionaryRecord that)
	{
		BeanHelper.copyProperties(this, that);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public void storeRouteInfo(String info)
	{
		synchKey = IDHelper.storeRouteInfo((String) synchKey, info);
	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo((String)synchKey);
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo((String) synchKey);
		setSynchKey(IDHelper.restoreOriginalId((String) synchKey));
		return info;
	}
}
