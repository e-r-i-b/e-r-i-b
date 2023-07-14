package com.rssl.phizic.operations.reminder;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.IdsTemplatesFilter;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.reminders.ReminderHelper;
import com.rssl.phizic.business.reminders.ReminderLink;
import com.rssl.phizic.business.reminders.ReminderLinkService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * @author niculichev
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ProcessReminderOperation extends OperationBase
{
	private static final ReminderLinkService reminderLinkService = new ReminderLinkService();
	private ReminderLink state;
	private TemplateDocument template;

	/**
	 * Инициализация операции
	 * @param reminderId идентификатор напоминания(шаблона)
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long reminderId) throws BusinessException, BusinessLogicException
	{
		if(reminderId == null || reminderId.equals(0L))
			throw new BusinessException("Не задан идентификатор напоминания.");

		Login login = PersonHelper.getContextPerson().getLogin();
		ReminderLink temp = reminderLinkService.getByLoginAndId(login.getId(), reminderId);

		this.state = temp == null ? new ReminderLink(login.getId(), reminderId) : temp;
		this.template = ReminderHelper.findReminderById(reminderId);

		if(template == null)
			throw new BusinessException("Шаблона с идентификатором " + reminderId + " не существует.");
	}

	/**
	 * Отложить выставленное напоминание
	 * @param delayDate дата откладывания
	 * @throws BusinessException
	 */
	public void delay(Date delayDate) throws BusinessException
	{
		state.setDelayedDate(DateHelper.toCalendar(delayDate));
		state.setProcessDate(null);
		state.setResidualDate(null);
		reminderLinkService.addOrUpdate(state);
	}

	/**
	 * Удалить выставленное напоминание
	 * @throws BusinessException
	 */
	public void remove() throws BusinessException
	{
		state.setProcessDate(DateHelper.getCurrentDate());
		state.setDelayedDate(null);
		state.setResidualDate(null);
		reminderLinkService.addOrUpdate(state);
	}

	public TemplateDocument getTemplate()
	{
		return template;
	}
}
