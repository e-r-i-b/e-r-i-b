package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.gate.longoffer.LongOffer;

/**
 * Проверка даты ближайшего платежа для длительного поручения
 * (хендлер использует дату приема документа банком (т.е она должна быть уже расчитана),
 * поэтому использовать его только после вызова SetBusinessDocumentDateAction)
 *
 * @author hudyakov
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CheckImmediatePaymentDateHandler extends BusinessDocumentHandlerBase
{
	private static final String WARNING_PERIODIC_MESSAGE_FOR_USER = "Обратите внимание: дата ближайшего платежа изменилась! Если Вы согласны с новой датой, то нажмите на кнопку «Подтвердить». Если не согласны, то отредактируйте дату оплаты.";
	private static final String WARNING_EVENT_MESSAGE_FOR_USER = "Обратите внимание: дата начала действия регулярного платежа %s. Если Вы согласны с датой, то нажмите на кнопку «Подтвердить». Если не согласны, то отредактируйте данные по регулярному платежу";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LongOffer))
			throw new DocumentException("Ожидался наследник LongOffer (длительное поручение)");

		AbstractLongOfferDocument longOffer = (AbstractLongOfferDocument) document;
		if (!longOffer.isLongOffer())
			return;

		if (longOffer.calcStartDate(longOffer.getAdmissionDate()))
		{
			if (longOffer.isPeriodic())
				throw new DocumentLogicException(WARNING_PERIODIC_MESSAGE_FOR_USER);

			String startDate = String.format("%1$te.%1$tm.%1$tY", longOffer.getStartDate());
			throw new DocumentLogicException(String.format(WARNING_EVENT_MESSAGE_FOR_USER, startDate));
		}
	}
}
