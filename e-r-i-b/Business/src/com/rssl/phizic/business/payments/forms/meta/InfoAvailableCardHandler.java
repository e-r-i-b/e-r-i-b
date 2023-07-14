package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * Хендлер информирует клиента о доступности выполнения операции перевода по номеру карты если
 * счет получателя начитается на 40817
 * @author basharin
 * @ created 21.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class InfoAvailableCardHandler extends BusinessDocumentHandlerBase
{
	private static final String INFO_MESSAGE = "Обратите внимание! Вы можете перевести деньги на карту Сбербанка России по номеру карты. Для этого перейдите по ссылке <a href='#' class='paperEnterLink' onclick='selectOurCard(); return false;'>На карту Сбербанка</a>.";
	private static final String INFO_MESSAGE_MOBILE = "Обратите внимание! Вы можете перевести деньги на карту Сбербанка России по номеру карты. Для этого выберите «Перевод на карту клиента Сбербанка».";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof RurPayment))
			throw new DocumentException("Ожидался RurPayment");

		RurPayment rurPayment = (RurPayment) document;
		if (RurPayment.OUR_ACCOUNT_TYPE_VALUE.equals(rurPayment.getReceiverSubType())
				&& rurPayment.getReceiverAccount().indexOf("40817") == 0
				&& !rurPayment.isLongOffer())
		{
			if (rurPayment.getInfoAvailableCardWasShow() == null || !rurPayment.getInfoAvailableCardWasShow())
			{
				rurPayment.setInfoAvailableCardWasShow(true);
				stateMachineEvent.addMessage(ApplicationUtil.isApi() ? INFO_MESSAGE_MOBILE : INFO_MESSAGE);
			}
		}
	}
}
