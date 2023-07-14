package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;

import java.math.BigDecimal;
import java.util.List;

/**
 @author Pankin
 @ created 08.02.2011
 @ $Author$
 @ $Revision$
 */
public class DepositProductSBRF extends DepositProduct
{
	public List<String> getCurrencies() throws BusinessException
	{
		throw new UnsupportedOperationException();
	}

	public BigDecimal getMinAmount(String currencyValue, String periodvalue) throws BusinessException
	{
		throw new UnsupportedOperationException();
	}

	public List<String> getPeriods(String currencyValue) throws BusinessException
	{
		throw new UnsupportedOperationException();
	}
}
