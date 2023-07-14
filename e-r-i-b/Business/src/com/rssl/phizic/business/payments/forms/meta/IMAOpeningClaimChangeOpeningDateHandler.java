package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.IMAOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.receptiontimes.WorkTimeInfo;
import com.rssl.phizic.business.receptiontimes.TimeMatching;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author Mescheryakova
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * Если документ отправлен в нерабочее время, меняем дату открытия ОМС на ближайшую рабочую дату
 */

public class IMAOpeningClaimChangeOpeningDateHandler extends SetBusinessDocumentDateAction
{
	private static final String OPENING_DATE_FIELD = "openingDate";
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final String CLIENT_MESSAGE = "Обратите внимание, Ваша заявка будет обработана %s. В связи с этим дата открытия ОМС изменена. Пожалуйста, при необходимости отредактируйте заявку";

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof IMAOpeningClaim))
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается IMAOpeningClaim)");

		IMAOpeningClaim imaOpeningClaim = (IMAOpeningClaim) document;
		Calendar openingDate = imaOpeningClaim.getOpeningDate();

		WorkTimeInfo workTimeInfo = getWorkTimeInfo(document);
		TimeMatching isWork = workTimeInfo.isWorkTimeNow();

		// если сейчас не рабочее время
		if (isWork != TimeMatching.RIGHT_NOW)
		{
			try
			{
				// если дата еще не пересчитывалась в связи с нерабочим временем
				if (isOpeningDateLTNext(openingDate))
				{
					//берем для сравнения даты открытия и исполнения без времени
					Calendar opDate = DateHelper.getOnlyDate(openingDate);
					Calendar admDate = DateHelper.getOnlyDate(imaOpeningClaim.getAdmissionDate());
					if (isWork == TimeMatching.TOO_LATE || opDate.before(admDate))
					{
						GateDocument gateDoc = ((ConvertableToGateDocument) document).asGateDocument();
						CalendarGateService calendarGateService = GateSingleton.getFactory().service(CalendarGateService.class);
						Calendar nextWorkDay = calendarGateService.getNextWorkDay(DateHelper.getCurrentDate(), gateDoc);

						recalculateDates(openingDate, nextWorkDay);
						imaOpeningClaim.setOpeningDate(openingDate);

						businessDocumentService.addOrUpdate(imaOpeningClaim);
						stateMachineEvent.registerChangedField(OPENING_DATE_FIELD);
						stateMachineEvent.addMessage(String.format(CLIENT_MESSAGE, DateHelper.formatDateToStringWithPoint(openingDate)));
					}
				}
			}
			catch (GateException e)
			{
				throw new DocumentException(e);
			}
			catch (GateLogicException e)
			{
				throw new DocumentLogicException(e);
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
		}
	}
}
