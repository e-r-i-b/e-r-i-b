package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * Проверка плановой даты исполнения пратежа, которая не может быть меньше даты начала
 * действия платежа
 * @author niculichev
 * @ created 19.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class CheckLongOfferStartDateHandler extends BusinessDocumentHandlerBase
{
	private static final String ERROR_MESSAGE_PARAMETER_MANE    = "error-message";
	private static final String ERROR_MESSAGE_FOR_USER          = "Вы создали автоплатеж во внеоперационное время банка, поэтому плановая дата исполнения стала больше даты начала действия автоплатежа. " +
			"Пожалуйста, отредактируйте автоплатеж, указав другую дату. ";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LongOffer))
			throw new DocumentException("Ожидался наследник LongOffer (длительное поручение)");

		AbstractLongOfferDocument longOffer = (AbstractLongOfferDocument) document;
		if (!longOffer.isLongOffer())
			return;

		// Плановая дата исполнения  не может быть больше даты начала действия
		// Чистим время, тк дата исполнения и дата начала могут совпадать
		Calendar admissionDate  = DateHelper.toCalendar(DateHelper.setTime(longOffer.getAdmissionDate().getTime(), 0, 0, 0, 0));
		Calendar startDate      = DateHelper.toCalendar(DateHelper.setTime(longOffer.getStartDate().getTime(), 0, 0, 0, 0));
		if (startDate.compareTo(admissionDate) < 0)
		{
			throw new DocumentLogicException(getErrorMessage());
		}
	}

	private String getErrorMessage()
	{
		String message = getParameter(ERROR_MESSAGE_PARAMETER_MANE);
		if (StringHelper.isEmpty(message))
		{
			return ERROR_MESSAGE_FOR_USER;
		}
		return message;
	}
}
