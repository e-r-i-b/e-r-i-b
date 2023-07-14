package com.rssl.phizicgate.uec;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.uec.generated.StateType;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Erkin
 * @ created 14.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Свод информации по статусам платёжных поручений УЭК
 * Сейчас сюда входит:
 * - список ЕРИБ-статусов платежей, по которым УЭК получает уведомления
 * - соответствие ЕРИБ-статуса платежа и УЭК-статуса платёжного поручения
 */
public final class UECOrderStates
{
	private static final Map<String, StateType> ERIB2UEC_STATES;

	static
	{
		ERIB2UEC_STATES = new TreeMap<String, StateType>(new Comparator<String>()
		{
			public int compare(String o1, String o2)
			{
				if (o1 == null)
					return -1;
				if (o2 == null)
					return 1;
				return o1.compareToIgnoreCase(o2);
			}
		});

		ERIB2UEC_STATES.put("DISPATCHED",   StateType.PROC);
		ERIB2UEC_STATES.put("EXECUTED",     StateType.APRP);
		ERIB2UEC_STATES.put("SUCCESSED",    StateType.APRP);
		ERIB2UEC_STATES.put("REFUSED",      StateType.DECL);
		ERIB2UEC_STATES.put("ERROR",        StateType.DECL);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return Перечень ЕРИБ-статусов платежей, по которым УЭК получает уведомления
	 */
	public static Collection<String> getNotifiedEribPaymentStates()
	{
		return ERIB2UEC_STATES.keySet();
	}

	/**
	 * Преобразует ЕРИБ-статус платежа в УЭК-статус
	 * @param eribPaymentState - ЕРИБ-статус платежа
	 * @return УЭК-статус платежа (never null)
	 */
	public static StateType convertPaymentStateToUEC(String eribPaymentState)
	{
		if (StringHelper.isEmpty(eribPaymentState))
			throw new IllegalArgumentException("Аргумент 'eribPaymentState' не может быть пустым");

		StateType uecPayOrderState = ERIB2UEC_STATES.get(eribPaymentState);
		if (uecPayOrderState == null)
			throw new UnsupportedOperationException("Не знаю, как сконвертировать ЕРИБ-статус платежа в УЭК-статус " + eribPaymentState);
		return uecPayOrderState;
	}
}
