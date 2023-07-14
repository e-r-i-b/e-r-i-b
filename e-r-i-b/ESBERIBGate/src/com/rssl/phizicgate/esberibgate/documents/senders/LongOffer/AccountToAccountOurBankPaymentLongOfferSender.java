package com.rssl.phizicgate.esberibgate.documents.senders.LongOffer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.AccountIntraBankPayment;
import com.rssl.phizic.gate.payments.longoffer.AccountIntraBankPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сендер заявки "Перевод физическому лицу со вклада на счет в другой банк внутри Сбербанка России(Длительное поручение)."
 */

public class AccountToAccountOurBankPaymentLongOfferSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public AccountToAccountOurBankPaymentLongOfferSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new AccountToAccountOurBankPaymentLongOfferProcessor(getFactory(), (AccountIntraBankPaymentLongOffer) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Повторная отправка заявки на перевод физическому лицу со вклада на счет в другой банк внутри Сбербанка России(Длительное поручение) не поддерживается");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв не поддерживается");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("Подготовка заявки на перевод физическому лицу со вклада на счет в другой банк внутри Сбербанка России(Длительное поручение) не поддерживается");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("Подтверждение заявки на перевод физическому лицу со вклада на счет в другой банк внутри Сбербанка России(Длительное поручение) не поддерживается");
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if(!(document instanceof AccountIntraBankPayment))
			throw new GateException("Некорректный тип документа, ожидался наследник AccountIntraBankPayment.");

		AccountIntraBankPayment transfer = (AccountIntraBankPayment) document;
		LongOffer longOffer = (LongOffer) document;
		if(!BackRefInfoRequestHelper.isSameTB(document.getOffice(), transfer.getReceiverAccount()))
		{
			// проверка на то, что в регулярной платеже, перевод с типом суммы "сумма необходимая для увеличения остатка на счете получателя до
			// указанной суммы" совершается внутри одного ТБ.
			if(longOffer.getSumType() == SumType.REMAIND_IN_RECIP)
			{
				throw new GateLogicException("Данный автоплатеж можно создать только для операций внутри одного территориального банка Сбербанка. " +
						"Пожалуйста, выберите другой тип суммы или укажите другой счет зачисления.");
			}

			// проверка на то, что в регулярной платеже, перевод с типом суммы " на сумму овердрафта на счете получателя"
			//совершается внутри одного ТБ.
			if(longOffer.getSumType() == SumType.OVER_DRAFT )
			{
				throw new GateLogicException("Данный автоплатеж можно создать только для операций внутри одного территориального банка Сбербанка. " +
						"Пожалуйста, выберите другой счет зачисления.");
			}

		}
	}
}