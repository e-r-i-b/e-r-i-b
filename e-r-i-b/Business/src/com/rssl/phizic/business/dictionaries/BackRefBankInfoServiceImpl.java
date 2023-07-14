package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.BackRefBankInfoService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.business.BusinessException;

/**
 * @author khudyakov
 * @ created 22.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class BackRefBankInfoServiceImpl extends AbstractService implements BackRefBankInfoService
{
	private static final BankDictionaryService bankDictionaryService = new BankDictionaryService();

	public BackRefBankInfoServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public ResidentBank findByBIC(String bic) throws GateException
	{
		try
		{
			return bankDictionaryService.findByBIC(bic);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
