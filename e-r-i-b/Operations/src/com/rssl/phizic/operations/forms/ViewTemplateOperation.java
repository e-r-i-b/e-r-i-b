package com.rssl.phizic.operations.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.impl.ReminderInfoImpl;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.informers.DocumentStateInformer;
import com.rssl.phizic.business.informers.ExternalServiceProviderTemplateStateInformer;
import com.rssl.phizic.business.informers.InternalServiceProviderTemplateStateInformer;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.reminders.ReminderLinkService;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderState;
import com.rssl.phizic.gate.reminder.ReminderType;
import com.rssl.phizic.operations.documents.templates.ViewTemplateOperationBase;
import com.rssl.phizic.utils.DateHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author mihaylov
 * @ created 15.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ViewTemplateOperation extends ViewTemplateOperationBase
{
	private static ReminderLinkService reminderLinkService = new ReminderLinkService();

	/**
	 * Сформировать информационные сообщения клиенту на данном шаге создания документа
	 * @return информационные сообщения
	 */
	public List<String> collectInfo() throws BusinessException
	{
		List<String> messages = new ArrayList<String>();
		for (DocumentStateInformer informer : getStateInformers())
		{
			if (informer.isActive())
			{
				messages.addAll(informer.inform());
			}
		}
		return messages;
	}

	/**
	 * Инициализация операции данными, пришедшими с финансового календаря
	 * @param source шаблон
	 * @param extractId идентификатор дня выписки календаря
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initializeFromFinanceCalendar(TemplateDocumentSource source, String extractId) throws BusinessException, BusinessLogicException
	{
		try
		{
			super.initialize(source);

			ReminderState reminderState = getLinkByReminderId(template.getId());
			ReminderInfo templateReminderInfo = template.getReminderInfo();

			boolean isResidual = templateReminderInfo == null && reminderState != null;
			boolean isReminder = templateReminderInfo != null && templateReminderInfo.getType() != null;

			//Если пришло напоминание или выставленный счет, значит ничего данными из календаря заполнять не надо
			if (isResidual || isReminder)
			{
				return;
			}

			ReminderInfoImpl reminderInfo = new ReminderInfoImpl();
			Calendar onceDate = DateHelper.fromDMYDateToDate(extractId.replace(".", "/"));

			//Создаем дефолтную информацию о напоминании с датой, выбранной в календаре
			reminderInfo.setType(ReminderType.ONCE);
			reminderInfo.setCreatedDate(Calendar.getInstance());
			reminderInfo.setOnceDate(onceDate);

			template.setReminderInfo(reminderInfo);
			TemplateDocumentService.getInstance().addOrUpdate(template);
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
	}

	private ReminderState getLinkByReminderId(Long reminderId) throws BusinessException
	{
		Long loginId = PersonHelper.getContextPerson().getLogin().getId();
		return reminderLinkService.getByLoginAndId(loginId, reminderId);
	}

	private DocumentStateInformer[] getStateInformers()
	{
		return new DocumentStateInformer[]{new InternalServiceProviderTemplateStateInformer(getTemplate()), new ExternalServiceProviderTemplateStateInformer(getTemplate())};
	}
}
