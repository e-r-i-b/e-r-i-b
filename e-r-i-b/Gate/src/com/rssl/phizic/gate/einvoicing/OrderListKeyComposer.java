package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.gate.cache.proxy.composers.AbstractCacheKeyComposer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author bogdanov
 * @ created 12.03.14
 * @ $Author$
 * @ $Revision$
 */

public class OrderListKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		List<ShopProfile> profiles = (List<ShopProfile>) args[0];
		Calendar dateFrom = (Calendar) args[1];
		Calendar dateTo = (Calendar) args[2];
		Calendar dateDelayedTo = (Calendar) args[3];
		BigDecimal amountFrom = (BigDecimal) args[4];
		BigDecimal amountTo = (BigDecimal) args[5];
		String currency = (String) args[6];
		OrderState[] status = (OrderState[]) args[7];

		StringBuilder sb = new StringBuilder(200);
		sb.append(dateFrom.getTimeInMillis()).append(dateTo.getTimeInMillis()).append(dateDelayedTo.getTimeInMillis()).append(amountFrom).append(amountTo).append(currency);
		for (ShopProfile profile : profiles)
		{
			sb.append(profile.getPassport()).append(profile.getBirthdate().getTimeInMillis()).append(profile.getFirstName()).append(profile.getSurName()).append(profile.getPatrName()).append(profile.getTb());
		}
		for (OrderState state : status) {
			sb.append(state.name());
		}
		return sb.toString();
	}
}
