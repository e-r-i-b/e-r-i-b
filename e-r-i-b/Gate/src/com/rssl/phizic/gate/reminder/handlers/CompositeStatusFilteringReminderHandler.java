package com.rssl.phizic.gate.reminder.handlers;

import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderState;
import com.rssl.phizic.gate.reminder.ReminderTypeHandler;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author tisov
 * @ created 06.06.15
 * @ $Author$
 * @ $Revision$
 *  омпозитный хендлер напоминаний, фильтрующий напоминани€ по статусам
 */
public class CompositeStatusFilteringReminderHandler implements ReminderTypeHandler
{
	private ReminderTypeHandler delegate;           //делегат, вложенный хендлер, логика которого используетс€ при одобрении напоминани€ по статуса
	private List<InvoiceStatus> allowedStatuses;    //список допустимых статусов

	public CompositeStatusFilteringReminderHandler(ReminderTypeHandler delegate, InvoiceStatus... statuses)
	{
		this.delegate = delegate;
		this.allowedStatuses = Arrays.asList(statuses);
	}

	private boolean isStateAllowed(ReminderState state)
	{
		if (state != null && state.getDelayedDate() != null)
		{
			return this.allowedStatuses.contains(InvoiceStatus.DELAYED);
		}
		else
		{
			return this.allowedStatuses.contains(InvoiceStatus.NEW);
		}
	}

	public boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date)
	{
		if (!isStateAllowed(state))
		{
			return false;
		}

		return this.delegate.isNeedRemind(state, info, date);
	}

	public Calendar getReminderDate(ReminderInfo info, Calendar date)
	{
		return this.delegate.getReminderDate(info, date);
	}

	public Calendar getNextReminderDate(ReminderInfo info, Calendar date)
	{
		return this.delegate.getNextReminderDate(info, date);
	}
}
