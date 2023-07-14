package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.billing.Billing;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class BillingBase implements Billing
{
	Billing delegate;

	public BillingBase(Billing delegate)
	{
		this.delegate = delegate;
	}

	public String getName()
	{
		return delegate.getName();
	}

	public void updateFrom(DictionaryRecord that)
	{
		throw new UnsupportedOperationException();
	}
}
