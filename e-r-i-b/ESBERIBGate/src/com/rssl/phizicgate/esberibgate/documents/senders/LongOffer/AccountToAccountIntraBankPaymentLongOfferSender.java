package com.rssl.phizicgate.esberibgate.documents.senders.LongOffer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.longoffer.AccountRUSPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сендер заявки "Перевод физическому лицу со вклада на счет в другой банк через платежную систему России(Длительное поручение)."
 */

public class AccountToAccountIntraBankPaymentLongOfferSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public AccountToAccountIntraBankPaymentLongOfferSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new AccountToAccountIntraBankPaymentLongOfferProcessor(getFactory(), (AccountRUSPaymentLongOffer) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Повторная отправка заявки на перевод физическому лицу со вклада на счет в другой банк через платежную систему России(Длительное поручение) не поддерживается");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв не поддерживается");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("Подготовка заявки на перевод физическому лицу со вклада на счет в другой банк через платежную систему России(Длительное поручение) не поддерживается");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("Подтверждение заявки на перевод физическому лицу со вклада на счет в другой банк через платежную систему России(Длительное поручение) не поддерживается");
	}

	public void validate(GateDocument document) throws GateException
	{
		if (!(document instanceof AbstractTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - AbstractTransfer, а пришел " + document.getClass());
		}

		//проверяем совпадение режима сендера и типа документа
		if (!LongOffer.class.isAssignableFrom(document.getType()))
		{
			throw new GateException("несовпадение тип документа и режима валидатора:\n тип документа =" + document.getType() + "; isLongOfferMode = true");
		}
		//проверим, что задан процент: Поле обязательно, если <SummaKindCode> = PERCENT_OF_REMAIND.
		LongOffer longOffer = (LongOffer) document;
		if (longOffer.getSumType() == SumType.PERCENT_OF_REMAIND && longOffer.getPercent() == null)
		{
			//где-то косяк в бизнесе - падаем
			throw new GateException(" Не соблюдается условие на Percent : Поле обязательно, если <SummaKindCode> = PERCENT_OF_REMAIND");
		}
		//проверяем, что задана сумма:Сумма обязательна при совершении платежа, а также при создании длительного поручения с кодами
		//1)FIXED_SUMMA,
		//2)REMAIND_OVER_SUMMA
		//3)FIXED_SUMMA_IN_RECIP_CURR
		//в поле <SummaKindCode>.
		SumType sumType = longOffer.getSumType();
		if ((sumType == SumType.FIXED_SUMMA || sumType == SumType.REMAIND_OVER_SUMMA||sumType == SumType.FIXED_SUMMA_IN_RECIP_CURR) && longOffer.getAmount() == null)
		{
			//где-то косяк в бизнесе - падаем
			throw new GateException("сумма отсутсвует для типа суммы :" + sumType);
		}
	}
}
