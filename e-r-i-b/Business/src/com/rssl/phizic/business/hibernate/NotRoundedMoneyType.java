package com.rssl.phizic.business.hibernate;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.NotRoundedMoney;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.HibernateException;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 23.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class NotRoundedMoneyType extends MoneyType
{
	public Class returnedClass()
	{
		return NotRoundedMoney.class;
	}

	public Object deepCopy(Object x)
	{
		if (x == null) return null;
		return new NotRoundedMoney((Money) x);
	}

	protected Object newInstance(BigDecimal amount, String currencyCode)
	{
		if(amount == null ^ currencyCode == null)
			throw new RuntimeException("money amount cannot be null");

		if(amount == null & currencyCode == null)
			return null;

        try
		{
            CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
            Currency currency        = currencyService.findByAlphabeticCode(currencyCode);

            return new NotRoundedMoney(amount, currency);
		}
		catch (GateException e)
		{
			throw new HibernateException(e);
		}
	}
}
