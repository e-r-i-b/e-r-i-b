package com.rssl.phizic.operations.finances.financeCalendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDataDescription;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDayExtractByReminderDescription;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.reminders.ReminderHelper;
import com.rssl.phizic.business.reminders.ReminderLinkService;
import com.rssl.phizic.business.reminders.finances.FinanceCalendarFillerFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderHandlerFactory;
import com.rssl.phizic.gate.reminder.ReminderState;
import com.rssl.phizic.gate.reminder.ReminderTypeHandler;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author osminin
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция получения информации о напоминаниях для финансового календаря
 */
public class GetRemindersToFinanceCalendarOperation extends OperationBase
{
	private static ReminderLinkService reminderLinkService = new ReminderLinkService();

	/**
	 * Заполнить данные календаря информацией о напоминаниях
	 * @param calendarData данные календаря
	 */
	public void fillRemindersData(List<CalendarDataDescription> calendarData) throws BusinessException, BusinessLogicException
	{
		if (CollectionUtils.isNotEmpty(calendarData))
		{
			for (TemplateDocument reminder : getReminders())
			{
				FinanceCalendarFillerFactory.getInstance().getFiller(reminder).fill(calendarData);
			}
		}
	}

	/**
	 * Получить информацию для финансового календаря о напоминаниях
	 * @param date дата, на которую получается информация
	 * @return информация о напоминаниях
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<CalendarDayExtractByReminderDescription> getRemindersDate(Calendar date) throws BusinessException, BusinessLogicException
	{
		if (DateHelper.getCurrentDate().after(date))
		{
			return Collections.emptyList();
		}

		List<TemplateDocument> reminders = getReminders();
		List<CalendarDayExtractByReminderDescription> descriptions = new ArrayList<CalendarDayExtractByReminderDescription>(reminders.size());

		for (TemplateDocument reminder: reminders)
		{
			ReminderInfo info = reminder.getReminderInfo();
			ReminderState state = getLinkByReminderId(reminder.getId());

			ReminderTypeHandler handler = ReminderHandlerFactory.getHandler(info);
			if (handler.isNeedRemind(state, info, date))
			{
				Calendar nextReminderDate = handler.getNextReminderDate(info, date);
				descriptions.add(ReminderHelper.buildDayExtractDescription(reminder, nextReminderDate));
			}
		}

		Collections.sort(descriptions, new Comparator<CalendarDayExtractByReminderDescription>()
		{
			public int compare(CalendarDayExtractByReminderDescription o1, CalendarDayExtractByReminderDescription o2)
			{
				ReminderInfo info1 = o1.getInfo();
				ReminderInfo info2 = o2.getInfo();

				if (info1 == null && info2 == null)
				{
					return o1.getName().compareTo(o2.getName());
				}
				if (info1 == null)
				{
					return 1;
				}
				if (info2 == null)
				{
					return -1;
				}

				return info1.getCreatedDate().compareTo(info2.getCreatedDate());
			}
		});

		return descriptions;
	}

	private List<TemplateDocument> getReminders() throws BusinessLogicException, BusinessException
	{
		Client client = PersonHelper.getContextPerson().asClient();
		return TemplateDocumentService.getInstance().getFiltered(client, ReminderHelper.getReminderTemplateFilter());
	}

	private ReminderState getLinkByReminderId(Long reminderId) throws BusinessException
	{
		Long loginId = PersonHelper.getContextPerson().getLogin().getId();
		return reminderLinkService.getByLoginAndId(loginId, reminderId);
	}
}
