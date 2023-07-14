package com.rssl.phizic.business.reminders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoice.FakeInvoice;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.PaymentSystemTransferTemplate;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.*;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDayExtractByReminderDescription;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author osminin
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер для работы с напоминаниями
 */
public class ReminderHelper
{
	private static ReminderLinkService reminderLinkService = new ReminderLinkService();

	/**
	 * Получить линк на напоминание по идентификатору напоминания
	 * @param reminderId идентификатор напоминания
	 * @return линк
	 * @throws BusinessException
	 */
	public static ReminderLink getLinkByReminderId(Long reminderId) throws BusinessException
	{
		if (reminderId == null)
		{
			return null;
		}

		Long loginId = PersonHelper.getContextPerson().getLogin().getId();
		return reminderLinkService.getByLoginAndId(loginId, reminderId);
	}

	/**
	 * @return мап дефолтных параметров напоминаний
	 */
	public static Map<String, Object> getDefaultReminderParameters()
	{
		Map<String, Object> result = new HashMap<String, Object>(5);
		Calendar defaultDate = DateHelper.getNextDay();
		result.put("enableReminder", Boolean.FALSE.toString());
		result.put("reminderType", ReminderType.ONCE);
		result.put("onceDate", DateHelper.formatDateToStringWithPoint(defaultDate));
		result.put("dayOfMonth", defaultDate.get(Calendar.DAY_OF_MONTH));
		result.put("monthOfQuarter", DateHelper.getMonthOfQuarter(defaultDate));

		return result;
	}

	/**
	 * @return множества фильтров для работы напоминаний
	 */
	public static List<TemplateDocumentFilter> getReminderTemplateFilter()
	{
		return Arrays.asList(
				new ActiveTemplateFilter(),
				new StateTemplateFilter(StateCode.TEMPLATE, StateCode.WAIT_CONFIRM_TEMPLATE),
				new ChannelActivityTemplateFilter(TemplateDocumentService.getInstance().getCurrentChannelType()));
	}

	/**
	 * Построить сущность инвойса по напоминанию
	 * @param template шаблон напоминания
	 * @param state текущее состояние напоминания
	 * @return инвойс
	 * @throws BusinessException
	 */
	public static FakeInvoice buildInvoice(TemplateDocument template, ReminderLink state) throws BusinessException
	{
		FakeInvoice result = new FakeInvoice();
		Money amount = template.getExactAmount();

		result.setSum(amount != null ? amount.getDecimal() : null);
		result.setName(template.getTemplateInfo().getName());
		result.setId(template.getId());
		result.setExternalId(template.getId().toString());
		result.setState(state != null && state.getDelayedDate() != null ? InvoiceStatus.DELAYED : InvoiceStatus.NEW);
		result.setCreatingDate(getCreationDate(template.getReminderInfo(), state));
		result.setFormType(template.getFormType());
		result.setType("reminder");

		if (result.getState() == InvoiceStatus.DELAYED)
		{
			result.setDelayedDate(state.getDelayedDate());
		}

		switch (template.getFormType())
		{
			case INTERNAL_PAYMENT_SYSTEM_TRANSFER:
			{
				result.setProviderName(template.getReceiverName());
				Field keyField = getFirstKeyField((PaymentSystemTransferTemplate) template);
				if(keyField != null)
				{
					result.setKeyName(keyField.getName());
					result.setKeyValue((String) keyField.getValue());
				}
				//result.setImageId();
				break;
			}
			case INTERNAL_TRANSFER:
			{
				result.setKeyName(getTransferKeyName(template.getDestinationResourceType()));
				result.setKeyValue(getTransferKeyValue(template.getDestinationResourceType(), template.getReceiverAccount()));
				break;
			}
			case JURIDICAL_TRANSFER:
			case INDIVIDUAL_TRANSFER:
			case INDIVIDUAL_TRANSFER_NEW:
			{
				result.setProviderName(template.getReceiverName());
				result.setKeyName(getTransferKeyName(template.getDestinationResourceType()));
				result.setKeyValue(getTransferKeyValue(template.getDestinationResourceType(), template.getReceiverAccount()));
			}
		}

		return result;
	}

