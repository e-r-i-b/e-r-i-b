package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.gate.payments.longoffer.ClientAccountsLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CreateCardToAccountLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EditCardToAccountLongOffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  онверсионные операции.
 *
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class ConversionLimitPaymentLink extends LimitPaymentsLinkBase
{
	private static final List<Class> typesOfPayments = new ArrayList<Class>();

	static
	{
		//переводы на внешние счета частному лицу.
		typesOfPayments.addAll(new PhysicalExternalAccountLimitPaymentLink().getPaymentTypes());
		//переводы на внешние карты частному лицу.
		typesOfPayments.addAll(new PhysicalExternalCardLimitPaymentLink().getPaymentTypes());
		//переводы внутри сбербанка на счета или карты.
		typesOfPayments.addAll(new PhysicalInternalLimitPaymentLink().getPaymentTypes());
		//переводы юр. лицу.
		typesOfPayments.addAll(new JuridicalExternalLimitPaymentLink().getPaymentTypes());

		//переводы между счетами/картами клиента.
		typesOfPayments.add(InternalCardsTransfer.class);                      //перевод между своими картами.
		typesOfPayments.add(CreateCardToAccountLongOffer.class);               //перевод со своей карты на счет (копилка).
		typesOfPayments.add(EditCardToAccountLongOffer.class);                 //перевод со своей карты на счет (редактирование копилки).
		typesOfPayments.add(CardToAccountTransfer.class);                      //перевод со своей карты на счет.
		typesOfPayments.add(AccountToCardTransfer.class);                      //перевод со своего счета на карту.
		typesOfPayments.add(ClientAccountsLongOffer.class);                    //перевод между своими счетами (длительное поручение).
		typesOfPayments.add(ClientAccountsTransfer.class);                     //перевод между своими счетами.
		typesOfPayments.add(IMAToAccountTransfer.class);                       //перевод с ќћ— на свой счет.
		typesOfPayments.add(AccountToIMATransfer.class);                       //перевод со своего счета на ќћ—.
	}

	public LimitPaymentsType getLinkType()
	{
		return LimitPaymentsType.CONVERSION_OPERATION_PAYMENT_LINK;
	}

	public List<Class> getPaymentTypes()
	{
		return Collections.unmodifiableList(typesOfPayments);
	}
}

