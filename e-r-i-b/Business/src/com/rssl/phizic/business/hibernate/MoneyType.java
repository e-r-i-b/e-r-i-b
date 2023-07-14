package com.rssl.phizic.business.hibernate;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.dataaccess.hibernate.MoneyTypeBase;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.HibernateException;

import java.math.BigDecimal;

/**
 * @author Evgrafov
 * @ created 30.05.2006
 * @ $Author: gladishev $
 * @ $Revision: 58178 $
 */
public class MoneyType extends MoneyTypeBase
{
	protected Object newInstance(BigDecimal amount, String currencyCode)
	{
		if(amount == null ^ currencyCode == null)
			throw new RuntimeException("money amount cannot be null");

		if(amount == null & currencyCode == null)
			return null;

        try
		{
            CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
            Currency        currency        = currencyService.findByAlphabeticCode(currencyCode);

            return new Money(amount, currency);
		}
		catch (GateException e)
		{
			throw new HibernateException(e);
		}
	}
}