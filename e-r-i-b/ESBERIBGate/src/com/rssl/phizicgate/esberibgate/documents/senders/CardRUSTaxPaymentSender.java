package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardRUSTaxPayment;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.ws.generated.TaxColl_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.XferInfo_Type;

/**
 * Сендер для оплаты налогов с карты 
 * @author niculichev
 * @ created 08.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class CardRUSTaxPaymentSender extends CardJurTransferSender
{

	public CardRUSTaxPaymentSender(GateFactory factory) throws GateException
	{
		super(factory);
	}


	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardRUSTaxPayment))
		{
			throw new GateException("Неверный тип документа, должен быть - CardRUSTaxPayment.");
		}
		CardRUSTaxPayment payment = (CardRUSTaxPayment) document;
		// заполняем данные перевода юридическому лицу
		XferInfo_Type xferInfo = super.createBody(document);
		//заполннем реквизиты налогового получателя
		TaxColl_Type taxColl_type = new TaxColl_Type();
		taxColl_type.setSRCstatus(payment.getTaxPaymentStatus());
		taxColl_type.setTaxRegCodeTo(payment.getReceiverKPP());
		taxColl_type.setBudgetCode(payment.getTaxKBK());
		taxColl_type.setOKATO(payment.getTaxOKATO());
		taxColl_type.setTaxBase(payment.getTaxGround());
		taxColl_type.setTaxPeriod(payment.getTaxPeriod());
		taxColl_type.setTaxNum(payment.getTaxDocumentNumber());
		taxColl_type.setTaxDt(RequestHelperBase.getStringDate(payment.getTaxDocumentDate()));
		taxColl_type.setTaxType(payment.getTaxPaymentType());
		xferInfo.setTaxColl(taxColl_type);

		return xferInfo;
	}

}
