package com.rssl.phizgate.common.routable;

import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author akrenev
 * @ created 29.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class RecipientImpl implements Recipient, Routable
{
	private Service service;
	private String name;
	private Boolean main;
	private Comparable synchKey;
	private String description;

	public Service getService()
	{
		return service;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public Boolean isMain()
	{
		return main;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void setService(Service service)
	{
		this.service = service;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setMain(Boolean main)
	{
		this.main = main;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public void updateFrom(DictionaryRecord that)
	{
		BeanHelper.copyProperties(this, that);
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

	public void setDescription(String description)
	{
		this.description = description;
	}
}