	/**
	 * Построить информацию о напоминании для финансового календаря
	 * @param reminder напоминание
	 * @param nextReminderDate дата ближайшего напоминания
	 * @return Информация о напоминании для финансового каледаря
	 * @throws BusinessException
	 */
	public static CalendarDayExtractByReminderDescription buildDayExtractDescription(TemplateDocument reminder, Calendar nextReminderDate) throws BusinessException
	{
		if (reminder == null)
		{
			throw new IllegalArgumentException("Шаблон не может быть null.");
		}

		CalendarDayExtractByReminderDescription description = new CalendarDayExtractByReminderDescription();
		description.setId(reminder.getId());
		description.setInfo(reminder.getReminderInfo());
		description.setName(reminder.getTemplateInfo().getName());
		description.setNextReminderDate(nextReminderDate);
		description.setState(reminder.getState());
		description.setFormTypeName(reminder.getFormType().getName());

		Money exactAmount = reminder.getExactAmount();
		if (exactAmount != null)
		{
			description.setAmount(exactAmount.getDecimal());
		}

		switch (reminder.getFormType())
		{
			case INTERNAL_PAYMENT_SYSTEM_TRANSFER:
			{
				Field keyField = getFirstKeyField((PaymentSystemTransferTemplate) reminder);
				if(keyField != null)
				{
					description.setKeyName(keyField.getName());
					description.setKeyValue((String) keyField.getValue());
				}
				break;
			}
			case INTERNAL_TRANSFER:
			{
				description.setKeyName(getTransferKeyName(reminder.getDestinationResourceType()));
				description.setKeyValue(getTransferKeyValue(reminder.getDestinationResourceType(), reminder.getReceiverAccount()));
				break;
			}
			case JURIDICAL_TRANSFER:
			case INDIVIDUAL_TRANSFER:
			case INDIVIDUAL_TRANSFER_NEW:
			{
				description.setProviderName(reminder.getReceiverName());
				description.setKeyName(getTransferKeyName(reminder.getDestinationResourceType()));
				description.setKeyValue(getTransferKeyValue(reminder.getDestinationResourceType(), reminder.getReceiverAccount()));
			}
		}

		return description;
	}

	private static String getTransferKeyName(ResourceType type)
	{
		switch (type)
		{
			case CARD:
			case EXTERNAL_CARD:
				return "Карта";

			case ACCOUNT:
			case EXTERNAL_ACCOUNT:
				return "Счет";

			default:
				return "";
		}
	}

	private static String getTransferKeyValue(ResourceType resourceType, String receiverAccount) throws BusinessException
	{
		if(StringHelper.isEmpty(receiverAccount))
			return "";

		switch (resourceType)
		{
			case CARD:
			case EXTERNAL_CARD:
				return MaskUtil.getCutCardNumber(receiverAccount);

			case ACCOUNT:
			case EXTERNAL_ACCOUNT:
				return receiverAccount;

			default:
				return "";
		}
	}

	private static Calendar getCreationDate(ReminderInfo info, ReminderLink state)
	{
		if(state != null)
		{
			if(state.getDelayedDate() != null)
				return state.getDelayedDate();

			if(state.getProcessDate() != null)
				return getCreationDateByType(info, state.getProcessDate());

			if(state.getResidualDate() != null)
				return state.getResidualDate();
		}

		Calendar creationDate = DateHelper.clearTime((Calendar) info.getCreatedDate().clone());
		return getCreationDateByType(info, creationDate);
	}

	private static Calendar getCreationDateByType(ReminderInfo info, Calendar fromDate)
	{
		switch (info.getType())
		{
			case ONCE_IN_MONTH:
				return DateHelper.getNearDateByMonth(fromDate, info.getDayOfMonth());
			case ONCE_IN_QUARTER:
				return DateHelper.getNearDateByQuarter(fromDate, info.getMonthOfQuarter(), info.getDayOfMonth());
			case ONCE:
				return info.getOnceDate();
			default:
				return Calendar.getInstance();
		}
	}

	private static Field getFirstKeyField(PaymentSystemTransferTemplate systemTransferTemplate) throws BusinessException
	{
		try
		{
			List<Field> fields = systemTransferTemplate.getExtendedFields();

			if(CollectionUtils.isEmpty(fields))
				return null;

			for(Field field : fields)
			{
				if(field.isKey())
					return field;
			}

			return null;

		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить напоминание по идентификатору с учетом кэша
	 * @param reminderId идентификатор напоминания
	 * @return сущность напоминания(шаблон)
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static TemplateDocument findReminderById(Long reminderId) throws BusinessException, BusinessLogicException
	{
		return TemplateDocumentService.getInstance().findSingle(
				PersonHelper.getContextPerson().asClient(), new IdsTemplatesFilter(reminderId));
	}
}
