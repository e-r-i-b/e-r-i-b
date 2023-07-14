package com.rssl.phizicgate.enisey;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.utils.BeanHelper;

/**
 * @author gladishev
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class RecipientImpl implements Recipient
{
	private Service service;
	private String name;
	private Boolean main;
	private Comparable synchKey;
	private String description;

	public RecipientImpl()
	{

	}

	public RecipientImpl(Service service, String name, String synchKey)
	{
		this.service = service;
		this.name = name;
		this.synchKey = synchKey;
	}

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

	public void updateFrom(DictionaryRecord that)
	{
		BeanHelper.copyProperties(this, that);
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

	public void setDescription(String description)
	{
		this.description = description;
	}
}
