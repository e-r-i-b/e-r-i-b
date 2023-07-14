package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.gate.payments.ReceiverCardType;

/**
 * @author lepihina
 * @ created 19.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер добавляющий сообщение о комиссии
 */
public class AddPaymentCommissionSaveHandler extends BusinessDocumentHandlerBase
{
	private static final String DEFAULT_PREFIX_MESSAGE = "За данную операцию может взиматься комиссия в соответствии с тарифами банка. ";
	private static final String FROM_ACCOUNT_COMMISSION_MESSAGE = DEFAULT_PREFIX_MESSAGE + "Сумму комиссии Вы можете посмотреть " +
			"<a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Perevody__Tarify.pdf' target='_blank'>здесь</a>.";
	private static final String DEFAULT_COMMISSION_MESSAGE_API = DEFAULT_PREFIX_MESSAGE + "Сумму комиссии Вы можете посмотреть на сайте банка.";
	private static final String FROM_CARD_COMMISSION_MESSAGE = DEFAULT_PREFIX_MESSAGE + "Сумму комиссии Вы можете посмотреть " +
			"<a href='http://www.sberbank.ru/common/img/uploaded/files/pdf/person/bank_cards/Beznalichnoe_perechislenie.pdf' target='_blank'>здесь</a>.";
	private static final String ZERO_COMMISSION_MASSEGE = "За выполнение данной операции комиссия не взимается.";
	private static final String MASTERCARD_EXTERNAL_CARD_COMMISSION_MASSEGE = "За совершение данной операции взимается комиссия в размере 1,5% от суммы операции, но не менее 30 рублей.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof RurPayment))
			throw new DocumentException("document должен быть RurPayment");

		RurPayment payment = (RurPayment) document;

		if (payment.getCommission() != null)
		{
			//если комиссия нулевая, то сообщаем об этом пользователю.
			if (payment.getCommission().isZero())
			{
				stateMachineEvent.addMessage(ZERO_COMMISSION_MASSEGE);
			}
			return;
		}

		//если комиссия не пришла, то выводим стандартное сообщение о возможности взимания комиссии.
		if (payment.getChargeOffResourceType() == ResourceType.CARD)
		{
			//оплата со счета
			if (ReceiverCardType.MASTERCARD == payment.getReceiverCardType())
			{
				//Для мастер кард - своя текстовка
				stateMachineEvent.addMessage(MASTERCARD_EXTERNAL_CARD_COMMISSION_MASSEGE);
			}
			else if (ApplicationUtil.isApi())
			{
				//Для АПИ - без ссылок
				stateMachineEvent.addMessage(DEFAULT_COMMISSION_MESSAGE_API);
			}
			else
			{
				//для остальных каналов - с ссылкми
				stateMachineEvent.addMessage(FROM_CARD_COMMISSION_MESSAGE);
			}
		}
		else if (payment.getChargeOffResourceType() == ResourceType.ACCOUNT)
		{
			if (ApplicationUtil.isApi())
			{
				//Для АПИ - без ссылок
				stateMachineEvent.addMessage(DEFAULT_COMMISSION_MESSAGE_API);
			}
			else
			{
				//для остальных каналов - с ссылкми
				stateMachineEvent.addMessage(FROM_ACCOUNT_COMMISSION_MESSAGE);
			}
		}
	}
}
