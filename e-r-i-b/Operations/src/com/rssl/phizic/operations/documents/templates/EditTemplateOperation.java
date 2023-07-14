package com.rssl.phizic.operations.documents.templates;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ClientInvoiceData;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.OverallAmountPerDayTemplateLimitStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringConfirmTemplateStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringPostConfirmTemplateStrategy;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.strategies.limits.BlockTemplateOperationLimitStrategy;
import com.rssl.phizic.business.limits.BlockDocumentOperationException;
import com.rssl.phizic.business.limits.ImpossiblePerformOperationException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.reminders.ReminderLink;
import com.rssl.phizic.business.reminders.ReminderLinkService;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.security.SecurityDbException;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Evgrafov
 * @ created 08.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 82962 $
 */

public class EditTemplateOperation extends EditTemplateOperationBase
{
	private static final ReminderLinkService reminderLinkService = new ReminderLinkService();

	/**
	 * Обновляем шаблон новым именем
	 * @param newTemplateName новое имя шаблона
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	@Transactional
	public void changeName(String newTemplateName) throws BusinessLogicException, BusinessException
	{
		template.getTemplateInfo().setName(newTemplateName);
		TemplateDocumentService.getInstance().addOrUpdate(getEntity());
	}

	/**
	 * Обновить шаблон данными о напоминании
	 * @param reminderInfo информации о напоминании
	 * @param residualDate дата оставшегося выставленного счета
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	@Transactional
	public void changeReminderInfo(ReminderInfo reminderInfo, Calendar residualDate) throws BusinessLogicException, BusinessException
	{
		Long loginId = PersonHelper.getContextPerson().getLogin().getId();
		Long reminderId = template.getId();

		if(residualDate != null)
		{
			ReminderLink reminderLink = reminderLinkService.getByLoginAndId(loginId, reminderId);
			if(reminderLink == null)
				reminderLink = new ReminderLink(loginId, reminderId, residualDate);
			else
			{
				reminderLink.setProcessDate(null);
				reminderLink.setResidualDate(residualDate);
			}

			reminderLinkService.addOrUpdate(reminderLink);
		}
		else
		{
			reminderLinkService.deleteById(reminderId, loginId);
		}

		template.setReminderInfo(reminderInfo);
		TemplateDocumentService.getInstance().addOrUpdate(template);
	}

	/**
	 * Сохранить шаблон по исполенному платежу
	 * @param templateName имя шаблона
	 */
	public void saveQuicklyCreatedTemplate(String templateName) throws BusinessLogicException, BusinessException
	{
		try
		{
			doFraud();
			doSaveConfirm(templateName);
		}
		catch (BlockDocumentOperationException e)
		{
			doSendFraudNotification();
			throw e;
		}
		catch (ImpossiblePerformOperationException e)
		{
			doSendFraudNotification();
			throw e;
		}
	}

	@Transactional
	protected void doSaveConfirm(String templateName) throws BusinessLogicException, BusinessException
	{
		new BlockTemplateOperationLimitStrategy(template).checkAndThrow(null);
		new OverallAmountPerDayTemplateLimitStrategy(template).checkAndThrow(null);

		template.getTemplateInfo().setName(templateName);
		template.setClientOperationDate(GregorianCalendar.getInstance());
		template.setClientOperationChannel(template.getClientCreationChannel());

		TemplateDocumentService.getInstance().addOrUpdate(template);

		try
		{
			// сброс стратегии подтверждения
			new SmsPasswordConfirmStrategy().reset(null, template);
		}
		catch(SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Сохранить шаблон по исполенному платежу
	 */
	public void saveQuicklyCreatedTemplate() throws BusinessException, BusinessLogicException
	{
		try
		{
			doFraud();
			doProcess();
		}
		catch (BlockDocumentOperationException e)
		{
			doSendFraudNotification();
			throw e;
		}
		catch (ImpossiblePerformOperationException e)
		{
			doSendFraudNotification();
			throw e;
		}
	}

	protected void doFraud() throws BusinessLogicException, BusinessException
	{
		ProcessDocumentStrategy strategy = new FraudMonitoringConfirmTemplateStrategy(template);
		strategy.process(null, null);
	}

	@Transactional
	protected void doProcess() throws BusinessLogicException, BusinessException
	{
		new BlockTemplateOperationLimitStrategy(template).checkAndThrow(null);
		new OverallAmountPerDayTemplateLimitStrategy(template).checkAndThrow(null);

		template.setClientOperationDate(GregorianCalendar.getInstance());
		template.setClientOperationChannel(template.getClientCreationChannel());

		executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, "client"));
		TemplateDocumentService.getInstance().addOrUpdate(template);
	}

	protected void doSendFraudNotification() throws BusinessLogicException, BusinessException
	{
		try
		{
			FraudMonitoringDocumentStrategy strategy = new FraudMonitoringPostConfirmTemplateStrategy(template);
			strategy.process(null, null);
		}
		catch (Exception e)
		{
			log.error("При оповещии ВС ФМ об изменения вработе с шаблоном id = " + template.getId() + " произошла ошибка.", e);
		}
	}

	/**
	 * Редактирование шаблона документа
	 *
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Transactional
	public void edit() throws BusinessException, BusinessLogicException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.EDIT_TEMPLATE, ObjectEvent.CLIENT_EVENT_TYPE));
		TemplateDocumentService.getInstance().addOrUpdate(template);
	}

	/**
	 * Сохранить ченовик шаблона с заданным именем
	 * @param templateName имя шаблона
	 */
	@Transactional
	public void saveDraftTemplate(String templateName) throws BusinessException, BusinessLogicException
	{
		template.setClientOperationDate(GregorianCalendar.getInstance());
		template.setClientOperationChannel(template.getClientCreationChannel());
		template.getTemplateInfo().setName(templateName);
		TemplateDocumentService.getInstance().addOrUpdate(template);
	}

	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVEASTEMPLATE, "client"));
		TemplateDocumentService.getInstance().addOrUpdate(template);
	}
}

