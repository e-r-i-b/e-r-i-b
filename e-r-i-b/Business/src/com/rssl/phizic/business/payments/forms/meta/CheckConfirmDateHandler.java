package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author: vagin
 * @ created: 13.01.2012
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер проверяющий не превышает ли дата подтверждения заявки в КЦ даты начала действия автоплатежа.
 */
public class CheckConfirmDateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		LongOffer payment = (LongOffer) document;
		if (payment.getStartDate() != null)
		{
			Calendar autoPaymentStartDate =(Calendar)payment.getStartDate().clone();
			DateHelper.clearTime(autoPaymentStartDate);
			Calendar curDate = Calendar.getInstance();
			DateHelper.clearTime(curDate);
			if(autoPaymentStartDate.compareTo(curDate) < 0)
			{
				throw new DocumentLogicException("Дата начала действия автоплатежа меньше текущей. Платеж не может быть подтвержден.");
			}
		}
	}
}
