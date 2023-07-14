package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.gate.cache.proxy.composers.AbstractCacheKeyComposer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author tisov
 * @ created 04.06.15
 * @ $Author$
 * @ $Revision$
 * Класс, создающий ключ для запроса на выборку инвойсов для сотрудника
 */
public class OrderListKeyForEmployeeComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		List<ShopProfile> profiles = (List<ShopProfile>) args[0];
		Calendar dateFrom = (Calendar) args[1];
		Calendar dateTo = (Calendar) args[2];
		BigDecimal amountFrom = (BigDecimal) args[3];
		BigDecimal amountTo = (BigDecimal) args[4];
		String currency = (String) args[5];
		OrderState[] status = (OrderState[]) args[6];
		Long limit = (Long) args[7];
		Boolean orderByDelayDate = (Boolean) args[8];

		StringBuilder sb = new StringBuilder(200);
		sb.append(dateFrom.getTimeInMillis()).append(dateTo.getTimeInMillis()).append(amountFrom).append(amountTo).append(currency);
		for (ShopProfile profile : profiles)
		{
			sb.append(profile.getPassport()).append(profile.getBirthdate().getTimeInMillis()).append(profile.getFirstName()).append(profile.getSurName()).append(profile.getPatrName()).append(profile.getTb());
		}
		for (OrderState state : status) {
			sb.append(state.name());
		}
		sb.append(limit);
		sb.append(orderByDelayDate);
		return sb.toString();
	}
}
