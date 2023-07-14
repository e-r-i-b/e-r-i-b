package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author: vagin
 * @ created: 06.03.2012
 * @ $Author$
 * @ $Revision$
 *  Хендлер проверяющий не превышает ли текущая дата дату начала действия регулярного платежа
 */
public class ValidateAutoPaymentStartDateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(document instanceof AbstractLongOfferDocument && ((AbstractLongOfferDocument)document).isLongOffer())
		{
			Calendar startDate = Calendar.getInstance();
			String message = "";
	        //проверка для шинных автоплатежей
			if(document instanceof JurPayment && ((JurPayment)document).isLongOffer())
			{
				JurPayment payment = (JurPayment) document;
				String autoSubType = payment.getExtendedAttributes().get("auto-sub-type");
				startDate = payment.getNextPayDate();

				if (AutoSubType.INVOICE.name().equals(autoSubType))
					message = "Ожидаемая дата оплаты счета не может быть меньше текущей даты";
				else if(AutoSubType.ALWAYS.name().equals(autoSubType))
					message = "Дата ближайшего платежа не может быть меньше текущей даты";
				else
					throw new DocumentException("Неизвестный тип автоплатежа: " + autoSubType);
			}
			//проверка для длительных поручений
			else
			{
				startDate = ((AbstractLongOfferDocument)document).getStartDate();
				message = "Дата начала действия регулярного платежа не может быть меньше текущей даты.";
			}
			Calendar dateToCompare = DateHelper.clearTime((Calendar) startDate.clone());
			if (DateHelper.clearTime(Calendar.getInstance()).compareTo(dateToCompare) > 0)
				throw new DocumentLogicException(message);
		}
	}
}
