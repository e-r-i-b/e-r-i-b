package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.business.documents.payments.handlers.DocumentFormNameHandlerFilter;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AccountClosingPayment;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * User: Balovtsev
 * Date: 30.12.2011
 * Time: 11:19:37
 *
 * Проверяет совпадает ли даты закрытия и создания документа 
 *
 */
public class DateChangedFilter extends DocumentFormNameHandlerFilter
{
	public boolean isEnabled(StateObject stateObject)
	{
		if (super.isEnabled(stateObject))
		{
			AccountClosingPayment payment = (AccountClosingPayment) stateObject;
			Calendar closingDate = DateHelper.clearTime((Calendar) payment.getClosingDate().clone());
			Calendar currentDate = DateHelper.getCurrentDate();

			return closingDate.compareTo(currentDate) != 0;
		}

		return false;
	}
}
