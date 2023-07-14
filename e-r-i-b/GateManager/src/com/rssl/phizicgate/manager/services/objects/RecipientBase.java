package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.Service;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class RecipientBase implements Recipient
{
	Recipient delegate;

	public RecipientBase(Recipient delegate)
	{
		this.delegate = delegate;
	}

	public Service getService()
	{
		return delegate.getService();
	}

	public String getName()
	{
		return delegate.getName();
	}

	public String getDescription()
	{
		return delegate.getDescription();
	}

	public Boolean isMain()
	{
		return delegate.isMain();
	}

	public void updateFrom(DictionaryRecord that)
	{
		throw new UnsupportedOperationException();
	}
}
