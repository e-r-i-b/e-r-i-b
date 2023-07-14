package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.AccountIntraBankPayment;

/**
 * @author krenev
 * @ created 22.10.2010
 * @ $Author$
 * @ $Revision$
 * сендер длительного поручения на перевод физическому лицу в другой банк внутри Сбербанка России
 */
public class AccountIntraBankPaymentLongOfferSender extends SDPPaymentSender
{
	public AccountIntraBankPaymentLongOfferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected XferMethodType getXferMethod(AbstractRUSPayment document) throws GateException
	{
		if (isSameTB(document.getOffice(), document.getReceiverAccount()))
			return XferMethodType.OUR_TERBANK;
		
		return XferMethodType.EXTERNAL_TERBANK;
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if(!(document instanceof AccountIntraBankPayment))
			throw new GateException("Некорректный тип документа, ожидался наследник AccountIntraBankPayment.");

		AccountIntraBankPayment transfer = (AccountIntraBankPayment) document;
		if(!isLongOfferMode())
			return;

		LongOffer longOffer = (LongOffer) document;
		if(!isSameTB(transfer.getOffice(), transfer.getReceiverAccount()))
		{
			// проверка на то, что в регулярной платеже, перевод с типом суммы "сумма необходимая для увеличения остатка на счете получателя до
			// указанной суммы" совершается внутри одного ТБ.
			if(longOffer.getSumType() == SumType.REMAIND_IN_RECIP)
			{
				throw  new GateLogicException(REMAIND_IN_RECIP_MESSAGE);
			}

			// проверка на то, что в регулярной платеже, перевод с типом суммы " на сумму овердрафта на счете получателя"
			//совершается внутри одного ТБ.
			if(longOffer.getSumType() == SumType.OVER_DRAFT )
			{
				throw  new GateLogicException(OVER_DRAFT_MESSAGE);
			}

		}
	}
}
