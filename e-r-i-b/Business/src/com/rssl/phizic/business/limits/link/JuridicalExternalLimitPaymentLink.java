package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.longoffer.CardJurIntraBankTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CardJurTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AccountPaymentSystemPaymentLongOfer;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Оплата юридическому лицу с карты/счета на счет
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class JuridicalExternalLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		typesOfPayments.add(CardJurIntraBankTransfer.class);            //Перевод юридическому лицу c карты на счет внутри Сбербанка России
		typesOfPayments.add(CardJurIntraBankTransferLongOffer.class);   //Длительное поручение на перевод юридическому лицу с карты клиента внутри банка
		typesOfPayments.add(CardJurTransfer.class);                     //Перевод со счета клиента юридическому лицу в другой банк через платежную систему России.
		typesOfPayments.add(CardJurTransferLongOffer.class);            //Длительное поручение на перевод юридическому лицу с карты клиента
		typesOfPayments.add(AccountJurTransfer.class);                  //Перевод со счета клиента юридическому лицу в другой банк через платежную систему России.
		typesOfPayments.add(AccountJurIntraBankTransfer.class);         //Перевод юридическому лицу cо счета на счет внутри Сбербанка России.
		typesOfPayments.add(AccountPaymentSystemPayment.class);         //Биллинговый платеж со счета
		typesOfPayments.add(AccountPaymentSystemPaymentLongOfer.class); //Автоплатеж на перевод со счета по биллинговой технологии
		typesOfPayments.add(CardPaymentSystemPayment.class);            //Биллинговый платеж с карты
		typesOfPayments.add(CardPaymentSystemPaymentLongOffer.class);   //Автоплатеж на перевод с карты по биллинговой технологии
		typesOfPayments.add(CardRUSTaxPayment.class);                   //Оплата налогов с карты
		typesOfPayments.add(AccountRUSTaxPayment.class);                //Оплата налогов со счета
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.JURIDICAL_PAYMENT_LINK;
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}
}
