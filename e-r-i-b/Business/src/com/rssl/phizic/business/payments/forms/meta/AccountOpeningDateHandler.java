package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.receptiontimes.TimeMatching;
import com.rssl.phizic.business.receptiontimes.WorkTimeInfo;
import com.rssl.phizic.utils.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Проверяем, что заявка на открытие вклада подтверждается в операционное время и дата открытия не меньше
 * текущей даты. Пересчитываем даты открытия и закрытия вклада в случае попытки открыть вклад, когда рабочее
 * время уже закончилось, или если текущая дата больше указанной даты открытия.
 @author Pankin
 @ created 28.02.2011
 @ $Author$
 @ $Revision$
 */
public class AccountOpeningDateHandler extends SetBusinessDocumentDateAction
{
	protected static final String INCORRECT_DOCUMENT_TYPE_MESSAGE = "Неверный тип платежа id=%d (Ожидается %s)";

	private static final String OPENING_DATE_FIELD = "openDate";
	private static final String CLOSING_DATE_FIELD = "closingDate";

	private static final String CLOSING_DATE_IS_NOT_HOLIDAY_MESSAGE = "Пожалуйста, при необходимости отредактируйте заявку.";
	protected static final String NOT_WORK_TIME_MESSAGE = "Обратите внимание, Ваша заявка будет обработана %s. " +
			"В связи с этим дата открытия и закрытия вклада изменены в соответствии с указанным сроком вклада. ";
	protected static final String NOT_WORK_TIME_MESSAGE_OPENING_ONLY = "Обратите внимание, Ваша заявка будет обработана %s. " +
			"В связи с этим дата открытия вклада изменена. ";
	private static final String OPENING_DATE_LT_CURRENT_MESSAGE = "Обратите внимание, изменились значения в выделенных полях. ";
	
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException(String.format(INCORRECT_DOCUMENT_TYPE_MESSAGE, document.getId(), "AccountOpeningClaim"));
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		Calendar openingDate = accountOpeningClaim.getOpeningDate();

		// Проверяем, что дата открытия вклада не меньше текущей даты, если меньше, пересчитываем (время не учитываем).
		boolean isOpeningDateChangedToCurrent = !checkOpeningDateGECurrent(openingDate);

		WorkTimeInfo workTimeInfo = getWorkTimeInfo(document);
		TimeMatching isWork = workTimeInfo.isWorkTimeNow();

		// если сейчас не рабочее время
		if (isWork != TimeMatching.RIGHT_NOW)
		{
			// если дата открытия вклада меньше следующей календарной даты (то есть не пересчитывалась в
			// связи с нерабочим временем)
			if (isOpeningDateLTNext(openingDate))
			{
				//берем для сравнения даты открытия и исполнения без времени
				Calendar opDate = DateHelper.getOnlyDate(openingDate);
				Calendar admDate = DateHelper.getOnlyDate(accountOpeningClaim.getAdmissionDate());
				// если рабочее время уже закончилось, выставляем следующий рабочий день
				// Если дата открытия меньше плановой даты исполнения, значит она была изменена из-за
				// разницы часовых поясов, выставляем следующий рабочий день
				if (isWork == TimeMatching.TOO_LATE || opDate.before(admDate))
				{
					Calendar nextWorkDay = getNextDate();

					recalculateDates(openingDate, nextWorkDay);

					updateDocumentDates(openingDate, accountOpeningClaim,
							String.format(accountOpeningClaim.isNeedInitialFee() ? NOT_WORK_TIME_MESSAGE :
									NOT_WORK_TIME_MESSAGE_OPENING_ONLY, new SimpleDateFormat("dd.MM.yyyy").format(openingDate.getTime())), stateMachineEvent);
				}
			}
		}

		// Если сейчас рабочее время, либо рабочее время на текущую дату еще не пришло, смотрим, пересчитывали
		// ли мы даты в связи с отличием текущей даты и даты открытия
		if (isOpeningDateChangedToCurrent)
		{
			updateDocumentDates(openingDate, accountOpeningClaim, OPENING_DATE_LT_CURRENT_MESSAGE, stateMachineEvent);
		}
	}

	protected boolean checkOpeningDateGECurrent(Calendar openingDate)
			throws DocumentLogicException, DocumentException
	{
		Calendar currentDate = DateHelper.getCurrentDate();
		Calendar openingDateWithoutTime = DateHelper.clearTime((Calendar) openingDate.clone());

		if (openingDateWithoutTime.before(currentDate))
		{
			recalculateDates(openingDate, currentDate);
			return false;
		}

		return true;
	}

	protected void updateDocumentDates(Calendar openingDate, AccountOpeningClaim accountOpeningClaim, String updatingCause, StateMachineEvent stateMachineEvent)
			throws DocumentException, DocumentLogicException
	{
		//устанавливаем новые даты в документе
		accountOpeningClaim.setOpeningDate(openingDate);
		Calendar newClosingDate = null;		
		if (accountOpeningClaim.isNeedInitialFee())
		{
			newClosingDate = DateHelper.add(openingDate, accountOpeningClaim.getPeriod());
			accountOpeningClaim.setClosingDate(newClosingDate);
		}

		try
		{
			businessDocumentService.addOrUpdate(accountOpeningClaim);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	    String errorMsg = null;

		if (!accountOpeningClaim.isNeedInitialFee())
		{
			errorMsg = updatingCause +CLOSING_DATE_IS_NOT_HOLIDAY_MESSAGE;
		}
		else
		{
			stateMachineEvent.registerChangedField(CLOSING_DATE_FIELD);
            errorMsg = updatingCause + CLOSING_DATE_IS_NOT_HOLIDAY_MESSAGE;
		}
		stateMachineEvent.registerChangedField(OPENING_DATE_FIELD);
		stateMachineEvent.addMessage(errorMsg);
	}
}
