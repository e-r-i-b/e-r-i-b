package com.rssl.phizic.einvoicing;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.dataaccess.hibernate.MoneyTypeBase;

import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 20.02.14
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
