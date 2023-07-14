package com.rssl.phizic.gate.hibernate.types;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.dataaccess.hibernate.MoneyTypeBase;
import com.rssl.phizic.gate.money.CurrencyImpl;

import java.math.BigDecimal;

/**
 * @author khudyakov
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */
public class MoneyType extends MoneyTypeBase
{
	protected Object newInstance(BigDecimal amount, String currencyCode)
	{
		if(amount == null ^ currencyCode == null)
			throw new RuntimeException("money amount cannot be null");

		if(amount == null & currencyCode == null)
			return null;

		return new Money(amount, new CurrencyImpl(currencyCode));
	}
}
