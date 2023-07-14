package com.rssl.phizicgate.rsV55.dictionaries;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.CurrencyOpTypeGateService;
import com.rssl.phizic.gate.dictionaries.CurrencyOperationType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;


import java.util.List;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyOpTypeGateServiceImpl extends AbstractService implements CurrencyOpTypeGateService
{
	public CurrencyOpTypeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<CurrencyOperationType> getAll(int firstResult, int maxResults) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public	List<CurrencyOperationType> getAll(CurrencyOperationType template, int firstResult, int listLimit) throws GateException
	{
	   throw new UnsupportedOperationException();
	}
}
