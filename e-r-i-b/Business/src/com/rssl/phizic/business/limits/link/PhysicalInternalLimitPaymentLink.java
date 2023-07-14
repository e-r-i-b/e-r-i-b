package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.AccountIntraBankPayment;
import com.rssl.phizic.gate.payments.CardIntraBankPayment;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizic.gate.payments.autosubscriptions.EditExternalP2PAutoTransfer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AccountIntraBankPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CardIntraBankPaymentLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Оплаты на счет/карту частному лицу внутри СберБанка с карты/со счета
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class PhysicalInternalLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		typesOfPayments.add(AccountIntraBankPayment.class);                     //Перевод физическому лицу со счета в другой банк внутри Сбербанка России
		typesOfPayments.add(AccountIntraBankPaymentLongOffer.class);            //Перевод физическому лицу со счета в другой банк внутри Сбербанка России (Длительное поручение)
		typesOfPayments.add(CardIntraBankPayment.class);                        //Перевод физическому лицу с карты в другой банк внутри Сбербанка России (Длительное поручение)
		typesOfPayments.add(CardIntraBankPaymentLongOffer.class);               //Перевод физическому лицу с карты в другой банк внутри Сбербанка России (Длительное поручение)
		typesOfPayments.add(ExternalCardsTransferToOurBank.class);              //Перевод физическому лицу с карты на карту внутри Сбербанка
		typesOfPayments.add(ExternalCardsTransferToOurBankLongOffer.class);     //Автоперевод физическому лицу с карты на карту внутри Сбербанка
		typesOfPayments.add(ExternalCardsTransferToOtherBankLongOffer.class);   //Автоперевод физическому лицу с карты на карту внутри Сбербанка
		typesOfPayments.add(EditExternalP2PAutoTransfer.class);                 //Автоперевод физическому лицу с карты на карту внутри Сбербанка
		typesOfPayments.add(InternalCardsTransferLongOffer.class);              //Автоперевод между своими картами
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.PHYSICAL_INTERNAL_PAYMENT_LINK;
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}
}
