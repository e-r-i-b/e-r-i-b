package com.rssl.phizic.business.loans;

import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.DateHelper;

import java.util.List;
import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 20.07.2010
 * @ $Author$
 * @ $Revision$
 */



public class LoanServiceHelper
{

	private static final ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
	/*
	Поиск ссылки на кредит по номеру ссудного счета
	 */
	public static LoanLink findLoanLinkByNumber(List<LoanLink> loanLinks, String accountNumber)
	{
		for (LoanLink link : loanLinks)
		{
			if(link.getNumber().equals(accountNumber))
				return link;
		}
		return null;
	}

	/**
	 * Преобразует временной отрезок в количество записей для графика платежей
	 * @param nextPaymentDate - дата следующего платежа
	 * @param fromDate - начальная дата
	 * @param toDate - конечная дата
	 * @return пара<начальный номер, количество записей>
	 */
	public static Pair<Long, Long> getPaymentCountByDates(Calendar nextPaymentDate, Calendar fromDate, Calendar toDate)
	{
		if (fromDate.after(toDate))
			return null;

		//если даты нет, то считаем датой следующего платежа, 1-е число следующего месяца.
		if(nextPaymentDate == null)
		{
			Calendar newDate = DateHelper.getCurrentDate();
			newDate.add(Calendar.MONTH, 1);
			newDate.set(Calendar.DATE, 1);
			newDate.get(Calendar.MONTH); //не убирать, иначе не обновиться значение
			nextPaymentDate = newDate;
		}

		Long startNumber;
		Long count;
		// если дата следующего платежа больше даты постоения графика,
		// то номер первого платежа в графике будет с минусом
		if(nextPaymentDate.after(fromDate))
		{
			DateSpan fromDateSpan = new DateSpan(fromDate,nextPaymentDate);
			startNumber = - fromDateSpan.getValueInMonth();
		}
		else
		{
			DateSpan fromDateSpan = new DateSpan(nextPaymentDate,fromDate);
			startNumber = fromDateSpan.getValueInMonth();
		}

		DateSpan counDateSpan = new DateSpan(fromDate,toDate);
		count = counDateSpan.getValueInMonth();

		return new Pair<Long, Long>(startNumber, count);
	}

	/* использовать JMS для кредитов*/
	public static boolean isJmsForLoanAvailable()
	{
		return clientConfig.isJmsForLoanEnabled();
	}


}
