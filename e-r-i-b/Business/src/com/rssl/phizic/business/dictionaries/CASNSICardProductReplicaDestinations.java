package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;

/**
 * @author Mescheryakova
 * @ created 28.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class CASNSICardProductReplicaDestinations extends QueryReplicaDestinationBase
{
	protected CASNSICardProductReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.CASNSICardProduct.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public void add(DictionaryRecord newValue) throws GateException
	{
		CASNSICardProduct cardProduct = (CASNSICardProduct) newValue;
		if (cardProduct != null)
		{
			cardProduct.setLastUpdateDate(Calendar.getInstance());
			super.add(cardProduct);
		}
	}

	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
	{
		CASNSICardProduct cardProduct = (CASNSICardProduct) newValue;
		CASNSICardProduct oldCardProduct = (CASNSICardProduct) oldValue;
		if (cardProduct != null)
		{
			cardProduct.setId(oldCardProduct.getId());
			cardProduct.setLastUpdateDate(Calendar.getInstance());
			super.update(oldValue, newValue);
		}
	}

	public void remove(DictionaryRecord oldValue) throws GateException
	{
		// Ќе удал€ем записи по карточным продуктам.
	}
}
